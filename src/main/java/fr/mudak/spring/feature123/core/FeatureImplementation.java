package fr.mudak.spring.feature123.core;

import java.lang.reflect.Method;

public class FeatureImplementation {

    private Object bean;
    private Method method;
    private Class[] arguments;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class[] getArguments() {
        return arguments;
    }

    public void setArguments(Class[] arguments) {
        this.arguments = arguments;
    }
}
