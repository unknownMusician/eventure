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
import com.eventure.controller.ControllerFactory;
import com.eventure.controller.FrontController;
import com.eventure.controller.LoginController;
import com.eventure.controller.MenuController;
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



        Button signInBtn = findViewById(R.id.btnSignIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mLogin = findViewById(R.id.loginText);
                EditText mPassword = findViewById(R.id.passwordText);



                Log.d(TAG, "onClick: Hello!");

                if(ControllerFactory.get().getLoginController().checkUserLoginAttributes(mLogin,mPassword)){
                    toastMessage("You sign it!");
                }
                else{
                    toastMessage("Wrong password or login");
                }

            }
        });

    }
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void toastMessage(String message){
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
