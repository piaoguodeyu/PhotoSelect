package com.an.myphotodemo.adapter;

/**
 * Created by zhangxiaowei on 16/7/14.
 */
public abstract class BaseRecyclerViewHolder {
    protected BaseRecyclerAdp.BaseAdapterEh mHolder;
    public BaseRecyclerViewHolder(BaseRecyclerAdp.BaseAdapterEh holder, int viewType) {
        this.mHolder = holder;
    }
}
