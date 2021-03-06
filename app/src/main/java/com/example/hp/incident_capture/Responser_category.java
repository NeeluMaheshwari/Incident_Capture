package com.example.hp.incident_capture;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Responser_category extends Activity implements AdapterView.OnItemSelectedListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responser_category);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> type = new ArrayList<>();
        type.add("Select Category");
        type.add("All Reports");
        type.add("Crime");
        type.add("Traffic");
        type.add("Municipal Issues");
        type.add("Harassment");
        type.add("Corruption");
        type.add("Domestic Violence");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
         String item = parent.getItemAtPosition(position).toString();

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String category =spinner.getSelectedItem().toString();
        Intent intent= new Intent(Responser_category.this,incidence_reports.class);
        Log.v("spinner value",category);
        intent.putExtra("category",category);
        if(category != "Select Category") {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Select a category!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}