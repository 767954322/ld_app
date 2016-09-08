package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.ImagesBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * Created by：yangxuewu on 16/7/20 16:05
 * auguest 新的案例详情适配器
 */
public class CaseLibraryAdapter extends BaseAdapter {

    private static final int SHARE_OR_SPRAISE = 1;
    private static final int CONTENT_DES = 2;
    private static final int IMAGE = 3;

    public interface OnShareOrPraiseListener {
        void onShareClick();//分享按钮

        void onPraiseClick();//点赞按钮
    }

    private OnShareOrPraiseListener mShareOrPraiseListener;

    public void setShareOrPraiseListener(OnShareOrPraiseListener mShareOrPraiseListener) {
        this.mShareOrPraiseListener = mShareOrPraiseListener;
    }

    public CaseLibraryAdapter(Context context, List<ImagesBean> items) {
        this.context = context;
//        removeHead(items);
        this.items = items;
    }

    private void removeHead(List<ImagesBean> allItems) {
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).is_primary()) {
                allItems.remove(i);
            }

        }
        this.items = allItems;

    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View convertView = null;
//        convertView = LayoutInflater.from(context).inflate(getItemViewType(position),null);
//        ViewHolder holder = (ViewHolder) convertView.getTag();
//        if (holder == null){
//            holder = new ViewHolder();
//        }
//        holder.mCaseLibraryLImage = (ImageView) convertView.findViewById(R.id.case_library_item_iv);
//        holder.mLlShare = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);
//        holder.mLlPraise = (LinearLayout) convertView.findViewById(R.id.rl_thumb_up);
//        if (holder.mCaseLibraryLImage != null){
//            if (!items.get(position).is_primary()) {
//                final String imageUrl = items.get(position).getFile_url() + Constant.CaseLibraryDetail.JPG;
////            ImageUtils.displayIconImage(imageUrl, holder.mCaseLibraryLImage);
//                ImageUtils.loadImageIcon(holder.mCaseLibraryLImage, imageUrl);
//            } else {
//                holder.mCaseLibraryLImage.setVisibility(View.GONE);
//            }
//        }
//        if (holder.mLlShare != null && holder.mLlPraise != null){
//            if (holder.mLlShare != null && holder.mLlPraise != null) {
//                if (mShareOrPraiseListener != null) {
//                    holder.mLlShare.setClickable(true);
//                    holder.mLlShare.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mShareOrPraiseListener.onShareClick();
//                        }
//                    });
//                }
//
//                if (holder.mLlPraise != null) {
//                    holder.mLlPraise.setClickable(true);
//                    holder.mLlPraise.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mShareOrPraiseListener.onPraiseClick();
//                        }
//                    });
//                }
//            }
//        }


//        if (convertView == null || convertView.getTag() == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.case_library_new_item, null);
//            holder = new ViewHolder();
//            holder.mCaseLibraryLImage = (ImageView) convertView.findViewById(R.id.case_library_item_iv);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        if (!items.get(position).is_primary()) {
//            final String imageUrl = items.get(position).getFile_url() + Constant.CaseLibraryDetail.JPG;
////            ImageUtils.displayIconImage(imageUrl, holder.mCaseLibraryLImage);
//            ImageUtils.loadImageIcon(holder.mCaseLibraryLImage, imageUrl);
//        } else {
//            holder.mCaseLibraryLImage.setVisibility(View.GONE);
//        }

//        convertView.setTag(items.get(position));

        ViewHolder holder;
        switch (getItemViewType(position)) {
            case SHARE_OR_SPRAISE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.caselibrary_head, null);
                    holder = new ViewHolder();
                    holder.mLlShare = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);
                    holder.mLlPraise = (LinearLayout) convertView.findViewById(R.id.rl_thumb_up);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (holder.mLlShare != null && holder.mLlPraise != null) {
                    if (mShareOrPraiseListener != null) {
                        holder.mLlShare.setClickable(true);
                        holder.mLlShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mShareOrPraiseListener.onShareClick();
                            }
                        });
                    }

                    if (holder.mLlPraise != null) {
                        holder.mLlPraise.setClickable(true);
                        holder.mLlPraise.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mShareOrPraiseListener.onPraiseClick();
                            }
                        });
                    }
                }

                break;
            case CONTENT_DES:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.case_library_text, null);
                }
                break;
            case IMAGE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.case_library_new_item, null);
                    holder = new ViewHolder();
                    holder.mCaseLibraryLImage = (ImageView) convertView.findViewById(R.id.case_library_item_iv);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (!items.get(position).is_primary()) {
                    final String imageUrl = items.get(position).getFile_url() + Constant.CaseLibraryDetail.JPG;
//            ImageUtils.displayIconImage(imageUrl, holder.mCaseLibraryLImage);
                    ImageUtils.loadImageIcon(holder.mCaseLibraryLImage, imageUrl);
                } else {
                    holder.mCaseLibraryLImage.setVisibility(View.GONE);
                }

                break;
            default:
                break;
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 1:
                return SHARE_OR_SPRAISE;
            case 2:
                return CONTENT_DES;
            default:
                return IMAGE;
        }
    }

    static class ViewHolder {
        public ImageView mCaseLibraryLImage;
        public LinearLayout mLlShare;
        public LinearLayout mLlPraise;
    }

    private List<ImagesBean> items;
    private Context context;
}
