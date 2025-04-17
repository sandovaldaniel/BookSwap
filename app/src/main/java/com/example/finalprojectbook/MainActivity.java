package com.example.finalprojectbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    Button btnLogin, btnNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialize();
    }

    private void initialize() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnNewUser = findViewById(R.id.btnNewUser);

        btnNewUser.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewUser.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPass.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("user_email", email); // pasar el email al Home si lo necesitas
                startActivity(intent);
                finish(); // opcional: evita volver al login con botón "back"
            } else {
                if (email.isEmpty()) {
                    edtEmail.setError("Email is required");
                }
                if (password.isEmpty()) {
                    edtPass.setError("Password is required");
                }
            }
        });
    }
}
