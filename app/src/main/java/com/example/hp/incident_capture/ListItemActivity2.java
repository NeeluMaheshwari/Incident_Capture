package com.example.hp.incident_capture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ListItemActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item2);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("error", "opened");

        final String userId = user.getUid();

        Bundle bundle = getIntent().getExtras();
        Log.e("error1", "bundle");
        //Extract the data…

        final String subject = bundle.getString("subjectline");

        Log.e("error11", subject);

        DatabaseReference fbDb = null;

        Log.e("error1", "start");

        //Get the bundle

        if (fbDb == null) {
            fbDb = FirebaseDatabase.getInstance().getReference("Reports").child(userId).child(subject);
        }
        Log.e("error1", subject);
        final TextView subj = (TextView) findViewById(R.id.sub);
        final TextView cat = (TextView) findViewById(R.id.cat);
        final TextView add = (TextView) findViewById(R.id.add);
        final TextView city = (TextView) findViewById(R.id.city);
        final TextView pincode = (TextView) findViewById(R.id.pincode);
        final TextView des = (TextView) findViewById(R.id.desc);
        final ImageView image=(ImageView) findViewById(R.id.img);

        fbDb.child("Subject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sub = dataSnapshot.getValue(String.class);
                subj.setText(sub);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });
        fbDb.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String category = dataSnapshot.getValue(String.class);
                cat.setText(category);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });
        fbDb.child("Address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address = dataSnapshot.getValue(String.class);
                add.setText(address);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });
        fbDb.child("City").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cit = dataSnapshot.getValue(String.class);
                city.setText(cit);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });
        fbDb.child("Pin-code").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pin = dataSnapshot.getValue(String.class);
                pincode.setText(pin);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });
        fbDb.child("Description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String desc = dataSnapshot.getValue(String.class);
                des.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });


        fbDb.child("ImageURL").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String img = dataSnapshot.getValue(String.class);
                if (img!=null) {
                    try {
                        Bitmap imageBitmap = decodeFromFirebaseBase64(img);
                        image.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    // This block of code should already exist, we're just moving it to the 'else' statement:
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });

    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
