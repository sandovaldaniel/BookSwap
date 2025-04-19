package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {

    TextView txtTitle, txtAuthor, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtStatus = findViewById(R.id.txtStatus);

        Button btnBackHome = findViewById(R.id.btnBackHome);

        // Obtener datos desde el intent
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String status = getIntent().getStringExtra("status");

        txtTitle.setText("Title: " + title);
        txtAuthor.setText("Author: " + author);
        txtStatus.setText("Status: " + status);

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(BookDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        });

    }
}
