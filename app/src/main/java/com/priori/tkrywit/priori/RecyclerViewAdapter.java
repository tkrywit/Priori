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
    OnItemClickListener mItemClickListener;

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

        return new RecyclerItemViewHolder(v);
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

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //views
        public TextView taskTitleTextView;
        public TextView taskDescTextView;

        //bind views
        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            taskTitleTextView = (TextView) itemView.findViewById(R.id.taskTitle);
            taskDescTextView = (TextView) itemView.findViewById(R.id.taskDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }




}

