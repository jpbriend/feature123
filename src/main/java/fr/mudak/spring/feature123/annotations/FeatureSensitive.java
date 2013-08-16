package fr.mudak.spring.feature123.annotations;

import fr.mudak.spring.feature123.core.FeatureRule;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FeatureSensitive {
    /**
     * Name of the Feature
     **/
    String feature();

    /**
     * Sub Element of the Feature.
     * @return
     */
    String element();

    /**
     * Rule associated to this Feature
     **/
    Class<? extends FeatureRule> rule();
}
