package fr.mudak.spring.feature.test;

import fr.mudak.spring.feature123.annotations.FeatureVersion;
import fr.mudak.spring.feature123.core.FeatureRule;
import org.springframework.stereotype.Component;

@Component
public class MyBeanImplementationNoParameter {

    @FeatureVersion(feature="TestFeature2", element = "methodNoParameter", version = FeatureRule.FeatureRuleVersion.ONE)
    public void methodToBeFeaturedWithoutParameter(String param1,String param2) {
        System.out.println("ImplementationNoParameter has been invoked.");
    }
}
