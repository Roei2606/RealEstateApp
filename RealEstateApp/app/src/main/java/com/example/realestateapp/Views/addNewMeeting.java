package com.example.realestateapp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.realestateapp.Interfaces.MeetingCallBack;
import com.example.realestateapp.MeetingsActivity;
import com.example.realestateapp.Modeles.Meeting;
import com.example.realestateapp.Modeles.MeetingList;
import com.example.realestateapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addNewMeeting extends AppCompatActivity {
    private TextInputEditText editTextDate;
    private TextInputEditText editTextTimeAndInfo;
    private MaterialButton buttonSetMeeting;
    private MaterialButton buttonCancel;
    private MeetingList currentAllMeetings;
    private FirebaseAuth auth;
    private String currentUID;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private DatabaseReference currentAllMeetingsRef;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_meeting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        currentUID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        currentAllMeetingsRef = database.getReference("Agents").child(currentUID).child("allAssets").child(intent.getStringExtra("CURRENT_ASSET_UID")).child("allMeetings");
        currentAllMeetingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentAllMeetings = snapshot.getValue(MeetingList.class);

                } else {
                    currentAllMeetings = new MeetingList();
                    currentAllMeetings.setAllMeetings(new ArrayList<Meeting>());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        findViews();
        initViews();
    }

    private void initViews() {
        editTextDate.setText(intent.getStringExtra("PICKED_DATE"));
        buttonSetMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMeetingForAsset();
                Intent intent = new Intent(addNewMeeting.this, AllAssets.class);
                startActivity(intent);
                Toast.makeText(addNewMeeting.this , "Meeting saved!",Toast.LENGTH_SHORT).show();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addNewMeeting.this, AllAssets.class);
                startActivity(intent);
                Toast.makeText(addNewMeeting.this , "Meeting Not saved!",Toast.LENGTH_SHORT).show();

            }
        });
    }
private void addNewMeetingForAsset(){
    Meeting newMeeting = new Meeting();
    newMeeting.setDate(editTextDate.getText().toString());
    newMeeting.setInfo(editTextTimeAndInfo.getText().toString());
    currentAllMeetings.getAllMeetings().add(newMeeting);
    String path = intent.getStringExtra("PATH_TO_allMeetings");
    db=FirebaseDatabase.getInstance();
    ref = db.getReference(path);
    ref.setValue(currentAllMeetings);
    finish();
}
    private void findViews() {
        editTextDate = findViewById(R.id.editTextDate);
        editTextTimeAndInfo= findViewById(R.id.editTextTimeAndInfo);
        buttonSetMeeting= findViewById(R.id.buttonSetMeeting);
        buttonCancel= findViewById(R.id.buttonCancel);
    }
}