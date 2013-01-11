package com.hiapk.ui.chart;

import java.util.Timer;
import java.util.TimerTask;

import com.hiapk.logs.Logs;
import com.hiapk.spearhead.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleProgress extends View {

	private static final int DEFAULT_MAX_VALUE = 100; // 默认进度条最大�1�7�1�7
	private static final int DEFAULT_PAINT_WIDTH = 10; // 默认画笔宽度
	private static final int DEFAULT_PAINT_COLOR = 0xffffcc00; // 默认画笔颜色
	private static final boolean DEFAULT_FILL_MODE = true; // 默认填充模式
	private static final int DEFAULT_INSIDE_VALUE = 0; // 默认缩进距离
	private static final boolean DEFAULT_HEIGH_CIRCLE = false; // 以高为基准的圆形
	private static final boolean DEFAULT_WIDTH_CIRCLE = false; // 以宽为基准的圆形
	private String TAG = "CircleProgress";
	private CircleAttribute mCircleAttribute; // 圆形进度条基本属怄1�7
	private int fontSize = 40;// 依据屏幕宽度自动
	private int fontRate = 6;// 依据屏幕宽度自动

	private int mMaxProgress; // 进度条最大�1�7�1�7
	private int mMainCurProgress; // 主进度条当前倄1�7
	private int mSubCurProgress; // 子进度条当前倄1�7
	// 是否完全的圆形
	private boolean isCircleHeight;
	private boolean isCircleWidth;
	private boolean isBackgroundColorful = false;// 是否采用彩色背景
	// 图片长宽
	private int mWith = 0;
	private int mHeight = 0;
	/**
	 * 笔刷宽度与图片宽度的比例
	 */
	private int brushWidthRate = 6;
	/**
	 * 填充模式
	 * 
	 */
	private boolean isbFill = false;

	public boolean isBackgroundColorful() {
		return isBackgroundColorful;
	}

	public void setBackgroundColorful(boolean isBackgroundColor) {
		this.isBackgroundColorful = isBackgroundColor;
	}

	private CartoomEngine mCartoomEngine; // 动画引擎

	private Drawable mBackgroundPicture; // 背景囄1�7

	public CircleProgress(Context context) {
		super(context);
		defaultParam();
	}

	public CircleProgress(Context context, AttributeSet attrs) {
		super(context, attrs);

		defaultParam();

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.CircleProgressBar);

		mMaxProgress = array.getInteger(R.styleable.CircleProgressBar_max,
				DEFAULT_MAX_VALUE); // 获取进度条最大�1�7�1�7

		boolean bFill = array.getBoolean(R.styleable.CircleProgressBar_fill,
				DEFAULT_FILL_MODE); // 获取填充模式
		this.isbFill = bFill;
		int paintWidth = array.getInt(
				R.styleable.CircleProgressBar_Paint_Width, DEFAULT_PAINT_WIDTH); // 获取画笔宽度
		mCircleAttribute.setFill(bFill);
		if (bFill == false) {
			brushWidthRate = paintWidth;
			mCircleAttribute.setPaintWidth(brushWidthRate);
		}

		int paintColor = array.getColor(
				R.styleable.CircleProgressBar_Paint_Color, DEFAULT_PAINT_COLOR); // 获取画笔颜色

		Log.i("", "paintColor = " + Integer.toHexString(paintColor));
		mCircleAttribute.setPaintColor(paintColor);

		mCircleAttribute.mSidePaintInterval = array.getInt(
				R.styleable.CircleProgressBar_Inside_Interval,
				DEFAULT_INSIDE_VALUE);// 圆环缩进距离
		// 是否完全圆形
		isCircleHeight = array.getBoolean(
				R.styleable.CircleProgressBar_TrueCircle_Heitht,
				DEFAULT_HEIGH_CIRCLE);
		isCircleWidth = array.getBoolean(
				R.styleable.CircleProgressBar_TrueCircle_Width,
				DEFAULT_WIDTH_CIRCLE);
		array.recycle(); // 丄1�7定要调用，否则会有问预1�7

	}

	/*
	 * 默认参数
	 */
	private void defaultParam() {
		mCircleAttribute = new CircleAttribute();

		mCartoomEngine = new CartoomEngine();

		mMaxProgress = DEFAULT_MAX_VALUE;
		mMainCurProgress = 0;
		mSubCurProgress = 0;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { // 设置视图大小
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		mWith = width;
		mHeight = height;
		Logs.d(TAG, "width=" + width + ",height=" + height);
		mBackgroundPicture = getBackground();
		if (mBackgroundPicture != null) {
			width = mBackgroundPicture.getMinimumWidth();
			height = mBackgroundPicture.getMinimumHeight();
		}
		mCircleAttribute.setPaintColor(0x00ffffff);
		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(width, heightMeasureSpec));
		invalidate();

	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mCircleAttribute.autoFix(w, h);

	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isbFill) {
			mCircleAttribute.setPaintWidth(brushWidthRate);
		}

		if (mBackgroundPicture == null) // 没背景图的话就绘制底艄1�7
		{
			canvas.drawArc(mCircleAttribute.mRoundOval, 0, 360,
					mCircleAttribute.mBRoundPaintsFill,
					mCircleAttribute.mBottomPaint);
		}
		float subRate = (float) mSubCurProgress / mMaxProgress;
		float subSweep = 360 * subRate;
		canvas.drawArc(mCircleAttribute.mRoundOval, mCircleAttribute.mDrawPos,
				subSweep, mCircleAttribute.mBRoundPaintsFill,
				mCircleAttribute.mSubPaint);

		float rate = (float) mMainCurProgress / mMaxProgress;
		float sweep = 360 * rate;

		canvas.drawArc(mCircleAttribute.mRoundOval, mCircleAttribute.mDrawPos,
				sweep, mCircleAttribute.mBRoundPaintsFill,
				mCircleAttribute.mMainPaints);
		// shezhi ziti
		fontSize = mWith / fontRate;
		mCircleAttribute.mFrontPaint.setTextSize(fontSize);
		if (mMainCurProgress < 10) {
			canvas.drawText(mMainCurProgress + "%", mWith / 2 - fontSize / 2,
					mHeight / 2 + fontSize / 3, mCircleAttribute.mFrontPaint);
		} else if (mMainCurProgress == 100) {
			canvas.drawText(mMainCurProgress + "%",
					mWith / 2 - fontSize * 1.2f, mHeight / 2 + fontSize / 3,
					mCircleAttribute.mFrontPaint);
		} else {
			canvas.drawText(mMainCurProgress + "%",
					mWith / 2 - fontSize * 0.8f, mHeight / 2 + fontSize / 3,
					mCircleAttribute.mFrontPaint);
		}

	}

	/*
	 * 设置主进度�1�7�1�7
	 */
	public synchronized void setMainProgress(int progress) {
		mMainCurProgress = progress;
		if (mMainCurProgress < 0) {
			mMainCurProgress = 0;
		}

		if (mMainCurProgress > mMaxProgress) {
			mMainCurProgress = mMaxProgress;
		}

		invalidate();
	}

	public synchronized int getMainProgress() {
		return mMainCurProgress;
	}

	/*
	 * 设置子进度�1�7�1�7
	 */
	public synchronized void setSubProgress(int progress) {
		mSubCurProgress = progress;
		if (mSubCurProgress < 0) {
			mSubCurProgress = 0;
		}

		if (mSubCurProgress > mMaxProgress) {
			mSubCurProgress = mMaxProgress;
		}

		invalidate();
	}

	public synchronized int getSubProgress() {
		return mSubCurProgress;
	}

	/*
	 * 弄1�7启动甄1�7
	 */
	public void startCartoom(int time) {
		mCartoomEngine.startCartoom(time);

	}

	/*
	 * 结束动画
	 */
	public void stopCartoom() {
		mCartoomEngine.stopCartoom();
	}

	class CircleAttribute {
		public RectF mRoundOval; // 圆形扄1�7在矩形区埄1�7
		public boolean mBRoundPaintsFill; // 是否填充以填充模式绘制圆彄1�7
		public int mSidePaintInterval; // 圆形向里缩进的距禄1�7
		public int mPaintWidth; // 圆形画笔宽度（填充模式下无视＄1�7
		public int mPaintColor; // 画笔颜色
								// （即主进度条画笔颜色，子进度条画笔颜色为其半透明值）
		public int mDrawPos; // 绘制圆形的起点（默认丄1�7-90度即12点钟方向＄1�7

		public Paint mMainPaints; // 主进度条画笔
		public Paint mSubPaint; // 子进度条画笔

		public Paint mBottomPaint; // 无背景图时绘制所用画笄1�7
		public Paint mFrontPaint; // 无背景图时绘制所用画笄1�7

		public CircleAttribute() {
			mRoundOval = new RectF();
			mBRoundPaintsFill = DEFAULT_FILL_MODE;
			mSidePaintInterval = DEFAULT_INSIDE_VALUE;
			mPaintWidth = 0;
			mPaintColor = DEFAULT_PAINT_COLOR;
			mDrawPos = 0;

			mMainPaints = new Paint();
			mMainPaints.setAntiAlias(true);
			mMainPaints.setStyle(Paint.Style.FILL);
			mMainPaints.setStrokeWidth(mPaintWidth);
			mMainPaints.setColor(mPaintColor);

			mSubPaint = new Paint();
			mSubPaint.setAntiAlias(true);
			mSubPaint.setStyle(Paint.Style.FILL);
			mSubPaint.setStrokeWidth(mPaintWidth);
			mSubPaint.setColor(mPaintColor);

			mBottomPaint = new Paint();
			mBottomPaint.setAntiAlias(true);
			mBottomPaint.setStyle(Paint.Style.FILL);
			mBottomPaint.setStrokeWidth(mPaintWidth);
			mBottomPaint.setColor(Color.GRAY);

			mFrontPaint = new Paint();
			mFrontPaint.setAntiAlias(true);
			mFrontPaint.setStyle(Paint.Style.FILL);
			mFrontPaint.setStrokeWidth(mPaintWidth);
			mFrontPaint.setColor(getResources().getColor(R.color.darkgray2));
			fontSize = mWith / 10;
			mFrontPaint.setTextSize(fontSize);

		}

		/**
		 * 设置画笔宽度1-10设置屏幕宽度比例
		 */
		public void setPaintWidth(int width) {
			Log.d("circlero", mWith / 2 / width + "");
			mMainPaints.setStrokeWidth(mWith / width);
			mSubPaint.setStrokeWidth(mWith / width);
			mBottomPaint.setStrokeWidth(mWith / width);
			mPaintWidth = mWith / width;
			autoFix(mWith, mHeight);
		}

		/*
		 * 设置画笔颜色
		 */
		public void setPaintColor(int color) {
			// if (isBackgroundColorful) {
			/* 设置渐变艄1�7 */
			// Shader mShaderMain = new RadialGradient(70, 70, 10, new int[] {
			// Color.rgb(223, 223, 223), Color.rgb(210, 210, 210),
			// Color.rgb(203, 203, 203) }, null, Shader.TileMode.CLAMP);
			// Shader mShaderMain = new SweepGradient(70, 70, new int[] {
			// Color.BLUE, Color.YELLOW, Color.RED }, null);
			Shader mShaderMain = new SweepGradient(mWith / 2, mHeight / 2,
					new int[] { Color.rgb(63, 172, 211),
							Color.rgb(249, 238, 68), Color.rgb(249, 66, 50) },
					null);
			// Matrix aa=new Matrix();
			// aa.setRotate(270);
			// mShaderMain.setLocalMatrix(aa);
			mMainPaints.setShader(mShaderMain);
			// }else
			// mMainPaints.setColor(color);
			// int color1 = color & 0x00ffffff | 0x66000000;
			// if (isBackgroundColorful) {
			/* 设置渐变艄1�7 */
			Logs.d(TAG, "mWith=" + mWith);
			Logs.d(TAG, "mHeight=" + mHeight);
			Shader mShader = new RadialGradient(
					mWith / 2,
					mHeight / 2,
					10,
					new int[] { Color.rgb(223, 223, 223),
							Color.rgb(210, 210, 210), Color.rgb(203, 203, 203) },
					null, Shader.TileMode.CLAMP);
			// Shader mShader = new SweepGradient(70, 70, new int[] {
			// Color.BLUE,
			// Color.YELLOW, Color.RED }, null);
			mBottomPaint.setShader(mShader);
			// }else
			// mSubPaint.setColor(color1);
		}

		/*
		 * 设置填充模式
		 */
		public void setFill(boolean fill) {
			mBRoundPaintsFill = fill;
			if (fill) {
				mMainPaints.setStyle(Paint.Style.FILL);
				mSubPaint.setStyle(Paint.Style.FILL);
				mBottomPaint.setStyle(Paint.Style.FILL);
			} else {
				mMainPaints.setStyle(Paint.Style.STROKE);
				mSubPaint.setStyle(Paint.Style.STROKE);
				mBottomPaint.setStyle(Paint.Style.STROKE);
			}
		}

		/*
		 * 自动修正
		 */
		public void autoFix(int w, int h) {
			Logs.d(TAG, "w=" + w + ",h=" + h);
			mWith = w;
			mHeight = h;
			int centreFix = Math.abs(w - h);
			if (isCircleHeight) {
				w = h;
			} else if (isCircleWidth) {
				h = w;
			}
			if (mSidePaintInterval != 0) {
				mRoundOval.set(mPaintWidth / 2 + mSidePaintInterval + centreFix
						/ 2, mPaintWidth / 2 + mSidePaintInterval, w
						- mPaintWidth / 2 - mSidePaintInterval + centreFix / 2,
						h - mPaintWidth / 2 - mSidePaintInterval);
			} else {

				int sl = getPaddingLeft();
				int sr = getPaddingRight();
				int st = getPaddingTop();
				int sb = getPaddingBottom();

				mRoundOval.set(sl + mPaintWidth / 2, st + mPaintWidth / 2, w
						- sr - mPaintWidth / 2, h - sb - mPaintWidth / 2);
			}
		}

	}

	class CartoomEngine {
		public Handler mHandler;
		public boolean mBCartoom; // 是否正在作动甄1�7
		public Timer mTimer; // 用于作动画的TIMER
		public MyTimerTask mTimerTask; // 动画任务
		public int mSaveMax; // 在作动画时会临时改变MAX值，该变量用于保存�1�7�以便恢处1�7
		public int mTimerInterval; // 定时器触发间隔时闄1�7(ms)
		public float mCurFloatProcess; // 作动画时当前进度倄1�7

		// private long timeMil;

		public CartoomEngine() {
			// mHandler = new Handler() {
			//
			// @Override
			// public void handleMessage(Message msg) {
			// switch (msg.what) {
			// case TIMER_ID: {
			// if (mBCartoom == false) {
			// return;
			// }
			//
			// mCurFloatProcess += 1;
			// setMainProgress((int) mCurFloatProcess);
			//
			// // long curtimeMil = System.currentTimeMillis();
			//
			// // timeMil = curtimeMil;
			//
			// if (mCurFloatProcess >= mMaxProgress) {
			// stopCartoom();
			// }
			// }
			// break;
			// }
			// }
			//
			// };

			mBCartoom = false;
			mTimer = new Timer();
			mSaveMax = 0;
			mTimerInterval = 50;
			mCurFloatProcess = 0;

		}

		public synchronized void startCartoom(int time) {
			if (time <= 0 || mBCartoom == true) {
				return;
			}

			// timeMil = 0;

			mBCartoom = true;

			setMainProgress(0);
			setSubProgress(0);

			mSaveMax = mMaxProgress;
			mMaxProgress = (1000 / mTimerInterval) * time;
			mCurFloatProcess = 0;

			mTimerTask = new MyTimerTask();
			mTimer.schedule(mTimerTask, mTimerInterval, mTimerInterval);

		}

		public synchronized void stopCartoom() {

			if (mBCartoom == false) {
				return;
			}

			mBCartoom = false;
			mMaxProgress = mSaveMax;

			setMainProgress(0);
			setSubProgress(0);

			if (mTimerTask != null) {
				mTimerTask.cancel();
				mTimerTask = null;
			}
		}

		private final static int TIMER_ID = 0x0010;

		class MyTimerTask extends TimerTask {

			@Override
			public void run() {
				Message msg = mHandler.obtainMessage(TIMER_ID);
				msg.sendToTarget();

			}

		}

	}

}
