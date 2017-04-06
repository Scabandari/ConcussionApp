package com.example.concussionapp;

/**
 * Created by scabandari on 05/04/17.
 */

public class SharedData {

    public static User user;
    public static String data = "";
    SharedData(User user) {
        this.user = user;
        data = "";
    }
}
