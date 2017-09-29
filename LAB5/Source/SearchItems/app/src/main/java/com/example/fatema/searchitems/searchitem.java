package com.example.fatema.searchitems;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchitem extends AppCompatActivity implements View.OnClickListener
{
    final String bestBuyKey = "68qthcka6yz8w2vkf7z2xqd7";

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchitem);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button upload=(Button) findViewById(R.id.upload);
        Button camera=(Button)findViewById(R.id.camera) ;
        camera.setOnClickListener(this);
        upload.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.camera){
            Toast.makeText(getApplicationContext(),"camara Open",Toast.LENGTH_LONG);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (getFromPref(this, ALLOW_KEY)) {
                    showSettingsAlert();
                } else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)

                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA)) {
                        showAlert();
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
            } else {
                openCamera();
            }

        }

        if(v.getId()==R.id.upload){
            openGallery();
        }

    }



    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(searchitem.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(searchitem.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(searchitem.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(searchitem.this);
                    }
                });

        alertDialog.show();
    }
    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");//It shows only images
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Picture"), 1);
    }
    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,0);
    }
    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView iv = (ImageView) findViewById(R.id.userimage);
        iv.setVisibility(View.VISIBLE);
        Bitmap bmp = null;

        if (requestCode == 0 && resultCode == RESULT_OK) {
            bmp = (Bitmap) data.getExtras().get("data");
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        iv.setImageBitmap(bmp);


    final ClarifaiClient client = new ClarifaiBuilder("e54514ddc4914b1bb92e34a52d8652a7").buildSync();
    final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

        new AsyncTask<byte[] , Object, ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {
    @Override
    protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(byte[]... params) {

        ClarifaiResponse<List<ClarifaiOutput<Concept>>> frames =client.getDefaultModels().generalModel().predict()
                .withInputs(ClarifaiInput.forImage(params[0]))
                .executeSync();
        //    Toast.makeText(getApplicationContext(),"==>",Toast.LENGTH_LONG).show();
        return frames;
    }
    @Override
    protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> feed) {

        List<ClarifaiOutput<Concept>> listResponse=feed.get();
        for(ClarifaiOutput<Concept> item:listResponse){
            List<Concept>  cc=item.data();
            Collections.sort(cc, new Comparator<Concept>(){
                public int compare(Concept o1, Concept o2){
                    if((o1.value() - o2.value())>0){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            });
            showResult(cc);
            TextView tvName = (TextView)findViewById(R.id.productName);
            tvName.setText(cc.get(0).name().toUpperCase());
            tvName.setVisibility(View.VISIBLE);
            searchResults("4807511","64112",bestBuyKey);


        }
    }

}.execute(outStream.toByteArray());

}

    private void showResult(List<Concept>  cc ){
        Item item=new Item(this,cc);
        item.show();
    }

    private List<StoreDetails> searchResults(String sku,String pincode,String Key)
    {


        BestBuyInterface apiService =
                BestBuy.getClient().create(BestBuyInterface.class);
        Call<StoreOutput> call = apiService.getResponse(sku,pincode,bestBuyKey);
        call.enqueue(new Callback<StoreOutput>() {
            @Override
            public void onResponse(Call<StoreOutput>call, Response<StoreOutput> response) {
                List<StoreDetails> stores = response.body().getStores();
                showStores(stores,getApplicationContext());
            }
            @Override
            public void onFailure(Call<StoreOutput>call, Throwable t) {
                // Log error here since request failed

            }
        });
        return null;
    }

    public void showStores(List<StoreDetails> stores,Context context){
        Stores st=new Stores(this,stores);
        st.show();
    }

}

