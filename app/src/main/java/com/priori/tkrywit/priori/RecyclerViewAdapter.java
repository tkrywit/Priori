package com.priori.tkrywit.priori;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Thomas on 11/28/2014.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerItemViewHolder> {

    private final TaskList taskList;
    OnItemClickListener mItemClickListener;
    OnItemLongClickListener mItemLongClickListener;
    Activity activity;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(TaskList taskIn, Activity act) {
        activity = act;
        taskList = taskIn;
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
        holder.taskTitleTextView.setText(taskList.getTaskList().get(position).getTitle());
        holder.taskDescTextView.setText(taskList.getTaskList().get(position).getDesc());
        holder.iconTextView.setText(CategoryHelper.getAbbrevName(taskList.getTaskList().get(position).getCategory()));
        if (taskList.getTaskList().get(position).getPriority() == 0) {
            holder.iconTextView.setBackground(activity.getResources().getDrawable(R.drawable.circle_critical));
        }

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskList.getTaskList().size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener{

        //views
        public TextView taskTitleTextView;
        public TextView taskDescTextView;
        public TextView iconTextView;

        //bind views
        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            taskTitleTextView = (TextView) itemView.findViewById(R.id.taskTitle);
            taskDescTextView = (TextView) itemView.findViewById(R.id.taskDesc);
            iconTextView = (TextView) itemView.findViewById(R.id.taskIcon);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onItemLongClick(v, getPosition());
            }
            return true;
        }
    }




}

