package fr.mudak.spring.feature.test;

import fr.mudak.spring.feature123.annotations.FeatureParam;
import fr.mudak.spring.feature123.annotations.FeatureSensitive;
import fr.mudak.spring.feature123.annotations.FeatureVersion;
import fr.mudak.spring.feature123.core.FeatureRule;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @FeatureSensitive(feature="TestFeature1", element="methodWithParameters", rule = MyRule.class)
    public void methodToBeFeatured(@FeatureParam String param1, @FeatureParam String param2) {
        System.out.println("MyBean.methodToBeFeatured invoked.");
    }

    @FeatureSensitive(feature="TestFeature2", element="methodNoParameter", rule = MyRuleNoParameters.class)
    public void methodToBeFeaturedWithoutParameters(String param1, String param2) {
        System.out.println("MyBean.methodToBeFeaturedWithoutParameters invoked.");
    }
}
