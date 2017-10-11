package bugzy.endlessadaptersample.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bugzy.endlessadapter.LoadCompletedListener;
import bugzy.endlessadapter.LoadMoreListener;
import bugzy.endlessadapter.R;
import bugzy.endlessadaptersample.adapter.ItemAdapter;
import bugzy.endlessadaptersample.model.Item;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findElements();
        initializeRecyclerView();
        initializeSwipeRefreshLayout();
        showData();
    }

    private void findElements() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    }

    private void initializeRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showData();
            }
        });
    }

    private void showData() {
        itemAdapter = new ItemAdapter(this, createItems(0), new LoadMoreListener() {
            @Override
            public void loadMore(final LoadCompletedListener loadCompletedListener) {
                if (itemAdapter.getItemCount() < 100) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            itemAdapter.addItems(createItems(itemAdapter.getItemCount() - 1));
                            loadCompletedListener.onLoadCompleted(true);
                        }
                    }, getRandomTime());
                } else {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadCompletedListener.onLoadCompleted(false);
                        }
                    }, getRandomTime());
                }
            }
        });
        recyclerView.setAdapter(itemAdapter);
    }

    private int getRandomTime() {
        Random random = new Random();
        return random.nextInt(1500);
    }

    private List<Item> createItems(int startPosition) {
        List<Item> items = new ArrayList<>();
        for (int i = startPosition; i < (startPosition + 15); i++) {
            Item item = new Item(i, "Item #" + i);
            items.add(item);
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return items;
    }
}
