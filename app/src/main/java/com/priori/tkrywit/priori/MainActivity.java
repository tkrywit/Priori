package com.priori.tkrywit.priori;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends Activity
        implements MainListFragment.OnFragmentInteractionListener, NewTaskFragment.OnNewTaskSelectedListener,
                    DatePickerFragment.datePickedCallback, TimePickerFragment.timePickedCallback,
                    PriorityFragment.OnPriorityInteractionListener, NewCategoryFragment.OnNewCategoryInteractionListener {

    Menu actionMenu;
    int listSelectedItem;
    private boolean itemSelected;
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
            MainListFragment mainFrag = new MainListFragment();
            mainFrag.updateTaskList(taskList);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mainFrag, "mainFrag")
                    .commit();
        }
        itemSelected = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        actionMenu = menu;
        listSelectedItem = 0;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case (R.id.action_settings):
                Log.d("Gubs", "Settings works");
                break;
            case (R.id.action_delete):
                FragmentManager fm = getFragmentManager();
                Log.d("Gubs", "Delete works");
                taskList.deleteListItem(listSelectedItem);
                fm.popBackStack();
                MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
                mainFrag.updateTaskList(taskList);
                mainFrag.notifyListDeletion(listSelectedItem);
                itemSelected = false;
                jUtil.saveFile(taskList, "saveData");
                actionMenu.findItem(R.id.action_delete).setVisible(false);
                break;
            case (R.id.menuSortPriority):
                sortTaskList(SORT_TYPE.PRIORITY);
                break;
            case (R.id.menuSortImportance):
                sortTaskList(SORT_TYPE.IMPORTANCE);
                break;
            case (R.id.menuSortDueFirst):
                sortTaskList(SORT_TYPE.DUE_FIRST);
                break;
            case (R.id.menuSortDueLast):
                sortTaskList(SORT_TYPE.DUE_LAST);
                break;
            case (R.id.menuSortNewest):
                sortTaskList(SORT_TYPE.NEWEST);
                break;
            case (R.id.menuSortOldest):
                sortTaskList(SORT_TYPE.OLDEST);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortTaskList(SORT_TYPE type) {

        //better way to do this
        switch (type) {
            case PRIORITY:
                Collections.sort(taskList.getTaskList(), Task.COMPARE_BY_PRIORITY);
                break;
            case IMPORTANCE:
                Collections.sort(taskList.getTaskList(), Task.COMPARE_BY_IMPORTANCE);
                break;
            case DUE_FIRST:
                Collections.sort(taskList.getTaskList(), Task.COMPARE_BY_DUE_FIRST);
                break;
            case DUE_LAST:
                Collections.sort(taskList.getTaskList(), Task.COMPARE_BY_DUE_LAST);
                break;
            case NEWEST:
                Collections.sort(taskList.getTaskList(), Task.COMPARE_BY_NEWEST);
                break;
            case OLDEST:
                Collections.sort(taskList.getTaskList(), Task.COMPARE_BY_OLDEST);
                break;

        }
        //set title bar to respective sort method
        try {
            getActionBar().setTitle(getResources().getStringArray(R.array.sort_types)[type.ordinal()]);
        } catch (NullPointerException np) {
            np.printStackTrace();
        }
        FragmentManager fm = getFragmentManager();
        MainListFragment frag = (MainListFragment) fm.findFragmentByTag("mainFrag");
        frag.updateTaskList(taskList);
        frag.contractAllListItems();
        frag.notifyListChange();
    }

    //callback method for the floating action button
    public void onNewTaskClick() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new NewTaskFragment(), "newTaskFrag");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //Callback methods below

    //handle list clicks by launching the item view fragment
    public void onRecyclerItemClick(int item) {
        FragmentManager fm = getFragmentManager();
        MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");

        MenuItem menuItem = actionMenu.findItem(R.id.action_delete);
        menuItem.setVisible(false);

        //temporary solution to deselecting tasks when cancelling deletion
        Log.d("Gubs", String.valueOf(itemSelected));
        if (itemSelected) {
            mainFrag.deselectItem();
            itemSelected = false;
        } else {
            mainFrag.expandListItem(item);
        }
    }

    public void onRecyclerItemLongClick(int item) {
        listSelectedItem = item;
        itemSelected = true;
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
        //ensure time is not in the past
        if (cal.before(Calendar.getInstance())) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.time_in_past, Toast.LENGTH_LONG);
            toast.show();
        } else {
            FragmentManager fm = getFragmentManager();
            NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
            newTaskFragment.passTime(cal);
        }
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

    enum SORT_TYPE {PRIORITY, IMPORTANCE, DUE_FIRST, DUE_LAST, NEWEST, OLDEST}

}
