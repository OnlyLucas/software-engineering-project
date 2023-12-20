package com.example.software_engineering_project.controller.appsettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.FragmentDialogLeaveFlatShare;
import com.example.software_engineering_project.util.UILoaderUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSettingsController #newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * FragmentSettingsController is a fragment that provides various settings options for the user in the FlatFusion app.
 * Users can change their email, password, manage flat share members, and log out using this fragment.
 * It contains buttons to navigate to other settings-related fragments.
 */
public class FragmentSettingsController extends Fragment {

    private static FragmentChangePasswordController fragmentChangePasswordController = new FragmentChangePasswordController();
    private static FragmentChangeMailController fragmentChangeMailController = new FragmentChangeMailController();
    private static FragmentManageFlatShareController fragmentManageFlatShareController = new FragmentManageFlatShareController();
    private Button changeMailButton, changePasswordButton, createFlatShareButton, leaveFlatShareButton, logOutButton;
    private View fragmentView;


    /**
     * Called to create and return the view hierarchy associated with the fragment.
     * This method inflates the fragment's layout defined in the XML file to create its UI components.
     * It initializes and sets up the UI elements when the fragment is being created or recreated.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the previous state of the fragment (if available).
     *                           It is null if the fragment is being created for the first time.
     * @return The root view of the fragment's layout.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_setting_screen, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    /**
     * Initializes and sets up click listeners for various buttons present in the fragment's layout.
     * This method associates specific actions or fragments with each button click.
     * - The 'changePasswordButton' triggers the transition to the change password screen.
     * - The 'changeMailButton' triggers the transition to the change mail screen.
     * - The 'createFlatShareButton' triggers the transition to the manage flat share screen.
     * - The 'leaveFlatShareButton' triggers the display of a dialog for leaving the flat share.
     * - The 'logOutButton' logs out the current user and redirects to the login screen.
     */
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
            UILoaderUtil.startLoginActivity(getContext());
        });

    }

    /**
     * Replaces the current fragment with the provided fragment in the content frame.
     *
     * @param fragment The fragment to be displayed.
     *                 Replaces the existing fragment in the content frame identified by R.id.contentFragmentMainScreen.
     *                 Utilizes the FragmentManager to handle transactions for fragment replacement.
     *                 This method commits the transaction and sets the provided fragment as the active fragment.
     *                 This action replaces the current UI in the content frame with the UI of the provided fragment.
     *                 Uses the support version of FragmentManager.
     */
    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    /**
     * Loads and initializes the UI elements from the associated layout file.
     * Retrieves references to UI elements in the fragment's layout file for further interaction.
     * These UI elements include buttons related to user actions.
     */
    private void loadScreenElements() {

        changeMailButton = fragmentView.findViewById(R.id.changeMailButton);
        changePasswordButton = fragmentView.findViewById(R.id.changePasswordButton);
        createFlatShareButton = fragmentView.findViewById(R.id.manageFlatShareButton);
        leaveFlatShareButton = fragmentView.findViewById(R.id.leaveFlatShare);
        logOutButton = fragmentView.findViewById(R.id.logOutButton);

    }

}