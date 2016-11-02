package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ShowBrandsBean;
import com.autodesk.shejijia.consumer.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yaoxuehua on 16-10-26.
 */

public class AddBrandShowAdapter extends CommonAdapter<ShowBrandsBean.BrandsBean> {

    private Context context;
    private List<ShowBrandsBean.BrandsBean> datas;
    private int layoutId;
    private Handler handler;
    private List<BtnStatusBean> list;
    private int countNumber = 0;

    public AddBrandShowAdapter(Context context, List<ShowBrandsBean.BrandsBean> datas, int layoutId, Handler handler) {
        super(context, datas, layoutId);
        this.handler = handler;
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
        list = new ArrayList<>();
    }

    @Override
    public void convert(final CommonViewHolder holder, final ShowBrandsBean.BrandsBean brandsBean) {

        BtnStatusBean btnStatusBean;

        if (countNumber < datas.size()) {
            btnStatusBean = new BtnStatusBean();
            btnStatusBean.setCountOffset(holder.getPosition());
            btnStatusBean.setSingleClickOrDoubleBtnCount(2);
            list.add(holder.getPosition(), btnStatusBean);
            countNumber++;
        }


        holder.setText(R.id.brand_name, brandsBean.getBrand_name());
        String storeName = "";
        for (int i = 0; i < brandsBean.getMalls().size(); i++) {

            storeName = storeName + brandsBean.getMalls().get(i).getMall_name() + ",";
        }

        holder.setText(R.id.store_show, storeName);
        holder.setOnClickListener(R.id.checked_img, new ItemImgListener(holder, brandsBean));
        BtnStatusBean btnStatusBeanCompare = list.get(holder.getPosition());

        if (btnStatusBeanCompare.getSingleClickOrDoubleBtnCount() == 2) {

            holder.setBackgroundRes(R.id.checked_img, R.drawable.brand_unchecked);
        } else {

            holder.setBackgroundRes(R.id.checked_img, R.drawable.brand_checked);
        }
    }

    class ItemImgListener implements View.OnClickListener {

        private int position;
        private View convertView;
        private CommonViewHolder holder;
        private ShowBrandsBean.BrandsBean brandsBean;


        public ItemImgListener(final CommonViewHolder holder, final ShowBrandsBean.BrandsBean brandsBean) {

            this.holder = holder;
            this.brandsBean = brandsBean;
        }

        @Override
        public void onClick(View v) {

            Message message = Message.obtain();
            message.obj = brandsBean;

            BtnStatusBean btnStatusBeanImg = list.get(holder.getPosition());
            if (btnStatusBeanImg.getSingleClickOrDoubleBtnCount() == 2) {

                message.what = 1;
                v.setBackgroundResource(R.drawable.brand_checked);
                btnStatusBeanImg.setSingleClickOrDoubleBtnCount(1);
            } else {

                message.what = 2;
                v.setBackgroundResource(R.drawable.brand_unchecked);
                btnStatusBeanImg.setSingleClickOrDoubleBtnCount(2);
            }
            list.set(holder.getPosition(), btnStatusBeanImg);

            handler.sendMessage(message);
        }
    }
}
