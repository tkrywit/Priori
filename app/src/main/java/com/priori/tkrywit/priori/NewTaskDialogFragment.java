package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NewTaskDialogFragment extends DialogFragment {

    static NewTaskDialogFragment newInstance() {
        NewTaskDialogFragment f = new NewTaskDialogFragment();

        return f;
    }

    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //inflate custom layout and create dialog builder
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.fragment_new_task_dialog, null));
        builder.setTitle(R.string.new_task_dialog);
                        // Positive button
        builder.setPositiveButton(R.string.dialog_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                }
            });

                        // Negative Button
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                }
            });

        Dialog dialog = builder.create();

        Button button = (Button) dialog.findViewById(R.id.dueDateButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Gubs", "Due date pressed!");
            }
        });

        return dialog;
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_task_dialog, container, false);
        getDialog().setTitle(R.string.new_task_dialog);

        return v;
    }
}
