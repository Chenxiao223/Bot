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
import android.widget.LinearLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.PhotoUtils2;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.io.File;

/**
 * Created by Administrator on 2018/7/11.
 * 上传素材
 */

public class UploadMaterialActivity extends BaseActivity implements View.OnClickListener {
    private PhotoView image;
    private ImageView image_logo;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + BaseUrl.BaseAPPName + "/t.jpg");
    private Uri cropImageUri;
    private Bitmap bitmap = null;
    private int flag_iv = 0;//区分改变哪张图
    private static final int OUTPUT_X = 300;
    private static final int OUTPUT_Y = 300;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_material);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        image = (PhotoView) findViewById(R.id.image);
        image.setOnClickListener(this);
        image_logo = findViewById(R.id.image_logo);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.line_logo).setOnClickListener(this);
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
                            if (flag_iv == 1) {
                                image.setImageBitmap(bitmap);
                            } else {
                                PhotoUtils2.cropImageUri(UploadMaterialActivity.this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                            }
                        }
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //裁剪返回
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils2.getBitmapFromUri(cropImageUri, this);
                if (bitmap != null) {
                    image_logo.setImageBitmap(bitmap);
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
            case R.id.line_logo:
                flag_iv = 2;
                autoObtainStoragePermission();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.image:
                flag_iv = 1;
                autoObtainStoragePermission();
                break;
        }
    }
}
