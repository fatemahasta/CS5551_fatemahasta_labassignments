package com.example.fatema.knowledgesearch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fatema on 9/19/17.
 */

public class activity_translate extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_translate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchText(View v) {
        final TextView result1 = (TextView) findViewById(R.id.result);
        result1.setTextColor(Color.parseColor("#000000"));
        final TextView view1 = (TextView) findViewById(R.id.textView6);

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
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                            jsonResult = new JSONObject(result);
                            JSONArray convertedTextArray = jsonResult.getJSONArray("itemListElement");
                            JSONObject convertedText = convertedTextArray.getJSONObject(0);
                            final String name = convertedText.getJSONObject("result").getString("name");
                            final String description = convertedText.getJSONObject("result").getString("description");
                            final String detail_description = convertedText.getJSONObject("result").getJSONObject("detailedDescription").getString("articleBody");

                            JSONObject convertedText1 = convertedTextArray.getJSONObject(1);
                            final String name1 = convertedText1.getJSONObject("result").getString("name");
                            final String description1 = convertedText1.getJSONObject("result").getString("description");
                            final String detail_description1 = convertedText1.getJSONObject("result").getJSONObject("detailedDescription").getString("articleBody");


                            //System.out.println(name);
                                //System.out.println(description);
                                //System.out.print(convertedTextArray);
                                //Log.d("okHttp", jsonResult.toString());
                            runOnUiThread(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        view1.setVisibility(View.VISIBLE);
                                        result1.setText("Name: " + name + "\n \n" + "Description: " + description + "\n \n" + "Detailed Description: " + detail_description+ "\n \n "+
                                                "Name: " + name1 + "\n \n" + "Description: " + description1 + "\n \n" + "Detailed Description: " + detail_description1
                                                );

                                    }
                                });


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


