package com.example.software_engineering_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.GroupGroceryService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.GroupGrocery;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroceryRepository {

    private GroupGroceryService groceryService;
    private MutableLiveData<List<GroupGrocery>> groupGroceries = new MutableLiveData<>();

    public GroceryRepository() {
        // Initialize your Retrofit service (you should have a Retrofit setup for your API)
        groceryService = RetrofitClient.getInstance().create(GroupGroceryService.class);
        // Fetch group groceries immediately upon repository creation
        fetchGroupGroceries();
    }

    public LiveData<List<GroupGrocery>> getGroupGroceries() {
        return groupGroceries;
    }

    public void insertGroupGrocery(GroupGrocery groupGrocery) {
        // Perform the API call to insert a new group grocery
        Call<GroupGrocery> call = groceryService.createGroupGroceryCall(groupGrocery);
        call.enqueue(new Callback<GroupGrocery>() {
            @Override
            public void onResponse(Call<GroupGrocery> call, Response<GroupGrocery> response) {
                if(response.isSuccessful()){
                    groupGroceries.getValue().add(groupGrocery);
                }
            }

            @Override
            public void onFailure(Call<GroupGrocery> call, Throwable t) {
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }


    // TODO implement properly with API call
    public void deleteGroupGrocery(GroupGrocery groupGrocery) {
        try{
            groupGroceries.getValue().remove(groupGrocery);
        } catch (NullPointerException e){

        }
    }

    private void fetchGroupGroceries() {
        // Get current group id
        UUID currentGroupId = UserViewModel.getCurrentGroup().getValue().getId();

        // Perform the API call to fetch group groceries asynchronously
        Call<List<GroupGrocery>> call = groceryService.getGroupGroceries(currentGroupId);
        call.enqueue(new Callback<List<GroupGrocery>>() {
            @Override
            public void onResponse(Call<List<GroupGrocery>> call, Response<List<GroupGrocery>> response) {
                if (response.isSuccessful()) {
                    List<GroupGrocery> groceries = response.body();
                    groupGroceries.setValue(groceries);
                }
            }

            @Override
            public void onFailure(Call<List<GroupGrocery>> call, Throwable t) {
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }
}