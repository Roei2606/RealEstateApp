package com.example.realestateapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestateapp.Interfaces.AssetCallback;
import com.example.realestateapp.Modeles.Asset;
import com.example.realestateapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.AssetViewHolder>{
    private Context context;
    private ArrayList<Asset> allAssets;
    private AssetCallback assetCallback;

    public AssetAdapter(Context context, ArrayList<Asset> allAssets) {
        this.context = context;
        this.allAssets = allAssets;
    }
    public AssetAdapter setAssetCallback(AssetCallback assetCallback){
        this.assetCallback=assetCallback;
        return this;

    }
    @NonNull
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_asset,parent,false);
    return new AssetViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
        Asset asset =getItem(position);
        Glide.with(context).load(R.drawable.building).into(holder.asset_IMG);
        holder.city_LBL_asset.setText("City: "+asset.getCity());
        holder.street_LBL_asset.setText("Street: "+asset.getStreet()+" "+asset.getStreetNumber());
        holder.floor_LBL_asset.setText("Floor: "+ asset.getFloor());
        if(asset.getRentOrSale().equals(Asset.RentOrSale.RENT)){
            holder.rentOrSale_LBL_asset.setText("For rent");
        }
        if(asset.getRentOrSale().equals(Asset.RentOrSale.SALE)){
            holder.rentOrSale_LBL_asset.setText("For sale");
        }
        holder.price_LBL_asset.setText("Price: " + asset.getPrice());
        holder.asset_LBL_moreInfo.setText(asset.ownerDetailsToString());
        holder.asset_LBL_moreInfo.setOnClickListener(v -> {
            if (asset.isCollapsed())
                holder.asset_LBL_moreInfo.setMaxLines(Integer.MAX_VALUE);
            else
                holder.asset_LBL_moreInfo.setMaxLines(Asset.MAX_LINES_COLLAPSED);
            asset.setCollapsed(!asset.isCollapsed());
        });
    }

    @Override
    public int getItemCount() {
        return allAssets.size();
    }
    private Asset getItem(int position){
        return allAssets.get(position);
    }

    public class AssetViewHolder extends RecyclerView.ViewHolder{
        private ShapeableImageView asset_IMG;
        private MaterialTextView city_LBL_asset;
        private MaterialTextView street_LBL_asset;
        private ShapeableImageView asset_BTN_bookMeeting;
        private ShapeableImageView asse_BTN_delete;
        private MaterialTextView floor_LBL_asset;
        private MaterialTextView rentOrSale_LBL_asset;
        private MaterialTextView price_LBL_asset;
        private MaterialTextView asset_LBL_moreInfo;
        public AssetViewHolder(@NonNull View itemView){
            super(itemView);
            asset_IMG = itemView.findViewById(R.id.asset_IMG);
            city_LBL_asset= itemView.findViewById(R.id.city_LBL_asset);
            street_LBL_asset= itemView.findViewById(R.id.street_LBL_asset);
            asset_BTN_bookMeeting= itemView.findViewById(R.id.asset_BTN_bookMeeting);
            asse_BTN_delete= itemView.findViewById(R.id.asse_BTN_delete);
            floor_LBL_asset= itemView.findViewById(R.id.floor_LBL_asset);
            rentOrSale_LBL_asset= itemView.findViewById(R.id.rentOrSale_LBL_asset);
            price_LBL_asset= itemView.findViewById(R.id.price_LBL_asset);
            asset_LBL_moreInfo= itemView.findViewById(R.id.asset_LBL_moreInfo);
            asset_BTN_bookMeeting.setOnClickListener(v->{
                if (assetCallback != null){
                    assetCallback.bookMeetingButtonClicked(getItem(getAdapterPosition()),getAdapterPosition());
                }
            });
            asse_BTN_delete.setOnClickListener(v->{
                if(assetCallback != null) {
                    assetCallback.deleteAssetButtonClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
