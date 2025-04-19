package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView bookListView;
    Button btnAddBook, btnNewOperation, btnLogout, btnAllBooks, btnMyOperations, btnMyBooks;
    List<String> bookTitles = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bookListView = findViewById(R.id.bookListView);
        btnAddBook = findViewById(R.id.btnAddBook);
        btnNewOperation = findViewById(R.id.btnNewOperation);
        btnLogout = findViewById(R.id.btnLogout);
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnMyOperations = findViewById(R.id.btnMyOperations);
        btnMyBooks = findViewById(R.id.btnMyBooks);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookTitles);
        bookListView.setAdapter(adapter);

        String userEmail = getIntent().getStringExtra("user_email");

        bookListView.setOnItemClickListener((parent, view, position, id) -> {
            String entry = bookTitles.get(position);
            String[] parts = entry.split(" by ");
            String title = parts[0];
            String authorStatus = parts[1];
            String[] authorAndStatus = authorStatus.split(" \\(");
            String author = authorAndStatus[0];
            String status = authorAndStatus[1].replace(")", "");

            Intent intent = new Intent(HomeActivity.this, BookDetailsActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("author", author);
            intent.putExtra("status", status);
            startActivity(intent);
        });



        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NewBookActivity.class);
            startActivity(intent);
        });

        btnNewOperation.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NewOperationActivity.class);
            startActivity(intent);
        });

        loadBooksFromFirebase();

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

    private void loadBooksFromFirebase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("books");
        dbRef.limitToLast(6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bookTitles.clear();
                for (DataSnapshot bookSnap : snapshot.getChildren()) {
                    Book book = bookSnap.getValue(Book.class);
                    if (book != null) {
                        bookTitles.add(book.title + " by " + book.author + " (" + book.status + ")");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

    }
}
