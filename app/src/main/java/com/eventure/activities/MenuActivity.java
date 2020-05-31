package com.eventure.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.controller.FrontController;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ListView list = findViewById(R.id.listMenu);
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Event list");
        choices.add("Calendar");
        choices.add("Map");
        choices.add("Settings");
        ListAdapter adapter = new ArrayAdapter(this,R.layout.menu_items_layout,choices);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    FrontController.getInstance().goToActivity(MenuActivity.this,EventListActivity.class);
                }
            }
        });


    }
}
