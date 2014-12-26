package com.priori.tkrywit.priori;

/**
 * Created by Thomas on 12/24/2014.
 */

import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<DrawerRecyclerAdapter.RecyclerDrawerViewHolder> {

    ArrayList<String> categoryList;
    OnItemClickListener itemClickListener;

    public DrawerRecyclerAdapter(ArrayList<String> catIn) {
        categoryList = catIn;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view based on task list row
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_item, parent, false);

        return new RecyclerDrawerViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerDrawerViewHolder holder, int position) {

        //bind views to data
        holder.categoryTitle.setText(categoryList.get(position));
        if (position == (categoryList.size() - 1)) {
                holder.categoryIcon.setText("");
        } else {
            holder.categoryIcon.setText(CategoryHelper.getAbbrevName(categoryList.get(position)));
        }
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    public class RecyclerDrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //views
        TextView categoryTitle;
        TextView categoryIcon;

        //bind views
        public RecyclerDrawerViewHolder(View itemView) {
            super(itemView);

            categoryTitle = (TextView) itemView.findViewById(R.id.drawerTitle);
            categoryIcon = (TextView) itemView.findViewById(R.id.drawerIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getPosition());
            }
        }
    }




}