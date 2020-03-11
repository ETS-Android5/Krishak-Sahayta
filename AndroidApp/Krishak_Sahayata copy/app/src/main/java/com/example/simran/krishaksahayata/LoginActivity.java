package com.example.simran.krishaksahayata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity
{
    Button btnSignIn, btnAccount, btnForgot;
    /*final FirebaseDatabase database=FirebaseDatabase.getInstance();*/
    EditText etUser, etPassword;
    SharedPreferences sp;
    String name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUser = (EditText) findViewById(R.id.etUser);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnAccount = findViewById(R.id.btnAccount);
        btnForgot = findViewById(R.id.btnForgot);
        sp = getSharedPreferences("f2", MODE_PRIVATE);

        Toast toast = Toast.makeText(this, "Welcome User!", Toast.LENGTH_SHORT);

        btnAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent b = new Intent(LoginActivity.this, AccountActivity.class);
                startActivity(b);
                finish();
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b1 = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(b1);
                finish();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etUser.getText().toString();
                password = etPassword.getText().toString();
                if (name.length() == 0) {
                    etUser.setError("Name is empty.");
                    etUser.requestFocus();
                    return;

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
                    etUser.setError("Invalid Username.");
                    etUser.requestFocus();
                    return;
                }

                if (password.length() == 0) {
                    etPassword.setError("Password is Empty.");
                    etPassword.requestFocus();
                    return;
                }


                if (password.length() < 6) {
                    etPassword.setError("Minimum Length should be of 8 characters. ");
                    etPassword.requestFocus();
                    return;
                }

                else {
                    Intent b2 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(b2);


                }
            }
        });
    }
}
