package com.eventure.activities;

import android.content.Intent;
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
import com.eventure.services.ServiceFactory;

public class ChangePasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Button submitBtn = findViewById(R.id.submitBtn);



        submitBtn.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "Change Password Activity";

            @Override
            public void onClick(View v) {
                EditText mOldPassword = findViewById(R.id.oldPasswordFormEditText);
                EditText mNewPassword = findViewById(R.id.newPasswordEditText);
                String oldPassword = mOldPassword.getText().toString();
                String newPassword = mNewPassword.getText().toString();
                Log.d(TAG, "onClick: " + oldPassword + " " + newPassword);

                if(!newPassword.equals("")){
                if(ServiceFactory.get().getUserService().changePassword(oldPassword, newPassword)){
                        toastMessage("You have successfully changed a password");
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    }
                else{
                    toastMessage("Wrong password");
                }
                }
                else{
                    toastMessage("You did not fill all fields");
                }


            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void toastMessage(String message){
        Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
