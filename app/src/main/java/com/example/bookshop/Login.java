package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    private TextInputEditText etUsername,etPassword;
    private TextView tvForgotpass, tvSignup;
    private Button btnLogIn;
    private ProgressBar progressBar;
    private AppCompatCheckBox checkBox;
    private Intent in;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String CODE_ENTER_MAIN = "code_enter_main";
    public static final String BOOK_FILE = "book file";
    public static final String USERNAME_KEY = "username key";
    public static final String PASSWORD_KEY = "password key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );


        toolbar=findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        etUsername = findViewById(R.id.login_ed_username);
        etPassword = findViewById(R.id.login_ed_password);
        tvForgotpass = findViewById(R.id.login_txt_forgotpassword);
        tvSignup=findViewById(R.id.login_txt_signup);
        btnLogIn = findViewById(R.id.login_btn_login);
        progressBar = findViewById(R.id.progress);
        checkBox=findViewById(R.id.remind_me);

        sharedPreferences = getSharedPreferences(BOOK_FILE,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvSignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(getBaseContext(),SignUp.class);
                startActivity(in);
            }
        } );
        tvForgotpass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(getBaseContext(),ForgotPassword.class);
                startActivity(in);
            }
        } );

        btnLogIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username, passwor;
                username = String.valueOf( etUsername.getText());
                passwor = String.valueOf( etPassword.getText());

                if (!username.equals("")&& !passwor.equals("")){

                    progressBar.setVisibility(View.VISIBLE);

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler();
                    handler.post( new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = passwor;
                            PutData putData = new PutData( "http://192.168.1.16/loginandregister/login.php", "POST", field, data );
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText( getBaseContext(), result, Toast.LENGTH_SHORT ).show();
                                        // shared
                                        if(checkBox.isChecked()){
                                            editor.putString(USERNAME_KEY,username);
                                            editor.putString(PASSWORD_KEY,passwor);
                                            editor.apply();
                                        }
                                        in = new Intent(getBaseContext(),MainActivity.class);
                                        startActivity(in);
                                        finish();
                                    }else{
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText( getBaseContext(), result, Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }else{
                    Toast.makeText( getBaseContext(), "All fields requirement", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.login_skip:
                in = new Intent(getBaseContext(),MainActivity.class);
                in.putExtra(CODE_ENTER_MAIN,-1);
                startActivity(in);
                return true;
        }
        return false;
    }
}
