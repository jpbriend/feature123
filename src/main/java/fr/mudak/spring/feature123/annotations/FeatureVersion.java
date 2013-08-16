package fr.mudak.spring.feature123.annotations;

import fr.mudak.spring.feature123.core.FeatureRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FeatureVersion {
    /**
     * Name of the Feature
     * @return
     */
    String feature();

    /**
     * Sub-element of the Feature
     */
    String element();

    /**
     * Version number of this specific Version
     * @return
     */
    FeatureRule.FeatureRuleVersion version();
}
