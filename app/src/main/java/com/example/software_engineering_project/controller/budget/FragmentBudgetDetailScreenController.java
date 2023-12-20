package com.example.software_engineering_project.controller.budget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetDetailGet;
import com.example.software_engineering_project.adapter.AdapterBudgetDetailOwe;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.PaymentParticipation;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.AppStateRepository;
import com.example.software_engineering_project.repository.PaymentParticipationRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetDetailScreenController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetDetailScreenController extends Fragment {
    private static PaymentParticipationRepository paymentParticipationRepository;
    private static LiveData<List<Object[]>> getPaymentsGroupedByUserLiveData;
    private static LiveData<List<Object[]>> owePaymentsGroupedByUserLiveData;
    private AdapterBudgetDetailGet adapterBudgetDetailGet;
    private AdapterBudgetDetailOwe adapterBudgetDetailOwe;
    private static Context context;
    private ListView listGetExpenses, listOweExpenses;
    private TextView totalCalculatedExpenses, totalGetExpenses, totalOweExpenses;
    private View fragmentView;
    private Double getDouble = new Double(0);
    private Double oweDouble = new Double(0);

    /**
     * Marks the payments as 'paid' for a specific user in the context of 'getting' payments,
     * updating the associated payment participations accordingly.
     * This method retrieves the necessary payment participations by user IDs
     * and marks them as 'paid', updating the database.
     *
     * @param position The position of the user whose payments need to be marked as 'paid'.
     * @param context  The Context reference used for displaying toasts and accessing resources.
     */
    public static void uncheckItemGet(int position, Context context) {

        List<Object[]> list = getPaymentsGroupedByUserLiveData.getValue();
        Object[] pair = list.get(position);
        LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

        String userIdOweString = (String) userMap.get("id");
        String name = (String) userMap.get("firstName");
        UUID userIdOwe = UUID.fromString(userIdOweString);
        UUID userIdGet = AppStateRepository.getCurrentAppUserLiveData().getValue().getId();
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
        if (group == null) {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        paymentParticipationRepository.getGetPaymentParticipationsByUserIds(group.getId(), userIdOwe, userIdGet, context)
                .observe((LifecycleOwner) context, affectedPaymentParticipations -> {
                    if (affectedPaymentParticipations != null) {

                        for (PaymentParticipation p : affectedPaymentParticipations) {
                            p.setPaid();
                            paymentParticipationRepository.update(p, context);
                        }
                        ToastUtil.makeToast("Paid " + name, context);
                    }
                });
    }


    /**
     * Marks the payments as 'paid' for a specific user in the context of 'owing' payments,
     * updating the associated payment participations accordingly.
     * This method retrieves the necessary payment participations by user IDs
     * and marks them as 'paid', updating the database.
     *
     * @param position The position of the user whose payments need to be marked as 'paid'.
     * @param context  The Context reference used for displaying toasts and accessing resources.
     */
    public static void uncheckItemOwe(int position, Context context) {

        List<Object[]> list = owePaymentsGroupedByUserLiveData.getValue();
        Object[] pair = list.get(position);
        LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

        String userIdOweString = (String) userMap.get("id");
        String name = (String) userMap.get("firstName");
        UUID userIdGet = UUID.fromString(userIdOweString);
        UUID userIdOwe = AppStateRepository.getCurrentAppUserLiveData().getValue().getId();
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
        if (group == null) {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
            return;
        }

        paymentParticipationRepository.getOwePaymentParticipationsByUserIds(group.getId(), userIdGet, userIdOwe, context)
                .observe((LifecycleOwner) context, affectedPaymentParticipations -> {
                    if (affectedPaymentParticipations != null) {
                        for (PaymentParticipation p : affectedPaymentParticipations) {
                            p.setPaid();
                            paymentParticipationRepository.update(p, context);
                        }
                        ToastUtil.makeToast("Paid " + name, context);
                    }
                });
    }


    /**
     * Initializes and populates the budget detail screen's view.
     * Retrieves the necessary data for displaying 'get' and 'owe' payments
     * for the current group and user. Observes the LiveData for 'get' and 'owe' payments
     * and updates the corresponding adapters and views accordingly.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment.
     * @return The inflated view for the budget detail screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_detail_screen, container, false);
        context = requireActivity();
        loadScreenElements();

        // get current group and user
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
        User user = AppStateRepository.getCurrentAppUserLiveData().getValue();

        if (group != null) {
            // get current get and owe payments
            paymentParticipationRepository = new PaymentParticipationRepository();

            getPaymentsGroupedByUserLiveData = paymentParticipationRepository.getGetPaymentsGroupedByUser(group.getId(), user.getId(), context);
            getPaymentsGroupedByUserLiveData.observe(getViewLifecycleOwner(), getPaymentsGroupedByUser -> {
                adapterBudgetDetailGet = new AdapterBudgetDetailGet(context, getPaymentsGroupedByUser);
                listGetExpenses.setAdapter(adapterBudgetDetailGet);
                getDouble = getTotalGetPayments(getPaymentsGroupedByUser);
                getTotalGetOrOwe(getDouble, oweDouble);
            });

            owePaymentsGroupedByUserLiveData = paymentParticipationRepository.getOwePaymentsGroupedByUser(group.getId(), user.getId(), context);
            owePaymentsGroupedByUserLiveData.observe(getViewLifecycleOwner(), owePaymentsGroupedByUser -> {
                adapterBudgetDetailOwe = new AdapterBudgetDetailOwe(context, owePaymentsGroupedByUserLiveData.getValue());
                listOweExpenses.setAdapter(adapterBudgetDetailOwe);
                oweDouble = getTotalOwePayments(owePaymentsGroupedByUser);
                getTotalGetOrOwe(getDouble, oweDouble);
            });
        } else {
            ToastUtil.makeToast(context.getString(R.string.join_a_group), context);
        }

        return fragmentView;

    }

    /**
     * Calculates and displays the total difference between 'get' and 'owe' payments.
     * Updates the UI to reflect the total amount to get back or owe after considering
     * 'get' and 'owe' payments. Displays the total amount in Euro currency format.
     *
     * @param getPayments The total 'get' payments received by the user.
     * @param owePayments The total 'owe' payments the user needs to pay.
     */
    private void getTotalGetOrOwe(Double getPayments, Double owePayments) {

        Double difference = getPayments - owePayments;
        // So far we only support euro as currency, but in this place a differentiation would be needed
        String differenceString;

        if (difference == 0) {
            totalCalculatedExpenses.setText(R.string.in_total_you_do_not_owe_or_get_any_money);
        } else if (difference > 0) {
            differenceString = String.format(Locale.getDefault(), "%.2f", difference) + "€";
            totalCalculatedExpenses.setText(getString(R.string.you_get_back_in_total) + differenceString);
        } else {
            difference = difference * (-1);
            differenceString = String.format(Locale.getDefault(), "%.2f", difference) + "€";
            totalCalculatedExpenses.setText(getString(R.string.you_owe_in_total) + differenceString);
        }
    }

    /**
     * Calculates the total 'get' payments received by the user from the provided list.
     * Updates the UI to display the total 'get' payments in Euro currency format.
     *
     * @param list A list of Object arrays containing payment information, including payment amount.
     * @return The total 'get' payments received by the user, in Double format, or null if the list is null.
     */
    private Double getTotalGetPayments(List<Object[]> list) {

        if (list != null) {
            Double totalAmountGet = new Double(0);

            for (Object[] object : list) {
                if (object.length > 1 && object[1] instanceof Double) {
                    Double paymentAmount = (Double) object[1];
                    totalAmountGet = totalAmountGet + paymentAmount;
                }
            }

            if (totalAmountGet == 0.00) {
                totalGetExpenses.setText(R.string.you_do_not_get_any_money_back);
            } else {
                // So far we only support euro as currency, but in this place a differentiation would be needed
                String totalAmountGetString = String.format(Locale.getDefault(), "%.2f", totalAmountGet) + "€";
                totalGetExpenses.setText(getString(R.string.you_get) + totalAmountGetString);
            }

            return totalAmountGet;
        }
        return null;
    }

    /**
     * Calculates the total 'owe' payments the user needs to make based on the provided list.
     * Updates the UI to display the total 'owe' payments in Euro currency format or a message if no money is owed.
     *
     * @param list A list of Object arrays containing payment information, including payment amount.
     * @return The total 'owe' payments to be made by the user in Double format, or null if the list is null.
     */
    private Double getTotalOwePayments(List<Object[]> list) {

        if (list != null) {
            Double totalAmountOwe = new Double(0);
            // So far we only support euro as currency, but in this place a differentiation would be needed
            String totalAmountOweString = String.format(Locale.getDefault(), "- %.2f", totalAmountOwe) + "€";

            for (Object[] object : list) {
                if (object.length > 1 && object[1] instanceof Double) {
                    Double paymentAmount = (Double) object[1];
                    totalAmountOwe = totalAmountOwe + paymentAmount;
                }
            }

            if (totalAmountOwe == 0.00) {
                totalOweExpenses.setText(R.string.you_do_not_owe_any_money);
            } else {
                totalOweExpenses.setText(getString(R.string.you_owe) + totalAmountOwe);
            }

            return totalAmountOwe;
        }
        return null;
    }

    /**
     * Initializes UI elements by assigning them from the corresponding XML layout elements in the fragment.
     * Retrieves references for displaying 'get' and 'owe' expenses lists, total calculated expenses,
     * total 'get' expenses, and total 'owe' expenses TextViews.
     * These elements are used to populate and display information on the UI.
     */
    private void loadScreenElements() {

        listGetExpenses = fragmentView.findViewById(R.id.listGetExpenses);
        listOweExpenses = fragmentView.findViewById(R.id.listOweExpenses);
        totalCalculatedExpenses = fragmentView.findViewById(R.id.totalCalculatedExpenses);
        totalGetExpenses = fragmentView.findViewById(R.id.totalGetExpenses);
        totalOweExpenses = fragmentView.findViewById(R.id.totalOweExpenses);

    }

}