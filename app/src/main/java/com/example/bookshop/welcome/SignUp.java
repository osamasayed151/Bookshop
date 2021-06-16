package com.example.bookshop.welcome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bookshop.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etPassword, etuserName;
    private Button btnSignUp;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.sign_up );

        toolbar=findViewById(R.id.toolbar_signUp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        etFullName = findViewById(R.id.signUp_edFullName);
        etEmail = findViewById(R.id.signUp_edEmail);
        etPassword = findViewById(R.id.signUp_edPassword);
        etuserName = findViewById(R.id.signUp_edUserName);
        btnSignUp=findViewById(R.id.signUp_btnSignUp);
        progressBar = findViewById(R.id.progress);

        btnSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname, email, username, passwor;
                fullname = String.valueOf( etFullName.getText());
                email = String.valueOf( etEmail.getText());
                username = String.valueOf( etuserName.getText());
                passwor = String.valueOf( etPassword.getText());

                if (!fullname.equals("") && !email.equals("") && !username.equals("")&& !passwor.equals("")){

                    progressBar.setVisibility(View.VISIBLE);

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler();
                    handler.post( new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = passwor;
                            data[3] = email;
                            PutData putData = new PutData( "http://192.168.1.16/loginandregister/signup.php", "POST", field, data );
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText( SignUp.this, result, Toast.LENGTH_SHORT ).show();
                                        in = new Intent(getBaseContext(), Login.class);
                                        startActivity(in);
                                        finish();
                                    }else{
                                        Toast.makeText( SignUp.this, result, Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }else{
                    Toast.makeText( SignUp.this, "All fields requirement", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signup_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.signup_back:
                finish();
                return true;
        }
        return false;
    }
}
