package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.PhotoUtils2;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.io.File;

/**
 * Created by Administrator on 2018/7/11.
 */

public class UploadMaterialActivity extends BaseActivity implements View.OnClickListener {
    private PhotoView image;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + BaseUrl.BaseAPPName + "/t.jpg");
    private Uri cropImageUri;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_material);
        initView();
    }

    private void initView() {
        image = (PhotoView) findViewById(R.id.image);
        image.setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    /**
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                PhotoUtils2.openPic(this, CODE_GALLERY_REQUEST);
            }
        } else {
            PhotoUtils2.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils2.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.showShort(this, "请允许操作SDCard！！");
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //相册返回
            case CODE_GALLERY_REQUEST:
                try {
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils2.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, getPackageName(), new File(newUri.getPath()));
                        }
                        bitmap = PhotoUtils2.getBitmapFromUri(newUri, this);
                        if (bitmap != null) {
                            //显示图片
                            image.setImageBitmap(bitmap);
                        }
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.image:
                autoObtainStoragePermission();
                break;
        }
    }
}
