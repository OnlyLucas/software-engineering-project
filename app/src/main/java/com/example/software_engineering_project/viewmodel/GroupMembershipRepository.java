package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import com.example.software_engineering_project.dataservice.GroupMembershipService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMembershipRepository {
    private GroupMembershipService service;


    public GroupMembershipRepository(){
        service = RetrofitClient.getInstance().create(GroupMembershipService.class);

    }

    public void deleteGroupMembership(User user, Group group, Context context) {
        // Perform the API call to delete the group grocery on the server
        Call<Void> call = service.deleteGroupMembership(user.getId(), group.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                UserRepository userRepository = new UserRepository();
                if (response.isSuccessful()) {
                    // Get updated Group Groceries from backend to show it in frontend
                    userRepository.getUsers();
                    System.out.println("Deletion of Group Membership successful");
                    ToastUtil.makeToast("Removed: " + user.getFirstName(), context);
                } else {
                    // If the server-side deletion is not successful, handle accordingly
                    // For example, show an error message
                    userRepository.getUsers();

                    System.out.println(response.code());
                    System.out.println("Failed to delete group membership on the server");

                    System.out.println(response.body().toString());

                    String errorMessage = "Failed to delete group membership on the server";
                    ToastUtil.makeToast("Deletion failed", context);
                    // Handle the error message appropriately
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure of the API call (e.g., network issues)
                String errorMessage = "Failed to delete group membership. Check your network connection.";
                ToastUtil.makeToast("Deletion failed", context);
                // Handle the error message appropriately
            }
        });
    }
}
