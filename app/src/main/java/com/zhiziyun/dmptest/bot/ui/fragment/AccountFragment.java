package com.zhiziyun.dmptest.bot.ui.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.StoreListActivity;
import com.zhiziyun.dmptest.bot.ui.activity.TransactionDetailsActivity;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.TakePhotoPopWin;

import org.json.JSONObject;

import java.io.File;
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
 * Created by Administrator on 2017/7/17 0017.
 * 账户
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    public static AccountFragment fragment;
    private ImageView iv_head;
    public final int PIC_FROM_CAMERA = 1;
    public final int PIC_FROM＿LOCALPHOTO = 0;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_FROM_AIBUM = 2;
    private Uri imageUri;
    private SharedPreferences share;
    private TextView tv_balance;

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
        tv_balance = getView().findViewById(R.id.tv_balance);
        ConsumptionDetails();
        getView().findViewById(R.id.rl_account).setOnClickListener(this);
        getView().findViewById(R.id.rl_store).setOnClickListener(this);
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        iv_head = getView().findViewById(R.id.iv_head);
        iv_head.setOnClickListener(this);
        TextView tv_companyname = getView().findViewById(R.id.tv_companyname);
        tv_companyname.setText(share.getString("company", ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head:
                TakePhotoPopWin takePhotoPopWin = new TakePhotoPopWin(getActivity(), onClickListener, 0);
                takePhotoPopWin.showAtLocation(getView().findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                break;
            case R.id.rl_account:
                startActivity(new Intent(getActivity(), TransactionDetailsActivity.class));
                break;
            case R.id.rl_store:
                startActivity(new Intent(getActivity(), StoreListActivity.class));
                break;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.tv_huowei:
//                    System.out.println("fewafwaefwea--------------------------------------");
//                    break;
            }
        }
    };

    //相册
    public void photoAlbum() {
        doHandlerPhoto(PIC_FROM＿LOCALPHOTO);
    }

    //拍照
    public void photograph() {
        doHandlerPhoto(PIC_FROM_CAMERA);
    }

    private void doHandlerPhoto(int type) {
        if (type == PIC_FROM＿LOCALPHOTO) {//相册
            /**
             * 因为相片是存在SD卡上的，所以我们需要SD卡的读写权限，因为读写权限在7.0属于高危权限了，所以在动态运行时去判断是否有这个权限，有则继续运行，没有则调用动态注册
             */
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                openAlbum();
            }
        } else if (type == PIC_FROM_CAMERA) {//拍照
            openCamara();
        } else {
            show("未知错误");
        }
    }

    public void show(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    //打开相册
    private void openAlbum() {
        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_FROM_AIBUM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    show("You denied the permission");
                }
                break;
        }
    }

    /**
     * 应用关联缓存目录：就是指SD卡中专门用于存放当前应用缓存数据的位置，调用getExternalCacheDir()可以得到这个目录。具体路径是：/sdcard/Android/data/<package name>/cache
     * 那么为什么要使用应用关联缓存目录来存放图片呢？应为从android6.0开始读写SD卡被列为了危险权限，如果将图片放在SD卡的其他地方，都要进行运行时权限处理才行，而使用应用缓存数据的位置则可以跳过这一步
     * 从android7.0开始，直接使用本地真实路径的URI被认为是不安全的，会抛出一个FileUriExposedException异常，所以在低于7.0的时候调用URI的fromFile()方法将File对象转换成URI对象，这个URI对象标志着
     * 图片地址的真实路径。
     * 在高于7.0的时候调用FileProvider的getUriForFile（）方法将file对象转换成一个封装过的uri对象。FileProvider是一个特殊的内容提供器，它使用了和内容提供器类似的机制来保护数据，可以选择性的将封装过的URI共享外部，从而提高应用的安全性
     * getUriForFile(context,Stirng,file)第一个是context对象，第二个是任意字符串，第三个是file对象
     */
    //打开相机
    private void openCamara() {
        //创建file对象，用于存储拍照后的图片
        File outputImg = new File(getActivity().getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImg.exists()) {
                outputImg.delete();
            }
            outputImg.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            //这里报空指针
            imageUri = FileProvider.getUriForFile(getActivity(), "com.example.cameraalbumtest.fileprovider", outputImg);
        } else {
            imageUri = Uri.fromFile(outputImg);
        }
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    //将照片显示出来
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        iv_head.setImageBitmap(createCircleImage(bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            /**
             * 因为从android4.4开始，选取相册中的图片就不再返回图片的真实URI了，而是返回一个封装过的URI，如果是4.4以上的就必须先对这个URI进行解析才行
             */
            case CHOOSE_FROM_AIBUM:
                if (resultCode == getActivity().RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上的系统使用这个方法处理照片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下的使用这个方法处理
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    /**
     * 那么关于4.4以上的系统，对于返回的URI怎么进行解析呢？
     * 一般就是集中判断
     * 返回的URI是不是document类型，如果是那么就取出来document id 进行处理，如果不是就进行普通的URI处理
     * 如果URI的authority是media格式的话，document id 还需要再进行一次解析，通过字符串的分割取出真正的id，取出来的id用于构建新的URI和条件语句
     *
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri则使用普通的方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的URI则直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }


    /**
     * 通过传过来的条件，获取图片的真实路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过URI和selection来获取真是的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 在这里我使用的是GLIDE来显示图片，你可以选择使用其他方法
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //将照片显示出来
            Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
            iv_head.setImageBitmap(createCircleImage(imageBitmap));
        } else {
            show("failed to get image!");
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
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
                            .url("http://test.zhiziyun.com/api-service/agentApp/account")
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
            }
        }
    };
}
