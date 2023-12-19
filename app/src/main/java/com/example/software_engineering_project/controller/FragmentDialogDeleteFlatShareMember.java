package com.example.software_engineering_project.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.appsettings.FragmentManageFlatShareController;

public class FragmentDialogDeleteFlatShareMember extends DialogFragment {
    private int position;

    public FragmentDialogDeleteFlatShareMember(int position) {
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_flat_share_member)
                .setMessage(R.string.do_you_really_want_to_delete_this_flat_share_member_)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FragmentManageFlatShareController.removeItem(position);
                            }
                        }
                )
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();

    }

}
