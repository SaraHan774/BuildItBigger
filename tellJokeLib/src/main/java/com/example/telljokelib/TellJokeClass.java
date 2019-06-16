package com.example.telljokelib;

import java.util.Random;

public class TellJokeClass {

    private final String[] jokes
            = {
      "tod saw I was dot",
      "funny joke",
      "one more funny joke"
    };

    public TellJokeClass() {
    }

    public String getJoke(){
        int index = new Random().nextInt(jokes.length);
        return jokes[index];
    }
}
