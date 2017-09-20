package com.example.fatema.knowledgesearch;

import android.content.Context;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by fatema on 9/19/17.
 */

public class RegisterActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public String name2,username2,email2,password2,confirm_password2;
    String phone2;

    public boolean validation()
    {

        boolean valid = true;
        if(name2.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter a valid name", Toast.LENGTH_SHORT).show();
        }

        if (email2.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email2).matches())
        {
            Toast.makeText(getApplicationContext(), "Enter a valid email address", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if(phone2.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Enter a phone number", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(username2.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (password2.isEmpty() || password2.length() < 8)
        {

            Toast.makeText(getApplicationContext(), "Password should be atleast 8 characters minimum", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (confirm_password2.isEmpty() || confirm_password2.length() < 8)
        {

            Toast.makeText(getApplicationContext(), "Password should be atleast 8 characters minimum", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if(!password2.equals(confirm_password2)){
            valid=false;
            Toast.makeText(getApplicationContext(), "Password mismatch!Please enter the passwords again!", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }

    public void register(View v)
    {
        SharedPreferences sharedPreferences;

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        EditText name=(EditText) findViewById(R.id.name);
        EditText email=(EditText) findViewById(R.id.email);
        EditText phone=(EditText) findViewById(R.id.phone);
        EditText username=(EditText) findViewById(R.id.username1);
        EditText password=(EditText) findViewById(R.id.password1);
        EditText cpassword=(EditText) findViewById(R.id.cpassword);

        name2=name.getText().toString();
        email2=email.getText().toString();
        phone2=phone.getText().toString();
        username2=username.getText().toString();
        password2=password.getText().toString();
        confirm_password2=cpassword.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("UserName", username2);
        editor.putString("Password", password2);
        editor.commit();

        if(validation())
        {
            Intent loginpage= new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginpage);
        }


    }


}
