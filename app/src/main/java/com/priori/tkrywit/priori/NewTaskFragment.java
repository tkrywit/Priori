package com.priori.tkrywit.priori;


import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NewTaskFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    String currentCategory;
    private OnNewTaskSelectedListener mListener;
    private TextView dateTextView;
    private TextView timeTextView;
    private Button priorityButton;
    private Calendar dueDate;
    private Calendar dueTime;
    private int priority;
    private ArrayList<String> categories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dueDate = null;
        dueTime = null;

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        //set up button listeners
        Button dateButton = (Button) view.findViewById(R.id.dueDateButton);
        Button timeButton = (Button) view.findViewById(R.id.dueTimeButton);
        priorityButton = (Button) view.findViewById(R.id.priorityButton);
        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        priorityButton.setOnClickListener(this);

        //set up textviews
        dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);

        //set up spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.categorySpinner);

        //request current category list from main list frag via activity
        mListener.updateCategoryList();
        categories.add(getResources().getString(R.string.add_category));
        currentCategory = categories.get(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.dueTimeButton:
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getFragmentManager(), "timePicker");
                break;
            case R.id.priorityButton:
                DialogFragment priorityFragment = new PriorityFragment();
                priorityFragment.show(getFragmentManager(), "priority");
                break;
        }
    }

    //handle spinner selection
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.d("Gubs", "Spinner item selected at " + String.valueOf(pos));

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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

    //create a task object from the fields shown in the fragment
    //ensure required fields are filled
    //called when user clicks accept action bar button
    //if all fields not filled, return null
    private Task createTask() {

        EditText title = (EditText) getActivity().findViewById(R.id.editTitle);
        EditText desc = (EditText) getActivity().findViewById(R.id.editDesc);
        String newTitle = title.getText().toString();
        String newDesc = desc.getText().toString();
        //if title or description are not set
        if (newTitle.length() == 0 || newDesc.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), R.string.new_task_requires, Toast.LENGTH_LONG);
            toast.show();
            return null;
        }

        //if date is set but time is not
        if (dueDate != null && dueTime == null) {
            Toast toast = Toast.makeText(getActivity(), R.string.date_not_set, Toast.LENGTH_LONG);
            toast.show();
            return null;
        }

        //if time is set but date is not
        if (dueDate == null && dueTime != null) {
            Toast toast = Toast.makeText(getActivity(), R.string.date_not_set, Toast.LENGTH_LONG);
            toast.show();
            return null;
        }

        Task newTask;

        if (dueDate != null && dueTime != null) {
            dueDate.set(Calendar.HOUR, dueTime.get(Calendar.HOUR));
            dueDate.set(Calendar.MINUTE, dueTime.get(Calendar.HOUR));
            newTask = new Task(newTitle, newDesc, currentCategory, null, dueDate, priority);

            return newTask;
        }

        newTask = new Task(newTitle, newDesc);

        return newTask;
    }

    //pass the calendar object from date picker back to the fragment
    public void passDate(Calendar cal) {
        dueDate = cal;
        dateTextView.setText(CalendarHelper.getDateString(cal));

    }

    //pass the calendar object from time picker back to the fragment
    public void passTime(Calendar cal) {
        dueTime = cal;
        timeTextView.setText(CalendarHelper.getTimeString(cal));
    }

    public void passPriority(int pri) {
        priority = pri;
        priorityButton.setText(getResources().getStringArray(R.array.priorities)[pri]);

        //not sure if we actually want to do this from a design perspective
        switch (pri) {
            case 0:
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.material_red_A400));
                break;
            case 1:
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.material_orange_700));
                break;
            case 2:
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.material_yellow_A400));
                break;
            case 3:
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.material_green_A400));
                break;
            default:
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.material_grey_700));
                break;
        }
    }

    public void setCategoryList(ArrayList<String> cats) {
        categories = cats;
    }

    //listener callback
    public interface OnNewTaskSelectedListener {
        public void onTaskAccepted(Task task);
        public void onTaskCanceled();

        //get or update category list
        public void updateCategoryList();
    }

}
