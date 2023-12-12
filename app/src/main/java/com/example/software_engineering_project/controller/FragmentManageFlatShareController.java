package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterManageFlatShareListView;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.GroupMembershipRepository;
import com.example.software_engineering_project.viewmodel.UserRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentManageFlatShareController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentManageFlatShareController extends Fragment {

    private static AdapterManageFlatShareListView adapter;
    private static ArrayList<String> items = new ArrayList<>();
    private static Context context;
    private static GroupMembershipRepository groupMembershipRepository;
    private static ListView listView;
    private static LiveData<List<User>> currentUsers;
    private static UserRepository userRepository;
    private Button cancelManageFlatShare, saveManageFlatShare;
    private EditText inputName, inputMail;
    private ImageView enter;
    private View fragmentView;


    // function to add an item given its name.
    public static void addItem(String item) {

        items.add(item);
        listView.setAdapter(adapter);

    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {

        User user = currentUsers.getValue().get(i);
        Group group = UserViewModel.getCurrentGroup().getValue();
        groupMembershipRepository.deleteGroupMembership(user, group, context);
        listView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load Backend
        userRepository = new UserRepository();
        groupMembershipRepository = new GroupMembershipRepository();
        currentUsers = userRepository.getCurrentUsers();

        //Load Frontend
        fragmentView = inflater.inflate(R.layout.fragment_manage_flat_share, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();


        currentUsers.observe(getViewLifecycleOwner(), currentUsers -> {
            adapter = new AdapterManageFlatShareListView(getActivity(), currentUsers);
            listView.setAdapter(adapter);
        });

        listView.setAdapter(adapter);

        return fragmentView;

    }

    private void addButtons() {

        listView.setLongClickable(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            User user = currentUsers.getValue().get(position);
            ToastUtil.makeToast(user.getFirstName(), context);
        });

        listView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            removeItem(position);
            return false;
        });

        cancelManageFlatShare.setOnClickListener(view -> {
            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.changes_discarded), context);
            callFragment(fragment);
        });

        saveManageFlatShare.setOnClickListener(view -> {
            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.saved), context);
            callFragment(fragment);
        });

        //TODO enter mail -> find user by mail -> add userMembership to current group
        enter.setOnClickListener(view -> {
            String text = inputMail.getText().toString();
            if (text.length() == 0) {
                ToastUtil.makeToast(getString(R.string.enter_mail_here), context);
            } else {
                addItem(text);
                inputMail.setText(getString(R.string.empty_input_fields));
                ToastUtil.makeToast(getString(R.string.added) + text, context);
            }
        });

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    private void loadScreenElements() {

        cancelManageFlatShare = fragmentView.findViewById(R.id.cancelManageFlatShare);
        enter = fragmentView.findViewById(R.id.addFlatShareMember);
        inputMail = fragmentView.findViewById(R.id.input_mail);
        inputName = fragmentView.findViewById(R.id.input_name);
        listView = fragmentView.findViewById(R.id.flatShareMemberList);
        saveManageFlatShare = fragmentView.findViewById(R.id.saveManageFlatShare);

    }

}
