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


            return jsonArray.toString();
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }

    }

    public ArrayList<Task> jsonToTaskList(String jsonIn) {

        try {
            //rebuild primary task list
            JSONArray jsonArray = new JSONArray(jsonIn);
            ArrayList<Task> = new ArrayList<Task>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                String title = jsonObj.getString("title");
                String desc = jsonObj.getString("desc");
                String category = jsonObj.getString("category");
                Date dateCreated = jsonObj.getJSONObject()


            }



        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }

    }
}
