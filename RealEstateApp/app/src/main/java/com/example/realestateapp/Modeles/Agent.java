package com.example.realestateapp.Modeles;

import java.util.HashMap;

public class Agent {
    private String uid;
    private HashMap<String,Asset> allAssets = new HashMap<>();

    public Agent() {
    }

    public String getUid() {
        return uid;
    }

    public Agent setUid(String uid) {
        this.uid = uid;
        return this;
    }


    public HashMap<String, Asset> getAllAssets() {
        return allAssets;
    }

    public Agent setAllAssets(HashMap<String, Asset> allAssets) {
        this.allAssets = allAssets;
        return this;
    }
}
