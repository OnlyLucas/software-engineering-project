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
import com.example.software_engineering_project.repository.AppStateRepository;
import com.example.software_engineering_project.repository.GroupMembershipRepository;
import com.example.software_engineering_project.repository.UserRepository;

public class FragmentDialogLeaveFlatShare extends DialogFragment {
    private static GroupMembershipRepository groupMembershipRepository;
    private Context context;

    /**
     * Creates a dialog instance to confirm leaving a flat share.
     *
     * @param savedInstanceState A Bundle containing data provided by the system to reconstruct the dialog
     *                            if it's being re-initialized after previously being shut down.
     * @return Dialog Returns an AlertDialog to confirm leaving the flat share.
     *         - Constructs an AlertDialog with a title, message, and positive/negative buttons.
     *         - Positive button triggers the removal of the user from the flat share and resets the current group.
     *         - Negative button dismisses the dialog.
     */
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
