package com.example.hp.incident_capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Reporter extends AppCompatActivity {
    //private Button incidence_register;
    //@InjectView(R.id.btn_report_incidence) Button incidence_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter);
//        ButterKnife.inject(this);
        //setContentView(R.layout.activity_main);
        ImageButton incidence_register = (ImageButton) findViewById(R.id.btn_report);
        ImageButton ClickableListActivity = (ImageButton) findViewById(R.id.btn_history);
        incidence_register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), incidence_register.class);
                        startActivity(intent);
                    }
                }

        );

        ClickableListActivity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ClickableListActivity.class);
                        startActivity(intent);
                    }
                }

        );
        /*incidence_register=(Button)findViewById(R.id.btn_report_incidence);
        incidence_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // login();
                Intent intent = new Intent(getApplicationContext(), incidence_register.class);
                startActivity(intent);
            }
        });*/
    }
}
