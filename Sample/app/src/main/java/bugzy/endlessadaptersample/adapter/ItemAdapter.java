package bugzy.endlessadaptersample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bugzy.endlessadapter.EndlessAdapter;
import bugzy.endlessadapter.LoadMoreListener;
import bugzy.endlessadapter.R;
import bugzy.endlessadaptersample.model.Item;
import bugzy.endlessadaptersample.viewholder.ItemViewHolder;

/**
 * Created by omar on 7/12/17.
 */

public class ItemAdapter extends EndlessAdapter<Item, RecyclerView.ViewHolder> {
    private Item latestItem;
    public ItemAdapter(@NonNull Context context, @NonNull List<Item> data, @NonNull LoadMoreListener loadMoreListener) {
        super(context, data, loadMoreListener);
        latestItem = data.get(data.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewHolder == null) {
            return new ItemViewHolder(inflater.inflate(R.layout.layout_item, parent, false));
        } else {
            return viewHolder;
        }
    }

    @Override
    protected void onBindNormalView(RecyclerView.ViewHolder viewHolder, int position) {
        Item item = data.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        itemViewHolder.textView.setText(item.getName());

        itemViewHolder.deleteAppCompatImageButton.setTag(item);
        itemViewHolder.deleteAppCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item1 = (Item) view.getTag();
                int index = data.indexOf(item1);
                if (index > -1) {
                    data.remove(item1);
                    notifyItemRemoved(index);
                }
            }
        });

        itemViewHolder.addAppCompatImageButton.setTag(item);
        itemViewHolder.addAppCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item1 = (Item) view.getTag();
                int index = data.indexOf(item1);
                latestItem = createNewItem();
                data.add(index, latestItem);
                notifyItemInserted(index);
            }
        });
    }

    @Override
    public void addItems(List<Item> items) {
        super.addItems(items);
        latestItem = items.get(items.size() - 1);
    }

    private Item createNewItem() {
        return new Item(latestItem.getId() + 1, "Item #" + (latestItem.getId() + 1));
    }
}
