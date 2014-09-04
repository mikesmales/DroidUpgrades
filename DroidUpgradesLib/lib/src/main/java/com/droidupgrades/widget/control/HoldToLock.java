package com.droidupgrades.widget.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

import com.droidupgrades.R;
import com.droidupgrades.widget.helpers.TimerHandler;

/**
 * Created by mike on 03/07/2014.
 */
public class HoldToLock extends ImageButton {

    public enum State {
        UNLOCKED, LOCKING, LOCKED
    }

    private final static int UPDATE_INTERVAL_TIME_MS = 1;

    private State state = State.UNLOCKED;
    private Paint paint = new Paint();
    private TimerHandler timerHandler;

    private final RectF lockingCircleBounds = new RectF();
    private int lockingBorderColor = 0xff000000;
    private int lockingBorderStrokeWidth = 10;
    private float lockingProgressRotation = 0;

    private float viewRadius;
    private float translationOffsetX;
    private float translationOffsetY;

    public HoldToLock(Context context) {
        super(context);
        setup();
    }

    public HoldToLock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoldToLock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttributes(context, attrs, defStyle);
        setup();
    }

    private void setAttributes(Context context, AttributeSet attrs, int defStyle) {

        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HoldToLock, defStyle, 0);

        lockingBorderColor = attributes.getColor(R.styleable.HoldToLock_locking_color, Color.BLACK);
        lockingBorderStrokeWidth = (int) attributes.getDimension(R.styleable.HoldToLock_locking_stroke_width, 10);
        attributes.recycle();
    }

    private void setup() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(lockingBorderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lockingBorderStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        int viewWidth = xNew;
        int viewHeight = yNew;

        viewRadius = viewWidth * 0.5f;

        float lockingRadius = viewRadius - getPaddingTop();
        lockingRadius = lockingRadius + lockingBorderStrokeWidth;

        lockingCircleBounds.set(-lockingRadius, -lockingRadius, lockingRadius, lockingRadius);

        translationOffsetX = viewRadius;
        translationOffsetY = viewRadius;
    }


    @Override
    protected void onDraw(final Canvas canvas) {

        super.onDraw(canvas);

        canvas.translate(translationOffsetX, translationOffsetY);

        if (state == State.LOCKING) {

            canvas.drawArc(lockingCircleBounds, 270, lockingProgressRotation, false, paint);
        }
        else if (state == State.LOCKED) {

            canvas.drawArc(lockingCircleBounds, 270, 360, false, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnabled())
            return false;

        final int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                state = State.LOCKING;
                setPressed(true);
                startLockingTimer();
                break;

            case MotionEvent.ACTION_UP:

                if (state == State.LOCKED) {

                }
                else {
                    state = State.UNLOCKED;
                    setPressed(false);
                    lockingProgressRotation = 0;
                    timerHandler.stop();
                }
                break;
        }

        invalidate();
        return true;
    }


    private void startLockingTimer() {
        timerHandler = new TimerHandler(timerTask);
        timerHandler.sleep(UPDATE_INTERVAL_TIME_MS);
    }

    private Runnable timerTask = new Runnable() {

        @Override
        public void run() {

            if (lockingProgressRotation >= 360) {

                state = State.LOCKED;
                performClick();
                invalidate();
                return;
            }
            lockingProgressRotation = lockingProgressRotation + 6;

            invalidate();
            timerHandler.sleep(UPDATE_INTERVAL_TIME_MS);

        }
    };

    public void reset() {
        state = State.UNLOCKED;
        setPressed(false);
        lockingProgressRotation = 0;
        timerHandler.stop();
    }

}