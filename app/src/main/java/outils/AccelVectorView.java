package outils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AccelVectorView extends View {
    private Paint paint;
    private Paint paint2;
    private float[] orientation,accel;
    public AccelVectorView(Context context) {
        super(context);
        init();
    }
    public AccelVectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.orientation = new float[3];
        this.accel = new float[3];
        paint = new Paint();
        paint2 = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.GREEN);
        paint2.setStrokeWidth(10);
        paint2.setColor(Color.BLUE);
    }
    public void setAccelXY(float[] accel){
        this.accel = accel;
        invalidate();
    }

    public void setOrientation(float[] orientation){
        this.orientation = orientation;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //boussole
        float x = (float) -Math.sin(orientation[0]);
        float y = (float) -Math.cos(orientation[0]);
        float centreX = (getWidth() / 2) ;
        float centreY = (float) (getHeight() / 2.5);

        canvas.drawLine(centreX, centreY, (x*80+centreX), (y*80+centreY), paint2);

        //accelerometre
        float startX = this.getWidth() / 2;
        float startY = this.getHeight() - getHeight() /4;
        float stopX = startX - accel[0] * 50;
        float stopY = startY + accel[1] *50;

        //canvas.drawLine(startX, startY, stopX, stopY, paint);

    }

}
