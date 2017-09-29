package com.example.fatema.searchitems;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class StoreOutput
{
    @SerializedName("ispuEligible")
    private String ispuEligible;
    @SerializedName("stores")
    private ArrayList<StoreDetails> stores;

    public String getIspuEligible() {
        return ispuEligible;
    }

    public void setIspuEligible(String ispuEligible) {
        this.ispuEligible = ispuEligible;
    }

    public ArrayList<StoreDetails> getStores() {
        return stores;
    }

    public void setStores(ArrayList<StoreDetails> stores) {
        this.stores = stores;
    }
}
