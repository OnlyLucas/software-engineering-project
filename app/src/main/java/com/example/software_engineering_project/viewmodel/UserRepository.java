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

/**
 * The UserRepository class is responsible for managing interactions between the app's data
 * and the UserService, which communicates with the server to perform CRUD operations on Users.
 * It uses Retrofit for network communication and LiveData for observing changes in the list of current users.
 */
public class UserRepository {

    private UserService userService;
    private MutableLiveData<List<User>> currentUsers = new MutableLiveData<>();
    private MutableLiveData<User> userByMail = new MutableLiveData<>();

    /**
     * Default constructor for UserRepository. Initializes the UserService using RetrofitClient
     * and fetches the current list of users immediately upon repository creation.
     */
    public UserRepository(){
        userService = RetrofitClient.getInstance().create(UserService.class);

        getUsers();
    }

    /**
     * Retrieves the LiveData object containing the list of current users.
     *
     * @return LiveData<List<User>> The LiveData object containing the list of current users.
     */
    public LiveData<List<User>> getCurrentUsers() {
        return currentUsers;
    }

    /**
     * Updates the email of a user on the server and shows a toast upon success or failure.
     *
     * @param user    The User object whose email needs to be updated.
     * @param map     The map containing the new email.
     * @param context The application context for displaying toasts and handling UI updates.
     */
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

    /**
     * Fetches the list of users from the server asynchronously.
     * Updates the LiveData with the retrieved list upon a successful API response.
     */
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

    /**
     * Inserts a new user on the server and shows a toast upon success or failure.
     *
     * @param user    The UserCreate object containing user information.
     * @param context The application context for displaying toasts and handling UI updates.
     */
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

    /**
     * Fetches a user by email from the server asynchronously.
     * Updates the LiveData with the retrieved user upon a successful API response.
     *
     * @param mail The email of the user to be fetched.
     */
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

    /**
     * Retrieves the LiveData object containing the user fetched by email.
     *
     * @param mail The email of the user to be fetched.
     * @return LiveData<User> The LiveData object containing the user fetched by email.
     */
    public LiveData<User> getUserByMail(String mail) {
        fetchUserByMail(mail);
        return userByMail;
    }
}
