package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.AdvertisingSize;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.PhotoUtils2;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/8.
 * 酒店住宿
 */

public class HotelActivity extends BaseActivity implements View.OnClickListener {
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/Bot/t.jpg");
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;
    private int flag = 0;
    private int flag_iv = 0;//区分改变哪张图
    private Uri cropImageUri;
    private Handler mHandler = new Handler();
    private String SIZE;//模板尺寸

    private RelativeLayout layout_1;
    private LinearLayout line1;
    private ImageView iv_picture;
    private EditText et_title1, et_text1, edit_url1;
    private Button btn_left1;
    private MyDialog dialog;
    private SharedPreferences share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        dialog = MyDialog.showDialog(this);
        layout_1 = (RelativeLayout) findViewById(R.id.layout_1);
        iv_picture = (ImageView) findViewById(R.id.iv_picture);
        et_title1 = (EditText) findViewById(R.id.et_title1);
        et_text1 = (EditText) findViewById(R.id.et_text1);
        edit_url1 = (EditText) findViewById(R.id.edit_url1);
        btn_left1 = (Button) findViewById(R.id.btn_left1);
        line1 = (LinearLayout) findViewById(R.id.line1);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.linearLayout).setOnClickListener(this);

        //设置布局可以截图
        layout_1 = (RelativeLayout) findViewById(R.id.layout_1);
        layout_1.setDrawingCacheEnabled(true);
        layout_1.buildDrawingCache();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        flag = getIntent().getIntExtra("flag", 0);
        SIZE = getIntent().getStringExtra("size");
        if (flag == 1) {//那边点击了那个模板，这边就显示这个模板
            line1.setVisibility(View.VISIBLE);
            //设置布局的长高
            LinearLayout.LayoutParams paramss = (LinearLayout.LayoutParams) layout_1.getLayoutParams();
            paramss.height = dm.widthPixels * 10 / 64;//设置布局的高度
            layout_1.setLayoutParams(paramss);//将设置好的布局参数应用到控件中

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_left1:
                flag_iv = 1;//根据这个值来区分
                autoObtainStoragePermission();
                break;
            case R.id.linearLayout:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                break;
            case R.id.btn_commit:
                if (ClickUtils.isFastClick()) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et_title1.setFocusable(false);//为了避免光标也在截图中，点击提交后去掉光标
                            et_text1.setFocusable(false);
                            if (flag == 1) {//区分模板
                                if (TextUtils.isEmpty(edit_url1.getText().toString())) {
                                    ToastUtils.showShort(HotelActivity.this, "请将URL填写完整");
                                } else {
                                    //加载动画
                                    dialog.show();
                                    final Bitmap bitmap = layout_1.getDrawingCache(); // 获取图片
                                    Bitmap bmp = zoomImg(bitmap, 640, 100);
                                    advertisingCreate(bitmapToBase64(bmp));//上传图片
                                    savePicture(bmp, "layout.jpg");// 保存图片
                                    layout_1.destroyDrawingCache(); // 保存过后释放资源
                                }
                            }
                        }
                    }, 1000);
                }
                break;
        }
    }

    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public void savePicture(Bitmap bm, String fileName) {
        if (bm == null) {
            Toast.makeText(this, "保存图片为空", Toast.LENGTH_SHORT).show();
            return;
        }
        File foder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bot");
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(foder, fileName);
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "保存成功!", Toast.LENGTH_SHORT).show();
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
                if (hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils2.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(this, "com.zhiziyun.dmptest.bot", new File(newUri.getPath()));
                    }
                    PhotoUtils2.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                } else {
                    ToastUtils.showShort(this, "设备没有SD卡！");
                }
                break;
            //裁剪返回
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils2.getBitmapFromUri(cropImageUri, this);
                if (bitmap != null) {
                    showImages(bitmap);
                }
                break;
            default:
        }
    }

    private void showImages(Bitmap bitmap) {
        switch (flag_iv) {
            case 1:
                iv_picture.setImageBitmap(bitmap);
                break;
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtils.showShort(HotelActivity.this, "图片上传成功");
                    dialog.dismiss();
                    finish();
                    break;
                case 2:
                    ToastUtils.showShort(HotelActivity.this, "图片上传失败");
                    dialog.dismiss();
                    break;
            }
        }
    };

    public void advertisingCreate(final String str) {
        //广告创建接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("creativeImage", str);//广告图片内容
                    json.put("creativeImageSuffix", ".jpg");//广告图片后缀
                    if (flag == 1) {//根据选择的模板来选择改模板的链接
                        json.put("targetUrl", edit_url1.getText().toString());//点击链接
                    }
                    String str = AddOriginalityActivity.addOriginalityActivity.type;
                    //判断是什么类型，根据类型填参数
                    if (str.equals("静态广告")) {
                        json.put("creativeType", "1");//广告类型
                        json.put("sizeId", AdvertisingSize.chooseSize(SIZE));//尺寸编号
                    } else if (str.equals("信息流")) {
                        json.put("creativeType", "4");//广告类型
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("templateId", "bVDr105NSk8");//模板编号根据尺寸固定死
                        json.put("templateAttributes", jsonObject);//信息流属性
                    }
                    String data = json.toString().replace("\\", "");
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + URLEncoder.encode(data, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "creativeApp/createCreative")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.get("status").toString().equals("true")) {//为null表示上传失败
                                    handler.sendEmptyMessage(1);
                                } else {
                                    handler.sendEmptyMessage(2);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //将bitmap转成Base64字符串
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        //Base64算法加密，当字符串过长（一般超过76）时会自动在中间加一个换行符，字符串最后也会加一个换行符。
        // 导致和其他模块对接时结果不一致。所以不能用默认Base64.DEFAULT，而是Base64.NO_WRAP不换行
        return new String(Base64.encode(bytes, Base64.NO_WRAP));
    }
}
