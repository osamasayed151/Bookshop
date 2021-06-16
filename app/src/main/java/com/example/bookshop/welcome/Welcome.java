package com.example.bookshop.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.bookshop.MainActivity;
import com.example.bookshop.R;

public class Welcome extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.wlcome );
        sharedPreferences = getSharedPreferences( Login.BOOK_FILE,MODE_PRIVATE);
        final String username = sharedPreferences.getString(Login.USERNAME_KEY,"-1");
        final String password = sharedPreferences.getString(Login.PASSWORD_KEY,"-1");
        Thread t1 = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        if (username == "-1" && password == "-1") {
                            in = new Intent( Welcome.this, Login.class );
                            startActivity(in);
                            finish();
                        }else{
                            in = new Intent( Welcome.this, MainActivity.class );
                            startActivity(in);
                            finish();
                        }
                    }
                } );
            }
        } );
        t1.start();

    }



}
