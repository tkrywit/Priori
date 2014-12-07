package com.priori.tkrywit.priori;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thomas on 11/28/2014.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListItemViewHolder> {

    public final ArrayList<Task> tasks;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListViewAdapter(ArrayList<Task> taskIn) {
        tasks = taskIn;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view based on task list row
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_row, parent, false);

        return new ListItemViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {

        //bind views to data
        holder.taskTitleTextView.setText(tasks.get(position).getTitle());
        holder.taskDescTextView.setText(tasks.get(position).getDesc());

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {

        //views
        public TextView taskTitleTextView;
        public TextView taskDescTextView;

        //bind views
        public ListItemViewHolder(View itemView) {
            super(itemView);
            taskTitleTextView = (TextView) itemView.findViewById(R.id.taskTitle);
            taskDescTextView = (TextView) itemView.findViewById(R.id.taskDesc);
        }
    }


}

