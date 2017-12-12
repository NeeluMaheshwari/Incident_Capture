package com.example.hp.incident_capture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListItemActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        final String userId=user.getUid();

        Log.e("error","opened");
        DatabaseReference fbDb = null;

        Log.e("error1","start");


        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        Log.e("error1","bundle");
       //Extract the data…
        final String subject = bundle.getString("subjectline");
        if (fbDb == null) {
            fbDb = FirebaseDatabase.getInstance().getReference("Reports").child(userId).child(subject);
        }
        Log.e("error1","reference");
        fbDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reportdata value= (dataSnapshot.getValue(reportdata.class));
                TextView subj = (TextView) findViewById(R.id.sub);
                TextView cat = (TextView) findViewById(R.id.cat);
                TextView add = (TextView) findViewById(R.id.add);
                TextView city = (TextView) findViewById(R.id.city);
                TextView pincode = (TextView) findViewById(R.id.pincode);
                TextView des = (TextView) findViewById(R.id.desc);

                subj.setText(value.getSubject());
                cat.setText(value.getCategory());
                add.setText(value.getAddress());
                city.setText(value.getCity());
                pincode.setText(value.getPincode());
                des.setText(value.getDescription());
                Log.e("error5",value.getSubject());
                Log.e("error6",value.getCategory());
                Log.e("error7",value.getCity());
                Log.e("error8",value.getAddress());
                Log.e("error9",value.getPincode());
                Log.e("error0",value.getDescription());

            }
            @Override

            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
    });
    }
}
