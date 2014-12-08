package com.priori.tkrywit.priori;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.shamanland.fab.FloatingActionButton;

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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
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

        //initialize task list
        taskList = new ArrayList<>();

        //test code for task list
        Task t1 = new Task("Item1", "Desc", "Cat", new Date(), new Date(), 0);
        Task t2 = new Task("Item2", "Desc", "Cat", new Date(), new Date(), 0);
        Task t3 = new Task("Item3", "Desc", "Cat", new Date(), new Date(), 0);
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main,container,false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        //set up onClick listeners

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
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

        recyclerView.setAdapter(new RecyclerViewAdapter(taskList));
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        public void onNewTaskClick();
    }
}
