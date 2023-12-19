package com.example.software_engineering_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.GroupMembershipService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.GroupMembership;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The GroupMembershipRepository class is responsible for managing interactions between the app's data
 * and the GroupMembershipService, which communicates with the server to perform operations related to
 * group memberships. It uses Retrofit for network communication and interacts with the UserRepository
 * to update user information upon successful operations.
 */
public class GroupMembershipRepository {
    private static final String TAG = GroupMembershipRepository.class.getSimpleName();
    private MutableLiveData<Group> groupByUserId = new MutableLiveData<>();

    private GroupMembershipService service;

    /**
     * Default constructor for GroupMembershipRepository. Initializes the GroupMembershipService using RetrofitClient.
     */
    public GroupMembershipRepository() {
        service = RetrofitClient.getInstance().create(GroupMembershipService.class);
    }

    /**
     * Deletes a user's group membership on the server and updates the list of users upon success.
     *
     * @param user           The User object whose group membership needs to be deleted.
     * @param userRepository The UserRepository to update the list of users.
     * @param context        The application context for displaying toasts and handling UI updates.
     */
    public void deleteGroupMembership(User user, UserRepository userRepository, Context context) {
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();

        Call<Void> call = service.deleteGroupMembership(user.getId(), group.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Deletion of group membership successful");
                    userRepository.fetchUsers(context);
                    User currentAppUser = AppStateRepository.getCurrentAppUserLiveData().getValue();
                    if(currentAppUser.getId().equals(user.getId())){
                        AppStateRepository.setCurrentGroup(null);
                    }
                    ToastUtil.makeToast(context.getString(R.string.removed) + user.getFirstName(), context);
                } else {
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Failed to delete group membership on the server");
                    ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Network error while deleting group membership: " + t);
                ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
            }
        });
    }

    /**
     * Inserts a new group membership on the server and updates the list of users upon success.
     *
     * @param groupMembership The GroupMembership object to be inserted.
     * @param userRepository   The UserRepository to update the list of users.
     * @param context          The application context for displaying toasts and handling UI updates.
     */
    public void insertGroupMembership(GroupMembership groupMembership, UserRepository userRepository, Context context) {
        Call<GroupMembership> call = service.createGroupMembership(groupMembership);
        call.enqueue(new Callback<GroupMembership>() {
            @Override
            public void onResponse(Call<GroupMembership> call, Response<GroupMembership> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Group membership creation successful");
                    userRepository.fetchUsers(context);
                    ToastUtil.makeToast(context.getString(R.string.added) + groupMembership.getUser().getFirstName(), context);
                } else {
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while group membership creation");
                    ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + groupMembership.getUser().getFirstName(), context);
                }
            }

            @Override
            public void onFailure(Call<GroupMembership> call, Throwable t) {
                Log.e(TAG, "Network error while group membership creation: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + groupMembership.getUser().getFirstName(), context);
            }
        });
    }

    /**
     * Fetches the group for a given user from the server.
     *
     * @param userId   The ID of the user for whom to fetch the group.
     * @param context
     */
    public void setGroupByUserId(UUID userId, Context context) {
        Call<Group> call = service.getGroupByUserId(userId);
        call.enqueue(new Callback<Group>(){
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Group fetching by userId successful");
                    Group group = response.body();
                    AppStateRepository.setCurrentGroup(group);
                    groupByUserId.setValue(group);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG,"Error while fetching group by userId");
                    groupByUserId.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Log.e(TAG, "Network error while fetching group by userId: " + t);
            }
        });
    }
}


