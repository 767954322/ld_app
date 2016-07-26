package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * Created by：yangxuewu on 16/7/20 16:05
 * auguest 新的案例详情适配器
 */
public class CaseLibraryAdapter extends BaseAdapter {


    public CaseLibraryAdapter(Context context, List<CaseDetailBean.ImagesEntity> items) {
        this.context = context;
//        removeHead(items);
        this.items = items;
    }

    private void removeHead(List<CaseDetailBean.ImagesEntity> allItems) {
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).isIs_primary()) {
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
    public View getView(int position, View view, ViewGroup parent) {
        View convertView = null;
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.case_library_new_item, null);
            holder = new ViewHolder();
            holder.mCaseLibraryLImage = (ImageView) convertView.findViewById(R.id.case_library_item_iv);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!items.get(position).isIs_primary()){
            final String imageUrl = items.get(position).getFile_url() + Constant.CaseLibraryDetail.JPG;
            ImageUtils.displayIconImage(imageUrl, holder.mCaseLibraryLImage);
        }else {
            holder.mCaseLibraryLImage.setVisibility(View.GONE);
        }

        convertView.setTag(items.get(position));
        return convertView;
    }

    static class ViewHolder {
        public ImageView mCaseLibraryLImage;
    }

    private List<CaseDetailBean.ImagesEntity> items;
    private Context context;
}
