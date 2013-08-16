package fr.mudak.spring.feature.test;

import fr.mudak.spring.feature123.annotations.FeatureParam;
import fr.mudak.spring.feature123.annotations.FeatureVersion;
import fr.mudak.spring.feature123.core.FeatureRule;
import org.springframework.stereotype.Component;

@Component
public class MyBeanImplementation2 {

    @FeatureVersion(feature="TestFeature1", element = "methodWithParameters", version = FeatureRule.FeatureRuleVersion.TWO)
    public void methodToBeFeatured(String param1,String param2) {
        System.out.println("Implementation2 has been invoked.");
    }
}
