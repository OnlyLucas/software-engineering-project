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
import android.widget.EditText;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddFlatShareController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddFlatShareController extends Fragment {

    private Button cancelAddFlatShare, saveAddFlatShare;
    private Context context;
    private EditText descriptionAddFlatShare, nameAddFlatShare;
    private View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load Frontend
        fragmentView = inflater.inflate(R.layout.fragment_add_flat_share, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    private void addButtons() {

        cancelAddFlatShare.setOnClickListener(view -> {
            FragmentManageFlatShareController fragment = new FragmentManageFlatShareController();
            ToastUtil.makeToast(getString(R.string.changes_discarded), context);
            callFragment(fragment);
        });

        saveAddFlatShare.setOnClickListener(view -> {
            FragmentManageFlatShareController fragment = new FragmentManageFlatShareController();
            ToastUtil.makeToast(getString(R.string.saved), context);
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

        cancelAddFlatShare = fragmentView.findViewById(R.id.cancelAddFlatShare);
        descriptionAddFlatShare = fragmentView.findViewById(R.id.descriptionAddFlatShare);
        nameAddFlatShare = fragmentView.findViewById(R.id.nameAddFlatShare);
        saveAddFlatShare = fragmentView.findViewById(R.id.saveAddFlatShare);

    }
}