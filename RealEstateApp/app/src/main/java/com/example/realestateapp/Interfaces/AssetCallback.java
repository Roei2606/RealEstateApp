package com.example.realestateapp.Interfaces;

import com.example.realestateapp.Modeles.Asset;

public interface AssetCallback {
    void bookMeetingButtonClicked(Asset asset, int position);
    void deleteAssetButtonClicked(Asset asset, int position);
}
