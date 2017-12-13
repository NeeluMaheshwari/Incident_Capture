package com.example.hp.incident_capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClickableListActivity extends AppCompatActivity {
    ArrayList<DataModel> dataModels;
    private static CustomAdapter adapter;
    ListView listView;
    long size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickable_list);
        listView = (ListView) findViewById(R.id.list);
        dataModels= new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String userId=user.getUid();

        Log.e("error","opened");
        DatabaseReference fbDb = null;
        if (fbDb == null) {
            fbDb = FirebaseDatabase.getInstance().getReference("Reports").child(userId);
        }
        Log.e("error1","start");
        fbDb.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            dataModels.add(new DataModel(subject));

                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
               DataModel dataModel= dataModels.get(position);
                String subject=dataModel.getName();
                Intent myIntent = new Intent(view.getContext(), ListItemActivity1.class);
                Bundle bundle = new Bundle();
                bundle.putString("subjectline",subject);
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, 0);

            }
        });

    }
}