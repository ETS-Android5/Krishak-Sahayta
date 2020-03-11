package com.example.simran.krishaksahayata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etEmailID;
    String email_id;
    Button btnSend;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmailID = findViewById(R.id.etEmailID);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_id = etEmailID.getText().toString();
                if(email_id.isEmpty())
                {
                    etEmailID.setError("Email is Empty.");
                    etEmailID.requestFocus();
                    return;
                }
                else
                {
                    if (email_id.trim().matches(emailPattern))
                    {

                    }
                }
                Toast.makeText(getApplicationContext(),"An Email has been sent on the above provided mail id. Please check your Email account.", Toast.LENGTH_LONG).show();
            }


        });

    }
}
