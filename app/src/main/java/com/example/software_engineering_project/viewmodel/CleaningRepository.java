package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.CleaningService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Cleaning;
import com.example.software_engineering_project.util.ToastUtil;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The CleaningRepository class is responsible for managing interactions between the app's data
 * and the CleaningService, which communicates with the server to perform CRUD operations on Cleanings.
 * It uses Retrofit for network communication and LiveData for observing changes in the list of uncompleted cleanings.
 */
public class CleaningRepository {
    private CleaningService cleaningService;
    private MutableLiveData<List<Cleaning>> uncompletedCleanings = new MutableLiveData<>();

    /**
     * Default constructor for CleaningRepository. Initializes the CleaningService using RetrofitClient.
     */
    public CleaningRepository(){
        cleaningService = RetrofitClient.getInstance().create(CleaningService.class);
    }

    /**
     * Fetches the list of uncompleted cleanings from the server asynchronously using the provided cleaning template ID.
     * Updates the LiveData with the retrieved list upon a successful API response.
     *
     * @param id The UUID of the cleaning template to fetch uncompleted cleanings for.
     */
    public void fetchUncompletedCleanings(UUID id) {
        // Perform the API call to get users asynchronously
        Call<List<Cleaning>> call = cleaningService.getUncompletedCleaningsForCleaningTemplate(id);
        call.enqueue(new Callback<List<Cleaning>>(){
            @Override
            public void onResponse(Call<List<Cleaning>> call, Response<List<Cleaning>> response) {
                if (response.isSuccessful()) {
                    List<Cleaning> cleanings = response.body();
                    uncompletedCleanings.setValue(cleanings);
                    System.out.println("Cleaning fetching successful");
                    // Handle the received users, e.g., update UI or store in a local database
                } else {
                    // Handle API error
                    System.out.println("Error while fetching cleanings");
                }
            }

            @Override
            public void onFailure(Call<List<Cleaning>> call, Throwable t) {
                // Handle network failure
                System.out.println("Error: " + t);
                System.out.println("Network error while fetching cleanings");
            }
        });
    }

    /**
     * Retrieves the LiveData object containing the list of uncompleted cleanings.
     * Initiates the fetching of uncompleted cleanings to update the LiveData.
     *
     * @param id The UUID of the cleaning template to fetch uncompleted cleanings for.
     * @return LiveData<List<Cleaning>> The LiveData object containing the list of uncompleted cleanings.
     */
    public LiveData<List<Cleaning>> getUncompletedCleanings(UUID id) {
        fetchUncompletedCleanings(id);
        return uncompletedCleanings;
    }

    /**
     * Deletes a cleaning from the server and updates the list of uncompleted cleanings upon a successful deletion.
     *
     * @param cleaning The Cleaning object to be deleted.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void deleteCleaning(Cleaning cleaning, Context context) {
        // Perform the API call to delete the group grocery on the server
        Call<Void> call = cleaningService.deleteCleaning(cleaning.getId());
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Get updated Group Groceries from backend to show it in frontend
                    fetchUncompletedCleanings(cleaning.getCleaningTemplate().getId());
                    System.out.println("Deletion of Cleaning successful");
                    ToastUtil.makeToast("Removed: " + cleaning.getDate(), context);
                } else {
                    // If the server-side deletion is not successful, handle accordingly
                    // For example, show an error message
                    System.out.println(response.code());
                    System.out.println("Failed to delete cleaning on the server");

                    System.out.println(response.body().toString());

                    String errorMessage = "Failed to delete cleaning on the server";
                    ToastUtil.makeToast("Deletion failed", context);
                    // Handle the error message appropriately
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure of the API call (e.g., network issues)
                String errorMessage = "Failed to delete cleaning. Check your network connection.";
                ToastUtil.makeToast("Deletion failed", context);
                // Handle the error message appropriately
            }

        });
    }

    /**
     * Updates a cleaning on the server and refreshes the list of uncompleted cleanings upon a successful update.
     *
     * @param cleaning The Cleaning object to be updated.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void updateCleaning(Cleaning cleaning, Context context) {
        // Perform the API call to update group grocery asynchronously
        Call<Cleaning> call = cleaningService.updateCleaning(cleaning.getId(), cleaning);
        call.enqueue(new Callback<Cleaning>() {
            @Override
            public void onResponse(Call<Cleaning> call, Response<Cleaning> response) {
                if(response.isSuccessful()){
                    fetchUncompletedCleanings(cleaning.getCleaningTemplate().getId());
                    // show toast of success
                    ToastUtil.makeToast("Unchecked " + cleaning.getDate(), context);
                } else {
                    ToastUtil.makeToast("Error while unchecking  " + cleaning.getDate(), context);
                }
            }

            @Override
            public void onFailure(Call<Cleaning> call, Throwable t) {
                ToastUtil.makeToast("Error while unchecking  " + cleaning.getDate(), context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }
}
