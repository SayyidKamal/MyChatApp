package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    private ImageView iv;
    private TextView tv;

    FirebaseUser firebaseUser;
    String strVersion = BuildConfig.VERSION_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            iv = (ImageView) findViewById(R.id.iv);
            tv = (TextView) findViewById(R.id.tv);

            Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
            iv.startAnimation(myanim);
            tv.startAnimation(myanim);
            tv.setText("ver " + strVersion);



            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent loginActivity = new Intent(getApplicationContext(), StartActivity.class);
                        startActivity(loginActivity);
                    }

                    finish();

                }
            };
            timer.start();

        }catch (Exception e)
        {
            Toast.makeText(this, "Some error" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}





