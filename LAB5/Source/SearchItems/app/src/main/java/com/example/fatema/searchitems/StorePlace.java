package com.example.fatema.searchitems;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fatema on 9/28/17.
 */

public class StorePlace
{
    @SerializedName("sku")
    ArrayList<String> sku;

    public ArrayList<String> getSku()
    {
        return sku;
    }

    public void setSku(ArrayList<String> sku)
    {
        this.sku = sku;
    }
}

