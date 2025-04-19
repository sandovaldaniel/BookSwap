package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import model.Book;

public class NewBookActivity extends AppCompatActivity {

    EditText editTitle, editAuthor;
    CheckBox chkForSale, chkForExchange;
    Button btnSaveBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        editTitle = findViewById(R.id.editTitle);
        editAuthor = findViewById(R.id.editAuthor);
        chkForSale = findViewById(R.id.chkForSale);
        chkForExchange = findViewById(R.id.chkForExchange);
        btnSaveBook = findViewById(R.id.btnSaveBook);
        Button btnBackHome = findViewById(R.id.btnBackHome);

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(NewBookActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        btnSaveBook.setOnClickListener(view -> {
            String title = editTitle.getText().toString();
            String author = editAuthor.getText().toString();
            boolean isForSale = chkForSale.isChecked();
            boolean isForExchange = chkForExchange.isChecked();

            if (title.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Please enter title and author", Toast.LENGTH_SHORT).show();
            } else {
                String bookId = "book_" + System.currentTimeMillis();

                Book book = new Book();
                book.bookId = bookId;
                book.title = title;
                book.author = author;
                book.status = isForSale ? "for sale" : isForExchange ? "for exchange" : "unlisted";

                // Guardar en Firebase
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("books");
                dbRef.child(bookId).setValue(book).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Book saved to Firebase", Toast.LENGTH_SHORT).show();
                    finish(); // Volver a HomeActivity
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Error saving to Firebase", Toast.LENGTH_SHORT).show();
                });
            }
        });

    }
}
