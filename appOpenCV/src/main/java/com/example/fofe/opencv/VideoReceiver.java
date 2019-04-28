package com.example.fofe.opencv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.fofe.opencv.tutorial3.SocketReceiver;

public class VideoReceiver extends AppCompatActivity {
     private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_receiver);
        imageView= (findViewById(R.id.imageSocketReceiver));
        Log.e("Test","On create OK");
       SocketReceiver testUDP = new SocketReceiver(imageView);
        testUDP.send();

    }
}
