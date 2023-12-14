package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.CleaningTemplateService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.util.ToastUtil;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CleaningTemplateRepository {
    private CleaningTemplateService cleaningTemplateService;
    private MutableLiveData<List<CleaningTemplate>> currentCleaningTemplates = new MutableLiveData<>();

    public CleaningTemplateRepository() {
        // Initialize Retrofit service
        cleaningTemplateService = RetrofitClient.getInstance().create(CleaningTemplateService.class);
        fetchCleaningTemplates();
    }

    public void createCleaningTemplate(CleaningTemplate cleaningTemplate, Context context) {
        Call<CleaningTemplate> call = cleaningTemplateService.createCleaningTemplateWithCleanings(cleaningTemplate);
        call.enqueue(new Callback<CleaningTemplate>() {
            // TODO Add internationalization
            @Override
            public void onResponse(Call<CleaningTemplate> call, Response<CleaningTemplate> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    ToastUtil.makeToast("Added " + cleaningTemplate.getName(), context);
                    fetchCleaningTemplates();
                } else {
                    ToastUtil.makeToast("Error while adding  " + cleaningTemplate.getName(), context);
                    fetchCleaningTemplates();
                }
            }

            @Override
            public void onFailure(Call<CleaningTemplate> call, Throwable t) {
                ToastUtil.makeToast("Error while adding  " + cleaningTemplate.getName(), context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

    public void fetchCleaningTemplates() {

        UUID currentGroupId = UserViewModel.getCurrentGroup().getValue().getId();

        // Perform the API call to get users asynchronously
        Call<List<CleaningTemplate>> call = cleaningTemplateService.getCleaningTemplates(currentGroupId);
        call.enqueue(new Callback<List<CleaningTemplate>>(){
            @Override
            public void onResponse(Call<List<CleaningTemplate>> call, Response<List<CleaningTemplate>> response) {
                if (response.isSuccessful()) {
                    List<CleaningTemplate> cleaningTemplates = response.body();
                    currentCleaningTemplates.setValue(cleaningTemplates);
                    System.out.println("Cleaning template fetching successful");
                } else {
                    System.out.println("Error while fetching cleaning templates");
                }
            }

            @Override
            public void onFailure(Call<List<CleaningTemplate>> call, Throwable t) {
                // Handle network failure
                System.out.println("Network error while fetching cleaning templates");
            }
        });
    }

    public LiveData<List<CleaningTemplate>> getCurrentCleaningTemplates() {
        return currentCleaningTemplates;
    }

    public void deleteCleaningTemplate(CleaningTemplate cleaningTemplate, Context context) {
        try {
            // Perform the API call to delete the group grocery on the server
            Call<Void> call = cleaningTemplateService.deleteCleaningTemplate(cleaningTemplate.getId());
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Get updated Group Groceries from backend to show it in frontend
                        fetchCleaningTemplates();
                        System.out.println("Deletion of Cleaning Template successful");
                        ToastUtil.makeToast("Removed: " + cleaningTemplate.getName(), context);
                    } else {
                        // If the server-side deletion is not successful, handle accordingly
                        // For example, show an error message
                        fetchCleaningTemplates();
                        System.out.println(response.code());
                        System.out.println("Failed to delete cleaning template on the server");

                        System.out.println(response.body().toString());

                        String errorMessage = "Failed to delete cleaning template on the server";
                        ToastUtil.makeToast("Deletion failed", context);
                        // Handle the error message appropriately
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Handle the failure of the API call (e.g., network issues)
                    String errorMessage = "Failed to delete cleaning template. Check your network connection.";
                    ToastUtil.makeToast("Deletion failed", context);
                    // Handle the error message appropriately
                }


            });
        } catch (NullPointerException e) {
            // Handle the case where groupGroceries or groupGroceries.getValue() is null
        }
    }
}
