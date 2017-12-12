package com.example.hp.incident_capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ClickableListActivity extends AppCompatActivity {
    ArrayList<DataModel> dataModels;
    private static CustomAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickable_list);
        listView = (ListView) findViewById(R.id.list);


        dataModels= new ArrayList<>();
        int i,count;
        count=3;
        for(i=0;i<count;i++){
            String subject;
            subject="apple pie";
            dataModels.add(new DataModel(subject));

        }
       /* dataModels.add(new DataModel("Apple Pie"));
        dataModels.add(new DataModel("Banana Bread"));
        dataModels.add(new DataModel("Cupcake"));
        dataModels.add(new DataModel("Donut"));
        dataModels.add(new DataModel("Eclair"));
        dataModels.add(new DataModel("Froyo"));
        dataModels.add(new DataModel("Gingerbread"));
        dataModels.add(new DataModel("Honeycomb"));
        dataModels.add(new DataModel("Ice Cream Sandwich"));
        dataModels.add(new DataModel("Jelly Bean"));
        dataModels.add(new DataModel("Kitkat"));
        dataModels.add(new DataModel("Lollipop"));
        dataModels.add(new DataModel("Marshmallow"));
*/
        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), ListItemActivity1.class);
                startActivityForResult(myIntent, 0);

            }
        });

    }
}