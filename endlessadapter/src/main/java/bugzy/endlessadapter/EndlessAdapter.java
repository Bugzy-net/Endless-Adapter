package bugzy.endlessadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by OMohamed on 1/15/2017.
 */

public abstract class EndlessAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected static final int FOOTER_VIEW = 1;
    protected static final int NORMAL_VIEW = 2;

    protected List<T> data;
    protected boolean showLoading = true;
    protected LoadMoreListener loadMoreListener;
    protected LayoutInflater inflater;
    protected boolean isLoading = false;

    public EndlessAdapter(@NonNull Context context, @NonNull List<T> data, @Nullable LoadMoreListener loadMoreListener) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.loadMoreListener = loadMoreListener;
        if (loadMoreListener == null) {
            setEndless(false);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW) {
            ProgressBar progressBar = new ProgressBar(parent.getContext());
            progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //noinspection unchecked
            return (VH) new LoadingViewHolder(progressBar);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size() && showLoading) {
            return FOOTER_VIEW;
        } else {
            return NORMAL_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == NORMAL_VIEW) {
            T object = data.get(position);
            onBindNormalView(holder, position, object);
        } else {
            onBindLoadingView();
        }
    }

    @Override
    public int getItemCount() {
        if (showLoading) {
            return data.size() + 1;
        } else {
            return data.size();
        }
    }

    public void setEndless(boolean isEndless) {
        if (showLoading != isEndless) {
            showLoading = isEndless;
            if (isEndless) {
                notifyItemInserted(getItemCount() - 1);
            } else {
                notifyItemRemoved(getItemCount());
            }
        }
    }

    protected T getItem(int position) {
        return data.get(position);
    }

    public void addItems(List<T> items) {
        int oldSize = data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(oldSize, items.size());
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    protected abstract void onBindNormalView(VH viewHolder, int position, T object);

    protected void onBindLoadingView() {
        if (!isLoading && !data.isEmpty()) {
            isLoading = true;
            loadMoreListener.loadMore(new LoadCompletedListener() {
                @Override
                public void onLoadCompleted(boolean foundData) {
                    if (!foundData) {
                        setEndless(false);
                    }
                    isLoading = false;
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}