package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.User;

public class NewUser extends AppCompatActivity implements View.OnClickListener {


    EditText edtUserId, edtUsername, edtEmail, edtRole, edtBio, edtProfilePic, edtPassword;;
    Button btnCreateUser;
    User user;



    DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialize();
    }

    private void initialize() {


        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtRole = findViewById(R.id.edtRole);
        edtPassword = findViewById(R.id.edtpassword);
        edtBio = findViewById(R.id.edtBio);
        edtProfilePic = findViewById(R.id.edtProfilePic);
        btnCreateUser = findViewById(R.id.btnCreateUser);

        btnCreateUser.setOnClickListener(this);
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCreateUser) {
            createUserInFirebase();
        }

    }

    private void createUserInFirebase() {


        String userPassword = edtPassword.getText().toString().trim();
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String role = edtRole.getText().toString().trim();
        String bio = edtBio.getText().toString().trim();
        String profilePic = edtProfilePic.getText().toString().trim();

        if (userPassword.isEmpty() || username.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }


        User.Profile profile = new User.Profile(bio, profilePic);
        User user = new User(username, email, role, profile, userPassword);

        userDatabase.push().setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "User successfully saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewUser.this, MainActivity.class);
                    startActivity(intent);
                    clearFields();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to save user: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void clearFields() {
        edtUsername.setText("");
        edtEmail.setText("");
        edtRole.setText("");
        edtPassword.setText("");
        edtBio.setText("");
        edtProfilePic.setText("");
    }
}