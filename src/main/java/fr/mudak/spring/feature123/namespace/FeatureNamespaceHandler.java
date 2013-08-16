package fr.mudak.spring.feature123.namespace;


import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class FeatureNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("feature-enable", new FeatureBeanDefinitionParser());
    }
}
