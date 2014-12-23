package com.priori.tkrywit.priori;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thomas on 11/26/2014.
 */
public class Task {
    Calendar dueDate;
    Calendar createdDate;
    private String title;
    private String desc;
    private String category;
    private int priority;

    //ArrayList<Task> subTasks;

    public Task(String titleIn, String descIn, String cat, Calendar created, Calendar due, int pri) {
        title = titleIn;
        desc = descIn;
        category = cat;
        dueDate = due;
        priority = pri;

        if (created == null) {
            createdDate = Calendar.getInstance();
            Log.d("Gubs", "Created new date!");
        }
    }

    public Task(String titleIn, String descIn, String cat, int pri) {
        title = titleIn;
        desc = descIn;
        priority = pri;
        category = cat;
        dueDate = null;
        createdDate = Calendar.getInstance();
    }



    String getTitle() {
        return title;
    }

    String getDesc() {
        return desc;
    }

    String getCategory() {
        return category;
    }

    Calendar getCreatedDate() { return createdDate; }

    Calendar getDueDate() { return dueDate; }

    int getPriority() { return priority; }

    //ArrayList<Task> getSubTasks() { return subTasks; }





}
