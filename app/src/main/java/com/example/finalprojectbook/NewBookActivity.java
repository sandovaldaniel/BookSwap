package com.example.finalprojectbook;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Book;

public class NewBookActivity extends AppCompatActivity {

    EditText editTitle, editAuthor;
    Spinner spinnerGenre;
    CheckBox chkForBorrow, chkForExchange, chkForSale;
    Button btnSaveBook;

    DatabaseReference booksRef;

    String ownerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        // initialize UI
        editTitle = findViewById(R.id.editTitle);
        editAuthor = findViewById(R.id.editAuthor);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        chkForBorrow = findViewById(R.id.chkForBorrow);
        chkForExchange = findViewById(R.id.chkForExchange);
        chkForSale = findViewById(R.id.chkForSale);
        btnSaveBook = findViewById(R.id.btnSaveBook);
        ownerEmail = getIntent().getStringExtra("user_email");

        // genre dropdown list
        String[] genres = {"Textbook", "Magazine", "Novel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genres);
        spinnerGenre.setAdapter(adapter);

        booksRef = FirebaseDatabase.getInstance().getReference("books");

        btnSaveBook.setOnClickListener(view -> saveBook());
    }

    private void saveBook() {
        String title = editTitle.getText().toString().trim();
        String author = editAuthor.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString(); // 👈 取选中项
        boolean isBorrow = chkForBorrow.isChecked();
        boolean isExchange = chkForExchange.isChecked();
        boolean isSale = chkForSale.isChecked();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String bookId = booksRef.push().getKey();

        if (bookId == null) {
            Toast.makeText(this, "Failed to generate book ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book = new Book(bookId, title, author, genre, ownerEmail, isBorrow, isExchange, isSale);

        booksRef.child(bookId).setValue(book)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Book saved!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}