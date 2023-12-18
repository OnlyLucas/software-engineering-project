package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.PaymentParticipationService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.PaymentParticipation;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentParticipationRepository {

    private MutableLiveData<List<Object[]>> getPaymentParticipationLiveData = new MutableLiveData<>();
    private PaymentParticipationService paymentParticipationService;


    public PaymentParticipationRepository(){
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
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        System.out.println("Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }
                    ToastUtil.makeToast("Error while adding payment participation ", context);
                }
            }

            @Override
            public void onFailure(Call<PaymentParticipation> call, Throwable t) {
                ToastUtil.makeToast("Error while adding payment participation", context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

    public void fetchPaymentParticipationsForPaidUser(UUID groupId, UUID userId) {
        Call<List<Object[]>> call = paymentParticipationService.findByGroupIdAndPaymentCreatedByUserId(groupId, userId);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    List<Object[]> paymentParticipations = response.body();
                    getPaymentParticipationLiveData.setValue(paymentParticipations);
                    System.out.println("Payment Participation fetching successful");
                } else {
                    // If unauthorized/bad credentials return to login screen
//                    if(response.code() == 401){
//                        System.out.println("Bad credentials. Rerouting to login activity.");
//                        ToastUtil.makeToast("Error with authentication. You need to login again.", context);
//                        UILoaderUtil.startLoginActivity(context);
//                        return;
//                    }
                    System.out.println("Error while fetching payment participations");
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                System.out.println("Network error while fetching payment participations");
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

    public LiveData<List<Object[]>> getPaymentParticipationsForPaidUser(UUID groupId, UUID userId) {
        fetchPaymentParticipationsForPaidUser(groupId, userId);
        return getPaymentParticipationLiveData;
    }
}
