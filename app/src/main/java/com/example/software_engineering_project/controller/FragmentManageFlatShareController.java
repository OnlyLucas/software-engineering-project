package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterManageFlatShareListView;
import com.example.software_engineering_project.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentManageFlatShareController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentManageFlatShareController extends Fragment {

    View fragmentView;
    static ListView listView;
    EditText input_name, input_mail;
    ImageView enter;
    static AdapterManageFlatShareListView adapter;
    static ArrayList<String> items = new ArrayList<>();
    static Context context;
    private Button cancelCreateFlatShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_manage_flat_share, container, false);
        loadScreenElements();
        context = requireActivity();

        adapter = new AdapterManageFlatShareListView(getActivity(), items);
        items.add("Meike");
        items.add("Lucas");
        items.add("Laura");
        items.add("Nikos");
        items.add("Jonas");

        listView.setLongClickable(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                Toast.makeText(getActivity(), clickedItem, Toast.LENGTH_SHORT).show();
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                removeItem(position);
                return false;
            }

        });

        enter.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View view) {
                String text = input_mail.getText().toString();
                if (text.length() == 0) {
                    makeToast("Enter an e-mail");
                } else {
                    addItem(text);
                    input_mail.setText("");
                    makeToast("Added " + text);
                }

            }

        });

        return fragmentView;

    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.flatShareMemberList);
        input_name = fragmentView.findViewById(R.id.input_name);
        input_mail = fragmentView.findViewById(R.id.input_mail);
        enter = fragmentView.findViewById(R.id.addFlatShareMember);

    }

    // Override onDestroy() to save the contents of the grocery list right before the app is terminated
    @Override
    public void onDestroy() {

        File path = context.getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "list.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {

        ToastUtil.makeToast("Removed: " + items.get(i),context);
        items.remove(i);
        listView.setAdapter(adapter);

    }

    public static void uncheckItem(int i) {
        ToastUtil.makeToast("Unchecked: " + items.get(i),context);
    }

    // function to add an item given its name.
    public static void addItem(String item) {

        items.add(item);
        listView.setAdapter(adapter);

    }

}
