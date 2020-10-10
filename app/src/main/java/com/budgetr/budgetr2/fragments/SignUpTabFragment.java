package com.budgetr.budgetr2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budgetr.budgetr2.R;
import com.budgetr.budgetr2.util.LoginUtil;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpTabFragment extends Fragment {

    protected TextInputLayout emailEditTextLayout;
    protected TextInputLayout passwordEditTextLayout;
    protected TextInputLayout confirmPasswordEditTextLayout;
    protected Button signUpButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment_layout, container, false);

        final Context context = getContext();

        emailEditTextLayout = root.findViewById(R.id.signup_email_edit_text_layout);
        passwordEditTextLayout = root.findViewById(R.id.signup_password_edit_text_layout);
        confirmPasswordEditTextLayout = root.findViewById(R.id.signup_confirm_password_edit_text_layout);
        signUpButton = root.findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg));
                passwordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg));
                confirmPasswordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg));

                String email = emailEditTextLayout.getEditText().getText().toString();
                String password = passwordEditTextLayout.getEditText().getText().toString();
                String confirmPassword = confirmPasswordEditTextLayout.getEditText().getText().toString();

                Integer validationCode = LoginUtil.validateCredentials(email, password, confirmPassword);
                System.out.println("Validation Code: " + validationCode);
                if(validationCode != null){
                    switch (validationCode){
                        case 0:
                            Toast.makeText(getActivity(), "Please enter your email address", Toast.LENGTH_LONG).show();
                            emailEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            emailEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                        case 1:
                            Toast.makeText(getActivity(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
                            emailEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            emailEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                        case 2:
                            Toast.makeText(getActivity(), "Please enter a password", Toast.LENGTH_LONG).show();
                            passwordEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            passwordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                        case 3:
                            Toast.makeText(getActivity(), "Please confirm your password", Toast.LENGTH_LONG).show();
                            confirmPasswordEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            confirmPasswordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                        case 4:
                            Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_LONG).show();
                            passwordEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            confirmPasswordEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            passwordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            confirmPasswordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                        case 5:
                            Toast.makeText(getActivity(), "Please enter a password longer than 7 characters", Toast.LENGTH_LONG).show();
                            passwordEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            passwordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                        case 6:
                            Toast.makeText(getActivity(), "Please enter a password shorter than 25 characters", Toast.LENGTH_LONG).show();
                            passwordEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            passwordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                        case 7:
                            Toast.makeText(getActivity(), "Password contains an illegal character", Toast.LENGTH_LONG).show();
                            passwordEditTextLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                            passwordEditTextLayout.getEditText().setBackground(ContextCompat.getDrawable(context, R.drawable.login_edittext_bg_error));
                            break;
                    }
                } else {
                    // try to create an account
                }
            }
        });

        return root;
    }
}