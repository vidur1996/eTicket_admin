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

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder> {

    //  private User[] userSet;
    private ArrayList<Trip> tripsset;


    public RevenueAdapter(ArrayList<Trip> arrayList) {
        //this.userSet = userSet;
        tripsset = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_revenue, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Trip myListData = tripsset.get(position);

        holder.tv_collection.setText("Rs."+myListData.getCollection().toString());
        holder.tv_conductor.setText(myListData.getConductorName());

    }

    @Override
    public int getItemCount() {
        return tripsset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_collection,tv_conductor;



        public ViewHolder(View itemView) {
            super(itemView);

            tv_collection = (TextView) itemView.findViewById(R.id.tv_revenue_collection);
            tv_conductor = (TextView) itemView.findViewById(R.id.tv_revenue_conductor);



        }
    }




}
