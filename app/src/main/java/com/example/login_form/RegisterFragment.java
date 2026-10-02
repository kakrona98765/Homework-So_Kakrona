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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.login_form.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerLayout.setOnClickListener(v -> hideKeyboard());
        setupForgotPassword();
        setupLoginFormPrompt();

        binding.btnCreateAccount.setOnClickListener(v -> {
            hideKeyboard();
            if (validateInputs()) {
                performRegister(v);
            }
        });

        binding.btnGoogle.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(requireContext(), "Google Sign-Up Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.btnFacebook.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(requireContext(), "Facebook Sign-Up Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.btnGithub.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(requireContext(), "GitHub Sign-Up Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateInputs() {
        String email = binding.inputNewEmail.getText() != null ? binding.inputNewEmail.getText().toString().trim() : "";
        String username = binding.inputNewUsername.getText() != null ? binding.inputNewUsername.getText().toString().trim() : "";
        String password = binding.inputNewPassword.getText() != null ? binding.inputNewPassword.getText().toString() : "";
        String confirmPassword = binding.inputNewConfirmPassword.getText() != null ? binding.inputNewConfirmPassword.getText().toString() : "";

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

    private void performRegister(View view) {
        Toast.makeText(requireContext(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(view).popBackStack();
    }

    private void setupForgotPassword() {
        String text = getString(R.string.text_forgot_password);
        SpannableString spannable = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                hideKeyboard();
                Toast.makeText(requireContext(), "Navigate to Forgot Password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
            }
        };

        spannable.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvForgotPassword.setText(spannable);
        binding.tvForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
        binding.tvForgotPassword.setHighlightColor(Color.TRANSPARENT);
    }

    private void setupLoginFormPrompt() {
        String fullText = "Already have an account? Log in";
        SpannableString spannable = new SpannableString(fullText);

        String clickableWord = "Log in";
        int startIndex = fullText.indexOf(clickableWord);
        int endIndex = startIndex + clickableWord.length();

        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                hideKeyboard();
                Navigation.findNavController(widget).popBackStack();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.textLoginForm.setText(spannable);
        binding.textLoginForm.setMovementMethod(LinkMovementMethod.getInstance());
        binding.textLoginForm.setHighlightColor(Color.TRANSPARENT);
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view == null) {
            view = new View(requireContext());
        }

        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}