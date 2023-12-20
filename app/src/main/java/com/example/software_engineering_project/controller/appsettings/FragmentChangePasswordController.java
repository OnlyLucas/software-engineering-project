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
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.repository.AppStateRepository;
import com.example.software_engineering_project.repository.UserRepository;
import com.example.software_engineering_project.util.ToastUtil;

import request.UserWithPasswordRequest;

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
    private EditText confirmNewPasswordText, currentPasswordText, newPasswordText;
    private View fragmentView;
    private UserRepository userRepository;


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
        userRepository = new UserRepository(context);
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

            String currentPassword, newPassword, confirmNewPassword;

            currentPassword = currentPasswordText.getText().toString();
            newPassword = newPasswordText.getText().toString();
            confirmNewPassword = confirmNewPasswordText.getText().toString();

            if(!checkInputs(currentPassword, newPassword, confirmNewPassword)){
                return;
            }

            User currentUser = AppStateRepository.getCurrentAppUserLiveData().getValue();
            UserWithPasswordRequest request = new UserWithPasswordRequest(currentUser, newPassword);
            userRepository.updatePassword(request, context);
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
        confirmNewPasswordText = fragmentView.findViewById(R.id.confirmNewPassword);
        currentPasswordText = fragmentView.findViewById(R.id.currentPassword);
        newPasswordText = fragmentView.findViewById(R.id.newPassword);
        saveChangePassword = fragmentView.findViewById(R.id.saveChangePassword);

    }

    private boolean checkInputs(String currentPassword, String newPassword, String confirmNewPassword){
        String actualOldPassword = AppStateRepository.getCurrentAppUserLiveData().getValue().getPassword();

        // Check if old password is entered correctly
        if (!currentPassword.equals(actualOldPassword)){
            ToastUtil.makeToast(getString(R.string.wrong_current_password), context);
            return false;
        }

        if (newPassword.length() == 0 || confirmNewPassword.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_password), context);
            return false;
        }

        if (newPassword.length() > 25 || confirmNewPassword.length() > 25) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_password), context);
            return false;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            ToastUtil.makeToast(getString(R.string.passwords_not_matching), context);
            return false;
        }

        if (newPassword.length() < 9 || confirmNewPassword.length() < 9) {
            ToastUtil.makeToast(getString(R.string.enter_longer_password), context);
            return false;
        }

        // Check if new password is the same as the old password
        if (newPassword.equals(actualOldPassword)){
            ToastUtil.makeToast(getString(R.string.old_new_passwords_equal), context);
            return false;
        }

        return true;
    }

}