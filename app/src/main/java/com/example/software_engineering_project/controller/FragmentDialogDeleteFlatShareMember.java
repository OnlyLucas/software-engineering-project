package com.example.software_engineering_project.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.software_engineering_project.R;

public class FragmentDialogDeleteFlatShareMember extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_flat_share_member)
                .setMessage(R.string.do_you_really_want_to_delete_this_flat_share_member_)
                .setPositiveButton(R.string.yes,
                        (dialog, whichButton) -> dialog.dismiss()
                )
                .setNegativeButton(R.string.no,
                        (dialog, whichButton) -> dialog.dismiss()
                )
                .create();

    }

}
