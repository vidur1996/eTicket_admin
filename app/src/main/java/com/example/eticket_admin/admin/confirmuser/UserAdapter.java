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

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

  //  private User[] userSet;
    private ArrayList<User> userset;


    public UserAdapter(ArrayList<User> arrayList) {
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


        }


        @Override
        public int getItemCount() {
            return userset.size();
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