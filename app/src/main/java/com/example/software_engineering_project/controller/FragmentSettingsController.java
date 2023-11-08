package com.example.software_engineering_project.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.software_engineering_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSettingsController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSettingsController extends Fragment {

    View fragmentView;
    private Button changePasswordButton;
    private Button changeMailButton;
    static FragmentChangePasswordController fragmentChangePasswordController;
    static FragmentChangeMailController fragmentChangeMailController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_setting_screen, container, false);
        this.addButtons();


        return fragmentView;
    }

    private void addButtons(){
        fragmentChangePasswordController = new FragmentChangePasswordController();
        changePasswordButton = fragmentView.findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(view -> {
            callFragment(fragmentChangePasswordController);
        });

        fragmentChangeMailController = new FragmentChangeMailController();
        changeMailButton = fragmentView.findViewById(R.id.changeMailButton);
        changeMailButton.setOnClickListener(view -> {
            callFragment(fragmentChangeMailController);
        });
    }

    private void callFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentSettingScreen, fragment);
        transaction.commit();
    }

}