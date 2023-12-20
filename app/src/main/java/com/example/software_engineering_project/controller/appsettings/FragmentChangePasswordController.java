package com.example.software_engineering_project.controller.appsettings;

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
     * Called when the fragment needs to create its associated view hierarchy.
     * Inflates the layout defined in 'fragment_change_password.xml' to create the UI for this fragment.
     *
     * @param inflater           The LayoutInflater object that is used to inflate any views in the fragment.
     * @param container          The ViewGroup container where the fragment UI should be attached, if not null.
     * @param savedInstanceState A Bundle object containing the fragment's previously saved state, if any.
     * @return The root View of the inflated layout file for this fragment ('fragment_change_password.xml').
     *         This View represents the fragment's UI and will be displayed as part of the parent activity's layout.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_change_password, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();
        return fragmentView;

    }

    /**
     * Adds event listeners to the 'Cancel' and 'Save' buttons within the 'Change Password' fragment UI.
     * Defines actions to perform when these buttons are clicked.
     * 'Cancel' button discards changes and navigates back to the settings fragment.
     * 'Save' button triggers the process to save a new password (yet to be implemented).
     */
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

    /**
     * Replaces the current fragment with the specified fragment.
     * Uses the FragmentManager to begin a transaction, replacing the current content
     * (identified by R.id.contentFragmentMainScreen) with the provided fragment.
     * Commits the transaction to apply the fragment replacement.
     *
     * @param fragment The fragment to be displayed in place of the current fragment.
     */
    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    /**
     * Loads and initializes UI elements from the layout associated with the change password fragment.
     * Retrieves references to the cancel button, confirm new password field, current password field,
     * new password field, and save button.
     * Sets up these UI elements to be used for further interactions and actions.
     */
    private void loadScreenElements() {

        cancelChangePassword = fragmentView.findViewById(R.id.cancelChangePassword);
        confirmNewPassword = fragmentView.findViewById(R.id.confirmNewPassword);
        currentPassword = fragmentView.findViewById(R.id.currentPassword);
        newPassword = fragmentView.findViewById(R.id.newPassword);
        saveChangePassword = fragmentView.findViewById(R.id.saveChangePassword);

    }

}