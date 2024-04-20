package com.example.realestateapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateapp.Interfaces.MeetingCallBack;
import com.example.realestateapp.Modeles.Meeting;
import com.example.realestateapp.Modeles.MeetingList;
import com.example.realestateapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>{
    private Context context;
    private ArrayList<Meeting> allMeeting;
    private MeetingCallBack meetingCallBack;
    public MeetingAdapter(Context context, ArrayList<Meeting> allMeeting) {
        this.context = context;
        this.allMeeting = allMeeting;
    }
    public MeetingAdapter setMeetingCallback(MeetingCallBack meetingCallback){
        this.meetingCallBack = meetingCallback;
        return this;

    }
    @NonNull
    @Override
    public MeetingAdapter.MeetingViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_meeting,parent,false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting meeting =getItem(position);

        holder.meetingDate_LBL.setText(meeting.getDate());
        holder.meetingMoreInfo_LBL.setText(meeting.getInfo());


    }

    @Override
    public int getItemCount() {
        return allMeeting.size();
    }
    private Meeting getItem(int position){return this.allMeeting.get(position);}
    public class MeetingViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView meetingDate_LBL;
        private MaterialTextView meetingMoreInfo_LBL;
        private ShapeableImageView meeting_BTN_delete;

        public MeetingViewHolder(@NonNull View itemView){
            super(itemView);
            meetingDate_LBL= itemView.findViewById(R.id.meetingDate_LBL);
            meetingMoreInfo_LBL= itemView.findViewById(R.id.meetingMoreInfo_LBL);
            meeting_BTN_delete=itemView.findViewById(R.id.meeting_BTN_delete);
            meeting_BTN_delete.setOnClickListener(v->{
                if(meetingCallBack != null) {
                    meetingCallBack.deleteMeetingButtonClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
