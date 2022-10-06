package com.itsolution.macroapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import static android.content.Context.WINDOW_SERVICE;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Window {

    // declaring required variables
    public Context context;
    public View mView;
    public WindowManager.LayoutParams mParams;
    public WindowManager mWindowManager;
    public LayoutInflater layoutInflater;

    public Window(Context context){
        this.context=context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // set the layout parameters of the window
            mParams = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    // Display it on top of other application windows
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    // Don't let it grab the input focus
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    // Make the underlying application window visible
                    // through any transparent parts
                    PixelFormat.TRANSLUCENT);

        }
        // getting a LayoutInflater
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflating the view with the custom layout we created
        mView = layoutInflater.inflate(R.layout.popup_window, null);
        // set onClickListener on the remove button, which removes
        // the view from the window
        mView.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //close();
                SharedPreferences sharedPreferences= context.getSharedPreferences("swipe",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("dir",4);
                editor.putBoolean("action",true);
                editor.apply();
            }
        });


        mView.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= context.getSharedPreferences("swipe",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("dir",3);
                editor.putBoolean("action",true);
                editor.apply();
            }
        });


        mView.findViewById(R.id.top).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= context.getSharedPreferences("swipe",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("dir",1);
                editor.putBoolean("action",true);
                editor.apply();
            }
        });

        mView.findViewById(R.id.bottom).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //close();
                SharedPreferences sharedPreferences= context.getSharedPreferences("swipe",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("dir",2);
                editor.putBoolean("action",true);
                editor.apply();
            }
        });

        mView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                close();
            }
        });

        // Define the position of the
        // window within the screen
        mParams.gravity = Gravity.TOP;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

    }

    public void open() {

        try {
            // check if the view is already
            // inflated or present in the window
            if(mView.getWindowToken()==null) {
                if(mView.getParent()==null) {
                    mWindowManager.addView(mView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }

    }


    public void abd_shell(){
        try {
            Process process=Runtime.getRuntime().exec("/system/bin/ input tap 245 1288");
            BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            line=reader.readLine();
            while (line!=null){
                Log.e("output",line);
            }
            int exitval=process.waitFor();
            if(exitval==0){
                Log.e("output","success");
            }
        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
            Log.e("error",e.toString());
        }
    }

    public void close() {

        try {
            // remove the view from the window
            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            // invalidate the view
            mView.invalidate();
            // remove all views
            ((ViewGroup)mView.getParent()).removeAllViews();

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (Exception e) {
            Log.d("Error2",e.toString());
        }
    }
}