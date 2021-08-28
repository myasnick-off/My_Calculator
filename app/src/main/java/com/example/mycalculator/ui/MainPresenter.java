package com.example.mycalculator.ui;

import com.example.mycalculator.domain.Calculation;
import com.example.mycalculator.domain.Operations;

/* Класс-презентер для калькулятора */
public class MainPresenter {

    MainView view;              // переменная типа MainView, куда передается сама MainActivity
    Calculation calculator;     // переменная типа Калькулятор

    public MainPresenter(MainView view, Calculation calculator) {
        this.view = view;
        this.calculator = calculator;
    }

    // Метод обратотки нажатия кнопки "точка"
    public void onDotKeyPressed() {
        calculator.setDot();
    }

    // Метод обратотки нажатия кнопки выбора знака
    public void onSignKeyPressed() {
        double value = calculator.setSign();
        view.displayInput(valToString(value));
    }

    // Метод обратотки нажатия кнопки с цифрой
    public void onDigitKeyPressed(int digit) {
        double value = calculator.createVal(digit);
        view.displayInput(valToString(value));          // выводим текущее значение на экран
    }

    // Метод обратотки нажатия кнопки арифметической операции
    public void onOperationKeyPressed(Operations operation) {
        calculator.selectOperation(operation);
    }

    // Метод обратотки нажатия кнопки "квадратный корень"
    public void onSqrtKeyPressed() {
        double result = calculator.sqrt();
        view.displayInput(valToString(result));
    }

    // Метод обратотки нажатия кнопки "равно"
    public void onResultKeyPressed() {
        double result = calculator.calculate();
        view.displayInput(valToString(result));
    }

    // Метод обратотки нажатия кнопки сброса
    public void onCancelKeyPressed() {
        calculator.cancel();
        view.displayInput(valToString(0));
    }

    // Метод отображения текущего значения на экране калькулятора (необходим при пересоздании акитивити)
    public void displayCurrentValue() {
        double value = calculator.getCurrentVal();
        view.displayInput(valToString(value));
    }

    // Метод преобразования передаваемого числа в строку
    public String valToString(double value) {
        if ((value * 10) % 10 > 0) {
            return String.valueOf(value);
        }
        return String.valueOf((int) value);
    }
}
