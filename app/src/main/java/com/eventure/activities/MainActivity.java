package com.eventure.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.controller.FrontController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = findViewById(R.id.btnLogIn);
        Button menuBtn = findViewById(R.id.btnMenu);
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


    }
}
