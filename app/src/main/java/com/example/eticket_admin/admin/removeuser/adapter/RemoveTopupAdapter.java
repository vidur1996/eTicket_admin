package com.example.eticket_admin.admin.removeuser.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket_admin.R;
import com.example.eticket_admin.data.User;

import java.util.ArrayList;

public class RemoveTopupAdapter extends RecyclerView.Adapter<RemoveTopupAdapter.ViewHolder> {

    //  private User[] userSet;
    private ArrayList<User> userset;
    onClickTopupRemoveAdapter callback;

    public RemoveTopupAdapter(ArrayList<User> arrayList) {
        //this.userSet = userSet;
        userset = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_delete_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User myListData = userset.get(position);
        holder.tv_name.setText(myListData.getName());
        holder.tv_email.setText(myListData.getEmail());
        holder.tv_userName.setText(myListData.getUsername());
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeleteClick(myListData,position);
            }
        });


    }


    @Override
    public int getItemCount() {
        return userset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_email;
        TextView tv_userName;
        Button delete_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name_delete);
            tv_email = (TextView) itemView.findViewById(R.id.tv_name_delete);
            tv_userName = (TextView) itemView.findViewById(R.id.tv_name_delete);
            delete_btn =(Button)itemView.findViewById(R.id.btn_delete_user);


        }
    }
    public void onClickTopupRemoveAdapter(onClickTopupRemoveAdapter callback){
        this.callback = callback;
    }
    public interface onClickTopupRemoveAdapter {
        public void onDeleteClick(User deleteUser,int index);


    }
}