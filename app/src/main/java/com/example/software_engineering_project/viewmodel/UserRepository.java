package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.dataservice.UserService;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private UserService service;

    public UserRepository(){
        service = RetrofitClient.getInstance().create(UserService.class);
    }

    public void updateEmail(User user, Map<String, String> map, Context context){
        Call<User> call = service.partialUpdateEntity(user.getId(), map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    System.out.println("Changed mail successfully");
                    // show toast of success
                    ToastUtil.makeToast("Changed mail successfully", context);
                } else {
                    ToastUtil.makeToast("Error while changing mail", context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ToastUtil.makeToast("Error while changing mail", context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }
}
