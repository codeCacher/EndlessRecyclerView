package com.cs.endlessrecyclerviewtest;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/28.
 * Blog item adapter
 */

public abstract class EndlessRecyclerViewAdapter<VH extends EndlessRecyclerViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = "EndlessRecyclerViewAdapter";
    public static final int FOOT_ITEM = 0;
    public static final int FOOT_ITEM_GONE = 0;
    public static final int FOOT_ITEM_MORE = 1;
    public static final int FOOT_ITEM_FAIL = 2;
    public static final int FOOT_ITEM_LOADING = 3;

    private Context context;

    private int mFootViewState;

    public abstract int getEndlessItemCount();

    public abstract int getEndlessItemViewType(int position);

    public abstract VH onCreateEndlessViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindEndlessViewHolder(VH holder, int position);

    public EndlessRecyclerViewAdapter(Context context) {
        this.context = context;
        mFootViewState = FOOT_ITEM_GONE;
    }

    /**
     * remove the foot item
     */
    public void hideFootItem() {
        mFootViewState = FOOT_ITEM_GONE;
        this.notifyDataSetChanged();
    }

    public int footItemState() {
        return mFootViewState;
    }

    public void setFootViewSuccess() {
        mFootViewState = FOOT_ITEM_MORE;
        this.notifyDataSetChanged();
    }

    /**
     * set foot item to fail state
     */
    public void setFootViewFail() {
        mFootViewState = FOOT_ITEM_FAIL;
        this.notifyDataSetChanged();
    }

    /**
     * set foot item to loading state
     */
    public void setFootViewLoading() {
        mFootViewState = FOOT_ITEM_LOADING;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getEndlessItemCount()) {
            return FOOT_ITEM;
        } else {
            return getEndlessItemViewType(position);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOT_ITEM) {
            MyViewHolder viewHolder = new MyViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.blog_foot_item, parent, false),
                    FOOT_ITEM);
            return (VH) viewHolder;
        } else {
            return onCreateEndlessViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == FOOT_ITEM) {
            switch (mFootViewState) {
                case FOOT_ITEM_GONE:
                    holder.itemView.setVisibility(View.GONE);
                    break;
                case FOOT_ITEM_LOADING:
                    holder.itemView.setVisibility(View.VISIBLE);
                    AnimationDrawable background = (AnimationDrawable) holder.iv_loading.getDrawable();
                    background.start();
                    holder.iv_loading.setVisibility(View.VISIBLE);
                    holder. tv_foot_text.setText("加载中....");
                    break;
                case FOOT_ITEM_FAIL:
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.iv_loading.setVisibility(View.GONE);
                    holder.tv_foot_text.setText("还没有联网哦，去设置网络吧");
                    break;
                case FOOT_ITEM_MORE:
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.iv_loading.setVisibility(View.GONE);
                    holder.tv_foot_text.setText("更多...");
                    break;
            }
        } else {
            onBindEndlessViewHolder(holder, position);
        }
    }


    @Override
    public int getItemCount() {
        return getEndlessItemCount() + 1;
    }

    private class MyViewHolder extends EndlessRecyclerViewHolder {
        MyViewHolder(View itemView, int viewType) {
            super(itemView, viewType);
        }
    }
}
