package su.j2e.rvtest;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class SwipeTouchListener extends RecyclerView.SimpleOnItemTouchListener {

	private static final int SWIPE_DIR_RIGHT = 0;
	private static final int SWIPE_DIR_LEFT = 1;
	private static final String TAG = SwipeTouchListener.class.getName();

	public interface Callback {

		void onDragSwipe(View view, float dx);

//		void onFlingSwipe(View view, int dir);
//
//		void onCancelSwipe(View view, float dx);

	}

	private float mTouchSlop;
	private final Callback mCallback;

	private View mSwipeView;
	private boolean mSwipe = false;
	private boolean mScroll = false;
	private float downX;
	private float downY;
	private VelocityTracker mVelocityTracker;

	public SwipeTouchListener(Context context, Callback callback) {
		mCallback = callback;
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mVelocityTracker = VelocityTracker.obtain();
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent ev) {
		final int action = MotionEventCompat.getActionMasked(ev);
		switch (action) {
			case MotionEvent.ACTION_MOVE: {
				if (mSwipe) return true;//handle event by item
				if (mScroll) return false;//handle event by recycler view
				//if no current scroll or swipe
				float dx = Math.abs(downX - ev.getX());
				float dy = Math.abs(downY - ev.getY());
				if (dx > mTouchSlop && dx > dy) {
					mSwipe = true;
					mVelocityTracker.clear();
					mSwipeView = rv.findChildViewUnder(downX, downY);
					return true;
				}
				if (dy > mTouchSlop && dy >= dx) {
					mScroll = true;
					return false;
				}
				return false;
			}
			case MotionEvent.ACTION_DOWN:
				downX = ev.getX();
				downY = ev.getY();
				return false;
//				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:

				break;

		}
		//if were swiping
		if (mSwipe) {
			mVelocityTracker.computeCurrentVelocity(1000);
			float vx = VelocityTrackerCompat.getXVelocity(mVelocityTracker, 0);
//			mCallback.onFlingSwipe(mSwipeView, ev.getX() - downX >);
			Log.d(TAG, "onInterceptTouchEvent: " + vx);
			mSwipe = false;
			mSwipeView = null;
		}
		mScroll = false;
		return false;
	}

	@Override
	public void onTouchEvent(RecyclerView rv, MotionEvent ev) {
		mVelocityTracker.addMovement(ev);
		if (mSwipe && mSwipeView != null)
		mCallback.onDragSwipe(mSwipeView, ev.getX() - downX);
	}

}
