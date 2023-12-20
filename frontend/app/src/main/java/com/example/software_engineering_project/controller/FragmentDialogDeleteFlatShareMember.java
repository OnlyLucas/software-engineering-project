package com.example.software_engineering_project.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.appsettings.FragmentManageFlatShareController;

/**
 * The FragmentDialogDeleteFlatShareMember class represents a dialog fragment in the Flat Fusion app
 * that confirms the user's intention to delete a flat share member. It provides a dialog with options
 * to either confirm or cancel the action.
 *
 * Responsibilities:
 * - Creates a dialog instance with appropriate title, message, and button actions.
 * - Positive button triggers the removal of the flat share member via FragmentManageFlatShareController.
 * - Negative button dismisses the dialog.
 */
public class FragmentDialogDeleteFlatShareMember extends DialogFragment {
    private int position;

    /**
     * Constructs a FragmentDialogDeleteFlatShareMember with the specified position of the flat share member.
     *
     * @param position The position of the flat share member in the list to be deleted.
     */
    public FragmentDialogDeleteFlatShareMember(int position) {
        this.position = position;
    }

    /**
     * Creates a dialog instance for confirming the deletion of a flat share member.
     *
     * @param savedInstanceState A Bundle containing the data provided by the system to reconstruct the dialog
     *                            if it is being re-initialized after previously being shut down.
     * @return Dialog Returns an AlertDialog to confirm the deletion of a flat share member.
     *         - Constructs an AlertDialog with a title, message, and positive/negative buttons.
     *         - Positive button triggers the removal of the flat share member via FragmentManageFlatShareController.
     *         - Negative button dismisses the dialog.
     */
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
