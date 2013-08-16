package fr.mudak.spring.feature123.core;

public interface FeatureRule {

    /**
     * Method called at runtime to determine which Feature version must be used.
     * @param parameters
     * @return version of the Feature.
     */
    FeatureRuleVersion match(Object... parameters);

    public enum FeatureRuleVersion {
        DEFAULT,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE
    }
}
