package com.example.lenovoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    dbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new dbHelper(this);

        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
            new LoadLocalData().execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        dbColumnList.allLenovo = new ArrayList<>();
    }


    class LoadLocalData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            Cursor cursor = dbHelper.getAllProduct(dbColumnList.lenovoData.TABLE_NAME);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    dbColumnList.allLenovo.add(new myModels(
                            cursor.getString(cursor.getColumnIndex(dbColumnList.lenovoData.COLUMN_RECORDID)),
                            cursor.getString(cursor.getColumnIndex(dbColumnList.lenovoData.COLUMN_RECORDTITLE)),
                            cursor.getString(cursor.getColumnIndex(dbColumnList.lenovoData.COLUMN_RECORDCONTENT)),
                            cursor.getBlob(cursor.getColumnIndex(dbColumnList.lenovoData.COLUMN_IMAGE))

                    ));
                }
            }
            cursor.close();
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(dbColumnList.allLenovo.size() > 0){
                        startProg();
                    }

                }
            },3000);



        }
    }

    public void startProg() {
        Intent intent = new Intent(getApplicationContext(), LenovoItems.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}