package fr.mudak.spring.feature.test;

import fr.mudak.spring.feature123.annotations.FeatureParam;
import fr.mudak.spring.feature123.annotations.FeatureVersion;
import fr.mudak.spring.feature123.core.FeatureRule;
import org.springframework.stereotype.Component;

@Component
public class MyBeanImplementation1 {

    @FeatureVersion(feature="TestFeature1", element = "methodWithParameters", version = FeatureRule.FeatureRuleVersion.ONE)
    public void methodToBeFeatured(@FeatureParam String param1, @FeatureParam String param2) {
        System.out.println("Implementation1 has been invoked.");
    }
}
