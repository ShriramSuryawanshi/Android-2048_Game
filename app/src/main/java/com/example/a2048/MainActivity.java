package com.example.a2048;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.MotionEvent;
import java.util.Random;


public class MainActivity extends AppCompatActivity  {

    private GestureDetectorCompat gestureDetectorCompat = null;
    public int[][] matrix = new int[4][4];
    public boolean start = true;

    public void newNumber() {

        Random rand = new Random();
        int n = rand.nextInt(16) + 1;

        TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + n,"id", this.getPackageName()));

        if(textView.getText().toString().equalsIgnoreCase("")) {

            if(start) {
                textView.setText("2");
                textView.setTextColor(Color.parseColor("#000000"));
                start = false;
                newNumber();
            }
            else
                textView.setText("2");
                textView.setTextColor(Color.parseColor("#000000"));
        }
        else
            newNumber();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setActivity(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

        for(int i = 1; i <= 16; i++) {

            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i,"id", this.getPackageName()));
            textView.setText("");
            textView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }

        newNumber();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }






}
