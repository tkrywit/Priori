package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;
import java.util.ArrayList;
import java.util.Date;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MainListFragment extends Fragment {

    TaskList taskList;
    JsonUtility jUtil;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private OnFragmentInteractionListener mListener;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to save and load data
        jUtil = new JsonUtility(getActivity());
        taskList = jUtil.loadFile("saveData");
        if (taskList == null) {
            taskList = new TaskList(getActivity());
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main,container,false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        //set up onClick listeners
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
        recyclerView.setOnTouchListener(new ShowHideOnScroll(fab, R.anim.floating_action_button_show, R.anim.floating_action_button_hide));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNewTaskClick();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new RecyclerViewAdapter(taskList.getTaskList());
        //initialize task list
        adapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                mListener.onRecyclerItemClick(position);
            }
        });

        adapter.SetOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {

            @Override
            public void onItemLongClick(View v, int position) {
                mListener.onRecyclerItemLongClick(position);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //add new task to adapter
    public void addNewTask(Task newTask) {
        taskList.getTaskList().add(newTask);
        adapter.notifyDataSetChanged();
        //save data
        jUtil.saveFile(taskList, "saveData");
    }

    //get the category list for passing to other fragments
    public ArrayList<String> getCategoryList() {
        return taskList.getCategoryList();
    }


    public interface OnFragmentInteractionListener {

        public void onNewTaskClick();
        public void onRecyclerItemClick(int item);
        public void onRecyclerItemLongClick(int item);
    }
}
