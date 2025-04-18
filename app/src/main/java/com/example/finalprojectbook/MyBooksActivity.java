package com.example.finalprojectbook;

import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

public class MyBooksActivity extends AppCompatActivity {

    LinearLayout booksContainer;
    String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        booksContainer = findViewById(R.id.booksContainer);
        currentUserEmail = getIntent().getStringExtra("user_email");

        loadMyBooks();

        Button btnReturnHome = findViewById(R.id.btnReturnHome);
        btnReturnHome.setOnClickListener(v -> finish());
        
    }

    private void loadMyBooks() {
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");
        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                booksContainer.removeAllViews();

                for (DataSnapshot bookSnap : snapshot.getChildren()) {
                    String bookId = bookSnap.getKey();
                    String title = bookSnap.child("title").getValue(String.class);
                    String author = bookSnap.child("author").getValue(String.class);
                    String ownerEmail = bookSnap.child("ownerEmail").getValue(String.class);

                    if (currentUserEmail.equals(ownerEmail)) {
                        Boolean canBorrow = bookSnap.child("isAvailableToBorrow").getValue(Boolean.class);
                        Boolean canExchange = bookSnap.child("isAvailableToExchange").getValue(Boolean.class);
                        Boolean canSell = bookSnap.child("isAvailableToSell").getValue(Boolean.class);

                        // 输入框 & 复选框
                        EditText inputTitle = new EditText(MyBooksActivity.this);
                        inputTitle.setText(title);
                        inputTitle.setHint("Title");
                        inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);

                        EditText inputAuthor = new EditText(MyBooksActivity.this);
                        inputAuthor.setText(author);
                        inputAuthor.setHint("Author");
                        inputAuthor.setInputType(InputType.TYPE_CLASS_TEXT);

                        CheckBox chkBorrow = new CheckBox(MyBooksActivity.this);
                        chkBorrow.setText("Available to Borrow");
                        chkBorrow.setChecked(Boolean.TRUE.equals(canBorrow));

                        CheckBox chkExchange = new CheckBox(MyBooksActivity.this);
                        chkExchange.setText("Available to Exchange");
                        chkExchange.setChecked(Boolean.TRUE.equals(canExchange));

                        CheckBox chkSell = new CheckBox(MyBooksActivity.this);
                        chkSell.setText("Available to Sell");
                        chkSell.setChecked(Boolean.TRUE.equals(canSell));

                        // 保存按钮
                        Button btnSave = new Button(MyBooksActivity.this);
                        btnSave.setText("Save");
                        btnSave.setOnClickListener(v -> {
                            String newTitle = inputTitle.getText().toString().trim();
                            String newAuthor = inputAuthor.getText().toString().trim();
                            boolean newBorrow = chkBorrow.isChecked();
                            boolean newExchange = chkExchange.isChecked();
                            boolean newSell = chkSell.isChecked();

                            DatabaseReference bookRef = booksRef.child(bookId);
                            bookRef.child("title").setValue(newTitle);
                            bookRef.child("author").setValue(newAuthor);
                            bookRef.child("isAvailableToBorrow").setValue(newBorrow);
                            bookRef.child("isAvailableToExchange").setValue(newExchange);
                            bookRef.child("isAvailableToSell").setValue(newSell);

                            Toast.makeText(MyBooksActivity.this, "Book updated!", Toast.LENGTH_SHORT).show();
                        });

                        // 删除按钮
                        Button btnDelete = new Button(MyBooksActivity.this);
                        btnDelete.setText("Delete");
                        btnDelete.setOnClickListener(v -> {
                            booksRef.child(bookId).removeValue();
                            Toast.makeText(MyBooksActivity.this, "Deleted " + title, Toast.LENGTH_SHORT).show();
                            loadMyBooks();
                        });

                        // 添加到容器
                        LinearLayout bookItem = new LinearLayout(MyBooksActivity.this);
                        bookItem.setOrientation(LinearLayout.VERTICAL);
                        bookItem.setPadding(0, 32, 0, 32);
                        bookItem.addView(inputTitle);
                        bookItem.addView(inputAuthor);
                        bookItem.addView(chkBorrow);
                        bookItem.addView(chkExchange);
                        bookItem.addView(chkSell);
                        bookItem.addView(btnSave);
                        bookItem.addView(btnDelete);

                        booksContainer.addView(bookItem);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyBooksActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
