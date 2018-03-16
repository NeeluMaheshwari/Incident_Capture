package com.example.hp.incident_capture;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Responser extends AppCompatActivity {
    //private Button incidence_register;
    //@InjectView(R.id.btn_report_incidence) Button incidence_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responser);
//        ButterKnife.inject(this);
        //setContentView(R.layout.activity_main);
        ImageButton incidence_reports = (ImageButton) findViewById(R.id.btn_response);
        ImageButton responser_history = (ImageButton) findViewById(R.id.btn_history);
        incidence_reports.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Responser_category.class);
                        startActivity(intent);
                    }
                }

        );

        responser_history.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("error","open");
                        Intent intent = new Intent(getApplicationContext(), responser_history.class);
                        startActivity(intent);
                    }
                }

        );

    }
}
