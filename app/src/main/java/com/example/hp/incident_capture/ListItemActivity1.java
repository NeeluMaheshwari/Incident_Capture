package com.example.hp.incident_capture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListItemActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item1);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

       //Extract the data…
        String subject = bundle.getString("subjectline");
    }
}
