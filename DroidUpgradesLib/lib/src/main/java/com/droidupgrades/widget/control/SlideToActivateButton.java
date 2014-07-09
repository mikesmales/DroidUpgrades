package com.droidupgrades.widget.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;



/**
 * Created by mikesmales on 07/07/2014.
 */
public class SlideToActivateButton extends Button {

    private Context context;
    private Drawable thumbDrawable;

    private int viewWidth;
    private boolean dragging = false;
    private float currentX = 0;

    private int thumbWidth;
    private int thumbHeight;
    private int thumbCentre;

    private int startLeft;
    private int startRight;
    private int endLeft;
    private int endRight;

    private boolean endReached;

    public SlideToActivateButton(Context context) {
        super(context);
        setup(context);
    }

    public SlideToActivateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public SlideToActivateButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context);
    }

    private void setup(Context context) {
        this.context = context;
    }

    public void setSliderImage(int resId) {
        thumbDrawable =  this.context.getResources().getDrawable(resId);
        thumbWidth = thumbDrawable.getIntrinsicWidth();
        thumbHeight = thumbDrawable.getIntrinsicHeight();
        thumbCentre = thumbWidth / 2;
    }

    private void initParams() {
        startLeft = 0;
        startRight = thumbWidth;
        endLeft = viewWidth - thumbWidth;
        endRight = viewWidth;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        viewWidth = xNew;
        initParams();
        extendTouchBounds();
    }


    private Rect calcBounds() {

        if (dragging) {
            return calcBoundsDragging();
        } else {
            return new Rect(0, 0, thumbWidth, thumbHeight);
        }
    }

    private Rect calcBoundsDragging() {

        int left = (int) (currentX - thumbCentre);
        int right = (int) (currentX + thumbCentre);
        int bottom = 0;
        int top = thumbHeight;

        if (left < 0) {
            left = startLeft;
            right = startRight;
        }
        else if (right > viewWidth) {
            left = endLeft;
            right = endRight;

            sendClick();
        }
        return new Rect(left, bottom, right, top);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect bounds = calcBounds();
        thumbDrawable.setBounds(bounds);
        thumbDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnabled())
            return false;

        final int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                dragging = true;
                break;

            case MotionEvent.ACTION_MOVE:

                if (dragging) {
                    currentX = event.getX();
                }
                break;

            case MotionEvent.ACTION_UP:
                dragging = false;
                break;
        }

        invalidate();
        return true;
    }

    private void sendClick() {

        if (!endReached) {
            endReached = true;
            performClick();
        }
    }


    private void extendTouchBounds() {

        Rect delegateArea = new Rect();
        getHitRect(delegateArea);

        delegateArea.top += 100;
        delegateArea.bottom += 100;

        TouchDelegate touchDelegate = new TouchDelegate(delegateArea, this);

        ViewParent parent = this.getParent();

        if (View.class.isInstance(parent)) {
            ((View) parent).setTouchDelegate(touchDelegate);
        }
    }
}