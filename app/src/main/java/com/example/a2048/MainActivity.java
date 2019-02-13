package com.example.a2048;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity  {

    private GestureDetectorCompat gestureDetectorCompat = null;
    public boolean start = true;

    public String[] bgColorCodes = {"#FFFFFF", "#8504ef", "#ef0462", "#991e1e", "#1b9633", "#1c7db5", "#99471e", "#686222", "#4f0541", "#635f5f", "#0021f9", "#005600"};


    public int[] array = new int[4];
    public int[] addedWith = new int[4];
    public int[] movedFrom = new int[4];

    public int newNumberLocation;
    public boolean newNumber = true;
    public boolean nextAvailable = true;

    public int[] undoArray = new int[16];
    public String undoAction = "";
    public int score = 0;
    public int lastScore = 0;
    public boolean undo = false;


    public void undoStep(View view) {

        if(undo) {

            Toast toast = Toast.makeText(getApplicationContext(), "Undone last " + undoAction + " swipe!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            for (int i = 1; i <= 16; i++) {

                TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));
                textView.setText(undoArray[i - 1] == 0 ? "" : String.valueOf(undoArray[i - 1]));
            }
            formatBoard();

            Log.i("last", String.valueOf(lastScore));
            TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
            score = lastScore;
            textViewScore.setText("Score : " + score);

            if(score == 0)
                undo = false;
        }

        undo = false;
    }


    public void createUndo(String action) {

        undoAction = action;
        lastScore = score;
        undo = true;

        for(int i = 1; i <= 16; i++) {

            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));
            undoArray[i-1] = textView.getText().toString().equalsIgnoreCase("")? 0 : Integer.parseInt(textView.getText().toString());
        }
    }


    public void gameReset() {

        for(int i = 1; i <= 16; i++) {
            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i,"id", this.getPackageName()));
            textView.setText("");
            textView.animate().alpha(0.2f).setDuration(0);
        }


        score = 0;
        lastScore = 0;
        undo = false;
        newNumber = true;
        newNumber();
    }


    public void gameEnd() {

        for(int i = 1; i <= 16; i++) {
            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));

            if (textView.getText().toString().equalsIgnoreCase("2048")) {

                Toast toast = Toast.makeText(getApplicationContext(), "Well Done! Game Over! \n\nLet's play again...", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                gameReset();
                break;
            }
        }

        for(int i = 1; i <= 16; i++) {
            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i, "id", this.getPackageName()));

            if (textView.getText().toString().equalsIgnoreCase("")) {
                nextAvailable = true;
                break;
            } else
                nextAvailable = false;
        }


        if(!nextAvailable) {
            Toast toast = Toast.makeText(getApplicationContext(),"Oops...! Game Over! \n\nLet's try again...", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            nextAvailable = true;
            gameReset();
        }
    }


    public void formatBoard() {

        for(int i = 1; i <= 16; i++) {
            TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + i,"id", this.getPackageName()));

            if (textView.getText().toString().equalsIgnoreCase("")) {
                textView.setBackgroundColor(Color.parseColor("#FFFAFA"));
                textView.animate().alpha(0.2f).setDuration(200);
            }
            else if (i == newNumberLocation && (Integer.parseInt(textView.getText().toString()) == 2)) {
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            else {
                textView.setBackgroundColor(Color.parseColor(bgColorCodes[(int) (Math.log(Integer.parseInt(textView.getText().toString())) / Math.log(2))]));
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                textView.animate().alpha(1f).setDuration(200);
            }
        }
    }


    public void newNumber() {

        gameEnd();

        Random rand = new Random();
        int n = rand.nextInt(16) + 1;

        TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + n,"id", this.getPackageName()));

        if(textView.getText().toString().equalsIgnoreCase("")) {

            if(start) {
                textView.setText("2");
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                textView.animate().alpha(1f).setDuration(200);
                start = false;
                newNumber();
            }
            else {
                textView.setText("2");
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                textView.animate().alpha(1f).setDuration(200);
                newNumberLocation = n;
            }
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
                else if (array[a] == array[b] && array[a] != 0) {
                    array[a] = array[a] + array[b];
                    score += array[a];
                    array[b] = 0;
                    addedWith[a] = b;
                    newNumber = true;
                    break;
                } else
                    break;
            }
        }

        TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewScore.setText("Score : " + score);
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
                newNumber = true;
            }
        }

        for(int a = 0; a <= 3; a++)
            array[a] = array1[a];
    }


    public void animationTransition(TextView animationTextView, final int old, final int local_n, final int local_value, final int fromxDelta, final int toXDelta, final int fromYDelta, final int toYDelta, final String op) {

        Animation animation = new TranslateAnimation(fromxDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(200);
        animation.setFillAfter(false);
        animationTextView.setText("");
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //Log.i("AtStart", op + " - " + String.valueOf(local_value) + " to be set at " + String.valueOf(local_n) + " from " + String.valueOf(old));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               // Log.i("AtEnd", op + " - " + String.valueOf(local_value) + " set at " + String.valueOf(local_n));
                TextView textView = (TextView) findViewById(getResources().getIdentifier("textView" + local_n, "id", getPackageName()));
                textView.setText(local_value == 0 ? "" : String.valueOf(local_value));

                formatBoard();
            }
        });

        animationTextView.startAnimation(animation);
        animationTextView.setText("");



    }


    public void moveLeft() {

        createUndo("LEFT");

        gameEnd();

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
            moveElements();

            if(addedWith[0] == 1)               animationTransition(textView2,i+1,i+0, array[0],0, -250, 0, 0, "sum");
            else if (addedWith[0] == 2)         animationTransition(textView3,i+2,i+0, array[0],0, -500, 0, 0, "sum");
            else if (addedWith[0] == 3)         animationTransition(textView4,i+3,i+0, array[0],0, -750, 0, 0, "sum");

            if(addedWith[1] == 2)               animationTransition(textView3,i+2,i+1, array[1],0, -250, 0, 0, "sum");
            else if (addedWith[1] == 3)         animationTransition(textView4,i+3,i+1, array[1],0, -500, 0, 0, "sum");

            if(addedWith[2] == 3)               animationTransition(textView4,i+3,i+2, array[2],0, -250, 0, 0, "sum");

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            if(movedFrom[0] == 1)               animationTransition(textView2,i+1,i+0, array[0],0, -250, 0, 0, "move");
            else if (movedFrom[0] == 2)         animationTransition(textView3,i+2,i+0, array[0],0, -500, 0, 0, "move");
            else if (movedFrom[0] == 3)         animationTransition(textView4,i+3,i+0, array[0],0, -750, 0, 0, "move");

            if(movedFrom[1] == 2)               animationTransition(textView3,i+2,i+1, array[1],0, -250, 0, 0, "move");
            else if (movedFrom[1] == 3)         animationTransition(textView4,i+3,i+1, array[1],0, -500, 0, 0, "move");

            if(movedFrom[2] == 3)               animationTransition(textView4,i+3,i+2, array[2],0, -250, 0, 0, "move");

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            textView1.setText(array[0] == 0? "" : String.valueOf(array[0]));
            textView2.setText(array[1] == 0? "" : String.valueOf(array[1]));
            textView3.setText(array[2] == 0? "" : String.valueOf(array[2]));
            textView4.setText(array[3] == 0? "" : String.valueOf(array[3]));
            formatBoard();
        }

        gameEnd();

        if(newNumber) {
            newNumber();
            formatBoard();
            newNumber = false;
        }
    }


    public void moveRight() {

        createUndo("RIGHT");

        gameEnd();

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

            if(addedWith[0] == 1)               animationTransition(textView3,i+2,i+3, array[0],0, 250, 0, 0, "sum");
            else if (addedWith[0] == 2)         animationTransition(textView2,i+1,i+3, array[0],0, 500, 0, 0, "sum");
            else if (addedWith[0] == 3)         animationTransition(textView1,i+0,i+3, array[0],0, 750, 0, 0, "sum");

            if(addedWith[1] == 2)               animationTransition(textView2,i+2,i+2, array[1],0, 250, 0, 0, "sum");
            else if (addedWith[1] == 3)         animationTransition(textView1,i+0,i+2, array[1],0, 500, 0, 0, "sum");

            if(addedWith[2] == 3)               animationTransition(textView1,i+0,i+1, array[2],0, 250, 0, 0, "sum");

            textView1.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            if(movedFrom[0] == 1)               animationTransition(textView3,i+2,i+3, array[0],0, 250, 0, 0, "move");
            else if (movedFrom[0] == 2)         animationTransition(textView2,i+1,i+3, array[0],0, 500, 0, 0, "move");
            else if (movedFrom[0] == 3)         animationTransition(textView1,i+0,i+3, array[0],0, 750, 0, 0, "move");

            if(movedFrom[1] == 2)               animationTransition(textView2,i+1,i+2, array[1],0, 250, 0, 0, "move");
            else if (movedFrom[1] == 3)         animationTransition(textView1,i+0,i+2, array[1],0, 500, 0, 0, "move");

            if(movedFrom[2] == 3)               animationTransition(textView1,i+0,i+1, array[2],0, 250, 0, 0, "move");

            textView1.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            textView1.setText(array[3] == 0? "" : String.valueOf(array[3]));
            textView2.setText(array[2] == 0? "" : String.valueOf(array[2]));
            textView3.setText(array[1] == 0? "" : String.valueOf(array[1]));
            textView4.setText(array[0] == 0? "" : String.valueOf(array[0]));
            formatBoard();
        }

        gameEnd();

        if(newNumber) {
            newNumber();
            formatBoard();
            newNumber = false;
        }
    }


    public void moveUp() {

        createUndo("UP");

        gameEnd();

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
            moveElements();

            if(addedWith[0] == 1)               animationTransition(textView2,i+4,i+0, array[0],0, 0, 0, -190, "sum");
            else if (addedWith[0] == 2)         animationTransition(textView3,i+8,i+0, array[0],0, 0, 0, -380, "sum");
            else if (addedWith[0] == 3)         animationTransition(textView4,i+12,i+0, array[0],0, 0, 0, -570, "sum");

            if(addedWith[1] == 2)               animationTransition(textView3,i+8,i+4, array[1],0, 0, 0, -190, "sum");
            else if (addedWith[1] == 3)         animationTransition(textView4,i+12,i+4, array[1],0, 0, 0, -380, "sum");

            if(addedWith[2] == 3)               animationTransition(textView4,i+12,i+8, array[2],0, 0, 0, -190, "sum");

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            if(movedFrom[0] == 1)               animationTransition(textView2,i+4,i+0, array[0],0, 0, 0, -190, "move");
            else if (movedFrom[0] == 2)         animationTransition(textView3,i+8,i+0, array[0],0, 0, 0, -380, "move");
            else if (movedFrom[0] == 3)         animationTransition(textView4,i+12,i+0, array[0],0, 0, 0, -570, "move");

            if(movedFrom[1] == 2)               animationTransition(textView3,i+8,i+4, array[1],0, 0, 0, -190, "move");
            else if (movedFrom[1] == 3)         animationTransition(textView4,i+12,i+4, array[1],0, 0, 0, -380, "move");

            if(movedFrom[2] == 3)               animationTransition(textView4,i+12,i+8, array[2],0, 0, 0, -190, "move");

            textView4.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            textView1.setText(array[0] == 0? "" : String.valueOf(array[0]));
            textView2.setText(array[1] == 0? "" : String.valueOf(array[1]));
            textView3.setText(array[2] == 0? "" : String.valueOf(array[2]));
            textView4.setText(array[3] == 0? "" : String.valueOf(array[3]));
            formatBoard();
        }

        gameEnd();

        if(newNumber) {
            newNumber();
            formatBoard();
            newNumber = false;
        }
    }


    public void moveDown() {

        createUndo("DOWN");

        gameEnd();

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

            if(addedWith[0] == 1)               animationTransition(textView3,i+8,i+12, array[0],0, 0, 0, 190, "sum");
            else if (addedWith[0] == 2)         animationTransition(textView2,i+4,i+12, array[0],0, 0, 0, 380, "sum");
            else if (addedWith[0] == 3)         animationTransition(textView1,i+0,i+12, array[0],0, 0, 0, 570, "sum");

            if(addedWith[1] == 2)               animationTransition(textView2,i+4,i+8, array[1],0, 0, 0, 190, "sum");
            else if (addedWith[1] == 3)         animationTransition(textView1,i+0,i+8, array[1],0, 0, 0, 380, "sum");

            if(addedWith[2] == 3)               animationTransition(textView1,i+0,i+4, array[2],0, 0, 0, 190, "sum");

            textView1.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            if(movedFrom[0] == 1)               animationTransition(textView3,i+8,i+12, array[0],0, 0, 0, 190, "move");
            else if (movedFrom[0] == 2)         animationTransition(textView2,i+4,i+12, array[0],0, 0, 0, 380, "move");
            else if (movedFrom[0] == 3)         animationTransition(textView1,i+0,i+12, array[0],0, 0, 0, 570, "move");

            if(movedFrom[1] == 2)               animationTransition(textView2,i+4,i+8, array[1],0, 0, 0, 190, "move");
            else if (movedFrom[1] == 3)         animationTransition(textView1,i+0,i+8, array[1],0, 0, 0, 380, "move");

            if(movedFrom[2] == 3)               animationTransition(textView1,i+0,i+4, array[2],0, 0, 0, 190, "move");

            textView1.setText(array[3] == 0 ? "" : String.valueOf(array[3]));
            formatBoard();

            textView1.setText(array[3] == 0? "" : String.valueOf(array[3]));
            textView2.setText(array[2] == 0? "" : String.valueOf(array[2]));
            textView3.setText(array[1] == 0? "" : String.valueOf(array[1]));
            textView4.setText(array[0] == 0? "" : String.valueOf(array[0]));
            formatBoard();
        }

        gameEnd();

        if(newNumber) {
            newNumber();
            formatBoard();
            newNumber = false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setActivity(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

        Toast toast = Toast.makeText(getApplicationContext(),"Welcome to 2048! \n\nMove numbers to left/right/up/down to make the sum 2048. \n\nNew number will appear after each sum/move operation in WHITE color.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

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
}
