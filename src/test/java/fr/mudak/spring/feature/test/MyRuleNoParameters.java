package fr.mudak.spring.feature.test;


import fr.mudak.spring.feature123.core.FeatureRule;
import org.springframework.stereotype.Component;

@Component
public class MyRuleNoParameters implements FeatureRule {

    @Override
    public FeatureRuleVersion match(Object... parameters) {
        System.out.println("Yeah Inside the NoParameter Rule !!!!");

        return FeatureRuleVersion.ONE;
    }
}
