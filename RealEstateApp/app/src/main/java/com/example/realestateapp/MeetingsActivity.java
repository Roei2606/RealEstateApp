package com.example.realestateapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;
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
import com.example.realestateapp.Adapters.MeetingAdapter;
import com.example.realestateapp.Interfaces.AssetCallback;
import com.example.realestateapp.Interfaces.MeetingCallBack;
import com.example.realestateapp.Modeles.Asset;
import com.example.realestateapp.Modeles.Meeting;
import com.example.realestateapp.Modeles.MeetingList;
import com.example.realestateapp.Views.AllAssets;
import com.example.realestateapp.Views.AllMeetings_fragment;
import com.example.realestateapp.Views.addAsset;
import com.example.realestateapp.Views.addNewMeeting;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class MeetingsActivity extends AppCompatActivity {
    private AllMeetings_fragment allMeetingsFragment;
    private FrameLayout allMeetings_FRAME;
    private CalendarView calenderView;
    private MaterialTextView noMeeting_LBL;
    private MaterialButton buttonAddNewMeeting;
    private String datePicked;
    private FirebaseAuth auth;
    private String currentUID;
    private DatabaseReference currentAllMeetingsRef;
    private String currentAssetId;
    private MeetingList currentAllMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meetings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        if (intent != null) {
            currentAssetId = intent.getStringExtra("ASSET_ID");
        }
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        currentUID = user.getUid();
        findViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        currentAllMeetingsRef = database.getReference("Agents").child(currentUID).child("allAssets").child(currentAssetId).child("allMeetings");
        currentAllMeetingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentAllMeetings = snapshot.getValue(MeetingList.class);
                    noMeeting_LBL.setVisibility(View.INVISIBLE);
                } else {
                    currentAllMeetings = new MeetingList();
                    currentAllMeetings.setAllMeetings(new ArrayList<Meeting>());
                    noMeeting_LBL.setVisibility(View.VISIBLE);
                }
                Bundle bundle = new Bundle();
                String currentAllMeetingsPath = "Agents/"+currentUID+"/"+"allAssets/"+currentAssetId+"/allMeetings";
                allMeetingsFragment = new AllMeetings_fragment(currentAllMeetings.getAllMeetings());
                bundle.putString("PATH_TO_ALL_MEETINGS",currentAllMeetingsPath);
                allMeetingsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.allMeetings_FRAME, allMeetingsFragment).commit();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                datePicked = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        buttonAddNewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingsActivity.this, addNewMeeting.class);
                String path = "Agents/"+currentUID+"/allAssets/"+currentAssetId+"/allMeetings";
                intent.putExtra("PATH_TO_allMeetings",path);
                intent.putExtra("PICKED_DATE",datePicked);
                intent.putExtra("CURRENT_ASSET_UID",currentAssetId);
                startActivity(intent);
            }
        });

    }

    private void findViews() {
        calenderView = findViewById(R.id.calenderView);
        allMeetings_FRAME = findViewById(R.id.allMeetings_FRAME);
        buttonAddNewMeeting = findViewById(R.id.buttonAddNewMeeting);
        noMeeting_LBL = findViewById(R.id.noMeeting_LBL);
    }
}