package com.example.jokeandroidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private TextView mTextView;
    public static final String JOKE = "jokeFromJavaLibrary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mTextView = findViewById(R.id.tv_tell_joke);

        setJokeFromIntent();
    }

    public void setJokeFromIntent(){
        Bundle bundle = getIntent().getExtras();
        String joke = bundle.getString(JOKE);
        mTextView.setText(joke);
    }
}
