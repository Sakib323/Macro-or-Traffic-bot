package com.itsolution.macroapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;

public class list_of_macro extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_macro);
        listView=findViewById(R.id.list_macro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.hide();


        SharedPreferences sharedPreferences=getSharedPreferences("name_of_all_macro",MODE_PRIVATE);
        String[] data;
        data=sharedPreferences.getAll().values().toString().split(",");

        for(int i=0;i<=data.length-1;i++){
            data[i]=data[i].replace("]", "");
            data[i]=data[i].replace("[", "");
            Log.e("step"+String.valueOf(i),data[i]);
        }
        listView.setAdapter(new ArrayAdapter<String>(list_of_macro.this, android.R.layout.simple_list_item_1, data));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedFromList = (String) (listView.getItemAtPosition(i));


                SharedPreferences sharedPreferences2=getSharedPreferences("commands",MODE_PRIVATE);
                SharedPreferences.Editor editor1=sharedPreferences2.edit();
                editor1.putString("command",selectedFromList);
                editor1.apply();


                SharedPreferences sharedPreferences=getSharedPreferences("keyword",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("encounter_to_run",true);
                editor.putBoolean("encounter",true);
                editor.apply();


            }
        });

    }
}