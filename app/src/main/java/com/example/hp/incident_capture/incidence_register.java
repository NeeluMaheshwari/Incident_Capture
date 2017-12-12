package com.example.hp.incident_capture;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.example.hp.incident_capture.R.id.imgView;
//import static com.example.hp.incident_capture.R.id.videoView;



public class incidence_register extends AppCompatActivity {
    String[] list = {"Fire", "Road Accident", "Health Problem", "Robbery"};
    @InjectView(R.id.input_subject)
    EditText _subjectText;
    @InjectView(R.id.input_address)
    EditText _addressText;
    @InjectView(R.id.input_city)
    EditText _cityText;
    @InjectView(R.id.input_pin_code)
    EditText _pin_codeText;
    @InjectView(R.id.input_description)
    EditText _descriptionText;
    String url=null;

    DatabaseReference mdatabaseref = FirebaseDatabase.getInstance().getReference("Reports");

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userId = "";

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // directory name to store captured images and videos
    //private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    //public Uri fileUri; // file url to store image/video


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidence_register);

        ButterKnife.inject(this);
/*
        //getting the instance of Spinner and applying
        Spinner spin=(Spinner)findViewById(R.id.spinner_ir);
        spin.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        //Creating the ArrayAdpter
        ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
*/
        if (user != null)

        {
            userId = user.getUid();
        }
        final DatabaseReference report = mdatabaseref.child(userId).getRef();


        mdatabaseref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Personaldetails value = dataSnapshot.getValue(Personaldetails.class);



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button report_incidence =(Button)findViewById(R.id.btn_report_incidence);
        report_incidence.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (_subjectText == null||url==null) {
                    Log.e("123", null);
                    return;
                }
                String subject = _subjectText.getText().toString();
                String address = _addressText.getText().toString();
                String city = _cityText.getText().toString();
                String pin_code = _pin_codeText.getText().toString();
                String description = _descriptionText.getText().toString();
                report.child(subject).setValue(subject);
                report.child(subject).child("Subject").setValue(subject);
                report.child(subject).child("Address").setValue(address);
                report.child(subject).child("City").setValue(city);
                report.child(subject).child("Pin-code").setValue(pin_code);
                report.child(subject).child("Description").setValue(description);
                report.child(subject).child("ImageURL").setValue(url);



                setResult(RESULT_OK, null);
                finish();
                Toast.makeText(getApplicationContext(), "Report Submitted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Reporter.class);
                startActivity(intent);
            }
        });
    }

    public String encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        return imageEncoded;
    }


    public void loadImagefromCamera(View view) {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 7);

    }




    public void loadImagefromGallery(View view) {

        // Create intent to Open Image applications like Gallery, Google Photos

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start the Intent

        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

    public void loadVideo(View view) {


        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
        }




//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//
//        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
//
//        // set video quality
//        // 1- for high quality video
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//
//       // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//        // start the video capture Intent
//        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);

    }


    final DatabaseReference report = mdatabaseref.child(userId).getRef();




    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            ImageView imgView = (ImageView) findViewById(R.id.imgView);

            imgView.setImageBitmap(bitmap);
            url=encodeBitmapAndSaveToFirebase(bitmap);
        }


        else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Uri videoUri = data.getData();
               //VideoView videoView = (VideoView) findViewById(R.id.videoView);
                //videoView.setVideoURI(videoUri);
                String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                //videoRef = storageRef.child("/videos/" + userUid );
                //TODO: save the video in the db
                //uploadData(selectedVideoUri);

                // video successfully recorded
                // preview the recorded video
                //previewVideo();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }


        try {

            // When an Image is picked

            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK

                    && null != data) {

                // Get the Image from data


                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};


                // Get the cursor

                Cursor cursor = getContentResolver().query(selectedImage,

                        filePathColumn, null, null, null);

                // Move to first row

                cursor.moveToFirst();


                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                imgDecodableString = cursor.getString(columnIndex);

                cursor.close();

                ImageView imgView = (ImageView) findViewById(R.id.imgView);


                // Set the Image in ImageView after decoding the String

                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                url=encodeBitmapAndSaveToFirebase(BitmapFactory.decodeFile(imgDecodableString));



            }
//

        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)

                    .show();

        }



    }

//    private void previewVideo() {
//        try {
//
//           VideoView videoView = (VideoView) findViewById(R.id.videoView);
//            videoView.setVisibility(View.VISIBLE);
//           // videoView.setVideoPath(fileUri.getPath());
//            // start playing
//            videoView.start();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
