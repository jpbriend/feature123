package fr.mudak.spring.feature123.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureElement {
    private String element;

    private List<Class<?>> parameters;

    private Map<FeatureRule.FeatureRuleVersion, FeatureImplementation> implementations;

    public FeatureElement() {
        this.parameters = new ArrayList<Class<?>>();
        this.implementations = new HashMap<FeatureRule.FeatureRuleVersion, FeatureImplementation>();
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public List<Class<?>> getParameters() {
        return parameters;
    }

    public void setParameters(List<Class<?>> parameters) {
        this.parameters = parameters;
    }

    public Map<FeatureRule.FeatureRuleVersion, FeatureImplementation> getImplementations() {
        return implementations;
    }

    public void setImplementations(Map<FeatureRule.FeatureRuleVersion, FeatureImplementation> implementations) {
        this.implementations = implementations;
    }
}
