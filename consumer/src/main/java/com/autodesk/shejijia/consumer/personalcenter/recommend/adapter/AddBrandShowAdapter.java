package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.CheckedInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;

import java.util.List;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-11-3 .
 * @file AddBrandShowAdapter.java .
 * @brief 展示品牌的适配器.
 */

public class AddBrandShowAdapter extends CommonAdapter<RecommendBrandsBean> {

    private Context context;
    private List<RecommendBrandsBean> datas;
    private int layoutId;
    private Handler handler;
    private List<BtnStatusBean> list;
    private int brandCount = 6;

    public AddBrandShowAdapter(Context context, List<RecommendBrandsBean> datas, int layoutId, Handler handler, List<BtnStatusBean> list,int brandCount) {
        super(context, datas, layoutId);
        this.handler = handler;
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
        this.list = list;
        this.brandCount = brandCount;
    }

    @Override
    public void convert(final CommonViewHolder holder, final RecommendBrandsBean brandsBean) {


        if (brandsBean != null){

            holder.setText(R.id.brand_name, brandsBean.getName());
        }
        String storeName = "";
        for (int i = 0; i < brandsBean.getMalls().size(); i++) {

            if (i == 0 && brandsBean.getMalls().size() == 1){

                storeName = brandsBean.getMalls().get(i).getMall_name();
            }

            if (i > 0 && i < brandsBean.getMalls().size() - 1){

                storeName = storeName + brandsBean.getMalls().get(i).getMall_name() + "、";
            }

            if (i == brandsBean.getMalls().size() - 1 && brandsBean.getMalls().size() != 1){

                storeName = storeName + brandsBean.getMalls().get(i).getMall_name();
            }

        }

        holder.setText(R.id.store_show, storeName);
        holder.setOnClickListener(R.id.checked_img, new ItemImgListener(holder, brandsBean));
        holder.setOnClickListener(R.id.rl_item,new ItemImgListener(holder,brandsBean));
        if (list.size() != 0){

            BtnStatusBean btnStatusBeanCompare = list.get(holder.getPosition());
            if (btnStatusBeanCompare.getSingleClickOrDoubleBtnCount() == 2) {

                holder.setBackgroundRes(R.id.checked_img, R.drawable.brand_unchecked);
            } else {

                holder.setBackgroundRes(R.id.checked_img, R.drawable.brand_checked);
            }
        }
    }

    class ItemImgListener implements View.OnClickListener {

        private int position;
        private View convertView;
        private CommonViewHolder holder;
        private RecommendBrandsBean brandsBean;


        public ItemImgListener(final CommonViewHolder holder, final RecommendBrandsBean brandBean) {

            this.holder = holder;
            this.brandsBean = brandBean;
        }

        @Override
        public void onClick(View v) {

            //装在标志位以及需要品牌
            CheckedInformationBean checkedInformationBean = new CheckedInformationBean();
            //装在品牌
            checkedInformationBean.setRecommendBrandsBean(brandsBean);
            Message message = Message.obtain();
            BtnStatusBean btnStatusBeanCheckedCount;
            BtnStatusBean btnStatusBeanImg = list.get(holder.getPosition());
            int countNumber = 0;
            boolean isCanSend = false;
            for (int i = 0; i < list.size(); i++) {
                btnStatusBeanCheckedCount = list.get(i);

                if (btnStatusBeanCheckedCount.getSingleClickOrDoubleBtnCount() == 1) {

                    countNumber++;
                }
            }

            if (btnStatusBeanImg.getSingleClickOrDoubleBtnCount() == 2 && countNumber <= brandCount - 1) {

                message.what = 1;
                v.findViewById(R.id.checked_img).setBackgroundResource(R.drawable.brand_checked);
                btnStatusBeanImg.setSingleClickOrDoubleBtnCount(1);
                isCanSend = true;
            } else {

                if (btnStatusBeanImg.getSingleClickOrDoubleBtnCount() == 1) {

                    message.what = 2;
                    v.findViewById(R.id.checked_img).setBackgroundResource(R.drawable.brand_unchecked);
                    btnStatusBeanImg.setSingleClickOrDoubleBtnCount(2);
                    isCanSend = true;
                }
            }

            //装在标志位，并发送
            if (isCanSend) {
                list.set(holder.getPosition(), btnStatusBeanImg);
                checkedInformationBean.setList(list);
                message.obj = checkedInformationBean;
                handler.sendMessage(message);
                isCanSend = false;
            }
        }
    }

    /**
     * 改变Tag
     */
    public void changeListTag(List<BtnStatusBean> list, List<RecommendBrandsBean> datas) {

        this.list = list;
        this.datas = datas;
    }
}
