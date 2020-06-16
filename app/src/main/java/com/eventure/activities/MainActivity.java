package com.eventure.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eventure.R;
import com.eventure.services.ServiceFactory;

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

                String login = mLogin.getText().toString();
                String password = mPassword.getText().toString();

                if(ServiceFactory.get().getUserService().checkPassword(login,password)){
                    toastMessage("You sign it!");
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }
                else{
                    toastMessage("Wrong password or login");
                }

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
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
