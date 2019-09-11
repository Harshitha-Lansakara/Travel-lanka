package com.example.travellanka;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class KandyInfo extends AppCompatActivity {

    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kandy_info);


        int images[]= {R.drawable.kandy,R.drawable.kan1,R.drawable.kan2,R.drawable.kan3,R.drawable.kan4};

        viewFlipper =findViewById(R.id.viewflip);

        for (int image:images){
            flipimages(image);
        }

        Button wiki = findViewById(R.id.websearch);

        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://en.wikipedia.org/wiki/Kandy");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button packages = findViewById(R.id.packages);

        packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set = new Intent(KandyInfo.this,Kandypackages.class);
                KandyInfo.this.
                        startActivity(set);
            }
        });
    }

    public void flipimages(int image){

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);


        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
}
