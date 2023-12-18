package com.example.software_engineering_project.controller.appsettings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.FragmentDialogLeaveFlatShare;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSettingsController #newInstance} factory method to
 * create an instance of this fragment.
 *
 * FragmentSettingsController is a fragment that provides various settings options for the user in the FlatFusion app.
 * Users can change their email, password, manage flat share members, and log out using this fragment.
 * It contains buttons to navigate to other settings-related fragments.
 *
 */
public class FragmentSettingsController extends Fragment {

    private static FragmentChangePasswordController fragmentChangePasswordController = new FragmentChangePasswordController();
    private static FragmentChangeMailController fragmentChangeMailController = new FragmentChangeMailController();
    private static FragmentManageFlatShareController fragmentManageFlatShareController = new FragmentManageFlatShareController();
    private Button changeMailButton, changePasswordButton, createFlatShareButton, leaveFlatShareButton, logOutButton;
    private View fragmentView;


    /**
     * Inflates the layout for this fragment, initializes the UI elements, and adds click listeners to buttons.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The inflated view for this fragment.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_setting_screen, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    private void addButtons() {

        changePasswordButton.setOnClickListener(view -> {
            callFragment(fragmentChangePasswordController);
        });

        changeMailButton.setOnClickListener(view -> {
            callFragment(fragmentChangeMailController);
        });

        createFlatShareButton.setOnClickListener(view -> {
            callFragment(fragmentManageFlatShareController);
        });

        leaveFlatShareButton.setOnClickListener(view -> {
            FragmentDialogLeaveFlatShare dialog = new FragmentDialogLeaveFlatShare();
            dialog.show(getChildFragmentManager(), "LeaveFlatShare");
        });

        logOutButton.setOnClickListener(view -> {
            Intent loginScreen = new Intent(requireActivity(), ActivityLoginScreenController.class);
            startActivity(loginScreen);
        });

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    private void loadScreenElements() {

        changeMailButton = fragmentView.findViewById(R.id.changeMailButton);
        changePasswordButton = fragmentView.findViewById(R.id.changePasswordButton);
        createFlatShareButton = fragmentView.findViewById(R.id.manageFlatShareButton);
        leaveFlatShareButton = fragmentView.findViewById(R.id.leaveFlatShare);
        logOutButton = fragmentView.findViewById(R.id.logOutButton);

    }

}