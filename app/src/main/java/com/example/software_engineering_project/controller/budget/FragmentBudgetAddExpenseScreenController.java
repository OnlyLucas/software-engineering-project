package com.example.software_engineering_project.controller.budget;

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

import request.PaymentCreationRequest;

import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.PaymentParticipationRepository;
import com.example.software_engineering_project.repository.PaymentRepository;
import com.example.software_engineering_project.repository.UserRepository;

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


    /**
     * Adds a user to the selected users list.
     *
     * @param user The user to be added to the selected users list.
     *             This method appends the provided user to the list of selected users.
     */
    public static void addUser(User user) {
        selectedUsers.add(user);
    }

    /**
     * Removes a user from the selected users list.
     *
     * @param user The user to be removed from the selected users list.
     *             This method removes the provided user from the list of selected users if present.
     */
    public static void deleteUser(User user) {
        selectedUsers.remove(user);
    }

    /**
     * Handles the save action for creating a payment using the provided inputs and selected users.
     * Retrieves payment details from inputs, creates a PaymentCreationRequest, and sends the payment creation request
     * to the repository for each selected user.
     * <p>
     * If payment details and selected users are available, it divides the payment amount equally among selected users
     * and creates a payment request for each user.
     */
    public static void handleSaveClicked() {
        Payment payment = getPaymentFromInputs();
        PaymentCreationRequest requestData = new PaymentCreationRequest(payment);

        if (payment != null) {
            if (selectedUsers.size() != 0) {
                for (User u : selectedUsers) {
                    BigDecimal paymentAmountForUser = payment.getAmount().divide(new BigDecimal(selectedUsers.size()), RoundingMode.HALF_UP);

                    // add users to requestData
                    requestData.getUserParticipations().put(u.getId(), paymentAmountForUser);
                }
                paymentRepository.createPayment(requestData, context);
            }
        }
    }

    /**
     * Retrieves payment details from user inputs and validates the input fields.
     * Parses the expense and reason fields to create a Payment object if the input is valid.
     * Shows appropriate toast messages for validation errors.
     *
     * @return A Payment object if the input is valid, or null if input validation fails.
     */
    private static Payment getPaymentFromInputs() {

        String expenseString = expense.getText().toString();
        // Remove commas and replace with dot for machine readable format
        expenseString = expenseString.replace(",", ".");
        int numberSelectedUsers = selectedUsers.size();

        try {
            BigDecimal expenseValue = new BigDecimal(expenseString);
            String reasonString = reason.getText().toString();
            if (expenseValue.compareTo(new BigDecimal(0)) == -1) {
                ToastUtil.makeToast(context.getString(R.string.no_negative_amount_allowed), context);
            } else if (expenseValue.compareTo(new BigDecimal(0)) == 0) {
                ToastUtil.makeToast(context.getString(R.string._0_is_not_a_valid_expense_amount), context);
            } else if (reasonString.length() == 0) {
                ToastUtil.makeToast(context.getString(R.string.enter_name_for_expense), context);
            } else if (reasonString.length() > 15) {
                ToastUtil.makeToast(context.getString(R.string.enter_shorter_reason), context);
            } else if (numberSelectedUsers == 0) {
                ToastUtil.makeToast(context.getString(R.string.select_users), context);
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
        }
        return null;
    }

    /**
     * Inflates the layout for the Budget Add Expense screen, initializes necessary repositories and adapters,
     * loads UI elements, observes changes in the user list, and sets up the ListView adapter.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the previous state of the fragment, or null if it's a fresh start.
     * @return The View for the Budget Add Expense screen after inflation and setup.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = requireActivity();
        userRepository = new UserRepository(context);
        currentUsers = userRepository.getCurrentUsers();

        paymentRepository = new PaymentRepository(context);
        paymentParticipationRepository = new PaymentParticipationRepository();

        fragmentView = inflater.inflate(R.layout.fragment_budget_add_expense_screen, container, false);
        fragmentViewHeader = inflater.inflate(R.layout.fragment_budget_main, container, false);
        loadScreenElements();

        currentUsers.observe(getViewLifecycleOwner(), currentUsers -> {
            adapter = new AdapterBudgetNewExpense(getActivity(), currentUsers);
            listView.setAdapter(adapter);
        });

        listView.setAdapter(adapter);

        return fragmentView;

    }

    /**
     * Initializes and assigns UI elements for the Budget Add Expense screen.
     * Finds and assigns specific UI components from the fragment's layout file to their respective variables.
     * The components include expense amount, involved persons list, and reason for the expense.
     */
    private void loadScreenElements() {

        expense = fragmentView.findViewById(R.id.enterNewExpenseAmount);
        listView = fragmentView.findViewById(R.id.enterNewExpenseInvolvedPersons);
        reason = fragmentView.findViewById(R.id.enterNewExpenseReason);

    }
}