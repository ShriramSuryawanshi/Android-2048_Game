package com.example.a2048;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener{

    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    private MainActivity activity = null;

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float deltaX = e1.getX() - e2.getX();
        float deltaY = e1.getY() - e2.getY();
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X)) {
            if(deltaX > 0) {
              //  ((MainActivity) getActivity()).moveLeft();
                Log.i("swipe", "Left");
            }
            else {
                //((MainActivity) getActivity()).moveRight();
                Log.i("swipe", "Right");
            }
        }

        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y)) {
            if(deltaY > 0) {
                //((MainActivity) getActivity()).moveUp();
                Log.i("swipe", "Up");
            }
            else {
                //((MainActivity) getActivity()).moveDown();
                Log.i("swipe", "Down");
            }
        }

        return true;
    }
}
