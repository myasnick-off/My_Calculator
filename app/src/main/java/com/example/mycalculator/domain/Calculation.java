package com.example.mycalculator.domain;

public interface Calculation {

    double getCurrentVal();

    double createVal(int digit);

    void selectOperation(Operations operation);

    double calculate();

    double sqrt();

    void setDot();

    double setSign();

    void cancel();
}
