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
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.UserRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChangeMailController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChangeMailController extends Fragment {

    static Context context;
    static UserRepository userRepository;
    private Button cancelChangeMail, saveChangeMail;
    private EditText confirmNewMail, currentMail, newMail;
    private View fragmentView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        userRepository = new UserRepository();
        fragmentView = inflater.inflate(R.layout.fragment_change_mail, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

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

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    private void checkMailChange() {

        String currentMailString = currentMail.getText().toString();
        String newMailString = newMail.getText().toString();
        String confirmMailString = confirmNewMail.getText().toString();

        User user = UserViewModel.getCurrentAppUser().getValue();

        if (user.getEmail().equals(currentMailString)) {

            System.out.println(getString(R.string.correct_current_mail));

            if (newMailString.equals(confirmMailString)) {
                System.out.println(getString(R.string.ready_to_persist_new_mail) + newMailString);
                Map<String, String> map = new HashMap<>();
                map.put(getString(R.string.email), newMailString);
                userRepository.updateEmail(user, map, context);
                ;
            } else {
                System.out.println(getString(R.string.new_mails_not_matching));
            }
        } else {
            System.out.println(getString(R.string.wrong_current_mail));
        }

    }

    private void loadScreenElements() {

        cancelChangeMail = fragmentView.findViewById(R.id.cancelChangeMail);
        confirmNewMail = fragmentView.findViewById(R.id.confirmNewMail);
        currentMail = fragmentView.findViewById(R.id.currentMail);
        newMail = fragmentView.findViewById(R.id.newMail);
        saveChangeMail = fragmentView.findViewById(R.id.saveChangeMail);

    }

}