package com.example.software_engineering_project.controller.appsettings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.AppStateRepository;
import com.example.software_engineering_project.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChangeMailController #newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * FragmentChangeMailController is a fragment that handles the change of email address in the FlatFusion app.
 * It provides UI elements for users to input their current email, new email, and confirm the new email.
 * The class interacts with the UserRepository to validate and update the user's email address.
 */
public class FragmentChangeMailController extends Fragment {

    private static final String TAG = FragmentChangeMailController.class.getSimpleName();
    static Context context;
    static UserRepository userRepository;
    private Button cancelChangeMail, saveChangeMail;
    private EditText confirmNewMail, currentMail, newMail;
    private View fragmentView;


    /**
     * Creates and inflates the view hierarchy associated with the Change Mail fragment.
     * Initializes UI elements, such as buttons and text fields, and sets up their respective listeners.
     *
     * @param inflater           The LayoutInflater object that can inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle object containing the fragment's previously saved state, if any.
     * @return The root View of the fragment layout.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = requireActivity();
        userRepository = new UserRepository(context);
        fragmentView = inflater.inflate(R.layout.fragment_change_mail, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    /**
     * Sets up listeners for the cancel and save buttons in the Change Mail fragment.
     * Performs actions when the buttons are clicked, such as discarding changes or saving a new email.
     * Calls appropriate methods based on button clicks and displays toast messages accordingly.
     */
    private void addButtons() {

        cancelChangeMail.setOnClickListener(view -> {
            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.changes_discarded), context);
            callFragment(fragment);
        });

        saveChangeMail.setOnClickListener(view -> {
            checkMailChange();
            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.new_e_mail_saved), context);
            callFragment(fragment);
        });

    }

    /**
     * Replaces the current fragment with the provided fragment in the activity's fragment container.
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
     * Checks and updates the user's email address based on the provided input.
     * Verifies if the current email matches the user's email and if the new email matches the confirmation.
     * Updates the user's email if the conditions are met.
     */
    private void checkMailChange() {

        String currentMailString = currentMail.getText().toString();
        String newMailString = newMail.getText().toString();
        String confirmMailString = confirmNewMail.getText().toString();

        User user = AppStateRepository.getCurrentAppUserLiveData().getValue();

        if (user.getEmail().equals(currentMailString)) {
            if (newMailString.equals(confirmMailString)) {
                Log.i(TAG, getString(R.string.ready_to_persist_new_mail) + newMailString);
                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.emailJson), newMailString);
                userRepository.updateEmail(user, map, context);
            } else {
                ToastUtil.makeToast(getString(R.string.new_mails_not_matching), context);
            }
        } else {
            ToastUtil.makeToast(getString(R.string.wrong_current_mail), context);
        }

    }

    /**
     * Initializes and loads the screen elements for changing the email.
     * Finds and assigns views by their IDs from the layout resource file.
     */
    private void loadScreenElements() {

        cancelChangeMail = fragmentView.findViewById(R.id.cancelChangeMail);
        confirmNewMail = fragmentView.findViewById(R.id.confirmNewMail);
        currentMail = fragmentView.findViewById(R.id.currentMail);
        newMail = fragmentView.findViewById(R.id.newMail);
        saveChangeMail = fragmentView.findViewById(R.id.saveChangeMail);

    }

}