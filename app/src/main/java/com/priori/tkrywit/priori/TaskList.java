package com.priori.tkrywit.priori;

import android.app.Activity;
import java.util.ArrayList;

/**
 * Created by Thomas on 12/22/2014.
 * Container class for a category and task list
 */
public class TaskList {
    Activity activity;
    private ArrayList<String> categoryList;
    private ArrayList<Task> taskList;

    public TaskList(Activity act) {
        categoryList = new ArrayList<>();
        taskList = new ArrayList<>();
        activity = act;
        categoryList.add(activity.getString(R.string.uncategorized));
        categoryList.add(activity.getString(R.string.add_category));
    }


    public void addCategory(String category) {
        categoryList.add(0, category);
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskListIn) {
        taskList = taskListIn;
    }

    public ArrayList<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<String> catIn) {
        categoryList = catIn;
    }

    public void deleteListItem(int item) {
        taskList.remove(item);
    }

}
