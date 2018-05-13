package com.example.daitoto.testapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AcuqActivity extends AppCompatActivity {

    public static final String RETURN_INFO = "com.demo.parameterdemo.SubActivity.info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acuq);
        getInfo();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);

    }

    private void getInfo(){
        String infoString = getIntent().getStringExtra(RETURN_INFO);
        Log.v("acuq", infoString);
        String[] s1 = infoString.split("\n");
        httpSend https = new httpSend();
        https.execute("http://13.58.195.209:8080/add/event", s1[0], s1[1]);
    }
}


