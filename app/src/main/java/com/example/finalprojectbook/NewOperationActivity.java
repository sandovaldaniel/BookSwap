package com.example.finalprojectbook;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.*;

import model.Operation;

public class NewOperationActivity extends AppCompatActivity {

    EditText editBookId, editFromUserId, editToUserId, editDate;
    Spinner spinnerBookTitle, spinnerType;
    Button btnSaveOperation;

    Map<String, String> titleToBookIdMap = new HashMap<>();
    Map<String, String> titleToOwnerEmailMap = new HashMap<>();

    String fromUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);

        spinnerBookTitle = findViewById(R.id.spinnerBookTitle);
        editBookId = findViewById(R.id.editBookId);
        editFromUserId = findViewById(R.id.editFromUserId);
        editToUserId = findViewById(R.id.editToUserId);
        editDate = findViewById(R.id.editDate);
        spinnerType = findViewById(R.id.spinnerType);
        btnSaveOperation = findViewById(R.id.btnSaveOperation);

        fromUserEmail = getIntent().getStringExtra("user_email");
        editFromUserId.setText(fromUserEmail);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        editDate.setText(currentDate);

        loadBookTitles();

        btnSaveOperation.setOnClickListener(v -> saveOperation());
    }

    private void loadBookTitles() {
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");
        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> titles = new ArrayList<>();
                for (DataSnapshot bookSnap : snapshot.getChildren()) {
                    String title = bookSnap.child("title").getValue(String.class);
                    String bookId = bookSnap.getKey();
                    String ownerEmail = bookSnap.child("ownerEmail").getValue(String.class);

                    if (title != null && bookId != null && ownerEmail != null) {
                        titles.add(title);
                        titleToBookIdMap.put(title, bookId);
                        titleToOwnerEmailMap.put(title, ownerEmail);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewOperationActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, titles);
                spinnerBookTitle.setAdapter(adapter);

                spinnerBookTitle.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                        String selectedTitle = parent.getItemAtPosition(position).toString();
                        String selectedBookId = titleToBookIdMap.get(selectedTitle);
                        String selectedOwnerEmail = titleToOwnerEmailMap.get(selectedTitle);

                        editBookId.setText(selectedBookId);
                        editToUserId.setText(selectedOwnerEmail);

                        DatabaseReference selectedBookRef = FirebaseDatabase.getInstance().getReference("books").child(selectedBookId);
                        selectedBookRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                List<String> availableTypes = new ArrayList<>();

                                Boolean canBorrow = snapshot.child("isAvailableToBorrow").getValue(Boolean.class);
                                Boolean canExchange = snapshot.child("isAvailableToExchange").getValue(Boolean.class);
                                Boolean canSell = snapshot.child("isAvailableToSell").getValue(Boolean.class);

                                if (Boolean.TRUE.equals(canBorrow)) availableTypes.add("borrow");
                                if (Boolean.TRUE.equals(canExchange)) availableTypes.add("exchange");
                                if (Boolean.TRUE.equals(canSell)) availableTypes.add("sell");

                                if (availableTypes.isEmpty()) {
                                    availableTypes.add("not available");
                                    btnSaveOperation.setEnabled(false);
                                } else {
                                    btnSaveOperation.setEnabled(true);
                                }

                                ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(NewOperationActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item, availableTypes);
                                spinnerType.setAdapter(typeAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(NewOperationActivity.this, "Failed to load book status", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(android.widget.AdapterView<?> parent) {}
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(NewOperationActivity.this, "Error loading books", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOperation() {
        String bookId = editBookId.getText().toString();
        String fromUserEmail = editFromUserId.getText().toString();
        String toUserEmail = editToUserId.getText().toString();
        String date = editDate.getText().toString();
        String type = spinnerType.getSelectedItem().toString();

        if (bookId.isEmpty() || fromUserEmail.isEmpty() || toUserEmail.isEmpty() || date.isEmpty() || type.equals("not available")) {
            Toast.makeText(this, "Please fill all fields and choose a valid operation", Toast.LENGTH_SHORT).show();
            return;
        }

        String operationId = "OP-" + System.currentTimeMillis();
        Operation op = new Operation(operationId, type, bookId, fromUserEmail, toUserEmail, date);

        FirebaseDatabase.getInstance().getReference("operations")
                .child(operationId)
                .setValue(op)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Operation created!", Toast.LENGTH_SHORT).show();

                    //update book available
                    DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("books").child(bookId);
                    Map<String, Object> updateMap = new HashMap<>();

                    switch (type) {
                        case "borrow":
                            updateMap.put("isAvailableToBorrow", false);
                            break;
                        case "exchange":
                            updateMap.put("isAvailableToExchange", false);
                            break;
                        case "sell":
                            updateMap.put("isAvailableToSell", false);
                            break;
                    }

                    bookRef.updateChildren(updateMap)
                            .addOnSuccessListener(v -> finish())
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Book update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}