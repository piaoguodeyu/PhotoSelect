package com.an.myphotodemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhangxiaowei on 16/1/3.
 */
public abstract class BaseRecyclerAdp<T, F extends BaseRecyclerViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Activity mContext;
    public List<T> mData;
    /**
     * -1头部布局，-2尾部布局
     */
    SparseIntArray mLayoutIds = new SparseIntArray();
//    private DialogProgress mDialogProgerss;

    private BaseRecyclerAdp() {

    }

    public BaseRecyclerAdp(Context context) {
        this(context, null);
    }

    public BaseRecyclerAdp(Context context, List<T> beans) {
        onCreateItemView();
        mData = beans;
        mContext = (Activity) context;
    }

    public void notifyData(List<T> mData) {
        if (mData == null || mData.isEmpty()) {
            return;
        }
        this.mData = mData;
        notifyDataSetChanged();
    }


    public T getItem(int position) {
        return mData.get(position);
    }

    public abstract void onCreateItemView();

    /**
     * 多个布局
     *
     * @param viewId   layoutId
     * @param viewType 布局类型
     */
    public void addItemView(int viewId, int viewType) {
        mLayoutIds.put(viewType, viewId);
    }

    /**
     * 仅适用一种布局
     *
     * @param viewId
     */
    public void addItemView(int viewId) {
        mLayoutIds.put(0, viewId);
    }

    public interface ItemClick<T> {
        void onItemClick(int position, T bean);
    }

    public void setOnItemClcik(ItemClick listener) {
        mItemClick = listener;
    }

    ItemClick mItemClick;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 final int position) {
        if (holder instanceof BaseRecyclerAdp.BaseAdapterEh) {
            final int viewType = getItemViewType(position);
            T data = mData.get(position);
            onBindViewHolder(((BaseAdapterEh<F>) holder).holder, data, position, viewType);
        }
    }

    public abstract void onBindViewHolder(F holder, T data, int position, int viewType);


    @Override
    public BaseAdapterEh onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(
                mContext).inflate(mLayoutIds.get(viewType), parent,
                false);
        BaseAdapterEh<F> holder = new BaseAdapterEh<F>(layout);
        F childer = getChildHolder(holder, viewType);
        holder.holder = childer;
        return holder;
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void remove(T bean) {
        mData.remove(bean);
        notifyDataSetChanged();
    }

    public void toast(final String str) {
        if (mContext != null) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, str, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }

    }

//    public void closeDialog() {
//        if (mDialogProgerss != null) {
//            mDialogProgerss.dismiss();
//            mDialogProgerss = null;
//        }
//    }
//
//    public void showCommonDialog() {
//        if (mDialogProgerss == null) {
//            mDialogProgerss = new DialogProgress(mContext);
//        }
//        if (!mDialogProgerss.isShowing()) {
//            mDialogProgerss.show();
//        }
//    }

    public abstract F getChildHolder(BaseAdapterEh holder, int viewType);

    public class BaseAdapterEh<F extends BaseRecyclerViewHolder> extends RecyclerView.ViewHolder {
        F holder;
        View layout;

        public <M> M findView(int id) {
            return (M) layout.findViewById(id);
        }

        public BaseAdapterEh(View itemView) {
            super(itemView);
            layout = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClick != null) {
                        int position = getLayoutPosition();
                        mItemClick.onItemClick(position, mData.get(position));
                    }
                }
            });
        }

        public void setLayoutParams(ViewGroup.LayoutParams params) {
            layout.setLayoutParams(params);
        }
    }

}
