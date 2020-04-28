package com.example.phableassignment.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.phableassignment.R;
import com.example.phableassignment.db.model.UserModel;
import com.example.phableassignment.viewmodel.UserListViewModel;

import java.util.Objects;

public class AddNewUserFragment extends Fragment {

    private EditText userName;
    private EditText email;
    private UserListViewModel viewModel;
    private boolean isUpdate;
    private UserModel updateModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(null == getActivity()) {
            throw new IllegalStateException("getActivity() called  when Activity equals null");
        }
        viewModel = ViewModelProviders.of(this.getActivity()).get(UserListViewModel.class);

        Bundle b = getArguments();
        if (b != null) {
            isUpdate = b.getBoolean("isUpdateQuery");
        }
        viewModel.getSelectedUser().observe(getActivity(), this::setDataToFields);
    }

    private void setDataToFields(UserModel item) {
        userName.setText(item.getName());
        email.setText(item.getEmailId());

        updateModel = new UserModel(item.getName(),item.getEmailId());
        updateModel.setId(item.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add,
                container, false);

        userName = view.findViewById(R.id.userName);
        email = view.findViewById(R.id.userEmail);
        Button submit = view.findViewById(R.id.submitButton);

        submit.setOnClickListener(view1 -> {
            if (userName.getText().toString().isEmpty() )
                userName.setError("Missing Name");
            else if (email.getText().toString().isEmpty())
                email.setError("Missing Email");
            else if(!isValidEmail(email.getText().toString()))
                email.setError("Email format incorrect");
            else {
                if(isUpdate){
                    updateModel.setEmailId(email.getText().toString());
                    updateModel.setName(userName.getText().toString());
                    viewModel.updateItem(updateModel);
                }
                else{
                    viewModel.addItem(new UserModel(
                            userName.getText().toString(),
                            email.getText().toString()
                    ));
                }

                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                UserListFragment userListFragment = new UserListFragment();
                FragmentTransaction transaction = Objects.requireNonNull(manager).beginTransaction();
                transaction.replace(R.id.fragmentHolder, userListFragment);
                transaction.commit();

            }
        });

        return view;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
