package com.eventure.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;

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
        choices.add("About");
        ListAdapter adapter = new ArrayAdapter(this,R.layout.menu_items_layout,choices);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(getApplicationContext(), EventListActivityDemo.class));
                }else if(position == 1){
                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                }else if(position == 2){
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                }
                else if(position == 3){
                    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                }
                else if(position==4){
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                }
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
}
