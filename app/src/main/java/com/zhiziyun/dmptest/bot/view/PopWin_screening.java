package com.zhiziyun.dmptest.bot.view;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.ChildrenCategoryAdapter;
import com.zhiziyun.dmptest.bot.adapter.ParentCategoryAdapter;
import com.zhiziyun.dmptest.bot.ui.fragment.CustomerFragment;

/**
 * 选择PopupWindow
 *
 * @author ansen
 * @create time 2015-10-09
 */
public class PopWin_screening extends PopupWindow implements View.OnClickListener {
    private SelectCategory selectCategory;

    private String[] parentStrings;
    private String[][] childrenStrings;

    private ListView lvParentCategory = null;
    private ListView lvChildrenCategory = null;
    private ParentCategoryAdapter parentCategoryAdapter = null;
    private ChildrenCategoryAdapter childrenCategoryAdapter = null;
    private int p1 = 0, p2 = 0, p3 = 0, parentP = 0;
    private String[] str = {"全部客户类型", "普通客户", "低价值客户", "积极客户", "高价值客户"};

    /**
     * @param parentStrings   字类别数据
     * @param childrenStrings 字类别二位数组
     * @param activity
     * @param selectCategory  回调接口注入
     */
    public PopWin_screening(String[] parentStrings, String[][] childrenStrings, Activity activity, SelectCategory selectCategory) {
        this.selectCategory = selectCategory;
        this.parentStrings = parentStrings;
        this.childrenStrings = childrenStrings;

        View contentView = LayoutInflater.from(activity).inflate(R.layout.view_screening, null);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小

        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);
        this.setHeight(dm.heightPixels * 7 / 10);

        contentView.findViewById(R.id.btn_determine).setOnClickListener(this);
        /* 设置背景显示 */
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pop_bg));
        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);

        //父类别适配器
        lvParentCategory = (ListView) contentView.findViewById(R.id.lv_parent_category);
        parentCategoryAdapter = new ParentCategoryAdapter(activity, parentStrings);
        lvParentCategory.setAdapter(parentCategoryAdapter);

        //子类别适配器
        lvChildrenCategory = (ListView) contentView.findViewById(R.id.lv_children_category);
        childrenCategoryAdapter = new ChildrenCategoryAdapter(activity, str);
        lvChildrenCategory.setAdapter(childrenCategoryAdapter);

        lvParentCategory.setOnItemClickListener(parentItemClickListener);
        lvChildrenCategory.setOnItemClickListener(childrenItemClickListener);
    }

    /**
     * 子类别点击事件
     */
    private OnItemClickListener childrenItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectCategory != null) {
                selectCategory.selectCategory(parentCategoryAdapter.getPos(), position);
                childrenCategoryAdapter.setSelectedPosition(position);
                childrenCategoryAdapter.notifyDataSetChanged();
                switch (parentP) {
                    case 0:
                        p1 = position;
                        break;
                    case 1:
                        p2 = position;
                        break;
                    case 2:
                        p3 = position;
                        break;
                }
            }
        }
    };

    /**
     * 父类别点击事件
     */
    private OnItemClickListener parentItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            parentP = position;
            switch (position) {
                case 0:
                    childrenCategoryAdapter.setSelectedPosition(p1);
                    break;
                case 1:
                    childrenCategoryAdapter.setSelectedPosition(p2);
                    break;
                case 2:
                    childrenCategoryAdapter.setSelectedPosition(p3);
                    break;
            }
            childrenCategoryAdapter.setDatas(childrenStrings[position]);
            childrenCategoryAdapter.notifyDataSetChanged();

            parentCategoryAdapter.setSelectedPosition(position);
            parentCategoryAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_determine:
                try {
                    CustomerFragment.fragment.getTypes(p1);
                    CustomerFragment.fragment.getMark(p2);
                    CustomerFragment.fragment.hasDial(p3);
                    CustomerFragment.fragment.clearAllData();
                    CustomerFragment.fragment.getData(1, "", CustomerFragment.fragment.jsonArray);
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 选择成功回调
     *
     * @author apple
     */
    public interface SelectCategory {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param parentSelectposition   父类别选中下标
         * @param childrenSelectposition 子类别选中下标
         */
        public void selectCategory(int parentSelectposition, int childrenSelectposition);
    }

}
