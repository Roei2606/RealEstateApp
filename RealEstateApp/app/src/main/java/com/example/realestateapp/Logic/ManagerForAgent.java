package com.example.realestateapp.Logic;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.realestateapp.Modeles.Agent;
import com.example.realestateapp.Modeles.Asset;
import com.example.realestateapp.Views.addNewMeeting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerForAgent {

    private DatabaseReference agentsRef;

    public ManagerForAgent() {agentsRef = FirebaseDatabase.getInstance().getReference().child("Agents");}

    public void loadAgentFromDatabase(String agentUid, final AgentCallback callback) {
        DatabaseReference agentRef = agentsRef.child(agentUid);
        agentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Agent agent = dataSnapshot.getValue(Agent.class);
                    if(agent!=null){
                        callback.onAgentLoaded(agent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled(databaseError);
            }
        });
    }


    public interface AgentCallback {
        void onAgentLoaded(Agent agent);
        void onCancelled(DatabaseError databaseError);
    }

    public void addAssetForAgent(Agent agent, Asset asset){
        if(agent.getAllAssets()==null){
            HashMap<String,Asset> newHash = new HashMap<>();
            newHash.put(asset.getAssetid(),asset);
            agent.setAllAssets(newHash);
        }
        agent.getAllAssets().put(asset.getAssetid(),asset);
    }
}



