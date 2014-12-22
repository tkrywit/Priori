package com.priori.tkrywit.priori;

import android.content.Context;
import java.util.ArrayList;

/**
 * Created by Thomas on 12/22/2014.
 * Container class for a category and task list
 */
public class TaskList {
    Context context;
    private ArrayList<String> categoryList;
    private ArrayList<Task> taskList;

    public TaskList(Context con) {
        categoryList = new ArrayList<>();
        taskList = new ArrayList<>();
        context = con;

        categoryList.add(context.getString(R.string.uncategorized));
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

}
