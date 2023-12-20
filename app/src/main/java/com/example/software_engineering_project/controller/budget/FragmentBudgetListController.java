package com.example.software_engineering_project.controller.budget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetListFirstLayer;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.repository.PaymentRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetListController extends Fragment {

    private static AdapterBudgetListFirstLayer adapter;
    private static Context context;
    private static ListView listView;
    private static PaymentRepository paymentRepository;
    private static LiveData<List<Payment>> currentPayments;
    private View fragmentView;

    /**
     * Removes a specific payment item from the list and deletes it from the repository.
     * The method identifies the payment at the provided position in the list of current payments
     * and deletes it from the payment repository using the associated context.
     *
     * @param position The position of the payment item to be removed from the list.
     */
    public static void removeItem(int position) {
        Payment payment = currentPayments.getValue().get(position);
        paymentRepository.deletePayment(payment, context);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI will be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The root view for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = requireActivity();
        paymentRepository = new PaymentRepository(context);
        currentPayments = paymentRepository.getCurrentPayments();

        fragmentView = inflater.inflate(R.layout.fragment_budget_list, container, false);
        loadScreenElements();

        currentPayments.observe(getViewLifecycleOwner(), currentPayments -> {
            adapter = new AdapterBudgetListFirstLayer(context, paymentRepository.getCurrentPayments().getValue());
            listView.setAdapter(adapter);
        });

        return fragmentView;

    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.budgetListFirstLayer);

    }
}