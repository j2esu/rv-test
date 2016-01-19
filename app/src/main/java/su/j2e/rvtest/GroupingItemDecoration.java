package su.j2e.rvtest;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GroupingItemDecoration extends RecyclerView.ItemDecoration {

	private int textSize = 50;
	private int groupSpacing = 100;
	private int itemsInGroup = 3;

	private Paint paint = new Paint();

	{
		paint.setTextSize(textSize);
	}

	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			View view = parent.getChildAt(i);
			int position = parent.getChildAdapterPosition(view);
			if (position % itemsInGroup == 0) {
				c.drawText("Group " + (position / itemsInGroup + 1), view.getLeft(),
						view.getTop() - groupSpacing / 2 + textSize / 3, paint);
			}
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		if (parent.getChildAdapterPosition(view) % itemsInGroup == 0) {
			outRect.set(0, groupSpacing, 0, 0);
		}
	}

}
