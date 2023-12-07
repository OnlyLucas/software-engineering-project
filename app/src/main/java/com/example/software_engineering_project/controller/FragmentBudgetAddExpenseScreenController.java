package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetNewExpense;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.PaymentRepository;
import com.example.software_engineering_project.viewmodel.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetAddExpenseScreenController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetAddExpenseScreenController extends Fragment {
    private static ListView listView;
    private static EditText expense, reason;
    private static ArrayAdapter<User> adapter;
    private static Context context;
    private static UserRepository userRepository;
    private static PaymentRepository paymentRepository;
    private static LiveData<List<User>> currentUsers;
    private static List<User> selectedUsers = new ArrayList<>();
    private View fragmentView, fragmentViewHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userRepository = new UserRepository();
        currentUsers = userRepository.getCurrentUsers();

        paymentRepository = new PaymentRepository();

        fragmentView = inflater.inflate(R.layout.fragment_budget_add_expense_screen, container, false);
        fragmentViewHeader = inflater.inflate(R.layout.fragment_budget_main, container, false);
        context = requireActivity();
        loadScreenElements();

        currentUsers.observe(getViewLifecycleOwner(), currentUsers -> {
            adapter = new  AdapterBudgetNewExpense(getActivity(), currentUsers);
            listView.setAdapter(adapter);
        });

        listView.setAdapter(adapter);

        return fragmentView;
    }

    public static void handleSaveClicked() {
        // get the inputs
        String expenseString = expense.getText().toString();
        try {
            BigDecimal expenseValue = new BigDecimal(expenseString);
            String reasonString = reason.getText().toString();
            if (expenseValue.compareTo(new BigDecimal(0)) == -1){
                ToastUtil.makeToast("No negative amount allowed", context);
            } else if (expenseValue.compareTo(new BigDecimal(0)) == 0) {
                ToastUtil.makeToast("0 is not a valid expense amount", context);
            } else if (reasonString.length() == 0) {
                ToastUtil.makeToast("Enter name for expense", context);
            } else {
                // add new payment to database
                Payment payment = new Payment(expenseValue, reasonString);
                paymentRepository.createPayment(payment, context);

                // empty input field
                expense.setText("");
                reason.setText("");
            }

        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid BigDecimal
            ToastUtil.makeToast("Enter number for expense", context);
            e.printStackTrace(); // Or log the error, show a message, etc.
        }
    }

    private void loadScreenElements() {
        listView = fragmentView.findViewById(R.id.enterNewExpenseInvolvedPersons);
        expense = fragmentView.findViewById(R.id.enterNewExpenseAmount);
        reason = fragmentView.findViewById(R.id.enterNewExpenseReason);
    }

}