package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
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

public class NewTaskFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private int currentCategory;
    private OnNewTaskSelectedListener mListener;
    private TextView dateTextView;
    private TextView timeTextView;
    private Button priorityButton;
    private Calendar dueDate;
    private Calendar dueTime;
    private int priority;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> categories;
    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dueDate = null;
        dueTime = null;
        priority = 4;
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
        spinner = (Spinner) view.findViewById(R.id.categorySpinner);

        //request current category list from main list frag via activity
        mListener.updateCategoryList();
        currentCategory = 0;

        adapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //set spinner to "Uncategorized"
        spinner.setSelection(categories.size() - 2);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        //hide action bar items we don't need
        MenuItem sort = menu.findItem(R.id.action_sort);
        sort.setVisible(false);
        MenuItem settings = menu.findItem(R.id.action_settings);
        settings.setVisible(false);

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
        //if New Category selected
        if (pos == (categories.size() - 1)) {
            DialogFragment catFragment = new NewCategoryFragment();
            catFragment.show(getFragmentManager(), "category");
        } else {
            currentCategory = pos;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        spinner.setSelection(0);
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

        //if title is not set
        if (newTitle.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), R.string.new_task_requires, Toast.LENGTH_LONG);
            toast.show();
            return null;
        }

        //if time is set but date is not
        if (dueDate == null && dueTime != null) {
            Toast toast = Toast.makeText(getActivity(), R.string.time_not_set, Toast.LENGTH_LONG);
            toast.show();
            return null;
        }

        //combine date and time fields into one calendar
        if (dueDate != null && dueTime != null) {
            dueDate.set(Calendar.HOUR, dueTime.get(Calendar.HOUR));
            dueDate.set(Calendar.MINUTE, dueTime.get(Calendar.HOUR));
        }

        if (dueDate == null && dueTime == null) {
            return new Task(newTitle, newDesc, categories.get(currentCategory), null, null, priority);
        }

        return new Task(newTitle, newDesc, categories.get(currentCategory), null, dueDate, priority);
    }

    //pass the calendar object from date picker back to the fragment
    public void passDate(Calendar cal) {
        //ensure date is not in past
        if (cal.before(Calendar.getInstance())) {
            Toast.makeText(getActivity(), R.string.date_in_past, Toast.LENGTH_LONG).show();
        } else {
            dueDate = cal;
            dateTextView.setText(CalendarHelper.getDateString(cal));
        }
    }

    //pass the calendar object from time picker back to the fragment
    public void passTime(Calendar cal) {
        //ensure time is not in the past
        if (dueDate == null) {
            if (cal.before(Calendar.getInstance())) {
                Toast.makeText(getActivity(), R.string.time_in_past, Toast.LENGTH_LONG).show();
            } else {
                dueTime = cal;
                timeTextView.setText(CalendarHelper.getTimeString(cal));
            }
        } else {
            //if we are setting due time to sometime on the same day
            if (cal.get(Calendar.DAY_OF_YEAR) == dueDate.get(Calendar.DAY_OF_YEAR)) {
                if (cal.before(dueDate)) {
                    Toast.makeText(getActivity(), R.string.time_in_past, Toast.LENGTH_LONG).show();
                } else {
                    dueTime = cal;
                    timeTextView.setText(CalendarHelper.getTimeString(cal));
                }
            } else {
                dueTime = cal;
                timeTextView.setText(CalendarHelper.getTimeString(cal));
            }
        }
    }

    public void passPriority(int pri) {
        priority = pri;
        priorityButton.setText(getResources().getStringArray(R.array.priorities)[pri]);

        //get desired colors from string array resource of priority colors
        //avoids long switch-case statement
        TypedArray priorityColors = getResources().obtainTypedArray(R.array.priority_colors);
        TypedArray priorityColorsAccent = getResources().obtainTypedArray(R.array.priority_colors_accent);

        getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(priorityColorsAccent.getResourceId(pri, -1)));
        priorityButton.setTextColor(getResources().getColor(priorityColors.getResourceId(pri, -1)));

        priorityColors.recycle();
        priorityColorsAccent.recycle();
    }

    public void setCategoryList(ArrayList<String> cats) {
        categories = cats;
    }

    public void setSpinnerZero() {
        spinner.setSelection(0);
    }

    //listener callback
    public interface OnNewTaskSelectedListener {
        public void onTaskAccepted(Task task);
        public void onTaskCanceled();

        //get or update category list
        public void updateCategoryList();
    }

}
