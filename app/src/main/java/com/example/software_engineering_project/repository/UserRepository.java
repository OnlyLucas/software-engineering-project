package com.example.software_engineering_project.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.dataservice.UserService;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.entity.UserCreate;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;

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

    private UserService service;
    private static final String TAG = UserRepository.class.getSimpleName();
    private UserService userService;
    private MutableLiveData<List<User>> currentUsers = new MutableLiveData<>();
    private MutableLiveData<User> userByMail = new MutableLiveData<>();

    /**
     * Default constructor for UserRepository. Initializes the UserService using RetrofitClient
     * and fetches the current list of users immediately upon repository creation.
     */
    public UserRepository(Context context){
        userService = RetrofitClient.getInstance().create(UserService.class);
        fetchUsers(context);
    }

    /**
     * Fetches the list of users from the server asynchronously.
     * Updates the LiveData with the retrieved list upon a successful API response.
     */
    public void fetchUsers(Context context) {
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
        if(group != null) {
            Call<List<User>> call = userService.getUsers(group.getId());
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "User fetching successful");
                        List<User> users = response.body();
                        currentUsers.setValue(users);
                    } else {
                        // If unauthorized/bad credentials return to login screen
                        if(response.code() == 401){
                            Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                            ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                            UILoaderUtil.startLoginActivity(context);
                            return;
                        }

                        Log.e(TAG, "Error while fetching users");
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.e(TAG, "Network error while fetching users: " + t);
                }
            });
        } else {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
        }
    }

    /**
     * Fetches a user by email from the server asynchronously.
     * Updates the LiveData with the retrieved user upon a successful API response.
     *
     * @param mail The email of the user to be fetched.
     */
    public void fetchUserByMail(String mail, Context context) {
        Call<User> call = userService.getUserByMail(mail);
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "User fetching by mail successful");
                    User user = response.body();
                    userByMail.setValue(user);
                    fetchUsers(context);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG,"Error while fetching user by mail");
                    userByMail.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Network error while fetching user by mail: " + t);
            }
        });
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
     * Retrieves the LiveData object containing the user fetched by email.
     *
     * @param mail The email of the user to be fetched.
     * @return LiveData<User> The LiveData object containing the user fetched by email.
     */
    public LiveData<User> getUserByMail(String mail, Context context) {
        fetchUserByMail(mail, context);
        return userByMail;
    }

    /**
     * Inserts a new user on the server and shows a toast upon success or failure.
     *
     * @param user    The UserCreate object containing user information.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void insertUser(UserCreate user, Context context) {
        Call<User> call = userService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "User registration successful");
                    ToastUtil.makeToast(context.getString(R.string.registered_) + user.getFirstName(), context);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while adding new user");
                    ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + user.getFirstName(), context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Network error while adding new user: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + user.getFirstName(), context);
            }
        });
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
                    Log.i(TAG, "Changed mail successfully");
                    ToastUtil.makeToast(context.getString(R.string.changed_mail_successfully), context);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while changing mail");
                    ToastUtil.makeToast(context.getString(R.string.error_changing_mail), context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Network error while changing mail: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_changing_mail), context);
            }
        });
    }
}
