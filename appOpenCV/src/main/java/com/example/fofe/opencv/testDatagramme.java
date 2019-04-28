package com.example.fofe.opencv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fofe.opencv.tutorial3.TestUDP;
import com.example.fofe.opencv.tutorial3.UDPClient;

public class testDatagramme extends AppCompatActivity {

    static EditText e1;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("UDP","on create testDatagram");
        setContentView(R.layout.activity_test_datagramme);
        e1= (EditText)findViewById(R.id.writeTest);
        e1.setText("hello word");
        send= (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                butclick();
            }
        });
        TestUDP testUDP = new TestUDP(e1);
        testUDP.send();


    }


public  void butclick(){

    Thread cli1 = new Thread(new UDPClient("Cysboy", 1000,e1));
    Thread cli2 = new Thread(new UDPClient("John-John", 1000,e1));

    cli1.start();

}





}
