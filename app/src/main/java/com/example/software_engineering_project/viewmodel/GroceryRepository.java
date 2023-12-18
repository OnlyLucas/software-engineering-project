package com.example.software_engineering_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.GroupGroceryService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.GroupGrocery;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;

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

    private static final String TAG = GroceryRepository.class.getSimpleName();
    private GroupGroceryService groceryService;
    private MutableLiveData<List<GroupGrocery>> uncompletedGroupGroceries = new MutableLiveData<>();
    private MutableLiveData<List<GroupGrocery>> completedGroupGroceries = new MutableLiveData<>();

    /**
     * Default constructor for GroceryRepository. Initializes the GroupGroceryService using RetrofitClient
     * and fetches the group groceries immediately upon repository creation.
     */
    public GroceryRepository() {
        groceryService = RetrofitClient.getInstance().create(GroupGroceryService.class);
        fetchGroupGroceries();
    }

    /**
     * Performs the API call to insert a new group grocery on the server and updates the list of uncompleted group groceries upon success.
     *
     * @param groupGrocery The GroupGrocery object to be inserted.
     * @param context      The application context for displaying toasts and handling UI updates.
     */
    public void insertGroupGrocery(GroupGrocery groupGrocery, Context context) {
        Call<GroupGrocery> call = groceryService.createGroupGrocery(groupGrocery);
        call.enqueue(new Callback<GroupGrocery>() {
            @Override
            public void onResponse(Call<GroupGrocery> call, Response<GroupGrocery> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Group grocery creation successful");
                    uncompletedGroupGroceries.getValue().add(groupGrocery);
                    fetchUncompletedGroupGroceries();
                    ToastUtil.makeToast(context.getString(R.string.added) + groupGrocery.getName(), context);
                } else {

                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        System.out.println("Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while group grocery creation");
                    ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + groupGrocery.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<GroupGrocery> call, Throwable t) {
                Log.e(TAG, "Network error while group grocery creation: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + groupGrocery.getName(), context);
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
            Call<Void> call = groceryService.deleteGroupGrocery(groupGrocery.getId());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Deletion of Group Grocery successful");
                        fetchGroupGroceries();
                        ToastUtil.makeToast(context.getString(R.string.removed) + groupGrocery.getName(), context);
                    } else {
                        // If unauthorized/bad credentials return to login screen
                        if (response.code() == 401) {
                            System.out.println("Bad credentials. Rerouting to login activity.");
                            ToastUtil.makeToast("Error with authentication. You need to login again.", context);
                            UILoaderUtil.startLoginActivity(context);
                            return;
                        }

                        Log.e(TAG, "Failed to delete group grocery on the server");
                        ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Network error while deleting group grocery: " + t);
                    ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                }
            });
        } catch (NullPointerException e) {
            // Handle the case where groupGroceries or groupGroceries.getValue() is null
        }
    }

    /**
     * Fetches both uncompleted and completed group groceries from the server.
     */
    public void fetchGroupGroceries(){
        fetchUncompletedGroupGroceries();
        fetchCompletedGroupGroceries();
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
     * Performs the API call to update a group grocery on the server and refreshes the list of group groceries upon success.
     *
     * @param grocery The GroupGrocery object to be updated.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void updateGroupGrocery(GroupGrocery grocery, Context context) {
        Call<GroupGrocery> call = groceryService.updateGroupGrocery(grocery.getId(), grocery);
        call.enqueue(new Callback<GroupGrocery>() {
            @Override
            public void onResponse(Call<GroupGrocery> call, Response<GroupGrocery> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Unchecking group grocery successful");
                    fetchGroupGroceries();
                    ToastUtil.makeToast(context.getString(R.string.uncheckedColon_) + grocery.getName(), context);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        System.out.println("Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while unchecking group grocery");
                    ToastUtil.makeToast(context.getString(R.string.error_while_unchecking) + grocery.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<GroupGrocery> call, Throwable t) {
                Log.e(TAG, "Network error while unchecking group grocery: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_unchecking) + grocery.getName(), context);
            }
        });
    }

    private void fetchUncompletedGroupGroceries() {
        // Get current group id
        UUID currentGroupId = AppStateRepository.getCurrentGroupLiveData().getValue().getId();

        Call<List<GroupGrocery>> call = groceryService.getUncompletedGroupGroceries(currentGroupId);
        call.enqueue(new Callback<List<GroupGrocery>>() {
            @Override
            public void onResponse(Call<List<GroupGrocery>> call, Response<List<GroupGrocery>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Uncompleted group groceries fetching successful");
                    List<GroupGrocery> groceries = response.body();
                    uncompletedGroupGroceries.setValue(groceries);
                } else {
                    // If unauthorized/bad credentials return to login screen
//                    if(response.code() == 401){
//                        System.out.println("Bad credentials. Rerouting to login activity.");
//                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
//                        UILoaderUtil.startLoginActivity(context);
//                        return;
//                    }

                    Log.e(TAG,"Error while fetching uncompleted group groceries");
                }
            }

            @Override
            public void onFailure(Call<List<GroupGrocery>> call, Throwable t) {
                Log.e(TAG,"Network error while fetching uncompleted group groceries: " + t);

            }
        });
    }

    private void fetchCompletedGroupGroceries() {
        UUID currentGroupId = AppStateRepository.getCurrentGroupLiveData().getValue().getId();

        Call<List<GroupGrocery>> call = groceryService.getCompletedGroupGroceries(currentGroupId);
        call.enqueue(new Callback<List<GroupGrocery>>() {
            @Override
            public void onResponse(Call<List<GroupGrocery>> call, Response<List<GroupGrocery>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Completed group groceries fetching successful");
                    List<GroupGrocery> groceries = response.body();
                    completedGroupGroceries.setValue(groceries);
                } else {
                    // If unauthorized/bad credentials return to login screen
//                    if(response.code() == 401){
//                        System.out.println("Bad credentials. Rerouting to login activity.");
//                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
//                        UILoaderUtil.startLoginActivity(context);
//                        return;
//                    }

                    Log.e(TAG,"Error while fetching completed group groceries");
                }
            }

            @Override
            public void onFailure(Call<List<GroupGrocery>> call, Throwable t) {
                Log.e(TAG,"Network error while fetching completed group groceries: " + t);
            }
        });
    }
}