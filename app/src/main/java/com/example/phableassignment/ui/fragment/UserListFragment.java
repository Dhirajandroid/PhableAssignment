package com.example.phableassignment.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phableassignment.R;
import com.example.phableassignment.db.model.UserModel;
import com.example.phableassignment.ui.adapter.UserListAdapter;
import com.example.phableassignment.ui.listener.RecyclerViewClickListener;
import com.example.phableassignment.viewmodel.UserListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class UserListFragment extends Fragment {
    private UserListAdapter userListAdapter;
    private UserListViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(null == getActivity()) {
            throw new IllegalStateException("getActivity() called  when Activity equals null");
        }

        viewModel = ViewModelProviders.of(this.getActivity()).get(UserListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main,
                container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        RecyclerViewClickListener listener = (v, position) -> {
            UserModel userModel = (UserModel) v.getTag();
            showDialog(userModel);
        };

        userListAdapter = new UserListAdapter(new ArrayList<>(),listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(userListAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putBoolean("isUpdateQuery",false);

            FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            AddNewUserFragment addNewUserFragment = new AddNewUserFragment();
            addNewUserFragment.setArguments(args);
            FragmentTransaction transaction = Objects.requireNonNull(manager).beginTransaction();
            transaction.replace(R.id.fragmentHolder, addNewUserFragment);
            transaction.commit();
        });

        UserListViewModel viewModel = ViewModelProviders.of(this).get(UserListViewModel.class);

        viewModel.getUserList().observe(Objects.requireNonNull(getActivity()), userModels -> userListAdapter.addItems(userModels));

        return view;
    }

    private void showDialog(final UserModel userModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setTitle("Choose Action");
        builder.setMessage("Press Update to update data or Delete to delete this item.");

        builder.setPositiveButton("Update", (dialog, which) -> {

            viewModel.selectUser(userModel);

            Bundle args = new Bundle();
            args.putBoolean("isUpdateQuery",true);

            FragmentManager manager = getActivity().getSupportFragmentManager();
            AddNewUserFragment addNewUserFragment = new AddNewUserFragment();
            addNewUserFragment.setArguments(args);
            FragmentTransaction transaction = Objects.requireNonNull(manager).beginTransaction();
            transaction.replace(R.id.fragmentHolder, addNewUserFragment);
            transaction.commit();

            dialog.dismiss();
        });

        builder.setNegativeButton("Delete", (dialog, which) -> {

            viewModel.deleteItem(userModel);
            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
