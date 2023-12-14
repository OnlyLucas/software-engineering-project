package com.example.software_engineering_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.CleaningService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Cleaning;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CleaningRepository {
    private CleaningService cleaningService;
    private MutableLiveData<List<Cleaning>> uncompletedCleanings = new MutableLiveData<>();

    public CleaningRepository(){
        cleaningService = RetrofitClient.getInstance().create(CleaningService.class);
    }

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

    public LiveData<List<Cleaning>> getUncompletedCleanings(UUID id) {
        fetchUncompletedCleanings(id);
        return uncompletedCleanings;
    }
}
