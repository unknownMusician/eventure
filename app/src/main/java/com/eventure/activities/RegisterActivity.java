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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Button registerBtn = findViewById(R.id.registerBtn);
        EditText mLogin = findViewById(R.id.loginRegisterEditText);
        EditText mPassword = findViewById(R.id.passwordRegisterEditText);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ControllerFactory.get().getRegisterController().registerUser(mLogin,mPassword)){
                    toastMessage("You have successfully created an account");
                    Log.d(TAG, "onClick: ");
                }
                else{
                    toastMessage("Sorry the is trouble!");
                }
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void toastMessage(String message){
        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
