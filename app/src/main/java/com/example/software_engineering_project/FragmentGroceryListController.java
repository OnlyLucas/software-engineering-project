package com.example.software_engineering_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroceryListController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGroceryListController extends Fragment {

    View fragmentView;
    ListView listView;

    String[] country = new String[]{"India","Germany","Hans", "Meier"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_grocery_list_controller, container, false);
        listView = fragmentView.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, country);
        listView.setAdapter(adapter);


        return fragmentView;
    }
}