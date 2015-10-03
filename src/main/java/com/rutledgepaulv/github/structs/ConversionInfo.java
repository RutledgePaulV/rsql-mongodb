package com.rutledgepaulv.github.structs;

import com.rutledgepaulv.github.Operator;

public class ConversionInfo {

    private String pathToField;
    private String argument;
    private Class<?> targetEntityClass;
    private Operator operator;

    public String getPathToField() {
        return pathToField;
    }

    public ConversionInfo setPathToField(String pathToField) {
        this.pathToField = pathToField;
        return this;
    }

    public String getArgument() {
        return argument;
    }

    public ConversionInfo setArgument(String argument) {
        this.argument = argument;
        return this;
    }

    public Class<?> getTargetEntityClass() {
        return targetEntityClass;
    }

    public ConversionInfo setTargetEntityClass(Class<?> targetEntityClass) {
        this.targetEntityClass = targetEntityClass;
        return this;
    }

    public Operator getOperator() {
        return operator;
    }

    public ConversionInfo setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

}
