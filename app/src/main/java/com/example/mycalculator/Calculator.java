package com.example.mycalculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {

    private double currentVal;          // текущее вводимое значение
    private double firstVal;            // первое введенное значение
    private double secondVal;           // второе введенное значение
    private double result;              // результат вычислений
    private boolean hasDot;             // флаг установки дробной точки
    private boolean inProgress;         // флаг указывающий, что вычисление в процессе
    private boolean isCalculated;       // флаг указывающий, что текущее вычисление произведено
    private int order;                  // текущий порядок дробной части числа
    private View view;                  // переменная типа View, куда передается сама MainActivity
    private Operations operation;       // объект enum Operations

    public Calculator(View view) {
        this.view = view;
        this.currentVal = 0;
        this.order = 1;
        this.hasDot = false;
        this.inProgress = false;
        this.isCalculated = false;
    }

    // конструктор для Parcelable
    protected Calculator(Parcel in) {
        currentVal = in.readDouble();
        firstVal = in.readDouble();
        secondVal = in.readDouble();
        result = in.readDouble();
        hasDot = in.readByte() != 0;
        inProgress = in.readByte() != 0;
        isCalculated = in.readByte() != 0;
        order = in.readInt();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    // Метод, возвращающий текущее значение в виде строки для вывода на экран
    public String getCurrentValString() {
        if (inProgress) return valToString(result); // если  новое значение еще не начали воодить, то возвращаем последний результат
        return valToString(currentVal);             // иначе возвращаем текущее значение
    }

    // Сеттер для view
    public void setView(View view) {
        this.view = view;
    }

    // Метод, устанавливающий дробную точку
    public void setDot() {
        this.hasDot = true;
    }

    // Метод, устанавливающий знак вводимого значения
    public void setSign() {
        currentVal *= -1;
        view.displayInput(valToString(currentVal));
    }

    // Метод вычисления квадратного корня
    public void sqrt() {
        selectOperation(Operations.SQRT);       // выбираем соотвествующую операцию из enum
        calculate();                            // запускаем вычисление
    }

    // Метод создания текущего значения
    public void createVal(int digit) {
        if (isCalculated) cancel();                             // если последнее вычисление завершено, вызываем сброс
        if (hasDot) {                                           // если установлена дробная точка,
            currentVal += (double) digit / Math.pow(10, order); // добавляем цифру в дробную часть в соответсвующий разряд
            order++;                                            // инкрементируем текущий разряд
        } else                                                  // если число целое,
            currentVal = currentVal * 10 + digit;               // добавляем новую цифру в младший разряд
        view.displayInput(valToString(currentVal));             // выводим текущее значение на экран
    }

    // Метод выбора арифметической операции
    public void selectOperation(Operations operation) {
        this.operation = operation;
        firstVal = currentVal;
        clearCurrentInput();
    }

    // Метод проведения вычисления в соответсие с выбранной операцией
    public void calculate() {
        secondVal = currentVal;
        if (!inProgress) result = firstVal;
        switch (operation) {
            case INCREASE:
                result += secondVal;
                break;
            case DECREASE:
                result -= secondVal;
                break;
            case MULTIPLY:
                result *= secondVal;
                break;
            case DIVIDE:
                result /= secondVal;
                break;
            case SQRT:
                if (result >= 0) result = Math.pow(result, 0.5);
                else result = 0;
                break;
        }
        inProgress = true;
        isCalculated = true;
        view.displayInput(valToString(result));
    }

    // метод выполнения полного сброса
    public void cancel() {
        clearCurrentInput();
        firstVal = 0;
        result = 0;
        inProgress = false;
        view.displayInput(valToString(currentVal));
    }

    // метод очистки ввода текущего значения
    private void clearCurrentInput() {
        currentVal = 0;
        hasDot = false;
        order = 1;
        isCalculated = false;
    }

    // Метод преобразования передаваемого числа в строку
    public String valToString(double value) {
        if ((value * 10) % 10 > 0) return String.valueOf(value);
        return String.valueOf((int) value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(currentVal);
        dest.writeDouble(firstVal);
        dest.writeDouble(secondVal);
        dest.writeDouble(result);
        dest.writeByte((byte) (hasDot ? 1 : 0));
        dest.writeByte((byte) (inProgress ? 1 : 0));
        dest.writeByte((byte) (isCalculated ? 1 : 0));
        dest.writeInt(order);
    }

    // Внутренний интерфейс, который будет реализовывать MainActivity
    // для передачи полномочий вывода данных на экран объекту класса Calculator
    public interface View {
        void displayInput(String value);
    }
}
