package com.biketaxi.app;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Link UI elements with XML
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        // Set click listener on Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields before login
                if (validateInput()) {
                    loginUser();
                }
            }
        });
    }

    // Validate email and password inputs
    private boolean validateInput() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if email is empty or invalid
        if (email.isEmpty()) {
            emailEditText.setError("Please enter your email");
            emailEditText.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            emailEditText.requestFocus();
            return false;
        }

        // Check if password is empty or too short
        if (password.isEmpty()) {
            passwordEditText.setError("Please enter your password");
            passwordEditText.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return false;
        }

        return true; // Input is valid
    }

    // Sign in user with Firebase Authentication
    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Attempt to log in with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login success
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this,
                                "Login successful! Welcome " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();

                        // TODO: Navigate to main dashboard activity
                        // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        // finish();

                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this,
                                "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}

