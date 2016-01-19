package su.j2e.rvtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVh> {

	private static final String TAG = MyAdapter.class.getName();
	private String[] data = new String[20];
	{
		for (int i = 0; i < data.length; i++) {
			data[i] = "Item " + i;
		}
	}

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

	class MyVh extends RecyclerView.ViewHolder implements View.OnTouchListener {

		private final TextView text;

		private MyVh(ViewGroup parent) {
			super(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false));
			text = (TextView) itemView.findViewById(R.id.rv_item_text);
			initSwipeListener();
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

		private void initSwipeListener() {

//			itemView.setOnTouchListener(this);
		}


		@Override
		public boolean onTouch(View v, MotionEvent event) {
//			Log.d(TAG, "onTouch: ");
			return true;
		}
	}

}
