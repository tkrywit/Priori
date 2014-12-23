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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends Activity
        implements MainListFragment.OnFragmentInteractionListener, NewTaskFragment.OnNewTaskSelectedListener,
                    DatePickerFragment.datePickedCallback, TimePickerFragment.timePickedCallback,
                    PriorityFragment.OnPriorityInteractionListener {

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

    public void onRecyclerItemLongClick(int item) {
        Log.d("Gubs", "long click");
    }

    public void onTaskAccepted(Task task) {
        if (task != null) {
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();
            MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
            mainFrag.addNewTask(task);
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.material_grey_700));
    }

    public void onTaskCanceled() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.material_grey_700));
        getFragmentManager().popBackStack();
    }

    public void setDate(Calendar cal) {

        //ensure date is not in past
        if (cal.before(Calendar.getInstance())) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.date_in_past, Toast.LENGTH_LONG);
            toast.show();
        } else {
            FragmentManager fm = getFragmentManager();
            NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
            newTaskFragment.passDate(cal);
        }
    }

    public void setTime(Calendar cal) {
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.passTime(cal);
    }

    public void updateCategoryList() {
        FragmentManager fm = getFragmentManager();
        MainListFragment mainTaskFragment = (MainListFragment) fm.findFragmentByTag("mainFrag");
        ArrayList<String> cats = mainTaskFragment.getCategoryList();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.setCategoryList(cats);
    }

    public void onPrioritySelected(int which) {
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.passPriority(which);
    }
}
