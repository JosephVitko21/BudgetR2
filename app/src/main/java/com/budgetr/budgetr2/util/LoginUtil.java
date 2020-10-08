package com.budgetr.budgetr2.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUtil {

    public static FirebaseUser globalUser;

    public static void createAccount(String email, String password, final FirebaseAuth mAuth, final Activity activity, final Context context){
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

                        }
                    }
                });

    }

    public static FirebaseUser getGlobalUser(){
        return globalUser;
    }
    public static void setGlobalUser(FirebaseUser user){
        globalUser = user;
    }
}
