package com.zhiziyun.dmptest.bot.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.AccountActivity;
import com.zhiziyun.dmptest.bot.ui.activity.CallRecordsActivity;
import com.zhiziyun.dmptest.bot.ui.activity.MyOriginalityActivity;
import com.zhiziyun.dmptest.bot.ui.activity.QualificationActivity;
import com.zhiziyun.dmptest.bot.ui.activity.SettingActivity;
import com.zhiziyun.dmptest.bot.ui.activity.SmsListActivity;
import com.zhiziyun.dmptest.bot.ui.activity.StoreListActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.PhotoUtils;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.TakePhotoPopWin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lib.homhomlib.design.SlidingLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * 账户
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "PhotoImageFragment";
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri;
    private File fileCropUri;
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    public static AccountFragment fragment;
    private ImageView iv_head;
    private SharedPreferences share;
    public TextView tv_balance;
    private SlidingLayout slidingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        fragment = this;
        initView();
    }

    public void initView() {
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        fileUri = new File(getActivity().getCacheDir().getPath() + "/Bot/" + share.getString("userid", "") + "/photo.jpg");
        fileCropUri = new File(getActivity().getCacheDir().getPath() + "/Bot/" + share.getString("userid", "") + "/img_head.jpg");
        tv_balance = getView().findViewById(R.id.tv_balance);
        getView().findViewById(R.id.rl_account).setOnClickListener(this);
        getView().findViewById(R.id.rl_store).setOnClickListener(this);
        getView().findViewById(R.id.rl_originality).setOnClickListener(this);
        getView().findViewById(R.id.rl_sms).setOnClickListener(this);
        getView().findViewById(R.id.rl_qualification).setOnClickListener(this);
        getView().findViewById(R.id.rl_call_records).setOnClickListener(this);
        getView().findViewById(R.id.rl_phone_number).setOnClickListener(this);

        iv_head = getView().findViewById(R.id.iv_head);
        iv_head.setOnClickListener(this);
        TextView tv_companyname = getView().findViewById(R.id.tv_companyname);
        tv_companyname.setText(share.getString("company", ""));
        //果冻弹跳效果
        slidingLayout = getView().findViewById(R.id.slidingLayout);
        slidingLayout.setSlidingOffset(0.2f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head:
                try {
                    if (ClickUtils.isFastClick()) {
                        //让背景变暗
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 0.7f;
                        getActivity().getWindow().setAttributes(lp);
                        //弹出pop窗体
                        TakePhotoPopWin takePhotoPopWin = new TakePhotoPopWin(getActivity(), null, 0);
                        takePhotoPopWin.showAtLocation(getView().findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                        //监听popwin是否关闭，关闭的话让背景恢复
                        takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                try {
                                    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                                    lp.alpha = 1f;
                                    getActivity().getWindow().setAttributes(lp);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_account:
                try {
                    startActivity(new Intent(getActivity(), AccountActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_store:
                startActivity(new Intent(getActivity(), StoreListActivity.class));
                break;
            case R.id.rl_originality:
                startActivity(new Intent(getActivity(), MyOriginalityActivity.class));
                break;
            case R.id.rl_sms:
                startActivity(new Intent(getActivity(), SmsListActivity.class));
                break;
            case R.id.rl_qualification:
                startActivity(new Intent(getActivity(), QualificationActivity.class));
                break;
            case R.id.rl_call_records:
                startActivity(new Intent(getActivity(), CallRecordsActivity.class));
                break;
            case R.id.rl_phone_number:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    //相册
    public void photoAlbum() {
        autoObtainStoragePermission();
    }

    //拍照
    public void photograph() {
        autoObtainCameraPermission();
    }

    /**
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
            }
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 申请访问相机权限
     */
    private void autoObtainCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    ToastUtils.showShort(getActivity(), "您已经拒绝过一次");
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {//有权限直接调用系统相机拍照
                if (hasSdcard()) {
                    imageUri = Uri.fromFile(fileUri);
                    //通过FileProvider创建一个content类型的Uri
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(getActivity(), "com.zhiziyun.dmptest.bot", fileUri);
                    }
                    PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                }
            }
        } else {//android5.0以下
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(getActivity(), "com.zhiziyun.dmptest.bot", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(getActivity(), "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                try {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (hasSdcard()) {
                            imageUri = Uri.fromFile(fileUri);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                //通过FileProvider创建一个content类型的Uri
                                imageUri = FileProvider.getUriForFile(getActivity(), "com.zhiziyun.dmptest.bot", fileUri);
                            }
                            PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                        } else {
                            ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                        }
                    } else {
                        ToastUtils.showShort(getActivity(), "请允许打开相机！！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "请允许操作SDCard！！");
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + "  resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "onActivityResult: resultCode!=RESULT_OK");
            return;
        }
        switch (requestCode) {
            //相机返回
            case CODE_CAMERA_REQUEST:
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                break;
            //相册返回
            case CODE_GALLERY_REQUEST:
                if (hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(getActivity(), data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(getActivity(), "com.zhiziyun.dmptest.bot", new File(newUri.getPath()));
                    }
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                }
                break;
            //裁剪返回
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getActivity());
                if (bitmap != null) {
                    showImages(ratio(bitmap, 240f, 120f));
                }
                break;
            default:
        }
    }

    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 3072) {//判断如果图片大于3M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    private void showImages(Bitmap bitmap) {
        iv_head.setImageBitmap(createCircleImage(bitmap));
        upImage(bitmapToBase64(bitmap));
    }

    public void upImage(final String str) {
        //上传图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("logo", str);
                    json.put("logoSuffix", ".jpg");
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
                            .url(BaseUrl.BaseZhang + "advertiserApp/editLogoImg")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {

                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (TextUtils.isEmpty(jsonObject.get("response").toString())) {//为null表示上传失败
                                    handler.sendEmptyMessage(5);
                                } else {
                                    handler.sendEmptyMessage(4);
                                }
                            } catch (JSONException e) {
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

    /**
     * 将图片剪裁为圆形
     */
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    //结算账户消费详情接口
    public void ConsumptionDetails() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("accountid", share.getString("accountid", ""));
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "advertiserApp/account")
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
                                JSONObject json = new JSONObject(jsonObject.get("response").toString());
                                Message message = new Message();
                                message.what = 1;
                                message.obj = json.get("balance").toString();
                                handler.sendMessage(message);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    tv_balance.setText("余额：" + msg.obj.toString());
                    break;
                case 4:
                    ToastUtils.showShort(getActivity(), "头像上传成功");
                    break;
                case 5:
                    ToastUtils.showShort(getActivity(), "头像上传失败");
                    break;
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (ClickUtils.isFastClick()) {//防止过快点击而闪退
                //每次滑动当前碎片就刷新头像路径
                getPictrue();//获取头像
                ConsumptionDetails();//结算账户消费详情接口
            }
        }
    }

    //获得头像
    public void getPictrue() {
        try {
            final String filepath = "/sdcard/Bot/" + share.getString("userid", "") + "/img_head.jpg";
            File file = new File(filepath);
            if (hasSdcard()) {
                if (file.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(filepath);
                    //将图片显示到ImageView中
                    iv_head.setImageBitmap(createCircleImage(bm));
                }
            } else {
                Toast.makeText(getActivity(), "没有SD卡", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
