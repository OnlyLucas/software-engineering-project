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
import com.example.software_engineering_project.viewmodel.PaymentParticipationRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

import java.util.LinkedHashMap;
import java.util.List;
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

    public static void uncheckItemGet(int position, Context context) {

        List<Object[]> list = getPaymentsGroupedByUserLiveData.getValue();
        Object[] pair = list.get(position);
        LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

        String userIdOweString = (String) userMap.get("id");
        String name = (String) userMap.get("firstName");
        UUID userIdOwe = UUID.fromString(userIdOweString);
        UUID userIdGet = UserViewModel.getCurrentAppUser().getValue().getId();
        UUID groupId = UserViewModel.getCurrentGroup().getValue().getId();

        paymentParticipationRepository.getGetPaymentParticipationsByUserIds(groupId, userIdOwe, userIdGet)
                .observe((LifecycleOwner) context, affectedPaymentParticipations -> {
                    if (affectedPaymentParticipations != null) {

                        for (PaymentParticipation p : affectedPaymentParticipations) {
                            p.setIsPaid(true);
                            paymentParticipationRepository.update(p, context);
                        }
                        ToastUtil.makeToast("Paid " + name, context);
                    }
                });
    }

    public static void uncheckItemOwe(int position, Context context) {

        List<Object[]> list = owePaymentsGroupedByUserLiveData.getValue();
        Object[] pair = list.get(position);
        LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

        String userIdOweString = (String) userMap.get("id");
        String name = (String) userMap.get("firstName");
        UUID userIdGet = UUID.fromString(userIdOweString);
        UUID userIdOwe = UserViewModel.getCurrentAppUser().getValue().getId();
        UUID groupId = UserViewModel.getCurrentGroup().getValue().getId();

        paymentParticipationRepository.getOwePaymentParticipationsByUserIds(groupId, userIdGet, userIdOwe)
                .observe((LifecycleOwner) context, affectedPaymentParticipations -> {
                    if (affectedPaymentParticipations != null) {
                        for (PaymentParticipation p : affectedPaymentParticipations) {
                            p.setIsPaid(true);
                            paymentParticipationRepository.update(p, context);
                        }
                        ToastUtil.makeToast("Paid " + name, context);
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_detail_screen, container, false);
        context = requireActivity();
        loadScreenElements();

        // get current group and user
        Group group = UserViewModel.getCurrentGroup().getValue();
        User user = UserViewModel.getCurrentAppUser().getValue();

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
        if(difference == 0){
            totalCalculatedExpenses.setText("In total you do not owe or get any money.");
        } else if(difference > 0){
            totalCalculatedExpenses.setText("You get back in total: " + difference);
        } else{
            totalCalculatedExpenses.setText("You owe in total: " + difference);
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
                totalGetExpenses.setText("You get: " + totalAmountGet);
                return  totalAmountGet;
            }
            return null;
    }

    private Double getTotalOwePayments(List<Object[]> list) {
        if (list != null) {
            Double totalAmountOwe = new Double(0);

            for (Object[] object : list) {
                if (object.length > 1 && object[1] instanceof Double) {
                    Double paymentAmount = (Double) object[1];
                    totalAmountOwe = totalAmountOwe + paymentAmount;
                }
            }
            totalOweExpenses.setText("You owe: " + totalAmountOwe);
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