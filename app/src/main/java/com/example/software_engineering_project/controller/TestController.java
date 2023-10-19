package com.example.software_engineering_project.controller;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TestController extends ListScreenController<String> {
/**
     * @param activity is the activity for a controller of a certain screen
     * @param adapter
 * */


    public TestController(int activity, ArrayAdapter<String> adapter) {
        super(activity, adapter);
    }

    @Override
    public void addButtons() {

    }

    @Override
    public ArrayList<String> loadItems() {
        ArrayList<String> list = new ArrayList();

        //Auslesebefehl für Datenbank
        list.add("Apple");
        list.add("Banana");
        list.add("Orange");
        list.add("Strawberry");
        list.add("Kiwi");
        return list;
    }
}
