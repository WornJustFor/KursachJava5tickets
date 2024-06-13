package com.example.pdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button abmCatBtn, cdCatBtn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        abmCatBtn = findViewById(R.id.abm_category);
        cdCatBtn = findViewById(R.id.cd_category);

        intent = new Intent(MainActivity.this, ListTicketsActivity.class);

        abmCatBtn.setOnClickListener(view -> {
            intent.putExtra("category", "A_B");
            startActivity(intent);
        });

        cdCatBtn.setOnClickListener(view -> {
            intent.putExtra("category", "C_D");
            startActivity(intent);
        });
    }
}