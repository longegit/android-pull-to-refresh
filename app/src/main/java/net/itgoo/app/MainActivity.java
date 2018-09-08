package net.itgoo.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener2 {
    PullToRefreshListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item);

        listView = (PullToRefreshListView) findViewById(R.id.pullListView);
        listView.setOnRefreshListener(this);
        listView.setAdapter(adapter);
        listView.setRefreshing();

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.onRefreshComplete();
            }
        }, 5000);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}
