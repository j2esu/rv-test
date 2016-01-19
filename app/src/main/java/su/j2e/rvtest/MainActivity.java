package su.j2e.rvtest;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SwipeTouchListener.Callback {

	private static final String TAG = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
		rv.setAdapter(new MyAdapter());
		rv.addOnItemTouchListener(new SwipeTouchListener(this, this));
		rv.addItemDecoration(new GroupingItemDecoration());
	}

	@Override
	public void onDragSwipe(View view, float dx) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			view.setTranslationX(dx);
		}
	}

	@Override
	public void onFlingSwipe(View view, float dx, float vx) {
		if (vx > 5000) {
			view.animate().translationX(view.getWidth()).setDuration(1000).start();
		} else if (vx < -5000) {
			view.animate().translationX(-view.getWidth()).setDuration(1000).start();
		} else {
			view.animate().translationX(0).setDuration(1000).start();
		}
	}

	@Override
	public void onCancelSwipe(View view, float dx) {

	}
}
