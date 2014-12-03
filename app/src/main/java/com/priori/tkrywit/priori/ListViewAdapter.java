package com.priori.tkrywit.priori;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thomas on 11/28/2014.
 */
public class ListViewAdapter extends ArrayAdapter<Task> {

    public final Context context;
    public final ArrayList<Task> tasks;

    public ListViewAdapter(Context context, ArrayList<Task> tasks) {
        super(context, R.layout.task_list_row, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_list_row, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.taskTitle);
        TextView descTextView = (TextView) rowView.findViewById(R.id.taskDesc);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        titleTextView.setText(tasks.get(position).getTitle());
        descTextView.setText(tasks.get(position).getDesc());

        return rowView;
    }



}

