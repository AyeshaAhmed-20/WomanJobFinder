package com.example.womanjobapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.Instant;

public class Register extends AppCompatActivity {
    public static final String tag="TAG";
    EditText mname,memail,mphone,mpass;
    Button register;
    TextView login;
    ProgressBar p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mname=findViewById(R.id.name);
        memail=findViewById(R.id.email);
        mphone=findViewById(R.id.phone_no);
        mpass=findViewById(R.id.password);
        register=findViewById(R.id.btn_register);
        login=findViewById(R.id.btn_login);
        p1=findViewById(R.id.progressbar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

    }
}