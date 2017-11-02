package com.example.rohithkumar.androidwearapplication;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.telecom.Call;
import android.view.Menu;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

    }

    public void searchText(View v) {
        final TextView result1 = (TextView) findViewById(R.id.result);
        result1.setTextColor(Color.parseColor("#000000"));
        //final TextView view1 = (TextView) findViewById(R.id.textView6);

        EditText string = (EditText) findViewById(R.id.string);
        String searchString;
        searchString = string.getText().toString();
        String data = "https://kgsearch.googleapis.com/v1/entities:search?" + "query=" + searchString + "&" + "key= AIzaSyAVKJCTAGwF8rLX-sSvFO-swMYoyhjmohs" + "&" + "indent=true" + "&" + "languages=en" + "&" + "limit=3";

        OkHttpClient client = new OkHttpClient();
        try {

           Request request = new Request.Builder()
                    .url(data)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e)
                {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException
                {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        JSONArray convertedTextArray = jsonResult.getJSONArray("itemListElement");
                        JSONObject convertedText = convertedTextArray.getJSONObject(0);
                        final String name = convertedText.getJSONObject("result").getString("name");
                        final String description = convertedText.getJSONObject("result").getString("description");
                        //final String detail_description = convertedText.getJSONObject("result").getJSONObject("detailedDescription").getString("articleBody");

                        JSONObject convertedText1 = convertedTextArray.getJSONObject(1);
                        final String name1 = convertedText1.getJSONObject("result").getString("name");
                        final String description1 = convertedText1.getJSONObject("result").getString("description");
                       // final String detail_description1 = convertedText1.getJSONObject("result").getJSONObject("detailedDescription").getString("articleBody");
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                            //  view1.setVisibility(View.VISIBLE);
                                              result1.setText("Name: " + name + "\n \n" + "Description: " + description + "\n \n" +
                                                      "Name: " + name1 + "\n \n" + "Description: " + description1 + "\n \n"
                                              );

                                          }
                                      }
                        );


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }


            });
        }
        catch (Exception ex)
        {
            string.setText(ex.getMessage());
        }


    }
    }
