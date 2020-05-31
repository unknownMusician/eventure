package com.eventure.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.dao.DaoFactory;
import com.eventure.inmemory.InMemoryDaoFactory;
import com.eventure.inmemory.InMemoryDatabase;
import com.eventure.inmemory.InMemoryTestData;
import com.eventure.model.User;
import com.eventure.services.UserService;
import com.eventure.services.UserServiceImp;

import java.util.function.UnaryOperator;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login Activity" ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InMemoryDatabase database = initDatabase();
        DaoFactory daoFactory = new InMemoryDaoFactory(database);
        UserService userService = new UserServiceImp(daoFactory, UnaryOperator.identity());

        Button signInBtn = findViewById(R.id.btnSignIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mLogin = findViewById(R.id.loginText);
                EditText mPassword = findViewById(R.id.passwordText);
                String login = mLogin.getText().toString();
                String password = mPassword.getText().toString();
                User currentUser = userService.getByLogin(login);
                Log.d(TAG, "onClick: " + login);
                if (!(login.equals("") && password.equals(""))) {
                    if (currentUser != null) {
                        if (userService.checkPassword(currentUser, password) == true) {
                            toastMessage("You have sign in");
                        } else {
                            toastMessage("Wrong Password");
                        }
                    } else {
                        toastMessage("There is no user with login " + login.toString());
                    }


                }
                else{
                    toastMessage("You didn`t apply fields");
                }
            }
        });


    }

    private InMemoryDatabase initDatabase(){
        InMemoryDatabase database = new InMemoryDatabase();
        InMemoryTestData.generateTo(database);
        return database;

    }
    private void toastMessage(String message){
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();

    }
}
