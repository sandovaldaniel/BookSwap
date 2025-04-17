package com.example.finalprojectbook;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import model.Operation;

public class NewOperationActivity extends AppCompatActivity {

    EditText editBookId, editFromUserId, editToUserId, editDate;
    Spinner spinnerType, spinnerStatus;
    Button btnSaveOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);

        editBookId = findViewById(R.id.editBookId);
        editFromUserId = findViewById(R.id.editFromUserId);
        editToUserId = findViewById(R.id.editToUserId);
        editDate = findViewById(R.id.editDate);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnSaveOperation = findViewById(R.id.btnSaveOperation);

        // Llenar los Spinners con valores fijos
        String[] types = {"borrow", "sell", "return", "exchange"};
        String[] statuses = {"pending", "approved", "completed"};

        spinnerType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types));
        spinnerStatus.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statuses));

        btnSaveOperation.setOnClickListener(v -> {
            String bookId = editBookId.getText().toString();
            String fromUserId = editFromUserId.getText().toString();
            String toUserId = editToUserId.getText().toString();
            String date = editDate.getText().toString();
            String type = spinnerType.getSelectedItem().toString();
            String status = spinnerStatus.getSelectedItem().toString();

            if (bookId.isEmpty() || fromUserId.isEmpty() || toUserId.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                String operationId = "OP-" + System.currentTimeMillis();
                Operation op = new Operation(operationId, type, bookId, fromUserId, toUserId, status, date);
                Toast.makeText(this, "Operation created: " + op.operationId, Toast.LENGTH_LONG).show();
                finish(); // volver a HomeActivity
            }
        });
    }
}
