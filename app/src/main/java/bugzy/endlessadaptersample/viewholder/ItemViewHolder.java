package bugzy.endlessadaptersample.viewholder;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bugzy.endlessadaptersample.R;

/**
 * Created by omar on 7/12/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageButton deleteAppCompatImageButton;
    public AppCompatImageButton addAppCompatImageButton;
    public TextView textView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        deleteAppCompatImageButton = itemView.findViewById(R.id.delete_button);
        addAppCompatImageButton = itemView.findViewById(R.id.add_button);
        textView = itemView.findViewById(R.id.item_name);
    }
}
