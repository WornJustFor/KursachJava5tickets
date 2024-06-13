package com.example.pdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;

public class ListTicketsActivity extends AppCompatActivity {

    ListView listView;
    String[] ticketsList;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tickets);


        Intent extrasIntent = getIntent();
        String category = extrasIntent.getStringExtra("category");

        listView = findViewById(R.id.ticketsList);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            intent = new Intent(ListTicketsActivity.this, QuizActivity.class);
            intent.putExtra("category", category);
            intent.putExtra("ticket_num", "" + (i + 1));
            startActivity(intent);
        });

        ticketsList = new String[5];
        for (int i = 0; i < 5; i++) {
            ticketsList[i] = "Билет " + (i + 1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ticketsList);
        listView.setAdapter(adapter);
    }
}