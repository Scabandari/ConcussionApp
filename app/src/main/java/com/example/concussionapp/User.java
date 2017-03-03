package com.example.concussionapp;

/**
 * Created by scabandari on 03/03/17.
 */

public class User {

    //these are the only fields we need in our database so far besides primary key
    String userName;
    String passWord;
    String careProviderEmailAddress; //not users email but their care provider so this person can be
    //updated on their progress via email. we can ad

    //contructor used when creating a user profile
    User(String name, String pwrd, String email)
    {
        userName = name;
        passWord = pwrd;
        careProviderEmailAddress = email;
    }

    //empty conctructor used when fetching a profile from database
    //then setters get called
    User() {
        userName = "";
        passWord = "";
        careProviderEmailAddress = "";
    }

    // getters & setters here
    public String getUserName() { return userName; }
    public String getPassWord() { return passWord; }
    public String getCareProviderEmailAddress() { return careProviderEmailAddress; }

    public void setUserName(String name) { userName = name; } //not sure if we'll need this one
    public void setPassWord(String pwrd) { passWord = pwrd; }
    public void setCareProviderEmailAddress(String email) { careProviderEmailAddress = email; }

}
