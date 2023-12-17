package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChangePasswordController #newInstance} factory method to
 * create an instance of this fragment.
 *
 * FragmentChangePasswordController is a fragment that handles the change of the user's password in the FlatFusion app.
 * It provides UI elements for users to input their current password, new password, and confirm the new password.
 * The class interacts with the UserRepository to validate and update the user's password.
 *
 */
public class FragmentChangePasswordController extends Fragment {

    static Context context;
    private Button cancelChangePassword, saveChangePassword;
    private EditText confirmNewPassword, currentPassword, newPassword;
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

        fragmentView = inflater.inflate(R.layout.fragment_change_password, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();
        return fragmentView;

    }

    private void addButtons() {

        cancelChangePassword.setOnClickListener(view -> {
            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.changes_discarded), context);
            callFragment(fragment);
        });

        saveChangePassword.setOnClickListener(view -> {

            //Daten müssen hier noch gesaved werden

            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.new_password_saved), context);
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

        cancelChangePassword = fragmentView.findViewById(R.id.cancelChangePassword);
        confirmNewPassword = fragmentView.findViewById(R.id.confirmNewPassword);
        currentPassword = fragmentView.findViewById(R.id.currentPassword);
        newPassword = fragmentView.findViewById(R.id.newPassword);
        saveChangePassword = fragmentView.findViewById(R.id.saveChangePassword);

    }

}