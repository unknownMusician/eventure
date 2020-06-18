package com.eventure.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.eventure.R;
import com.eventure.model.Test;
import com.eventure.model.User;
import com.eventure.services.ServiceFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity" ;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Button registerBtn = findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mLogin = findViewById(R.id.loginRegisterEditText);
                EditText mPassword = findViewById(R.id.passwordRegisterEditText);
                String login = mLogin.getText().toString();
                String password = mPassword.getText().toString();


                if (!login.equals("") && !password.equals("")) {
                    mAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                    toastMessage("An email already exists");
                                    Log.d(TAG, "onComplete: exist_email");
                                } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                    toastMessage("Weak password");
                                    Log.d(TAG, "onComplete: weak_password");
                                } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                    toastMessage("Wrong email format");
                                    Log.d(TAG, "onComplete: malformed_email");
                                } catch (Exception e) {
                                    toastMessage("Sorry there is a trouble");
                                    Log.d(TAG, "onComplete: " + e.getMessage());
                                }
                            } else {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                User user = new User(login,password);
                                myRef.child("users").child(currentUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            toastMessage("Nice");
                                        }
                                        else{
                                            toastMessage("fuck!");
                                        }
                                    }
                                });
                                toastMessage("You have successfully created an account");
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    toastMessage("Please fill all fields");
                }
            }
        });
    }






    @RequiresApi(api = Build.VERSION_CODES.N)
    private void toastMessage(String message){
        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
