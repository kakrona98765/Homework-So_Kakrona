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

import com.example.login_form.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rootLayout.setOnClickListener(v -> hideKeyboard());
        setupForgotPassword();
        setupSignUpPrompt();

        binding.btnLogin.setOnClickListener(v -> {
            hideKeyboard();
            if (validateInputs()) {
                performLogin();
            }
        });

        binding.btnGoogle.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(requireContext(), "Google Login Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.btnFacebook.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(requireContext(), "Facebook Login Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.btnGithub.setOnClickListener(v -> {
            hideKeyboard();
            Toast.makeText(requireContext(), "GitHub Login Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateInputs() {
        String email = binding.inputEmail.getText() != null ? binding.inputEmail.getText().toString().trim() : "";
        String password = binding.inputPassword.getText() != null ? binding.inputPassword.getText().toString() : "";

        // Reset Error
        binding.tinputEmail.setError(null);
        binding.tinputPassword.setError(null);

        if (email.isEmpty()) {
            binding.tinputEmail.setError("Please enter your email");
            binding.inputEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tinputEmail.setError("Please enter a valid email address");
            binding.inputEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            binding.tinputPassword.setError("Please enter your password");
            binding.inputPassword.requestFocus();
            return false;
        } else if (password.length() < 3) {
            binding.tinputPassword.setError("Password must be at least 3 characters");
            binding.inputPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void performLogin() {
        Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
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

    private void setupSignUpPrompt() {
        String fullText = "Don't have account? Sign Up";
        SpannableString spannable = new SpannableString(fullText);

        String clickableWord = "Sign Up";
        int startIndex = fullText.indexOf(clickableWord);
        int endIndex = startIndex + clickableWord.length();

        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                hideKeyboard();
                Navigation.findNavController(widget).navigate(R.id.action_loginFragment_to_registerFragment);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvSignUpPrompt.setText(spannable);
        binding.tvSignUpPrompt.setMovementMethod(LinkMovementMethod.getInstance());
        binding.tvSignUpPrompt.setHighlightColor(Color.TRANSPARENT);
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