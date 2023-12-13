package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.PaymentParticipationService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.PaymentParticipation;
import com.example.software_engineering_project.util.ToastUtil;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentParticipationRepository {

    private MutableLiveData<List<Object[]>> getPaymentParticipationLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Object[]>> owePaymentParticipationLiveData = new MutableLiveData<>();
    private PaymentParticipationService paymentParticipationService;


    public PaymentParticipationRepository() {
        // Initialize Retrofit service
        paymentParticipationService = RetrofitClient.getInstance().create(PaymentParticipationService.class);

    }

    public void createPaymentParticipation(PaymentParticipation paymentParticipation, Context context) {
        Call<PaymentParticipation> call = paymentParticipationService.createPaymentParticipation(paymentParticipation);
        call.enqueue(new Callback<PaymentParticipation>() {
            @Override
            public void onResponse(Call<PaymentParticipation> call, Response<PaymentParticipation> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    ToastUtil.makeToast("Success", context);
                } else {
                    ToastUtil.makeToast("Error while adding payment participation ", context);
                }
            }

            @Override
            public void onFailure(Call<PaymentParticipation> call, Throwable t) {
                ToastUtil.makeToast("Error while adding payment participation", context);
            }
        });
    }

    public void fetchGetPaymentsGroupedByUser(UUID groupId, UUID userId) {
        Call<List<Object[]>> call = paymentParticipationService.getGetPaymentsGroupedByUser(groupId, userId);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    List<Object[]> paymentParticipations = response.body();
                    getPaymentParticipationLiveData.setValue(paymentParticipations);
                    System.out.println("Get Payments fetching successful");
                } else {
                    System.out.println("Error while fetching Get Payments");
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                System.out.println("Network error while fetching Get Payments");
            }
        });
    }

    public void fetchOwePaymentsGroupedByUser(UUID groupId, UUID userId) {
        Call<List<Object[]>> call = paymentParticipationService.getOwePaymentsGroupedByUser(groupId, userId);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    List<Object[]> paymentParticipations = response.body();
                    owePaymentParticipationLiveData.setValue(paymentParticipations);
                    System.out.println("Owe Payments fetching successful");
                } else {
                    System.out.println("Error while fetching Owe Payments");
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                System.out.println("Network error while fetching Owe Payments");
            }
        });
    }

    public LiveData<List<Object[]>> getGetPaymentsGroupedByUser(UUID groupId, UUID userId) {
        fetchGetPaymentsGroupedByUser(groupId, userId);
        return getPaymentParticipationLiveData;
    }

    public LiveData<List<Object[]>> getOwePaymentsGroupedByUser(UUID groupId, UUID userId) {
        fetchOwePaymentsGroupedByUser(groupId, userId);
        return owePaymentParticipationLiveData;
    }
}
