package com.example.software_engineering_project.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.CleaningTemplateService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;

import java.util.List;

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
    public CleaningTemplateRepository(Context context) {
        cleaningTemplateService = RetrofitClient.getInstance().create(CleaningTemplateService.class);
        fetchCleaningTemplates(context);
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
                    fetchCleaningTemplates(context);
                } else {
                    Log.i(TAG, "Error while cleaning template creation");
                    ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + cleaningTemplate.getName(), context);

                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    ToastUtil.makeToast("Error while adding  " + cleaningTemplate.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<CleaningTemplate> call, Throwable t) {
                Log.i(TAG, "Network error while cleaning template creation: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + cleaningTemplate.getName(), context);
            }
        });
    }

    /**
     * Fetches the list of current cleaning templates from the server asynchronously.
     * Updates the LiveData with the retrieved list upon a successful API response.
     */
    public void fetchCleaningTemplates(Context context) {
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
        if(group == null){
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        Call<List<CleaningTemplate>> call = cleaningTemplateService.getCleaningTemplates(group.getId());
        call.enqueue(new Callback<List<CleaningTemplate>>(){
            @Override
            public void onResponse(Call<List<CleaningTemplate>> call, Response<List<CleaningTemplate>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Cleaning template fetching successful");
                    List<CleaningTemplate> cleaningTemplates = response.body();
                    currentCleaningTemplates.setValue(cleaningTemplates);
                } else {

                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    // Handle API error
                    Log.e(TAG, "Error while fetching cleaning templates");
                }
            }

            @Override
            public void onFailure(Call<List<CleaningTemplate>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching cleaning templates: " + t);
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
        try {
            Call<Void> call = cleaningTemplateService.deleteCleaningTemplate(cleaningTemplate.getId());
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Deletion of Cleaning Template successful");
                        fetchCleaningTemplates(context);
                        ToastUtil.makeToast(context.getString(R.string.removed) + cleaningTemplate.getName(), context);
                    } else {
                        if(response.code() == 401){
                            Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                            ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                            UILoaderUtil.startLoginActivity(context);
                            return;
                        }

                        Log.e(TAG, "Failed to delete cleaning template on the server");
                        ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Network error while deleting cleaning template: " + t);
                    ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                }

            });
        } catch (NullPointerException e) {
            // Handle the case where groupGroceries or groupGroceries.getValue() is null
        }
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
