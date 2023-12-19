package com.example.software_engineering_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.PaymentService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.entity.PaymentCreationData;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;


import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The PaymentRepository class is responsible for managing interactions between the app's data
 * and the PaymentService, which communicates with the server to perform CRUD operations on Payments.
 * It uses Retrofit for network communication and LiveData for observing changes in the list of current payments.
 */
public class PaymentRepository {
    private static final String TAG = PaymentRepository.class.getSimpleName();
    private PaymentService paymentService;
    private MutableLiveData<List<Payment>> currentPayments = new MutableLiveData<>();

    /**
     * Default constructor for PaymentRepository. Initializes the PaymentService using RetrofitClient
     * and fetches the current list of payments immediately upon repository creation.
     */
    public PaymentRepository(Context context) {
        paymentService = RetrofitClient.getInstance().create(PaymentService.class);
        fetchPayments(context);
    }

    /**
     * Creates a new payment on the server and updates the list of payments upon success.
     *
     * @param paymentData The PaymentCreationData object containing payment information.
     * @param context     The application context for displaying toasts and handling UI updates.
     */
    public void createPayment(PaymentCreationData paymentData , Context context) {
        Call<Payment> call = paymentService.createPayment(paymentData);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Payment creation successful");
                    fetchPayments(context);
                    ToastUtil.makeToast("Added " + paymentData.getPayment().getName(), context);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while creating payment");
                    ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + paymentData.getPayment().getName(), context);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Log.e(TAG, "Network error while creating payment: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_while_adding_) + paymentData.getPayment().getName(), context);
            }
        });
    }

    /**
     * Deletes a payment from the server and updates the list of payments upon success.
     *
     * @param payment The Payment object to be deleted.
     * @param context The application context for displaying toasts and handling UI updates.
     */
    public void deletePayment(Payment payment, Context context) {
        Call<Void> call = paymentService.deletePayment(payment.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Deletion of Payment successful");
                    fetchPayments(context);
                    ToastUtil.makeToast(context.getString(R.string.removed) + payment.getName(), context);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Failed to delete payment on the server");
                    ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Network error while deleting payment: " + t);
                ToastUtil.makeToast(context.getString(R.string.deletion_failed), context);
            }

        });
    };

    /**
     * Fetches the list of payments from the server asynchronously.
     * Updates the LiveData with the retrieved list upon a successful API response.
     */
    public void fetchPayments(Context context){
        UUID currentGroupId = AppStateRepository.getCurrentGroupLiveData().getValue().getId();
        if(currentGroupId == null){
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        Call<List<Payment>> call = paymentService.getPayments(currentGroupId);
        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Payment fetching successful");
                    List<Payment> payments = response.body();
                    currentPayments.setValue(payments);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while fetching payments");
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching payments: " + t);
            }
        });
    }

    /**
     * Retrieves the LiveData object containing the list of current payments.
     *
     * @return LiveData<List<Payment>> The LiveData object containing the list of current payments.
     */
    public LiveData<List<Payment>> getCurrentPayments() {
        return currentPayments;
    }


}
