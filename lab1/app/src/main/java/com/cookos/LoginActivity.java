package com.cookos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN = "admin@gmail.com";
    private static final String PASSWORD = "admin";

    private HashMap<String, byte[]> accounts;

    private EditText emailField;
    private EditText passwordField;
    private TextView loginMessage;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginMessage = findViewById(R.id.loginMessage);

        initLoginButton();
        initRegisterButton();

        var extra = getIntent().getSerializableExtra(getString(R.string.accountsMap));

        if (extra == null) {
            accounts = new HashMap<>();
            accounts.put(LOGIN, HashPassword.getHash(PASSWORD));
        } else {
            accounts = (HashMap<String, byte[]>) extra;
        }
    }

    private void initRegisterButton() {
        Button b = findViewById(R.id.registerButton);
        b.setOnClickListener(v -> {
            var intent = new Intent(this, RegisterActivity.class);
            intent.putExtra(getString(R.string.accountsMap), accounts);
            startActivity(intent);
        });
    }

    private void initLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            var correctPassword = accounts.get(emailField.getText().toString());

            if (correctPassword == null) {
                loginMessage.setText(R.string.wrongEmail);
                return;
            }

            var enteredPassword = HashPassword.getHash(passwordField.getText().toString());

            if (!Arrays.equals(correctPassword, enteredPassword)) {
                loginMessage.setText(R.string.wrongPassword);
                return;
            }

            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}