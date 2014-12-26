package com.priori.tkrywit.priori;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Thomas on 12/22/2014.
 * Container class for a category and task list
 */
public class TaskList {
    Activity activity;
    String lastCategory;
    MainActivity.SORT_TYPE currentSort;
    private ArrayList<String> categoryList;
    private ArrayList<Task> activeList;
    private ArrayList<Task> masterList;

    public TaskList(Activity act) {
        categoryList = new ArrayList<>();
        activeList = new ArrayList<>();
        masterList = new ArrayList<>();
        activity = act;
        lastCategory = null;
        currentSort = MainActivity.SORT_TYPE.PRIORITY;
        categoryList.add(activity.getString(R.string.uncategorized));
        categoryList.add(activity.getString(R.string.add_category));
    }


    public void addCategory(String category) {
        categoryList.add(0, category);
    }

    public ArrayList<Task> getActiveList() {
        return activeList;
    }

    public ArrayList<Task> getMasterList() { return masterList; }

    public void setTaskList(ArrayList<Task> taskListIn) {
        masterList = taskListIn;
    }

    public ArrayList<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<String> catIn) {
        categoryList = catIn;
    }

    public void addTask(Task task) {
        masterList.add(0, task);
        setCategorySublist(lastCategory);
        sort(currentSort);
    }

    public void deleteListItem(int item) {
        masterList.remove(item);
        setCategorySublist(lastCategory);
    }

    public void setCategorySublist(String category) {
        Log.d("Gubs", category + " " + masterList.toString());
        if (category == null) {
            activeList = masterList;
            sort(currentSort);
            lastCategory = null;
        } else {
            //works, but is not ideal due to performance costs
            activeList = new ArrayList<>();
            for (Task task : masterList) {
                //if task category match desired category
                if (task.getCategory().equals(category)) {
                    activeList.add(task);
                }
            }
            lastCategory = category;
            //sort to keep list sorting consistent between list switches
            //should implement in a runnable?
            sort(currentSort);
        }
    }

    public void sort(MainActivity.SORT_TYPE type) {
        currentSort = type;

        switch (type) {
            case PRIORITY:
                Collections.sort(activeList, Task.COMPARE_BY_PRIORITY);
                break;
            case IMPORTANCE:
                Collections.sort(activeList, Task.COMPARE_BY_IMPORTANCE);
                break;
            case DUE_FIRST:
                Collections.sort(activeList, Task.COMPARE_BY_DUE_FIRST);
                break;
            case DUE_LAST:
                Collections.sort(activeList, Task.COMPARE_BY_DUE_LAST);
                break;
            case NEWEST:
                Collections.sort(activeList, Task.COMPARE_BY_NEWEST);
                break;
            case OLDEST:
                Collections.sort(activeList, Task.COMPARE_BY_OLDEST);
                break;

        }
    }

}
