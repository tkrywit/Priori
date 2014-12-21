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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Task> taskList;
    JsonUtility jUtil;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private OnFragmentInteractionListener mListener;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainListFragment() {
    }

    // TODO: Rename and change types of parameters
    public static MainListFragment newInstance(String param1, String param2) {
        MainListFragment fragment = new MainListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //need to save and load data
        jUtil = new JsonUtility();
        taskList = jUtil.loadFile("saveData", getActivity().getApplicationContext());
        if (taskList == null) {
            Log.d("Gubs", "Null");
            taskList = new ArrayList<>();
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

        adapter = new RecyclerViewAdapter(taskList);
        //initialize task list
        adapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                mListener.onRecyclerItemClick(position);
            };
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
        taskList.add(newTask);
        adapter.notifyDataSetChanged();
        //save data
        jUtil.saveFile(taskList, "saveData", getActivity().getApplicationContext());
    }


    public interface OnFragmentInteractionListener {

        public void onNewTaskClick();
        public void onRecyclerItemClick(int item);
    }
}
