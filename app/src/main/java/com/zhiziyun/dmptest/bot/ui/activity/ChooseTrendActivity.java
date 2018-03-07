package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lib.homhomlib.design.SlidingLayout;

/**
 * Created by Administrator on 2018/1/11.
 * 投放时段页面
 */

public class ChooseTrendActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_all, btn_working_days, btn_weekend, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_21, btn_22, btn_23, btn_24, btn_25, btn_26, btn_27, btn_31, btn_32, btn_33, btn_34, btn_35, btn_36, btn_37, btn_41, btn_42, btn_43, btn_44, btn_45, btn_46, btn_47, btn_51, btn_52, btn_53, btn_54, btn_55, btn_56, btn_57, btn_61, btn_62, btn_63, btn_64, btn_65, btn_66, btn_67, btn_71, btn_72, btn_73, btn_74, btn_75, btn_76, btn_77, btn_81, btn_82, btn_83, btn_84, btn_85, btn_86, btn_87, btn_91, btn_92, btn_93, btn_94, btn_95, btn_96, btn_97, btn_101, btn_102, btn_103, btn_104, btn_105, btn_106, btn_107, btn_111, btn_112, btn_113, btn_114, btn_115, btn_116, btn_117, btn_121, btn_122, btn_123, btn_124, btn_125, btn_126, btn_127, btn_131, btn_132, btn_133, btn_134, btn_135, btn_136, btn_137, btn_141, btn_142, btn_143, btn_144, btn_145, btn_146, btn_147, btn_151, btn_152, btn_153, btn_154, btn_155, btn_156, btn_157, btn_161, btn_162, btn_163, btn_164, btn_165, btn_166, btn_167, btn_171, btn_172, btn_173, btn_174, btn_175, btn_176, btn_177, btn_181, btn_182, btn_183, btn_184, btn_185, btn_186, btn_187, btn_191, btn_192, btn_193, btn_194, btn_195, btn_196, btn_197, btn_201, btn_202, btn_203, btn_204, btn_205, btn_206, btn_207, btn_211, btn_212, btn_213, btn_214, btn_215, btn_216, btn_217, btn_221, btn_222, btn_223, btn_224, btn_225, btn_226, btn_227, btn_231, btn_232, btn_233, btn_234, btn_235, btn_236, btn_237, btn_241, btn_242, btn_243, btn_244, btn_245, btn_246, btn_247;
    private Boolean bool_11 = false, bool_12 = false, bool_13 = false, bool_14 = false, bool_15 = false, bool_16 = false, bool_17 = false, bool_21 = false, bool_22 = false, bool_23 = false, bool_24 = false, bool_25 = false, bool_26 = false, bool_27 = false, bool_31 = false, bool_32 = false, bool_33 = false, bool_34 = false, bool_35 = false, bool_36 = false, bool_37 = false, bool_41 = false, bool_42 = false, bool_43 = false, bool_44 = false, bool_45 = false, bool_46 = false, bool_47 = false, bool_51 = false, bool_52 = false, bool_53 = false, bool_54 = false, bool_55 = false, bool_56 = false, bool_57 = false, bool_61 = false, bool_62 = false, bool_63 = false, bool_64 = false, bool_65 = false, bool_66 = false, bool_67 = false, bool_71 = false, bool_72 = false, bool_73 = false, bool_74 = false, bool_75 = false, bool_76 = false, bool_77 = false, bool_81 = false, bool_82 = false, bool_83 = false, bool_84 = false, bool_85 = false, bool_86 = false, bool_87 = false, bool_91 = false, bool_92 = false, bool_93 = false, bool_94 = false, bool_95 = false, bool_96 = false, bool_97 = false, bool_101 = false, bool_102 = false, bool_103 = false, bool_104 = false, bool_105 = false, bool_106 = false, bool_107 = false, bool_111 = false, bool_112 = false, bool_113 = false, bool_114 = false, bool_115 = false, bool_116 = false, bool_117 = false, bool_121 = false, bool_122 = false, bool_123 = false, bool_124 = false, bool_125 = false, bool_126 = false, bool_127 = false, bool_131 = false, bool_132 = false, bool_133 = false, bool_134 = false, bool_135 = false, bool_136 = false, bool_137 = false, bool_141 = false, bool_142 = false, bool_143 = false, bool_144 = false, bool_145 = false, bool_146 = false, bool_147 = false, bool_151 = false, bool_152 = false, bool_153 = false, bool_154 = false, bool_155 = false, bool_156 = false, bool_157 = false, bool_161 = false, bool_162 = false, bool_163 = false, bool_164 = false, bool_165 = false, bool_166 = false, bool_167 = false, bool_171 = false, bool_172 = false, bool_173 = false, bool_174 = false, bool_175 = false, bool_176 = false, bool_177 = false, bool_181 = false, bool_182 = false, bool_183 = false, bool_184 = false, bool_185 = false, bool_186 = false, bool_187 = false, bool_191 = false, bool_192 = false, bool_193 = false, bool_194 = false, bool_195 = false, bool_196 = false, bool_197 = false, bool_201 = false, bool_202 = false, bool_203 = false, bool_204 = false, bool_205 = false, bool_206 = false, bool_207 = false, bool_211 = false, bool_212 = false, bool_213 = false, bool_214 = false, bool_215 = false, bool_216 = false, bool_217 = false, bool_221 = false, bool_222 = false, bool_223 = false, bool_224 = false, bool_225 = false, bool_226 = false, bool_227 = false, bool_231 = false, bool_232 = false, bool_233 = false, bool_234 = false, bool_235 = false, bool_236 = false, bool_237 = false, bool_241 = false, bool_242 = false, bool_243 = false, bool_244 = false, bool_245 = false, bool_246 = false, bool_247 = false;
    private TextView tv_9_12, tv_13_16, tv_17_20, tv_21_00, tv_01_04, tv_05_08;
    private SlidingLayout slidingLayout;
    private final int FLAG = 1280;
    private JSONObject jsonObject;
    private HorizontalScrollView mHorizontalScrollView;
    private int screenWidth, screenHeight;
    private String flag_trend = "全部";
    private String flag_time = "未知";
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_trend);
        initView();
        if (getIntent().getIntExtra("flag", 0) != 0) {//如果传进来有值
            try {
                jsonObject = new JSONObject(getIntent().getStringExtra("json"));
                showTrend();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showTrend() {
        try {
            //星期1到7，周一是2，类推，周日是1
            String str1 = jsonObject.get("1").toString();
            if (!TextUtils.isEmpty(str1)) {
                if (str1.indexOf("00") != -1) {
                    bool_17 = true;
                    btn_17.setTextColor(Color.parseColor("#ffffff"));
                    btn_17.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("01") != -1) {
                    bool_27 = true;
                    btn_27.setTextColor(Color.parseColor("#ffffff"));
                    btn_27.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("02") != -1) {
                    bool_37 = true;
                    btn_37.setTextColor(Color.parseColor("#ffffff"));
                    btn_37.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("03") != -1) {
                    bool_47 = true;
                    btn_47.setTextColor(Color.parseColor("#ffffff"));
                    btn_47.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("04") != -1) {
                    bool_57 = true;
                    btn_57.setTextColor(Color.parseColor("#ffffff"));
                    btn_57.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("05") != -1) {
                    bool_67 = true;
                    btn_67.setTextColor(Color.parseColor("#ffffff"));
                    btn_67.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("06") != -1) {
                    bool_77 = true;
                    btn_77.setTextColor(Color.parseColor("#ffffff"));
                    btn_77.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("07") != -1) {
                    bool_87 = true;
                    btn_87.setTextColor(Color.parseColor("#ffffff"));
                    btn_87.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("08") != -1) {
                    bool_97 = true;
                    btn_97.setTextColor(Color.parseColor("#ffffff"));
                    btn_97.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("09") != -1) {
                    bool_107 = true;
                    btn_107.setTextColor(Color.parseColor("#ffffff"));
                    btn_107.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("10") != -1) {
                    bool_117 = true;
                    btn_117.setTextColor(Color.parseColor("#ffffff"));
                    btn_117.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("11") != -1) {
                    bool_127 = true;
                    btn_127.setTextColor(Color.parseColor("#ffffff"));
                    btn_127.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("12") != -1) {
                    bool_137 = true;
                    btn_137.setTextColor(Color.parseColor("#ffffff"));
                    btn_137.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("13") != -1) {
                    bool_147 = true;
                    btn_147.setTextColor(Color.parseColor("#ffffff"));
                    btn_147.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("14") != -1) {
                    bool_157 = true;
                    btn_157.setTextColor(Color.parseColor("#ffffff"));
                    btn_157.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("15") != -1) {
                    bool_167 = true;
                    btn_167.setTextColor(Color.parseColor("#ffffff"));
                    btn_167.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("16") != -1) {
                    bool_177 = true;
                    btn_177.setTextColor(Color.parseColor("#ffffff"));
                    btn_177.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("17") != -1) {
                    bool_187 = true;
                    btn_187.setTextColor(Color.parseColor("#ffffff"));
                    btn_187.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("18") != -1) {
                    bool_197 = true;
                    btn_197.setTextColor(Color.parseColor("#ffffff"));
                    btn_197.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("19") != -1) {
                    bool_207 = true;
                    btn_207.setTextColor(Color.parseColor("#ffffff"));
                    btn_207.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("20") != -1) {
                    bool_217 = true;
                    btn_217.setTextColor(Color.parseColor("#ffffff"));
                    btn_217.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("21") != -1) {
                    bool_227 = true;
                    btn_227.setTextColor(Color.parseColor("#ffffff"));
                    btn_227.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("22") != -1) {
                    bool_237 = true;
                    btn_237.setTextColor(Color.parseColor("#ffffff"));
                    btn_237.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str1.indexOf("23") != -1) {
                    bool_247 = true;
                    btn_247.setTextColor(Color.parseColor("#ffffff"));
                    btn_247.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String str2 = jsonObject.get("2").toString();
            if (!TextUtils.isEmpty(str2)) {
                if (str2.indexOf("00") != -1) {
                    bool_11 = true;
                    btn_11.setTextColor(Color.parseColor("#ffffff"));
                    btn_11.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("01") != -1) {
                    bool_21 = true;
                    btn_21.setTextColor(Color.parseColor("#ffffff"));
                    btn_21.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("02") != -1) {
                    bool_31 = true;
                    btn_31.setTextColor(Color.parseColor("#ffffff"));
                    btn_31.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("03") != -1) {
                    bool_41 = true;
                    btn_41.setTextColor(Color.parseColor("#ffffff"));
                    btn_41.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("04") != -1) {
                    bool_51 = true;
                    btn_51.setTextColor(Color.parseColor("#ffffff"));
                    btn_51.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("05") != -1) {
                    bool_61 = true;
                    btn_61.setTextColor(Color.parseColor("#ffffff"));
                    btn_61.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("06") != -1) {
                    bool_71 = true;
                    btn_71.setTextColor(Color.parseColor("#ffffff"));
                    btn_71.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("07") != -1) {
                    bool_81 = true;
                    btn_81.setTextColor(Color.parseColor("#ffffff"));
                    btn_81.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("08") != -1) {
                    bool_91 = true;
                    btn_91.setTextColor(Color.parseColor("#ffffff"));
                    btn_91.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("09") != -1) {
                    bool_101 = true;
                    btn_101.setTextColor(Color.parseColor("#ffffff"));
                    btn_101.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("10") != -1) {
                    bool_111 = true;
                    btn_111.setTextColor(Color.parseColor("#ffffff"));
                    btn_111.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("11") != -1) {
                    bool_121 = true;
                    btn_121.setTextColor(Color.parseColor("#ffffff"));
                    btn_121.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("12") != -1) {
                    bool_131 = true;
                    btn_131.setTextColor(Color.parseColor("#ffffff"));
                    btn_131.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("13") != -1) {
                    bool_141 = true;
                    btn_141.setTextColor(Color.parseColor("#ffffff"));
                    btn_141.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("14") != -1) {
                    bool_151 = true;
                    btn_151.setTextColor(Color.parseColor("#ffffff"));
                    btn_151.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("15") != -1) {
                    bool_161 = true;
                    btn_161.setTextColor(Color.parseColor("#ffffff"));
                    btn_161.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("16") != -1) {
                    bool_171 = true;
                    btn_171.setTextColor(Color.parseColor("#ffffff"));
                    btn_171.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("17") != -1) {
                    bool_181 = true;
                    btn_181.setTextColor(Color.parseColor("#ffffff"));
                    btn_181.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("18") != -1) {
                    bool_191 = true;
                    btn_191.setTextColor(Color.parseColor("#ffffff"));
                    btn_191.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("19") != -1) {
                    bool_201 = true;
                    btn_201.setTextColor(Color.parseColor("#ffffff"));
                    btn_201.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("20") != -1) {
                    bool_211 = true;
                    btn_211.setTextColor(Color.parseColor("#ffffff"));
                    btn_211.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("21") != -1) {
                    bool_221 = true;
                    btn_221.setTextColor(Color.parseColor("#ffffff"));
                    btn_221.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("22") != -1) {
                    bool_231 = true;
                    btn_231.setTextColor(Color.parseColor("#ffffff"));
                    btn_231.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str2.indexOf("23") != -1) {
                    bool_241 = true;
                    btn_241.setTextColor(Color.parseColor("#ffffff"));
                    btn_241.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String str3 = jsonObject.get("3").toString();
            if (!TextUtils.isEmpty(str3)) {
                if (str3.indexOf("00") != -1) {
                    bool_12 = true;
                    btn_12.setTextColor(Color.parseColor("#ffffff"));
                    btn_12.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("01") != -1) {
                    bool_22 = true;
                    btn_22.setTextColor(Color.parseColor("#ffffff"));
                    btn_22.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("02") != -1) {
                    bool_32 = true;
                    btn_32.setTextColor(Color.parseColor("#ffffff"));
                    btn_32.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("03") != -1) {
                    bool_42 = true;
                    btn_42.setTextColor(Color.parseColor("#ffffff"));
                    btn_42.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("04") != -1) {
                    bool_52 = true;
                    btn_52.setTextColor(Color.parseColor("#ffffff"));
                    btn_52.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("05") != -1) {
                    bool_62 = true;
                    btn_62.setTextColor(Color.parseColor("#ffffff"));
                    btn_62.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("06") != -1) {
                    bool_72 = true;
                    btn_72.setTextColor(Color.parseColor("#ffffff"));
                    btn_72.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("07") != -1) {
                    bool_82 = true;
                    btn_82.setTextColor(Color.parseColor("#ffffff"));
                    btn_82.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("08") != -1) {
                    bool_92 = true;
                    btn_92.setTextColor(Color.parseColor("#ffffff"));
                    btn_92.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("09") != -1) {
                    bool_102 = true;
                    btn_102.setTextColor(Color.parseColor("#ffffff"));
                    btn_102.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("10") != -1) {
                    bool_112 = true;
                    btn_112.setTextColor(Color.parseColor("#ffffff"));
                    btn_112.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("11") != -1) {
                    bool_122 = true;
                    btn_122.setTextColor(Color.parseColor("#ffffff"));
                    btn_122.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("12") != -1) {
                    bool_132 = true;
                    btn_132.setTextColor(Color.parseColor("#ffffff"));
                    btn_132.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("13") != -1) {
                    bool_142 = true;
                    btn_142.setTextColor(Color.parseColor("#ffffff"));
                    btn_142.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("14") != -1) {
                    bool_152 = true;
                    btn_152.setTextColor(Color.parseColor("#ffffff"));
                    btn_152.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("15") != -1) {
                    bool_162 = true;
                    btn_162.setTextColor(Color.parseColor("#ffffff"));
                    btn_162.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("16") != -1) {
                    bool_172 = true;
                    btn_172.setTextColor(Color.parseColor("#ffffff"));
                    btn_172.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("17") != -1) {
                    bool_182 = true;
                    btn_182.setTextColor(Color.parseColor("#ffffff"));
                    btn_182.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("18") != -1) {
                    bool_192 = true;
                    btn_192.setTextColor(Color.parseColor("#ffffff"));
                    btn_192.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("19") != -1) {
                    bool_202 = true;
                    btn_202.setTextColor(Color.parseColor("#ffffff"));
                    btn_202.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("20") != -1) {
                    bool_212 = true;
                    btn_212.setTextColor(Color.parseColor("#ffffff"));
                    btn_212.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("21") != -1) {
                    bool_222 = true;
                    btn_222.setTextColor(Color.parseColor("#ffffff"));
                    btn_222.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("22") != -1) {
                    bool_232 = true;
                    btn_232.setTextColor(Color.parseColor("#ffffff"));
                    btn_232.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str3.indexOf("23") != -1) {
                    bool_242 = true;
                    btn_242.setTextColor(Color.parseColor("#ffffff"));
                    btn_242.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String str4 = jsonObject.get("4").toString();
            if (!TextUtils.isEmpty(str4)) {
                if (str4.indexOf("00") != -1) {
                    bool_13 = true;
                    btn_13.setTextColor(Color.parseColor("#ffffff"));
                    btn_13.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("01") != -1) {
                    bool_23 = true;
                    btn_23.setTextColor(Color.parseColor("#ffffff"));
                    btn_23.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("02") != -1) {
                    bool_33 = true;
                    btn_33.setTextColor(Color.parseColor("#ffffff"));
                    btn_33.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("03") != -1) {
                    bool_43 = true;
                    btn_43.setTextColor(Color.parseColor("#ffffff"));
                    btn_43.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("04") != -1) {
                    bool_53 = true;
                    btn_53.setTextColor(Color.parseColor("#ffffff"));
                    btn_53.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("05") != -1) {
                    bool_63 = true;
                    btn_63.setTextColor(Color.parseColor("#ffffff"));
                    btn_63.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("06") != -1) {
                    bool_73 = true;
                    btn_73.setTextColor(Color.parseColor("#ffffff"));
                    btn_73.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("07") != -1) {
                    bool_83 = true;
                    btn_83.setTextColor(Color.parseColor("#ffffff"));
                    btn_83.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("08") != -1) {
                    bool_93 = true;
                    btn_93.setTextColor(Color.parseColor("#ffffff"));
                    btn_93.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("09") != -1) {
                    bool_103 = true;
                    btn_103.setTextColor(Color.parseColor("#ffffff"));
                    btn_103.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("10") != -1) {
                    bool_113 = true;
                    btn_113.setTextColor(Color.parseColor("#ffffff"));
                    btn_113.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("11") != -1) {
                    bool_123 = true;
                    btn_123.setTextColor(Color.parseColor("#ffffff"));
                    btn_123.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("12") != -1) {
                    bool_133 = true;
                    btn_133.setTextColor(Color.parseColor("#ffffff"));
                    btn_133.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("13") != -1) {
                    bool_143 = true;
                    btn_143.setTextColor(Color.parseColor("#ffffff"));
                    btn_143.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("14") != -1) {
                    bool_153 = true;
                    btn_153.setTextColor(Color.parseColor("#ffffff"));
                    btn_153.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("15") != -1) {
                    bool_163 = true;
                    btn_163.setTextColor(Color.parseColor("#ffffff"));
                    btn_163.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("16") != -1) {
                    bool_173 = true;
                    btn_173.setTextColor(Color.parseColor("#ffffff"));
                    btn_173.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("17") != -1) {
                    bool_183 = true;
                    btn_183.setTextColor(Color.parseColor("#ffffff"));
                    btn_183.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("18") != -1) {
                    bool_193 = true;
                    btn_193.setTextColor(Color.parseColor("#ffffff"));
                    btn_193.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("19") != -1) {
                    bool_203 = true;
                    btn_203.setTextColor(Color.parseColor("#ffffff"));
                    btn_203.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("20") != -1) {
                    bool_213 = true;
                    btn_213.setTextColor(Color.parseColor("#ffffff"));
                    btn_213.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("21") != -1) {
                    bool_223 = true;
                    btn_223.setTextColor(Color.parseColor("#ffffff"));
                    btn_223.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("22") != -1) {
                    bool_233 = true;
                    btn_233.setTextColor(Color.parseColor("#ffffff"));
                    btn_233.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str4.indexOf("23") != -1) {
                    bool_243 = true;
                    btn_243.setTextColor(Color.parseColor("#ffffff"));
                    btn_243.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String str5 = jsonObject.get("5").toString();
            if (!TextUtils.isEmpty(str5)) {
                if (str5.indexOf("00") != -1) {
                    bool_14 = true;
                    btn_14.setTextColor(Color.parseColor("#ffffff"));
                    btn_14.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("01") != -1) {
                    bool_24 = true;
                    btn_24.setTextColor(Color.parseColor("#ffffff"));
                    btn_24.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("02") != -1) {
                    bool_34 = true;
                    btn_34.setTextColor(Color.parseColor("#ffffff"));
                    btn_34.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("03") != -1) {
                    bool_44 = true;
                    btn_44.setTextColor(Color.parseColor("#ffffff"));
                    btn_44.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("04") != -1) {
                    bool_54 = true;
                    btn_54.setTextColor(Color.parseColor("#ffffff"));
                    btn_54.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("05") != -1) {
                    bool_64 = true;
                    btn_64.setTextColor(Color.parseColor("#ffffff"));
                    btn_64.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("06") != -1) {
                    bool_74 = true;
                    btn_74.setTextColor(Color.parseColor("#ffffff"));
                    btn_74.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("07") != -1) {
                    bool_84 = true;
                    btn_84.setTextColor(Color.parseColor("#ffffff"));
                    btn_84.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("08") != -1) {
                    bool_94 = true;
                    btn_94.setTextColor(Color.parseColor("#ffffff"));
                    btn_94.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("09") != -1) {
                    bool_104 = true;
                    btn_104.setTextColor(Color.parseColor("#ffffff"));
                    btn_104.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("10") != -1) {
                    bool_114 = true;
                    btn_114.setTextColor(Color.parseColor("#ffffff"));
                    btn_114.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("11") != -1) {
                    bool_124 = true;
                    btn_124.setTextColor(Color.parseColor("#ffffff"));
                    btn_124.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("12") != -1) {
                    bool_134 = true;
                    btn_134.setTextColor(Color.parseColor("#ffffff"));
                    btn_134.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("13") != -1) {
                    bool_144 = true;
                    btn_144.setTextColor(Color.parseColor("#ffffff"));
                    btn_144.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("14") != -1) {
                    bool_154 = true;
                    btn_154.setTextColor(Color.parseColor("#ffffff"));
                    btn_154.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("15") != -1) {
                    bool_164 = true;
                    btn_164.setTextColor(Color.parseColor("#ffffff"));
                    btn_164.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("16") != -1) {
                    bool_174 = true;
                    btn_174.setTextColor(Color.parseColor("#ffffff"));
                    btn_174.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("17") != -1) {
                    bool_184 = true;
                    btn_184.setTextColor(Color.parseColor("#ffffff"));
                    btn_184.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("18") != -1) {
                    bool_194 = true;
                    btn_194.setTextColor(Color.parseColor("#ffffff"));
                    btn_194.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("19") != -1) {
                    bool_204 = true;
                    btn_204.setTextColor(Color.parseColor("#ffffff"));
                    btn_204.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("20") != -1) {
                    bool_214 = true;
                    btn_214.setTextColor(Color.parseColor("#ffffff"));
                    btn_214.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("21") != -1) {
                    bool_224 = true;
                    btn_224.setTextColor(Color.parseColor("#ffffff"));
                    btn_224.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("22") != -1) {
                    bool_234 = true;
                    btn_234.setTextColor(Color.parseColor("#ffffff"));
                    btn_234.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str5.indexOf("23") != -1) {
                    bool_244 = true;
                    btn_244.setTextColor(Color.parseColor("#ffffff"));
                    btn_244.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String str6 = jsonObject.get("6").toString();
            if (!TextUtils.isEmpty(str6)) {
                if (str6.indexOf("00") != -1) {
                    bool_15 = true;
                    btn_15.setTextColor(Color.parseColor("#ffffff"));
                    btn_15.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("01") != -1) {
                    bool_25 = true;
                    btn_25.setTextColor(Color.parseColor("#ffffff"));
                    btn_25.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("02") != -1) {
                    bool_35 = true;
                    btn_35.setTextColor(Color.parseColor("#ffffff"));
                    btn_35.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("03") != -1) {
                    bool_45 = true;
                    btn_45.setTextColor(Color.parseColor("#ffffff"));
                    btn_45.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("04") != -1) {
                    bool_55 = true;
                    btn_55.setTextColor(Color.parseColor("#ffffff"));
                    btn_55.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("05") != -1) {
                    bool_65 = true;
                    btn_65.setTextColor(Color.parseColor("#ffffff"));
                    btn_65.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("06") != -1) {
                    bool_75 = true;
                    btn_75.setTextColor(Color.parseColor("#ffffff"));
                    btn_75.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("07") != -1) {
                    bool_85 = true;
                    btn_85.setTextColor(Color.parseColor("#ffffff"));
                    btn_85.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("08") != -1) {
                    bool_95 = true;
                    btn_95.setTextColor(Color.parseColor("#ffffff"));
                    btn_95.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("09") != -1) {
                    bool_105 = true;
                    btn_105.setTextColor(Color.parseColor("#ffffff"));
                    btn_105.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("10") != -1) {
                    bool_115 = true;
                    btn_115.setTextColor(Color.parseColor("#ffffff"));
                    btn_115.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("11") != -1) {
                    bool_125 = true;
                    btn_125.setTextColor(Color.parseColor("#ffffff"));
                    btn_125.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("12") != -1) {
                    bool_135 = true;
                    btn_135.setTextColor(Color.parseColor("#ffffff"));
                    btn_135.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("13") != -1) {
                    bool_145 = true;
                    btn_145.setTextColor(Color.parseColor("#ffffff"));
                    btn_145.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("14") != -1) {
                    bool_155 = true;
                    btn_155.setTextColor(Color.parseColor("#ffffff"));
                    btn_155.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("15") != -1) {
                    bool_165 = true;
                    btn_165.setTextColor(Color.parseColor("#ffffff"));
                    btn_165.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("16") != -1) {
                    bool_175 = true;
                    btn_175.setTextColor(Color.parseColor("#ffffff"));
                    btn_175.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("17") != -1) {
                    bool_185 = true;
                    btn_185.setTextColor(Color.parseColor("#ffffff"));
                    btn_185.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("18") != -1) {
                    bool_195 = true;
                    btn_195.setTextColor(Color.parseColor("#ffffff"));
                    btn_195.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("19") != -1) {
                    bool_205 = true;
                    btn_205.setTextColor(Color.parseColor("#ffffff"));
                    btn_205.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("20") != -1) {
                    bool_215 = true;
                    btn_215.setTextColor(Color.parseColor("#ffffff"));
                    btn_215.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("21") != -1) {
                    bool_225 = true;
                    btn_225.setTextColor(Color.parseColor("#ffffff"));
                    btn_225.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("22") != -1) {
                    bool_235 = true;
                    btn_235.setTextColor(Color.parseColor("#ffffff"));
                    btn_235.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str6.indexOf("23") != -1) {
                    bool_245 = true;
                    btn_245.setTextColor(Color.parseColor("#ffffff"));
                    btn_245.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String str7 = jsonObject.get("7").toString();
            if (!TextUtils.isEmpty(str7)) {
                if (str7.indexOf("00") != -1) {
                    bool_16 = true;
                    btn_16.setTextColor(Color.parseColor("#ffffff"));
                    btn_16.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("01") != -1) {
                    bool_26 = true;
                    btn_26.setTextColor(Color.parseColor("#ffffff"));
                    btn_26.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("02") != -1) {
                    bool_36 = true;
                    btn_36.setTextColor(Color.parseColor("#ffffff"));
                    btn_36.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("03") != -1) {
                    bool_46 = true;
                    btn_46.setTextColor(Color.parseColor("#ffffff"));
                    btn_46.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("04") != -1) {
                    bool_56 = true;
                    btn_56.setTextColor(Color.parseColor("#ffffff"));
                    btn_56.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("05") != -1) {
                    bool_66 = true;
                    btn_66.setTextColor(Color.parseColor("#ffffff"));
                    btn_66.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("06") != -1) {
                    bool_76 = true;
                    btn_76.setTextColor(Color.parseColor("#ffffff"));
                    btn_76.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("07") != -1) {
                    bool_86 = true;
                    btn_86.setTextColor(Color.parseColor("#ffffff"));
                    btn_86.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("08") != -1) {
                    bool_96 = true;
                    btn_96.setTextColor(Color.parseColor("#ffffff"));
                    btn_96.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("09") != -1) {
                    bool_106 = true;
                    btn_106.setTextColor(Color.parseColor("#ffffff"));
                    btn_106.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("10") != -1) {
                    bool_116 = true;
                    btn_116.setTextColor(Color.parseColor("#ffffff"));
                    btn_116.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("11") != -1) {
                    bool_126 = true;
                    btn_126.setTextColor(Color.parseColor("#ffffff"));
                    btn_126.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("12") != -1) {
                    bool_136 = true;
                    btn_136.setTextColor(Color.parseColor("#ffffff"));
                    btn_136.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("13") != -1) {
                    bool_146 = true;
                    btn_146.setTextColor(Color.parseColor("#ffffff"));
                    btn_146.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("14") != -1) {
                    bool_156 = true;
                    btn_156.setTextColor(Color.parseColor("#ffffff"));
                    btn_156.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("15") != -1) {
                    bool_166 = true;
                    btn_166.setTextColor(Color.parseColor("#ffffff"));
                    btn_166.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("16") != -1) {
                    bool_176 = true;
                    btn_176.setTextColor(Color.parseColor("#ffffff"));
                    btn_176.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("17") != -1) {
                    bool_186 = true;
                    btn_186.setTextColor(Color.parseColor("#ffffff"));
                    btn_186.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("18") != -1) {
                    bool_196 = true;
                    btn_196.setTextColor(Color.parseColor("#ffffff"));
                    btn_196.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("19") != -1) {
                    bool_206 = true;
                    btn_206.setTextColor(Color.parseColor("#ffffff"));
                    btn_206.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("20") != -1) {
                    bool_216 = true;
                    btn_216.setTextColor(Color.parseColor("#ffffff"));
                    btn_216.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("21") != -1) {
                    bool_226 = true;
                    btn_226.setTextColor(Color.parseColor("#ffffff"));
                    btn_226.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("22") != -1) {
                    bool_236 = true;
                    btn_236.setTextColor(Color.parseColor("#ffffff"));
                    btn_236.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                if (str7.indexOf("23") != -1) {
                    bool_246 = true;
                    btn_246.setTextColor(Color.parseColor("#ffffff"));
                    btn_246.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_all:
                flag_trend = "全部";
                bool_11 = true;
                bool_12 = true;
                bool_13 = true;
                bool_14 = true;
                bool_15 = true;
                bool_16 = true;
                bool_17 = true;
                bool_21 = true;
                bool_22 = true;
                bool_23 = true;
                bool_24 = true;
                bool_25 = true;
                bool_26 = true;
                bool_27 = true;
                bool_31 = true;
                bool_32 = true;
                bool_33 = true;
                bool_34 = true;
                bool_35 = true;
                bool_36 = true;
                bool_37 = true;
                bool_41 = true;
                bool_42 = true;
                bool_43 = true;
                bool_44 = true;
                bool_45 = true;
                bool_46 = true;
                bool_47 = true;
                bool_51 = true;
                bool_52 = true;
                bool_53 = true;
                bool_54 = true;
                bool_55 = true;
                bool_56 = true;
                bool_57 = true;
                bool_61 = true;
                bool_62 = true;
                bool_63 = true;
                bool_64 = true;
                bool_65 = true;
                bool_66 = true;
                bool_67 = true;
                bool_71 = true;
                bool_72 = true;
                bool_73 = true;
                bool_74 = true;
                bool_75 = true;
                bool_76 = true;
                bool_77 = true;
                bool_81 = true;
                bool_82 = true;
                bool_83 = true;
                bool_84 = true;
                bool_85 = true;
                bool_86 = true;
                bool_87 = true;
                bool_91 = true;
                bool_92 = true;
                bool_93 = true;
                bool_94 = true;
                bool_95 = true;
                bool_96 = true;
                bool_97 = true;
                bool_101 = true;
                bool_102 = true;
                bool_103 = true;
                bool_104 = true;
                bool_105 = true;
                bool_106 = true;
                bool_107 = true;
                bool_111 = true;
                bool_112 = true;
                bool_113 = true;
                bool_114 = true;
                bool_115 = true;
                bool_116 = true;
                bool_117 = true;
                bool_121 = true;
                bool_122 = true;
                bool_123 = true;
                bool_124 = true;
                bool_125 = true;
                bool_126 = true;
                bool_127 = true;
                bool_131 = true;
                bool_132 = true;
                bool_133 = true;
                bool_134 = true;
                bool_135 = true;
                bool_136 = true;
                bool_137 = true;
                bool_141 = true;
                bool_142 = true;
                bool_143 = true;
                bool_144 = true;
                bool_145 = true;
                bool_146 = true;
                bool_147 = true;
                bool_151 = true;
                bool_152 = true;
                bool_153 = true;
                bool_154 = true;
                bool_155 = true;
                bool_156 = true;
                bool_157 = true;
                bool_161 = true;
                bool_162 = true;
                bool_163 = true;
                bool_164 = true;
                bool_165 = true;
                bool_166 = true;
                bool_167 = true;
                bool_171 = true;
                bool_172 = true;
                bool_173 = true;
                bool_174 = true;
                bool_175 = true;
                bool_176 = true;
                bool_177 = true;
                bool_181 = true;
                bool_182 = true;
                bool_183 = true;
                bool_184 = true;
                bool_185 = true;
                bool_186 = true;
                bool_187 = true;
                bool_191 = true;
                bool_192 = true;
                bool_193 = true;
                bool_194 = true;
                bool_195 = true;
                bool_196 = true;
                bool_197 = true;
                bool_201 = true;
                bool_202 = true;
                bool_203 = true;
                bool_204 = true;
                bool_205 = true;
                bool_206 = true;
                bool_207 = true;
                bool_211 = true;
                bool_212 = true;
                bool_213 = true;
                bool_214 = true;
                bool_215 = true;
                bool_216 = true;
                bool_217 = true;
                bool_221 = true;
                bool_222 = true;
                bool_223 = true;
                bool_224 = true;
                bool_225 = true;
                bool_226 = true;
                bool_227 = true;
                bool_231 = true;
                bool_232 = true;
                bool_233 = true;
                bool_234 = true;
                bool_235 = true;
                bool_236 = true;
                bool_237 = true;
                bool_241 = true;
                bool_242 = true;
                bool_243 = true;
                bool_244 = true;
                bool_245 = true;
                bool_246 = true;
                bool_247 = true;
                btn_all.setTextColor(Color.parseColor("#ffffff"));
                btn_all.setBackgroundColor(Color.parseColor("#2a7ccf"));

                btn_working_days.setTextColor(Color.parseColor("#555555"));
                btn_working_days.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                btn_weekend.setTextColor(Color.parseColor("#555555"));
                btn_weekend.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));

                clearAllColor();
                setAllColor();
                break;
            case R.id.btn_working_days:
                flag_trend = "工作日";
                btn_working_days.setTextColor(Color.parseColor("#ffffff"));
                btn_working_days.setBackgroundColor(Color.parseColor("#2a7ccf"));

                btn_weekend.setTextColor(Color.parseColor("#555555"));
                btn_weekend.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                btn_all.setTextColor(Color.parseColor("#555555"));
                btn_all.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));

                bool_11 = true;
                bool_12 = true;
                bool_13 = true;
                bool_14 = true;
                bool_15 = true;
                bool_16 = false;
                bool_17 = false;
                bool_21 = true;
                bool_22 = true;
                bool_23 = true;
                bool_24 = true;
                bool_25 = true;
                bool_26 = false;
                bool_27 = false;
                bool_31 = true;
                bool_32 = true;
                bool_33 = true;
                bool_34 = true;
                bool_35 = true;
                bool_36 = false;
                bool_37 = false;
                bool_41 = true;
                bool_42 = true;
                bool_43 = true;
                bool_44 = true;
                bool_45 = true;
                bool_46 = false;
                bool_47 = false;
                bool_51 = true;
                bool_52 = true;
                bool_53 = true;
                bool_54 = true;
                bool_55 = true;
                bool_56 = false;
                bool_57 = false;
                bool_61 = true;
                bool_62 = true;
                bool_63 = true;
                bool_64 = true;
                bool_65 = true;
                bool_66 = false;
                bool_67 = false;
                bool_71 = true;
                bool_72 = true;
                bool_73 = true;
                bool_74 = true;
                bool_75 = true;
                bool_76 = false;
                bool_77 = false;
                bool_81 = true;
                bool_82 = true;
                bool_83 = true;
                bool_84 = true;
                bool_85 = true;
                bool_86 = false;
                bool_87 = false;
                bool_91 = true;
                bool_92 = true;
                bool_93 = true;
                bool_94 = true;
                bool_95 = true;
                bool_96 = false;
                bool_97 = false;
                bool_101 = true;
                bool_102 = true;
                bool_103 = true;
                bool_104 = true;
                bool_105 = true;
                bool_106 = false;
                bool_107 = false;
                bool_111 = true;
                bool_112 = true;
                bool_113 = true;
                bool_114 = true;
                bool_115 = true;
                bool_116 = false;
                bool_117 = false;
                bool_121 = true;
                bool_122 = true;
                bool_123 = true;
                bool_124 = true;
                bool_125 = true;
                bool_126 = false;
                bool_127 = false;
                bool_131 = true;
                bool_132 = true;
                bool_133 = true;
                bool_134 = true;
                bool_135 = true;
                bool_136 = false;
                bool_137 = false;
                bool_141 = true;
                bool_142 = true;
                bool_143 = true;
                bool_144 = true;
                bool_145 = true;
                bool_146 = false;
                bool_147 = false;
                bool_151 = true;
                bool_152 = true;
                bool_153 = true;
                bool_154 = true;
                bool_155 = true;
                bool_156 = false;
                bool_157 = false;
                bool_161 = true;
                bool_162 = true;
                bool_163 = true;
                bool_164 = true;
                bool_165 = true;
                bool_166 = false;
                bool_167 = false;
                bool_171 = true;
                bool_172 = true;
                bool_173 = true;
                bool_174 = true;
                bool_175 = true;
                bool_176 = false;
                bool_177 = false;
                bool_181 = true;
                bool_182 = true;
                bool_183 = true;
                bool_184 = true;
                bool_185 = true;
                bool_186 = false;
                bool_187 = false;
                bool_191 = true;
                bool_192 = true;
                bool_193 = true;
                bool_194 = true;
                bool_195 = true;
                bool_196 = false;
                bool_197 = false;
                bool_201 = true;
                bool_202 = true;
                bool_203 = true;
                bool_204 = true;
                bool_205 = true;
                bool_206 = false;
                bool_207 = false;
                bool_211 = true;
                bool_212 = true;
                bool_213 = true;
                bool_214 = true;
                bool_215 = true;
                bool_216 = false;
                bool_217 = false;
                bool_221 = true;
                bool_222 = true;
                bool_223 = true;
                bool_224 = true;
                bool_225 = true;
                bool_226 = false;
                bool_227 = false;
                bool_231 = true;
                bool_232 = true;
                bool_233 = true;
                bool_234 = true;
                bool_235 = true;
                bool_236 = false;
                bool_237 = false;
                bool_241 = true;
                bool_242 = true;
                bool_243 = true;
                bool_244 = true;
                bool_245 = true;
                bool_246 = false;
                bool_247 = false;

                clearAllColor();
                WorkingDays();
                break;
            case R.id.btn_weekend:
                flag_trend = "周末";
                bool_11 = false;
                bool_12 = false;
                bool_13 = false;
                bool_14 = false;
                bool_15 = false;
                bool_16 = true;
                bool_17 = true;
                bool_21 = false;
                bool_22 = false;
                bool_23 = false;
                bool_24 = false;
                bool_25 = false;
                bool_26 = true;
                bool_27 = true;
                bool_31 = false;
                bool_32 = false;
                bool_33 = false;
                bool_34 = false;
                bool_35 = false;
                bool_36 = true;
                bool_37 = true;
                bool_41 = false;
                bool_42 = false;
                bool_43 = false;
                bool_44 = false;
                bool_45 = false;
                bool_46 = true;
                bool_47 = true;
                bool_51 = false;
                bool_52 = false;
                bool_53 = false;
                bool_54 = false;
                bool_55 = false;
                bool_56 = true;
                bool_57 = true;
                bool_61 = false;
                bool_62 = false;
                bool_63 = false;
                bool_64 = false;
                bool_65 = false;
                bool_66 = true;
                bool_67 = true;
                bool_71 = false;
                bool_72 = false;
                bool_73 = false;
                bool_74 = false;
                bool_75 = false;
                bool_76 = true;
                bool_77 = true;
                bool_81 = false;
                bool_82 = false;
                bool_83 = false;
                bool_84 = false;
                bool_85 = false;
                bool_86 = true;
                bool_87 = true;
                bool_91 = false;
                bool_92 = false;
                bool_93 = false;
                bool_94 = false;
                bool_95 = false;
                bool_96 = true;
                bool_97 = true;
                bool_101 = false;
                bool_102 = false;
                bool_103 = false;
                bool_104 = false;
                bool_105 = false;
                bool_106 = true;
                bool_107 = true;
                bool_111 = false;
                bool_112 = false;
                bool_113 = false;
                bool_114 = false;
                bool_115 = false;
                bool_116 = true;
                bool_117 = true;
                bool_121 = false;
                bool_122 = false;
                bool_123 = false;
                bool_124 = false;
                bool_125 = false;
                bool_126 = true;
                bool_127 = true;
                bool_131 = false;
                bool_132 = false;
                bool_133 = false;
                bool_134 = false;
                bool_135 = false;
                bool_136 = true;
                bool_137 = true;
                bool_141 = false;
                bool_142 = false;
                bool_143 = false;
                bool_144 = false;
                bool_145 = false;
                bool_146 = true;
                bool_147 = true;
                bool_151 = false;
                bool_152 = false;
                bool_153 = false;
                bool_154 = false;
                bool_155 = false;
                bool_156 = true;
                bool_157 = true;
                bool_161 = false;
                bool_162 = false;
                bool_163 = false;
                bool_164 = false;
                bool_165 = false;
                bool_166 = true;
                bool_167 = true;
                bool_171 = false;
                bool_172 = false;
                bool_173 = false;
                bool_174 = false;
                bool_175 = false;
                bool_176 = true;
                bool_177 = true;
                bool_181 = false;
                bool_182 = false;
                bool_183 = false;
                bool_184 = false;
                bool_185 = false;
                bool_186 = true;
                bool_187 = true;
                bool_191 = false;
                bool_192 = false;
                bool_193 = false;
                bool_194 = false;
                bool_195 = false;
                bool_196 = true;
                bool_197 = true;
                bool_201 = false;
                bool_202 = false;
                bool_203 = false;
                bool_204 = false;
                bool_205 = false;
                bool_206 = true;
                bool_207 = true;
                bool_211 = false;
                bool_212 = false;
                bool_213 = false;
                bool_214 = false;
                bool_215 = false;
                bool_216 = true;
                bool_217 = true;
                bool_221 = false;
                bool_222 = false;
                bool_223 = false;
                bool_224 = false;
                bool_225 = false;
                bool_226 = true;
                bool_227 = true;
                bool_231 = false;
                bool_232 = false;
                bool_233 = false;
                bool_234 = false;
                bool_235 = false;
                bool_236 = true;
                bool_237 = true;
                bool_241 = false;
                bool_242 = false;
                bool_243 = false;
                bool_244 = false;
                bool_245 = false;
                bool_246 = true;
                bool_247 = true;
                btn_weekend.setTextColor(Color.parseColor("#ffffff"));
                btn_weekend.setBackgroundColor(Color.parseColor("#2a7ccf"));

                btn_working_days.setTextColor(Color.parseColor("#555555"));
                btn_working_days.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                btn_all.setTextColor(Color.parseColor("#555555"));
                btn_all.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                clearAllColor();
                weekend();
                break;
            case R.id.tv_9_12:
                flag_time = "9_12";
                int rb_px = (int) tv_9_12.getX() + tv_9_12.getWidth() / 2;
                mHorizontalScrollView.scrollTo(rb_px - screenWidth / 2, 0);
                int num = (int) (btn_91.getY() + btn_91.getHeight() / 2);
                scrollView.scrollTo(0, screenHeight / 2 - num);
                Log.i("infoss", "9_12:" + (screenHeight / 2 - num));
                tv_9_12.setTextColor(this.getResources().getColor(R.color.black));
                tv_13_16.setTextColor(this.getResources().getColor(R.color.gray));
                tv_17_20.setTextColor(this.getResources().getColor(R.color.gray));
                tv_21_00.setTextColor(this.getResources().getColor(R.color.gray));
                tv_01_04.setTextColor(this.getResources().getColor(R.color.gray));
                tv_05_08.setTextColor(this.getResources().getColor(R.color.gray));
                if (flag_trend.equals("全部")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = true;
                    bool_102 = true;
                    bool_103 = true;
                    bool_104 = true;
                    bool_105 = true;
                    bool_106 = true;
                    bool_107 = true;
                    bool_111 = true;
                    bool_112 = true;
                    bool_113 = true;
                    bool_114 = true;
                    bool_115 = true;
                    bool_116 = true;
                    bool_117 = true;
                    bool_121 = true;
                    bool_122 = true;
                    bool_123 = true;
                    bool_124 = true;
                    bool_125 = true;
                    bool_126 = true;
                    bool_127 = true;
                    bool_131 = true;
                    bool_132 = true;
                    bool_133 = true;
                    bool_134 = true;
                    bool_135 = true;
                    bool_136 = true;
                    bool_137 = true;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_9_12();
                } else if (flag_trend.equals("工作日")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = true;
                    bool_102 = true;
                    bool_103 = true;
                    bool_104 = true;
                    bool_105 = true;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = true;
                    bool_112 = true;
                    bool_113 = true;
                    bool_114 = true;
                    bool_115 = true;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = true;
                    bool_122 = true;
                    bool_123 = true;
                    bool_124 = true;
                    bool_125 = true;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = true;
                    bool_132 = true;
                    bool_133 = true;
                    bool_134 = true;
                    bool_135 = true;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_9_12_b();
                } else if (flag_trend.equals("周末")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = true;
                    bool_107 = true;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = true;
                    bool_117 = true;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = true;
                    bool_127 = true;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = true;
                    bool_137 = true;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_9_12_c();
                }
                break;
            case R.id.tv_13_16:
                flag_time = "13_16";
                int rb_px2 = (int) tv_13_16.getX() + tv_13_16.getWidth() / 2;
                mHorizontalScrollView.scrollTo(rb_px2 - screenWidth / 2, 0);
                int num2 = (int) (btn_131.getY() + btn_131.getHeight() / 2);
                scrollView.scrollTo(0, screenHeight / 2 - num2);
                Log.i("infoss", "13_16:" + (screenHeight / 2 - num2));
                tv_13_16.setTextColor(this.getResources().getColor(R.color.black));
                tv_9_12.setTextColor(this.getResources().getColor(R.color.gray));
                tv_17_20.setTextColor(this.getResources().getColor(R.color.gray));
                tv_21_00.setTextColor(this.getResources().getColor(R.color.gray));
                tv_01_04.setTextColor(this.getResources().getColor(R.color.gray));
                tv_05_08.setTextColor(this.getResources().getColor(R.color.gray));
                if (flag_trend.equals("全部")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = true;
                    bool_142 = true;
                    bool_143 = true;
                    bool_144 = true;
                    bool_145 = true;
                    bool_146 = true;
                    bool_147 = true;
                    bool_151 = true;
                    bool_152 = true;
                    bool_153 = true;
                    bool_154 = true;
                    bool_155 = true;
                    bool_156 = true;
                    bool_157 = true;
                    bool_161 = true;
                    bool_162 = true;
                    bool_163 = true;
                    bool_164 = true;
                    bool_165 = true;
                    bool_166 = true;
                    bool_167 = true;
                    bool_171 = true;
                    bool_172 = true;
                    bool_173 = true;
                    bool_174 = true;
                    bool_175 = true;
                    bool_176 = true;
                    bool_177 = true;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_13_16();
                } else if (flag_trend.equals("工作日")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = true;
                    bool_142 = true;
                    bool_143 = true;
                    bool_144 = true;
                    bool_145 = true;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = true;
                    bool_152 = true;
                    bool_153 = true;
                    bool_154 = true;
                    bool_155 = true;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = true;
                    bool_162 = true;
                    bool_163 = true;
                    bool_164 = true;
                    bool_165 = true;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = true;
                    bool_172 = true;
                    bool_173 = true;
                    bool_174 = true;
                    bool_175 = true;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_13_16_b();
                } else if (flag_trend.equals("周末")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = true;
                    bool_147 = true;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = true;
                    bool_157 = true;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = true;
                    bool_167 = true;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = true;
                    bool_177 = true;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_13_16_c();
                }
                break;
            case R.id.tv_17_20:
                flag_time = "17_20";
                int rb_px3 = (int) tv_17_20.getX() + tv_17_20.getWidth() / 2;
                mHorizontalScrollView.scrollTo(rb_px3 - screenWidth / 2, 0);
                int num3 = (int) (btn_171.getY() + btn_171.getHeight() / 2);
                scrollView.scrollTo(0, screenHeight / 2 - num3);
                Log.i("infoss", "7_20:" + (screenHeight / 2 - num3));
                tv_17_20.setTextColor(this.getResources().getColor(R.color.black));
                tv_9_12.setTextColor(this.getResources().getColor(R.color.gray));
                tv_13_16.setTextColor(this.getResources().getColor(R.color.gray));
                tv_21_00.setTextColor(this.getResources().getColor(R.color.gray));
                tv_01_04.setTextColor(this.getResources().getColor(R.color.gray));
                tv_05_08.setTextColor(this.getResources().getColor(R.color.gray));
                if (flag_trend.equals("全部")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = true;
                    bool_182 = true;
                    bool_183 = true;
                    bool_184 = true;
                    bool_185 = true;
                    bool_186 = true;
                    bool_187 = true;
                    bool_191 = true;
                    bool_192 = true;
                    bool_193 = true;
                    bool_194 = true;
                    bool_195 = true;
                    bool_196 = true;
                    bool_197 = true;
                    bool_201 = true;
                    bool_202 = true;
                    bool_203 = true;
                    bool_204 = true;
                    bool_205 = true;
                    bool_206 = true;
                    bool_207 = true;
                    bool_211 = true;
                    bool_212 = true;
                    bool_213 = true;
                    bool_214 = true;
                    bool_215 = true;
                    bool_216 = true;
                    bool_217 = true;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_17_20();
                } else if (flag_trend.equals("工作日")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = true;
                    bool_182 = true;
                    bool_183 = true;
                    bool_184 = true;
                    bool_185 = true;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = true;
                    bool_192 = true;
                    bool_193 = true;
                    bool_194 = true;
                    bool_195 = true;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = true;
                    bool_202 = true;
                    bool_203 = true;
                    bool_204 = true;
                    bool_205 = true;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = true;
                    bool_212 = true;
                    bool_213 = true;
                    bool_214 = true;
                    bool_215 = true;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_17_20_b();
                } else if (flag_trend.equals("周末")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = true;
                    bool_187 = true;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = true;
                    bool_197 = true;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = true;
                    bool_207 = true;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = true;
                    bool_217 = true;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_17_20_c();
                }
                break;
            case R.id.tv_21_00:
                flag_time = "21_00";
                int rb_px4 = (int) tv_21_00.getX() + tv_21_00.getWidth() / 2;
                mHorizontalScrollView.scrollTo(rb_px4 - screenWidth / 2, 0);
                int num4 = (int) (btn_211.getY() + btn_211.getHeight() / 2);
                scrollView.scrollTo(0, screenHeight / 2 - num4);
                Log.i("infoss", "21_00:" + (screenHeight / 2 - num4));
                tv_21_00.setTextColor(this.getResources().getColor(R.color.black));
                tv_9_12.setTextColor(this.getResources().getColor(R.color.gray));
                tv_13_16.setTextColor(this.getResources().getColor(R.color.gray));
                tv_17_20.setTextColor(this.getResources().getColor(R.color.gray));
                tv_01_04.setTextColor(this.getResources().getColor(R.color.gray));
                tv_05_08.setTextColor(this.getResources().getColor(R.color.gray));
                if (flag_trend.equals("全部")) {
                    bool_11 = true;
                    bool_12 = true;
                    bool_13 = true;
                    bool_14 = true;
                    bool_15 = true;
                    bool_16 = true;
                    bool_17 = true;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = true;
                    bool_222 = true;
                    bool_223 = true;
                    bool_224 = true;
                    bool_225 = true;
                    bool_226 = true;
                    bool_227 = true;
                    bool_231 = true;
                    bool_232 = true;
                    bool_233 = true;
                    bool_234 = true;
                    bool_235 = true;
                    bool_236 = true;
                    bool_237 = true;
                    bool_241 = true;
                    bool_242 = true;
                    bool_243 = true;
                    bool_244 = true;
                    bool_245 = true;
                    bool_246 = true;
                    bool_247 = true;
                    clearAllColor();
                    set_21_00();
                } else if (flag_trend.equals("工作日")) {
                    bool_11 = true;
                    bool_12 = true;
                    bool_13 = true;
                    bool_14 = true;
                    bool_15 = true;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = true;
                    bool_222 = true;
                    bool_223 = true;
                    bool_224 = true;
                    bool_225 = true;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = true;
                    bool_232 = true;
                    bool_233 = true;
                    bool_234 = true;
                    bool_235 = true;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = true;
                    bool_242 = true;
                    bool_243 = true;
                    bool_244 = true;
                    bool_245 = true;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_21_00_b();
                } else if (flag_trend.equals("周末")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = true;
                    bool_17 = true;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = true;
                    bool_227 = true;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = true;
                    bool_237 = true;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = true;
                    bool_247 = true;
                    clearAllColor();
                    set_21_00_c();
                }
                break;
            case R.id.tv_01_04:
                flag_time = "01_04";
                int rb_px5 = (int) tv_01_04.getX() + tv_01_04.getWidth() / 2;
                mHorizontalScrollView.scrollTo(rb_px5 - screenWidth / 2, 0);
                int num5 = (int) (btn_11.getY() + btn_11.getHeight() / 2);
                scrollView.scrollTo(0, screenHeight / 2 - num5);
                Log.i("infoss", "01_04:" + (screenHeight / 2 - num5));
                tv_01_04.setTextColor(this.getResources().getColor(R.color.black));
                tv_9_12.setTextColor(this.getResources().getColor(R.color.gray));
                tv_13_16.setTextColor(this.getResources().getColor(R.color.gray));
                tv_17_20.setTextColor(this.getResources().getColor(R.color.gray));
                tv_21_00.setTextColor(this.getResources().getColor(R.color.gray));
                tv_05_08.setTextColor(this.getResources().getColor(R.color.gray));
                if (flag_trend.equals("全部")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = true;
                    bool_22 = true;
                    bool_23 = true;
                    bool_24 = true;
                    bool_25 = true;
                    bool_26 = true;
                    bool_27 = true;
                    bool_31 = true;
                    bool_32 = true;
                    bool_33 = true;
                    bool_34 = true;
                    bool_35 = true;
                    bool_36 = true;
                    bool_37 = true;
                    bool_41 = true;
                    bool_42 = true;
                    bool_43 = true;
                    bool_44 = true;
                    bool_45 = true;
                    bool_46 = true;
                    bool_47 = true;
                    bool_51 = true;
                    bool_52 = true;
                    bool_53 = true;
                    bool_54 = true;
                    bool_55 = true;
                    bool_56 = true;
                    bool_57 = true;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_01_04();
                } else if (flag_trend.equals("工作日")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = true;
                    bool_22 = true;
                    bool_23 = true;
                    bool_24 = true;
                    bool_25 = true;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = true;
                    bool_32 = true;
                    bool_33 = true;
                    bool_34 = true;
                    bool_35 = true;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = true;
                    bool_42 = true;
                    bool_43 = true;
                    bool_44 = true;
                    bool_45 = true;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = true;
                    bool_52 = true;
                    bool_53 = true;
                    bool_54 = true;
                    bool_55 = true;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_01_04_b();
                } else if (flag_trend.equals("周末")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = true;
                    bool_27 = true;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = true;
                    bool_37 = true;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = true;
                    bool_47 = true;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = true;
                    bool_57 = true;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_01_04_c();
                }
                break;
            case R.id.tv_05_08:
                flag_time = "05_08";
                int rb_px6 = (int) tv_05_08.getX() + tv_05_08.getWidth() / 2;
                mHorizontalScrollView.scrollTo(rb_px6 - screenWidth / 2, 0);
                int num6 = (int) (btn_51.getY() + btn_51.getHeight() / 2);
                scrollView.scrollTo(0, screenHeight / 2 - num6);
                Log.i("infoss", "05_08:" + (screenHeight / 2 - num6));
                tv_05_08.setTextColor(this.getResources().getColor(R.color.black));
                tv_9_12.setTextColor(this.getResources().getColor(R.color.gray));
                tv_13_16.setTextColor(this.getResources().getColor(R.color.gray));
                tv_17_20.setTextColor(this.getResources().getColor(R.color.gray));
                tv_21_00.setTextColor(this.getResources().getColor(R.color.gray));
                tv_01_04.setTextColor(this.getResources().getColor(R.color.gray));
                if (flag_trend.equals("全部")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = true;
                    bool_62 = true;
                    bool_63 = true;
                    bool_64 = true;
                    bool_65 = true;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = true;
                    bool_72 = true;
                    bool_73 = true;
                    bool_74 = true;
                    bool_75 = true;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = true;
                    bool_82 = true;
                    bool_83 = true;
                    bool_84 = true;
                    bool_85 = true;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = true;
                    bool_92 = true;
                    bool_93 = true;
                    bool_94 = true;
                    bool_95 = true;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_05_08();
                } else if (flag_trend.equals("工作日")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = true;
                    bool_62 = true;
                    bool_63 = true;
                    bool_64 = true;
                    bool_65 = true;
                    bool_66 = false;
                    bool_67 = false;
                    bool_71 = true;
                    bool_72 = true;
                    bool_73 = true;
                    bool_74 = true;
                    bool_75 = true;
                    bool_76 = false;
                    bool_77 = false;
                    bool_81 = true;
                    bool_82 = true;
                    bool_83 = true;
                    bool_84 = true;
                    bool_85 = true;
                    bool_86 = false;
                    bool_87 = false;
                    bool_91 = true;
                    bool_92 = true;
                    bool_93 = true;
                    bool_94 = true;
                    bool_95 = true;
                    bool_96 = false;
                    bool_97 = false;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_05_08_b();
                } else if (flag_trend.equals("周末")) {
                    bool_11 = false;
                    bool_12 = false;
                    bool_13 = false;
                    bool_14 = false;
                    bool_15 = false;
                    bool_16 = false;
                    bool_17 = false;
                    bool_21 = false;
                    bool_22 = false;
                    bool_23 = false;
                    bool_24 = false;
                    bool_25 = false;
                    bool_26 = false;
                    bool_27 = false;
                    bool_31 = false;
                    bool_32 = false;
                    bool_33 = false;
                    bool_34 = false;
                    bool_35 = false;
                    bool_36 = false;
                    bool_37 = false;
                    bool_41 = false;
                    bool_42 = false;
                    bool_43 = false;
                    bool_44 = false;
                    bool_45 = false;
                    bool_46 = false;
                    bool_47 = false;
                    bool_51 = false;
                    bool_52 = false;
                    bool_53 = false;
                    bool_54 = false;
                    bool_55 = false;
                    bool_56 = false;
                    bool_57 = false;
                    bool_61 = false;
                    bool_62 = false;
                    bool_63 = false;
                    bool_64 = false;
                    bool_65 = false;
                    bool_66 = true;
                    bool_67 = true;
                    bool_71 = false;
                    bool_72 = false;
                    bool_73 = false;
                    bool_74 = false;
                    bool_75 = false;
                    bool_76 = true;
                    bool_77 = true;
                    bool_81 = false;
                    bool_82 = false;
                    bool_83 = false;
                    bool_84 = false;
                    bool_85 = false;
                    bool_86 = true;
                    bool_87 = true;
                    bool_91 = false;
                    bool_92 = false;
                    bool_93 = false;
                    bool_94 = false;
                    bool_95 = false;
                    bool_96 = true;
                    bool_97 = true;
                    bool_101 = false;
                    bool_102 = false;
                    bool_103 = false;
                    bool_104 = false;
                    bool_105 = false;
                    bool_106 = false;
                    bool_107 = false;
                    bool_111 = false;
                    bool_112 = false;
                    bool_113 = false;
                    bool_114 = false;
                    bool_115 = false;
                    bool_116 = false;
                    bool_117 = false;
                    bool_121 = false;
                    bool_122 = false;
                    bool_123 = false;
                    bool_124 = false;
                    bool_125 = false;
                    bool_126 = false;
                    bool_127 = false;
                    bool_131 = false;
                    bool_132 = false;
                    bool_133 = false;
                    bool_134 = false;
                    bool_135 = false;
                    bool_136 = false;
                    bool_137 = false;
                    bool_141 = false;
                    bool_142 = false;
                    bool_143 = false;
                    bool_144 = false;
                    bool_145 = false;
                    bool_146 = false;
                    bool_147 = false;
                    bool_151 = false;
                    bool_152 = false;
                    bool_153 = false;
                    bool_154 = false;
                    bool_155 = false;
                    bool_156 = false;
                    bool_157 = false;
                    bool_161 = false;
                    bool_162 = false;
                    bool_163 = false;
                    bool_164 = false;
                    bool_165 = false;
                    bool_166 = false;
                    bool_167 = false;
                    bool_171 = false;
                    bool_172 = false;
                    bool_173 = false;
                    bool_174 = false;
                    bool_175 = false;
                    bool_176 = false;
                    bool_177 = false;
                    bool_181 = false;
                    bool_182 = false;
                    bool_183 = false;
                    bool_184 = false;
                    bool_185 = false;
                    bool_186 = false;
                    bool_187 = false;
                    bool_191 = false;
                    bool_192 = false;
                    bool_193 = false;
                    bool_194 = false;
                    bool_195 = false;
                    bool_196 = false;
                    bool_197 = false;
                    bool_201 = false;
                    bool_202 = false;
                    bool_203 = false;
                    bool_204 = false;
                    bool_205 = false;
                    bool_206 = false;
                    bool_207 = false;
                    bool_211 = false;
                    bool_212 = false;
                    bool_213 = false;
                    bool_214 = false;
                    bool_215 = false;
                    bool_216 = false;
                    bool_217 = false;
                    bool_221 = false;
                    bool_222 = false;
                    bool_223 = false;
                    bool_224 = false;
                    bool_225 = false;
                    bool_226 = false;
                    bool_227 = false;
                    bool_231 = false;
                    bool_232 = false;
                    bool_233 = false;
                    bool_234 = false;
                    bool_235 = false;
                    bool_236 = false;
                    bool_237 = false;
                    bool_241 = false;
                    bool_242 = false;
                    bool_243 = false;
                    bool_244 = false;
                    bool_245 = false;
                    bool_246 = false;
                    bool_247 = false;
                    clearAllColor();
                    set_05_08_c();
                }
                break;

            case R.id.btn_11:
                if (bool_11) {
                    bool_11 = false;
                    btn_11.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_11.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_11 = true;
                    btn_11.setTextColor(Color.parseColor("#ffffff"));
                    btn_11.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_12:
                if (bool_12) {
                    bool_12 = false;
                    btn_12.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_12.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_12 = true;
                    btn_12.setTextColor(Color.parseColor("#ffffff"));
                    btn_12.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_13:
                if (bool_13) {
                    bool_13 = false;
                    btn_13.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_13.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_13 = true;
                    btn_13.setTextColor(Color.parseColor("#ffffff"));
                    btn_13.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_14:
                if (bool_14) {
                    bool_14 = false;
                    btn_14.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_14.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_14 = true;
                    btn_14.setTextColor(Color.parseColor("#ffffff"));
                    btn_14.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_15:
                if (bool_15) {
                    bool_15 = false;
                    btn_15.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_15.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_15 = true;
                    btn_15.setTextColor(Color.parseColor("#ffffff"));
                    btn_15.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_16:
                if (bool_16) {
                    bool_16 = false;
                    btn_16.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_16.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_16 = true;
                    btn_16.setTextColor(Color.parseColor("#ffffff"));
                    btn_16.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_17:
                if (bool_17) {
                    bool_17 = false;
                    btn_17.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_17.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_17 = true;
                    btn_17.setTextColor(Color.parseColor("#ffffff"));
                    btn_17.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_21:
                if (bool_21) {
                    bool_21 = false;
                    btn_21.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_21.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_21 = true;
                    btn_21.setTextColor(Color.parseColor("#ffffff"));
                    btn_21.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_22:
                if (bool_22) {
                    bool_22 = false;
                    btn_22.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_22.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_22 = true;
                    btn_22.setTextColor(Color.parseColor("#ffffff"));
                    btn_22.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_23:
                if (bool_23) {
                    bool_23 = false;
                    btn_23.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_23.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_23 = true;
                    btn_23.setTextColor(Color.parseColor("#ffffff"));
                    btn_23.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_24:
                if (bool_24) {
                    bool_24 = false;
                    btn_24.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_24.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_24 = true;
                    btn_24.setTextColor(Color.parseColor("#ffffff"));
                    btn_24.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_25:
                if (bool_25) {
                    bool_25 = false;
                    btn_25.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_25.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_25 = true;
                    btn_25.setTextColor(Color.parseColor("#ffffff"));
                    btn_25.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_26:
                if (bool_26) {
                    bool_26 = false;
                    btn_26.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_26.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_26 = true;
                    btn_26.setTextColor(Color.parseColor("#ffffff"));
                    btn_26.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_27:
                if (bool_27) {
                    bool_27 = false;
                    btn_27.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_27.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_27 = true;
                    btn_27.setTextColor(Color.parseColor("#ffffff"));
                    btn_27.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_31:
                if (bool_31) {
                    bool_31 = false;
                    btn_31.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_31.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_31 = true;
                    btn_31.setTextColor(Color.parseColor("#ffffff"));
                    btn_31.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_32:
                if (bool_32) {
                    bool_32 = false;
                    btn_32.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_32.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_32 = true;
                    btn_32.setTextColor(Color.parseColor("#ffffff"));
                    btn_32.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_33:
                if (bool_33) {
                    bool_33 = false;
                    btn_33.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_33.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_33 = true;
                    btn_33.setTextColor(Color.parseColor("#ffffff"));
                    btn_33.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_34:
                if (bool_34) {
                    bool_34 = false;
                    btn_34.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_34.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_34 = true;
                    btn_34.setTextColor(Color.parseColor("#ffffff"));
                    btn_34.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_35:
                if (bool_35) {
                    bool_35 = false;
                    btn_35.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_35.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_35 = true;
                    btn_35.setTextColor(Color.parseColor("#ffffff"));
                    btn_35.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_36:
                if (bool_36) {
                    bool_36 = false;
                    btn_36.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_36.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_36 = true;
                    btn_36.setTextColor(Color.parseColor("#ffffff"));
                    btn_36.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_37:
                if (bool_37) {
                    bool_37 = false;
                    btn_37.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_37.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_37 = true;
                    btn_37.setTextColor(Color.parseColor("#ffffff"));
                    btn_37.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_41:
                if (bool_41) {
                    bool_41 = false;
                    btn_41.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_41.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_41 = true;
                    btn_41.setTextColor(Color.parseColor("#ffffff"));
                    btn_41.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_42:
                if (bool_42) {
                    bool_42 = false;
                    btn_42.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_42.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_42 = true;
                    btn_42.setTextColor(Color.parseColor("#ffffff"));
                    btn_42.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_43:
                if (bool_43) {
                    bool_43 = false;
                    btn_43.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_43.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_43 = true;
                    btn_43.setTextColor(Color.parseColor("#ffffff"));
                    btn_43.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_44:
                if (bool_44) {
                    bool_44 = false;
                    btn_44.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_44.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_44 = true;
                    btn_44.setTextColor(Color.parseColor("#ffffff"));
                    btn_44.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_45:
                if (bool_45) {
                    bool_45 = false;
                    btn_45.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_45.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_45 = true;
                    btn_45.setTextColor(Color.parseColor("#ffffff"));
                    btn_45.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_46:
                if (bool_46) {
                    bool_46 = false;
                    btn_46.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_46.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_46 = true;
                    btn_46.setTextColor(Color.parseColor("#ffffff"));
                    btn_46.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_47:
                if (bool_47) {
                    bool_47 = false;
                    btn_47.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_47.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_47 = true;
                    btn_47.setTextColor(Color.parseColor("#ffffff"));
                    btn_47.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_51:
                if (bool_51) {
                    bool_51 = false;
                    btn_51.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_51.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_51 = true;
                    btn_51.setTextColor(Color.parseColor("#ffffff"));
                    btn_51.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_52:
                if (bool_52) {
                    bool_52 = false;
                    btn_52.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_52.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_52 = true;
                    btn_52.setTextColor(Color.parseColor("#ffffff"));
                    btn_52.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_53:
                if (bool_53) {
                    bool_53 = false;
                    btn_53.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_53.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_53 = true;
                    btn_53.setTextColor(Color.parseColor("#ffffff"));
                    btn_53.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_54:
                if (bool_54) {
                    bool_54 = false;
                    btn_54.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_54.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_54 = true;
                    btn_54.setTextColor(Color.parseColor("#ffffff"));
                    btn_54.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_55:
                if (bool_55) {
                    bool_55 = false;
                    btn_55.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_55.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_55 = true;
                    btn_55.setTextColor(Color.parseColor("#ffffff"));
                    btn_55.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_56:
                if (bool_56) {
                    bool_56 = false;
                    btn_56.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_56.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_56 = true;
                    btn_56.setTextColor(Color.parseColor("#ffffff"));
                    btn_56.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_57:
                if (bool_57) {
                    bool_57 = false;
                    btn_57.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_57.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_57 = true;
                    btn_57.setTextColor(Color.parseColor("#ffffff"));
                    btn_57.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_61:
                if (bool_61) {
                    bool_61 = false;
                    btn_61.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_61.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_61 = true;
                    btn_61.setTextColor(Color.parseColor("#ffffff"));
                    btn_61.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_62:
                if (bool_62) {
                    bool_62 = false;
                    btn_62.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_62.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_62 = true;
                    btn_62.setTextColor(Color.parseColor("#ffffff"));
                    btn_62.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_63:
                if (bool_63) {
                    bool_63 = false;
                    btn_63.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_63.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_63 = true;
                    btn_63.setTextColor(Color.parseColor("#ffffff"));
                    btn_63.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_64:
                if (bool_64) {
                    bool_64 = false;
                    btn_64.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_64.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_64 = true;
                    btn_64.setTextColor(Color.parseColor("#ffffff"));
                    btn_64.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_65:
                if (bool_65) {
                    bool_65 = false;
                    btn_65.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_65.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_65 = true;
                    btn_65.setTextColor(Color.parseColor("#ffffff"));
                    btn_65.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_66:
                if (bool_66) {
                    bool_66 = false;
                    btn_66.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_66.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_66 = true;
                    btn_66.setTextColor(Color.parseColor("#ffffff"));
                    btn_66.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_67:
                if (bool_67) {
                    bool_67 = false;
                    btn_67.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_67.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_67 = true;
                    btn_67.setTextColor(Color.parseColor("#ffffff"));
                    btn_67.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_71:
                if (bool_71) {
                    bool_71 = false;
                    btn_71.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_71.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_71 = true;
                    btn_71.setTextColor(Color.parseColor("#ffffff"));
                    btn_71.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_72:
                if (bool_72) {
                    bool_72 = false;
                    btn_72.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_72.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_72 = true;
                    btn_72.setTextColor(Color.parseColor("#ffffff"));
                    btn_72.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_73:
                if (bool_73) {
                    bool_73 = false;
                    btn_73.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_73.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_73 = true;
                    btn_73.setTextColor(Color.parseColor("#ffffff"));
                    btn_73.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_74:
                if (bool_74) {
                    bool_74 = false;
                    btn_74.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_74.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_74 = true;
                    btn_74.setTextColor(Color.parseColor("#ffffff"));
                    btn_74.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_75:
                if (bool_75) {
                    bool_75 = false;
                    btn_75.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_75.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_75 = true;
                    btn_75.setTextColor(Color.parseColor("#ffffff"));
                    btn_75.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_76:
                if (bool_76) {
                    bool_76 = false;
                    btn_76.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_76.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_76 = true;
                    btn_76.setTextColor(Color.parseColor("#ffffff"));
                    btn_76.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_77:
                if (bool_77) {
                    bool_77 = false;
                    btn_77.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_77.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_77 = true;
                    btn_77.setTextColor(Color.parseColor("#ffffff"));
                    btn_77.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_81:
                if (bool_81) {
                    bool_81 = false;
                    btn_81.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_81.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_81 = true;
                    btn_81.setTextColor(Color.parseColor("#ffffff"));
                    btn_81.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_82:
                if (bool_82) {
                    bool_82 = false;
                    btn_82.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_82.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_82 = true;
                    btn_82.setTextColor(Color.parseColor("#ffffff"));
                    btn_82.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_83:
                if (bool_83) {
                    bool_83 = false;
                    btn_83.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_83.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_83 = true;
                    btn_83.setTextColor(Color.parseColor("#ffffff"));
                    btn_83.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_84:
                if (bool_84) {
                    bool_84 = false;
                    btn_84.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_84.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_84 = true;
                    btn_84.setTextColor(Color.parseColor("#ffffff"));
                    btn_84.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_85:
                if (bool_85) {
                    bool_85 = false;
                    btn_85.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_85.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_85 = true;
                    btn_85.setTextColor(Color.parseColor("#ffffff"));
                    btn_85.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_86:
                if (bool_86) {
                    bool_86 = false;
                    btn_86.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_86.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_86 = true;
                    btn_86.setTextColor(Color.parseColor("#ffffff"));
                    btn_86.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_87:
                if (bool_87) {
                    bool_87 = false;
                    btn_87.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_87.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_87 = true;
                    btn_87.setTextColor(Color.parseColor("#ffffff"));
                    btn_87.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_91:
                if (bool_91) {
                    bool_91 = false;
                    btn_91.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_91.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_91 = true;
                    btn_91.setTextColor(Color.parseColor("#ffffff"));
                    btn_91.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_92:
                if (bool_92) {
                    bool_92 = false;
                    btn_92.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_92.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_92 = true;
                    btn_92.setTextColor(Color.parseColor("#ffffff"));
                    btn_92.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_93:
                if (bool_93) {
                    bool_93 = false;
                    btn_93.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_93.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_93 = true;
                    btn_93.setTextColor(Color.parseColor("#ffffff"));
                    btn_93.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_94:
                if (bool_94) {
                    bool_94 = false;
                    btn_94.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_94.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_94 = true;
                    btn_94.setTextColor(Color.parseColor("#ffffff"));
                    btn_94.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_95:
                if (bool_95) {
                    bool_95 = false;
                    btn_95.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_95.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_95 = true;
                    btn_95.setTextColor(Color.parseColor("#ffffff"));
                    btn_95.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_96:
                if (bool_96) {
                    bool_96 = false;
                    btn_96.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_96.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_96 = true;
                    btn_96.setTextColor(Color.parseColor("#ffffff"));
                    btn_96.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_97:
                if (bool_97) {
                    bool_97 = false;
                    btn_97.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_97.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_97 = true;
                    btn_97.setTextColor(Color.parseColor("#ffffff"));
                    btn_97.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_101:
                if (bool_101) {
                    bool_101 = false;
                    btn_101.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_101.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_101 = true;
                    btn_101.setTextColor(Color.parseColor("#ffffff"));
                    btn_101.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_102:
                if (bool_102) {
                    bool_102 = false;
                    btn_102.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_102.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_102 = true;
                    btn_102.setTextColor(Color.parseColor("#ffffff"));
                    btn_102.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_103:
                if (bool_103) {
                    bool_103 = false;
                    btn_103.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_103.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_103 = true;
                    btn_103.setTextColor(Color.parseColor("#ffffff"));
                    btn_103.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_104:
                if (bool_104) {
                    bool_104 = false;
                    btn_104.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_104.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_104 = true;
                    btn_104.setTextColor(Color.parseColor("#ffffff"));
                    btn_104.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_105:
                if (bool_105) {
                    bool_105 = false;
                    btn_105.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_105.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_105 = true;
                    btn_105.setTextColor(Color.parseColor("#ffffff"));
                    btn_105.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_106:
                if (bool_106) {
                    bool_106 = false;
                    btn_106.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_106.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_106 = true;
                    btn_106.setTextColor(Color.parseColor("#ffffff"));
                    btn_106.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_107:
                if (bool_107) {
                    bool_107 = false;
                    btn_107.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_107.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_107 = true;
                    btn_107.setTextColor(Color.parseColor("#ffffff"));
                    btn_107.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_111:
                if (bool_111) {
                    bool_111 = false;
                    btn_111.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_111.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_111 = true;
                    btn_111.setTextColor(Color.parseColor("#ffffff"));
                    btn_111.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_112:
                if (bool_112) {
                    bool_112 = false;
                    btn_112.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_112.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_112 = true;
                    btn_112.setTextColor(Color.parseColor("#ffffff"));
                    btn_112.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_113:
                if (bool_113) {
                    bool_113 = false;
                    btn_113.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_113.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_113 = true;
                    btn_113.setTextColor(Color.parseColor("#ffffff"));
                    btn_113.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_114:
                if (bool_114) {
                    bool_114 = false;
                    btn_114.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_114.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_114 = true;
                    btn_114.setTextColor(Color.parseColor("#ffffff"));
                    btn_114.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_115:
                if (bool_115) {
                    bool_115 = false;
                    btn_115.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_115.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_115 = true;
                    btn_115.setTextColor(Color.parseColor("#ffffff"));
                    btn_115.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_116:
                if (bool_116) {
                    bool_116 = false;
                    btn_116.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_116.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_116 = true;
                    btn_116.setTextColor(Color.parseColor("#ffffff"));
                    btn_116.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_117:
                if (bool_117) {
                    bool_117 = false;
                    btn_117.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_117.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_117 = true;
                    btn_117.setTextColor(Color.parseColor("#ffffff"));
                    btn_117.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_121:
                if (bool_121) {
                    bool_121 = false;
                    btn_121.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_121.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_121 = true;
                    btn_121.setTextColor(Color.parseColor("#ffffff"));
                    btn_121.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_122:
                if (bool_122) {
                    bool_122 = false;
                    btn_122.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_122.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_122 = true;
                    btn_122.setTextColor(Color.parseColor("#ffffff"));
                    btn_122.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_123:
                if (bool_123) {
                    bool_123 = false;
                    btn_123.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_123.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_123 = true;
                    btn_123.setTextColor(Color.parseColor("#ffffff"));
                    btn_123.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_124:
                if (bool_124) {
                    bool_124 = false;
                    btn_124.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_124.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_124 = true;
                    btn_124.setTextColor(Color.parseColor("#ffffff"));
                    btn_124.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_125:
                if (bool_125) {
                    bool_125 = false;
                    btn_125.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_125.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_125 = true;
                    btn_125.setTextColor(Color.parseColor("#ffffff"));
                    btn_125.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_126:
                if (bool_126) {
                    bool_126 = false;
                    btn_126.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_126.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_126 = true;
                    btn_126.setTextColor(Color.parseColor("#ffffff"));
                    btn_126.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_127:
                if (bool_127) {
                    bool_127 = false;
                    btn_127.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_127.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_127 = true;
                    btn_127.setTextColor(Color.parseColor("#ffffff"));
                    btn_127.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_131:
                if (bool_131) {
                    bool_131 = false;
                    btn_131.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_131.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_131 = true;
                    btn_131.setTextColor(Color.parseColor("#ffffff"));
                    btn_131.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_132:
                if (bool_132) {
                    bool_132 = false;
                    btn_132.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_132.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_132 = true;
                    btn_132.setTextColor(Color.parseColor("#ffffff"));
                    btn_132.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_133:
                if (bool_133) {
                    bool_133 = false;
                    btn_133.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_133.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_133 = true;
                    btn_133.setTextColor(Color.parseColor("#ffffff"));
                    btn_133.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_134:
                if (bool_134) {
                    bool_134 = false;
                    btn_134.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_134.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_134 = true;
                    btn_134.setTextColor(Color.parseColor("#ffffff"));
                    btn_134.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_135:
                if (bool_135) {
                    bool_135 = false;
                    btn_135.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_135.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_135 = true;
                    btn_135.setTextColor(Color.parseColor("#ffffff"));
                    btn_135.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_136:
                if (bool_136) {
                    bool_136 = false;
                    btn_136.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_136.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_136 = true;
                    btn_136.setTextColor(Color.parseColor("#ffffff"));
                    btn_136.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_137:
                if (bool_137) {
                    bool_137 = false;
                    btn_137.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_137.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_137 = true;
                    btn_137.setTextColor(Color.parseColor("#ffffff"));
                    btn_137.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_141:
                if (bool_141) {
                    bool_141 = false;
                    btn_141.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_141.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_141 = true;
                    btn_141.setTextColor(Color.parseColor("#ffffff"));
                    btn_141.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_142:
                if (bool_142) {
                    bool_142 = false;
                    btn_142.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_142.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_142 = true;
                    btn_142.setTextColor(Color.parseColor("#ffffff"));
                    btn_142.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_143:
                if (bool_143) {
                    bool_143 = false;
                    btn_143.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_143.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_143 = true;
                    btn_143.setTextColor(Color.parseColor("#ffffff"));
                    btn_143.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_144:
                if (bool_144) {
                    bool_144 = false;
                    btn_144.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_144.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_144 = true;
                    btn_144.setTextColor(Color.parseColor("#ffffff"));
                    btn_144.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_145:
                if (bool_145) {
                    bool_145 = false;
                    btn_145.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_145.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_145 = true;
                    btn_145.setTextColor(Color.parseColor("#ffffff"));
                    btn_145.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_146:
                if (bool_146) {
                    bool_146 = false;
                    btn_146.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_146.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_146 = true;
                    btn_146.setTextColor(Color.parseColor("#ffffff"));
                    btn_146.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_147:
                if (bool_147) {
                    bool_147 = false;
                    btn_147.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_147.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_147 = true;
                    btn_147.setTextColor(Color.parseColor("#ffffff"));
                    btn_147.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_151:
                if (bool_151) {
                    bool_151 = false;
                    btn_151.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_151.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_151 = true;
                    btn_151.setTextColor(Color.parseColor("#ffffff"));
                    btn_151.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_152:
                if (bool_152) {
                    bool_152 = false;
                    btn_152.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_152.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_152 = true;
                    btn_152.setTextColor(Color.parseColor("#ffffff"));
                    btn_152.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_153:
                if (bool_153) {
                    bool_153 = false;
                    btn_153.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_153.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_153 = true;
                    btn_153.setTextColor(Color.parseColor("#ffffff"));
                    btn_153.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_154:
                if (bool_154) {
                    bool_154 = false;
                    btn_154.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_154.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_154 = true;
                    btn_154.setTextColor(Color.parseColor("#ffffff"));
                    btn_154.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_155:
                if (bool_155) {
                    bool_155 = false;
                    btn_155.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_155.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_155 = true;
                    btn_155.setTextColor(Color.parseColor("#ffffff"));
                    btn_155.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_156:
                if (bool_156) {
                    bool_156 = false;
                    btn_156.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_156.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_156 = true;
                    btn_156.setTextColor(Color.parseColor("#ffffff"));
                    btn_156.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_157:
                if (bool_157) {
                    bool_157 = false;
                    btn_157.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_157.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_157 = true;
                    btn_157.setTextColor(Color.parseColor("#ffffff"));
                    btn_157.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_161:
                if (bool_161) {
                    bool_161 = false;
                    btn_161.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_161.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_161 = true;
                    btn_161.setTextColor(Color.parseColor("#ffffff"));
                    btn_161.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_162:
                if (bool_162) {
                    bool_162 = false;
                    btn_162.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_162.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_162 = true;
                    btn_162.setTextColor(Color.parseColor("#ffffff"));
                    btn_162.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_163:
                if (bool_163) {
                    bool_163 = false;
                    btn_163.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_163.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_163 = true;
                    btn_163.setTextColor(Color.parseColor("#ffffff"));
                    btn_163.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_164:
                if (bool_164) {
                    bool_164 = false;
                    btn_164.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_164.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_164 = true;
                    btn_164.setTextColor(Color.parseColor("#ffffff"));
                    btn_164.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_165:
                if (bool_165) {
                    bool_165 = false;
                    btn_165.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_165.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_165 = true;
                    btn_165.setTextColor(Color.parseColor("#ffffff"));
                    btn_165.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_166:
                if (bool_166) {
                    bool_166 = false;
                    btn_166.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_166.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_166 = true;
                    btn_166.setTextColor(Color.parseColor("#ffffff"));
                    btn_166.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_167:
                if (bool_167) {
                    bool_167 = false;
                    btn_167.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_167.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_167 = true;
                    btn_167.setTextColor(Color.parseColor("#ffffff"));
                    btn_167.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_171:
                if (bool_171) {
                    bool_171 = false;
                    btn_171.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_171.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_171 = true;
                    btn_171.setTextColor(Color.parseColor("#ffffff"));
                    btn_171.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_172:
                if (bool_172) {
                    bool_172 = false;
                    btn_172.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_172.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_172 = true;
                    btn_172.setTextColor(Color.parseColor("#ffffff"));
                    btn_172.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_173:
                if (bool_173) {
                    bool_173 = false;
                    btn_173.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_173.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_173 = true;
                    btn_173.setTextColor(Color.parseColor("#ffffff"));
                    btn_173.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_174:
                if (bool_174) {
                    bool_174 = false;
                    btn_174.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_174.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_174 = true;
                    btn_174.setTextColor(Color.parseColor("#ffffff"));
                    btn_174.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_175:
                if (bool_175) {
                    bool_175 = false;
                    btn_175.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_175.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_175 = true;
                    btn_175.setTextColor(Color.parseColor("#ffffff"));
                    btn_175.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_176:
                if (bool_176) {
                    bool_176 = false;
                    btn_176.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_176.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_176 = true;
                    btn_176.setTextColor(Color.parseColor("#ffffff"));
                    btn_176.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_177:
                if (bool_177) {
                    bool_177 = false;
                    btn_177.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_177.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_177 = true;
                    btn_177.setTextColor(Color.parseColor("#ffffff"));
                    btn_177.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_181:
                if (bool_181) {
                    bool_181 = false;
                    btn_181.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_181.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_181 = true;
                    btn_181.setTextColor(Color.parseColor("#ffffff"));
                    btn_181.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_182:
                if (bool_182) {
                    bool_182 = false;
                    btn_182.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_182.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_182 = true;
                    btn_182.setTextColor(Color.parseColor("#ffffff"));
                    btn_182.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_183:
                if (bool_183) {
                    bool_183 = false;
                    btn_183.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_183.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_183 = true;
                    btn_183.setTextColor(Color.parseColor("#ffffff"));
                    btn_183.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_184:
                if (bool_184) {
                    bool_184 = false;
                    btn_184.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_184.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_184 = true;
                    btn_184.setTextColor(Color.parseColor("#ffffff"));
                    btn_184.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_185:
                if (bool_185) {
                    bool_185 = false;
                    btn_185.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_185.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_185 = true;
                    btn_185.setTextColor(Color.parseColor("#ffffff"));
                    btn_185.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_186:
                if (bool_186) {
                    bool_186 = false;
                    btn_186.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_186.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_186 = true;
                    btn_186.setTextColor(Color.parseColor("#ffffff"));
                    btn_186.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_187:
                if (bool_187) {
                    bool_187 = false;
                    btn_187.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_187.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_187 = true;
                    btn_187.setTextColor(Color.parseColor("#ffffff"));
                    btn_187.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_191:
                if (bool_191) {
                    bool_191 = false;
                    btn_191.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_191.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_191 = true;
                    btn_191.setTextColor(Color.parseColor("#ffffff"));
                    btn_191.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_192:
                if (bool_192) {
                    bool_192 = false;
                    btn_192.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_192.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_192 = true;
                    btn_192.setTextColor(Color.parseColor("#ffffff"));
                    btn_192.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_193:
                if (bool_193) {
                    bool_193 = false;
                    btn_193.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_193.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_193 = true;
                    btn_193.setTextColor(Color.parseColor("#ffffff"));
                    btn_193.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_194:
                if (bool_194) {
                    bool_194 = false;
                    btn_194.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_194.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_194 = true;
                    btn_194.setTextColor(Color.parseColor("#ffffff"));
                    btn_194.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_195:
                if (bool_195) {
                    bool_195 = false;
                    btn_195.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_195.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_195 = true;
                    btn_195.setTextColor(Color.parseColor("#ffffff"));
                    btn_195.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_196:
                if (bool_196) {
                    bool_196 = false;
                    btn_196.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_196.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_196 = true;
                    btn_196.setTextColor(Color.parseColor("#ffffff"));
                    btn_196.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_197:
                if (bool_197) {
                    bool_197 = false;
                    btn_197.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_197.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_197 = true;
                    btn_197.setTextColor(Color.parseColor("#ffffff"));
                    btn_197.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_201:
                if (bool_201) {
                    bool_201 = false;
                    btn_201.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_201.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_201 = true;
                    btn_201.setTextColor(Color.parseColor("#ffffff"));
                    btn_201.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_202:
                if (bool_202) {
                    bool_202 = false;
                    btn_202.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_202.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_202 = true;
                    btn_202.setTextColor(Color.parseColor("#ffffff"));
                    btn_202.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_203:
                if (bool_203) {
                    bool_203 = false;
                    btn_203.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_203.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_203 = true;
                    btn_203.setTextColor(Color.parseColor("#ffffff"));
                    btn_203.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_204:
                if (bool_204) {
                    bool_204 = false;
                    btn_204.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_204.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_204 = true;
                    btn_204.setTextColor(Color.parseColor("#ffffff"));
                    btn_204.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_205:
                if (bool_205) {
                    bool_205 = false;
                    btn_205.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_205.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_205 = true;
                    btn_205.setTextColor(Color.parseColor("#ffffff"));
                    btn_205.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_206:
                if (bool_206) {
                    bool_206 = false;
                    btn_206.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_206.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_206 = true;
                    btn_206.setTextColor(Color.parseColor("#ffffff"));
                    btn_206.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_207:
                if (bool_207) {
                    bool_207 = false;
                    btn_207.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_207.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_207 = true;
                    btn_207.setTextColor(Color.parseColor("#ffffff"));
                    btn_207.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_211:
                if (bool_211) {
                    bool_211 = false;
                    btn_211.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_211.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_211 = true;
                    btn_211.setTextColor(Color.parseColor("#ffffff"));
                    btn_211.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_212:
                if (bool_212) {
                    bool_212 = false;
                    btn_212.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_212.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_212 = true;
                    btn_212.setTextColor(Color.parseColor("#ffffff"));
                    btn_212.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_213:
                if (bool_213) {
                    bool_213 = false;
                    btn_213.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_213.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_213 = true;
                    btn_213.setTextColor(Color.parseColor("#ffffff"));
                    btn_213.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_214:
                if (bool_214) {
                    bool_214 = false;
                    btn_214.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_214.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_214 = true;
                    btn_214.setTextColor(Color.parseColor("#ffffff"));
                    btn_214.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_215:
                if (bool_215) {
                    bool_215 = false;
                    btn_215.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_215.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_215 = true;
                    btn_215.setTextColor(Color.parseColor("#ffffff"));
                    btn_215.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.tv_add_crowd:
                JSONObject json = new JSONObject();
                JSONArray jsonArray_1 = new JSONArray();
                JSONArray jsonArray_2 = new JSONArray();
                JSONArray jsonArray_3 = new JSONArray();
                JSONArray jsonArray_4 = new JSONArray();
                JSONArray jsonArray_5 = new JSONArray();
                JSONArray jsonArray_6 = new JSONArray();
                JSONArray jsonArray_7 = new JSONArray();
                //星期1到7，周一是2，类推，周日是1
                if (bool_11) {
                    jsonArray_1.put("00");
                }
                if (bool_21) {
                    jsonArray_1.put("01");
                }
                if (bool_31) {
                    jsonArray_1.put("02");
                }
                if (bool_41) {
                    jsonArray_1.put("03");
                }
                if (bool_51) {
                    jsonArray_1.put("04");
                }
                if (bool_61) {
                    jsonArray_1.put("05");
                }
                if (bool_71) {
                    jsonArray_1.put("06");
                }
                if (bool_81) {
                    jsonArray_1.put("07");
                }
                if (bool_91) {
                    jsonArray_1.put("08");
                }
                if (bool_101) {
                    jsonArray_1.put("09");
                }
                if (bool_111) {
                    jsonArray_1.put("10");
                }
                if (bool_121) {
                    jsonArray_1.put("11");
                }
                if (bool_131) {
                    jsonArray_1.put("12");
                }
                if (bool_141) {
                    jsonArray_1.put("13");
                }
                if (bool_151) {
                    jsonArray_1.put("14");
                }
                if (bool_161) {
                    jsonArray_1.put("15");
                }
                if (bool_171) {
                    jsonArray_1.put("16");
                }
                if (bool_181) {
                    jsonArray_1.put("17");
                }
                if (bool_191) {
                    jsonArray_1.put("18");
                }
                if (bool_201) {
                    jsonArray_1.put("19");
                }
                if (bool_211) {
                    jsonArray_1.put("20");
                }
                if (bool_221) {
                    jsonArray_1.put("21");
                }
                if (bool_231) {
                    jsonArray_1.put("22");
                }
                if (bool_241) {
                    jsonArray_1.put("23");
                }
                try {
                    json.put("2", jsonArray_1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (bool_12) {
                    jsonArray_2.put("00");
                }
                if (bool_22) {
                    jsonArray_2.put("01");
                }
                if (bool_32) {
                    jsonArray_2.put("02");
                }
                if (bool_42) {
                    jsonArray_2.put("03");
                }
                if (bool_52) {
                    jsonArray_2.put("04");
                }
                if (bool_62) {
                    jsonArray_2.put("05");
                }
                if (bool_72) {
                    jsonArray_2.put("06");
                }
                if (bool_82) {
                    jsonArray_2.put("07");
                }
                if (bool_92) {
                    jsonArray_2.put("08");
                }
                if (bool_102) {
                    jsonArray_2.put("09");
                }
                if (bool_112) {
                    jsonArray_2.put("10");
                }
                if (bool_122) {
                    jsonArray_2.put("11");
                }
                if (bool_132) {
                    jsonArray_2.put("12");
                }
                if (bool_142) {
                    jsonArray_2.put("13");
                }
                if (bool_152) {
                    jsonArray_2.put("14");
                }
                if (bool_162) {
                    jsonArray_2.put("15");
                }
                if (bool_172) {
                    jsonArray_2.put("16");
                }
                if (bool_182) {
                    jsonArray_2.put("17");
                }
                if (bool_192) {
                    jsonArray_2.put("18");
                }
                if (bool_202) {
                    jsonArray_2.put("19");
                }
                if (bool_212) {
                    jsonArray_2.put("20");
                }
                if (bool_222) {
                    jsonArray_2.put("21");
                }
                if (bool_232) {
                    jsonArray_2.put("22");
                }
                if (bool_242) {
                    jsonArray_2.put("23");
                }
                try {
                    json.put("3", jsonArray_2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (bool_13) {
                    jsonArray_3.put("00");
                }
                if (bool_23) {
                    jsonArray_3.put("01");
                }
                if (bool_33) {
                    jsonArray_3.put("02");
                }
                if (bool_43) {
                    jsonArray_3.put("03");
                }
                if (bool_53) {
                    jsonArray_3.put("04");
                }
                if (bool_63) {
                    jsonArray_3.put("05");
                }
                if (bool_73) {
                    jsonArray_3.put("06");
                }
                if (bool_83) {
                    jsonArray_3.put("07");
                }
                if (bool_93) {
                    jsonArray_3.put("08");
                }
                if (bool_103) {
                    jsonArray_3.put("09");
                }
                if (bool_113) {
                    jsonArray_3.put("10");
                }
                if (bool_123) {
                    jsonArray_3.put("11");
                }
                if (bool_133) {
                    jsonArray_3.put("12");
                }
                if (bool_143) {
                    jsonArray_3.put("13");
                }
                if (bool_153) {
                    jsonArray_3.put("14");
                }
                if (bool_163) {
                    jsonArray_3.put("15");
                }
                if (bool_173) {
                    jsonArray_3.put("16");
                }
                if (bool_183) {
                    jsonArray_3.put("17");
                }
                if (bool_193) {
                    jsonArray_3.put("18");
                }
                if (bool_203) {
                    jsonArray_3.put("19");
                }
                if (bool_213) {
                    jsonArray_3.put("20");
                }
                if (bool_223) {
                    jsonArray_3.put("21");
                }
                if (bool_233) {
                    jsonArray_3.put("22");
                }
                if (bool_243) {
                    jsonArray_3.put("23");
                }
                try {
                    json.put("4", jsonArray_3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (bool_14) {
                    jsonArray_4.put("00");
                }
                if (bool_24) {
                    jsonArray_4.put("01");
                }
                if (bool_34) {
                    jsonArray_4.put("02");
                }
                if (bool_44) {
                    jsonArray_4.put("03");
                }
                if (bool_54) {
                    jsonArray_4.put("04");
                }
                if (bool_64) {
                    jsonArray_4.put("05");
                }
                if (bool_74) {
                    jsonArray_4.put("06");
                }
                if (bool_84) {
                    jsonArray_4.put("07");
                }
                if (bool_94) {
                    jsonArray_4.put("08");
                }
                if (bool_104) {
                    jsonArray_4.put("09");
                }
                if (bool_114) {
                    jsonArray_4.put("10");
                }
                if (bool_124) {
                    jsonArray_4.put("11");
                }
                if (bool_134) {
                    jsonArray_4.put("12");
                }
                if (bool_144) {
                    jsonArray_4.put("13");
                }
                if (bool_154) {
                    jsonArray_4.put("14");
                }
                if (bool_164) {
                    jsonArray_4.put("15");
                }
                if (bool_174) {
                    jsonArray_4.put("16");
                }
                if (bool_184) {
                    jsonArray_4.put("17");
                }
                if (bool_194) {
                    jsonArray_4.put("18");
                }
                if (bool_204) {
                    jsonArray_4.put("19");
                }
                if (bool_214) {
                    jsonArray_4.put("20");
                }
                if (bool_224) {
                    jsonArray_4.put("21");
                }
                if (bool_234) {
                    jsonArray_4.put("22");
                }
                if (bool_244) {
                    jsonArray_4.put("23");
                }
                try {
                    json.put("5", jsonArray_4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (bool_15) {
                    jsonArray_5.put("00");
                }
                if (bool_25) {
                    jsonArray_5.put("01");
                }
                if (bool_35) {
                    jsonArray_5.put("02");
                }
                if (bool_45) {
                    jsonArray_5.put("03");
                }
                if (bool_55) {
                    jsonArray_5.put("04");
                }
                if (bool_65) {
                    jsonArray_5.put("05");
                }
                if (bool_75) {
                    jsonArray_5.put("06");
                }
                if (bool_85) {
                    jsonArray_5.put("07");
                }
                if (bool_95) {
                    jsonArray_5.put("08");
                }
                if (bool_105) {
                    jsonArray_5.put("09");
                }
                if (bool_115) {
                    jsonArray_5.put("10");
                }
                if (bool_125) {
                    jsonArray_5.put("11");
                }
                if (bool_135) {
                    jsonArray_5.put("12");
                }
                if (bool_145) {
                    jsonArray_5.put("13");
                }
                if (bool_155) {
                    jsonArray_5.put("14");
                }
                if (bool_165) {
                    jsonArray_5.put("15");
                }
                if (bool_175) {
                    jsonArray_5.put("16");
                }
                if (bool_185) {
                    jsonArray_5.put("17");
                }
                if (bool_195) {
                    jsonArray_5.put("18");
                }
                if (bool_205) {
                    jsonArray_5.put("19");
                }
                if (bool_215) {
                    jsonArray_5.put("20");
                }
                if (bool_225) {
                    jsonArray_5.put("21");
                }
                if (bool_235) {
                    jsonArray_5.put("22");
                }
                if (bool_245) {
                    jsonArray_5.put("23");
                }
                try {
                    json.put("6", jsonArray_5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (bool_16) {
                    jsonArray_6.put("00");
                }
                if (bool_26) {
                    jsonArray_6.put("01");
                }
                if (bool_36) {
                    jsonArray_6.put("02");
                }
                if (bool_46) {
                    jsonArray_6.put("03");
                }
                if (bool_56) {
                    jsonArray_6.put("04");
                }
                if (bool_66) {
                    jsonArray_6.put("05");
                }
                if (bool_76) {
                    jsonArray_6.put("06");
                }
                if (bool_86) {
                    jsonArray_6.put("07");
                }
                if (bool_96) {
                    jsonArray_6.put("08");
                }
                if (bool_106) {
                    jsonArray_6.put("09");
                }
                if (bool_116) {
                    jsonArray_6.put("10");
                }
                if (bool_126) {
                    jsonArray_6.put("11");
                }
                if (bool_136) {
                    jsonArray_6.put("12");
                }
                if (bool_146) {
                    jsonArray_6.put("13");
                }
                if (bool_156) {
                    jsonArray_6.put("14");
                }
                if (bool_166) {
                    jsonArray_6.put("15");
                }
                if (bool_176) {
                    jsonArray_6.put("16");
                }
                if (bool_186) {
                    jsonArray_6.put("17");
                }
                if (bool_196) {
                    jsonArray_6.put("18");
                }
                if (bool_206) {
                    jsonArray_6.put("19");
                }
                if (bool_216) {
                    jsonArray_6.put("20");
                }
                if (bool_226) {
                    jsonArray_6.put("21");
                }
                if (bool_236) {
                    jsonArray_6.put("22");
                }
                if (bool_246) {
                    jsonArray_6.put("23");
                }
                try {
                    json.put("7", jsonArray_6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (bool_17) {
                    jsonArray_7.put("00");
                }
                if (bool_27) {
                    jsonArray_7.put("01");
                }
                if (bool_37) {
                    jsonArray_7.put("02");
                }
                if (bool_47) {
                    jsonArray_7.put("03");
                }
                if (bool_57) {
                    jsonArray_7.put("04");
                }
                if (bool_67) {
                    jsonArray_7.put("05");
                }
                if (bool_77) {
                    jsonArray_7.put("06");
                }
                if (bool_87) {
                    jsonArray_7.put("07");
                }
                if (bool_97) {
                    jsonArray_7.put("08");
                }
                if (bool_107) {
                    jsonArray_7.put("09");
                }
                if (bool_117) {
                    jsonArray_7.put("10");
                }
                if (bool_127) {
                    jsonArray_7.put("11");
                }
                if (bool_137) {
                    jsonArray_7.put("12");
                }
                if (bool_147) {
                    jsonArray_7.put("13");
                }
                if (bool_157) {
                    jsonArray_7.put("14");
                }
                if (bool_167) {
                    jsonArray_7.put("15");
                }
                if (bool_177) {
                    jsonArray_7.put("16");
                }
                if (bool_187) {
                    jsonArray_7.put("17");
                }
                if (bool_197) {
                    jsonArray_7.put("18");
                }
                if (bool_207) {
                    jsonArray_7.put("19");
                }
                if (bool_217) {
                    jsonArray_7.put("20");
                }
                if (bool_227) {
                    jsonArray_7.put("21");
                }
                if (bool_237) {
                    jsonArray_7.put("22");
                }
                if (bool_247) {
                    jsonArray_7.put("23");
                }
                try {
                    json.put("1", jsonArray_7);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.putExtra("trend", json.toString());
                setResult(FLAG, intent);
                finish();
                break;
            case R.id.btn_216:
                if (bool_216) {
                    bool_216 = false;
                    btn_216.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_216.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_216 = true;
                    btn_216.setTextColor(Color.parseColor("#ffffff"));
                    btn_216.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_217:
                if (bool_217) {
                    bool_217 = false;
                    btn_217.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_217.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_217 = true;
                    btn_217.setTextColor(Color.parseColor("#ffffff"));
                    btn_217.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_221:
                if (bool_221) {
                    bool_221 = false;
                    btn_221.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_221.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_221 = true;
                    btn_221.setTextColor(Color.parseColor("#ffffff"));
                    btn_221.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_222:
                if (bool_222) {
                    bool_222 = false;
                    btn_222.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_222.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_222 = true;
                    btn_222.setTextColor(Color.parseColor("#ffffff"));
                    btn_222.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_223:
                if (bool_223) {
                    bool_223 = false;
                    btn_223.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_223.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_223 = true;
                    btn_223.setTextColor(Color.parseColor("#ffffff"));
                    btn_223.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_224:
                if (bool_224) {
                    bool_224 = false;
                    btn_224.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_224.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_224 = true;
                    btn_224.setTextColor(Color.parseColor("#ffffff"));
                    btn_224.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_225:
                if (bool_225) {
                    bool_225 = false;
                    btn_225.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_225.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_225 = true;
                    btn_225.setTextColor(Color.parseColor("#ffffff"));
                    btn_225.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_226:
                if (bool_226) {
                    bool_226 = false;
                    btn_226.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_226.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_226 = true;
                    btn_226.setTextColor(Color.parseColor("#ffffff"));
                    btn_226.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_227:
                if (bool_227) {
                    bool_227 = false;
                    btn_227.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_227.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_227 = true;
                    btn_227.setTextColor(Color.parseColor("#ffffff"));
                    btn_227.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_231:
                if (bool_231) {
                    bool_231 = false;
                    btn_231.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_231.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_231 = true;
                    btn_231.setTextColor(Color.parseColor("#ffffff"));
                    btn_231.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_232:
                if (bool_232) {
                    bool_232 = false;
                    btn_232.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_232.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_232 = true;
                    btn_232.setTextColor(Color.parseColor("#ffffff"));
                    btn_232.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_233:
                if (bool_233) {
                    bool_233 = false;
                    btn_233.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_233.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_233 = true;
                    btn_233.setTextColor(Color.parseColor("#ffffff"));
                    btn_233.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_234:
                if (bool_234) {
                    bool_234 = false;
                    btn_234.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_234.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_234 = true;
                    btn_234.setTextColor(Color.parseColor("#ffffff"));
                    btn_234.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_235:
                if (bool_235) {
                    bool_235 = false;
                    btn_235.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_235.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_235 = true;
                    btn_235.setTextColor(Color.parseColor("#ffffff"));
                    btn_235.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_236:
                if (bool_236) {
                    bool_236 = false;
                    btn_236.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_236.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_236 = true;
                    btn_236.setTextColor(Color.parseColor("#ffffff"));
                    btn_236.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_237:
                if (bool_237) {
                    bool_237 = false;
                    btn_237.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_237.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_237 = true;
                    btn_237.setTextColor(Color.parseColor("#ffffff"));
                    btn_237.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_241:
                if (bool_241) {
                    bool_241 = false;
                    btn_241.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_241.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_241 = true;
                    btn_241.setTextColor(Color.parseColor("#ffffff"));
                    btn_241.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_242:
                if (bool_242) {
                    bool_242 = false;
                    btn_242.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_242.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_242 = true;
                    btn_242.setTextColor(Color.parseColor("#ffffff"));
                    btn_242.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_243:
                if (bool_243) {
                    bool_243 = false;
                    btn_243.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_243.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_243 = true;
                    btn_243.setTextColor(Color.parseColor("#ffffff"));
                    btn_243.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_244:
                if (bool_244) {
                    bool_244 = false;
                    btn_244.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_244.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_244 = true;
                    btn_244.setTextColor(Color.parseColor("#ffffff"));
                    btn_244.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_245:
                if (bool_245) {
                    bool_245 = false;
                    btn_245.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_245.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_245 = true;
                    btn_245.setTextColor(Color.parseColor("#ffffff"));
                    btn_245.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_246:
                if (bool_246) {
                    bool_246 = false;
                    btn_246.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_246.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_246 = true;
                    btn_246.setTextColor(Color.parseColor("#ffffff"));
                    btn_246.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
            case R.id.btn_247:
                if (bool_247) {
                    bool_247 = false;
                    btn_247.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
                    btn_247.setTextColor(Color.parseColor("#555555"));
                } else {
                    bool_247 = true;
                    btn_247.setTextColor(Color.parseColor("#ffffff"));
                    btn_247.setBackgroundColor(Color.parseColor("#2a7ccf"));
                }
                break;
        }
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        //果冻弹跳效果
        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setSlidingOffset(0.2f);

        //计算屏幕的宽度
        WindowManager wm1 = this.getWindowManager();
        screenWidth = wm1.getDefaultDisplay().getWidth();
        screenHeight = wm1.getDefaultDisplay().getHeight();

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.mHorizontalScrollView);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_add_crowd).setOnClickListener(this);
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_all.setOnClickListener(this);
        btn_working_days = (Button) findViewById(R.id.btn_working_days);
        btn_working_days.setOnClickListener(this);
        btn_weekend = (Button) findViewById(R.id.btn_weekend);
        btn_weekend.setOnClickListener(this);
        tv_9_12 = (TextView) findViewById(R.id.tv_9_12);
        tv_9_12.setOnClickListener(this);
        tv_13_16 = (TextView) findViewById(R.id.tv_13_16);
        tv_13_16.setOnClickListener(this);
        tv_17_20 = (TextView) findViewById(R.id.tv_17_20);
        tv_17_20.setOnClickListener(this);
        tv_21_00 = (TextView) findViewById(R.id.tv_21_00);
        tv_21_00.setOnClickListener(this);
        tv_01_04 = (TextView) findViewById(R.id.tv_01_04);
        tv_01_04.setOnClickListener(this);
        tv_05_08 = (TextView) findViewById(R.id.tv_05_08);
        tv_05_08.setOnClickListener(this);

        btn_11 = (Button) findViewById(R.id.btn_11);
        btn_12 = (Button) findViewById(R.id.btn_12);
        btn_13 = (Button) findViewById(R.id.btn_13);
        btn_14 = (Button) findViewById(R.id.btn_14);
        btn_15 = (Button) findViewById(R.id.btn_15);
        btn_16 = (Button) findViewById(R.id.btn_16);
        btn_17 = (Button) findViewById(R.id.btn_17);
        btn_21 = (Button) findViewById(R.id.btn_21);
        btn_22 = (Button) findViewById(R.id.btn_22);
        btn_23 = (Button) findViewById(R.id.btn_23);
        btn_24 = (Button) findViewById(R.id.btn_24);
        btn_25 = (Button) findViewById(R.id.btn_25);
        btn_26 = (Button) findViewById(R.id.btn_26);
        btn_27 = (Button) findViewById(R.id.btn_27);
        btn_31 = (Button) findViewById(R.id.btn_31);
        btn_32 = (Button) findViewById(R.id.btn_32);
        btn_33 = (Button) findViewById(R.id.btn_33);
        btn_34 = (Button) findViewById(R.id.btn_34);
        btn_35 = (Button) findViewById(R.id.btn_35);
        btn_36 = (Button) findViewById(R.id.btn_36);
        btn_37 = (Button) findViewById(R.id.btn_37);
        btn_41 = (Button) findViewById(R.id.btn_41);
        btn_42 = (Button) findViewById(R.id.btn_42);
        btn_43 = (Button) findViewById(R.id.btn_43);
        btn_44 = (Button) findViewById(R.id.btn_44);
        btn_45 = (Button) findViewById(R.id.btn_45);
        btn_46 = (Button) findViewById(R.id.btn_46);
        btn_47 = (Button) findViewById(R.id.btn_47);
        btn_51 = (Button) findViewById(R.id.btn_51);
        btn_52 = (Button) findViewById(R.id.btn_52);
        btn_53 = (Button) findViewById(R.id.btn_53);
        btn_54 = (Button) findViewById(R.id.btn_54);
        btn_55 = (Button) findViewById(R.id.btn_55);
        btn_56 = (Button) findViewById(R.id.btn_56);
        btn_57 = (Button) findViewById(R.id.btn_57);
        btn_61 = (Button) findViewById(R.id.btn_61);
        btn_62 = (Button) findViewById(R.id.btn_62);
        btn_63 = (Button) findViewById(R.id.btn_63);
        btn_64 = (Button) findViewById(R.id.btn_64);
        btn_65 = (Button) findViewById(R.id.btn_65);
        btn_66 = (Button) findViewById(R.id.btn_66);
        btn_67 = (Button) findViewById(R.id.btn_67);
        btn_71 = (Button) findViewById(R.id.btn_71);
        btn_72 = (Button) findViewById(R.id.btn_72);
        btn_73 = (Button) findViewById(R.id.btn_73);
        btn_74 = (Button) findViewById(R.id.btn_74);
        btn_75 = (Button) findViewById(R.id.btn_75);
        btn_76 = (Button) findViewById(R.id.btn_76);
        btn_77 = (Button) findViewById(R.id.btn_77);
        btn_81 = (Button) findViewById(R.id.btn_81);
        btn_82 = (Button) findViewById(R.id.btn_82);
        btn_83 = (Button) findViewById(R.id.btn_83);
        btn_84 = (Button) findViewById(R.id.btn_84);
        btn_85 = (Button) findViewById(R.id.btn_85);
        btn_86 = (Button) findViewById(R.id.btn_86);
        btn_87 = (Button) findViewById(R.id.btn_87);
        btn_91 = (Button) findViewById(R.id.btn_91);
        btn_92 = (Button) findViewById(R.id.btn_92);
        btn_93 = (Button) findViewById(R.id.btn_93);
        btn_94 = (Button) findViewById(R.id.btn_94);
        btn_95 = (Button) findViewById(R.id.btn_95);
        btn_96 = (Button) findViewById(R.id.btn_96);
        btn_97 = (Button) findViewById(R.id.btn_97);
        btn_101 = (Button) findViewById(R.id.btn_101);
        btn_102 = (Button) findViewById(R.id.btn_102);
        btn_103 = (Button) findViewById(R.id.btn_103);
        btn_104 = (Button) findViewById(R.id.btn_104);
        btn_105 = (Button) findViewById(R.id.btn_105);
        btn_106 = (Button) findViewById(R.id.btn_106);
        btn_107 = (Button) findViewById(R.id.btn_107);
        btn_111 = (Button) findViewById(R.id.btn_111);
        btn_112 = (Button) findViewById(R.id.btn_112);
        btn_113 = (Button) findViewById(R.id.btn_113);
        btn_114 = (Button) findViewById(R.id.btn_114);
        btn_115 = (Button) findViewById(R.id.btn_115);
        btn_116 = (Button) findViewById(R.id.btn_116);
        btn_117 = (Button) findViewById(R.id.btn_117);
        btn_121 = (Button) findViewById(R.id.btn_121);
        btn_122 = (Button) findViewById(R.id.btn_122);
        btn_123 = (Button) findViewById(R.id.btn_123);
        btn_124 = (Button) findViewById(R.id.btn_124);
        btn_125 = (Button) findViewById(R.id.btn_125);
        btn_126 = (Button) findViewById(R.id.btn_126);
        btn_127 = (Button) findViewById(R.id.btn_127);
        btn_131 = (Button) findViewById(R.id.btn_131);
        btn_132 = (Button) findViewById(R.id.btn_132);
        btn_133 = (Button) findViewById(R.id.btn_133);
        btn_134 = (Button) findViewById(R.id.btn_134);
        btn_135 = (Button) findViewById(R.id.btn_135);
        btn_136 = (Button) findViewById(R.id.btn_136);
        btn_137 = (Button) findViewById(R.id.btn_137);
        btn_141 = (Button) findViewById(R.id.btn_141);
        btn_142 = (Button) findViewById(R.id.btn_142);
        btn_143 = (Button) findViewById(R.id.btn_143);
        btn_144 = (Button) findViewById(R.id.btn_144);
        btn_145 = (Button) findViewById(R.id.btn_145);
        btn_146 = (Button) findViewById(R.id.btn_146);
        btn_147 = (Button) findViewById(R.id.btn_147);
        btn_151 = (Button) findViewById(R.id.btn_151);
        btn_152 = (Button) findViewById(R.id.btn_152);
        btn_153 = (Button) findViewById(R.id.btn_153);
        btn_154 = (Button) findViewById(R.id.btn_154);
        btn_155 = (Button) findViewById(R.id.btn_155);
        btn_156 = (Button) findViewById(R.id.btn_156);
        btn_157 = (Button) findViewById(R.id.btn_157);
        btn_161 = (Button) findViewById(R.id.btn_161);
        btn_162 = (Button) findViewById(R.id.btn_162);
        btn_163 = (Button) findViewById(R.id.btn_163);
        btn_164 = (Button) findViewById(R.id.btn_164);
        btn_165 = (Button) findViewById(R.id.btn_165);
        btn_166 = (Button) findViewById(R.id.btn_166);
        btn_167 = (Button) findViewById(R.id.btn_167);
        btn_171 = (Button) findViewById(R.id.btn_171);
        btn_172 = (Button) findViewById(R.id.btn_172);
        btn_173 = (Button) findViewById(R.id.btn_173);
        btn_174 = (Button) findViewById(R.id.btn_174);
        btn_175 = (Button) findViewById(R.id.btn_175);
        btn_176 = (Button) findViewById(R.id.btn_176);
        btn_177 = (Button) findViewById(R.id.btn_177);
        btn_181 = (Button) findViewById(R.id.btn_181);
        btn_182 = (Button) findViewById(R.id.btn_182);
        btn_183 = (Button) findViewById(R.id.btn_183);
        btn_184 = (Button) findViewById(R.id.btn_184);
        btn_185 = (Button) findViewById(R.id.btn_185);
        btn_186 = (Button) findViewById(R.id.btn_186);
        btn_187 = (Button) findViewById(R.id.btn_187);
        btn_191 = (Button) findViewById(R.id.btn_191);
        btn_192 = (Button) findViewById(R.id.btn_192);
        btn_193 = (Button) findViewById(R.id.btn_193);
        btn_194 = (Button) findViewById(R.id.btn_194);
        btn_195 = (Button) findViewById(R.id.btn_195);
        btn_196 = (Button) findViewById(R.id.btn_196);
        btn_197 = (Button) findViewById(R.id.btn_197);
        btn_201 = (Button) findViewById(R.id.btn_201);
        btn_202 = (Button) findViewById(R.id.btn_202);
        btn_203 = (Button) findViewById(R.id.btn_203);
        btn_204 = (Button) findViewById(R.id.btn_204);
        btn_205 = (Button) findViewById(R.id.btn_205);
        btn_206 = (Button) findViewById(R.id.btn_206);
        btn_207 = (Button) findViewById(R.id.btn_207);
        btn_211 = (Button) findViewById(R.id.btn_211);
        btn_212 = (Button) findViewById(R.id.btn_212);
        btn_213 = (Button) findViewById(R.id.btn_213);
        btn_214 = (Button) findViewById(R.id.btn_214);
        btn_215 = (Button) findViewById(R.id.btn_215);
        btn_216 = (Button) findViewById(R.id.btn_216);
        btn_217 = (Button) findViewById(R.id.btn_217);
        btn_221 = (Button) findViewById(R.id.btn_221);
        btn_222 = (Button) findViewById(R.id.btn_222);
        btn_223 = (Button) findViewById(R.id.btn_223);
        btn_224 = (Button) findViewById(R.id.btn_224);
        btn_225 = (Button) findViewById(R.id.btn_225);
        btn_226 = (Button) findViewById(R.id.btn_226);
        btn_227 = (Button) findViewById(R.id.btn_227);
        btn_231 = (Button) findViewById(R.id.btn_231);
        btn_232 = (Button) findViewById(R.id.btn_232);
        btn_233 = (Button) findViewById(R.id.btn_233);
        btn_234 = (Button) findViewById(R.id.btn_234);
        btn_235 = (Button) findViewById(R.id.btn_235);
        btn_236 = (Button) findViewById(R.id.btn_236);
        btn_237 = (Button) findViewById(R.id.btn_237);
        btn_241 = (Button) findViewById(R.id.btn_241);
        btn_242 = (Button) findViewById(R.id.btn_242);
        btn_243 = (Button) findViewById(R.id.btn_243);
        btn_244 = (Button) findViewById(R.id.btn_244);
        btn_245 = (Button) findViewById(R.id.btn_245);
        btn_246 = (Button) findViewById(R.id.btn_246);
        btn_247 = (Button) findViewById(R.id.btn_247);

        btn_11.setOnClickListener(this);
        btn_12.setOnClickListener(this);
        btn_13.setOnClickListener(this);
        btn_14.setOnClickListener(this);
        btn_15.setOnClickListener(this);
        btn_16.setOnClickListener(this);
        btn_17.setOnClickListener(this);
        btn_21.setOnClickListener(this);
        btn_22.setOnClickListener(this);
        btn_23.setOnClickListener(this);
        btn_24.setOnClickListener(this);
        btn_25.setOnClickListener(this);
        btn_26.setOnClickListener(this);
        btn_27.setOnClickListener(this);
        btn_31.setOnClickListener(this);
        btn_32.setOnClickListener(this);
        btn_33.setOnClickListener(this);
        btn_34.setOnClickListener(this);
        btn_35.setOnClickListener(this);
        btn_36.setOnClickListener(this);
        btn_37.setOnClickListener(this);
        btn_41.setOnClickListener(this);
        btn_42.setOnClickListener(this);
        btn_43.setOnClickListener(this);
        btn_44.setOnClickListener(this);
        btn_45.setOnClickListener(this);
        btn_46.setOnClickListener(this);
        btn_47.setOnClickListener(this);
        btn_51.setOnClickListener(this);
        btn_52.setOnClickListener(this);
        btn_53.setOnClickListener(this);
        btn_54.setOnClickListener(this);
        btn_55.setOnClickListener(this);
        btn_56.setOnClickListener(this);
        btn_57.setOnClickListener(this);
        btn_61.setOnClickListener(this);
        btn_62.setOnClickListener(this);
        btn_63.setOnClickListener(this);
        btn_64.setOnClickListener(this);
        btn_65.setOnClickListener(this);
        btn_66.setOnClickListener(this);
        btn_67.setOnClickListener(this);
        btn_71.setOnClickListener(this);
        btn_72.setOnClickListener(this);
        btn_73.setOnClickListener(this);
        btn_74.setOnClickListener(this);
        btn_75.setOnClickListener(this);
        btn_76.setOnClickListener(this);
        btn_77.setOnClickListener(this);
        btn_81.setOnClickListener(this);
        btn_82.setOnClickListener(this);
        btn_83.setOnClickListener(this);
        btn_84.setOnClickListener(this);
        btn_85.setOnClickListener(this);
        btn_86.setOnClickListener(this);
        btn_87.setOnClickListener(this);
        btn_91.setOnClickListener(this);
        btn_92.setOnClickListener(this);
        btn_93.setOnClickListener(this);
        btn_94.setOnClickListener(this);
        btn_95.setOnClickListener(this);
        btn_96.setOnClickListener(this);
        btn_97.setOnClickListener(this);
        btn_101.setOnClickListener(this);
        btn_102.setOnClickListener(this);
        btn_103.setOnClickListener(this);
        btn_104.setOnClickListener(this);
        btn_105.setOnClickListener(this);
        btn_106.setOnClickListener(this);
        btn_107.setOnClickListener(this);
        btn_111.setOnClickListener(this);
        btn_112.setOnClickListener(this);
        btn_113.setOnClickListener(this);
        btn_114.setOnClickListener(this);
        btn_115.setOnClickListener(this);
        btn_116.setOnClickListener(this);
        btn_117.setOnClickListener(this);
        btn_121.setOnClickListener(this);
        btn_122.setOnClickListener(this);
        btn_123.setOnClickListener(this);
        btn_124.setOnClickListener(this);
        btn_125.setOnClickListener(this);
        btn_126.setOnClickListener(this);
        btn_127.setOnClickListener(this);
        btn_131.setOnClickListener(this);
        btn_132.setOnClickListener(this);
        btn_133.setOnClickListener(this);
        btn_134.setOnClickListener(this);
        btn_135.setOnClickListener(this);
        btn_136.setOnClickListener(this);
        btn_137.setOnClickListener(this);
        btn_141.setOnClickListener(this);
        btn_142.setOnClickListener(this);
        btn_143.setOnClickListener(this);
        btn_144.setOnClickListener(this);
        btn_145.setOnClickListener(this);
        btn_146.setOnClickListener(this);
        btn_147.setOnClickListener(this);
        btn_151.setOnClickListener(this);
        btn_152.setOnClickListener(this);
        btn_153.setOnClickListener(this);
        btn_154.setOnClickListener(this);
        btn_155.setOnClickListener(this);
        btn_156.setOnClickListener(this);
        btn_157.setOnClickListener(this);
        btn_161.setOnClickListener(this);
        btn_162.setOnClickListener(this);
        btn_163.setOnClickListener(this);
        btn_164.setOnClickListener(this);
        btn_165.setOnClickListener(this);
        btn_166.setOnClickListener(this);
        btn_167.setOnClickListener(this);
        btn_171.setOnClickListener(this);
        btn_172.setOnClickListener(this);
        btn_173.setOnClickListener(this);
        btn_174.setOnClickListener(this);
        btn_175.setOnClickListener(this);
        btn_176.setOnClickListener(this);
        btn_177.setOnClickListener(this);
        btn_181.setOnClickListener(this);
        btn_182.setOnClickListener(this);
        btn_183.setOnClickListener(this);
        btn_184.setOnClickListener(this);
        btn_185.setOnClickListener(this);
        btn_186.setOnClickListener(this);
        btn_187.setOnClickListener(this);
        btn_191.setOnClickListener(this);
        btn_192.setOnClickListener(this);
        btn_193.setOnClickListener(this);
        btn_194.setOnClickListener(this);
        btn_195.setOnClickListener(this);
        btn_196.setOnClickListener(this);
        btn_197.setOnClickListener(this);
        btn_201.setOnClickListener(this);
        btn_202.setOnClickListener(this);
        btn_203.setOnClickListener(this);
        btn_204.setOnClickListener(this);
        btn_205.setOnClickListener(this);
        btn_206.setOnClickListener(this);
        btn_207.setOnClickListener(this);
        btn_211.setOnClickListener(this);
        btn_212.setOnClickListener(this);
        btn_213.setOnClickListener(this);
        btn_214.setOnClickListener(this);
        btn_215.setOnClickListener(this);
        btn_216.setOnClickListener(this);
        btn_217.setOnClickListener(this);
        btn_221.setOnClickListener(this);
        btn_222.setOnClickListener(this);
        btn_223.setOnClickListener(this);
        btn_224.setOnClickListener(this);
        btn_225.setOnClickListener(this);
        btn_226.setOnClickListener(this);
        btn_227.setOnClickListener(this);
        btn_231.setOnClickListener(this);
        btn_232.setOnClickListener(this);
        btn_233.setOnClickListener(this);
        btn_234.setOnClickListener(this);
        btn_235.setOnClickListener(this);
        btn_236.setOnClickListener(this);
        btn_237.setOnClickListener(this);
        btn_241.setOnClickListener(this);
        btn_242.setOnClickListener(this);
        btn_243.setOnClickListener(this);
        btn_244.setOnClickListener(this);
        btn_245.setOnClickListener(this);
        btn_246.setOnClickListener(this);
        btn_247.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void clearAllColor() {
        btn_11.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_12.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_13.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_14.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_15.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_16.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_17.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_21.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_22.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_23.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_24.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_25.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_26.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_27.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_31.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_32.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_33.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_34.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_35.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_36.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_37.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_41.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_42.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_43.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_44.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_45.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_46.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_47.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_51.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_52.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_53.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_54.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_55.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_56.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_57.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_61.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_62.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_63.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_64.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_65.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_66.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_67.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_71.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_72.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_73.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_74.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_75.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_76.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_77.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_81.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_82.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_83.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_84.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_85.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_86.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_87.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_91.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_92.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_93.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_94.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_95.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_96.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_97.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_101.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_102.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_103.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_104.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_105.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_106.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_107.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_111.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_112.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_113.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_114.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_115.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_116.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_117.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_121.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_122.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_123.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_124.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_125.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_126.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_127.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_131.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_132.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_133.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_134.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_135.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_136.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_137.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_141.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_142.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_143.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_144.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_145.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_146.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_147.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_151.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_152.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_153.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_154.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_155.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_156.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_157.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_161.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_162.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_163.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_164.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_165.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_166.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_167.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_171.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_172.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_173.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_174.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_175.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_176.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_177.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_181.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_182.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_183.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_184.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_185.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_186.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_187.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_191.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_192.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_193.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_194.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_195.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_196.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_197.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_201.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_202.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_203.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_204.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_205.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_206.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_207.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_211.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_212.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_213.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_214.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_215.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_216.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_217.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_221.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_222.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_223.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_224.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_225.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_226.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_227.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_231.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_232.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_233.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_234.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_235.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_236.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_237.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_241.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_242.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_243.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_244.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_245.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_246.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_247.setBackground(this.getResources().getDrawable(R.drawable.shape_btn2));
        btn_11.setTextColor(Color.parseColor("#555555"));
        btn_12.setTextColor(Color.parseColor("#555555"));
        btn_13.setTextColor(Color.parseColor("#555555"));
        btn_14.setTextColor(Color.parseColor("#555555"));
        btn_15.setTextColor(Color.parseColor("#555555"));
        btn_16.setTextColor(Color.parseColor("#555555"));
        btn_17.setTextColor(Color.parseColor("#555555"));
        btn_21.setTextColor(Color.parseColor("#555555"));
        btn_22.setTextColor(Color.parseColor("#555555"));
        btn_23.setTextColor(Color.parseColor("#555555"));
        btn_24.setTextColor(Color.parseColor("#555555"));
        btn_25.setTextColor(Color.parseColor("#555555"));
        btn_26.setTextColor(Color.parseColor("#555555"));
        btn_27.setTextColor(Color.parseColor("#555555"));
        btn_31.setTextColor(Color.parseColor("#555555"));
        btn_32.setTextColor(Color.parseColor("#555555"));
        btn_33.setTextColor(Color.parseColor("#555555"));
        btn_34.setTextColor(Color.parseColor("#555555"));
        btn_35.setTextColor(Color.parseColor("#555555"));
        btn_36.setTextColor(Color.parseColor("#555555"));
        btn_37.setTextColor(Color.parseColor("#555555"));
        btn_41.setTextColor(Color.parseColor("#555555"));
        btn_42.setTextColor(Color.parseColor("#555555"));
        btn_43.setTextColor(Color.parseColor("#555555"));
        btn_44.setTextColor(Color.parseColor("#555555"));
        btn_45.setTextColor(Color.parseColor("#555555"));
        btn_46.setTextColor(Color.parseColor("#555555"));
        btn_47.setTextColor(Color.parseColor("#555555"));
        btn_51.setTextColor(Color.parseColor("#555555"));
        btn_52.setTextColor(Color.parseColor("#555555"));
        btn_53.setTextColor(Color.parseColor("#555555"));
        btn_54.setTextColor(Color.parseColor("#555555"));
        btn_55.setTextColor(Color.parseColor("#555555"));
        btn_56.setTextColor(Color.parseColor("#555555"));
        btn_57.setTextColor(Color.parseColor("#555555"));
        btn_61.setTextColor(Color.parseColor("#555555"));
        btn_62.setTextColor(Color.parseColor("#555555"));
        btn_63.setTextColor(Color.parseColor("#555555"));
        btn_64.setTextColor(Color.parseColor("#555555"));
        btn_65.setTextColor(Color.parseColor("#555555"));
        btn_66.setTextColor(Color.parseColor("#555555"));
        btn_67.setTextColor(Color.parseColor("#555555"));
        btn_71.setTextColor(Color.parseColor("#555555"));
        btn_72.setTextColor(Color.parseColor("#555555"));
        btn_73.setTextColor(Color.parseColor("#555555"));
        btn_74.setTextColor(Color.parseColor("#555555"));
        btn_75.setTextColor(Color.parseColor("#555555"));
        btn_76.setTextColor(Color.parseColor("#555555"));
        btn_77.setTextColor(Color.parseColor("#555555"));
        btn_81.setTextColor(Color.parseColor("#555555"));
        btn_82.setTextColor(Color.parseColor("#555555"));
        btn_83.setTextColor(Color.parseColor("#555555"));
        btn_84.setTextColor(Color.parseColor("#555555"));
        btn_85.setTextColor(Color.parseColor("#555555"));
        btn_86.setTextColor(Color.parseColor("#555555"));
        btn_87.setTextColor(Color.parseColor("#555555"));
        btn_91.setTextColor(Color.parseColor("#555555"));
        btn_92.setTextColor(Color.parseColor("#555555"));
        btn_93.setTextColor(Color.parseColor("#555555"));
        btn_94.setTextColor(Color.parseColor("#555555"));
        btn_95.setTextColor(Color.parseColor("#555555"));
        btn_96.setTextColor(Color.parseColor("#555555"));
        btn_97.setTextColor(Color.parseColor("#555555"));
        btn_101.setTextColor(Color.parseColor("#555555"));
        btn_102.setTextColor(Color.parseColor("#555555"));
        btn_103.setTextColor(Color.parseColor("#555555"));
        btn_104.setTextColor(Color.parseColor("#555555"));
        btn_105.setTextColor(Color.parseColor("#555555"));
        btn_106.setTextColor(Color.parseColor("#555555"));
        btn_107.setTextColor(Color.parseColor("#555555"));
        btn_111.setTextColor(Color.parseColor("#555555"));
        btn_112.setTextColor(Color.parseColor("#555555"));
        btn_113.setTextColor(Color.parseColor("#555555"));
        btn_114.setTextColor(Color.parseColor("#555555"));
        btn_115.setTextColor(Color.parseColor("#555555"));
        btn_116.setTextColor(Color.parseColor("#555555"));
        btn_117.setTextColor(Color.parseColor("#555555"));
        btn_121.setTextColor(Color.parseColor("#555555"));
        btn_122.setTextColor(Color.parseColor("#555555"));
        btn_123.setTextColor(Color.parseColor("#555555"));
        btn_124.setTextColor(Color.parseColor("#555555"));
        btn_125.setTextColor(Color.parseColor("#555555"));
        btn_126.setTextColor(Color.parseColor("#555555"));
        btn_127.setTextColor(Color.parseColor("#555555"));
        btn_131.setTextColor(Color.parseColor("#555555"));
        btn_132.setTextColor(Color.parseColor("#555555"));
        btn_133.setTextColor(Color.parseColor("#555555"));
        btn_134.setTextColor(Color.parseColor("#555555"));
        btn_135.setTextColor(Color.parseColor("#555555"));
        btn_136.setTextColor(Color.parseColor("#555555"));
        btn_137.setTextColor(Color.parseColor("#555555"));
        btn_141.setTextColor(Color.parseColor("#555555"));
        btn_142.setTextColor(Color.parseColor("#555555"));
        btn_143.setTextColor(Color.parseColor("#555555"));
        btn_144.setTextColor(Color.parseColor("#555555"));
        btn_145.setTextColor(Color.parseColor("#555555"));
        btn_146.setTextColor(Color.parseColor("#555555"));
        btn_147.setTextColor(Color.parseColor("#555555"));
        btn_151.setTextColor(Color.parseColor("#555555"));
        btn_152.setTextColor(Color.parseColor("#555555"));
        btn_153.setTextColor(Color.parseColor("#555555"));
        btn_154.setTextColor(Color.parseColor("#555555"));
        btn_155.setTextColor(Color.parseColor("#555555"));
        btn_156.setTextColor(Color.parseColor("#555555"));
        btn_157.setTextColor(Color.parseColor("#555555"));
        btn_161.setTextColor(Color.parseColor("#555555"));
        btn_162.setTextColor(Color.parseColor("#555555"));
        btn_163.setTextColor(Color.parseColor("#555555"));
        btn_164.setTextColor(Color.parseColor("#555555"));
        btn_165.setTextColor(Color.parseColor("#555555"));
        btn_166.setTextColor(Color.parseColor("#555555"));
        btn_167.setTextColor(Color.parseColor("#555555"));
        btn_171.setTextColor(Color.parseColor("#555555"));
        btn_172.setTextColor(Color.parseColor("#555555"));
        btn_173.setTextColor(Color.parseColor("#555555"));
        btn_174.setTextColor(Color.parseColor("#555555"));
        btn_175.setTextColor(Color.parseColor("#555555"));
        btn_176.setTextColor(Color.parseColor("#555555"));
        btn_177.setTextColor(Color.parseColor("#555555"));
        btn_181.setTextColor(Color.parseColor("#555555"));
        btn_182.setTextColor(Color.parseColor("#555555"));
        btn_183.setTextColor(Color.parseColor("#555555"));
        btn_184.setTextColor(Color.parseColor("#555555"));
        btn_185.setTextColor(Color.parseColor("#555555"));
        btn_186.setTextColor(Color.parseColor("#555555"));
        btn_187.setTextColor(Color.parseColor("#555555"));
        btn_191.setTextColor(Color.parseColor("#555555"));
        btn_192.setTextColor(Color.parseColor("#555555"));
        btn_193.setTextColor(Color.parseColor("#555555"));
        btn_194.setTextColor(Color.parseColor("#555555"));
        btn_195.setTextColor(Color.parseColor("#555555"));
        btn_196.setTextColor(Color.parseColor("#555555"));
        btn_197.setTextColor(Color.parseColor("#555555"));
        btn_201.setTextColor(Color.parseColor("#555555"));
        btn_202.setTextColor(Color.parseColor("#555555"));
        btn_203.setTextColor(Color.parseColor("#555555"));
        btn_204.setTextColor(Color.parseColor("#555555"));
        btn_205.setTextColor(Color.parseColor("#555555"));
        btn_206.setTextColor(Color.parseColor("#555555"));
        btn_207.setTextColor(Color.parseColor("#555555"));
        btn_211.setTextColor(Color.parseColor("#555555"));
        btn_212.setTextColor(Color.parseColor("#555555"));
        btn_213.setTextColor(Color.parseColor("#555555"));
        btn_214.setTextColor(Color.parseColor("#555555"));
        btn_215.setTextColor(Color.parseColor("#555555"));
        btn_216.setTextColor(Color.parseColor("#555555"));
        btn_217.setTextColor(Color.parseColor("#555555"));
        btn_221.setTextColor(Color.parseColor("#555555"));
        btn_222.setTextColor(Color.parseColor("#555555"));
        btn_223.setTextColor(Color.parseColor("#555555"));
        btn_224.setTextColor(Color.parseColor("#555555"));
        btn_225.setTextColor(Color.parseColor("#555555"));
        btn_226.setTextColor(Color.parseColor("#555555"));
        btn_227.setTextColor(Color.parseColor("#555555"));
        btn_231.setTextColor(Color.parseColor("#555555"));
        btn_232.setTextColor(Color.parseColor("#555555"));
        btn_233.setTextColor(Color.parseColor("#555555"));
        btn_234.setTextColor(Color.parseColor("#555555"));
        btn_235.setTextColor(Color.parseColor("#555555"));
        btn_236.setTextColor(Color.parseColor("#555555"));
        btn_237.setTextColor(Color.parseColor("#555555"));
        btn_241.setTextColor(Color.parseColor("#555555"));
        btn_242.setTextColor(Color.parseColor("#555555"));
        btn_243.setTextColor(Color.parseColor("#555555"));
        btn_244.setTextColor(Color.parseColor("#555555"));
        btn_245.setTextColor(Color.parseColor("#555555"));
        btn_246.setTextColor(Color.parseColor("#555555"));
        btn_247.setTextColor(Color.parseColor("#555555"));

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setAllColor() {
        btn_11.setTextColor(Color.parseColor("#ffffff"));
        btn_12.setTextColor(Color.parseColor("#ffffff"));
        btn_13.setTextColor(Color.parseColor("#ffffff"));
        btn_14.setTextColor(Color.parseColor("#ffffff"));
        btn_15.setTextColor(Color.parseColor("#ffffff"));
        btn_16.setTextColor(Color.parseColor("#ffffff"));
        btn_17.setTextColor(Color.parseColor("#ffffff"));
        btn_21.setTextColor(Color.parseColor("#ffffff"));
        btn_22.setTextColor(Color.parseColor("#ffffff"));
        btn_23.setTextColor(Color.parseColor("#ffffff"));
        btn_24.setTextColor(Color.parseColor("#ffffff"));
        btn_25.setTextColor(Color.parseColor("#ffffff"));
        btn_26.setTextColor(Color.parseColor("#ffffff"));
        btn_27.setTextColor(Color.parseColor("#ffffff"));
        btn_31.setTextColor(Color.parseColor("#ffffff"));
        btn_32.setTextColor(Color.parseColor("#ffffff"));
        btn_33.setTextColor(Color.parseColor("#ffffff"));
        btn_34.setTextColor(Color.parseColor("#ffffff"));
        btn_35.setTextColor(Color.parseColor("#ffffff"));
        btn_36.setTextColor(Color.parseColor("#ffffff"));
        btn_37.setTextColor(Color.parseColor("#ffffff"));
        btn_41.setTextColor(Color.parseColor("#ffffff"));
        btn_42.setTextColor(Color.parseColor("#ffffff"));
        btn_43.setTextColor(Color.parseColor("#ffffff"));
        btn_44.setTextColor(Color.parseColor("#ffffff"));
        btn_45.setTextColor(Color.parseColor("#ffffff"));
        btn_46.setTextColor(Color.parseColor("#ffffff"));
        btn_47.setTextColor(Color.parseColor("#ffffff"));
        btn_51.setTextColor(Color.parseColor("#ffffff"));
        btn_52.setTextColor(Color.parseColor("#ffffff"));
        btn_53.setTextColor(Color.parseColor("#ffffff"));
        btn_54.setTextColor(Color.parseColor("#ffffff"));
        btn_55.setTextColor(Color.parseColor("#ffffff"));
        btn_56.setTextColor(Color.parseColor("#ffffff"));
        btn_57.setTextColor(Color.parseColor("#ffffff"));
        btn_61.setTextColor(Color.parseColor("#ffffff"));
        btn_62.setTextColor(Color.parseColor("#ffffff"));
        btn_63.setTextColor(Color.parseColor("#ffffff"));
        btn_64.setTextColor(Color.parseColor("#ffffff"));
        btn_65.setTextColor(Color.parseColor("#ffffff"));
        btn_66.setTextColor(Color.parseColor("#ffffff"));
        btn_67.setTextColor(Color.parseColor("#ffffff"));
        btn_71.setTextColor(Color.parseColor("#ffffff"));
        btn_72.setTextColor(Color.parseColor("#ffffff"));
        btn_73.setTextColor(Color.parseColor("#ffffff"));
        btn_74.setTextColor(Color.parseColor("#ffffff"));
        btn_75.setTextColor(Color.parseColor("#ffffff"));
        btn_76.setTextColor(Color.parseColor("#ffffff"));
        btn_77.setTextColor(Color.parseColor("#ffffff"));
        btn_81.setTextColor(Color.parseColor("#ffffff"));
        btn_82.setTextColor(Color.parseColor("#ffffff"));
        btn_83.setTextColor(Color.parseColor("#ffffff"));
        btn_84.setTextColor(Color.parseColor("#ffffff"));
        btn_85.setTextColor(Color.parseColor("#ffffff"));
        btn_86.setTextColor(Color.parseColor("#ffffff"));
        btn_87.setTextColor(Color.parseColor("#ffffff"));
        btn_91.setTextColor(Color.parseColor("#ffffff"));
        btn_92.setTextColor(Color.parseColor("#ffffff"));
        btn_93.setTextColor(Color.parseColor("#ffffff"));
        btn_94.setTextColor(Color.parseColor("#ffffff"));
        btn_95.setTextColor(Color.parseColor("#ffffff"));
        btn_96.setTextColor(Color.parseColor("#ffffff"));
        btn_97.setTextColor(Color.parseColor("#ffffff"));
        btn_101.setTextColor(Color.parseColor("#ffffff"));
        btn_102.setTextColor(Color.parseColor("#ffffff"));
        btn_103.setTextColor(Color.parseColor("#ffffff"));
        btn_104.setTextColor(Color.parseColor("#ffffff"));
        btn_105.setTextColor(Color.parseColor("#ffffff"));
        btn_106.setTextColor(Color.parseColor("#ffffff"));
        btn_107.setTextColor(Color.parseColor("#ffffff"));
        btn_111.setTextColor(Color.parseColor("#ffffff"));
        btn_112.setTextColor(Color.parseColor("#ffffff"));
        btn_113.setTextColor(Color.parseColor("#ffffff"));
        btn_114.setTextColor(Color.parseColor("#ffffff"));
        btn_115.setTextColor(Color.parseColor("#ffffff"));
        btn_116.setTextColor(Color.parseColor("#ffffff"));
        btn_117.setTextColor(Color.parseColor("#ffffff"));
        btn_121.setTextColor(Color.parseColor("#ffffff"));
        btn_122.setTextColor(Color.parseColor("#ffffff"));
        btn_123.setTextColor(Color.parseColor("#ffffff"));
        btn_124.setTextColor(Color.parseColor("#ffffff"));
        btn_125.setTextColor(Color.parseColor("#ffffff"));
        btn_126.setTextColor(Color.parseColor("#ffffff"));
        btn_127.setTextColor(Color.parseColor("#ffffff"));
        btn_131.setTextColor(Color.parseColor("#ffffff"));
        btn_132.setTextColor(Color.parseColor("#ffffff"));
        btn_133.setTextColor(Color.parseColor("#ffffff"));
        btn_134.setTextColor(Color.parseColor("#ffffff"));
        btn_135.setTextColor(Color.parseColor("#ffffff"));
        btn_136.setTextColor(Color.parseColor("#ffffff"));
        btn_137.setTextColor(Color.parseColor("#ffffff"));
        btn_141.setTextColor(Color.parseColor("#ffffff"));
        btn_142.setTextColor(Color.parseColor("#ffffff"));
        btn_143.setTextColor(Color.parseColor("#ffffff"));
        btn_144.setTextColor(Color.parseColor("#ffffff"));
        btn_145.setTextColor(Color.parseColor("#ffffff"));
        btn_146.setTextColor(Color.parseColor("#ffffff"));
        btn_147.setTextColor(Color.parseColor("#ffffff"));
        btn_151.setTextColor(Color.parseColor("#ffffff"));
        btn_152.setTextColor(Color.parseColor("#ffffff"));
        btn_153.setTextColor(Color.parseColor("#ffffff"));
        btn_154.setTextColor(Color.parseColor("#ffffff"));
        btn_155.setTextColor(Color.parseColor("#ffffff"));
        btn_156.setTextColor(Color.parseColor("#ffffff"));
        btn_157.setTextColor(Color.parseColor("#ffffff"));
        btn_161.setTextColor(Color.parseColor("#ffffff"));
        btn_162.setTextColor(Color.parseColor("#ffffff"));
        btn_163.setTextColor(Color.parseColor("#ffffff"));
        btn_164.setTextColor(Color.parseColor("#ffffff"));
        btn_165.setTextColor(Color.parseColor("#ffffff"));
        btn_166.setTextColor(Color.parseColor("#ffffff"));
        btn_167.setTextColor(Color.parseColor("#ffffff"));
        btn_171.setTextColor(Color.parseColor("#ffffff"));
        btn_172.setTextColor(Color.parseColor("#ffffff"));
        btn_173.setTextColor(Color.parseColor("#ffffff"));
        btn_174.setTextColor(Color.parseColor("#ffffff"));
        btn_175.setTextColor(Color.parseColor("#ffffff"));
        btn_176.setTextColor(Color.parseColor("#ffffff"));
        btn_177.setTextColor(Color.parseColor("#ffffff"));
        btn_181.setTextColor(Color.parseColor("#ffffff"));
        btn_182.setTextColor(Color.parseColor("#ffffff"));
        btn_183.setTextColor(Color.parseColor("#ffffff"));
        btn_184.setTextColor(Color.parseColor("#ffffff"));
        btn_185.setTextColor(Color.parseColor("#ffffff"));
        btn_186.setTextColor(Color.parseColor("#ffffff"));
        btn_187.setTextColor(Color.parseColor("#ffffff"));
        btn_191.setTextColor(Color.parseColor("#ffffff"));
        btn_192.setTextColor(Color.parseColor("#ffffff"));
        btn_193.setTextColor(Color.parseColor("#ffffff"));
        btn_194.setTextColor(Color.parseColor("#ffffff"));
        btn_195.setTextColor(Color.parseColor("#ffffff"));
        btn_196.setTextColor(Color.parseColor("#ffffff"));
        btn_197.setTextColor(Color.parseColor("#ffffff"));
        btn_201.setTextColor(Color.parseColor("#ffffff"));
        btn_202.setTextColor(Color.parseColor("#ffffff"));
        btn_203.setTextColor(Color.parseColor("#ffffff"));
        btn_204.setTextColor(Color.parseColor("#ffffff"));
        btn_205.setTextColor(Color.parseColor("#ffffff"));
        btn_206.setTextColor(Color.parseColor("#ffffff"));
        btn_207.setTextColor(Color.parseColor("#ffffff"));
        btn_211.setTextColor(Color.parseColor("#ffffff"));
        btn_212.setTextColor(Color.parseColor("#ffffff"));
        btn_213.setTextColor(Color.parseColor("#ffffff"));
        btn_214.setTextColor(Color.parseColor("#ffffff"));
        btn_215.setTextColor(Color.parseColor("#ffffff"));
        btn_216.setTextColor(Color.parseColor("#ffffff"));
        btn_217.setTextColor(Color.parseColor("#ffffff"));
        btn_221.setTextColor(Color.parseColor("#ffffff"));
        btn_222.setTextColor(Color.parseColor("#ffffff"));
        btn_223.setTextColor(Color.parseColor("#ffffff"));
        btn_224.setTextColor(Color.parseColor("#ffffff"));
        btn_225.setTextColor(Color.parseColor("#ffffff"));
        btn_226.setTextColor(Color.parseColor("#ffffff"));
        btn_227.setTextColor(Color.parseColor("#ffffff"));
        btn_231.setTextColor(Color.parseColor("#ffffff"));
        btn_232.setTextColor(Color.parseColor("#ffffff"));
        btn_233.setTextColor(Color.parseColor("#ffffff"));
        btn_234.setTextColor(Color.parseColor("#ffffff"));
        btn_235.setTextColor(Color.parseColor("#ffffff"));
        btn_236.setTextColor(Color.parseColor("#ffffff"));
        btn_237.setTextColor(Color.parseColor("#ffffff"));
        btn_241.setTextColor(Color.parseColor("#ffffff"));
        btn_242.setTextColor(Color.parseColor("#ffffff"));
        btn_243.setTextColor(Color.parseColor("#ffffff"));
        btn_244.setTextColor(Color.parseColor("#ffffff"));
        btn_245.setTextColor(Color.parseColor("#ffffff"));
        btn_246.setTextColor(Color.parseColor("#ffffff"));
        btn_247.setTextColor(Color.parseColor("#ffffff"));
        btn_11.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_12.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_13.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_14.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_15.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_16.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_17.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_21.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_22.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_23.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_24.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_25.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_26.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_27.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_31.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_32.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_33.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_34.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_35.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_36.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_37.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_41.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_42.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_43.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_44.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_45.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_46.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_47.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_51.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_52.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_53.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_54.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_55.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_56.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_57.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_61.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_62.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_63.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_64.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_65.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_66.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_67.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_71.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_72.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_73.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_74.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_75.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_76.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_77.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_81.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_82.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_83.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_84.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_85.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_86.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_87.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_91.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_92.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_93.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_94.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_95.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_96.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_97.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_101.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_102.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_103.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_104.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_105.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_106.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_107.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_111.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_112.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_113.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_114.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_115.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_116.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_117.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_121.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_122.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_123.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_124.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_125.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_126.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_127.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_131.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_132.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_133.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_134.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_135.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_136.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_137.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_141.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_142.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_143.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_144.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_145.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_146.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_147.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_151.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_152.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_153.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_154.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_155.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_156.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_157.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_161.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_162.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_163.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_164.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_165.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_166.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_167.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_171.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_172.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_173.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_174.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_175.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_176.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_177.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_181.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_182.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_183.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_184.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_185.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_186.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_187.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_191.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_192.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_193.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_194.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_195.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_196.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_197.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_201.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_202.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_203.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_204.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_205.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_206.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_207.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_211.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_212.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_213.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_214.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_215.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_216.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_217.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_221.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_222.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_223.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_224.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_225.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_226.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_227.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_231.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_232.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_233.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_234.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_235.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_236.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_237.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_241.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_242.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_243.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_244.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_245.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_246.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_247.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_05_08() {
        btn_61.setTextColor(Color.parseColor("#ffffff"));
        btn_62.setTextColor(Color.parseColor("#ffffff"));
        btn_63.setTextColor(Color.parseColor("#ffffff"));
        btn_64.setTextColor(Color.parseColor("#ffffff"));
        btn_65.setTextColor(Color.parseColor("#ffffff"));
        btn_66.setTextColor(Color.parseColor("#ffffff"));
        btn_67.setTextColor(Color.parseColor("#ffffff"));
        btn_71.setTextColor(Color.parseColor("#ffffff"));
        btn_72.setTextColor(Color.parseColor("#ffffff"));
        btn_73.setTextColor(Color.parseColor("#ffffff"));
        btn_74.setTextColor(Color.parseColor("#ffffff"));
        btn_75.setTextColor(Color.parseColor("#ffffff"));
        btn_76.setTextColor(Color.parseColor("#ffffff"));
        btn_77.setTextColor(Color.parseColor("#ffffff"));
        btn_81.setTextColor(Color.parseColor("#ffffff"));
        btn_82.setTextColor(Color.parseColor("#ffffff"));
        btn_83.setTextColor(Color.parseColor("#ffffff"));
        btn_84.setTextColor(Color.parseColor("#ffffff"));
        btn_85.setTextColor(Color.parseColor("#ffffff"));
        btn_86.setTextColor(Color.parseColor("#ffffff"));
        btn_87.setTextColor(Color.parseColor("#ffffff"));
        btn_91.setTextColor(Color.parseColor("#ffffff"));
        btn_92.setTextColor(Color.parseColor("#ffffff"));
        btn_93.setTextColor(Color.parseColor("#ffffff"));
        btn_94.setTextColor(Color.parseColor("#ffffff"));
        btn_95.setTextColor(Color.parseColor("#ffffff"));
        btn_96.setTextColor(Color.parseColor("#ffffff"));
        btn_97.setTextColor(Color.parseColor("#ffffff"));
        btn_61.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_62.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_63.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_64.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_65.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_66.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_67.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_71.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_72.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_73.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_74.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_75.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_76.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_77.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_81.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_82.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_83.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_84.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_85.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_86.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_87.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_91.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_92.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_93.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_94.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_95.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_96.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_97.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_05_08_b() {
        btn_61.setTextColor(Color.parseColor("#ffffff"));
        btn_62.setTextColor(Color.parseColor("#ffffff"));
        btn_63.setTextColor(Color.parseColor("#ffffff"));
        btn_64.setTextColor(Color.parseColor("#ffffff"));
        btn_65.setTextColor(Color.parseColor("#ffffff"));
        btn_71.setTextColor(Color.parseColor("#ffffff"));
        btn_72.setTextColor(Color.parseColor("#ffffff"));
        btn_73.setTextColor(Color.parseColor("#ffffff"));
        btn_74.setTextColor(Color.parseColor("#ffffff"));
        btn_75.setTextColor(Color.parseColor("#ffffff"));
        btn_81.setTextColor(Color.parseColor("#ffffff"));
        btn_82.setTextColor(Color.parseColor("#ffffff"));
        btn_83.setTextColor(Color.parseColor("#ffffff"));
        btn_84.setTextColor(Color.parseColor("#ffffff"));
        btn_85.setTextColor(Color.parseColor("#ffffff"));
        btn_91.setTextColor(Color.parseColor("#ffffff"));
        btn_92.setTextColor(Color.parseColor("#ffffff"));
        btn_93.setTextColor(Color.parseColor("#ffffff"));
        btn_94.setTextColor(Color.parseColor("#ffffff"));
        btn_95.setTextColor(Color.parseColor("#ffffff"));
        btn_61.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_62.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_63.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_64.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_65.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_71.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_72.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_73.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_74.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_75.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_81.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_82.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_83.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_84.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_85.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_91.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_92.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_93.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_94.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_95.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_05_08_c() {
        btn_66.setTextColor(Color.parseColor("#ffffff"));
        btn_67.setTextColor(Color.parseColor("#ffffff"));
        btn_76.setTextColor(Color.parseColor("#ffffff"));
        btn_77.setTextColor(Color.parseColor("#ffffff"));
        btn_86.setTextColor(Color.parseColor("#ffffff"));
        btn_87.setTextColor(Color.parseColor("#ffffff"));
        btn_96.setTextColor(Color.parseColor("#ffffff"));
        btn_97.setTextColor(Color.parseColor("#ffffff"));
        btn_66.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_67.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_76.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_77.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_86.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_87.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_96.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_97.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_01_04() {
        btn_21.setTextColor(Color.parseColor("#ffffff"));
        btn_22.setTextColor(Color.parseColor("#ffffff"));
        btn_23.setTextColor(Color.parseColor("#ffffff"));
        btn_24.setTextColor(Color.parseColor("#ffffff"));
        btn_25.setTextColor(Color.parseColor("#ffffff"));
        btn_26.setTextColor(Color.parseColor("#ffffff"));
        btn_27.setTextColor(Color.parseColor("#ffffff"));
        btn_31.setTextColor(Color.parseColor("#ffffff"));
        btn_32.setTextColor(Color.parseColor("#ffffff"));
        btn_33.setTextColor(Color.parseColor("#ffffff"));
        btn_34.setTextColor(Color.parseColor("#ffffff"));
        btn_35.setTextColor(Color.parseColor("#ffffff"));
        btn_36.setTextColor(Color.parseColor("#ffffff"));
        btn_37.setTextColor(Color.parseColor("#ffffff"));
        btn_41.setTextColor(Color.parseColor("#ffffff"));
        btn_42.setTextColor(Color.parseColor("#ffffff"));
        btn_43.setTextColor(Color.parseColor("#ffffff"));
        btn_44.setTextColor(Color.parseColor("#ffffff"));
        btn_45.setTextColor(Color.parseColor("#ffffff"));
        btn_46.setTextColor(Color.parseColor("#ffffff"));
        btn_47.setTextColor(Color.parseColor("#ffffff"));
        btn_51.setTextColor(Color.parseColor("#ffffff"));
        btn_52.setTextColor(Color.parseColor("#ffffff"));
        btn_53.setTextColor(Color.parseColor("#ffffff"));
        btn_54.setTextColor(Color.parseColor("#ffffff"));
        btn_55.setTextColor(Color.parseColor("#ffffff"));
        btn_56.setTextColor(Color.parseColor("#ffffff"));
        btn_57.setTextColor(Color.parseColor("#ffffff"));
        btn_21.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_22.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_23.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_24.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_25.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_26.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_27.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_31.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_32.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_33.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_34.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_35.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_36.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_37.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_41.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_42.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_43.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_44.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_45.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_46.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_47.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_51.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_52.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_53.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_54.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_55.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_56.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_57.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_01_04_b() {
        btn_21.setTextColor(Color.parseColor("#ffffff"));
        btn_22.setTextColor(Color.parseColor("#ffffff"));
        btn_23.setTextColor(Color.parseColor("#ffffff"));
        btn_24.setTextColor(Color.parseColor("#ffffff"));
        btn_25.setTextColor(Color.parseColor("#ffffff"));
        btn_31.setTextColor(Color.parseColor("#ffffff"));
        btn_32.setTextColor(Color.parseColor("#ffffff"));
        btn_33.setTextColor(Color.parseColor("#ffffff"));
        btn_34.setTextColor(Color.parseColor("#ffffff"));
        btn_35.setTextColor(Color.parseColor("#ffffff"));
        btn_41.setTextColor(Color.parseColor("#ffffff"));
        btn_42.setTextColor(Color.parseColor("#ffffff"));
        btn_43.setTextColor(Color.parseColor("#ffffff"));
        btn_44.setTextColor(Color.parseColor("#ffffff"));
        btn_45.setTextColor(Color.parseColor("#ffffff"));
        btn_51.setTextColor(Color.parseColor("#ffffff"));
        btn_52.setTextColor(Color.parseColor("#ffffff"));
        btn_53.setTextColor(Color.parseColor("#ffffff"));
        btn_54.setTextColor(Color.parseColor("#ffffff"));
        btn_55.setTextColor(Color.parseColor("#ffffff"));
        btn_21.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_22.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_23.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_24.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_25.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_31.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_32.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_33.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_34.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_35.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_41.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_42.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_43.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_44.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_45.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_51.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_52.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_53.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_54.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_55.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_01_04_c() {
        btn_26.setTextColor(Color.parseColor("#ffffff"));
        btn_27.setTextColor(Color.parseColor("#ffffff"));
        btn_36.setTextColor(Color.parseColor("#ffffff"));
        btn_37.setTextColor(Color.parseColor("#ffffff"));
        btn_46.setTextColor(Color.parseColor("#ffffff"));
        btn_47.setTextColor(Color.parseColor("#ffffff"));
        btn_56.setTextColor(Color.parseColor("#ffffff"));
        btn_57.setTextColor(Color.parseColor("#ffffff"));
        btn_26.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_27.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_36.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_37.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_46.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_47.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_56.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_57.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_21_00() {
        btn_11.setTextColor(Color.parseColor("#ffffff"));
        btn_12.setTextColor(Color.parseColor("#ffffff"));
        btn_13.setTextColor(Color.parseColor("#ffffff"));
        btn_14.setTextColor(Color.parseColor("#ffffff"));
        btn_15.setTextColor(Color.parseColor("#ffffff"));
        btn_16.setTextColor(Color.parseColor("#ffffff"));
        btn_17.setTextColor(Color.parseColor("#ffffff"));
        btn_221.setTextColor(Color.parseColor("#ffffff"));
        btn_222.setTextColor(Color.parseColor("#ffffff"));
        btn_223.setTextColor(Color.parseColor("#ffffff"));
        btn_224.setTextColor(Color.parseColor("#ffffff"));
        btn_225.setTextColor(Color.parseColor("#ffffff"));
        btn_226.setTextColor(Color.parseColor("#ffffff"));
        btn_227.setTextColor(Color.parseColor("#ffffff"));
        btn_231.setTextColor(Color.parseColor("#ffffff"));
        btn_232.setTextColor(Color.parseColor("#ffffff"));
        btn_233.setTextColor(Color.parseColor("#ffffff"));
        btn_234.setTextColor(Color.parseColor("#ffffff"));
        btn_235.setTextColor(Color.parseColor("#ffffff"));
        btn_236.setTextColor(Color.parseColor("#ffffff"));
        btn_237.setTextColor(Color.parseColor("#ffffff"));
        btn_241.setTextColor(Color.parseColor("#ffffff"));
        btn_242.setTextColor(Color.parseColor("#ffffff"));
        btn_243.setTextColor(Color.parseColor("#ffffff"));
        btn_244.setTextColor(Color.parseColor("#ffffff"));
        btn_245.setTextColor(Color.parseColor("#ffffff"));
        btn_246.setTextColor(Color.parseColor("#ffffff"));
        btn_247.setTextColor(Color.parseColor("#ffffff"));
        btn_11.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_12.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_13.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_14.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_15.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_16.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_17.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_221.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_222.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_223.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_224.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_225.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_226.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_227.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_231.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_232.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_233.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_234.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_235.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_236.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_237.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_241.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_242.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_243.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_244.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_245.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_246.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_247.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_21_00_b() {
        btn_11.setTextColor(Color.parseColor("#ffffff"));
        btn_12.setTextColor(Color.parseColor("#ffffff"));
        btn_13.setTextColor(Color.parseColor("#ffffff"));
        btn_14.setTextColor(Color.parseColor("#ffffff"));
        btn_15.setTextColor(Color.parseColor("#ffffff"));
        btn_221.setTextColor(Color.parseColor("#ffffff"));
        btn_222.setTextColor(Color.parseColor("#ffffff"));
        btn_223.setTextColor(Color.parseColor("#ffffff"));
        btn_224.setTextColor(Color.parseColor("#ffffff"));
        btn_225.setTextColor(Color.parseColor("#ffffff"));
        btn_231.setTextColor(Color.parseColor("#ffffff"));
        btn_232.setTextColor(Color.parseColor("#ffffff"));
        btn_233.setTextColor(Color.parseColor("#ffffff"));
        btn_234.setTextColor(Color.parseColor("#ffffff"));
        btn_235.setTextColor(Color.parseColor("#ffffff"));
        btn_241.setTextColor(Color.parseColor("#ffffff"));
        btn_242.setTextColor(Color.parseColor("#ffffff"));
        btn_243.setTextColor(Color.parseColor("#ffffff"));
        btn_244.setTextColor(Color.parseColor("#ffffff"));
        btn_245.setTextColor(Color.parseColor("#ffffff"));
        btn_11.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_12.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_13.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_14.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_15.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_221.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_222.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_223.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_224.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_225.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_231.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_232.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_233.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_234.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_235.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_241.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_242.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_243.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_244.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_245.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_21_00_c() {
        btn_16.setTextColor(Color.parseColor("#ffffff"));
        btn_17.setTextColor(Color.parseColor("#ffffff"));
        btn_226.setTextColor(Color.parseColor("#ffffff"));
        btn_227.setTextColor(Color.parseColor("#ffffff"));
        btn_236.setTextColor(Color.parseColor("#ffffff"));
        btn_237.setTextColor(Color.parseColor("#ffffff"));
        btn_246.setTextColor(Color.parseColor("#ffffff"));
        btn_247.setTextColor(Color.parseColor("#ffffff"));
        btn_16.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_17.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_226.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_227.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_236.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_237.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_246.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_247.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_17_20() {
        btn_181.setTextColor(Color.parseColor("#ffffff"));
        btn_182.setTextColor(Color.parseColor("#ffffff"));
        btn_183.setTextColor(Color.parseColor("#ffffff"));
        btn_184.setTextColor(Color.parseColor("#ffffff"));
        btn_185.setTextColor(Color.parseColor("#ffffff"));
        btn_186.setTextColor(Color.parseColor("#ffffff"));
        btn_187.setTextColor(Color.parseColor("#ffffff"));
        btn_191.setTextColor(Color.parseColor("#ffffff"));
        btn_192.setTextColor(Color.parseColor("#ffffff"));
        btn_193.setTextColor(Color.parseColor("#ffffff"));
        btn_194.setTextColor(Color.parseColor("#ffffff"));
        btn_195.setTextColor(Color.parseColor("#ffffff"));
        btn_196.setTextColor(Color.parseColor("#ffffff"));
        btn_197.setTextColor(Color.parseColor("#ffffff"));
        btn_201.setTextColor(Color.parseColor("#ffffff"));
        btn_202.setTextColor(Color.parseColor("#ffffff"));
        btn_203.setTextColor(Color.parseColor("#ffffff"));
        btn_204.setTextColor(Color.parseColor("#ffffff"));
        btn_205.setTextColor(Color.parseColor("#ffffff"));
        btn_206.setTextColor(Color.parseColor("#ffffff"));
        btn_207.setTextColor(Color.parseColor("#ffffff"));
        btn_211.setTextColor(Color.parseColor("#ffffff"));
        btn_212.setTextColor(Color.parseColor("#ffffff"));
        btn_213.setTextColor(Color.parseColor("#ffffff"));
        btn_214.setTextColor(Color.parseColor("#ffffff"));
        btn_215.setTextColor(Color.parseColor("#ffffff"));
        btn_216.setTextColor(Color.parseColor("#ffffff"));
        btn_217.setTextColor(Color.parseColor("#ffffff"));
        btn_181.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_182.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_183.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_184.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_185.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_186.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_187.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_191.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_192.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_193.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_194.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_195.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_196.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_197.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_201.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_202.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_203.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_204.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_205.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_206.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_207.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_211.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_212.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_213.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_214.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_215.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_216.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_217.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_17_20_b() {
        btn_181.setTextColor(Color.parseColor("#ffffff"));
        btn_182.setTextColor(Color.parseColor("#ffffff"));
        btn_183.setTextColor(Color.parseColor("#ffffff"));
        btn_184.setTextColor(Color.parseColor("#ffffff"));
        btn_185.setTextColor(Color.parseColor("#ffffff"));
        btn_191.setTextColor(Color.parseColor("#ffffff"));
        btn_192.setTextColor(Color.parseColor("#ffffff"));
        btn_193.setTextColor(Color.parseColor("#ffffff"));
        btn_194.setTextColor(Color.parseColor("#ffffff"));
        btn_195.setTextColor(Color.parseColor("#ffffff"));
        btn_201.setTextColor(Color.parseColor("#ffffff"));
        btn_202.setTextColor(Color.parseColor("#ffffff"));
        btn_203.setTextColor(Color.parseColor("#ffffff"));
        btn_204.setTextColor(Color.parseColor("#ffffff"));
        btn_205.setTextColor(Color.parseColor("#ffffff"));
        btn_211.setTextColor(Color.parseColor("#ffffff"));
        btn_212.setTextColor(Color.parseColor("#ffffff"));
        btn_213.setTextColor(Color.parseColor("#ffffff"));
        btn_214.setTextColor(Color.parseColor("#ffffff"));
        btn_215.setTextColor(Color.parseColor("#ffffff"));
        btn_181.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_182.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_183.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_184.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_185.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_191.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_192.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_193.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_194.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_195.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_201.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_202.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_203.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_204.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_205.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_211.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_212.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_213.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_214.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_215.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_17_20_c() {
        btn_186.setTextColor(Color.parseColor("#ffffff"));
        btn_187.setTextColor(Color.parseColor("#ffffff"));
        btn_196.setTextColor(Color.parseColor("#ffffff"));
        btn_197.setTextColor(Color.parseColor("#ffffff"));
        btn_206.setTextColor(Color.parseColor("#ffffff"));
        btn_207.setTextColor(Color.parseColor("#ffffff"));
        btn_216.setTextColor(Color.parseColor("#ffffff"));
        btn_217.setTextColor(Color.parseColor("#ffffff"));
        btn_186.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_187.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_196.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_197.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_206.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_207.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_216.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_217.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void WorkingDays() {
        btn_11.setTextColor(Color.parseColor("#ffffff"));
        btn_12.setTextColor(Color.parseColor("#ffffff"));
        btn_13.setTextColor(Color.parseColor("#ffffff"));
        btn_14.setTextColor(Color.parseColor("#ffffff"));
        btn_15.setTextColor(Color.parseColor("#ffffff"));
        btn_21.setTextColor(Color.parseColor("#ffffff"));
        btn_22.setTextColor(Color.parseColor("#ffffff"));
        btn_23.setTextColor(Color.parseColor("#ffffff"));
        btn_24.setTextColor(Color.parseColor("#ffffff"));
        btn_25.setTextColor(Color.parseColor("#ffffff"));
        btn_31.setTextColor(Color.parseColor("#ffffff"));
        btn_32.setTextColor(Color.parseColor("#ffffff"));
        btn_33.setTextColor(Color.parseColor("#ffffff"));
        btn_34.setTextColor(Color.parseColor("#ffffff"));
        btn_35.setTextColor(Color.parseColor("#ffffff"));
        btn_41.setTextColor(Color.parseColor("#ffffff"));
        btn_42.setTextColor(Color.parseColor("#ffffff"));
        btn_43.setTextColor(Color.parseColor("#ffffff"));
        btn_44.setTextColor(Color.parseColor("#ffffff"));
        btn_45.setTextColor(Color.parseColor("#ffffff"));
        btn_51.setTextColor(Color.parseColor("#ffffff"));
        btn_52.setTextColor(Color.parseColor("#ffffff"));
        btn_53.setTextColor(Color.parseColor("#ffffff"));
        btn_54.setTextColor(Color.parseColor("#ffffff"));
        btn_55.setTextColor(Color.parseColor("#ffffff"));
        btn_61.setTextColor(Color.parseColor("#ffffff"));
        btn_62.setTextColor(Color.parseColor("#ffffff"));
        btn_63.setTextColor(Color.parseColor("#ffffff"));
        btn_64.setTextColor(Color.parseColor("#ffffff"));
        btn_65.setTextColor(Color.parseColor("#ffffff"));
        btn_71.setTextColor(Color.parseColor("#ffffff"));
        btn_72.setTextColor(Color.parseColor("#ffffff"));
        btn_73.setTextColor(Color.parseColor("#ffffff"));
        btn_74.setTextColor(Color.parseColor("#ffffff"));
        btn_75.setTextColor(Color.parseColor("#ffffff"));
        btn_81.setTextColor(Color.parseColor("#ffffff"));
        btn_82.setTextColor(Color.parseColor("#ffffff"));
        btn_83.setTextColor(Color.parseColor("#ffffff"));
        btn_84.setTextColor(Color.parseColor("#ffffff"));
        btn_85.setTextColor(Color.parseColor("#ffffff"));
        btn_91.setTextColor(Color.parseColor("#ffffff"));
        btn_92.setTextColor(Color.parseColor("#ffffff"));
        btn_93.setTextColor(Color.parseColor("#ffffff"));
        btn_94.setTextColor(Color.parseColor("#ffffff"));
        btn_95.setTextColor(Color.parseColor("#ffffff"));
        btn_101.setTextColor(Color.parseColor("#ffffff"));
        btn_102.setTextColor(Color.parseColor("#ffffff"));
        btn_103.setTextColor(Color.parseColor("#ffffff"));
        btn_104.setTextColor(Color.parseColor("#ffffff"));
        btn_105.setTextColor(Color.parseColor("#ffffff"));
        btn_111.setTextColor(Color.parseColor("#ffffff"));
        btn_112.setTextColor(Color.parseColor("#ffffff"));
        btn_113.setTextColor(Color.parseColor("#ffffff"));
        btn_114.setTextColor(Color.parseColor("#ffffff"));
        btn_115.setTextColor(Color.parseColor("#ffffff"));
        btn_121.setTextColor(Color.parseColor("#ffffff"));
        btn_122.setTextColor(Color.parseColor("#ffffff"));
        btn_123.setTextColor(Color.parseColor("#ffffff"));
        btn_124.setTextColor(Color.parseColor("#ffffff"));
        btn_125.setTextColor(Color.parseColor("#ffffff"));
        btn_131.setTextColor(Color.parseColor("#ffffff"));
        btn_132.setTextColor(Color.parseColor("#ffffff"));
        btn_133.setTextColor(Color.parseColor("#ffffff"));
        btn_134.setTextColor(Color.parseColor("#ffffff"));
        btn_135.setTextColor(Color.parseColor("#ffffff"));
        btn_141.setTextColor(Color.parseColor("#ffffff"));
        btn_142.setTextColor(Color.parseColor("#ffffff"));
        btn_143.setTextColor(Color.parseColor("#ffffff"));
        btn_144.setTextColor(Color.parseColor("#ffffff"));
        btn_145.setTextColor(Color.parseColor("#ffffff"));
        btn_151.setTextColor(Color.parseColor("#ffffff"));
        btn_152.setTextColor(Color.parseColor("#ffffff"));
        btn_153.setTextColor(Color.parseColor("#ffffff"));
        btn_154.setTextColor(Color.parseColor("#ffffff"));
        btn_155.setTextColor(Color.parseColor("#ffffff"));
        btn_161.setTextColor(Color.parseColor("#ffffff"));
        btn_162.setTextColor(Color.parseColor("#ffffff"));
        btn_163.setTextColor(Color.parseColor("#ffffff"));
        btn_164.setTextColor(Color.parseColor("#ffffff"));
        btn_165.setTextColor(Color.parseColor("#ffffff"));
        btn_171.setTextColor(Color.parseColor("#ffffff"));
        btn_172.setTextColor(Color.parseColor("#ffffff"));
        btn_173.setTextColor(Color.parseColor("#ffffff"));
        btn_174.setTextColor(Color.parseColor("#ffffff"));
        btn_175.setTextColor(Color.parseColor("#ffffff"));
        btn_181.setTextColor(Color.parseColor("#ffffff"));
        btn_182.setTextColor(Color.parseColor("#ffffff"));
        btn_183.setTextColor(Color.parseColor("#ffffff"));
        btn_184.setTextColor(Color.parseColor("#ffffff"));
        btn_185.setTextColor(Color.parseColor("#ffffff"));
        btn_191.setTextColor(Color.parseColor("#ffffff"));
        btn_192.setTextColor(Color.parseColor("#ffffff"));
        btn_193.setTextColor(Color.parseColor("#ffffff"));
        btn_194.setTextColor(Color.parseColor("#ffffff"));
        btn_195.setTextColor(Color.parseColor("#ffffff"));
        btn_201.setTextColor(Color.parseColor("#ffffff"));
        btn_202.setTextColor(Color.parseColor("#ffffff"));
        btn_203.setTextColor(Color.parseColor("#ffffff"));
        btn_204.setTextColor(Color.parseColor("#ffffff"));
        btn_205.setTextColor(Color.parseColor("#ffffff"));
        btn_211.setTextColor(Color.parseColor("#ffffff"));
        btn_212.setTextColor(Color.parseColor("#ffffff"));
        btn_213.setTextColor(Color.parseColor("#ffffff"));
        btn_214.setTextColor(Color.parseColor("#ffffff"));
        btn_215.setTextColor(Color.parseColor("#ffffff"));
        btn_221.setTextColor(Color.parseColor("#ffffff"));
        btn_222.setTextColor(Color.parseColor("#ffffff"));
        btn_223.setTextColor(Color.parseColor("#ffffff"));
        btn_224.setTextColor(Color.parseColor("#ffffff"));
        btn_225.setTextColor(Color.parseColor("#ffffff"));
        btn_231.setTextColor(Color.parseColor("#ffffff"));
        btn_232.setTextColor(Color.parseColor("#ffffff"));
        btn_233.setTextColor(Color.parseColor("#ffffff"));
        btn_234.setTextColor(Color.parseColor("#ffffff"));
        btn_235.setTextColor(Color.parseColor("#ffffff"));
        btn_241.setTextColor(Color.parseColor("#ffffff"));
        btn_242.setTextColor(Color.parseColor("#ffffff"));
        btn_243.setTextColor(Color.parseColor("#ffffff"));
        btn_244.setTextColor(Color.parseColor("#ffffff"));
        btn_245.setTextColor(Color.parseColor("#ffffff"));
        btn_11.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_12.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_13.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_14.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_15.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_21.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_22.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_23.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_24.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_25.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_31.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_32.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_33.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_34.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_35.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_41.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_42.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_43.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_44.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_45.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_51.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_52.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_53.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_54.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_55.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_61.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_62.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_63.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_64.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_65.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_71.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_72.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_73.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_74.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_75.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_81.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_82.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_83.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_84.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_85.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_91.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_92.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_93.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_94.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_95.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_101.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_102.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_103.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_104.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_105.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_111.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_112.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_113.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_114.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_115.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_121.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_122.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_123.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_124.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_125.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_131.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_132.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_133.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_134.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_135.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_141.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_142.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_143.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_144.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_145.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_151.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_152.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_153.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_154.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_155.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_161.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_162.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_163.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_164.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_165.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_171.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_172.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_173.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_174.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_175.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_181.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_182.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_183.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_184.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_185.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_191.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_192.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_193.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_194.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_195.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_201.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_202.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_203.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_204.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_205.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_211.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_212.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_213.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_214.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_215.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_221.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_222.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_223.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_224.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_225.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_231.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_232.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_233.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_234.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_235.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_241.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_242.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_243.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_244.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_245.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void weekend() {
        btn_16.setTextColor(Color.parseColor("#ffffff"));
        btn_17.setTextColor(Color.parseColor("#ffffff"));
        btn_26.setTextColor(Color.parseColor("#ffffff"));
        btn_27.setTextColor(Color.parseColor("#ffffff"));
        btn_36.setTextColor(Color.parseColor("#ffffff"));
        btn_37.setTextColor(Color.parseColor("#ffffff"));
        btn_46.setTextColor(Color.parseColor("#ffffff"));
        btn_47.setTextColor(Color.parseColor("#ffffff"));
        btn_56.setTextColor(Color.parseColor("#ffffff"));
        btn_57.setTextColor(Color.parseColor("#ffffff"));
        btn_66.setTextColor(Color.parseColor("#ffffff"));
        btn_67.setTextColor(Color.parseColor("#ffffff"));
        btn_76.setTextColor(Color.parseColor("#ffffff"));
        btn_77.setTextColor(Color.parseColor("#ffffff"));
        btn_86.setTextColor(Color.parseColor("#ffffff"));
        btn_87.setTextColor(Color.parseColor("#ffffff"));
        btn_96.setTextColor(Color.parseColor("#ffffff"));
        btn_97.setTextColor(Color.parseColor("#ffffff"));
        btn_106.setTextColor(Color.parseColor("#ffffff"));
        btn_107.setTextColor(Color.parseColor("#ffffff"));
        btn_116.setTextColor(Color.parseColor("#ffffff"));
        btn_117.setTextColor(Color.parseColor("#ffffff"));
        btn_126.setTextColor(Color.parseColor("#ffffff"));
        btn_127.setTextColor(Color.parseColor("#ffffff"));
        btn_136.setTextColor(Color.parseColor("#ffffff"));
        btn_137.setTextColor(Color.parseColor("#ffffff"));
        btn_146.setTextColor(Color.parseColor("#ffffff"));
        btn_147.setTextColor(Color.parseColor("#ffffff"));
        btn_156.setTextColor(Color.parseColor("#ffffff"));
        btn_157.setTextColor(Color.parseColor("#ffffff"));
        btn_166.setTextColor(Color.parseColor("#ffffff"));
        btn_167.setTextColor(Color.parseColor("#ffffff"));
        btn_176.setTextColor(Color.parseColor("#ffffff"));
        btn_177.setTextColor(Color.parseColor("#ffffff"));
        btn_186.setTextColor(Color.parseColor("#ffffff"));
        btn_187.setTextColor(Color.parseColor("#ffffff"));
        btn_196.setTextColor(Color.parseColor("#ffffff"));
        btn_197.setTextColor(Color.parseColor("#ffffff"));
        btn_206.setTextColor(Color.parseColor("#ffffff"));
        btn_207.setTextColor(Color.parseColor("#ffffff"));
        btn_216.setTextColor(Color.parseColor("#ffffff"));
        btn_217.setTextColor(Color.parseColor("#ffffff"));
        btn_226.setTextColor(Color.parseColor("#ffffff"));
        btn_227.setTextColor(Color.parseColor("#ffffff"));
        btn_236.setTextColor(Color.parseColor("#ffffff"));
        btn_237.setTextColor(Color.parseColor("#ffffff"));
        btn_246.setTextColor(Color.parseColor("#ffffff"));
        btn_247.setTextColor(Color.parseColor("#ffffff"));
        btn_16.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_17.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_26.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_27.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_36.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_37.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_46.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_47.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_56.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_57.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_66.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_67.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_76.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_77.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_86.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_87.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_96.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_97.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_106.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_107.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_116.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_117.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_126.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_127.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_136.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_137.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_146.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_147.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_156.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_157.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_166.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_167.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_176.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_177.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_186.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_187.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_196.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_197.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_206.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_207.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_216.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_217.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_226.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_227.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_236.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_237.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_246.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_247.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_9_12() {
        btn_101.setTextColor(Color.parseColor("#ffffff"));
        btn_102.setTextColor(Color.parseColor("#ffffff"));
        btn_103.setTextColor(Color.parseColor("#ffffff"));
        btn_104.setTextColor(Color.parseColor("#ffffff"));
        btn_105.setTextColor(Color.parseColor("#ffffff"));
        btn_106.setTextColor(Color.parseColor("#ffffff"));
        btn_107.setTextColor(Color.parseColor("#ffffff"));
        btn_111.setTextColor(Color.parseColor("#ffffff"));
        btn_112.setTextColor(Color.parseColor("#ffffff"));
        btn_113.setTextColor(Color.parseColor("#ffffff"));
        btn_114.setTextColor(Color.parseColor("#ffffff"));
        btn_115.setTextColor(Color.parseColor("#ffffff"));
        btn_116.setTextColor(Color.parseColor("#ffffff"));
        btn_117.setTextColor(Color.parseColor("#ffffff"));
        btn_121.setTextColor(Color.parseColor("#ffffff"));
        btn_122.setTextColor(Color.parseColor("#ffffff"));
        btn_123.setTextColor(Color.parseColor("#ffffff"));
        btn_124.setTextColor(Color.parseColor("#ffffff"));
        btn_125.setTextColor(Color.parseColor("#ffffff"));
        btn_126.setTextColor(Color.parseColor("#ffffff"));
        btn_127.setTextColor(Color.parseColor("#ffffff"));
        btn_131.setTextColor(Color.parseColor("#ffffff"));
        btn_132.setTextColor(Color.parseColor("#ffffff"));
        btn_133.setTextColor(Color.parseColor("#ffffff"));
        btn_134.setTextColor(Color.parseColor("#ffffff"));
        btn_135.setTextColor(Color.parseColor("#ffffff"));
        btn_136.setTextColor(Color.parseColor("#ffffff"));
        btn_137.setTextColor(Color.parseColor("#ffffff"));

        btn_101.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_102.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_103.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_104.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_105.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_106.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_107.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_111.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_112.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_113.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_114.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_115.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_116.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_117.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_121.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_122.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_123.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_124.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_125.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_126.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_127.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_131.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_132.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_133.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_134.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_135.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_136.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_137.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_9_12_b() {
        btn_101.setTextColor(Color.parseColor("#ffffff"));
        btn_102.setTextColor(Color.parseColor("#ffffff"));
        btn_103.setTextColor(Color.parseColor("#ffffff"));
        btn_104.setTextColor(Color.parseColor("#ffffff"));
        btn_105.setTextColor(Color.parseColor("#ffffff"));
        btn_111.setTextColor(Color.parseColor("#ffffff"));
        btn_112.setTextColor(Color.parseColor("#ffffff"));
        btn_113.setTextColor(Color.parseColor("#ffffff"));
        btn_114.setTextColor(Color.parseColor("#ffffff"));
        btn_115.setTextColor(Color.parseColor("#ffffff"));
        btn_121.setTextColor(Color.parseColor("#ffffff"));
        btn_122.setTextColor(Color.parseColor("#ffffff"));
        btn_123.setTextColor(Color.parseColor("#ffffff"));
        btn_124.setTextColor(Color.parseColor("#ffffff"));
        btn_125.setTextColor(Color.parseColor("#ffffff"));
        btn_131.setTextColor(Color.parseColor("#ffffff"));
        btn_132.setTextColor(Color.parseColor("#ffffff"));
        btn_133.setTextColor(Color.parseColor("#ffffff"));
        btn_134.setTextColor(Color.parseColor("#ffffff"));
        btn_135.setTextColor(Color.parseColor("#ffffff"));

        btn_101.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_102.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_103.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_104.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_105.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_111.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_112.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_113.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_114.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_115.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_121.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_122.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_123.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_124.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_125.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_131.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_132.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_133.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_134.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_135.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_9_12_c() {
        btn_106.setTextColor(Color.parseColor("#ffffff"));
        btn_107.setTextColor(Color.parseColor("#ffffff"));
        btn_116.setTextColor(Color.parseColor("#ffffff"));
        btn_117.setTextColor(Color.parseColor("#ffffff"));
        btn_126.setTextColor(Color.parseColor("#ffffff"));
        btn_127.setTextColor(Color.parseColor("#ffffff"));
        btn_136.setTextColor(Color.parseColor("#ffffff"));
        btn_137.setTextColor(Color.parseColor("#ffffff"));

        btn_106.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_107.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_116.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_117.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_126.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_127.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_136.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_137.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_13_16() {
        btn_141.setTextColor(Color.parseColor("#ffffff"));
        btn_142.setTextColor(Color.parseColor("#ffffff"));
        btn_143.setTextColor(Color.parseColor("#ffffff"));
        btn_144.setTextColor(Color.parseColor("#ffffff"));
        btn_145.setTextColor(Color.parseColor("#ffffff"));
        btn_146.setTextColor(Color.parseColor("#ffffff"));
        btn_147.setTextColor(Color.parseColor("#ffffff"));
        btn_151.setTextColor(Color.parseColor("#ffffff"));
        btn_152.setTextColor(Color.parseColor("#ffffff"));
        btn_153.setTextColor(Color.parseColor("#ffffff"));
        btn_154.setTextColor(Color.parseColor("#ffffff"));
        btn_155.setTextColor(Color.parseColor("#ffffff"));
        btn_156.setTextColor(Color.parseColor("#ffffff"));
        btn_157.setTextColor(Color.parseColor("#ffffff"));
        btn_161.setTextColor(Color.parseColor("#ffffff"));
        btn_162.setTextColor(Color.parseColor("#ffffff"));
        btn_163.setTextColor(Color.parseColor("#ffffff"));
        btn_164.setTextColor(Color.parseColor("#ffffff"));
        btn_165.setTextColor(Color.parseColor("#ffffff"));
        btn_166.setTextColor(Color.parseColor("#ffffff"));
        btn_167.setTextColor(Color.parseColor("#ffffff"));
        btn_171.setTextColor(Color.parseColor("#ffffff"));
        btn_172.setTextColor(Color.parseColor("#ffffff"));
        btn_173.setTextColor(Color.parseColor("#ffffff"));
        btn_174.setTextColor(Color.parseColor("#ffffff"));
        btn_175.setTextColor(Color.parseColor("#ffffff"));
        btn_176.setTextColor(Color.parseColor("#ffffff"));
        btn_177.setTextColor(Color.parseColor("#ffffff"));

        btn_141.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_142.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_143.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_144.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_145.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_146.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_147.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_151.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_152.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_153.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_154.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_155.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_156.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_157.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_161.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_162.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_163.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_164.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_165.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_166.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_167.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_171.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_172.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_173.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_174.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_175.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_176.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_177.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_13_16_b() {
        btn_141.setTextColor(Color.parseColor("#ffffff"));
        btn_142.setTextColor(Color.parseColor("#ffffff"));
        btn_143.setTextColor(Color.parseColor("#ffffff"));
        btn_144.setTextColor(Color.parseColor("#ffffff"));
        btn_145.setTextColor(Color.parseColor("#ffffff"));
        btn_151.setTextColor(Color.parseColor("#ffffff"));
        btn_152.setTextColor(Color.parseColor("#ffffff"));
        btn_153.setTextColor(Color.parseColor("#ffffff"));
        btn_154.setTextColor(Color.parseColor("#ffffff"));
        btn_155.setTextColor(Color.parseColor("#ffffff"));
        btn_161.setTextColor(Color.parseColor("#ffffff"));
        btn_162.setTextColor(Color.parseColor("#ffffff"));
        btn_163.setTextColor(Color.parseColor("#ffffff"));
        btn_164.setTextColor(Color.parseColor("#ffffff"));
        btn_165.setTextColor(Color.parseColor("#ffffff"));
        btn_171.setTextColor(Color.parseColor("#ffffff"));
        btn_172.setTextColor(Color.parseColor("#ffffff"));
        btn_173.setTextColor(Color.parseColor("#ffffff"));
        btn_174.setTextColor(Color.parseColor("#ffffff"));
        btn_175.setTextColor(Color.parseColor("#ffffff"));

        btn_141.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_142.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_143.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_144.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_145.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_151.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_152.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_153.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_154.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_155.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_161.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_162.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_163.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_164.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_165.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_171.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_172.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_173.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_174.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_175.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }

    public void set_13_16_c() {
        btn_146.setTextColor(Color.parseColor("#ffffff"));
        btn_147.setTextColor(Color.parseColor("#ffffff"));
        btn_156.setTextColor(Color.parseColor("#ffffff"));
        btn_157.setTextColor(Color.parseColor("#ffffff"));
        btn_166.setTextColor(Color.parseColor("#ffffff"));
        btn_167.setTextColor(Color.parseColor("#ffffff"));
        btn_176.setTextColor(Color.parseColor("#ffffff"));
        btn_177.setTextColor(Color.parseColor("#ffffff"));

        btn_146.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_147.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_156.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_157.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_166.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_167.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_176.setBackgroundColor(Color.parseColor("#2a7ccf"));
        btn_177.setBackgroundColor(Color.parseColor("#2a7ccf"));
    }
}
