package com.example.realestateapp.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateapp.Adapters.MeetingAdapter;
import com.example.realestateapp.Interfaces.MeetingCallBack;
import com.example.realestateapp.MeetingsActivity;
import com.example.realestateapp.Modeles.Meeting;
import com.example.realestateapp.Modeles.MeetingList;
import com.example.realestateapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllMeetings_fragment extends Fragment {

    private RecyclerView main_LST_meetings;
    private ArrayList<Meeting> meetingsList;
    private MeetingAdapter meetingAdapter;
    private String pathToCurrentAllMeetings;
    private MeetingList rearrangeMeetingList;


    public AllMeetings_fragment() {
    }

    public AllMeetings_fragment(ArrayList<Meeting> meetingsList){
        this.meetingsList = meetingsList;

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_all_meetings_fragment, container, false);
        Bundle bundle = getArguments();
        if(bundle!=null){
            pathToCurrentAllMeetings = bundle.getString("PATH_TO_ALL_MEETINGS");
        }
        findViews(view);
        initViews();
        return view;

    }
    private void initViews() {
        meetingAdapter = new MeetingAdapter(getContext(),meetingsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_meetings.setLayoutManager(linearLayoutManager);
        main_LST_meetings.setAdapter(meetingAdapter);

        meetingAdapter.setMeetingCallback(new MeetingCallBack() {
            @Override
            public void deleteMeetingButtonClicked(Meeting meeting, int position) {
                String meetingToDelete = pathToCurrentAllMeetings + "/allMeetings/" + position;
                showDeleteConfirmationDialog(meetingToDelete);
            }
        });
    }
    private void showDeleteConfirmationDialog(String meetingToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Meeting")
                .setMessage("Are you sure you want to delete this meeting?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMeeting(meetingToDelete);
                        Intent intent = new Intent(getContext(), AllAssets.class);
                        startActivity(intent);
                        Toast.makeText(getContext() , "Meeting deleted!",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext() , "Delete meeting canceled!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void rearrangeAllMeetingsForAsset() {
        rearrangeMeetingList = new MeetingList();
        rearrangeMeetingList.setAllMeetings(new ArrayList<>());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference currentAllMeetingsRef = database.getReference(pathToCurrentAllMeetings+"/allMeetings");
        currentAllMeetingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if ( snapshot.exists()) {
                    for(DataSnapshot meetingSnapShout : snapshot.getChildren()){
                        rearrangeMeetingList.getAllMeetings().add(meetingSnapShout.getValue(Meeting.class));
                    }
                    currentAllMeetingsRef.removeValue();
                    DatabaseReference newRef = database.getReference(pathToCurrentAllMeetings);
                    newRef.setValue(rearrangeMeetingList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void deleteMeeting(String meetingToDelete) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference currentMeetingRef = database.getReference(meetingToDelete);
        currentMeetingRef.setValue(null);
        rearrangeAllMeetingsForAsset();
    }

    private void findViews(View view) {
        main_LST_meetings = view.findViewById(R.id.main_LST_meetings);
    }
}