package com.priori.tkrywit.priori;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Thomas on 12/3/2014.
 */
public class JsonUtility {

    public static String taskListToJson(ArrayList<Task> taskList) {

        try {
            //create JSON object
            JSONArray jsonArray = new JSONArray();
            for (Task task : taskList) {
                //handle primary task fields
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("title", task.getTitle());
                jsonItem.put("desc", task.getDesc());
                jsonItem.put("category", task.getCategory());
                jsonItem.put("dateCreated", task.getCreatedDate());
                jsonItem.put("dateDue", task.getDueDate());
                jsonItem.put("priority", task.getPriority());

                jsonArray.put(jsonItem);
                //TO DO - SUBTASKS
            }


            return
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }


    }

    public ArrayList<Task> jsonToTaskList(String jsonIn) {

        try {
            JSONArray jsonArray = new JSONArray(jsonIn);



        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }

    }
}
