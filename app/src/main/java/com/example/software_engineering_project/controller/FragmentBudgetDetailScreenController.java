package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetDetailGet;
import com.example.software_engineering_project.adapter.AdapterBudgetDetailOwe;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.viewmodel.PaymentParticipationRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetDetailScreenController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetDetailScreenController extends Fragment {
    private static PaymentParticipationRepository paymentParticipationRepository;
    private static ArrayList<String> itemsGet = new ArrayList<>();
    private static ArrayList<String> itemsOwe = new ArrayList<>();

    private static LiveData<List<Object[]>> getPaymentsGroupedByUserLiveData;
    private static LiveData<List<Object[]>> owePaymentsGroupedByUserLiveData;
    private AdapterBudgetDetailGet adapterBudgetDetailGet;
    private AdapterBudgetDetailOwe adapterBudgetDetailOwe;
    private Context context;
    private ListView listGetExpenses, listOweExpenses;
    private TextView totalCalculatedExpenses, totalGetExpenses, totalOweExpenses;
    private View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // get current group and user
        Group group = UserViewModel.getCurrentGroup().getValue();
        User user = UserViewModel.getCurrentAppUser().getValue();

        // get current get and owe payments
        paymentParticipationRepository = new PaymentParticipationRepository();

        final Double[] payments = new Double[2];

        getPaymentsGroupedByUserLiveData = paymentParticipationRepository.getGetPaymentsGroupedByUser(group.getId(), user.getId());
        getPaymentsGroupedByUserLiveData.observe(getViewLifecycleOwner(), getPaymentsGroupedByUser -> {
            adapterBudgetDetailGet = new AdapterBudgetDetailGet(context, getPaymentsGroupedByUserLiveData.getValue());
            listGetExpenses.setAdapter(adapterBudgetDetailGet);
            payments[0] = getTotalGetPayments(getPaymentsGroupedByUser);
        });

        owePaymentsGroupedByUserLiveData = paymentParticipationRepository.getOwePaymentsGroupedByUser(group.getId(), user.getId());
        owePaymentsGroupedByUserLiveData.observe(getViewLifecycleOwner(), owePaymentsGroupedByUser -> {
            adapterBudgetDetailOwe = new AdapterBudgetDetailOwe(context, owePaymentsGroupedByUserLiveData.getValue());
            listOweExpenses.setAdapter(adapterBudgetDetailOwe);
            payments[1] = getTotalOwePayments(owePaymentsGroupedByUser);
        });

        //TODO Calculate total owe or get
//        double getPayments = getTotalGetPayments(getPaymentsGroupedByUserLiveData.getValue());
//        double owePayments = getTotalOwePayments(owePaymentsGroupedByUserLiveData.getValue());
//        getTotalGetOrOwe(getPayments, owePayments);

        fragmentView = inflater.inflate(R.layout.fragment_budget_detail_screen, container, false);
        context = requireActivity();
        loadScreenElements();

        return fragmentView;

    }

    //TODO use this method
    private void getTotalGetOrOwe(Double getPayments, Double owePayments) {
        Double difference = getPayments - owePayments;
        if(difference == 0){
           totalCalculatedExpenses.setText("In total you do not owe or get any money.");
        } else if(difference > 0){
            totalCalculatedExpenses.setText("You get back: " + difference);
        } else{
            totalCalculatedExpenses.setText("You owe: " + difference);
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
                return totalAmountGet;
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