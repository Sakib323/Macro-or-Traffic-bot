package com.itsolution.macroapp;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.compose.ui.semantics.AccessibilityAction;

import com.google.android.material.textfield.TextInputEditText;

import java.nio.charset.Charset;
import java.sql.Time;
import java.util.Random;

public class MyAccessibilityService extends AccessibilityService {
    public String key1,key2,key3,key4,key5,description=null,Text=null,class_name_yt="android.view.ViewGroup";
    public boolean encounter,encounter_to_run,click1,click2,home,start=false,once;
    String already_seen1="",already_seen2="",already_seen3="",package_name,final_matched_one,name_for_macro;
    String search_for,pkg,key1_,key2_,key3_;
    int count,step=1,interval_time,cycle_time;
    Intent intent;

    public String content_with_most_keyword,already_analyzed="",temp,temp1,a;
    int rank=0,rank1=0;





    String TAG="description not matched yet";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {



        SharedPreferences settings=getSharedPreferences("settings",MODE_PRIVATE);
        cycle_time=settings.getInt("cycle_time",0);
        interval_time=settings.getInt("interval_time",0);


        SharedPreferences save_macro=getSharedPreferences("save_macro",MODE_PRIVATE);
        start=save_macro.getBoolean("start",false);
        home=save_macro.getBoolean("home",false);
        if(home==true){
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            home=false;
            SharedPreferences save_macro_to_home=getSharedPreferences("save_macro",MODE_PRIVATE);
            SharedPreferences.Editor editor=save_macro_to_home.edit();
            editor.putBoolean("home",false);
            editor.apply();
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));

            Log.e("saving home step",String.valueOf(step));

            SharedPreferences name=getSharedPreferences("temp_name",MODE_PRIVATE);
            name_for_macro=name.getString("name","");


            SharedPreferences sharedPreferences=getSharedPreferences("name_of_all_macro",MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences.edit();
            editor1.putString(""+generatedString,name_for_macro);
            editor1.apply();


            SharedPreferences key=getSharedPreferences(name_for_macro,MODE_PRIVATE);
            SharedPreferences.Editor edit=key.edit();
            edit.putString(String.valueOf(step),"HOME");
            edit.apply();

            step=step+1;
        }
        if (start==true){
            save_macro(0,accessibilityEvent);
        }



        SharedPreferences sharedPreferences1=getSharedPreferences("keyword",MODE_PRIVATE);
        encounter=sharedPreferences1.getBoolean("encounter",false);
        if(encounter==true){
            encounter=false;
            SharedPreferences sharedPreferences=getSharedPreferences("commands",MODE_PRIVATE);
            String command=sharedPreferences.getString("command","");

            SharedPreferences test=getSharedPreferences(command,MODE_PRIVATE);
            String[] data = new String[test.getAll().size()+1];

            for(int k=1;k<=test.getAll().size();k++){
                Log.e("step>"+String.valueOf(k),test.getString(String.valueOf(k),"") );
                data[k]=test.getString(String.valueOf(k),"");
            }

            for(int j=1;j<=data.length-1;j++){
                if(data[j].contains("HOME")){
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                }
                if(data[j].contains("swipe(1)")){
                    swipe(1);
                    Log.e("calling","swipe(1)");
                }
                if(data[j].contains("swipe(2)")){
                    swipe(2);
                    Log.e("calling","swipe(2)");
                }
                if(data[j].contains("swipe(3)")){
                    swipe(3);
                    Log.e("calling","swipe(3)");
                }
                if(data[j].contains("swipe(4)")){
                    swipe(4);
                    Log.e("calling","swipe(4)");
                }

                if(data[j].contains("open_")){
                    pkg=data[j].replaceAll("open_", "");
                    Log.e("pkg is",pkg);

                }
                if(data[j].contains("search_")){
                     search_for=data[j].replaceAll("search_", "");
                     Log.e("search_for",search_for);
                }
                if(data[j].contains("key1_")){
                     key1_=data[j].replaceAll("key1_", "");
                }
                if(data[j].contains("key2_")){
                     key2_=data[j].replaceAll("key2_", "");

                }
                if(data[j].contains("key3_")){
                     key3_=data[j].replaceAll("key3_", "");
                }


                Log.e("the keyword is",key1_+","+key2_+","+key3_);

                if(search_for!=null&&key1_!=null&&key2_!=null&&key3_!=null){
                    search(pkg.replaceAll(" ",""),search_for);

                    SharedPreferences preferences=getSharedPreferences("keyword",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("key1",key1_);
                    editor.putString("key2",key2_);
                    editor.putString("key3",key3_);
                    editor.putBoolean("encounter",false);
                    editor.apply();
                    Log.e("command here is",command);



                    SharedPreferences sharedPref=getSharedPreferences("keyword",MODE_PRIVATE);
                    encounter_to_run=sharedPref.getBoolean("encounter_to_run",false);

                    key1=key1_;
                    key2=key2_;
                    key3=key3_;


                    Log.e("the keyword is",key1+","+key2+","+key3);

                    search_for=null;key1_=null;key2_=null;key3_=null;

                }

            }


            SharedPreferences preferences=getSharedPreferences("keyword",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("encounter",false);
            editor.apply();
            Log.e("command here is",command);
            encounter=false;

        }



        if(encounter_to_run==true){
            function(getRootInActiveWindow(),0,accessibilityEvent);
        }





        SharedPreferences swipe=getSharedPreferences("swipe",MODE_PRIVATE);
        Boolean action=swipe.getBoolean("action",false);
        if(action==true){

            if(start==true){
                swipe= getSharedPreferences("swipe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=swipe.edit();
                editor.putBoolean("action",false);
                editor.apply();
                int dir=swipe.getInt("dir",10);
                swipe_action_for_save_macro(dir,accessibilityEvent);
                Log.e("action is true"," and calling swipe");

            }
            else{
                swipe= getSharedPreferences("swipe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=swipe.edit();
                editor.putBoolean("action",false);
                editor.apply();
                int dir=swipe.getInt("dir",10);
                swipe(dir);
                Log.e("action is true"," and calling swipe");
            }

        }


    }
    @Override
    public void onInterrupt() {

    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    public int touchTo(int x, int y) {
        GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x, y);
        gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 0, 50));
        boolean click=dispatchGesture(gestureBuilder.build(), null , null);
        if(click==true){
            return 1;
        }else
        {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int swipe(int dir){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int middleYValue = displayMetrics.heightPixels / 2;
        final int leftSideOfScreen = displayMetrics.widthPixels / 4;
        final int rightSizeOfScreen = leftSideOfScreen * 3;

        final int height = displayMetrics.heightPixels;
        final int top = (int) (height * .25);
        final int bottom = (int) (height * .75);
        final int midX = displayMetrics.widthPixels / 2;


        GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
        Path path = new Path();

        if(dir==1)
        {
            //down
            path.moveTo(midX, bottom);
            path.lineTo(midX, top);
            if(encounter==true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(dir==2)
        {
            //up
            path.moveTo(midX, top);
            path.lineTo(midX, bottom);

            if(encounter==true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (dir==3)
        {
            //Swipe left
            path.moveTo(rightSizeOfScreen, middleYValue);
            path.lineTo(leftSideOfScreen, middleYValue);

            if(encounter==true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(dir==4) {
            //Swipe right
            path.moveTo(leftSideOfScreen, middleYValue);
            path.lineTo(rightSizeOfScreen, middleYValue);

            if(encounter==true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(dir==0){

        }
        gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 100, 50));
        dispatchGesture(gestureBuilder.build(),null, null);
        return middleYValue;
    }


    //saving macro
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int swipe_action_for_save_macro(int dir,AccessibilityEvent accessibilityEvent){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int middleYValue = displayMetrics.heightPixels / 2;
        final int leftSideOfScreen = displayMetrics.widthPixels / 4;
        final int rightSizeOfScreen = leftSideOfScreen * 3;

        final int height = displayMetrics.heightPixels;
        final int top = (int) (height * .25);
        final int bottom = (int) (height * .75);
        final int midX = displayMetrics.widthPixels / 2;


        GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
        Path path = new Path();

        if(dir==1)
        {
            //down
            path.moveTo(midX, bottom);
            path.lineTo(midX, top);
            save_macro(1,accessibilityEvent);
            Log.e("swipe is called"," and start is true here calling save_macro");

        }
        if(dir==2)
        {
            //up
            path.moveTo(midX, top);
            path.lineTo(midX, bottom);

            Log.e("swipe is called","and start is false so not calling save_macro");

            save_macro(2,accessibilityEvent);
            Log.e("swipe is called"," and start is true here calling save_macro");

        }
        if (dir==3)
        {
            //Swipe left
            path.moveTo(rightSizeOfScreen, middleYValue);
            path.lineTo(leftSideOfScreen, middleYValue);

            Log.e("swipe is called","and start is false so not calling save_macro");

            save_macro(3,accessibilityEvent);
            Log.e("swipe is called"," and start is true here calling save_macro");
        }
        if(dir==4) {
            //Swipe right
            path.moveTo(leftSideOfScreen, middleYValue);
            path.lineTo(rightSizeOfScreen, middleYValue);
            if(start==true){
                save_macro(4,accessibilityEvent);
                Log.e("swipe is called"," and start is true here calling save_macro");
            }
        }

        gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 100, 50));
        dispatchGesture(gestureBuilder.build(),null, null);
        return middleYValue;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void save_macro(int dir, AccessibilityEvent accessibilityEvent){


        SharedPreferences key=getSharedPreferences(name_for_macro,MODE_PRIVATE);
        SharedPreferences.Editor edit=key.edit();

        if(dir==1){
            edit.putString(String.valueOf(step),"swipe(1)");
            edit.apply();
            Log.e("saving swipe step nmbr",String.valueOf(step));
            step=step+1;
            dir=100;
        }
        if(dir==2){
            edit.putString(String.valueOf(step),"swipe(2)");
            edit.apply();
            Log.e("saving swipe step nmbr",String.valueOf(step));
            step=step+1;
            dir=100;
        }
        if(dir==3){
            edit.putString(String.valueOf(step),"swipe(3)");
            edit.apply();
            Log.e("saving swipe step nmbr",String.valueOf(step));
            step=step+1;
            dir=100;
        }
        if(dir==4){
            edit.putString(String.valueOf(step),"swipe(4)");
            edit.apply();
            Log.e("saving swipe step nmbr",String.valueOf(step));
            step=step+1;
            dir=100;
        }


            if(accessibilityEvent.getEventType()==AccessibilityEvent.TYPE_VIEW_CLICKED){
                package_name=accessibilityEvent.getPackageName().toString();
                Log.e("pkg name but not saving",package_name);


                if(!package_name.contains("systemui") && !package_name.contains("com.google.android.apps")&& !package_name.contains("nexuslauncher") && !package_name.contains("macroapp")){
                    edit.putString(String.valueOf(step),"open_"+package_name);
                    edit.apply();


                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);


                    CardView search,stay;

                    Window dialog_=new Window(MyAccessibilityService.this);
                    dialog_.mView=dialog_.layoutInflater.inflate(R.layout.pop_up_last_action,null);
                    dialog_.open();
                    search=dialog_.mView.findViewById(R.id.stay);
                    stay=dialog_.mView.findViewById(R.id.quit);

                    stay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog_.close();
                            step=1;
                            SharedPreferences save_macro=getSharedPreferences("save_macro",MODE_PRIVATE);
                            SharedPreferences.Editor edit1=save_macro.edit();
                            edit1.putBoolean("start",false);
                            edit1.apply();
                            start=false;
                        }
                    });
                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            SharedPreferences sharedPreferences=getSharedPreferences("take_rest_of_data",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean("new",true);
                            editor.putInt("step",step);
                            editor.putString("name",name_for_macro);
                            editor.apply();





                            Intent searchIntent = new Intent(MyAccessibilityService.this, MainActivity.class);
                            searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(searchIntent);

                            dialog_.close();
                            SharedPreferences save_macro=getSharedPreferences("save_macro",MODE_PRIVATE);
                            SharedPreferences.Editor edit1=save_macro.edit();
                            edit1.putBoolean("start",false);
                            edit1.apply();
                            start=false;
                            step=1;
                        }
                    });


                }

            }

    }



    //matching with key
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void function(AccessibilityNodeInfo nodeInfo, int depth, AccessibilityEvent accessibilityEvent){
        Log.e("key1",key1);


        if(description!=null || Text!=null){
            if(description!=null){

                Log.e("description is",description);
                Log.e(TAG,description);
                if( (description.contains(key1.toLowerCase()) || description.contains(key2.toLowerCase()))){
                    Rect buttonRect = new Rect();
                    nodeInfo.getBoundsInScreen(buttonRect);
                    int bottom=buttonRect.bottom;
                    int top=buttonRect.top;
                    int left=buttonRect.left;
                    int right=buttonRect.right;
                    int x=(left+right)/2;
                    int y=(top+bottom)/2;
                    //click1=nodeInfo.performAction(touchTo(x,y));
                    Log.e("clicked", String.valueOf(click1));
                    Log.e("description is",description);
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            //performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                            encounter_to_run=false;

                            SharedPreferences sharedPref=getSharedPreferences("keyword",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPref.edit();
                            editor.putBoolean("encounter_to_run",false);
                            editor.apply();
                        }
                    }.start();
                }
            }
            if(Text!=null){
                Log.e("text not match",Text);
                if((Text.contains(key1.toLowerCase()) || Text.contains(key2.toLowerCase()))){
                    Rect buttonRect = new Rect();
                    nodeInfo.getBoundsInScreen(buttonRect);
                    int bottom=buttonRect.bottom;
                    int top=buttonRect.top;
                    int left=buttonRect.left;
                    int right=buttonRect.right;
                    int x=(left+right)/2;
                    int y=(top+bottom)/2;

                    click2=nodeInfo.performAction(touchTo(x,y));
                    Log.e("clicked", String.valueOf(click2));
                    Log.e("text is",Text);
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            //performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                            encounter_to_run=false;

                            SharedPreferences sharedPref=getSharedPreferences("keyword",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPref.edit();
                            editor.putBoolean("encounter_to_run",false);
                            editor.apply();
                        }
                    }.start();
                }
            }
        }
        try{
            for (int i=0 ; i < nodeInfo.getChildCount(); ++i) {


                if(nodeInfo.getContentDescription()!=null){
                    description=nodeInfo.getContentDescription().toString().toLowerCase();

                }
                if(nodeInfo.getText()!=null){
                    Text=nodeInfo.getText().toString().toLowerCase();
                }

                if(description!=null){
                    String most_match=analyze(description,key1,key2,key3);
                    //nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                    Log.e("most match",""+most_match);
                }

                //nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                function(nodeInfo.getChild(i), depth + 1, accessibilityEvent);

            }
        }
        catch (Exception e){
            Log.e("error in try",e.toString());
        }
    }

    public String analyze(String content_we_got_from_window,String key1,String key2,String key3){
        if(rank==0) {
            if (content_we_got_from_window != null) {
                String[] word = content_we_got_from_window.split("\\s");
                for (String w : word) {
                    if (w.equals(key1)) {
                        rank = rank + 1;
                    }
                    if (w.equals(key2)) {
                        rank = rank + 1;
                    }
                    if (w.equals(key3)) {
                        rank = rank + 1;
                    }
                    Log.e("rank= ",String.valueOf(rank));
                }
                temp=content_we_got_from_window;
            }
        }
        else {

            if (rank1 != 0 && rank != 0) {
                if (rank > rank1) {
                    content_with_most_keyword = temp;
                } else {
                    content_with_most_keyword = temp1;
                }
                rank = 0;


            }
            else {
            if (content_we_got_from_window != null) {
                String[] word = content_we_got_from_window.split("\\s");
                for (String w : word) {
                    if (w.equals(key1)) {
                        rank1 = rank1 + 1;
                    }
                    if (w.equals(key2)) {
                        rank1 = rank1 + 1;
                    }
                    if (w.equals(key3)) {
                        rank1 = rank1 + 1;
                    }
                    Log.e("rank1= ",String.valueOf(rank1));

                }
                temp1 = content_we_got_from_window;
            }

        }
        }
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        return content_with_most_keyword;
    }


    public void search(String package_nm,String searchQuery){


        if(package_nm.contains("youtube")){

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
                try{

                    Intent searchIntent = new Intent(Intent.ACTION_SEARCH);
                    searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    searchIntent.setPackage(package_nm);
                    searchIntent.putExtra(SearchManager.QUERY, searchQuery);
                    startActivity(searchIntent);

                }catch (Exception e1){
                    Toast.makeText(this, "This app don't have any search field", Toast.LENGTH_SHORT).show();
                }


            }


        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
