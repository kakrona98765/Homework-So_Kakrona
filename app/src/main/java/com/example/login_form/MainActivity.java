package com.example.login_form;


import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login_form.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.btnLogin.setOnClickListener(v -> {
            var email = Objects.requireNonNull(binding.inputEmail.getText()).toString();
            var password = Objects.requireNonNull(binding.inputPassword.getText()).toString();
            boolean isEmailValid = !email.isEmpty();
            boolean isPasswordValid = !password.isEmpty();

            if (!isEmailValid) {
                binding.tinputEmail.setError("Please enter your email");
            } else {
                binding.tinputEmail.setError(null);
            }

            if (!isPasswordValid) {
                binding.tinputPassword.setError("Please enter your password");
            } else {
                binding.tinputPassword.setError(null);
            }

            if (isEmailValid && isPasswordValid) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Email or Password failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

}