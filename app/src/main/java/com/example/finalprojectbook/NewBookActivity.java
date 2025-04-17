package com.example.finalprojectbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

        btnSaveBook.setOnClickListener(view -> {
            String title = editTitle.getText().toString();
            String author = editAuthor.getText().toString();
            boolean isForSale = chkForSale.isChecked();
            boolean isForExchange = chkForExchange.isChecked();

            if (title.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Please enter title and author", Toast.LENGTH_SHORT).show();
            } else {
                Book book = new Book(title, author, isForSale, isForExchange);
                Toast.makeText(this, "Book saved: " + book.getTitle(), Toast.LENGTH_LONG).show();
                finish(); // vuelve a HomeActivity
            }
        });
    }
}
