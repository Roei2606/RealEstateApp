package com.example.realestateapp.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.realestateapp.Logic.ManagerForAgent;
import com.example.realestateapp.Modeles.Agent;
import com.example.realestateapp.Modeles.Asset;
import com.example.realestateapp.Modeles.Meeting;
import com.example.realestateapp.Modeles.MeetingList;
import com.example.realestateapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class addAsset extends AppCompatActivity {
    private TextInputEditText editTextCity;
    private TextInputEditText editTextStreetName;
    private TextInputEditText editTextStreetNumber;
    private TextInputEditText editTextFloor;
    private RadioGroup radioGroupForSaleRent;
    private RadioButton radioButtonForSale;
    private RadioButton radioButtonForRent;
    private TextInputEditText editTextMoreInfo;
    private TextInputEditText editTextPrice;
    private TextInputEditText editTextOwnerFirstName;
    private TextInputEditText editTexOwnerLastName;
    private TextInputEditText editTextOwnerPhoneNumber;
    private MaterialButton buttonSaveAsset;
    private MaterialButton buttonCancelAdd;
    private ManagerForAgent managerForAgent;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private String currentUID;
    private FirebaseAuth auth;
    private Agent currentAgent;
    private Asset newAsset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_asset);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        currentUID = user.getUid();
        managerForAgent = new ManagerForAgent();
        newAsset = new Asset();
        managerForAgent.loadAgentFromDatabase(currentUID, new ManagerForAgent.AgentCallback() {
            @Override
            public void onAgentLoaded(Agent agent) {
                currentAgent=agent;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addAsset.this , "Can't load data!",Toast.LENGTH_SHORT).show();
            }
        });

        findviews();
        initViews();
    }

    private void initViews() {
        buttonCancelAdd.setOnClickListener(v->{
            Intent intent = new Intent(addAsset.this, AllAssets.class);
            startActivity(intent);
            finish();
        });
        buttonSaveAsset.setOnClickListener(v->{
            addNewAsset();
            Intent intent = new Intent(addAsset.this, AllAssets.class);
            startActivity(intent);
            Toast.makeText(addAsset.this , "Asset saved!",Toast.LENGTH_SHORT).show();
        });
        radioGroupForSaleRent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButtonForSale){
                    newAsset.setRentOrSale(Asset.RentOrSale.SALE);
                }else if(checkedId == R.id.radioButtonForRent){
                    newAsset.setRentOrSale(Asset.RentOrSale.RENT);
                }
            }
        });
    }

    private void addNewAsset() {
        newAsset
                .setCity(editTextCity.getText().toString())
                .setStreet(editTextStreetName.getText().toString())
                .setStreetNumber(Integer.parseInt(editTextStreetNumber.getText().toString()))
                .setFloor(Integer.parseInt(editTextFloor.getText().toString()))
                .setMoreInfo(editTextMoreInfo.getText().toString())
                .setPrice(Long.parseLong(editTextPrice.getText().toString()))
                .setOwnerFirstName(editTextOwnerFirstName.getText().toString())
                .setOwnerLastName(editTexOwnerLastName.getText().toString())
                .setOwnerPhoneNumber(editTextOwnerPhoneNumber.getText().toString());
        String assetID = newAsset.getCity()+"_"+newAsset.getStreet()+"_"+newAsset.getStreetNumber();
        newAsset.setAssetID(assetID);

        newAsset.setAllMeetings(new MeetingList());
        managerForAgent.addAssetForAgent(currentAgent,newAsset);
        db=FirebaseDatabase.getInstance();
        String path = "Agents/"+currentUID;
        ref = db.getReference(path);
        ref.setValue(currentAgent);
        finish();
    }

    private void findviews() {
         editTextCity= findViewById(R.id.editTextCity);
         editTextStreetName= findViewById(R.id.editTextStreetName);
         editTextStreetNumber= findViewById(R.id.editTextStreetNumber);
         editTextFloor= findViewById(R.id.editTextFloor);
         radioGroupForSaleRent= findViewById(R.id.radioGroupForSaleRent);
         radioButtonForSale= findViewById(R.id.radioButtonForSale);
         radioButtonForRent= findViewById(R.id.radioButtonForRent);
         editTextMoreInfo= findViewById(R.id.editTextMoreInfo);
         editTextPrice= findViewById(R.id.editTextPrice);
         editTextOwnerFirstName= findViewById(R.id.editTextOwnerFirstName);
         editTexOwnerLastName= findViewById(R.id.editTexOwnerLastName);
         editTextOwnerPhoneNumber= findViewById(R.id.editTextOwnerPhoneNumber);
         buttonSaveAsset= findViewById(R.id.buttonSaveAsset);
         buttonCancelAdd = findViewById(R.id.buttonCancelAdd);
    }
}