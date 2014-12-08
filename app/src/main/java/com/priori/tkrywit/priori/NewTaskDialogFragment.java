package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewTaskDialogFragment extends DialogFragment {

    static NewTaskDialogFragment newInstance() {
        NewTaskDialogFragment f = new NewTaskDialogFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_task_dialog, container, false);

        return v;
    }
}
