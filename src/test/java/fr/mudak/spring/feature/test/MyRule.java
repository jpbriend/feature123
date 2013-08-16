package fr.mudak.spring.feature.test;


import fr.mudak.spring.feature123.core.FeatureRule;
import org.springframework.stereotype.Component;

@Component
public class MyRule implements FeatureRule {

    @Override
    public FeatureRuleVersion match(Object... parameters) {
        System.out.println("Yeah Inside the Rule !!!!");
        if (parameters[0].equals("1")) {
            return FeatureRuleVersion.ONE;
        } else if (parameters[0].equals("2")) {
            return FeatureRuleVersion.TWO;
        }
        return FeatureRuleVersion.DEFAULT;
    }
}
