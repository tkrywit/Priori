package com.priori.tkrywit.priori;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Thomas on 11/28/2014.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerItemViewHolder> {

    public final ArrayList<Task> tasks;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(ArrayList<Task> taskIn) {
        tasks = taskIn;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view based on task list row
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_row, parent, false);

        return new RecyclerItemViewHolder(v, new RecyclerItemViewHolder.ViewHolderClick() {
            public void onItemClick(int position) {
                String s = String.valueOf(position);
                Log.d("GUBS", "Pressed!" + s);
            }

        });
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {

        //bind views to data
        holder.taskTitleTextView.setText(tasks.get(position).getTitle());
        holder.taskDescTextView.setText(tasks.get(position).getDesc());
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public final static class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //views
        public TextView taskTitleTextView;
        public TextView taskDescTextView;

        //viewholder click
        public ViewHolderClick vc;

        //bind views
        public RecyclerItemViewHolder(View itemView, ViewHolderClick listener) {
            super(itemView);
            taskTitleTextView = (TextView) itemView.findViewById(R.id.taskTitle);
            taskDescTextView = (TextView) itemView.findViewById(R.id.taskDesc);

            //needed?
            vc = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            vc.onItemClick(getPosition());
        }

        public static interface ViewHolderClick {
            public void onItemClick(int position);
        }

    }


}

