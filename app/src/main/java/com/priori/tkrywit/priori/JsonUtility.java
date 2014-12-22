package com.priori.tkrywit.priori;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thomas on 12/3/2014.
 */
public class JsonUtility {

    private String taskListToJson(ArrayList<Task> taskList) {

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
                jsonItem.put("priority", task.getPriority());

                //serialize dates
                jsonItem.put("dateCreated", task.getCreatedDate().getTimeInMillis());
                jsonItem.put("dateDue", task.getDueDate().getTimeInMillis());

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

    private ArrayList<Task> jsonToTaskList(String jsonIn) {

        ArrayList<Task> finalArray = new ArrayList<Task>();
        JSONArray jsonArray;

        try {
            //rebuild primary task list
            jsonArray = new JSONArray(jsonIn);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                String title = jsonObj.getString("title");
                String desc = jsonObj.getString("desc");
                String category = jsonObj.getString("category");
                int priority = jsonObj.getInt("priority");

                Calendar dateCreated = Calendar.getInstance();
                Calendar dateDue = Calendar.getInstance();
                dateCreated.setTimeInMillis(jsonObj.getLong("dateCreated"));
                dateDue.setTimeInMillis(jsonObj.getLong("dateDue"));

                Task task = new Task(title, desc, category, dateCreated, dateDue, priority);
                finalArray.add(task);
            }
            return finalArray;
        }
        catch(JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void saveFile(ArrayList<Task> list, String fileName, Context con) {

        String s = taskListToJson(list);

        try {
            FileOutputStream fileOS = con.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOS.write(s.getBytes());
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> loadFile(String fileName, Context con) {
        String s = null;

        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    con.openFileInput(fileName)));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            s = stringBuffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Task> list = jsonToTaskList(s);

        return list;
    }
}
