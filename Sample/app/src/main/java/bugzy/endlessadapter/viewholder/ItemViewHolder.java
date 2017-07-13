package bugzy.endlessadapter.viewholder;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bugzy.endlessadapter.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by omar on 7/12/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.delete_button) public AppCompatImageButton deleteAppCompatImageButton;
    @BindView(R.id.add_button) public AppCompatImageButton addAppCompatImageButton;
    @BindView(R.id.item_name) public TextView textView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
