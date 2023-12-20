package com.example.software_engineering_project.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
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

/**
 * Repository class for handling interactions with the server related to payment participations.
 * This class uses Retrofit to perform asynchronous network requests.
 */
public class PaymentParticipationRepository {

    private static final String TAG = PaymentParticipationRepository.class.getSimpleName();
    private MutableLiveData<List<Object[]>> getPaymentParticipationLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Object[]>> owePaymentParticipationLiveData = new MutableLiveData<>();
    private MutableLiveData<List<PaymentParticipation>> getPaymentParticipationsByUserIds = new MutableLiveData<>();
    private MutableLiveData<List<PaymentParticipation>> owePaymentParticipationsByUserIds = new MutableLiveData<>();
    private PaymentParticipationService paymentParticipationService;


    public PaymentParticipationRepository() {

        paymentParticipationService = RetrofitClient.getInstance().create(PaymentParticipationService.class);

    }

    /**
     * Fetches payments where the current user is marked as the receiver (owed payments),
     * grouped by the user who owes the payment.
     *
     * @param groupId The ID of the current group.
     * @param userId  The ID of the current user.
     */
    public void fetchGetPaymentsGroupedByUser(UUID groupId, UUID userId, Context context) {

        if (groupId == null) {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        Call<List<Object[]>> call = paymentParticipationService.getGetPaymentsGroupedByUser(groupId, userId);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Get Payments fetching successful");
                    List<Object[]> paymentParticipations = response.body();
                    getPaymentParticipationLiveData.setValue(paymentParticipations);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if (response.code() == 401) {
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while fetching Get Payments");
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching Get Payments: " + t);
            }
        });
    }

    /**
     * Fetches payments where the current user is marked as the payer (owing payments),
     * grouped by the user who is owed the payment.
     *
     * @param groupId The ID of the current group.
     * @param userId  The ID of the current user.
     */
    public void fetchOwePaymentsGroupedByUser(UUID groupId, UUID userId, Context context) {

        if (groupId == null) {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        Call<List<Object[]>> call = paymentParticipationService.getOwePaymentsGroupedByUser(groupId, userId);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Owe Payments fetching successful");
                    List<Object[]> paymentParticipations = response.body();
                    owePaymentParticipationLiveData.setValue(paymentParticipations);
                } else {
                    // If unauthorized/bad credentials return to login screen
                    if (response.code() == 401) {
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error while fetching Owe Payments");
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching Owe Payments: " + t);
            }
        });
    }

    private void fetchGetPaymentParticipationsByUserIds(UUID groupId, UUID userIdOwe, UUID userIdGet, Context context) {

        if (groupId == null) {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        Call<List<PaymentParticipation>> call = paymentParticipationService.getGetPaymentParticipationsByUserIds(groupId, userIdOwe, userIdGet);
        call.enqueue(new Callback<List<PaymentParticipation>>() {
            @Override
            public void onResponse(Call<List<PaymentParticipation>> call, Response<List<PaymentParticipation>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Get Payment Participation fetching successful");
                    List<PaymentParticipation> paymentParticipations = response.body();
                    getPaymentParticipationsByUserIds.setValue(paymentParticipations);
                } else {
                    Log.e(TAG, "Error while fetching Get Payment Participations");
                }
            }

            @Override
            public void onFailure(Call<List<PaymentParticipation>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching Get Payment Participations: " + t);
            }
        });
    }

    private void fetchOwePaymentParticipationsByUserIds(UUID groupId, UUID userIdGet, UUID userIdOwe, Context context) {

        if (groupId == null) {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        Call<List<PaymentParticipation>> call = paymentParticipationService.getOwePaymentParticipationsByUserIds(groupId, userIdGet, userIdOwe);
        call.enqueue(new Callback<List<PaymentParticipation>>() {
            @Override
            public void onResponse(Call<List<PaymentParticipation>> call, Response<List<PaymentParticipation>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Owe Payment Participation fetching successful");
                    List<PaymentParticipation> paymentParticipations = response.body();
                    owePaymentParticipationsByUserIds.setValue(paymentParticipations);
                } else {
                    Log.e(TAG, "Error while fetching Owe Payment Participations");
                }
            }

            @Override
            public void onFailure(Call<List<PaymentParticipation>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching Owe Payment Participations: " + t);
            }
        });
    }

    public LiveData<List<Object[]>> getGetPaymentsGroupedByUser(UUID groupId, UUID userId, Context context) {

        fetchGetPaymentsGroupedByUser(groupId, userId, context);
        return getPaymentParticipationLiveData;

    }

    public LiveData<List<PaymentParticipation>> getGetPaymentParticipationsByUserIds(UUID groupId, UUID userIdOwe, UUID userIdGet, Context context) {

        fetchGetPaymentParticipationsByUserIds(groupId, userIdOwe, userIdGet, context);
        return getPaymentParticipationsByUserIds;

    }

    public LiveData<List<Object[]>> getOwePaymentsGroupedByUser(UUID groupId, UUID userId, Context context) {

        fetchOwePaymentsGroupedByUser(groupId, userId, context);
        return owePaymentParticipationLiveData;

    }

    public LiveData<List<PaymentParticipation>> getOwePaymentParticipationsByUserIds(UUID groupId, UUID userIdGet, UUID userIdOwe, Context context) {

        fetchOwePaymentParticipationsByUserIds(groupId, userIdGet, userIdOwe, context);
        return owePaymentParticipationsByUserIds;

    }

    /**
     * Updates a payment participation and fetches the latest owed and owing payments.
     *
     * @param context The application context for displaying toasts.
     */
    public void update(PaymentParticipation p, Context context) {

        Call<PaymentParticipation> call = paymentParticipationService.updatePaymentParticipation(p.getId(), p);
        call.enqueue(new Callback<PaymentParticipation>() {
            @Override
            public void onResponse(Call<PaymentParticipation> call, Response<PaymentParticipation> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Paying payment participation successful");
                    UUID userId = AppStateRepository.getCurrentAppUserLiveData().getValue().getId();
                    UUID groupId = AppStateRepository.getCurrentGroupLiveData().getValue().getId();
                    fetchGetPaymentsGroupedByUser(groupId, userId, context);
                    fetchOwePaymentsGroupedByUser(groupId, userId, context);
                    // no toast needed as user only needs to get informed when all participations are paid
                } else {
                    Log.e(TAG, "Error while paying payment participation");
                    ToastUtil.makeToast("Error while paying  " + p.getUser().getFirstName(), context);
                }
            }

            @Override
            public void onFailure(Call<PaymentParticipation> call, Throwable t) {
                Log.e(TAG, "Network error while paying payment participation: " + t);
                ToastUtil.makeToast("Error while paying  " + p.getUser().getFirstName(), context);

            }
        });
    }
}