package com.example.dikshanta.eyeattend;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText login_username, login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_username = (EditText)findViewById(R.id.log_username);
        login_password = (EditText)findViewById(R.id.log_password);
    }

    public void OnLogin(View view) {
        String username = login_username.getText().toString();
        String password = login_password.getText().toString();
        String catogery = "access";
        background runbackground = new background(this);
        runbackground.execute(catogery, username, password);
    }





}