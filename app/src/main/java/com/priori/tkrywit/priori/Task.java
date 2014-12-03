package com.priori.tkrywit.priori;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas on 11/26/2014.
 */
public class Task {
    String title;
    String desc;
    String category;

    Date dueDate;
    Date createdDate;
    int priority;
    //ArrayList<Task> subTasks;

    public Task(String titleIn, String descIn, Date due, int pri) {
        title = titleIn;
        desc = descIn;
        createdDate = new Date();
        dueDate = due;
        priority = pri;
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

    Date getCreatedDate() { return createdDate; }

    Date getDueDate() { return dueDate; }

    int getPriority() { return priority; }

    //ArrayList<Task> getSubTasks() { return subTasks; }





}
