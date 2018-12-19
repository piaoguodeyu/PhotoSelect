package com.an.myphotodemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.an.myphotodemo.adapter.BaseRecyclerAdp;
import com.an.myphotodemo.adapter.NinePhotoAdapter;
import com.an.myphotodemo.adapter.NinePicturesAdapter;
import com.an.myphotodemo.image.ImagePagerActivity;
import com.an.myphotodemo.util.KeyUtil;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    private RecyclerView recyclerView;
    NinePhotoAdapter ninePhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        ninePhotoAdapter = new NinePhotoAdapter(this, new ArrayList<String>());
        ninePhotoAdapter.setOnItemClcik(new BaseRecyclerAdp.ItemClick<String>() {
            @Override
            public void onItemClick(int position, String bean) {
                if (KeyUtil.empty.equals(bean)) {
                    choosePhoto();
                } else {
                    String[] items = ninePhotoAdapter.getData().toArray(new String[0]);
                    imageBrower(position, items);
                }
            }
        });
        recyclerView.setAdapter(ninePhotoAdapter);

    }

    /**
     * 每一张图片放大查看
     *
     * @param position
     * @param urls
     */
    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        startActivity(intent);
    }


    /**
     * 开启图片选择器
     */
    private void choosePhoto() {

//        PhotoPicker.builder()
//                .setPhotoCount(9)
//                .setGridColumnCount(4)
//                .start(MainActivity.this);


        PhotoPicker.builder()
                .setPhotoCount(9)
                .setShowCamera(true)
//                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);


//        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
//        intent.setPhotoCount(9);
//        intent.setShowCamera(true);
//        startActivityForResult(intent, REQUEST_CODE);

        //ImageLoaderUtils.display(context,imageView,path);
    }

    private static final String TAG = "MainActivity";

    /**
     * 接受返回的图片数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }


            for (int i = 0; i < photos.size(); i++) {
                Log.i(TAG, "----------onActivityResult: " + photos.get(i));
            }
            ninePhotoAdapter.notifyData(photos);


//            selectedPhotos.clear();
//
//            if (photos != null) {
//
//                selectedPhotos.addAll(photos);
//            }
//            photoAdapter.notifyDataSetChanged();
        }


//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            if (data != null) {
//                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
//
//                for (int i = 0; i < photos.size(); i++) {
//                    Log.i(TAG, "----------onActivityResult: " + photos.get(i));
//                }
//                if (recyclerView != null) {
//                    Log.i(TAG, "----------photossss: ========");
//                    recyclerView.addAll(photos);
//                    photossss = recyclerView.getData();
//                    Log.i(TAG, "----------photossss: ========" + photossss.size());
//
//                }
//            }
//        }
    }
}
