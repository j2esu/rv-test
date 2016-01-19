package su.j2e.rvtest;

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
		view.setTranslationX(dx);
	}
}
