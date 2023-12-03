package com.example.software_engineering_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ApiPaths;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

public class EntityViewModel<T> extends ViewModel {
//    private MutableLiveData<T> data = new MutableLiveData<>();
//    private final Class<T> entityClass;
//    private final EntityService entityService;
//    private final String urlPath;
//
//    public EntityViewModel(Class<T> entityClass){
//        this.entityClass = entityClass;
//        ApiClient apiClient = new ApiClient();
//        entityService = apiClient.getEntityService(entityClass);
//        urlPath = ApiPaths.getUrlPathByClass(entityClass);
//    }
//
//    public LiveData<T> getData() {
//        return data;
//    }
//
//    public void fetchData(String id) {
//        new Thread(() -> {
//
//            Call<T> entityCall = entityService.getEntity(urlPath, id);
//
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                Response<T> entityResponse = entityCall.execute();
//                if (entityResponse.isSuccessful()) {
//                    T entity = entityResponse.body();
//                    System.out.println("Entity: " + entity);
//
//                    // Update Data
//                    data.postValue(entity);
//                } else {
//                    System.err.println("Failed to get entity. Error code: " + entityResponse.code());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//
////            // Example: Create a new user
////            User newUser = new User();
////            // Set user properties...
////            Call<User> createUserCall = entityCall.(newUser);
//
////            try {
////                Response<User> createUserResponse = createUserCall.execute();
////                if (createUserResponse.isSuccessful()) {
////                    User createdUser = createUserResponse.body();
////                    System.out.println("Created User: " + createdUser);
////                } else {
////                    System.err.println("Failed to create user. Error code: " + createUserResponse.code());
////                }
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////            // Update LiveData with the result
////            data.postValue();
//        }).start();
//    }
}
