package com.droidupgrades.widget.progress;

/**
 * Created by mike on 23/09/2013.
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.droidupgrades.R;

public class PercentBar extends View
{
    private Context context;

    private NinePatchDrawable backgroundDrawable;
    private NinePatchDrawable foregroundDrawable;
    private Paint paint = new Paint();

    private int width;
    private int height;
    private int percentage;

    public PercentBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        backgroundDrawable =  (NinePatchDrawable) this.context.getResources().getDrawable(R.drawable.progress_bar_empty);
        foregroundDrawable =  (NinePatchDrawable) this.context.getResources().getDrawable(R.drawable.progress_bar_full);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        width = getWidth();
        height = getHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (backgroundDrawable != null) {
            backgroundDrawable.setBounds(0, 0, width, height);
            backgroundDrawable.draw(canvas);
        }

        if (foregroundDrawable != null) {

            double onePercent = (float) width / 100;
            double newWidth = onePercent * percentage;

            foregroundDrawable.setBounds(0, 0, (int) newWidth, height);
            foregroundDrawable.draw(canvas);
        }

        drawPercentageLabel(canvas);
    }


    private void drawPercentageLabel(Canvas canvas) {

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(0);
        paint.setTextSize(20);

        String labelText = percentage + "%";

        Rect bounds = new Rect();
        paint.getTextBounds(labelText, 0, labelText.length(), bounds);
        float textWidth = paint.measureText(labelText);

        int x = (getWidth() / 2) - ((int)textWidth / 2);
        int y = (getHeight() + bounds.height() )/ 2;

        canvas.drawText(labelText, x, y, paint);
    }

    public void setPercentage(int percentage) {
        if (percentage > 100)
            percentage = 100;

        setForegroundImage(percentage);
        this.percentage = percentage;
    }


    private void setForegroundImage(int percentage) {

        if (percentage > 75) {
            foregroundDrawable =  (NinePatchDrawable) context.getResources().getDrawable(R.drawable.progress_bar_full);
        }
        else if (percentage > 50) {
            foregroundDrawable =  (NinePatchDrawable) context.getResources().getDrawable(R.drawable.progress_bar_upper_mid);
        }
        else if (percentage > 25) {
            foregroundDrawable =  (NinePatchDrawable) context.getResources().getDrawable(R.drawable.progress_bar_lower_mid);
        }
        else {
            foregroundDrawable =  (NinePatchDrawable) context.getResources().getDrawable(R.drawable.progress_bar_low);
        }
    }


}
