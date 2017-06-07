package com.cs.endlessrecyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/5.
 */

abstract public class EndlessRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_foot_text = null;
    public ImageView iv_loading = null;

    EndlessRecyclerViewHolder(View itemView, int viewType) {
        super(itemView);
        if(viewType== EndlessRecyclerViewAdapter.FOOT_ITEM) {
            tv_foot_text = (TextView) itemView.findViewById(R.id.tv_foot_text);
            iv_loading = (ImageView) itemView.findViewById(R.id.iv_loading);
        }
    }
}