package com.priori.tkrywit.priori;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas on 12/5/2014.
 */
public class JsonUtilityTest extends TestCase {

    JsonUtility jsonUtil;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        jsonUtil = new JsonUtility();
    }

    public void testJsonCreation() {
        Date due = new Date();
        Task t1 = new Task("Test Title", "Test Desc", due, 1 );
        Task t2 = new Task("Test Title", "Test Desc", due, 1 );

        ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(t1);
        taskList.add(t2);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
