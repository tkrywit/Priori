package com.priori.tkrywit.priori;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by Thomas on 11/28/2014.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerItemViewHolder> {

    private final TaskList taskList;
    OnItemClickListener mItemClickListener;
    OnItemLongClickListener mItemLongClickListener;
    private Activity activity;
    private boolean[] expandedList;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(TaskList taskIn, Activity act) {
        activity = act;
        taskList = taskIn;
        expandedList = new boolean[taskList.getActiveList().size()];
        Arrays.fill(expandedList, Boolean.FALSE);
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
        holder.taskTitleTextView.setText(taskList.getActiveList().get(position).getTitle());
        holder.taskDescTextView.setText(taskList.getActiveList().get(position).getDesc());
        holder.iconTextView.setText(CategoryHelper.getAbbrevName(taskList.getActiveList().get(position).getCategory()));

        if (expandedList[position]) {
            holder.expLayout.setVisibility(View.VISIBLE);
            holder.priorityTextView.setText(activity.getResources()
                    .getStringArray(R.array.priorities)[taskList.getActiveList().get(position).getImportance()]);

            //too much for UI thread?
            TypedArray priorityColors = activity.getResources().obtainTypedArray(R.array.priority_colors);
            holder.priorityTextView.setTextColor(activity.getResources().getColor(priorityColors.
                    getResourceId(taskList.getActiveList().get(position).getImportance(), -1)));
            priorityColors.recycle();

            holder.categoryTextView.setText(taskList.getActiveList().get(position).getCategory());
            holder.dueDateTextView.setText(CalendarHelper.getDateTimeString(activity, taskList.getActiveList().get(position).getDueDate()));
            holder.editTaskImageView.setVisibility(View.VISIBLE);
        } else {
            holder.expLayout.setVisibility(View.GONE);
            holder.editTaskImageView.setVisibility(View.GONE);
        }

            //change circle logo colors depending on priority
        switch (taskList.getActiveList().get(position).getImportance()) {
            case 0:
                holder.iconTextView.setBackground(activity.getResources().getDrawable(R.drawable.circle_critical));
                break;
            case 1:
                holder.iconTextView.setBackground(activity.getResources().getDrawable(R.drawable.circle_high));
                break;
            case 2:
                holder.iconTextView.setBackground(activity.getResources().getDrawable(R.drawable.circle_med));
                break;
            case 3:
                holder.iconTextView.setBackground(activity.getResources().getDrawable(R.drawable.circle_low));
                break;
            default:
                holder.iconTextView.setBackground(activity.getResources().getDrawable(R.drawable.circle_base));
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskList.getActiveList().size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    //set inflated list view item
    public void setExpandedItem(int exp) {
        //toggle list of expanded items
        expandedList[exp] = !expandedList[exp];
    }

    //close all expanded items
    public void contractAllItems() {
        Arrays.fill(expandedList, Boolean.FALSE);
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
        public TextView priorityTextView;
        public TextView dueDateTextView;
        public TextView categoryTextView;
        public ImageView editTaskImageView;
        public RelativeLayout expLayout;


        //bind views
        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            taskTitleTextView = (TextView) itemView.findViewById(R.id.taskTitle);
            taskDescTextView = (TextView) itemView.findViewById(R.id.taskDesc);
            iconTextView = (TextView) itemView.findViewById(R.id.taskIcon);
            priorityTextView = (TextView) itemView.findViewById(R.id.listExpandedPriority);
            dueDateTextView = (TextView) itemView.findViewById(R.id.listExpandedDueDate);
            categoryTextView = (TextView) itemView.findViewById(R.id.listExpandedCategory);
            editTaskImageView = (ImageView) itemView.findViewById(R.id.editButton);
            expLayout = (RelativeLayout) itemView.findViewById(R.id.expandListItem);

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

