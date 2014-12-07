package com.priori.tkrywit.priori;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize task list
        taskList = new ArrayList<>();

        //test code for task list
        Task t1 = new Task("Item1", "Desc", "Cat", new Date(), new Date(), 0);
        Task t2 = new Task("Item2", "Desc", "Cat", new Date(), new Date(), 0);
        Task t3 = new Task("Item3", "Desc", "Cat", new Date(), new Date(), 0);
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);

        //initialize adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ListViewAdapter(taskList));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newTask(View view) {

        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);

    }
}
