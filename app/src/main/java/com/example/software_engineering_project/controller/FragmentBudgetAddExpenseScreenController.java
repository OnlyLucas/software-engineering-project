package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetNewExpense;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.entity.PaymentCreationData;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.PaymentParticipationRepository;
import com.example.software_engineering_project.viewmodel.PaymentRepository;
import com.example.software_engineering_project.viewmodel.UserRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private static List<User> selectedUsers = new ArrayList<>();
    private static LiveData<List<User>> currentUsers;
    private static PaymentParticipationRepository paymentParticipationRepository;
    private static UserRepository userRepository;
    private static PaymentRepository paymentRepository;
    private View fragmentView, fragmentViewHeader;


    public static void addUser(User user) {
        selectedUsers.add(user);
    }

    public static void deleteUser(User user) {
        selectedUsers.remove(user);
    }

    public static void handleSaveClicked() {
        Payment payment = getPaymentFromInputs();
        PaymentCreationData requestData = new PaymentCreationData(payment);

        //TODO Auslagerung in Konstruktor; richtige Erfassung der ausgewählten Personen; Payment muss vor PaymentParticipation erstellt sein
        if (payment != null) {
            System.out.println(selectedUsers.toString());

            for (User u : selectedUsers) {

                //int amountSelected = listView.getCheckedItemCount();
                BigDecimal paymentAmountForUser = payment.getAmount().divide(new BigDecimal(selectedUsers.size()), RoundingMode.HALF_UP);

                // add users to requestData
                requestData.getUserParticipations().put(u.getId(), paymentAmountForUser);
            }

            paymentRepository.createPayment(requestData, context);
        }
    }


    private static Payment getPaymentFromInputs() {
        // get the inputs
        String expenseString = expense.getText().toString();
        try {
            BigDecimal expenseValue = new BigDecimal(expenseString);
            String reasonString = reason.getText().toString();
            if (expenseValue.compareTo(new BigDecimal(0)) == -1) {
                ToastUtil.makeToast(context.getString(R.string.no_negative_amount_allowed), context);
            } else if (expenseValue.compareTo(new BigDecimal(0)) == 0) {
                ToastUtil.makeToast(context.getString(R.string._0_is_not_a_valid_expense_amount), context);
            } else if (reasonString.length() == 0) {
                ToastUtil.makeToast(context.getString(R.string.enter_name_for_expense), context);
            } else {
                // add new payment to database
                Payment payment = new Payment(expenseValue, reasonString);

                // empty input field
                expense.setText(R.string.empty_input_fields);
                reason.setText(R.string.empty_input_fields);

                return payment;
            }

        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid BigDecimal
            ToastUtil.makeToast(context.getString(R.string.enter_number_for_expense), context);
            e.printStackTrace(); // Or log the error, show a message, etc.
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        userRepository = new UserRepository();
        currentUsers = userRepository.getCurrentUsers();

        paymentRepository = new PaymentRepository();
        paymentParticipationRepository = new PaymentParticipationRepository();

        fragmentView = inflater.inflate(R.layout.fragment_budget_add_expense_screen, container, false);
        fragmentViewHeader = inflater.inflate(R.layout.fragment_budget_main, container, false);
        context = requireActivity();
        loadScreenElements();

        currentUsers.observe(getViewLifecycleOwner(), currentUsers -> {
            adapter = new AdapterBudgetNewExpense(getActivity(), currentUsers);
            listView.setAdapter(adapter);
        });

        listView.setAdapter(adapter);

        return fragmentView;
    }

    private void loadScreenElements() {
        listView = fragmentView.findViewById(R.id.enterNewExpenseInvolvedPersons);
        expense = fragmentView.findViewById(R.id.enterNewExpenseAmount);
        listView = fragmentView.findViewById(R.id.enterNewExpenseInvolvedPersons);
        reason = fragmentView.findViewById(R.id.enterNewExpenseReason);
    }
}