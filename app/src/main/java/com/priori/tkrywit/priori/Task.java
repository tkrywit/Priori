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

    public Task(String titleIn, String descIn, String cat, Date created, Date due, int pri) {
        title = titleIn;
        desc = descIn;
        category = cat;
        dueDate = due;
        priority = pri;

        if (created == null) {
            //createdDate = new Date();
        }
    }

    public Task(String titleIn, String descIn) {
        title = titleIn;
        desc = descIn;
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
