package com.example.a2048;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener{

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

        Log.i("values", "deltaX : " + String.valueOf(deltaX) + ", deltaY : " + String.valueOf(deltaY));

        if(deltaX > 0 && deltaXAbs > deltaYAbs)             ((MainActivity) getActivity()).moveLeft();
        else if (deltaX < 0 && deltaXAbs > deltaYAbs)       ((MainActivity) getActivity()).moveRight();
        else if (deltaY > 0 && deltaXAbs < deltaYAbs)       ((MainActivity) getActivity()).moveUp();
        else if (deltaY < 0 && deltaXAbs < deltaYAbs)       ((MainActivity) getActivity()).moveDown();
        return true;
    }
}
