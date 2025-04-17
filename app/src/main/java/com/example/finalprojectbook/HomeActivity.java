package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnAddBook, btnNewOperation, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAddBook = findViewById(R.id.btnAddBook);
        btnNewOperation = findViewById(R.id.btnNewOperation);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddBook.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, NewBookActivity.class);
            startActivity(intent);
        });

        btnNewOperation.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, NewOperationActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(view -> {
            finish(); // vuelve al login
        });
    }
}
