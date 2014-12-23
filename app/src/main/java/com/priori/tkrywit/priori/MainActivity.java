package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity
        implements MainListFragment.OnFragmentInteractionListener, NewTaskFragment.OnNewTaskSelectedListener,
                    DatePickerFragment.datePickedCallback, TimePickerFragment.timePickedCallback,
                    PriorityFragment.OnPriorityInteractionListener, NewCategoryFragment.OnNewCategoryInteractionListener {

    Menu actionMenu;
    int listSelectedItem;
    private TaskList taskList;
    private JsonUtility jUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //need to save and load data
        jUtil = new JsonUtility(this);
        taskList = jUtil.loadFile("saveData");
        if (taskList == null) {
            taskList = new TaskList(this);
        }

        if (savedInstanceState == null) {
            FragmentManager fm = getFragmentManager();
            MainListFragment mainFrag = new MainListFragment();
            mainFrag.updateTaskList(taskList);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mainFrag, "mainFrag")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        actionMenu = menu;
        listSelectedItem = 0;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case (R.id.action_settings):
                return true;
            case (R.id.action_delete):
                taskList.deleteListItem(listSelectedItem);
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
                MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
                mainFrag.updateTaskList(taskList);
                jUtil.saveFile(taskList, "saveData");

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
        listSelectedItem = item;
        MenuItem menuItem = actionMenu.findItem(R.id.action_delete);
        menuItem.setVisible(true);
    }

    public void onTaskAccepted(Task task) {
        if (task != null) {
            taskList.getTaskList().add(task);
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();
            MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
            mainFrag.updateTaskList(taskList);
            jUtil.saveFile(taskList, "saveData");
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.material_grey_700));

        //hide soft keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onTaskCanceled() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.material_grey_700));
        getFragmentManager().popBackStack();

        //hide soft keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.setCategoryList(taskList.getCategoryList());
    }

    public void onPrioritySelected(int which) {
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.passPriority(which);
    }

    public void newCategory(String newCat) {
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.addNewCategory(newCat);
    }

    public void addCategory(String category) {
        taskList.addCategory(category);
    }

    public void getCurrentTaskList() {
        FragmentManager fm = getFragmentManager();
        MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
        mainFrag.updateTaskList(taskList);
    }

}
