package com.example.concussionapp;

/**
 * Created by scabandari on 05/04/17.
 */

public class SharedData {

    public static User user;
    public static String data = "";
    public static final int size = 4;
    public static String[] dataArray = new String[size];
    SharedData(User user) {
        this.user = user;
        data = "";
        for(int i=0; i<size; i++) {
            dataArray[i] = "";
        }
    }
}