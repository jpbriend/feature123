package fr.mudak.spring.feature123.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feature {

    private String name;
    private Map<String, FeatureElement> elements;
    private Object rule;
    private List<Class<?>> ruleParameters;

    public Feature() {
        this.ruleParameters = new ArrayList<Class<?>>();
        this.elements = new HashMap<String, FeatureElement>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getRule() {
        return rule;
    }

    public void setRule(Object rule) {
        this.rule = rule;
    }

    public List<Class<?>> getRuleParameters() {
        return ruleParameters;
    }

    public void setRuleParameters(List<Class<?>> ruleParameters) {
        this.ruleParameters = ruleParameters;
    }

    public Map<String, FeatureElement> getElements() {
        return elements;
    }

    public void setElements(Map<String, FeatureElement> elements) {
        this.elements = elements;
    }
}
