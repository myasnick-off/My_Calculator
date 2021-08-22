package com.example.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Calculator.View {

    private static final String KEY_CALCULATOR_VALUE = "CALCULATOR_VALUE";

    private Calculator calculator;
    private TextView displayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {                                        // если запуск впервые,
            calculator = new Calculator(this);                              // создаем новый калькулятор
        } else {                                                                 // если запуск перезапуск
            calculator = savedInstanceState.getParcelable(KEY_CALCULATOR_VALUE); // загружаем сохраненный калькулятор
            calculator.setView(this);                                            // заново присваиваем текущий View
        }
        init();                                                                  // запускаем метод инициализации
        buttonsHandler();                                                        // запускаем метод-обработчик кнопок
    }

    /* Метод инициализации */
    private void init() {
        displayTextView = findViewById(R.id.display_textView);  // находим экран калькулятора по id и присваиваем переменной
        this.displayInput(calculator.getCurrentValString());    // выводим на экран калькулятора текущие данные (нужно при восстановлении активити)
    }

    /* Метод-обработчик кнопок */
    private void buttonsHandler() {
        findViewById(R.id.btn_dot).setOnClickListener(v -> calculator.setDot());
        findViewById(R.id.btn_sign).setOnClickListener(v -> calculator.setSign());
        findViewById(R.id.btn_plus).setOnClickListener(v -> calculator.selectOperation(Operations.INCREASE));
        findViewById(R.id.btn_minus).setOnClickListener(v -> calculator.selectOperation(Operations.DECREASE));
        findViewById(R.id.btn_multiply).setOnClickListener(v -> calculator.selectOperation(Operations.MULTIPLY));
        findViewById(R.id.btn_divide).setOnClickListener(v -> calculator.selectOperation(Operations.DIVIDE));
        findViewById(R.id.btn_sqrt).setOnClickListener(v -> calculator.sqrt());
        findViewById(R.id.btn_zero).setOnClickListener(v -> calculator.createVal(0));
        findViewById(R.id.btn_one).setOnClickListener(v -> calculator.createVal(1));
        findViewById(R.id.btn_two).setOnClickListener(v -> calculator.createVal(2));
        findViewById(R.id.btn_three).setOnClickListener(v -> calculator.createVal(3));
        findViewById(R.id.btn_four).setOnClickListener(v -> calculator.createVal(4));
        findViewById(R.id.btn_five).setOnClickListener(v -> calculator.createVal(5));
        findViewById(R.id.btn_six).setOnClickListener(v -> calculator.createVal(6));
        findViewById(R.id.btn_seven).setOnClickListener(v -> calculator.createVal(7));
        findViewById(R.id.btn_eight).setOnClickListener(v -> calculator.createVal(8));
        findViewById(R.id.btn_nine).setOnClickListener(v -> calculator.createVal(9));
        findViewById(R.id.btn_cancel).setOnClickListener(v -> calculator.cancel());
        findViewById(R.id.btn_result).setOnClickListener(v -> calculator.calculate());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CALCULATOR_VALUE, calculator);
    }

    // Переопределяем метод внутреннего интерфейса класса Calculator, который имплементировала MainActivity
    @Override
    public void displayInput(String value) {
        displayTextView.setText(value);
    }
}
