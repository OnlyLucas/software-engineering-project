package com.example.software_engineering_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.CleaningService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Cleaning;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;

import java.util.List;
import java.util.UUID;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The CleaningRepository class is responsible for managing interactions between the app's data
 * and the CleaningService, which communicates with the server to perform CRUD operations on Cleanings.
 * It uses Retrofit for network communication and LiveData for observing changes in the list of uncompleted cleanings.
 */
public class CleaningRepository {
    private static final String TAG = CleaningRepository.class.getSimpleName();
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
        Call<List<Cleaning>> call = cleaningService.getUncompletedCleaningsForCleaningTemplate(id);
        call.enqueue(new Callback<List<Cleaning>>(){
            @Override
            public void onResponse(Call<List<Cleaning>> call, Response<List<Cleaning>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Cleaning fetching successful");
                    List<Cleaning> cleanings = response.body();
                    uncompletedCleanings.setValue(cleanings);
                } else {
                    Log.e(TAG, "Error while fetching cleanings");
                    // If unauthorized/bad credentials return to login screen
//                    if(response.code() == 401){
//                        System.out.println("Bad credentials. Rerouting to login activity.");
//                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
//                        UILoaderUtil.startLoginActivity(context);
//                    }
                    // Handle API error
                    System.out.println("Error while fetching cleanings");
                }
            }

            @Override
            public void onFailure(Call<List<Cleaning>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching cleanings: " + t);
            }
        });
    }

    /**
     * Deletes a cleaning from the server and updates the list of uncompleted cleanings upon a successful deletion.
     *
     * @param cleaning The Cleaning object to be deleted.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void deleteCleaning(Cleaning cleaning, Context context) {
        Call<Void> call = cleaningService.deleteCleaning(cleaning.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchUncompletedCleanings(cleaning.getCleaningTemplate().getId());
                    Log.i(TAG, "Deletion of Cleaning successful");
                    ToastUtil.makeToast(context.getString(R.string.removed) + cleaning.getDate(), context);
                } else {
                    System.out.println(response.code());
                    Log.e(TAG, "Failed to delete cleaning on the server");
                    System.out.println(response.body().toString());
                    ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG,"Network error while deleting cleaning: " + t);
                ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
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
     * Updates a cleaning on the server and refreshes the list of uncompleted cleanings upon a successful update.
     *
     * @param cleaning The Cleaning object to be updated.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void updateCleaning(Cleaning cleaning, Context context) {
        Call<Cleaning> call = cleaningService.updateCleaning(cleaning.getId(), cleaning);
        call.enqueue(new Callback<Cleaning>() {
            @Override
            public void onResponse(Call<Cleaning> call, Response<Cleaning> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Unchecking cleaning successful");
                    fetchUncompletedCleanings(cleaning.getCleaningTemplate().getId());
                    ToastUtil.makeToast(context.getString(R.string.uncheckedColon_) + cleaning.getDate(), context);
                } else {
                    Log.e(TAG, "Error while unchecking cleaning");
                    ToastUtil.makeToast(context.getString(R.string.error_while_unchecking) + cleaning.getDate(), context);
                }
            }

            @Override
            public void onFailure(Call<Cleaning> call, Throwable t) {
                Log.e(TAG, "Network error while unchecking cleaning: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_unchecking) + cleaning.getDate(), context);
            }
        });
    }
}
