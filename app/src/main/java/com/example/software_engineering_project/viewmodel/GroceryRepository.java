package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.GroupGroceryService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.GroupGrocery;
import com.example.software_engineering_project.util.ToastUtil;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The GroceryRepository class is responsible for managing interactions between the app's data
 * and the GroupGroceryService, which communicates with the server to perform CRUD operations on Group Groceries.
 * It uses Retrofit for network communication and LiveData for observing changes in the list of group groceries.
 */
public class GroceryRepository {

    private GroupGroceryService groceryService;
    private MutableLiveData<List<GroupGrocery>> uncompletedGroupGroceries = new MutableLiveData<>();
    private MutableLiveData<List<GroupGrocery>> completedGroupGroceries = new MutableLiveData<>();

    /**
     * Default constructor for GroceryRepository. Initializes the GroupGroceryService using RetrofitClient
     * and fetches the group groceries immediately upon repository creation.
     */
    public GroceryRepository() {
        // Initialize Retrofit service
        groceryService = RetrofitClient.getInstance().create(GroupGroceryService.class);
        // Fetch group groceries immediately upon repository creation
        fetchGroupGroceries();
    }

    /**
     * Retrieves the LiveData object containing the list of completed group groceries.
     *
     * @return LiveData<List<GroupGrocery>> The LiveData object containing the list of completed group groceries.
     */
    public LiveData<List<GroupGrocery>> getCompletedGroupGroceries() {
        return completedGroupGroceries;
    }

    /**
     * Retrieves the LiveData object containing the list of uncompleted group groceries.
     *
     * @return LiveData<List<GroupGrocery>> The LiveData object containing the list of uncompleted group groceries.
     */
    public LiveData<List<GroupGrocery>> getUncompletedGroupGroceries() {
        return uncompletedGroupGroceries;
    }

    /**
     * Performs the API call to insert a new group grocery on the server and updates the list of uncompleted group groceries upon success.
     *
     * @param groupGrocery The GroupGrocery object to be inserted.
     * @param context      The application context for displaying toasts and handling UI updates.
     */
    public void insertGroupGrocery(GroupGrocery groupGrocery, Context context) {
        // Perform the API call to insert a new group grocery
        Call<GroupGrocery> call = groceryService.createGroupGrocery(groupGrocery);
        call.enqueue(new Callback<GroupGrocery>() {
            @Override
            public void onResponse(Call<GroupGrocery> call, Response<GroupGrocery> response) {
                if(response.isSuccessful()){
                    uncompletedGroupGroceries.getValue().add(groupGrocery);
                    fetchUncompletedGroupGroceries();
                    // show toast of success
                    ToastUtil.makeToast("Added " + groupGrocery.getName(), context);
                } else {
                    ToastUtil.makeToast("Error while adding  " + groupGrocery.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<GroupGrocery> call, Throwable t) {
                ToastUtil.makeToast("Error while adding  " + groupGrocery.getName(), context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

    /**
     * Performs the API call to delete a group grocery on the server and updates the list of uncompleted group groceries upon success.
     *
     * @param groupGrocery The GroupGrocery object to be deleted.
     * @param context      The application context for displaying toasts and handling UI updates.
     */
    public void deleteGroupGrocery(GroupGrocery groupGrocery, Context context) {
        try {
            // Perform the API call to delete the group grocery on the server
            Call<Void> call = groceryService.deleteGroupGrocery(groupGrocery.getId());
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Get updated Group Groceries from backend to show it in frontend
                        fetchGroupGroceries();
                        System.out.println("Deletion of Group Grocery successful");
                        ToastUtil.makeToast("Removed: " + groupGrocery.getName(), context);
                    } else {
                        // If the server-side deletion is not successful, handle accordingly
                        // For example, show an error message
                        System.out.println(response.code());
                        System.out.println("Failed to delete group grocery on the server");

                        System.out.println(response.body().toString());

                        String errorMessage = "Failed to delete group grocery on the server";
                        ToastUtil.makeToast("Deletion failed", context);
                        // Handle the error message appropriately
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Handle the failure of the API call (e.g., network issues)
                    String errorMessage = "Failed to delete group grocery. Check your network connection.";
                    ToastUtil.makeToast("Deletion failed", context);
                    // Handle the error message appropriately
                }


            });
        } catch (NullPointerException e) {
            // Handle the case where groupGroceries or groupGroceries.getValue() is null
        }
    }

    /**
     * Performs the API call to update a group grocery on the server and refreshes the list of group groceries upon success.
     *
     * @param grocery The GroupGrocery object to be updated.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void updateGroupGrocery(GroupGrocery grocery, Context context) {
        // Perform the API call to update group grocery asynchronously
        Call<GroupGrocery> call = groceryService.updateGroupGrocery(grocery.getId(), grocery);
        call.enqueue(new Callback<GroupGrocery>() {
            @Override
            public void onResponse(Call<GroupGrocery> call, Response<GroupGrocery> response) {
                if(response.isSuccessful()){
                    fetchGroupGroceries();
                    // show toast of success
                    ToastUtil.makeToast("Unchecked " + grocery.getName(), context);
                } else {
                    ToastUtil.makeToast("Error while unchecking  " + grocery.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<GroupGrocery> call, Throwable t) {
                ToastUtil.makeToast("Error while unchecking  " + grocery.getName(), context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

    private void fetchUncompletedGroupGroceries() {
        // Get current group id
        UUID currentGroupId = UserViewModel.getCurrentGroup().getValue().getId();

        // Perform the API call to fetch group groceries asynchronously
        Call<List<GroupGrocery>> call = groceryService.getUncompletedGroupGroceries(currentGroupId);
        call.enqueue(new Callback<List<GroupGrocery>>() {
            @Override
            public void onResponse(Call<List<GroupGrocery>> call, Response<List<GroupGrocery>> response) {
                if (response.isSuccessful()) {
                    List<GroupGrocery> groceries = response.body();
                    uncompletedGroupGroceries.setValue(groceries);
                    System.out.println("Uncompleted group groceries fetching successful");
                } else {
                System.out.println("Error while fetching uncompleted group groceries");
            }
            }

            @Override
            public void onFailure(Call<List<GroupGrocery>> call, Throwable t) {
                // Handle the failure if needed
                // For example, show an error message
                System.out.println("Failure while fetching uncompleted group groceries" + t);
            }
        });
    }

    private void fetchCompletedGroupGroceries() {
        // Get current group id
        UUID currentGroupId = UserViewModel.getCurrentGroup().getValue().getId();

        // Perform the API call to fetch group groceries asynchronously
        Call<List<GroupGrocery>> call = groceryService.getCompletedGroupGroceries(currentGroupId);
        call.enqueue(new Callback<List<GroupGrocery>>() {
            @Override
            public void onResponse(Call<List<GroupGrocery>> call, Response<List<GroupGrocery>> response) {
                if (response.isSuccessful()) {
                    List<GroupGrocery> groceries = response.body();
                    completedGroupGroceries.setValue(groceries);
                    System.out.println("Group Grocery fetching successful");
                }
            }

            @Override
            public void onFailure(Call<List<GroupGrocery>> call, Throwable t) {
                // Handle the failure if needed
                // For example, show an error message
                System.out.println("Failure while fetching completed group groceries" + t);
            }
        });
    }

    /**
     * Fetches both uncompleted and completed group groceries from the server.
     */
    public void fetchGroupGroceries(){
        fetchUncompletedGroupGroceries();
        fetchCompletedGroupGroceries();
    }
}