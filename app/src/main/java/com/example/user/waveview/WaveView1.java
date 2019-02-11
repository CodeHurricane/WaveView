package com.example.user.waveview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class WaveView1 extends View {

	private Paint paint;
	private Path path;
	private int waveLength = 800;
	protected float fraction;
	private Bitmap mBitmap;
	private PathMeasure mPathMeasure;
	private Matrix mMatrix;

	public WaveView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL_AND_STROKE);
		
		path = new Path();
		
		Options options = new Options();
		options.inSampleSize = 2;
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg,options );

	}

	private void setPathData() {
		path.reset();
		int halfWaveLength = waveLength/2;
		path.moveTo(-waveLength+fraction*waveLength, 500);
		for (int i = -waveLength; i < getWidth() + waveLength; i += waveLength) {
			path.rQuadTo(halfWaveLength/2, -150, halfWaveLength, 0);
			path.rQuadTo(halfWaveLength/2, 150, halfWaveLength, 0);
		}
		path.lineTo(getWidth(), getHeight());
		path.lineTo(0, getHeight());
		path.close();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		setPathData();
		mPathMeasure = new PathMeasure(path, false);
		mMatrix = new Matrix();
		mMatrix.reset();
		mPathMeasure.getMatrix((waveLength+getWidth()/2)-fraction*waveLength, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG|PathMeasure.POSITION_MATRIX_FLAG);
		mMatrix.preTranslate(-mBitmap.getWidth()/2, -mBitmap.getHeight());
		canvas.drawPath(path, paint);
       canvas.drawBitmap(mBitmap, mMatrix, null);
	}
	
	public void startAnimation(){
		ValueAnimator animator = ValueAnimator.ofFloat(0,1);
		animator.setDuration(10000);
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				fraction = (float) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		animator.start();
		
	}
	
}
