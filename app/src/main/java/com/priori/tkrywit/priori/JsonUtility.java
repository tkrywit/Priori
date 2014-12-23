package com.priori.tkrywit.priori;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Thomas on 12/3/2014.
 */
public class JsonUtility {

    Activity act;

    public JsonUtility(Activity con) {
        act = con;
    }

    private String taskListToJson(TaskList taskList) {

        JSONArray jsonArray;

        try {
            //create JSON object
            jsonArray = new JSONArray();

            //serialize categories
            JSONObject jsonObject = new JSONObject();
            ArrayList<String> cats = taskList.getCategoryList();
            for (int i = 0; i < cats.size(); i++) {
                //create json handle
                String handle = "Cat" + String.valueOf(i);
                jsonObject.put(handle, cats.get(i));
            }
            jsonArray.put(jsonObject);

            //serialize tasks
            for (Task task : taskList.getTaskList()) {
                //handle primary task fields
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("title", task.getTitle());
                jsonItem.put("desc", task.getDesc());
                jsonItem.put("category", task.getCategory());
                jsonItem.put("priority", task.getPriority());

                //serialize dates
                if (task.getCreatedDate() != null) {
                    jsonItem.put("dateCreated", task.getCreatedDate().getTimeInMillis());
                }
                if (task.getDueDate() != null) {
                    jsonItem.put("dateDue", task.getDueDate().getTimeInMillis());
                }

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

    private TaskList jsonToTaskList(String jsonIn) {

        ArrayList<String> catList = new ArrayList<>();
        ArrayList<Task> taskArray = new ArrayList<>();
        TaskList taskList = new TaskList(act);
        JSONArray jsonArray;

        try {
            //rebuild category list
            jsonArray = new JSONArray(jsonIn);
            JSONObject jsonObj = jsonArray.getJSONObject(0);
            for (int i = 0; i < jsonObj.length(); i++) {
                String handle = "Cat" + String.valueOf(i);
                catList.add(jsonObj.getString(handle));
            }

            //rebuild primary task list
            for (int i = 1; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);

                String title = jsonObj.getString("title");
                String desc = jsonObj.getString("desc");
                String category = jsonObj.getString("category");
                int priority = jsonObj.getInt("priority");

                Calendar dateCreated = Calendar.getInstance();
                Calendar dateDue = Calendar.getInstance();
                if (jsonObj.has("dateCreated")) {
                    dateCreated.setTimeInMillis(jsonObj.getLong("dateCreated"));
                }
                if (jsonObj.has("dateDue")) {
                    dateDue.setTimeInMillis(jsonObj.getLong("dateDue"));
                }

                Task task = new Task(title, desc, category, dateCreated, dateDue, priority);
                taskArray.add(task);
            }

            taskList.setCategoryList(catList);
            taskList.setTaskList(taskArray);
            return taskList;
        }
        catch(JSONException ex) {
            Log.d("Gubs", "Excepted!");
            ex.printStackTrace();
            return null;
        }
    }

    public void saveFile(TaskList list, String fileName) {

        String s = taskListToJson(list);

        try {
            FileOutputStream fileOS = act.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOS.write(s.getBytes());
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TaskList loadFile(String fileName) {
        String s = null;

        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    act.openFileInput(fileName)));
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

         return jsonToTaskList(s);
    }
}
