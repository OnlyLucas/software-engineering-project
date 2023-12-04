package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.GroceryListListViewAdapter;
import com.example.software_engineering_project.dataservice.GroupGroceryService;
import com.example.software_engineering_project.dataservice.GroupService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.GroupGrocery;
import com.example.software_engineering_project.viewmodel.GroceryRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroceryListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGroceryListController extends Fragment {

    View fragmentView;
    static ListView listView;
    EditText input;
    ImageView enter;
    static GroceryRepository groceryRepository;
    static LiveData<List<GroupGrocery>> groceryLiveData;
    static ArrayAdapter<GroupGrocery> adapter;
    static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        groceryRepository = new GroceryRepository();
        groceryLiveData = groceryRepository.getGroupGroceries();

        // TODO maybe wont update, as list is altered, not exchanged
        groceryLiveData.observe(getViewLifecycleOwner(), groceryList -> {
            adapter = new GroceryListListViewAdapter(getActivity(), groceryList);
            listView.setAdapter(adapter);
        });

        fragmentView = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        listView = fragmentView.findViewById(R.id.groceryList);

        input = fragmentView.findViewById(R.id.input);
        enter = fragmentView.findViewById(R.id.enter);
        context = getActivity();
        listView.setLongClickable(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupGrocery grocery = groceryLiveData.getValue().get(position);
                makeToast(grocery.getName());
                EditText input = fragmentView.findViewById(R.id.input);
                String inputString = input.getText().toString();

                GroupGroceryService service = RetrofitClient.getInstance().create(GroupGroceryService.class);
                GroupService serviceGroup = RetrofitClient.getInstance().create(GroupService.class);
                DisposableObserver<GroupGrocery> groupGrocery = service.getGroupGrocery(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<GroupGrocery>() {

                            @Override
                            public void onNext(@NonNull GroupGrocery grocery) {
                                grocery.setId(UUID.randomUUID());
                                grocery.setName(inputString);
                                Group group = new Group();
                                grocery.setGroup(group);
                                service.createGroupGrocery(grocery)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(updatedUser -> {
                                            // Handle the creation of group grocery
                                            System.out.println("Group grocery added successfully");
                                        }, throwable -> {
                                            // Handle errors in the creation process
                                            System.out.println("Error adding group grocery: " + throwable.getMessage());
                                        });
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error while adding group grocery" + e.toString());
                            }

                            @Override
                            public void onComplete() {
                                System.out.println("Group grocery adding completed");
                            }

                        });
            }

        });

        // Remove an item when its row is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItem(i);
                return false;
            }

        });

        enter.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupGrocery grocery = new GroupGrocery();
                String text = input.getText().toString();

                if (text.length() == 0) {
                    makeToast("Enter an item.");
                } else {
                    grocery.setName(text);
                    grocery.setCreatedByUser(UserViewModel.getCurrentAppUser().getValue());
                    grocery.setGroup(UserViewModel.getCurrentGroup().getValue());
                    addItem(grocery);
                    input.setText("");
                    makeToast("Added " + text);
                }

            }

        });

        return fragmentView;
    }

    // Override onDestroy() to save the contents of the grocery list right before the app is terminated
    @Override
    public void onDestroy() {

        File path = context.getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "list.txt"));
            writer.write(groceryLiveData.getValue().toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int item) {
        GroupGrocery grocery = groceryLiveData.getValue().get(item);
        makeToast("Removed: " + grocery.getName());
        groceryRepository.deleteGroupGrocery(grocery);
    }

    //TODO API Call for changing is_completed (and fetching only items which are not completed)
    public static void uncheckItem(int i) {
        GroupGrocery grocery = groceryLiveData.getValue().get(i);
        makeToast("Unchecked: " + grocery.getName());
    }

    // function to add an item given its name.
    public void addItem(GroupGrocery item) {
        groceryRepository.insertGroupGrocery(item);
    }

    // function to make a Toast given a string
    static Toast t;

    private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }

}