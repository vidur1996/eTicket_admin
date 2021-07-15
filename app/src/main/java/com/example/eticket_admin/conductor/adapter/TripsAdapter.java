package com.example.eticket_admin.conductor.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket_admin.R;
import com.example.eticket_admin.data.Trip;
import com.example.eticket_admin.data.User;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {

    //  private User[] userSet;
    private ArrayList<Trip> tripsset;
    onClickTripsAdapter callback;

    public TripsAdapter(ArrayList<Trip> arrayList) {
        //this.userSet = userSet;
        tripsset = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_trip, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Trip myListData = tripsset.get(position);
        holder.tv_start.setText(myListData.getStartTime());
        holder.tv_end.setText( myListData.getEndTime());
        holder.tv_from.setText(myListData.getFromTrip());
        holder.tv_to.setText(myListData.getToTrip());
        holder.tv_collection.setText(myListData.getCollection());
        holder.tv_passsenger_count.setText(myListData.getPassengerCount());
        holder.tv_conductor.setText(myListData.getConductorName());
        holder.btn_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAcceptClick(myListData.getStartTime());
            }
        });


    }


    @Override
    public int getItemCount() {
        return tripsset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_start,tv_end,tv_from,tv_to,tv_collection,tv_passsenger_count,tv_conductor;
        Button btn_passenger;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_start = (TextView) itemView.findViewById(R.id.tv_trip_start);
            tv_end = (TextView) itemView.findViewById(R.id.tv_trip_end);
            tv_from = (TextView) itemView.findViewById(R.id.tv_trip_from);
            tv_to    = (TextView) itemView.findViewById(R.id.tv_trip_to);
            tv_collection = (TextView) itemView.findViewById(R.id.tv_trip_collection);
            tv_conductor = (TextView) itemView.findViewById(R.id.tv_trip_conductor);
            tv_passsenger_count = (TextView) itemView.findViewById(R.id.tv_trip_passsenger_count);
            btn_passenger = (Button) itemView.findViewById(R.id.btn_trip_view_passengers);


        }
    }
    public void onClickTripsAdapter(onClickTripsAdapter callback){
        this.callback = callback;
    }
    public interface onClickTripsAdapter {
        public void onAcceptClick(String tripID);


    }
}
