package com.an.myphotodemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.an.myphotodemo.ImageLoaderUtils;
import com.an.myphotodemo.R;
import com.an.myphotodemo.util.KeyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhangxiaowei on 2018/12/19 4:41 PM
 */
public class NinePhotoAdapter extends BaseRecyclerAdp<String, NinePhotoAdapter.ViewHolder> {
    private boolean showAdd = true;
    private int picturnNum = 9;
    private boolean isDelete = false;//当前是否显示删除按钮
    private boolean isAdd = true;//当前是否显示添加按钮

    public NinePhotoAdapter(Context context, List<String> beans) {
        super(context, beans);
        initAdd();
    }

    /**
     * 显示add按钮
     */
    public void initAdd() {
        if (getItemCount() < picturnNum) {
            isAdd = true;
            if (mData.contains(KeyUtil.empty)) {
                return;
            }
            addAt(getItemCount(), KeyUtil.empty);
//            notifyDataSetChanged();
        } else {
            isAdd = false;
        }
//        notifyDataSetChanged();
    }

    @Override
    public void notifyData(List<String> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        this.mData = data;
        mData.remove(KeyUtil.empty);
        initAdd();
        notifyDataSetChanged();
    }

    public List<String> getData() {
        return mData;
    }

    public void addAt(int position, String d) {
        if (d == null) return;
        if (mData == null) mData = new ArrayList<>();
        mData.add(position, d);
        notifyDataSetChanged();
    }

    @Override
    public void onCreateItemView() {
        addItemView(R.layout.item_grid_photo);
    }

    /**
     * 移除add按钮
     */
    public void autoHideShowAdd() {
        int lastPosition = getItemCount() - 1;
        if (lastPosition == picturnNum && mData.get(lastPosition) != null && KeyUtil.empty.equals(mData.get(lastPosition))) {
            mData.remove(lastPosition);
            isAdd = false;
            notifyDataSetChanged();
        } else if (!isAdd) {
            initAdd();
        }
    }


    @Override
    public void onBindViewHolder(NinePhotoAdapter.ViewHolder holder, final String url, int position, int viewType) {
        //显示图片
        if (KeyUtil.empty.equals(url) && showAdd) {
            ImageLoaderUtils.display(mContext, holder.imageView, R.drawable.addphoto);
            holder.imgDelete.setVisibility(View.GONE);
        } else {
            holder.imgDelete.setVisibility(View.VISIBLE);
            ImageLoaderUtils.display(mContext, holder.imageView, url);
        }


//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //再次选择图片
//                if (TextUtils.isEmpty(url)) {
//                    if (onClickAddListener != null) {
//                        onClickAddListener.onClickAdd(position);
//                    }
//                } else {
//                    //放大查看图片
//
//                    onItemClickListener.onItemClick(position);
//                    Log.i(TAG, "onClick: " + position);
//                    //BigImagePagerActivity.startImagePagerActivity((Activity) mContext, getData(), position);
//                }
//            }
//        });
        //删除按钮
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(url);
                initAdd();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public NinePhotoAdapter.ViewHolder getChildHolder(BaseAdapterEh holder, int viewType) {
        return new ViewHolder(holder, viewType);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        ImageView imageView;
        ImageView imgDelete;

        public ViewHolder(BaseAdapterEh<ViewHolder> holder, int viewType) {
            super(holder, viewType);
            imageView = holder.findView(R.id.img_photo);
            imgDelete = holder.findView(R.id.img_delete);
        }
    }
}
