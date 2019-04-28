package org.pjsip.pjsua2.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fofe.opencv.R;

public class Main2Activity extends Activity {
private Button Regsitrer ;
private  Button Hangup;
private Button CallPhone3;
private  Button CallPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TEST FOFE","C EST BON");
        setContentView(R.layout.activity_main2);
        Regsitrer= (Button) findViewById(R.id.registrer);
        CallPhone2 = (Button) findViewById(R.id.Call);

        Regsitrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TEST 2","REGISTRER before ");
                startActivity(new Intent(Main2Activity.this, MainActivity1.class));
                Log.e("TEST 2","REGISTRER after");
            }
        });



        CallPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TEST CALL","DEBUT CALL PHONE2");





                //// fofe
                CallPhone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("CALL PHONE 2","DEBUT");

MainActivity1.CallPhone2();
                        showCallActivity();

                    }
                });



            }
        });

    }

    private void showCallActivity()
    {
        Intent intent = new Intent(this, CallActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
