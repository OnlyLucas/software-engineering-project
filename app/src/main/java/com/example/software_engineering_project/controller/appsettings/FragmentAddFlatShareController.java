package com.example.software_engineering_project.controller.appsettings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.GroupRepository;
import com.example.software_engineering_project.repository.AppStateRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddFlatShareController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddFlatShareController extends Fragment {

    private static GroupRepository groupRepository;
    private Button cancelAddFlatShare, saveAddFlatShare;
    private Context context;
    private EditText descriptionAddFlatShare, nameAddFlatShare;
    private View fragmentView;

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null if the fragment does not provide a UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        groupRepository = new GroupRepository();

        fragmentView = inflater.inflate(R.layout.fragment_add_flat_share, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    /**
     * Sets up button click listeners for the cancel and save actions in the Add Flat Share screen.
     * - The cancel button discards changes and navigates back to the Manage Flat Share screen.
     * - The save button checks for existing group membership and navigates accordingly.
     */
    private void addButtons() {

        cancelAddFlatShare.setOnClickListener(view -> {
            FragmentManageFlatShareController fragment = new FragmentManageFlatShareController();
            ToastUtil.makeToast(getString(R.string.changes_discarded), context);
            callFragment(fragment);
        });

        saveAddFlatShare.setOnClickListener(view -> {
            Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
            if(group == null){
                FragmentManageFlatShareController fragment = new FragmentManageFlatShareController();
                checkInputs();
                callFragment(fragment);
            } else {
                ToastUtil.makeToast(getString(R.string.already_member_of_group), context);
            }

        });

    }

    /**
     * Validates the input fields for adding a new flat share.
     * - Checks the length and content of the name and description fields.
     * - Displays toast messages for invalid input or performs the insertion of a new group if inputs are valid.
     */
    private void checkInputs() {
        String name = nameAddFlatShare.getText().toString();
        String description = descriptionAddFlatShare.getText().toString();

        if(name.length() == 0){
            ToastUtil.makeToast(getString(R.string.enter_flat_share_name), context);
            return;
        }

        if(name.length() > 20){
            ToastUtil.makeToast(getString(R.string.enter_shorter_name), context);
            return;
        }
        if(description.length() == 0){
            ToastUtil.makeToast(getString(R.string.enter_description), context);
            return;
        }

        if(description.length() > 20){
            ToastUtil.makeToast(getString(R.string.enter_shorter_description), context);
            return;
        }

        Group group = new Group(name, description);
        groupRepository.insertGroup(group, context);

    }

    /**
     * Replaces the current fragment with the provided fragment in the Main Screen container.
     *
     * @param fragment The fragment to be displayed in the Main Screen container.
     */
    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    /**
     * Initializes UI elements by finding and assigning views from the inflated layout.
     * Specifically, it initializes cancel, description, name, and save buttons for adding a flat share.
     */
    private void loadScreenElements() {

        cancelAddFlatShare = fragmentView.findViewById(R.id.cancelAddFlatShare);
        descriptionAddFlatShare = fragmentView.findViewById(R.id.descriptionAddFlatShare);
        nameAddFlatShare = fragmentView.findViewById(R.id.nameAddFlatShare);
        saveAddFlatShare = fragmentView.findViewById(R.id.saveAddFlatShare);

    }
}