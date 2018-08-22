package com.zhiziyun.dmptest.bot.mode.originality.uploadmaterial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.IsEmpty;

import java.util.List;

/**
 * Created by zhiziyun on 2018/7/20.
 */

public class MaterialAdapter extends RecyclerView.Adapter {
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_TEXTVIEW = 1;
    private static final int TYPE_FOOT = 2;
    private Context mContext;
    private List<TemplatePackageAddItems> mList;
    private ItemClickListener itemClickListener;
    private ItemClickDataListener<List<TemplatePackageAddItems>> mItemClickDataListener;

    public MaterialAdapter(Context mContext, List<TemplatePackageAddItems> templatePackageAddItemses) {
        this.mContext = mContext;
        this.mList = templatePackageAddItemses;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemClickDataListener(ItemClickDataListener<List<TemplatePackageAddItems>> itemClickDataListener) {
        mItemClickDataListener = itemClickDataListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_IMAGE) {
            view = layoutInflater.inflate(R.layout.create_image_item, parent, false);
            holder = new ImageHodler(view);
        }
        if (viewType == TYPE_TEXTVIEW) {
            view = layoutInflater.inflate(R.layout.create_text_item, parent, false);
            holder = new TextviewHodler(view);
        }
        if (viewType == TYPE_FOOT) {
            view = layoutInflater.inflate(R.layout.type_foot_item, parent, false);
            holder = new FooterViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).mCommit.setTag(R.string.app_name, mList);
        } else if (holder instanceof TextviewHodler) {
            ((TextviewHodler) holder).mTitleTv.setText(mList.get(position).getAddItemName());
            if (!IsEmpty.string(mList.get(position).getTextMaxNum())) {
                ((TextviewHodler) holder).mMaxEt.setHint("2-" + mList.get(position).getTextMaxNum() + "个字");
            }
            if (!IsEmpty.string(mList.get(position).getEditItemText())) {
                ((TextviewHodler) holder).mMaxEt.setText(mList.get(position).getEditItemText());
            }
            ((TextviewHodler) holder).itemView.setTag(R.string.app_name, position);
            ((TextviewHodler) holder).mMaxEt.addTextChangedListener(new TextWatcher() {

                private String mEditChar;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mEditChar = null;
                    mEditChar = charSequence.toString();
                    mList.get(position).setEditItemText(mEditChar);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else {
            ((ImageHodler) holder).itemView.setTag(R.string.app_name, position);
            ((ImageHodler) holder).mImageTv.setText(mList.get(position).getAddItemName());
            if (!IsEmpty.string(mList.get(position).getImagePicture())) {
                Glide.with(mContext).load(mList.get(position).getImagePicture()).into(((ImageHodler) holder).mMaterialLogo);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button mCommit;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mCommit = (Button) itemView.findViewById(R.id.commit_rl);
            mCommit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                mItemClickDataListener.OnItemClick(view, (List<TemplatePackageAddItems>) mCommit.getTag(R.string.app_name));
            }
        }
    }

    class TextviewHodler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTitleTv;
        private final EditText mMaxEt;

        public TextviewHodler(View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.title_tv);
            mMaxEt = itemView.findViewById(R.id.max_et);
            mMaxEt.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.OnItemClick(view, (Integer) itemView.getTag(R.string.app_name));
            }
        }
    }

    class ImageHodler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RelativeLayout mImageLy;
        private final ImageView mMaterialLogo;
        private final TextView mImageTv;

        public ImageHodler(View itemView) {
            super(itemView);
            mImageLy = itemView.findViewById(R.id.image_ly);
            mMaterialLogo = itemView.findViewById(R.id.material_logo);
            mImageTv = itemView.findViewById(R.id.image_tv);
            mImageLy.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.OnItemClick(view, (Integer) itemView.getTag(R.string.app_name));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return TYPE_FOOT;
        } else if (mList.get(position).getAddItemType().equals("IMAGE")) {
            return TYPE_IMAGE;
        } else {
            return TYPE_TEXTVIEW;
        }
    }

    public interface ItemClickListener {
        void OnItemClick(View v, int position);
    }

    public interface ItemClickDataListener<T> {
        void OnItemClick(View v, List<TemplatePackageAddItems> data);
    }
}
