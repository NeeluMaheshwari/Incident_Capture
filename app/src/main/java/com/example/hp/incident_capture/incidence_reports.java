package com.example.hp.incident_capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class incidence_reports extends AppCompatActivity {
    ArrayList<DataModel> dataModels;
    private static CustomAdapter adapter;
    ListView listView;
    long size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidence_reports);

        Bundle bundle=getIntent().getExtras();
        String category=bundle.get("category").toString();

        listView = (ListView) findViewById(R.id.list);
        dataModels= new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String userId=user.getUid();

        Log.e("error","opened");
        DatabaseReference fbDb = null;
        if (fbDb == null) {
            fbDb = FirebaseDatabase.getInstance().getReference("Reports");
        }
        Log.e("error1","start");
        ValueEventListener key =  new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest

                size = dataSnapshot.getChildrenCount();
                //Log.e("error1", String.valueOf(dsChildData));
                Iterable<DataSnapshot> dsChildData = dataSnapshot.getChildren();
                Log.e("error", String.valueOf(size));
                Log.e("error2",dsChildData.toString());

                int i;
                //DatabaseReference fdbd1=FirebaseDatabase.getInstance().getReference("Reports").child(userId).child(dataSnapshot);

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("error3", childDataSnapshot.getKey()); //displays the key for the node

                    String subject;
                    subject=childDataSnapshot.getKey();
                    if(subject!=null)
                    {
                        DatabaseReference sub = FirebaseDatabase.getInstance().getReference("Reports").child(subject);
                        ValueEventListener sname = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                String subject1=childDataSnapshot.getKey();
                                    dataModels.add(new DataModel(subject1));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        sub.addValueEventListener(sname);
                    }
                    //Log.v("error4",childDataSnapshot.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
fbDb.addValueEventListener(key);


        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel= dataModels.get(position);
                String subject=dataModel.getName();
                Intent myIntent = new Intent(view.getContext(), ListItemActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("subjectline",subject);
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, 0);

            }
        });

    }
}