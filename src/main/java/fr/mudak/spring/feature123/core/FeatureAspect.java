package fr.mudak.spring.feature123.core;

import fr.mudak.spring.feature123.annotations.FeatureParam;
import fr.mudak.spring.feature123.annotations.FeatureSensitive;
import fr.mudak.spring.feature123.annotations.FeatureVersion;
import fr.mudak.spring.feature123.core.exception.FeatureRuntimeException;
import fr.mudak.spring.feature123.namespace.FeatureBeanDefinitionParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class FeatureAspect implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ApplicationContext context;

    private Map<String, Feature> features;

    private final Logger logger = LoggerFactory.getLogger(FeatureAspect.class);

    // Define anyPublicMethod used in next method
    @Pointcut(value="execution(public * *(..))")
        public void anyPublicMethod() {
    }

    @Around("anyPublicMethod() && @annotation(annotation )")
    public Object myAspect(ProceedingJoinPoint pjp, FeatureSensitive annotation) throws Throwable {

        // Extract parameter values used by the rule
        List<Object> annotatedParameters = extractParameterValuesForRule(pjp);

        // Call the Rule
        Feature feature = this.features.get(annotation.feature());
        Object ruleBean = feature.getRule();
        Method match = ruleBean.getClass().getMethod("match", Object[].class);
        FeatureRule.FeatureRuleVersion version = (FeatureRule.FeatureRuleVersion)match.invoke(ruleBean, new Object[]{annotatedParameters.toArray()});

        // Execute the good version of the feature.
        FeatureElement element = feature.getElements().get(annotation.element());

        if (version.equals(FeatureRule.FeatureRuleVersion.DEFAULT)) {
            logger.debug("Invoking DEFAULT version of the feature.");
            return pjp.proceed();
        } else {
            logger.debug("Invoking version " + version + " of the feature.");
            FeatureImplementation featureImpl = element.getImplementations().get(version);
            if (featureImpl == null) {
                throw new FeatureRuntimeException("Version " + version + " does not exists for feature " + feature.getName() + " and element " + element.getElement() + ". Please define it.");
            }
            Object bean = featureImpl.getBean();
            Method method = featureImpl.getMethod();
            return method.invoke(bean, pjp.getArgs());
        }

    }

    private List<Object> extractParameterValuesForRule(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        // First action is to extract the parameters values that must be sent to Rule.
        List<Object> annotatedParameters = new ArrayList<Object>();

        // retrieve the methods parameter types :
        final Signature signature = pjp.getStaticPart().getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature ms = (MethodSignature) signature;
            String methodName = ms.getMethod().getName();
            Class<?>[] parameterTypes = ms.getParameterTypes();
            Annotation[][] annotations = pjp.getTarget().getClass().getMethod(methodName,parameterTypes).getParameterAnnotations();

            if (annotations != null) {
                for (int i = 0 ; i < annotations.length; i++) {
                    List<Annotation> annot = Arrays.asList(annotations[i]);
                    for (Annotation a : annot) {
                        if (a instanceof FeatureParam) {
                            annotatedParameters.add(pjp.getArgs()[i]);
                        }
                    }
                }
            }
        }
        return annotatedParameters;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (features != null) {
            return;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Feature loading");
        logger.debug("Loading Feature annotations configuration. This happens only once, after Spring context has loaded.");

        logger.debug("logging package : " + FeatureBeanDefinitionParser.scanPackage);
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(FeatureBeanDefinitionParser.scanPackage))
                        .setScanners(new MethodAnnotationsScanner(),
                                new MethodParameterScanner())
                        .filterInputsBy(new FilterBuilder().includePackage(FeatureBeanDefinitionParser.scanPackage))
        );

        // Find all Features
        Set<Method> annotated = reflections.getMethodsAnnotatedWith(FeatureSensitive.class);
        logger.debug("Found " + annotated.size() + " FeatureSensitive annotations.");

        // Create all the Features and keep them in a List
        features = new HashMap<String, Feature>(annotated.size());
        for (Method method : annotated) {
            FeatureSensitive annotation = method.getAnnotation(FeatureSensitive.class);

            // Feature
            Feature feature = null;
            if ( !features.containsKey(annotation.feature()) ) {
                logger.debug("Configuring a new Feature : " + annotation.feature() +" element : " + annotation.element());
                feature = new Feature();
                feature.setName(annotation.feature());
            } else {
                feature = features.get(annotation.feature());
            }

            // Element
            FeatureElement element = null;
            if (feature.getElements().get(annotation.element()) == null) {
                logger.debug("Creating a new element : " + annotation.element());
                element = new FeatureElement();
                element.setElement(annotation.element());
            } else {
                element = feature.getElements().get(annotation.element());
            }
            element.setParameters(Arrays.asList(method.getParameterTypes()));

            // Find all implementations
            Set<Method> annotatedImplementation = reflections.getMethodsAnnotatedWith(FeatureVersion.class);

            // Extract implementations for this particular Feature
            for (Method methodImpl : annotatedImplementation) {
                FeatureVersion version = methodImpl.getAnnotation(FeatureVersion.class);
                if (version.feature().equals(feature.getName())
                        && element.getElement().equals(version.element())
                        && !element.getImplementations().containsKey(version.version())) {
                    logger.debug("Found a new Implementation for Feature " + feature.getName() + " in class " + methodImpl.getDeclaringClass());
                    FeatureImplementation implementation = new FeatureImplementation();
                    implementation.setBean(context.getBean(methodImpl.getDeclaringClass()));
                    implementation.setMethod(methodImpl);
                    implementation.setArguments(method.getParameterTypes());
                    element.getImplementations().put(version.version(), implementation);
                }
            }

            feature.getElements().put(element.getElement(), element);

            // Find the Rule
            Class<?> ruleClass = annotation.rule();
            Object ruleBean = context.getBean(ruleClass);
            feature.setRule(ruleBean);
            logger.debug("Feature " + feature.getName() + " is associated to this rule : " + ruleClass);

            // Find the Rule parameters (@FeatureParam)
            logger.debug("Loading parameters annotated with @FeatureParam.");
            Set<Method> paramAnnotations = reflections.getMethodsWithAnyParamAnnotated(FeatureParam.class);
            for (Method m : paramAnnotations) {
                if (isMethodAnnotatedWith(m, FeatureVersion.class)) {
                    FeatureVersion version = m.getAnnotation(FeatureVersion.class);
                    if (version.feature().equals(feature.getName())
                            && version.element().equals(element.getElement())) {
                        Annotation[][] annotatedParameters = m.getParameterAnnotations();
                        for (int i = 0; i < annotatedParameters.length; i++) {
                            if (Arrays.asList(annotatedParameters[i]).contains(FeatureParam.class)) {
                                feature.getRuleParameters().add(m.getParameterTypes()[i]);
                            }

                        }
                    }
                }
            }

            features.put(feature.getName(), feature);
        }
        stopWatch.stop();
        logger.debug("Feature configuration loaded in " + stopWatch.getLastTaskTimeMillis() + " ms.");
    }

    private boolean isMethodAnnotatedWith( Method method, Class annotation) {
        return (method != null && annotation != null && method.getAnnotation(annotation) != null);
    }
}
