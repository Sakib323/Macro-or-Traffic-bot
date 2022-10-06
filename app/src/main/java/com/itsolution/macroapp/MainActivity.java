package com.itsolution.macroapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.things.device.DeviceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    TextView interval_time_text;
    int interval_time_int,step;
    CardView reboot,macro,interval_time,cycle_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SharedPreferences sharedPreferences1=getSharedPreferences("take_rest_of_data",MODE_PRIVATE);

        boolean new1=sharedPreferences1.getBoolean("new",false);
        step=sharedPreferences1.getInt("step",0);
        String name_for_macro=sharedPreferences1.getString("name","");


        if(new1==true){
            SharedPreferences sharedPreferences2=getSharedPreferences("take_rest_of_data",MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences2.edit();
            editor1.putBoolean("new",false);
            editor1.apply();

            Log.e("name of macro is",name_for_macro);
            Log.e("nmbr of step ",String.valueOf(step));

            Dialog dialog_=new Dialog(MainActivity.this);
            dialog_.setContentView(R.layout.take_word_to_search_and_keyword);
            dialog_.getWindow().setBackgroundDrawableResource(R.drawable.bg_with_radius);
            dialog_.setCancelable(false);
            dialog_.show();
            TextInputEditText search,key1,key2,key3;
            String search_,key1_,key2_,key3_;
            CardView btn_save;

            btn_save=dialog_.findViewById(R.id.save_macro);
            search=dialog_.findViewById(R.id.search);
            key1=dialog_.findViewById(R.id.key1);
            key2=dialog_.findViewById(R.id.key2);
            key3=dialog_.findViewById(R.id.key3);

            search_=search.getText().toString();
            key1_=key1.getText().toString();
            key2_=key2.getText().toString();
            key3_=key3.getText().toString();
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    SharedPreferences key=getSharedPreferences(name_for_macro,MODE_PRIVATE);
                    SharedPreferences.Editor edit=key.edit();

                    if(search_!=null && key1_!=null && key2_!=null && key3_ !=null){
                        step=step+1;
                        edit.putString(String.valueOf(step),"search_"+search.getText().toString());
                        step=step+1;
                        edit.putString(String.valueOf(step),"key1_"+key1.getText().toString());
                        step=step+1;
                        edit.putString(String.valueOf(step),"key2_"+key2.getText().toString());
                        step=step+1;
                        edit.putString(String.valueOf(step),"key3_"+key3.getText().toString());
                        step=step+1;
                        edit.apply();
                        dialog_.dismiss();




                    }else{
                        Toast.makeText(MainActivity.this, "Empty field!", Toast.LENGTH_SHORT).show();
                    }


                }
            });



        }


        SharedPreferences settings=getSharedPreferences("settings",MODE_PRIVATE);
        interval_time_int=settings.getInt("interval_time",1);

        interval_time_text=findViewById(R.id.interval_time_text);
        interval_time_text.setText(String.valueOf(interval_time_int));



        reboot=findViewById(R.id.reboot_schedule);
        reboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reboot_phone();

            }
        });


        interval_time=findViewById(R.id.interval_time);
        interval_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_setting_for_taking_input);
                dialog.getWindow().setBackgroundDrawableResource(R.color.white);
                TextView txt=dialog.findViewById(R.id.text);
                txt.setText("Set a interval time.1 second is set in default");
                dialog.show();
                TextInputEditText nmbr=dialog.findViewById(R.id.set_nmbr);

                CardView btn=dialog.findViewById(R.id.save);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String data=nmbr.getText().toString();
                        if(data!=null){
                            SharedPreferences settings=getSharedPreferences("settings",MODE_PRIVATE);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putInt("interval_time",Integer.valueOf(data));
                            editor.apply();
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "You have saved interval time to "+data, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Set a value!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });


        cycle_time=findViewById(R.id.cycle_time);
        cycle_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_setting_for_taking_input);
                dialog.getWindow().setBackgroundDrawableResource(R.color.white);
                TextView txt=dialog.findViewById(R.id.text);
                txt.setText("How much time you want to run a macro");
                dialog.show();
                TextInputEditText nmbr=dialog.findViewById(R.id.set_nmbr);

                CardView btn=dialog.findViewById(R.id.save);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String data=nmbr.getText().toString();
                        if(data!=null){
                            SharedPreferences settings=getSharedPreferences("settings",MODE_PRIVATE);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putInt("cycle_time",Integer.valueOf(data));
                            editor.apply();
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "You have set the cycle to "+data, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Set a value!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });





        macro=findViewById(R.id.macro);
        macro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean a=false;
                if(a==true){
                    SharedPreferences key=getSharedPreferences("keyword",MODE_PRIVATE);
                    SharedPreferences.Editor edit=key.edit();
                    edit.putString("key1","BAN");
                    edit.putString("key2","GAYI");
                    edit.putString("key3","21");

                    edit.putBoolean("encounter",true);
                    edit.apply();
                    search("com.google.android.youtube","hania amir tiktoker");
                }else{
                    Intent intent=new Intent(MainActivity.this,list_of_macro.class);
                    startActivity(intent);
                }

            }
        });




        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.hide();








        if (ContextCompat.checkSelfPermission(MainActivity.this, Settings.ACTION_ACCESSIBILITY_SETTINGS) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission is already granted", Toast.LENGTH_SHORT).show();
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Settings.ACTION_ACCESSIBILITY_SETTINGS) != PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }


        ExtendedFloatingActionButton start=findViewById(R.id.new_macro);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_setting_for_taking_input);
                dialog.getWindow().setBackgroundDrawableResource(R.color.white);
                TextView txt=dialog.findViewById(R.id.text);
                txt.setText("Name your macro");
                dialog.show();
                TextInputEditText nmbr=dialog.findViewById(R.id.set_nmbr);
                nmbr.setInputType(InputType.TYPE_CLASS_TEXT);

                CardView btn=dialog.findViewById(R.id.save);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String data=nmbr.getText().toString();
                        if(data!=null){
                            SharedPreferences settings=getSharedPreferences("temp_name",MODE_PRIVATE);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString("name",data);
                            editor.apply();
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "You have saved name to "+data, Toast.LENGTH_SHORT).show();

                            checkOverlayPermission();
                            startService();
                            SharedPreferences save_macro=getSharedPreferences("save_macro",MODE_PRIVATE);
                            SharedPreferences.Editor edit1=save_macro.edit();
                            edit1.putBoolean("start",true);
                            edit1.putBoolean("home",true);
                            edit1.apply();

                        }else {
                            Toast.makeText(MainActivity.this, "Set a name!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                Window window=new Window(MainActivity.this);
                window.open();


            }
        });

    }



    private void reboot_phone() {


    }

    public void search(String package_nm,String searchQuery){


        if(package_nm.contains("youtube")){


            Log.e("pkg ",package_nm);
            Intent searchIntent = new Intent(Intent.ACTION_SEARCH);
            searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            searchIntent.setPackage(package_nm);
            searchIntent.putExtra(SearchManager.QUERY, searchQuery);
            startActivity(searchIntent);


        }
        if(package_nm.contains("chrome")){
            Uri uri = Uri.parse("https://www.google.com/search?q="+searchQuery);
            Intent searchIntent = new Intent(Intent.ACTION_VIEW,uri);
            searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            searchIntent.setPackage(package_nm);
            startActivity(searchIntent);
        }

        if(package_nm.contains("com.nhn.android")){

            Uri uri = Uri.parse("https://m.search.naver.com/search.naver?query="+searchQuery);
            Intent searchIntent = new Intent(Intent.ACTION_VIEW,uri);
            searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            searchIntent.setPackage(package_nm);
            startActivity(searchIntent);

        }
        else {


            try{
                Uri uri = Uri.parse("https://www.google.com/search?q="+searchQuery);
                Intent searchIntent = new Intent(Intent.ACTION_VIEW,uri);
                searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                searchIntent.setPackage(package_nm);
                startActivity(searchIntent);
            }
            catch (Exception e){
                Intent searchIntent = new Intent(Intent.ACTION_SEARCH);
                searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                searchIntent.setPackage(package_nm);
                searchIntent.putExtra(SearchManager.QUERY, searchQuery);
                startActivity(searchIntent);

            }


        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public void startService(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(Settings.canDrawOverlays(this)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(this, ForegroundService.class));
                } else {
                    startService(new Intent(this, ForegroundService.class));
                }
            }
        }else{
            startService(new Intent(this, ForegroundService.class));
        }
    }

    public void checkOverlayPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(myIntent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService();
    }






}