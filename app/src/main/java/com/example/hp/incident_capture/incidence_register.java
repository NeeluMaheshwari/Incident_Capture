package com.example.hp.incident_capture;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.example.hp.incident_capture.R.id.imgView;
import static com.example.hp.incident_capture.R.id.videoView;

public class incidence_register extends AppCompatActivity {
    String[] list ={"Fire","Road Accident","Health Problem","Robbery"};



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

    }

    public void loadAudio(View view) {


        Intent intent1 = new Intent(getApplicationContext(), RecordActivity.class);
        startActivity(intent1);

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            ImageView imgView = (ImageView) findViewById(R.id.imgView);

            imgView.setImageBitmap(bitmap);
        }


        else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Uri videoUri = data.getData();
                VideoView videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setVideoURI(videoUri);


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

                imgView.setImageBitmap(BitmapFactory

                        .decodeFile(imgDecodableString));


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
