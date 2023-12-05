package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.software_engineering_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChangePasswordController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChangePasswordController extends Fragment {

    View fragmentView;
    private Button cancelChangePassword;
    private Button saveChangePassword;
    static Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_change_password, container, false);
        context = getActivity();
        this.addButtons();
        return fragmentView;

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen,fragment);
        transaction.commit();

    }

    private void addButtons() {

        cancelChangePassword = fragmentView.findViewById(R.id.cancelChangePassword);
        cancelChangePassword.setOnClickListener(view -> {
            FragmentSettingsController fragment = new FragmentSettingsController();
            makeToast(getString(R.string.changes_discarded));
            callFragment(fragment);
        });

        saveChangePassword = fragmentView.findViewById(R.id.saveChangePassword);
        saveChangePassword.setOnClickListener(view -> {

            //Daten müssen hier noch gesaved werden

            FragmentSettingsController fragment = new FragmentSettingsController();
            makeToast(getString(R.string.new_password_saved));
            callFragment(fragment);
        });

    }

    static Toast t;

    private static void makeToast(String s) {

        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();

    }

}