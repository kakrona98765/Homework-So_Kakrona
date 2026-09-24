package com.example.login_form;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login_form.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets insetsToApply = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(insetsToApply.left, insetsToApply.top, insetsToApply.right, insetsToApply.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        binding.registerLayout.setOnClickListener(v -> hideKeyboard());
        setupForgotPassword();
        setupLoginFormPrompt();
        //check even
        binding.btnCreateAccount.setOnClickListener(v -> {
            hideKeyboard();

            if (validateInputs()) {
                performRegister();
            }
        });

        binding.btnGoogle.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(this, "Google Sign-Up Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.btnFacebook.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(this, "Facebook Sign-Up Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.btnGithub.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(this, "GitHub Sign-Up Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    // check Validation
    private boolean validateInputs() {
        String email = binding.inputNewEmail.getText() != null ? binding.inputNewEmail.getText().toString().trim() : "";
        String username = binding.inputNewUsername.getText() != null ? binding.inputNewUsername.getText().toString().trim() : "";
        String password = binding.inputNewPassword.getText() != null ? binding.inputNewPassword.getText().toString() : "";
        String confirmPassword = binding.inputNewConfirmPassword.getText() != null ? binding.inputNewConfirmPassword.getText().toString() : "";

        //
        binding.inputLayoutEmail.setError(null);
        binding.inputLayoutUsername.setError(null);
        binding.inputLayoutPassword.setError(null);
        binding.inputLayoutConfirmPassword.setError(null);

        // 1. Email Validation
        if (email.isEmpty()) {
            binding.inputLayoutEmail.setError("Email is required");
            binding.inputNewEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.inputLayoutEmail.setError("Please enter a valid email");
            binding.inputNewEmail.requestFocus();
            return false;
        }

        // 2. Username Validation
        if (username.isEmpty()) {
            binding.inputLayoutUsername.setError("Username is required");
            binding.inputNewUsername.requestFocus();
            return false;
        } else if (username.length() < 3) {
            binding.inputLayoutUsername.setError("Username must be at least 3 characters");
            binding.inputNewUsername.requestFocus();
            return false;
        }

        // 3. Password Validation
        if (password.isEmpty()) {
            binding.inputLayoutPassword.setError("Password is required");
            binding.inputNewPassword.requestFocus();
            return false;
        } else if (password.length() < 3) {
            binding.inputLayoutPassword.setError("Password must be at least 3 characters");
            binding.inputNewPassword.requestFocus();
            return false;
        }

        // 4. Confirm Password Validation
        if (confirmPassword.isEmpty()) {
            binding.inputLayoutConfirmPassword.setError("Please confirm your password");
            binding.inputNewConfirmPassword.requestFocus();
            return false;
        } else if (!password.equals(confirmPassword)) {
            binding.inputLayoutConfirmPassword.setError("Passwords do not match");
            binding.inputNewConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void performRegister() {
        Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Prepare Text "Forgot Password"
    private void setupForgotPassword() {
        String text = getString(R.string.text_forgot_password);
        SpannableString spannable = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                hideKeyboard();
                Toast.makeText(RegisterActivity.this, "Navigate to Forgot Password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.primary));
            }
        };

        spannable.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvForgotPassword.setText(spannable);
        binding.tvForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
        binding.tvForgotPassword.setHighlightColor(Color.TRANSPARENT);
    }

    // Prepare Text " Log in"
    private void setupLoginFormPrompt() {
        String fullText = "Already have an account? Log in";
        SpannableString spannable = new SpannableString(fullText);

        String clickableWord = "Log in";
        int startIndex = fullText.indexOf(clickableWord);
        int endIndex = startIndex + clickableWord.length();

        // Color Text
        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // ClickableSpan for "Log in"
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                hideKeyboard();
                finish();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.primary));
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.textLoginForm.setText(spannable);
        binding.textLoginForm.setMovementMethod(LinkMovementMethod.getInstance());
        binding.textLoginForm.setHighlightColor(Color.TRANSPARENT);
    }
    // Inside your Activity
    private void hideKeyboard() {
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();

        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}