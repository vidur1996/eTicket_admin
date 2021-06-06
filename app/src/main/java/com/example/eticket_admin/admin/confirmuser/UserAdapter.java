package com.example.eticket_admin.admin.confirmuser;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket_admin.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private User[] userSet;

    public UserAdapter(User[] userSet) {
        this.userSet = userSet;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.item_confirm_user, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }
        public void onBindViewHolder(ViewHolder holder, int position) {
            final User myListData = userSet[position];
            holder.tv_name.setText(userSet[position].name);
            holder.tv_email.setText(userSet[position].email);
            holder.tv_userName.setText(userSet[position].username);


        }


        @Override
        public int getItemCount() {
            return userSet.length;
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