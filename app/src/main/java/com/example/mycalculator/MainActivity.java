package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
      Для отрисовки клавиатуры калькулятора был выбран GridLayout, так как этот лейаут, в сочетании с FrameLayout,
      наиболее оптимально подходит для реализации данной задачи. В итоге получаем лаконичный и читабельный код
      разметки и не теряем в производительности.
     */
}