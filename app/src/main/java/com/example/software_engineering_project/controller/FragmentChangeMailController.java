package com.example.software_engineering_project.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    static UserRepository userRepository;
    static Context context;
    private View fragmentView;
    private Button cancelChangeMail, saveChangeMail;
    private EditText currentMail, newMail, confirmNewMail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userRepository = new UserRepository();
        context = getActivity();
        fragmentView = inflater.inflate(R.layout.fragment_change_mail, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();
        return fragmentView;

    }

    private void loadScreenElements() {

        cancelChangeMail = fragmentView.findViewById(R.id.cancelChangeMail);
        saveChangeMail = fragmentView.findViewById(R.id.saveChangeMail);

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen,fragment);
        transaction.commit();

    }

    private void addButtons() {

        cancelChangeMail.setOnClickListener(view -> {
            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.changes_discarded),context);
            callFragment(fragment);
        });

        saveChangeMail.setOnClickListener(view -> {
            checkMailChange();
            FragmentSettingsController fragment = new FragmentSettingsController();
            ToastUtil.makeToast(getString(R.string.new_e_mail_saved),context);
            callFragment(fragment);
        });

    }

    private void checkMailChange() {
        currentMail = fragmentView.findViewById(R.id.currentMail);
        newMail = fragmentView.findViewById(R.id.newMail);
        confirmNewMail = fragmentView.findViewById(R.id.confirmNewMail);
        String currentMailString = currentMail.getText().toString();
        String newMailString = newMail.getText().toString();
        String confirmMailString = confirmNewMail.getText().toString();

        User user = UserViewModel.getCurrentAppUser().getValue();

        if(user.getEmail().equals(currentMailString)) {

            System.out.println("Correct current mail");

            if(newMailString.equals(confirmMailString)) {
                System.out.println("Ready to persist new mail " + newMailString);
                Map<String, String> map = new HashMap<>();
                map.put("email", newMailString);
                userRepository.updateEmail(user, map, context);;
            } else {
                System.out.println("New mails not matching");
            }
        } else {
            System.out.println("Wrong current mail");
        }
    }

}