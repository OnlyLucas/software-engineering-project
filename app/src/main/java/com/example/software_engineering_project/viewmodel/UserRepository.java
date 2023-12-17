package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.dataservice.UserService;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.entity.UserCreate;
import com.example.software_engineering_project.util.ToastUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private UserService userService;
    private MutableLiveData<List<User>> currentUsers = new MutableLiveData<>();
    private MutableLiveData<User> userByMail = new MutableLiveData<>();

    public UserRepository(){
        userService = RetrofitClient.getInstance().create(UserService.class);

        getUsers();
    }

    public LiveData<List<User>> getCurrentUsers() {
        return currentUsers;
    }
    public void updateEmail(User user, Map<String, String> map, Context context){
        Call<User> call = userService.partialUpdateEntity(user.getId(), map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    System.out.println("Changed mail successfully");
                    // show toast of success
                    ToastUtil.makeToast("Changed mail successfully", context);
                } else {
                    ToastUtil.makeToast("Error while changing mail", context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ToastUtil.makeToast("Error while changing mail", context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

    public void getUsers() {
        // Perform the API call to get users asynchronously
        Group group = UserViewModel.getCurrentGroup().getValue();
        Call<List<User>> call = userService.getUsers(group.getId());
        call.enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    currentUsers.setValue(users);
                    System.out.println("User fetching successful");
                    // Handle the received users, e.g., update UI or store in a local database
                } else {
                    // Handle API error
                    System.out.println("Error while fetching users");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Handle network failure
                System.out.println("Network error while fetching users");
            }
        });
    }

    public void insertUser(UserCreate user, Context context) {
        // Perform the API call to insert a new group grocery
        Call<User> call = userService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    ToastUtil.makeToast("Registered " + user.getFirstName(), context);
                } else {
                    ToastUtil.makeToast("Error while adding new user " + user.getFirstName(), context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ToastUtil.makeToast("Error while adding new user " + user.getFirstName(), context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

    public void fetchUserByMail(String mail) {
        Call<User> call = userService.getUserByMail(mail);
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    userByMail.setValue(user);
                    getUsers();
                    System.out.println("User fetching by mail successful");
                    // Handle the received users, e.g., update UI or store in a local database
                } else {
                    // Handle API error
                    System.out.println("Error while fetching user by mail");
                    userByMail.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle network failure
                System.out.println("Network error while fetching user by mail");
            }
        });
    }

    public LiveData<User> getUserByMail(String mail) {
        fetchUserByMail(mail);
        return userByMail;
    }
}
