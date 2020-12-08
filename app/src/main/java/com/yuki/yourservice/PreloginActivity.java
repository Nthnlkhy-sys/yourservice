package com.yuki.yourservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreloginActivity extends AppCompatActivity {
    Button btntolgn, btntoreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelogin);

        Button buttontologin    = (Button) findViewById(R.id.btntolgn);
        Button buttontoreg      = (Button) findViewById(R.id.btntoreg);


        buttontologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreloginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buttontoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreloginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


}