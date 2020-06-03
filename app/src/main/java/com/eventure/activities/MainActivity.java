package com.eventure.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.controller.FrontController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signInBtn = findViewById(R.id.logInBtn);
        Button registerBtn = findViewById(R.id.registrationBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mLogin = findViewById(R.id.loginFormEditText);
                EditText mPassword = findViewById(R.id.passwordFormEditText);
                if(ControllerFactory.get().getLoginController().checkUserLoginAttributes(mLogin,mPassword)){
                    toastMessage("You sign it!");
                    ControllerFactory.get().getFrontController().goToActivity(MainActivity.this,MenuActivity.class);
                }
                else{
                    toastMessage("Wrong password or login");
                }

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerFactory.get().getFrontController().goToActivity(MainActivity.this,RegisterActivity.class);
            }
        });

    }

      /*  Button loginBtn = findViewById(R.id.btnLogIn);
        Button menuBtn = findViewById(R.id.btnMenu);
        Button registrationBtn = findViewById(R.id.registrationBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ControllerFactory.get().getFrontController().goToActivity(MainActivity.this,LoginActivity.class);

            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ControllerFactory.get().getFrontController().goToActivity(MainActivity.this,MenuActivity.class);
            }
        });
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerFactory.get().getFrontController().goToActivity(MainActivity.this,RegisterActivity.class);
            }
        });

    }

       */
     @RequiresApi(api = Build.VERSION_CODES.N)
        private void toastMessage(String message){
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
        }
}
