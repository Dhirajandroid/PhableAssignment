package com.example.phableassignment.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phableassignment.R;
import com.example.phableassignment.db.model.UserModel;
import com.example.phableassignment.ui.listener.RecyclerViewClickListener;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.RecyclerViewHolder>  {

    private List<UserModel> userList;
    private RecyclerViewClickListener mListener;

    public UserListAdapter(List<UserModel> userList,RecyclerViewClickListener listener) {
        this.userList = userList;
        mListener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_recycler_list, parent, false),mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int position) {
        UserModel userModel = userList.get(position);
        recyclerViewHolder.name.setText(userModel.getName());
        recyclerViewHolder.email.setText(userModel.getEmailId());
        recyclerViewHolder.itemView.setTag(userModel);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addItems(List<UserModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView email;
        private RecyclerViewClickListener mListener;

        RecyclerViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
}
