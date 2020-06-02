package com.eventure.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.controller.FrontController;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings);
        ListView listView = findViewById(R.id.settingList);
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Change password");
        choices.add("Log out");
        ListAdapter adapter = new ArrayAdapter(this,R.layout.event_list_items_layout,choices);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    ControllerFactory.get().getFrontController().goToActivity(SettingActivity.this,ChangePasswordActivity.class);
                }
                if(position == 1){
                    ControllerFactory.get().getFrontController().goToActivity(SettingActivity.this,MainActivity.class);
                    toastMessage("You have been logged out");
                }
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void toastMessage(String message){
        Toast.makeText(SettingActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}

