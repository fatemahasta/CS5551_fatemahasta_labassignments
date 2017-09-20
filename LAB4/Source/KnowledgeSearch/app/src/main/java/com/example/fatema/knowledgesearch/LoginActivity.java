package com.example.fatema.knowledgesearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;




/**
 * Created by fatema on 9/19/17.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final int REQUEST_CODE = 100;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
       final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton = (SignInButton)findViewById(R.id.google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, REQUEST_CODE);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = result.getSignInAccount();
            Intent gotologin = new Intent(LoginActivity.this,activity_translate.class);
            startActivity(gotologin);
        }
    }



    public void login(View v)
    {


        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        String username3 = sharedPreferences.getString("UserName", "fatema");
        String password3 = sharedPreferences.getString("Password", "12345678");

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        //EditText username3 = (EditText) findViewById(R.id.username1);
        //EditText password3 = (EditText) findViewById(R.id.password1);
        String user_name = username.getText().toString();
        System.out.println(user_name);
        String pass_word = password.getText().toString();
        TextView errorText = (TextView) findViewById(R.id.error);
        boolean validationFlag = false;

        //Verify if the username and password are not empty.
        if (!user_name.isEmpty() && !pass_word.isEmpty())
        {

            if (user_name.equals(username3) && pass_word.equals(password3))

            {
                System.out.println(username3);
                validationFlag = true;
                //System.out.println("correct");
            }
            if (!validationFlag) {
                errorText.setVisibility(View.VISIBLE);
            }
            else
            {
                Intent redirect = new Intent(LoginActivity.this, activity_translate.class);
                startActivity(redirect);

            }

        }
    }
    public void register(View v)
    {
        Intent redirect1 = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(redirect1);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
