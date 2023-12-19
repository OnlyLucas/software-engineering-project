package com.example.software_engineering_project.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.viewmodel.AppStateRepository;
import com.example.software_engineering_project.viewmodel.GroupMembershipRepository;
import com.example.software_engineering_project.viewmodel.UserRepository;

public class FragmentDialogLeaveFlatShare extends DialogFragment {
    private static GroupMembershipRepository groupMembershipRepository;
    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        groupMembershipRepository = new GroupMembershipRepository();
        context = requireActivity();

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.leave_flat_share)
                .setMessage(R.string.do_you_really_want_to_leave_the_flat_share_)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Remove groupMembership
                                User user = AppStateRepository.getCurrentAppUserLiveData().getValue();
                                groupMembershipRepository.deleteGroupMembership(user, new UserRepository(context), context);
                                //Reset currentGroup to null
                                AppStateRepository.setCurrentGroup(null);
                                dialog.dismiss();
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
