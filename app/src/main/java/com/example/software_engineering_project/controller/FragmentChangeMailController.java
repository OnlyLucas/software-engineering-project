package com.example.software_engineering_project.controller;

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
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.dataservice.UserService;
import com.example.software_engineering_project.entity.User;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChangeMailController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChangeMailController extends Fragment {

    View fragmentView;
    private Button cancelChangeMail;
    private Button saveChangeMail;

    private EditText currentMail, newMail, confirmNewMail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_change_mail, container, false);
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

        cancelChangeMail = fragmentView.findViewById(R.id.cancelChangeMail);
        cancelChangeMail.setOnClickListener(view -> {
            FragmentSettingsController fragment = new FragmentSettingsController();
            callFragment(fragment);
        });

        saveChangeMail = fragmentView.findViewById(R.id.saveChangeMail);
        saveChangeMail.setOnClickListener(view -> {
            currentMail = fragmentView.findViewById(R.id.currentMail);
            newMail = fragmentView.findViewById(R.id.newMail);
            confirmNewMail = fragmentView.findViewById(R.id.confirmNewMail);
            String currentMailString = currentMail.getText().toString();
            String newMailString = newMail.getText().toString();
            String confirmMailString = confirmNewMail.getText().toString();
            UserService service = RetrofitClient.getInstance().create(UserService.class);

            // TODO query current user
            User user = new User();
            user.setEmail("");

            if(user.getEmail().equals(currentMailString)){

                System.out.println("Correct current mail");

                if(newMailString.equals(confirmMailString)){
                    System.out.println("Ready to persist new mail " + newMailString);

                    Map<String, String> map = new HashMap<>();
                    map.put("email", newMailString);

                    Observable<User> updatedUser = service.partialUpdateEntity(UUID.fromString("00000000-0000-0000-0000-000000000000"), map);
                    updatedUser.subscribeWith(new DisposableObserver<User>() {
                        @Override
                        public void onNext(@NonNull User user) {
                            // TODO user from context gets updated in android app
                            System.out.println("Success: " + user.toString());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            // TODO user mail gets reset to old mail in android app
                            System.out.println("Error: " + e.toString());
                        }

                        @Override
                        public void onComplete() {
                            System.out.println("Mail change observer completed: ");
                        }
                    });


                } else {
                    System.out.println("New mails not matching");
                }
            } else {
                System.out.println("Wrong current mail");
            }



            //Daten müssen hier noch gesaved werden

            FragmentSettingsController fragment = new FragmentSettingsController();
            callFragment(fragment);
        });

    }

}