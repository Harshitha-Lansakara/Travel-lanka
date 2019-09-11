package com.example.travellanka;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text views
        TextView date = findViewById(R.id.date);



        //buttons
        Button map=findViewById(R.id.maps);
        Button settings=findViewById(R.id.settings);
        Button TrainBooking=findViewById(R.id.trainbooking);
        Button Uberbooking = findViewById(R.id.uberbook);
        Button Avoid = findViewById(R.id.avoidplaces);
        Button Destination =findViewById(R.id.destination);
        Button travelplans =findViewById(R.id.travelplan);





        //date for app
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        date.setText(currentDate);

        //declaring time text
        final TextView times = findViewById(R.id.time);

        //current time for app
        CountDownTimer downTimer=new CountDownTimer(1000000000,1000) {
            @Override
            public void onTick(long l) {
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
                String time=dateFormat.format(calendar.getTime());
                times.setText(time);
            }

            @Override
            public void onFinish() {

            }
        };
        downTimer.start();


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set = new Intent(MainActivity.this,MapsActivity.class);
                MainActivity.this.
                        startActivity(set);
            }
        });




        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set = new Intent(MainActivity.this,Settings.class);
                MainActivity.this.
                        startActivity(set);
            }
        });
        TrainBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set = new Intent(MainActivity.this,TrainBook.class);
                MainActivity.this.
                        startActivity(set);
            }
        });
        Uberbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_MAIN);

                    PackageManager managerclock = getPackageManager();

                    i = managerclock.getLaunchIntentForPackage("com.ubercab");
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    MainActivity.this.
                            startActivity(i);

            }
        });
        Avoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set = new Intent(MainActivity.this,PlacestoAvoid.class);
                MainActivity.this.
                        startActivity(set);
            }
        });
        Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set = new Intent(MainActivity.this,Destinations.class);
                MainActivity.this.
                        startActivity(set);
            }
        });
        travelplans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set = new Intent(MainActivity.this,TravelPlanes.class);
                MainActivity.this.
                        startActivity(set);
            }
        });
    }
}
