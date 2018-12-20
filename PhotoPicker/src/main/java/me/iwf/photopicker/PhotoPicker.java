package me.iwf.photopicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.utils.CameraKeys;
import me.iwf.photopicker.utils.PermissionsUtils;

/**
 * Created by Donglua on 16/6/25.
 * Builder class to ease Intent setup.
 */
public class PhotoPicker {
    public static PhotoPickerBuilder builder() {
        return new PhotoPickerBuilder();
    }

    public static class PhotoPickerBuilder {
        private Bundle mPickerOptionsBundle;
        private Intent mPickerIntent;

        public PhotoPickerBuilder() {
            mPickerOptionsBundle = new Bundle();
            mPickerIntent = new Intent();
        }

        /**
         * Send the Intent from an Activity with a custom request code
         *
         * @param activity    Activity to receive result
         * @param requestCode requestCode for result
         */
        public void start(@NonNull Activity activity, int requestCode) {
            if (PermissionsUtils.checkReadStoragePermission(activity)) {
                activity.startActivityForResult(getIntent(activity), requestCode);
            }
        }

        /**
         * @param fragment    Fragment to receive result
         * @param requestCode requestCode for result
         */
        public void start(@NonNull Context context,
                          @NonNull android.support.v4.app.Fragment fragment, int requestCode) {
            if (PermissionsUtils.checkReadStoragePermission(fragment.getActivity())) {
                fragment.startActivityForResult(getIntent(context), requestCode);
            }
        }

        /**
         * Send the Intent with a custom request code
         *
         * @param fragment Fragment to receive result
         */
        public void start(@NonNull Context context,
                          @NonNull android.support.v4.app.Fragment fragment) {
            if (PermissionsUtils.checkReadStoragePermission(fragment.getActivity())) {
                fragment.startActivityForResult(getIntent(context), CameraKeys.REQUEST_CODE);
            }
        }

        /**
         * Get Intent to start {@link PhotoPickerActivity}
         *
         * @return Intent for {@link PhotoPickerActivity}
         */
        public Intent getIntent(@NonNull Context context) {
            mPickerIntent.setClass(context, PhotoPickerActivity.class);
            mPickerIntent.putExtras(mPickerOptionsBundle);
            return mPickerIntent;
        }

        /**
         * Send the crop Intent from an Activity
         *
         * @param activity Activity to receive result
         */
        public void start(@NonNull Activity activity) {
            start(activity, CameraKeys.REQUEST_CODE);
        }

        public PhotoPickerBuilder setPhotoCount(int photoCount) {
            mPickerOptionsBundle.putInt(CameraKeys.EXTRA_MAX_COUNT, photoCount);
            return this;
        }

        public PhotoPickerBuilder setGridColumnCount(int columnCount) {
            mPickerOptionsBundle.putInt(CameraKeys.EXTRA_GRID_COLUMN, columnCount);
            return this;
        }

        public PhotoPickerBuilder setShowGif(boolean showGif) {
            mPickerOptionsBundle.putBoolean(CameraKeys.EXTRA_SHOW_GIF, showGif);
            return this;
        }

        public PhotoPickerBuilder setShowCamera(boolean showCamera) {
            mPickerOptionsBundle.putBoolean(CameraKeys.EXTRA_SHOW_CAMERA, showCamera);
            return this;
        }

        public PhotoPickerBuilder setSelected(List<String> imagesUri) {
            mPickerOptionsBundle.putStringArrayList(CameraKeys.EXTRA_ORIGINAL_PHOTOS, (ArrayList<String>) imagesUri);
            return this;
        }

        public PhotoPickerBuilder setPreviewEnabled(boolean previewEnabled) {
            mPickerOptionsBundle.putBoolean(CameraKeys.EXTRA_PREVIEW_ENABLED, previewEnabled);
            return this;
        }
    }
}
