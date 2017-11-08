package com.example.hp.incident_capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class incidence_register extends AppCompatActivity {
    String[] list ={"Fire","Road Accident","Health Problem","Robbery"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidence_register);
/*
        //getting the instance of Spinner and applying
        Spinner spin=(Spinner)findViewById(R.id.spinner_ir);
        spin.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        //Creating the ArrayAdpter
        ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
*/
        Button report_incidence =(Button)findViewById(R.id.btn_report_incidence);
        report_incidence.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reporter.class);
                startActivity(intent);
            }
        });
    }


}
