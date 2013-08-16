package fr.mudak.spring.feature123.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
import org.w3c.dom.Element;


public class FeatureBeanDefinitionParser implements BeanDefinitionParser {

    private ComponentScanBeanDefinitionParser componentParser;

    // Ugly but simple to transfer a property to the FeatureAspect.
    public static String scanPackage = "";

    public FeatureBeanDefinitionParser() {
        this.componentParser = new ComponentScanBeanDefinitionParser();
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        scanPackage = element.getAttribute("scan-package");

        element.setAttribute("base-package", "fr.mudak.spring.feature123");
        this.componentParser.parse(element, parserContext);

        return null;
    }
}
