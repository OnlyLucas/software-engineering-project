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


import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository {

    private PaymentService paymentService;
    private MutableLiveData<List<Payment>> currentPayments = new MutableLiveData<>();

    public PaymentRepository() {
        // Initialize Retrofit service
        paymentService = RetrofitClient.getInstance().create(PaymentService.class);

        getPayments();
    }

    private void getPayments() {
        // Perform the API call to get users asynchronously
        Group group = UserViewModel.getCurrentGroup().getValue();
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
        UUID currentGroupId = UserViewModel.getCurrentGroup().getValue().getId();

        // Perform the API call to fetch group groceries asynchronously
        Call<List<Payment>> call = paymentService.getPayments(currentGroupId);
        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    List<Payment> payments = response.body();
                    currentPayments.setValue(payments);
                    System.out.println("Payment fetching successful");
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

    public void deletePayment(Payment payment, Context context) {
            // Perform the API call to delete the group grocery on the server
            Call<Void> call = paymentService.deletePayment(payment.getId());
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Get updated Group Groceries from backend to show it in frontend
                        fetchPayments();
                        System.out.println("Deletion of Payment successful");
                        ToastUtil.makeToast("Removed: " + payment.getName(), context);
                    } else {
                        // If the server-side deletion is not successful, handle accordingly
                        // For example, show an error message
                        fetchPayments();

                        System.out.println(response.code());
                        System.out.println("Failed to delete payment on the server");

                        String errorMessage = "Failed to delete payment on the server";
                        ToastUtil.makeToast("Deletion failed", context);
                        // Handle the error message appropriately
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Handle the failure of the API call (e.g., network issues)
                    String errorMessage = "Failed to delete payment. Check your network connection.";
                    ToastUtil.makeToast("Deletion failed", context);
                    // Handle the error message appropriately
                }

            });
        };
}
