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

/**
 * The CleaningTemplateRepository class is responsible for managing interactions between the app's data
 * and the CleaningTemplateService, which communicates with the server to perform CRUD operations on Cleaning Templates.
 * It uses Retrofit for network communication and LiveData for observing changes in the list of current cleaning templates.
 */
public class CleaningTemplateRepository {
    private CleaningTemplateService cleaningTemplateService;
    private MutableLiveData<List<CleaningTemplate>> currentCleaningTemplates = new MutableLiveData<>();

    /**
     * Default constructor for CleaningTemplateRepository. Initializes the CleaningTemplateService using RetrofitClient
     * and fetches the current list of cleaning templates from the server.
     */
    public CleaningTemplateRepository() {
        // Initialize Retrofit service
        cleaningTemplateService = RetrofitClient.getInstance().create(CleaningTemplateService.class);
        fetchCleaningTemplates();
    }

    /**
     * Creates a new cleaning template on the server and updates the list of current cleaning templates upon success.
     *
     * @param cleaningTemplate The CleaningTemplate object to be created.
     * @param context          The application context for displaying toasts and handling UI updates.
     */
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

    /**
     * Fetches the list of current cleaning templates from the server asynchronously.
     * Updates the LiveData with the retrieved list upon a successful API response.
     */
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

    /**
     * Retrieves the LiveData object containing the list of current cleaning templates.
     *
     * @return LiveData<List<CleaningTemplate>> The LiveData object containing the list of current cleaning templates.
     */
    public LiveData<List<CleaningTemplate>> getCurrentCleaningTemplates() {
        return currentCleaningTemplates;
    }

    /**
     * Deletes a cleaning template from the server and updates the list of current cleaning templates upon success.
     *
     * @param cleaningTemplate The CleaningTemplate object to be deleted.
     * @param context          The application context for displaying toasts and handling UI updates.
     */
    public void deleteCleaningTemplate(CleaningTemplate cleaningTemplate, Context context) {
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
                    System.out.println(response.code());
                    System.out.println("Failed to delete cleaning template on the server");

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
    }
}
