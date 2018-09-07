package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.zhiziyun.dmptest.bot.BuildConfig;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.originality.AdvertCreatCase;
import com.zhiziyun.dmptest.bot.mode.originality.ChooseCretiveCase;
import com.zhiziyun.dmptest.bot.mode.originality.EditCreativeCase;
import com.zhiziyun.dmptest.bot.mode.originality.GetCreateDetailCase;
import com.zhiziyun.dmptest.bot.mode.originality.Uploadmaterialcase;
import com.zhiziyun.dmptest.bot.mode.originality.mateialdetail.BMaterialResult;
import com.zhiziyun.dmptest.bot.mode.originality.mateialdetail.BianjiResult;
import com.zhiziyun.dmptest.bot.mode.originality.mateialdetail.MaterialJsonBean;
import com.zhiziyun.dmptest.bot.mode.originality.origincreat.BOriginalityResult;
import com.zhiziyun.dmptest.bot.mode.originality.origincreat.CreativeTemplatePackages;
import com.zhiziyun.dmptest.bot.mode.originality.uploadmaterial.BCreateDetailResult;
import com.zhiziyun.dmptest.bot.network.subscriber.PureSubscriber;
import com.zhiziyun.dmptest.bot.util.DateUtil;
import com.zhiziyun.dmptest.bot.util.GlideImageLoader;
import com.zhiziyun.dmptest.bot.util.IsEmpty;
import com.zhiziyun.dmptest.bot.util.MyActivityManager;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.StorageMgr;
import com.zhiziyun.dmptest.bot.util.StringUtil;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/7/11.
 * 选择创意
 */

public class ChooseCreativeActivity extends BaseActivity implements View.OnClickListener {
    private Banner mBanner;
    private List<String> mList;
    private TextView mMouldTv;
    private CreativeTemplatePackages mCreativeTemplatePackages;
    private BianjiResult.ResponseBean.TemplatePackageSaveInfoBean TemplatePackageSaveInfoBean;
    private SharedPreferences share;
    private EditText mMaterialUrl;
    private String mJsonBean, mActivityId;
    private JSONArray mJsonArray, mCreativeIds;
    private List<String> mImagePreview;
    //    上传返回的素材
    private String mMaterialToatal;
    private MyDialog mDialog;
    private TextView mTvUpload;
    private BianjiResult.ResponseBean mResponseBean;
    private String mTargetUrl;
    private String mTemplatePackageId;
    private List<String> mTagIdsList;
    // 上个界面传回来的json数据
    private String mMJS;
    private String mActivityType, activityType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_creative);
        MyActivityManager.add(ChooseCreativeActivity.this);
//        获取上传后的创意模板包
        initView();
    }

    private void initView() {
        //设置系统栏颜色
//        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_system.getLayoutParams();
//        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mJsonArray = new JSONArray();
        mCreativeIds = new JSONArray();
        mTagIdsList = new ArrayList<>();
// json数据人群数据和activityId
        mJsonBean = getIntent().getStringExtra("advert");
        mTagIdsList = getIntent().getStringArrayListExtra("Total");
        mActivityId = getIntent().getStringExtra("creativeId");

        for (int i = 0; i < mTagIdsList.size(); i++) {
            mJsonArray.put(mTagIdsList.get(i));
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mJsonBean);
            mActivityType = (String) jsonObject.get("activityType");
            jsonObject.put("tagIds", mJsonArray);

            mMJS = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTvUpload = findViewById(R.id.tv_upload);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_template).setOnClickListener(this);
        findViewById(R.id.rl_material).setOnClickListener(this);
        findViewById(R.id.rl_promote).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        mMaterialUrl = findViewById(R.id.material_url);
        mMaterialUrl.setOnClickListener(this);
        mMouldTv = findViewById(R.id.mould_tv);
        moduleType();
        mList = new ArrayList<>();
        if (!IsEmpty.string(mActivityId)) {
            getCreativeActivityDetail();
        } else {
            getData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_template:
                break;
            case R.id.rl_material:
//                上传图片需要templatePackageId
                Intent intent = new Intent(this, UploadMaterialActivity.class);
                intent.putExtra("templatePackageId", mTemplatePackageId);
                startActivityForResult(intent, 11);
                break;
            case R.id.rl_promote:
//                startActivity(new Intent(this, SpreadActivity.class));
                break;
            case R.id.btn_commit:
                try {
//活动名为空走创建接口不为空走编辑接口
                    if (IsEmpty.string(mActivityId)) {
                        if (!IsEmpty.string(mMaterialToatal) && !IsEmpty.string(mMaterialUrl.getText().toString())) {
                            JSONObject jsonObject = new JSONObject(mMaterialToatal);
                            JSONObject json = new JSONObject(jsonObject.get("response").toString());
                            creatAdvert((JSONObject) json.get("templateCreativeInfo"));
                        } else {
                            Toast.makeText(this, "网址和素材不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
//             mCreativeIds为空需上传素材
                        if (mCreativeIds.length() != 0) {
                            if (!IsEmpty.string(mMaterialToatal) && !IsEmpty.string(mMaterialUrl.getText().toString())) {
                                JSONObject jsonObject = new JSONObject(mMaterialToatal);
                                JSONObject json = new JSONObject(jsonObject.get("response").toString());
                                creatAdvert((JSONObject) json.get("templateCreativeInfo"));
                            } else {
                                Toast.makeText(this, "素材和网址不能为空", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (!IsEmpty.string(mMaterialToatal)) {
                                JSONObject jsonObject = new JSONObject(mMaterialToatal);
                                JSONObject json = new JSONObject(jsonObject.get("response").toString());
                                creatAdvert((JSONObject) json.get("templateCreativeInfo"));
                            } else {
                                JSONObject jsonObject = new JSONObject(mMJS);
//                            jsonObject.put("templatePackageId", mTemplatePackageId);
                                jsonObject.put("targetUrl", mMaterialUrl.getText().toString());
                                if (StringUtil.pattern().matcher(mMaterialUrl.getText().toString()).matches()) {
                                    editCreativeCase(jsonObject.toString().replaceAll("\\\\", ""));
                                } else {
                                    Toast.makeText(this, "网址不正确", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    //获取数据
    public void getData() {
        JSONObject js = new JSONObject();
        try {
            js.put("activityType", mActivityType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            new ChooseCretiveCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), js.toString()).execute(new PureSubscriber<BOriginalityResult>() {

                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(BOriginalityResult data) {
                    if (IsEmpty.string(mMaterialToatal)) {
                        mTemplatePackageId = data.getResponse().getCreativeTemplatePackages().get(0).getTemplatePackageId();
                        mCreativeTemplatePackages = data.getResponse().getCreativeTemplatePackages().get(0);
                        for (int i = 0; i < mCreativeTemplatePackages.getTemplatePackagePreviewImages().size(); i++) {
                            mList.add(mCreativeTemplatePackages.getTemplatePackagePreviewImages().get(i));
                        }
                        mMouldTv.setText(mCreativeTemplatePackages.getTemplatePackageName());
                        bananer(mList);
                    } else {
                        getList();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取广告详情
    public void getCreativeActivityDetail() {
        try {
//            createId为空获取走getData方法获取首个创意模板包
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("siteId", share.getString("siteid", ""));
            jsonObject.put("activityId", mActivityId);
            new GetCreateDetailCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), jsonObject.toString()).execute(new PureSubscriber<String>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(String data) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(data);
                        JSONObject json = new JSONObject(jsonObject1.get("response").toString());
                        mActivityType = json.getString("activityType");
//                        mCreativeIds = json.getJSONArray("creativeIds");
                        JSONObject templatePackageSaveInfo = new JSONObject(json.get("templatePackageSaveInfo").toString());
                        mTargetUrl = templatePackageSaveInfo.getString("targetUrl");
                        mTemplatePackageId = templatePackageSaveInfo.getString("templatePackageId");
                        String templatePackagePreviewImages = templatePackageSaveInfo.get("templatePackagePreviewImages").toString().replaceAll("\\\\", "");
                        String mm = templatePackagePreviewImages.replaceAll("\"", "");
                        String url[] = mm.substring(1, mm.length() - 1).split(",");
                        for (int i = 0; i < url.length; i++) {
                            mList.add(url[i]);
                        }
                        if (IsEmpty.string(templatePackageSaveInfo.toString())) {
                            getData();
                        } else {
                            mTvUpload.setText("已上传");
                            mMaterialUrl.setText(mTargetUrl);
                            moduleType();
                            bananer(mList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        JSONObject jsonObject1 = null;
                        try {
                            jsonObject1 = new JSONObject(data);
                            JSONObject json = new JSONObject(jsonObject1.get("response").toString());
                            mActivityType = json.getString("activityType");
                            mCreativeIds = json.getJSONArray("creativeIds");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        getData();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化json对象上传
    public void creatAdvert(JSONObject mBeanJson) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mMJS);
            if (IsEmpty.string(mActivityId)) {
                jsonObject.put("templateCreativeInfo", mBeanJson);
                jsonObject.put("templatePackageId", mTemplatePackageId);
                jsonObject.put("targetUrl", mMaterialUrl.getText().toString());
                if (StringUtil.pattern().matcher(mMaterialUrl.getText().toString()).matches()) {
                    upLoad(jsonObject.toString().replaceAll("\\\\", ""));
                } else {
                    Toast.makeText(this, "网址不正确", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!IsEmpty.string(mMaterialUrl.getText().toString())) {
                    jsonObject.put("templateCreativeInfo", mBeanJson);
                    jsonObject.put("templatePackageId", mTemplatePackageId);
                    jsonObject.put("targetUrl", mMaterialUrl.getText().toString());
                    Log.e("creatAdvert123141: ", jsonObject.toString());
                    if (StringUtil.pattern().matcher(mMaterialUrl.getText().toString()).matches()) {
                        editCreativeCase(jsonObject.toString().replaceAll("\\\\", ""));
                    } else {
                        Toast.makeText(this, "网址不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "网址不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //创建上传
    public void upLoad(String json) {
        mDialog = MyDialog.showDialog(this);
        mDialog.show();
        try {
            new AdvertCreatCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), json).execute(new PureSubscriber<BCreateDetailResult>() {
                @Override
                public void onFailure(Throwable throwable) {
                    mDialog.dismiss();
                    Toast.makeText(ChooseCreativeActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(BCreateDetailResult data) {
                    if (data.getErrorcode().equals("B50E002")) {
                        mDialog.dismiss();
                        Toast.makeText(ChooseCreativeActivity.this, "该活动名已创建", Toast.LENGTH_SHORT).show();
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(ChooseCreativeActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        MyActivityManager.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //编辑上传
    public void editCreativeCase(String json) {
        mDialog = MyDialog.showDialog(this);
        mDialog.show();
        try {
            new EditCreativeCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), json).execute(new PureSubscriber() {
                @Override
                public void onFailure(Throwable throwable) {
                    mDialog.dismiss();
                    Toast.makeText(ChooseCreativeActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Object data) {
                    mDialog.dismiss();
                    Toast.makeText(ChooseCreativeActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    MyActivityManager.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 12:
                mMaterialToatal = data.getStringExtra("upload");
                mTvUpload.setText("已上传");
                getList();
                break;
        }
    }

    //处理json数据实现轮播效果
    public void getList() {
        mImagePreview = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(mMaterialToatal);
            JSONObject json = new JSONObject(jsonObject.get("response").toString());
            String ss = json.get("templatePackagePreviewImages").toString().replaceAll("\\\\", "");
            String mm = ss.replaceAll("\"", "");
            String url[] = mm.substring(1, mm.length() - 1).split(",");
            for (int i = 0; i < url.length; i++) {
                mImagePreview.add(url[i]);
            }
            bananer(mImagePreview);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化轮播图
    public void bananer(List<String> mm) {
        mBanner = (Banner) findViewById(R.id.banner);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mm);
        mBanner.isAutoPlay(false);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    //销毁Banner
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBanner.stopAutoPlay();
    }

    public void moduleType() {
        if (mActivityType.equals("1")) {
            mMouldTv.setText("移动静态");
        } else if (mActivityType.equals("4")) {
            mMouldTv.setText("移动信息流");
        } else if (mActivityType.equals("7")) {
            mMouldTv.setText("移动视频");
        }
    }
}
