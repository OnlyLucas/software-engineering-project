package com.example.software_engineering_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.GroupService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.GroupMembership;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupRepository{
    private static final String TAG = GroupRepository.class.getSimpleName();
    private static MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();

    private GroupService groupService;


    /**
     * Default constructor for GroupRepository. Initializes the GroupService using RetrofitClient
     */
    public GroupRepository() {
        groupService = RetrofitClient.getInstance().create(GroupService.class);
    }

    /**
     * Performs the API call to insert a new group  on the server.
     *
     * @param group        The Group object to be inserted.
     * @param context      The application context for displaying toasts and handling UI updates.
     */
    public void insertGroup(Group group, Context context) {
        Call<Group> call = groupService.createGroup(group);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Group creation successful");
                    ToastUtil.makeToast(context.getString(R.string.createdColon_) + group.getName(), context);
                    // TODO set currentGroup and create groupMembership (not working yet)
                    groupMutableLiveData.setValue(group);
                    UserViewModel.setCurrentGroup(groupMutableLiveData);
                    User user = UserViewModel.getCurrentAppUser().getValue();
                    GroupMembership groupMembership = new GroupMembership(user, group);
                    GroupMembershipRepository groupMembershipRepository = new GroupMembershipRepository();
                    UserRepository userRepository = new UserRepository();
                    groupMembershipRepository.insertGroupMembership(groupMembership, userRepository, context);
                } else {
                    Log.e(TAG, "Error while group creation");
                    ToastUtil.makeToast(context.getString(R.string.error_while_creating) + group.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Log.e(TAG, "Network error while group creation: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_creating) + group.getName(), context);
            }
        });
    }
}
