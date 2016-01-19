package su.j2e.rvtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVh> implements SwipeTouchListener.Callback {

	private static final String TAG = MyAdapter.class.getName();

	private String[] data = new String[20];
	{
		for (int i = 0; i < data.length; i++) {
			data[i] = "Item " + i;
		}
	}

	private int animCancelDuration = 300;
	private int minSwipeVelocity = 4000;
	private int avgSwipeVelocity = 10000;
	private int avgSwipeAnimDuration = 100;

	private RecyclerView mRecyclerView;

	@Override
	public MyVh onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyVh(parent);
	}

	@Override
	public void onBindViewHolder(MyVh holder, int position) {
		holder.bind(data[position]);
	}

	@Override
	public int getItemCount() {
		return data.length;
	}

	@Override
	public void onDragSwipe(View view, float dx) {
		((MyVh) mRecyclerView.getChildViewHolder(view)).onDragSwipe(dx);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		mRecyclerView = recyclerView;
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onFlingSwipe(final View view, float dx, float vx) {

		if (Math.abs(vx) > minSwipeVelocity) {
			int direction = vx > 0 ? 1 : -1;
			float duration = avgSwipeAnimDuration * avgSwipeVelocity / Math.abs(vx);
			view.animate().translationX(direction * view.getWidth())
					.setDuration((long) duration).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					notifyItemRemoved(mRecyclerView.getChildAdapterPosition(view));
				}
			}).start();

		} else {
			view.animate().translationX(0).setDuration(animCancelDuration).start();
		}
	}

	@Override
	public void onCancelSwipe(View view, float dx) {

	}

	class MyVh extends RecyclerView.ViewHolder {

		private final TextView text;
		private final View topView;

		private MyVh(ViewGroup parent) {
			super(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false));
			text = (TextView) itemView.findViewById(R.id.rv_item_text);
			topView = itemView.findViewById(R.id.rv_item_top_view);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(itemView.getContext(), "click", Toast.LENGTH_SHORT).show();
				}
			});
		}

		private void bind(String s) {
			text.setText(s);
		}

		private void onDragSwipe(float dx) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				topView.setTranslationX(dx);
			}
		}

	}

}
