package com.cookos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmailField;
    private EditText registerPasswordField;
    private EditText repeatPasswordField;
    private TextView registerMessage;

    private HashMap<String, byte[]> accounts;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmailField = findViewById(R.id.registerEmailField);
        registerPasswordField = findViewById(R.id.registerPasswordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        registerMessage = findViewById(R.id.registerMessage);

        accounts = (HashMap<String, byte[]>) getIntent().getSerializableExtra(getString(R.string.accountsMap));

        initRegisterButton();
    }

    private void initRegisterButton() {
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            var email = registerEmailField.getText().toString();

            if (email.isBlank()) {
                registerError(R.string.registerEmailBlank);
                return;
            }

            if (accounts.containsKey(email)) {
                registerError(R.string.usedEmail);
                return;
            }

            var password = registerPasswordField.getText().toString();
            var repeatPassword = repeatPasswordField.getText().toString();

            if (password.isEmpty() || repeatPassword.isEmpty()) {
                registerError(R.string.passwordBlank);
                return;
            }

            if (!password.equals(repeatPassword)) {
                registerError(R.string.differentPasswords);
                return;
            }

            accounts.put(email, HashPassword.getHash(password));
            registerSuccess();

            var intent = new Intent(this, LoginActivity.class);
            intent.putExtra(getString(R.string.accountsMap), accounts);
            startActivity(intent);
            finish();
        });
    }

    private void registerError(int messageId) {
        registerMessage.setTextColor(getResources().getColor(R.color.red));
        registerMessage.setText(messageId);
    }

    private void registerSuccess() {
        registerMessage.setTextColor(getResources().getColor(R.color.green));
        registerMessage.setText(R.string.registered);
    }
}