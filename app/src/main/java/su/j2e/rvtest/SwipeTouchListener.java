package su.j2e.rvtest;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class SwipeTouchListener extends RecyclerView.SimpleOnItemTouchListener {

	private static final String TAG = SwipeTouchListener.class.getName();

	public interface Callback {

		void onDragSwipe(View view, float dx);

		void onFlingSwipe(View view, float dx, float vx);

		void onCancelSwipe(View view, float dx);

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
		int action = MotionEventCompat.getActionMasked(ev);
		switch (action) {
			case MotionEvent.ACTION_MOVE: {
				if (mSwipe) return true;//handle by item
				if (mScroll) return false;//handle event by recycler view
				//if no current scroll or swipe
				float dx = Math.abs(downX - ev.getX());
				float dy = Math.abs(downY - ev.getY());
				if (dx > mTouchSlop && dx > dy) {//detect swipe
					mSwipe = true;
					mVelocityTracker.clear();
					mSwipeView = rv.findChildViewUnder(downX, downY);
					return true;//handle by item
				}
				if (dy > mTouchSlop && dy >= dx) {//detect scroll
					mScroll = true;
				}
				return false;
			}
			case MotionEvent.ACTION_DOWN:
				downX = ev.getX();
				downY = ev.getY();
				return false;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mScroll = false;
				return false;
			default:
				return false;
		}
	}

	@Override
	public void onTouchEvent(RecyclerView rv, MotionEvent ev) {
		int action = MotionEventCompat.getActionMasked(ev);
		switch (action) {
			case MotionEvent.ACTION_UP:
				if (mSwipe && mSwipeView != null) {//if were swiping
					mVelocityTracker.computeCurrentVelocity(1000);
					float vx = VelocityTrackerCompat.getXVelocity(mVelocityTracker, 0);
					mCallback.onFlingSwipe(mSwipeView, ev.getX(), vx);
					mSwipe = false;
					mSwipeView = null;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				if (mSwipe && mSwipeView != null) mCallback.onCancelSwipe(mSwipeView, ev.getX());
				break;
			case MotionEvent.ACTION_MOVE://this runs if swiping (swipe check not needed)
				mVelocityTracker.addMovement(ev);
				if (mSwipeView != null) {
					mCallback.onDragSwipe(mSwipeView, ev.getX() - downX);
				}
				break;
		}
	}

}
