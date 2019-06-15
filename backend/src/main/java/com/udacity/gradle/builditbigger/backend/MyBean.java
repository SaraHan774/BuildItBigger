package com.udacity.gradle.builditbigger.backend;

import com.example.telljokelib.TellJokeClass;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public String getData() {
        TellJokeClass tellJokeClass = new TellJokeClass();
        myData = tellJokeClass.getJoke();
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}