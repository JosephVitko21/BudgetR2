package com.budgetr.budgetr2.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUtil {

    private static FirebaseUser globalUser;

    public static void createAccount(String email, String password, final FirebaseAuth mAuth, final Activity activity, final Context context) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // sign in was successful
                            System.out.println("createUserWithEmail:success");
                            setGlobalUser(mAuth.getCurrentUser());
                        } else {
                            // sign in failed
                            Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
                            setGlobalUser(null);
                        }
                    }
                });

    }
    public static void signIn(String email, String password, final FirebaseAuth mAuth, final Activity activity, final Context context) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in was successful
                            System.out.println("signInWithEmail:success");
                            setGlobalUser(mAuth.getCurrentUser());
                        } else {
                            // sign in failed
                            Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
                            setGlobalUser(null);
                        }
                    }
                });
    }
    public static int checkLoggedIn(final FirebaseAuth mAuth){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            setGlobalUser(currentUser);
            return 1; // user was logged in
        } else {
            return 0; // no current user
        }
    }

    public static FirebaseUser getGlobalUser(){
        return globalUser;
    }
    public static void setGlobalUser(FirebaseUser user){
        globalUser = user;
    }

    private static Integer validateEmail(String email){
        if(TextUtils.isEmpty(email)){
            return 0; // email is blank
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return 1; // not a valid email
        } else {
            return null;
        }
    }

    private static Integer validatePassword(String password){
        if(TextUtils.isEmpty(password)){
            return 0; // password is blank
        }
        // password must be between 8 and 24 chars long (inclusive) and only contain characters a-z, A-Z, 0-9, and !,@,#,$.
        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
        if(!PASSWORD_PATTERN.matcher(password).matches()){
            return 1; // not a valid password
        } else {
            return null;
        }
    }

    private static Integer validatePasswords(String password, String confirmPassword){
        System.out.println("password: " + password);
        if(TextUtils.isEmpty(password)){
            return 0; // password is empty
        }
        if(TextUtils.isEmpty(confirmPassword)){
            return 1; // confirm password is empty
        }
        if(!password.equals(confirmPassword)){
            return 2; // passwords don't match
        }
        if(password.length() < 8){
            return 3; // password is too short
        }
        if(password.length() > 24){
            return 4; // password is too long
        }
        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
        System.out.println("password is correctly formatted: " + PASSWORD_PATTERN.matcher(password).matches());
        if(!PASSWORD_PATTERN.matcher(password).matches()){
            return 5; // not a valid password
        } else {
            return null;
        }
    }

    public static Integer validateCredentials(String email, String password){
        Integer emailValidationCode = validateEmail(email);
        if(emailValidationCode != null){
            return emailValidationCode;
        }
        Integer passwordValidationCode = validatePassword(password);
        if(passwordValidationCode != null){
            return passwordValidationCode + 2;
        }
        return null;
    }

    public static Integer validateCredentials(String email, String password, String confirmPassword){
        Integer emailValidationCode = validateEmail(email);
        if(emailValidationCode != null){
            return emailValidationCode;
        }
        Integer passwordValidationCode = validatePasswords(password, confirmPassword);
        if(passwordValidationCode != null){
            return passwordValidationCode + 2;
        } else {
            return null;
        }
    }
}
