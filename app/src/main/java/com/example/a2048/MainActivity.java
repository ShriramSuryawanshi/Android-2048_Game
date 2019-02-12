package com.example.a2048;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.MotionEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity  {

    private GestureDetectorCompat gestureDetectorCompat = null;
    public boolean start = true;
    public int[] array = new int[4];
    public String[] colorCodes = {"#e22a64", "#40E0D0", "#672ae2", "#2abde2", "#cfe22a", "#e29e2a", "#e2582a", "#2ae2d5", "#800000", "#008000", "#008000"};

    public int[] addedWith = new int[4];


    public void formatBoard() {

        for(int i = 1; i <= 16; i++) {
            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i,"id", this.getPackageName()));

            if (textView.getText().toString().equalsIgnoreCase(""))
                textView.setBackgroundColor(Color.parseColor("#FFFAFA"));
            else {
                textView.setBackgroundColor(Color.parseColor(colorCodes[(int) (Math.log(Integer.parseInt(textView.getText().toString())) / Math.log(2))]));
                textView.setTextColor(Color.parseColor("#000000"));
            }
        }
    }


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


    public void sumElements() {

        int cnt = 0;

        for(int i = 0; i <= 3; i++)
            addedWith[i] = 0;

        for (int a = 0; a <= 2; a++) {

            for (int b = a + 1; b <= 3; b++) {

                if (array[b] == 0)
                    continue;
                else if (array[a] == array[b]) {
                    array[a] = array[a] + array[b];
                    array[b] = 0;
                    addedWith[cnt++] = b;
                    break;
                } else
                    break;
            }
        }
    }


    public void moveElement() {

        int[] array1 = new int[4];
        for(int a = 0; a <= 3; a++)
            array1[a] = 0;

        int cnt = 0;

        for(int a = 0; a <= 3; a++) {

            if(array[a] != 0) {
                array1[cnt] = array[a];
                cnt++;
            }
        }

        for(int a = 0; a <= 3; a++)
            array[a] = array1[a];
    }


    public void moveLeft() {

        for(int i = 1; i <= 16; i += 4) {

            TextView textView1 = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));
            array[0] = textView1.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView1.getText().toString());

            TextView textView2 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+1), "id", this.getPackageName()));
            array[1] = textView2.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView2.getText().toString());

            TextView textView3 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+2), "id", this.getPackageName()));
            array[2] = textView3.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView3.getText().toString());

            TextView textView4 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+3), "id", this.getPackageName()));
            array[3] = textView4.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView4.getText().toString());

            sumElements();
           // Log.i("addedElements", String.valueOf(addedWith[0]) + ", " + String.valueOf(addedWith[1]) + ", " + String.valueOf(addedWith[2]) + ", " + String.valueOf(addedWith[3]));

            if(addedWith[0] == 1) {
                textView2.animate().translationX(-250f).setDuration(500);
                textView2.setTranslationX(250f);
            } else if (addedWith[0] == 2) {
                textView3.animate().translationX(-500f).setDuration(500);
                textView3.setTranslationX(500f);
            } else if (addedWith[0] == 3) {
                textView4.animate().translationX(-750f).setDuration(500);
                textView4.setTranslationX(750f);
            }
            textView1.setText(array[0] == 0 ? "" : String.valueOf(array[0]));

            if(addedWith[1] == 2) {
                textView3.animate().translationX(-250f).setDuration(500);
                textView3.setTranslationX(250f);
            } else if (addedWith[1] == 3) {
                textView4.animate().translationX(-500f).setDuration(500);
                textView4.setTranslationX(500f);
            }
            textView2.setText(array[1] == 0 ? "" : String.valueOf(array[1]));

            if(addedWith[2] == 3) {
                textView4.animate().translationX(-250f).setDuration(500);
                textView4.setTranslationX(250f);
            }

            textView3.setText(array[2] == 0 ? "" : String.valueOf(array[2]));
            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
        }

        formatBoard();
        newNumber();


    }


    public void moveRight() {

        for(int i = 1; i <= 16; i += 4) {

            TextView textView1 = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));
            array[3] = textView1.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView1.getText().toString());

            TextView textView2 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+1), "id", this.getPackageName()));
            array[2] = textView2.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView2.getText().toString());

            TextView textView3 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+2), "id", this.getPackageName()));
            array[1] = textView3.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView3.getText().toString());

            TextView textView4 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+3), "id", this.getPackageName()));
            array[0] = textView4.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView4.getText().toString());

            sumElements();

            textView1.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            textView2.setText(array[2] == 0 ? "" : String.valueOf(array[2]));
            textView3.setText(array[1] == 0 ? "" : String.valueOf(array[1]));
            textView4.setText(array[0] == 0 ? "" : String.valueOf(array[0]));
        }

        formatBoard();
        newNumber();
    }


    public void moveUp() {

        for(int i = 1; i <= 4; i++) {

            TextView textView1 = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));
            array[0] = textView1.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView1.getText().toString());

            TextView textView2 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+4), "id", this.getPackageName()));
            array[1] = textView2.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView2.getText().toString());

            TextView textView3 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+8), "id", this.getPackageName()));
            array[2] = textView3.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView3.getText().toString());

            TextView textView4 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+12), "id", this.getPackageName()));
            array[3] = textView4.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView4.getText().toString());

            sumElements();

            textView1.setText(array[0] == 0 ? "" : String.valueOf(array[0]));
            textView2.setText(array[1] == 0 ? "" : String.valueOf(array[1]));
            textView3.setText(array[2] == 0 ? "" : String.valueOf(array[2]));
            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
        }

        formatBoard();
        newNumber();
    }


    public void moveDown() {

        for(int i = 1; i <= 4; i++) {

            TextView textView1 = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));
            array[3] = textView1.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView1.getText().toString());

            TextView textView2 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+4), "id", this.getPackageName()));
            array[2] = textView2.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView2.getText().toString());

            TextView textView3 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+8), "id", this.getPackageName()));
            array[1] = textView3.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView3.getText().toString());

            TextView textView4 = (TextView) findViewById(getResources().getIdentifier("textView" + (i+12), "id", this.getPackageName()));
            array[0] = textView4.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView4.getText().toString());

            sumElements();

            textView1.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            textView2.setText(array[2] == 0 ? "" : String.valueOf(array[2]));
            textView3.setText(array[1] == 0 ? "" : String.valueOf(array[1]));
            textView4.setText(array[0] == 0 ? "" : String.valueOf(array[0]));
        }

        formatBoard();
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

    public void clickLeft(View view) {
        moveLeft();
    }

    public void clickRight(View view) {
        moveRight();
    }

    public void clickUp(View view) {
        moveUp();
    }

    public void clickDown(View view) {
        moveDown();
    }
}
