package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends Activity
        implements MainListFragment.OnFragmentInteractionListener, NewTaskFragment.OnNewTaskSelectedListener,
                    DatePickerFragment.datePickedCallback, TimePickerFragment.timePickedCallback,
                    PriorityFragment.OnPriorityInteractionListener, NewCategoryFragment.OnNewCategoryInteractionListener {

    Menu actionMenu;
    private int listSelectedItem;
    private boolean itemSelected;
    private TaskList taskList;
    private JsonUtility jUtil;
    private SORT_TYPE currentSort;
    //drawer stuff
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerRecycler;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerRecyclerAdapter drawerAdapter;

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
        //set tasklist to all category view
        taskList.setCategorySublist(null);


        initDrawer();

        if (savedInstanceState == null) {
            MainListFragment mainFrag = new MainListFragment();
            mainFrag.updateTaskList(taskList);
            getFragmentManager().beginTransaction()
                    .add(R.id.container , mainFrag, "mainFrag")
                    .commit();
        }
        itemSelected = false;
        currentSort = SORT_TYPE.PRIORITY;
    }

    private void initDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerRecycler = (RecyclerView) findViewById(R.id.drawerRecyclerView);
        mDrawerRecycler.setLayoutManager(new LinearLayoutManager(this));
        drawerAdapter = new DrawerRecyclerAdapter(taskList.getCategoryList());
        drawerAdapter.SetOnItemClickListener(new DrawerRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                //if new category selected
                if (position == (taskList.getCategoryList().size() - 1))  {
                    DialogFragment catFragment = new NewCategoryFragment();
                    catFragment.show(getFragmentManager(), "category");
                } else {
                    selectCategory(taskList.getCategoryList().get(position));
                }
                mDrawerLayout.closeDrawers();
            }
        });

        mDrawerRecycler.setAdapter(drawerAdapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

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

        currentSort = type;
        taskList.sort(type);
        //set title bar to respective sort method
        setTitle(getResources().getStringArray(R.array.sort_types)[type.ordinal()]);
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

    //handle list clicks by launching the item view fragment
    public void onRecyclerItemClick(int item) {
        FragmentManager fm = getFragmentManager();
        MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");

        MenuItem menuItem = actionMenu.findItem(R.id.action_delete);
        menuItem.setVisible(false);

        //temporary solution to deselecting tasks when cancelling deletion
        //need a better way to deal with sub lists
        if (itemSelected) {
            mainFrag.deselectItem();
            itemSelected = false;
        } else {
            mainFrag.expandListItem(item);
        }
    }

    //Callback methods below

    public void onRecyclerItemLongClick(int item) {
        listSelectedItem = item;
        itemSelected = true;
        MenuItem menuItem = actionMenu.findItem(R.id.action_delete);
        menuItem.setVisible(true);
    }

    public void onTaskAccepted(Task task) {
        if (task != null) {
            taskList.addTask(task);
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
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.passDate(cal);
    }

    public void setTime(Calendar cal) {
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.passTime(cal);
    }

    public void updateCategoryList() {
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        if (newTaskFragment != null) {
            newTaskFragment.setCategoryList(taskList.getCategoryList());
            newTaskFragment.setSpinnerZero();
        }
    }

    public void onPrioritySelected(int which) {
        FragmentManager fm = getFragmentManager();
        NewTaskFragment newTaskFragment = (NewTaskFragment) fm.findFragmentByTag("newTaskFrag");
        newTaskFragment.passPriority(which);
    }

    //call by new category dialog fragment
    public void newCategory(String newCat) {
        taskList.addCategory(newCat);
        drawerAdapter.notifyDataSetChanged();
        updateCategoryList();

        //newTaskFragment.setSpinnerZero();
    }

    public void getCurrentTaskList() {
        FragmentManager fm = getFragmentManager();
        MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
        mainFrag.updateTaskList(taskList);
    }

    //drawer show all button clicked
    public void showAllCategories(View v) {
        taskList.setCategorySublist(null);
        FragmentManager fm = getFragmentManager();
        MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
        mainFrag.updateTaskList(taskList);
        mainFrag.notifyListChange();
        mDrawerLayout.closeDrawers();
        setSubTitle(getString(R.string.all));
    }

    private void selectCategory(String cat) {
        taskList.setCategorySublist(cat);
        FragmentManager fm = getFragmentManager();
        MainListFragment mainFrag = (MainListFragment) fm.findFragmentByTag("mainFrag");
        mainFrag.updateTaskList(taskList);
        mainFrag.notifyListChange();
        setSubTitle(cat);
    }

    private void setTitle(String title) {
        try {
            getActionBar().setTitle(title);
        } catch (NullPointerException np) {
            np.printStackTrace();
        }
    }

    private void setSubTitle(String sub) {
        try {
            getActionBar().setSubtitle(sub);
        } catch (NullPointerException np) {
            np.printStackTrace();
        }
    }

    enum SORT_TYPE {PRIORITY, IMPORTANCE, DUE_FIRST, DUE_LAST, NEWEST, OLDEST}
}
