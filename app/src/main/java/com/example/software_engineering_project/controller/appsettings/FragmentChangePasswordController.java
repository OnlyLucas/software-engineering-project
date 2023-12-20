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
        userRepository = new UserRepository(context);
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

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

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