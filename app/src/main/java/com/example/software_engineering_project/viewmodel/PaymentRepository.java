package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.PaymentService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.entity.PaymentCreationData;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;


import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository {

    private PaymentService paymentService;
    private MutableLiveData<List<Payment>> currentPayments = new MutableLiveData<>();

    public PaymentRepository(){
        // Initialize Retrofit service
        paymentService = RetrofitClient.getInstance().create(PaymentService.class);

        getPayments();
    }

    private void getPayments() {
        // Perform the API call to get users asynchronously
        // TODO nullcheck for group --> if no group, then make toast: "Join a group/Not in a group"
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
        Call<List<Payment>> call = paymentService.getPayments(group.getId());
        call.enqueue(new Callback<List<Payment>>(){
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    List<Payment> payments = response.body();
                    currentPayments.setValue(payments);
                    System.out.println("Payment fetching successful");
                    // Handle the received users, e.g., update UI or store in a local database
                } else {
                    // If unauthorized/bad credentials return to login screen
//                    if(response.code() == 401){
//                        System.out.println("Bad credentials. Rerouting to login activity.");
//                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
//                        UILoaderUtil.startLoginActivity(context);
//                        return;
//                    }

                    // Handle API error
                    System.out.println("Error while fetching payments");
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                // Handle network failure
                System.out.println("Network error while fetching payments");
            }
        });
    }

    public void createPayment(PaymentCreationData paymentData , Context context) {
        Call<Payment> call = paymentService.createPayment(paymentData);
        System.out.println("Request body for payment: " + call.request().body().toString());
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    ToastUtil.makeToast("Added " + paymentData.getPayment().getName(), context);
                    fetchPayments();
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        System.out.println("Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }
                    ToastUtil.makeToast("Error while adding payment " + paymentData.getPayment().getName(), context);
                    System.out.println(response.code());
                    System.out.println(call);
                    System.out.println(call.request().body().toString());
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                ToastUtil.makeToast("Error while adding payment. " + paymentData.getPayment().getName(), context);
                System.out.println("Payment creation error: " + call.request().toString());
                // For example, show an error message
            }
        });
    }

    public LiveData<List<Payment>> getCurrentPayments() {
        return currentPayments;
    }

    public void fetchPayments(){
        // Get current group id
        UUID currentGroupId = AppStateRepository.getCurrentGroupLiveData().getValue().getId();

        // Perform the API call to fetch group groceries asynchronously
        Call<List<Payment>> call = paymentService.getPayments(currentGroupId);
        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    List<Payment> payments = response.body();
                    currentPayments.setValue(payments);
                    System.out.println("Payment fetching successful");
                } else {
                    // If unauthorized/bad credentials return to login screen
//                    if(response.code() == 401){
//                        System.out.println("Bad credentials. Rerouting to login activity.");
//                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
//                        UILoaderUtil.startLoginActivity(context);
//                        return;
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                // Handle the failure if needed
                // For example, show an error message
                System.out.println("Failure while fetching payments. " + t);
            }
        });
    }
}
