package com.example.eticket_admin.admin.confirmuser.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket_admin.R;
import com.example.eticket_admin.data.User;

import java.util.ArrayList;

public class UserConductorAdapter extends RecyclerView.Adapter<UserConductorAdapter.ViewHolder> {

    private final ArrayList<User> userset;
    onClickConductorAdapter callback;

    public UserConductorAdapter(ArrayList<User> arrayList) {
        //this.userSet = userSet;
        userset = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_confirm_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final User myListData = userset.get(position);
        holder.tv_name.setText(myListData.getName());
        holder.tv_email.setText(myListData.getEmail());
        holder.tv_userName.setText(myListData.getUsername());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAcceptClick(myListData, position);
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeclineClick(myListData, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return userset.size();
    }

    public void onClickConductorAdapter(onClickConductorAdapter callback) {
        this.callback = callback;
    }

    public interface onClickConductorAdapter {
        void onAcceptClick(User acceptUser, int index);

        void onDeclineClick(User declineUser, int index);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_email;
        TextView tv_userName;
        ImageView cancel;
        ImageView accept;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name_confirm);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email_confirm);
            tv_userName = (TextView) itemView.findViewById(R.id.tv_username_confirm);
            accept = (ImageView) itemView.findViewById(R.id.accept_confirm);
            cancel = (ImageView) itemView.findViewById(R.id.decline_confirm);

        }
    }
}