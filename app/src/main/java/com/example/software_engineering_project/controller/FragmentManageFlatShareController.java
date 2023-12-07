package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterManageFlatShareListView;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.GroupMembershipRepository;
import com.example.software_engineering_project.viewmodel.UserRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentManageFlatShareController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentManageFlatShareController extends Fragment {
    private static UserRepository userRepository;
    private static GroupMembershipRepository groupMembershipRepository;

    private static LiveData<List<User>> currentUsers;

    View fragmentView;
    static ListView listView;
    EditText input_name, input_mail;
    ImageView enter;
    static AdapterManageFlatShareListView adapter;
    static ArrayList<String> items = new ArrayList<>();
    static Context context;
    private Button cancelCreateFlatShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userRepository = new UserRepository();
        groupMembershipRepository = new GroupMembershipRepository();
        currentUsers = userRepository.getCurrentUsers();

        fragmentView = inflater.inflate(R.layout.fragment_manage_flat_share, container, false);
        loadScreenElements();
        context = requireActivity();

        currentUsers.observe(getViewLifecycleOwner(), currentUsers -> {
            adapter = new AdapterManageFlatShareListView(getActivity(), currentUsers);
            listView.setAdapter(adapter);
        });

        listView.setLongClickable(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = currentUsers.getValue().get(position);
                ToastUtil.makeToast(user.getFirstName(), context);
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                removeItem(position);
                return false;
            }

        });

        //TODO enter mail -> find user by mail -> add userMembership to current group
        enter.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View view) {
                String text = input_mail.getText().toString();
                if (text.length() == 0) {
                    ToastUtil.makeToast("Enter mail here", context);
                } else {
                    addItem(text);
                    input_mail.setText("");
                    ToastUtil.makeToast("Added " + text, context);
                }


            }

        });

        return fragmentView;

    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.flatShareMemberList);
        input_name = fragmentView.findViewById(R.id.input_name);
        input_mail = fragmentView.findViewById(R.id.input_mail);
        enter = fragmentView.findViewById(R.id.addFlatShareMember);

    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {
        User user = currentUsers.getValue().get(i);
        Group group = UserViewModel.getCurrentGroup().getValue();
        groupMembershipRepository.deleteGroupMembership(user, group, context);
        listView.setAdapter(adapter);

    }

    // function to add an item given its name.
    public static void addItem(String item) {

        items.add(item);
        listView.setAdapter(adapter);

    }

}
