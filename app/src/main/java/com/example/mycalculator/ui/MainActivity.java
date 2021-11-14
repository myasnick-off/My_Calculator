package com.example.mycalculator.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mycalculator.R;
import com.example.mycalculator.domain.Calculator;
import com.example.mycalculator.domain.Operations;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String KEY_CALCULATOR_VALUE = "CALCULATOR_VALUE";
    private static final String KEY_THEME_VAL = "KEY_THEME";
    private static final int DEFAULT_THEME = 0;

    private Calculator calculator;
    private MainPresenter presenter;
    private TextView displayTextView;
    private int themeIndex;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        themeIndex = result.getData().getIntExtra(SettingsActivity.KEY_THEME, DEFAULT_THEME);
                        recreate();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {                                        // если запуск впервые,
            calculator = new Calculator();                                       // создаем новый калькулятор
        } else {                                                                 // если перезапуск
            calculator = savedInstanceState.getParcelable(KEY_CALCULATOR_VALUE); // загружаем сохраненный калькулятор
            themeIndex = savedInstanceState.getInt(KEY_THEME_VAL);
        }
        // Установка темы приложения в зависимости от выбранных настроек
        switch (themeIndex) {
            case 0:
                setTheme(R.style.Theme_MyCalculator);
                break;
            case 1:
                setTheme(R.style.Theme_OrangeCalculator);
                break;
            case 2:
                setTheme(R.style.Theme_PurpleCalculator);
                break;
        }
        setContentView(R.layout.activity_main);
        init();                                                                  // запускаем метод инициализации
        buttonsHandler();                                                        // запускаем метод-обработчик кнопок
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Метод обработки нажатия элементов Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_item) {
            openSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Метод инициализации */
    private void init() {
        displayTextView = findViewById(R.id.display_text_view);  // находим экран калькулятора по id и присваиваем переменной
        presenter = new MainPresenter(this, calculator);    // создаем объект презентера с текущей активити и калькулятором в качестве праметров
        presenter.displayCurrentValue();                         // выводим на экран калькулятора текущие данные (нужно при восстановлении активити)
    }

    /* Метод-обработчик кнопок */
    private void buttonsHandler() {
        findViewById(R.id.btn_dot).setOnClickListener(v -> presenter.onDotKeyPressed());
        findViewById(R.id.btn_sign).setOnClickListener(v -> presenter.onSignKeyPressed());
        findViewById(R.id.btn_plus).setOnClickListener(v -> presenter.onOperationKeyPressed(Operations.INCREASE));
        findViewById(R.id.btn_minus).setOnClickListener(v -> presenter.onOperationKeyPressed(Operations.DECREASE));
        findViewById(R.id.btn_multiply).setOnClickListener(v -> presenter.onOperationKeyPressed(Operations.MULTIPLY));
        findViewById(R.id.btn_divide).setOnClickListener(v -> presenter.onOperationKeyPressed(Operations.DIVIDE));
        findViewById(R.id.btn_sqrt).setOnClickListener(v -> presenter.onSqrtKeyPressed());
        findViewById(R.id.btn_zero).setOnClickListener(v -> presenter.onDigitKeyPressed(0));
        findViewById(R.id.btn_one).setOnClickListener(v -> presenter.onDigitKeyPressed(1));
        findViewById(R.id.btn_two).setOnClickListener(v -> presenter.onDigitKeyPressed(2));
        findViewById(R.id.btn_three).setOnClickListener(v -> presenter.onDigitKeyPressed(3));
        findViewById(R.id.btn_four).setOnClickListener(v -> presenter.onDigitKeyPressed(4));
        findViewById(R.id.btn_five).setOnClickListener(v -> presenter.onDigitKeyPressed(5));
        findViewById(R.id.btn_six).setOnClickListener(v -> presenter.onDigitKeyPressed(6));
        findViewById(R.id.btn_seven).setOnClickListener(v -> presenter.onDigitKeyPressed(7));
        findViewById(R.id.btn_eight).setOnClickListener(v -> presenter.onDigitKeyPressed(8));
        findViewById(R.id.btn_nine).setOnClickListener(v -> presenter.onDigitKeyPressed(9));
        findViewById(R.id.btn_cancel).setOnClickListener(v -> presenter.onCancelKeyPressed());
        findViewById(R.id.btn_result).setOnClickListener(v -> presenter.onResultKeyPressed());
    }

    // Метод запуска активити с настройками
    private void openSettingsActivity() {
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        settingsIntent.putExtra(SettingsActivity.KEY_THEME, themeIndex);
        activityResultLauncher.launch(settingsIntent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CALCULATOR_VALUE, calculator);
        outState.putInt(KEY_THEME_VAL, themeIndex);
    }

    // Переопределяем метод интерфейса MainView, который реализует MainActivity
    @Override
    public void displayInput(String value) {
        displayTextView.setText(value);
    }
}
