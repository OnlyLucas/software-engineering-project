package com.example.software_engineering_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
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
    private static final String TAG = CleaningTemplateRepository.class.getSimpleName();
    private CleaningTemplateService cleaningTemplateService;
    private MutableLiveData<List<CleaningTemplate>> currentCleaningTemplates = new MutableLiveData<>();

    /**
     * Default constructor for CleaningTemplateRepository. Initializes the CleaningTemplateService using RetrofitClient
     * and fetches the current list of cleaning templates from the server.
     */
    public CleaningTemplateRepository() {
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
            @Override
            public void onResponse(Call<CleaningTemplate> call, Response<CleaningTemplate> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Cleaning template creation successful");
                    ToastUtil.makeToast(context.getString(R.string.added) + cleaningTemplate.getName(), context);
                    fetchCleaningTemplates();
                } else {
                    Log.i(TAG, "Error while cleaning template creation");
                    ToastUtil.makeToast(context.getString(R.string.error_while_adding) + cleaningTemplate.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<CleaningTemplate> call, Throwable t) {
                Log.i(TAG, "Network error while cleaning template creation");
                ToastUtil.makeToast(context.getString(R.string.error_while_adding) + cleaningTemplate.getName(), context);
            }
        });
    }

    /**
     * Fetches the list of current cleaning templates from the server asynchronously.
     * Updates the LiveData with the retrieved list upon a successful API response.
     */
    public void fetchCleaningTemplates() {

        UUID currentGroupId = UserViewModel.getCurrentGroup().getValue().getId();

        Call<List<CleaningTemplate>> call = cleaningTemplateService.getCleaningTemplates(currentGroupId);
        call.enqueue(new Callback<List<CleaningTemplate>>(){
            @Override
            public void onResponse(Call<List<CleaningTemplate>> call, Response<List<CleaningTemplate>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Cleaning template fetching successful");
                    List<CleaningTemplate> cleaningTemplates = response.body();
                    currentCleaningTemplates.setValue(cleaningTemplates);
                } else {
                    Log.e(TAG, "Error while fetching cleaning templates");
                }
            }

            @Override
            public void onFailure(Call<List<CleaningTemplate>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching cleaning templates");
            }
        });
    }

    /**
     * Deletes a cleaning template from the server and updates the list of current cleaning templates upon success.
     *
     * @param cleaningTemplate The CleaningTemplate object to be deleted.
     * @param context          The application context for displaying toasts and handling UI updates.
     */
    public void deleteCleaningTemplate(CleaningTemplate cleaningTemplate, Context context) {
        Call<Void> call = cleaningTemplateService.deleteCleaningTemplate(cleaningTemplate.getId());
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Deletion of Cleaning Template successful");
                    fetchCleaningTemplates();
                    ToastUtil.makeToast(context.getString(R.string.removed) + cleaningTemplate.getName(), context);
                } else {
                    Log.e(TAG, "Failed to delete cleaning template on the server");
                    ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Network error while deleting cleaning template");
                ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
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
}
