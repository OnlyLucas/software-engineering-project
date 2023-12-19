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
import com.example.software_engineering_project.viewmodel.AppStateRepository;
import com.example.software_engineering_project.viewmodel.PaymentParticipationRepository;

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
     * Unchecks an item in the get category when the user marks a payment as paid.
     *
     * @param position The position of the item in the get category.
     * @param context  The context used for operations.
     */
    public static void uncheckItemGet(int position, Context context) {

        List<Object[]> list = getPaymentsGroupedByUserLiveData.getValue();
        Object[] pair = list.get(position);
        LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

        String userIdOweString = (String) userMap.get("id");
        String name = (String) userMap.get("firstName");
        UUID userIdOwe = UUID.fromString(userIdOweString);
        UUID userIdGet = AppStateRepository.getCurrentAppUserLiveData().getValue().getId();
        UUID groupId = AppStateRepository.getCurrentGroupLiveData().getValue().getId();

        paymentParticipationRepository.getGetPaymentParticipationsByUserIds(groupId, userIdOwe, userIdGet)
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
     * Unchecks an item in the owe category when the user marks a payment as paid.
     *
     * @param position The position of the item in the owe category.
     * @param context  The context used for operations.
     */
    public static void uncheckItemOwe(int position, Context context) {

        List<Object[]> list = owePaymentsGroupedByUserLiveData.getValue();
        Object[] pair = list.get(position);
        LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

        String userIdOweString = (String) userMap.get("id");
        String name = (String) userMap.get("firstName");
        UUID userIdGet = UUID.fromString(userIdOweString);
        UUID userIdOwe = AppStateRepository.getCurrentAppUserLiveData().getValue().getId();
        UUID groupId = AppStateRepository.getCurrentGroupLiveData().getValue().getId();

        paymentParticipationRepository.getOwePaymentParticipationsByUserIds(groupId, userIdGet, userIdOwe)
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
     * Overrides the onCreateView method to inflate the layout and set up the UI elements.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The Bundle containing the fragment's previously saved state.
     * @return The inflated View for the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_detail_screen, container, false);
        context = requireActivity();
        loadScreenElements();

        // get current group and user
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
        User user = AppStateRepository.getCurrentAppUserLiveData().getValue();

        // get current get and owe payments
        paymentParticipationRepository = new PaymentParticipationRepository();

        getPaymentsGroupedByUserLiveData = paymentParticipationRepository.getGetPaymentsGroupedByUser(group.getId(), user.getId());
        getPaymentsGroupedByUserLiveData.observe(getViewLifecycleOwner(), getPaymentsGroupedByUser -> {
            adapterBudgetDetailGet = new AdapterBudgetDetailGet(context, getPaymentsGroupedByUser);
            listGetExpenses.setAdapter(adapterBudgetDetailGet);
            getDouble = getTotalGetPayments(getPaymentsGroupedByUser);
            getTotalGetOrOwe(getDouble, oweDouble);
        });

        owePaymentsGroupedByUserLiveData = paymentParticipationRepository.getOwePaymentsGroupedByUser(group.getId(), user.getId());
        owePaymentsGroupedByUserLiveData.observe(getViewLifecycleOwner(), owePaymentsGroupedByUser -> {
            adapterBudgetDetailOwe = new AdapterBudgetDetailOwe(context, owePaymentsGroupedByUserLiveData.getValue());
            listOweExpenses.setAdapter(adapterBudgetDetailOwe);
            oweDouble = getTotalOwePayments(owePaymentsGroupedByUser);
            getTotalGetOrOwe(getDouble, oweDouble);
        });

        return fragmentView;

    }

    private void getTotalGetOrOwe(Double getPayments, Double owePayments) {
        Double difference = getPayments - owePayments;
        // So far we only support euro as currency, but in this place a differentiation would be needed
        String differenceString;

        if(difference == 0){
            totalCalculatedExpenses.setText("In total you do not owe or get any money.");
        } else if(difference > 0){
            differenceString = String.format(Locale.getDefault(), "%.2f", difference) + "€";
            totalCalculatedExpenses.setText("You get back in total: " + differenceString);
        } else{
            difference = difference * (-1);
            differenceString = String.format(Locale.getDefault(), "%.2f", difference) + "€";
            totalCalculatedExpenses.setText("You owe in total: " + differenceString);
        }
    }

    private Double getTotalGetPayments(List<Object[]> list) {
            if (list != null) {
                Double totalAmountGet = new Double(0);

                for (Object[] object : list) {
                    if (object.length > 1 && object[1] instanceof Double) {
                        Double paymentAmount = (Double) object[1];
                        totalAmountGet = totalAmountGet + paymentAmount;
                    }
                }
                if(totalAmountGet == 0.00){
                    totalGetExpenses.setText("You do not get any money back.");
                } else {
                    // So far we only support euro as currency, but in this place a differentiation would be needed
                    String totalAmountGetString = String.format(Locale.getDefault(), "%.2f", totalAmountGet) + "€";
                    totalGetExpenses.setText("You get: " + totalAmountGetString);
                }
                return totalAmountGet;
            }
            return null;
    }

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
            if(totalAmountOwe == 0.00) {
                totalOweExpenses.setText("You do not owe any money.");
            } else {
                totalOweExpenses.setText("You owe: " + totalAmountOwe);
            }
            return totalAmountOwe;
        }
        return null;
    }

    private void loadScreenElements() {

        listGetExpenses = fragmentView.findViewById(R.id.listGetExpenses);
        listOweExpenses = fragmentView.findViewById(R.id.listOweExpenses);
        totalCalculatedExpenses = fragmentView.findViewById(R.id.totalCalculatedExpenses);
        totalGetExpenses = fragmentView.findViewById(R.id.totalGetExpenses);
        totalOweExpenses = fragmentView.findViewById(R.id.totalOweExpenses);

    }

}