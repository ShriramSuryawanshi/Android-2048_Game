package com.example.a2048;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.view.MotionEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity  {

    private GestureDetectorCompat gestureDetectorCompat = null;
    public boolean start = true;
    public int[] array = new int[4];
    public String[] colorCodes = {"#fffff0", "#cccc78", "#e8e812", "#e8e812", "#18e295", "#18d8e2", "#2e25d1", "#2ae2d5", "#800000", "#008000", "#008000"};

    public int[] addedWith = new int[4];
    public int[] movedFrom = new int[4];

    public int value;
    public int n;

    public void formatBoard() {

        for(int i = 1; i <= 16; i++) {
            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i,"id", this.getPackageName()));

            if (textView.getText().toString().equalsIgnoreCase("")) {
                textView.setBackgroundColor(Color.parseColor("#FFFAFA"));
                textView.animate().alpha(0.2f).setDuration(50);
            }
            else {
                textView.setBackgroundColor(Color.parseColor(colorCodes[(int) (Math.log(Integer.parseInt(textView.getText().toString())) / Math.log(2))]));
                textView.setTextColor(Color.parseColor("#000000"));
                textView.animate().alpha(1f).setDuration(50);
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
                textView.animate().alpha(1f).setDuration(50);
                start = false;
                newNumber();
            }
            else
                textView.setText("2");
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setBackgroundColor(Color.parseColor("#FFFAFA"));
                textView.animate().alpha(1f).setDuration(50);
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
                    addedWith[a] = b;
                    break;
                } else
                    break;
            }
        }
    }


    public void moveElements() {

        int[] array1 = new int[4];
        for(int a = 0; a <= 3; a++) {
            array1[a] = 0;
            movedFrom[a] = 0;
        }

        int cnt = 0;

        for(int a = 0; a <= 3; a++) {

            if(array[a] != 0) {
                array1[cnt] = array[a];
                movedFrom[cnt] = a;
                cnt++;
            }
        }

        for(int a = 0; a <= 3; a++)
            array[a] = array1[a];
    }


    public void animationTransition(final TextView animationTextView, final int local_n, final int local_value, int fromxDelta, int toXDelta, int fromYDelta, int toYDelta) {

        Log.i("AtStart", String.valueOf(local_value) + " to be set at " + String.valueOf(local_n));
        Animation animation = new TranslateAnimation(fromxDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(200);
        animation.setFillAfter(false);
        animation.setAnimationListener(new Animation.AnimationListener(){
            @Override public void onAnimationStart(Animation animation){}
            @Override public void onAnimationRepeat(Animation animation){}
            @Override public void onAnimationEnd(Animation animation){
                Log.i("AtEnd", String.valueOf(local_value) + " set at " + String.valueOf(local_n));
                TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + local_n, "id", getPackageName()));
                textView.setText(local_value == 0 ? "" : String.valueOf(local_value));
                formatBoard();
            }
        });

        animationTextView.startAnimation(animation);
        animationTextView.postDelayed(new Runnable() { @Override public void run() { }}, 200);
        animationTextView.setText("");
        animationTextView.animate().alpha(0.2f).setDuration(0);
    }


    public void fadeAnimation(TextView animationTextView, int textViewID, int textValue) {


    }


    public void moveLeft() {

        for(int i = 1; i <= 16; i += 4) {

            TextView textView1 = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));
            array[0] = textView1.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView1.getText().toString());

            TextView textView2 = (TextView) findViewById(getResources().getIdentifier("textView" + (i + 1), "id", this.getPackageName()));
            array[1] = textView2.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView2.getText().toString());

            TextView textView3 = (TextView) findViewById(getResources().getIdentifier("textView" + (i + 2), "id", this.getPackageName()));
            array[2] = textView3.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView3.getText().toString());

            TextView textView4 = (TextView) findViewById(getResources().getIdentifier("textView" + (i + 3), "id", this.getPackageName()));
            array[3] = textView4.getText().toString().equalsIgnoreCase("") ? 0 : Integer.parseInt(textView4.getText().toString());

            sumElements();

            if(addedWith[0] == 1)               animationTransition(textView2,i+0, array[0],0, -250, 0, 0);
            else if (addedWith[0] == 2)         animationTransition(textView3,i+0, array[0],0, -500, 0, 0);
            else if (addedWith[0] == 3)         animationTransition(textView4,i+0, array[0],0, -750, 0, 0);

            if(addedWith[1] == 2)               animationTransition(textView3,i+1, array[1],0, -250, 0, 0);
            else if (addedWith[1] == 3)         animationTransition(textView4,i+1, array[1],0, -500, 0, 0);

            if(addedWith[2] == 3)               animationTransition(textView4,i+2, array[2],0, -250, 0, 0);

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));

            moveElements();

            if(movedFrom[0] == 1)               animationTransition(textView2,i+0, array[0],0, -250, 0, 0);
            else if (movedFrom[0] == 2)         animationTransition(textView3,i+0, array[0],0, -500, 0, 0);
            else if (movedFrom[0] == 3)         animationTransition(textView4,i+0, array[0],0, -750, 0, 0);

            if(movedFrom[1] == 2)               animationTransition(textView3,i+1, array[1],0, -250, 0, 0);
            else if (movedFrom[1] == 3)         animationTransition(textView4,i+1, array[1],0, -500, 0, 0);

            if(movedFrom[2] == 3)               animationTransition(textView4,i+2, array[2],0, -250, 0, 0);

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));

        }

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
            moveElements();

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

            if(addedWith[0] == 1)               animationTransition(textView2,i+0, array[0],0, 0, 0, -190);
            else if (addedWith[0] == 2)         animationTransition(textView3,i+0, array[0],0, 0, 0, -380);
            else if (addedWith[0] == 3)         animationTransition(textView4,i+0, array[0],0, 0, 0, -570);

            if(addedWith[1] == 2)               animationTransition(textView3,i+4, array[1],0, 0, 0, -190);
            else if (addedWith[1] == 3)         animationTransition(textView4,i+4, array[1],0, 0, 0, -380);

            if(addedWith[2] == 3)               animationTransition(textView4,i+8, array[2],0, 0, 0, -570);

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));

            moveElements();

            if(movedFrom[0] == 1)               animationTransition(textView2,i+0, array[0],0, 0, 0, -190);
            else if (movedFrom[0] == 2)         animationTransition(textView3,i+0, array[0],0, 0, 0, -380);
            else if (movedFrom[0] == 3)         animationTransition(textView4,i+0, array[0],0, 0, 0, -570);

            if(movedFrom[1] == 2)               animationTransition(textView3,i+4, array[1],0, 0, 0, -190);
            else if (movedFrom[1] == 3)         animationTransition(textView4,i+4, array[1],0, 0, 0, -380);

            if(movedFrom[2] == 3)               animationTransition(textView4,i+8, array[2],0, 0, 0, -380);

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));




          /*  moveElements();

            textView1.setText(array[0] == 0 ? "" : String.valueOf(array[0]));
            textView2.setText(array[1] == 0 ? "" : String.valueOf(array[1]));
            textView3.setText(array[2] == 0 ? "" : String.valueOf(array[2]));
            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3])); */
        }

        //formatBoard();
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
            moveElements();

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
            textView.animate().alpha(0.2f).setDuration(0);
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
