package com.example.pdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = findViewById(R.id.resultText);
        Intent intent = getIntent();

        int score = intent.getIntExtra("score", 0);
        int countQuestions = intent.getIntExtra("questions", 0);

        result.setText("Правильных ответов:\n" + score + "/" + countQuestions);
    }
}