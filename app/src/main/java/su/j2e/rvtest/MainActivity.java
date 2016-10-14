package su.j2e.rvtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter adapter = new MyAdapter();
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
//        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(adapter);
//        rv.addOnItemTouchListener(new SwipeTouchListener(this, adapter));
//        rv.addItemDecoration(new GroupingItemDecoration());

        RecyclerView rv2 = (RecyclerView) findViewById(R.id.rv2);
        rv2.setAdapter(adapter);
    }

}
