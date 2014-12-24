package com.priori.tkrywit.priori;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by Thomas on 11/26/2014.
 */
public class Task {
    private Calendar dueDate;
    public static Comparator<Task> COMPARE_BY_DUE_FIRST = new Comparator<Task>() {
        public int compare(Task t1, Task t2) {
            if (t1.dueDate == null && t2.dueDate == null) return 0;
            if (t1.dueDate == null) return 1;
            if (t2.dueDate == null) return -1;
            return t1.dueDate.compareTo(t2.dueDate);
        }
    };
    public static Comparator<Task> COMPARE_BY_DUE_LAST = new Comparator<Task>() {
        public int compare(Task t1, Task t2) {
            if (t1.dueDate == null && t2.dueDate == null) return 0;
            if (t1.dueDate == null) return 1;
            if (t2.dueDate == null) return -1;
            return t2.dueDate.compareTo(t1.dueDate);
        }
    };
    private Calendar createdDate;
    public static Comparator<Task> COMPARE_BY_NEWEST = new Comparator<Task>() {
        public int compare(Task t1, Task t2) {
            return t2.createdDate.compareTo(t1.createdDate);
        }
    };
    public static Comparator<Task> COMPARE_BY_OLDEST = new Comparator<Task>() {
        public int compare(Task t1, Task t2) {
            return t1.createdDate.compareTo(t2.createdDate);
        }
    };

    //ArrayList<Task> subTasks;
    private String title;
    private String desc;
    private String category;
    private int importance;
    //Comparators for sorting
    public static Comparator<Task> COMPARE_BY_PRIORITY = new Comparator<Task>() {
        public int compare(Task t1, Task t2) {

            //this needs work
            if (t1.importance == 0) return -1;
            if (t2.importance == 0) return 1;
            if (t1.importance == 4) return 1;
            if (t2.importance == 4) return -1;
            if (t1.dueDate == null && t2.dueDate == null) {
                if (t1.importance < t2.importance) return -1;
                if (t1.importance > t2.importance) return 1;
                return 0;
            }
            if (t1.dueDate == null) return 1;
            if (t2.dueDate == null) return -1;
            return t1.dueDate.compareTo(t2.dueDate);
        }
    };
    public static Comparator<Task> COMPARE_BY_IMPORTANCE = new Comparator<Task>() {
        public int compare(Task t1, Task t2) {
            if (t1.importance < t2.importance) return -1;
            if (t1.importance > t2.importance) return 1;

            //if importance equal, return whichever is due first
            if (t1.dueDate == null) return 1;
            if (t2.dueDate == null) return -1;
            return t1.dueDate.compareTo(t2.dueDate);
        }
    };

    public Task(String titleIn, String descIn, String cat, Calendar created, Calendar due, int pri) {
        title = titleIn;
        desc = descIn;
        category = cat;
        dueDate = due;
        importance = pri;

        if (created == null) {
            createdDate = Calendar.getInstance();
        } else {
            createdDate = created;
        }
    }

    //ArrayList<Task> getSubTasks() { return subTasks; }

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

    int getImportance() { return importance; }

}
