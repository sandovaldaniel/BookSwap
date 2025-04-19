package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ListBooksActivity extends AppCompatActivity {

    ListView listBooks;
    ArrayAdapter<String> adapter;
    List<String> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);

        listBooks = findViewById(R.id.listBooks);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        listBooks.setAdapter(adapter);

        Button btnBackHome = findViewById(R.id.btnBackHome);

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(ListBooksActivity.this, HomeActivity.class);
            startActivity(intent);
        });


        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot bookSnap : snapshot.getChildren()) {
                    String title = bookSnap.child("title").getValue(String.class);
                    String author = bookSnap.child("author").getValue(String.class);
                    bookList.add(title + " by " + author);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
