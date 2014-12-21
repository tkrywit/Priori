package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;


public class MainActivity extends Activity
        implements MainListFragment.OnFragmentInteractionListener, NewTaskFragment.OnNewTaskSelectedListener,
                    DatePickerFragment.datePickedCallback, TimePickerFragment.timePickedCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainListFragment(), "mainFrag")
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //callback method for the floating action button
    public void onNewTaskClick() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new NewTaskFragment(), "newTaskFrag");
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //handle list clicks by launching the item view fragment
    public void onRecyclerItemClick(int item) {
        String s = String.valueOf(item);
        Log.d("Gubs", "Pressedddd" + s);
    }

    public void onTaskAccepted(Task task) {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
        MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
        mainFrag.addNewTask(task);

    }

    public void onTaskCanceled() {
        getFragmentManager().popBackStack();
    }

    public void showDatePicker() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePicker() {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getFragmentManager(), "timePicker");
    }

    public void setDate(Calendar cal) {
        Log.d("Gubs", cal.toString());
    }

    public void setTime(Calendar cal) {
        Log.d("Gubs", cal.toString());
    }
}
