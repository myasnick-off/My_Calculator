package com.example.mycalculator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mycalculator.R;
import com.example.mycalculator.domain.Calculator;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_THEME = "KEY_THEME";

    private int themeIndex;
    private Intent launchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchIntent = getIntent();
        themeIndex = launchIntent.getIntExtra(KEY_THEME, 0);
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
        setContentView(R.layout.activity_settings);
        spinnerExecutor();
    }

    // Метод для инициализации и работы со спиннером
    private void spinnerExecutor() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.theme_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.theme_spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(themeIndex);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                themeIndex = position;
                Intent dataIntent = new Intent();
                dataIntent.putExtra(KEY_THEME, themeIndex);
                setResult(Activity.RESULT_OK, dataIntent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}