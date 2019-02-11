package com.example.user.waveview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class WaveView extends View {

    private Paint paint;
    private Path path;
    private int waveLength = 800;
    private int dx;
    private int dy;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Style.FILL_AND_STROKE);

        path = new Path();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        int originY = 200;
        if(dy<originY + 100){
            dy += 30;
        }
        int halfWaveLength = waveLength/2;
        path.moveTo(-waveLength+dx, originY-dy);
        //屏幕的宽度里面放多少个波长
        for (int i = -waveLength; i < getWidth() + waveLength; i += waveLength) {
            //相对绘制二阶贝塞尔曲线(相对于自己的起始点--也即是上一个曲线的终点的距离dx1)
            path.rQuadTo(halfWaveLength/2, -150, halfWaveLength, 0);
            path.rQuadTo(halfWaveLength/2, 150, halfWaveLength, 0);
//			path.quadTo(x1, y1, x2, y2)
        }
        //颜色填充
        //画一个封闭的空间
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, paint);
    }

    public void startAnimation(){
        ValueAnimator animator = ValueAnimator.ofInt(0,waveLength);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        //无限循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

}