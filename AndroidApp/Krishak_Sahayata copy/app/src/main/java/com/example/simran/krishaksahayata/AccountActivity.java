package com.example.simran.krishaksahayata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class AccountActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    TextView tvPasswordCheck;
    Button btnCreate;
    String name, email, password, confirmPassword;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvPasswordCheck = findViewById(R.id.tvPasswordCheck);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                if (name.length() == 0) {
                    etName.setError("Name is empty.");
                    etName.requestFocus();
                    return;

                }
                if(email.isEmpty())
                {
                    etEmail.setError("Email is Empty.");
                    etEmail.requestFocus();
                    return;
                }
                else
                    {
                        if (email.trim().matches(emailPattern)) {
                        etPassword.requestFocus();
                    }
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

                if(confirmPassword.equals(password))
                {
                    tvPasswordCheck.setText("Password Matched!");
                    tvPasswordCheck.setTextColor(Color.GREEN);
                }

                else
                {
                    tvPasswordCheck.setText("Password did not match!");
                    tvPasswordCheck.setTextColor(Color.RED);
                    etConfirmPassword.requestFocus();
                    return;
                }

                Toast.makeText(getApplicationContext(),"Account Created!",Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application? ");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Exit");
        alertDialog.show();
    }
}
