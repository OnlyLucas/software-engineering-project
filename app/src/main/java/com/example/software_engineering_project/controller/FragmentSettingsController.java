package com.example.software_engineering_project.controller;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSettingsController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSettingsController extends Fragment {

    static FragmentChangePasswordController fragmentChangePasswordController = new FragmentChangePasswordController();
    static FragmentChangeMailController fragmentChangeMailController = new FragmentChangeMailController();
    static FragmentManageFlatShareController fragmentManageFlatShareController = new FragmentManageFlatShareController();
    View fragmentView;
    private Button changePasswordButton;
    private Button changeMailButton;
    private Button createFlatShareButton;
    private Button logOutButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_setting_screen, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    private void loadScreenElements() {

        changePasswordButton = fragmentView.findViewById(R.id.changePasswordButton);
        changeMailButton = fragmentView.findViewById(R.id.changeMailButton);
        createFlatShareButton = fragmentView.findViewById(R.id.createFlatShareButton);
        logOutButton = fragmentView.findViewById(R.id.logOutButton);

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

}