package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnAddBook, btnNewOperation, btnLogout, btnAllBooks, btnMyOperations, btnMyBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAddBook = findViewById(R.id.btnAddBook);
        btnNewOperation = findViewById(R.id.btnNewOperation);
        btnLogout = findViewById(R.id.btnLogout);
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnMyOperations = findViewById(R.id.btnMyOperations);
        btnMyBooks = findViewById(R.id.btnMyBooks);


        String userEmail = getIntent().getStringExtra("user_email");

        btnAddBook.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, NewBookActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnNewOperation.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, NewOperationActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnAllBooks.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ListBooksActivity.class);
            startActivity(intent);
        });

        btnMyOperations.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyOperationsActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnMyBooks.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyBooksActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });



        btnLogout.setOnClickListener(view -> {
            finish(); // back to login
        });
    }
}
