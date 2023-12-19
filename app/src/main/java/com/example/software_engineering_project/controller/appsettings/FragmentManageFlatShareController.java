package com.example.software_engineering_project.controller.appsettings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterManageFlatShareListView;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.GroupMembership;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.GroupMembershipRepository;
import com.example.software_engineering_project.repository.UserRepository;
import com.example.software_engineering_project.repository.AppStateRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentManageFlatShareController #newInstance} factory method to
 * create an instance of this fragment.
 *
 * FragmentManageFlatShareController is a fragment that allows users to manage members in a flat share group in the FlatFusion app.
 * Users can view, add, and remove members from the flat share group using this fragment.
 * It interacts with the UserRepository and GroupMembershipRepository to handle user and group membership operations.
 *
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
    private EditText inputMail;
    private ImageView addFlatShare, enter;
    private TextView descriptionManageFlatShare, nameManageFlatShare;
    private View fragmentView;


    /**
     * Adds an item with the specified name to the list of flat share members.
     *
     * @param mail The mail of the member to be added.
     */
    public static void addItem(String mail) {
        userRepository.getUserByMail(mail, context).observe((LifecycleOwner) context, newUser -> {
            if (newUser != null) {
                GroupMembership groupMembership = new GroupMembership(newUser);
                groupMembershipRepository.insertGroupMembership(groupMembership, userRepository, context);
            } else {
                ToastUtil.makeToast(context.getString(R.string.enter_valid_mail), context);
            }
        });
    }

    /**
     * Removes a flat share member at the specified index from the list.
     *
     * @param i The index of the member to be removed.
     */

    public static void removeItem(int i) {

        User user = currentUsers.getValue().get(i);
        groupMembershipRepository.deleteGroupMembership(user, userRepository, context);
        listView.setAdapter(adapter);

    }

    /**
     * Inflates the layout for this fragment, initializes the UI elements, and adds click listeners to buttons.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The inflated view for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = requireActivity();
        //Load Backend
        userRepository = new UserRepository(context);
        groupMembershipRepository = new GroupMembershipRepository();
        currentUsers = userRepository.getCurrentUsers();

        //Load Frontend
        fragmentView = inflater.inflate(R.layout.fragment_manage_flat_share, container, false);
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

        enter.setOnClickListener(view -> {
            Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
            if(group == null) {
                ToastUtil.makeToast(context.getString(R.string.join_a_group_first), context);
            } else {
                String text = inputMail.getText().toString();
                if (text.length() == 0) {
                    ToastUtil.makeToast(getString(R.string.enter_mail_here), context);
                } else {
                    boolean userExists = false;
                    for (User u : currentUsers.getValue()) {
                        if (u.getEmail().equals(text)) {
                            userExists = true;
                            ToastUtil.makeToast(getString(R.string.user_already_added), context);
                            break;
                        }
                    }
                    if (!userExists) {
                        addItem(text);
                        inputMail.setText(getString(R.string.empty_input_fields));
                    }
                }
            }
        });

        addFlatShare.setOnClickListener (view -> {
            FragmentAddFlatShareController fragment = new FragmentAddFlatShareController();
            callFragment(fragment);
        });

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    private void loadScreenElements() {

        addFlatShare = fragmentView.findViewById(R.id.addFlatShare);
        cancelManageFlatShare = fragmentView.findViewById(R.id.cancelManageFlatShare);
        descriptionManageFlatShare = fragmentView.findViewById(R.id.descriptionManageFlatShare);
        enter = fragmentView.findViewById(R.id.addFlatShareMember);
        inputMail = fragmentView.findViewById(R.id.inputMailManageFlatShare);
        listView = fragmentView.findViewById(R.id.flatShareMemberList);
        nameManageFlatShare = fragmentView.findViewById(R.id.nameManageFlatShare);
        saveManageFlatShare = fragmentView.findViewById(R.id.saveManageFlatShare);

    }

}
