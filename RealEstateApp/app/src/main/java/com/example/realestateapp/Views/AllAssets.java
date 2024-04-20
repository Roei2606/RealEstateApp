package com.example.realestateapp.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateapp.Adapters.AssetAdapter;
import com.example.realestateapp.Interfaces.AssetCallback;
import com.example.realestateapp.MeetingsActivity;
import com.example.realestateapp.Modeles.Asset;
import com.example.realestateapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllAssets extends AppCompatActivity {
   private ShapeableImageView addAsset_BTN;
   private MaterialTextView allAssets_TXT_label;
   private Intent intentFromAllAssets;
   private RecyclerView main_LST_assets;
   private FirebaseAuth auth;
   private String currentUID;
   private HashMap<String, Asset>currentAllAssets;
   private ArrayList<Asset> allAssetsToArrayList = new ArrayList<>();
   private DatabaseReference allAssetsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_assets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        currentUID = user.getUid();
        findViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        allAssetsRef = database.getReference("Agents").child(currentUID).child("allAssets");
        allAssetsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    HashMap<String, Asset> assetHashMap = new HashMap<>();
                    for (DataSnapshot assetSnapshot : dataSnapshot.getChildren()) {
                        String assetKey = assetSnapshot.getKey();
                        Asset asset = assetSnapshot.getValue(Asset.class);
                        assetHashMap.put(assetKey, asset);
                    }
                    currentAllAssets=assetHashMap;
                    for (Map.Entry<String, Asset> entry : currentAllAssets.entrySet()){
                        allAssetsToArrayList.add(entry.getValue());
                    }
                    initViews();

                } else {
                    Toast.makeText(AllAssets.this , "No data!",Toast.LENGTH_SHORT).show();
                    initViews();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllAssets.this , "Error with loading data!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {
        addAsset_BTN.setOnClickListener(v->{
            startActivity(intentFromAllAssets);
        });
        AssetAdapter assetAdapter = new AssetAdapter(this,allAssetsToArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_assets.setLayoutManager(linearLayoutManager);
        main_LST_assets.setAdapter(assetAdapter);
        assetAdapter.setAssetCallback(new AssetCallback() {
            @Override
            public void bookMeetingButtonClicked(Asset asset, int position) {
                String assetForBookMeeting = asset.getCity()+"_"+asset.getStreet()+"_"+asset.getStreetNumber();
                Intent intent = new Intent(AllAssets.this, MeetingsActivity.class);
                intent.putExtra("ASSET_ID", assetForBookMeeting);
                startActivity(intent);
            }
            @Override
            public void deleteAssetButtonClicked(Asset asset, int position) {
                String assetForDelete = asset.getCity()+"_"+asset.getStreet()+"_"+asset.getStreetNumber();
                AlertDialog.Builder deleteAlert= new AlertDialog.Builder(AllAssets.this);
                deleteAlert.setTitle("Delete Asset");
                deleteAlert.setMessage("Are you sure you want to delete asset:\n"+assetForDelete);
                deleteAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = asset.getCity() + "_" + asset.getStreet() + "_" + asset.getStreetNumber();
                        allAssetsRef.child(s).setValue(null);
                        Intent myIntent = new Intent(AllAssets.this,AllAssets.class);
                        AllAssets.this.startActivity(myIntent);
                        Toast.makeText(AllAssets.this , "Asset deleted!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                deleteAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AllAssets.this , "The action was canceled",Toast.LENGTH_SHORT).show();
                    }
                });
                deleteAlert.show();

            }
        });

    }
    private void findViews() {
        intentFromAllAssets = new Intent(AllAssets.this, addAsset.class);
        allAssets_TXT_label = findViewById(R.id.allAssets_TXT_label);
        addAsset_BTN = findViewById(R.id.addAsset_BTN);
        main_LST_assets = findViewById(R.id.main_LST_assets);
    }
}