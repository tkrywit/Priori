package com.priori.tkrywit.priori;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas on 12/3/2014.
 */
public class JsonUtility {

    public static String taskListToJson(ArrayList<Task> taskList) {

        JSONArray jsonArray;

        try {
            //create JSON object
            jsonArray = new JSONArray();

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

            return jsonArray.toString();
        }
        catch(JSONException ex) {
            ex.printStackTrace();

            return null;
        }
    }

    public ArrayList<Task> jsonToTaskList(String jsonIn) {

        ArrayList<Task> finalArray = new ArrayList<Task>();
        JSONArray jsonArray;

        try {
            //rebuild primary task list
            jsonArray = new JSONArray(jsonIn);
            ArrayList<Task> taskList = new ArrayList<Task>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                String title = jsonObj.getString("title");
                String desc = jsonObj.getString("desc");
                String category = jsonObj.getString("category");
                //Date dateCreated = jsonObj.getJSONObject();
                //Date dateDue = jsonObj.getJSONObject();
                int priority = jsonObj.getInt("priority");

                Date testDate1 = new Date();

                Task task = new Task(title, desc, category, testDate1, testDate1, priority);
                taskList.add(task);
            }
            return finalArray;
        }
        catch(JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
