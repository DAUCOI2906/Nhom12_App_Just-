package com.example.app_coffee_order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//KHÔNG ĐƯỢC XOÁ
public class RegisterActivity extends AppCompatActivity {
    private EditText fullnameEditText, emailEditText, phoneEditText, passwordEditText, rePasswordEditText;
    private Button registerButton;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    TextView login, haveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        fullnameEditText = findViewById(R.id.inputFullName);
        emailEditText = findViewById(R.id.inputUsername);
        phoneEditText = findViewById(R.id.inputPhone);
        passwordEditText = findViewById(R.id.inputPassword);
        rePasswordEditText = findViewById(R.id.inputPassword2);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        login = findViewById(R.id.loginText);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String fullname = fullnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String rePassword = rePasswordEditText.getText().toString().trim();

        if (!password.equals(rePassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu thông tin người dùng vào Firebase Realtime Database
        UserInfo userInfo = new UserInfo(fullname, phone, email, password, "");

        databaseReference.child(fullname).setValue(userInfo);


        // Chuyển đến màn hình chính hoặc màn hình đăng nhập
        startActivity(new Intent(RegisterActivity.this, forgot_pass.class));
        finish();
    }
}