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
import com.example.software_engineering_project.entity.PaymentParticipation;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.viewmodel.PaymentParticipationRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

import java.math.BigDecimal;
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

    private static LiveData<List<Object[]>> getPaymentParticipationLiveData;
    private AdapterBudgetDetailGet adapterBudgetDetailGet;
    private AdapterBudgetDetailOwe adapterBudgetDetailOwe;
    private Context context;
    private ListView listGetExpenses, listOweExpenses;
    private TextView totalCalculatedExpenses, totalGetExpenses, totalOweExpenses;
    private View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Group group = UserViewModel.getCurrentGroup().getValue();
//        User user = UserViewModel.getCurrentAppUser().getValue();
//        paymentParticipationRepository = new PaymentParticipationRepository();
//        getPaymentParticipationLiveData = paymentParticipationRepository.getPaymentParticipationsForPaidUser(group.getId(), user.getId());
//
//        List<Object[]> listGet = getPaymentParticipationLiveData.getValue();
//        for (Object[] p: listGet) {
//            User userGet = (User) p[0];
//            if(userGet.getId().equals(user.getId())){
//                listGet.remove(p);
//            }
//        }

        fragmentView = inflater.inflate(R.layout.fragment_budget_detail_screen, container, false);
        context = requireActivity();
        loadScreenElements();

//        adapterBudgetDetailGet = new AdapterBudgetDetailGet(context, listGet);
        adapterBudgetDetailOwe = new AdapterBudgetDetailOwe(context, itemsOwe);

        return fragmentView;

    }

    private void loadScreenElements() {

        listGetExpenses = fragmentView.findViewById(R.id.listGetExpenses);
        listOweExpenses = fragmentView.findViewById(R.id.listOweExpenses);
        totalCalculatedExpenses = fragmentView.findViewById(R.id.totalCalculatedExpenses);
        totalGetExpenses = fragmentView.findViewById(R.id.totalGetExpenses);
        totalOweExpenses = fragmentView.findViewById(R.id.totalOweExpenses);

    }

}