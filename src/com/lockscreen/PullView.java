package com.lockscreen;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class PullView extends FrameLayout {

	private Scroller mScroller;
	private int mTouchSlop;
	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;
	private int mTouchState = TOUCH_STATE_REST;
	
	public PullView(Context context) {
		super(context);
		init();
	}
	
    public PullView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    private void init() {
    	Interpolator polator = new BounceInterpolator();
		mScroller = new Scroller(getContext(), polator);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		setAlpha(1);
    }

	public void startBounceAnim(int startY, int dy, int duration) {
		mScroller.startScroll(0, startY, 0, dy, duration);
		invalidate();
	}
	
	boolean needFinish = false;
	
	@Override
	public void computeScroll() {

		if (mScroller.computeScrollOffset()) {
			int mScrollerY = mScroller.getCurrY();
			scrollTo(0, mScrollerY);
			Log.i("~peter", "mScrollerY=" + mScrollerY);
			invalidate();
		}
	}
	
	int mStartY = 0;
	int mDeltaY = 0;
	
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	Log.i("~peter", "onInterceptTouchEvent-");
    	int action = ev.getAction();
    	int currentY = (int) ev.getY();
    	switch(action) {
    	case MotionEvent.ACTION_DOWN:
    		mStartY = currentY;
    		mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
    		break;
    	case MotionEvent.ACTION_MOVE:
    		int deltaY = mStartY - currentY;
    		if(deltaY > mTouchSlop) {//向上滑动
    			mTouchState = TOUCH_STATE_SCROLLING;
    		}
    		break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
    	}
        return mTouchState != TOUCH_STATE_REST;
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	int currentY = (int) event.getY();
    	switch(action) {
    	case MotionEvent.ACTION_DOWN:
    		mStartY = currentY;
    		if (!mScroller.isFinished()){
				mScroller.abortAnimation();
			}
    		break;
    	case MotionEvent.ACTION_MOVE:
    		mDeltaY = mStartY - currentY;
    		scrollTo(0, mDeltaY);
    		float alpha = 1 - mDeltaY / 500;
    		setAlpha(alpha);
    		break;
    	case MotionEvent.ACTION_UP:
    		mTouchState = TOUCH_STATE_REST;
    		if(mDeltaY > 500) {
				MainActivity act = (MainActivity) getContext();
				act.finish();
    		}else {
    			startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);
    		}
    		break;
    	}
		return false;
    }
	
}
