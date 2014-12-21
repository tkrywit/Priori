package com.priori.tkrywit.priori;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class NewTaskFragment extends Fragment implements View.OnClickListener {

    private OnNewTaskSelectedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        Button dateButton = (Button) view.findViewById(R.id.dueDateButton);
        Button timeButton = (Button) view.findViewById(R.id.dueTimeButton);
        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu items for use in the action bar
        menuInflater.inflate(R.menu.menu_new_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_accept:
                mListener.onTaskAccepted(createTask());
                return true;
            case R.id.action_cancel:
                mListener.onTaskCanceled();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //handle button presses
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dueDateButton:
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getFragmentManager(), "timePicker");
                break;
            case R.id.dueTimeButton:
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getFragmentManager(), "datePicker");
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNewTaskSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //handle task creation
    private Task createTask() {

        EditText title = (EditText) getActivity().findViewById(R.id.editTitle);
        EditText desc = (EditText) getActivity().findViewById(R.id.editDesc);
        String newTitle = title.getText().toString();
        String newDesc = desc.getText().toString();
        Task newTask = new Task(newTitle, newDesc);

        return newTask;
    }

    //listener callback
    public interface OnNewTaskSelectedListener {
        public void onTaskAccepted(Task task);
        public void onTaskCanceled();
    }

}
