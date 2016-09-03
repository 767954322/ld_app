package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBody;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;

import java.util.List;

/**
 * @author gumenghao .
 * @version 1.0 .
 * @date 16-7-13
 * @file MessageCenterAdapter.java  .
 * @brief 消息中心 .
 */
public class MessageCenterAdapter extends BaseAdapter {

    private Context mContext;
    private Boolean ifIsDesiner;
    private List<MessageCenterBean.MessagesBean> mCasesEntities;


    public MessageCenterAdapter(Context mContext, List<MessageCenterBean.MessagesBean> mCasesEntities, Boolean ifIsDesiner) {
        this.mContext = mContext;
        this.mCasesEntities = mCasesEntities;
        this.ifIsDesiner = ifIsDesiner;
    }

    @Override
    public int getCount() {
        return mCasesEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCasesEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyHolder myHolder;
        if (convertView == null) {
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_messagecenter_msg, null);
            myHolder.item_msg_content = (TextView) convertView.findViewById(R.id.tv_msg_content);
            myHolder.tv_msg_title = (TextView) convertView.findViewById(R.id.tv_msg_title);
            myHolder.tv_msg_date = (TextView) convertView.findViewById(R.id.tv_msg_date);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        MessageCenterBean.MessagesBean messagesBean = mCasesEntities.get(position);

        String body = messagesBean.getBody();
        String title = messagesBean.getTitle();
        String sent_time = messagesBean.getSent_time();

        String timeMY = "";
        if (StringUtils.isNumeric(sent_time)) {
            Long aLong = Long.valueOf(sent_time);
            timeMY = DateUtil.showDate(aLong);
        }

        title = TextUtils.isEmpty(title) ? "消息中心" : title;

        myHolder.tv_msg_title.setText(title);
        myHolder.tv_msg_date.setText(timeMY);

        if (!TextUtils.isEmpty(body) && body.contains("&quot;")) {
            MessageCenterBody messageCenterBody = GsonUtil.jsonToBean(body.replaceAll("&quot;", "\""), MessageCenterBody.class);

            Log.d("test", messageCenterBody.toString());
            if (ifIsDesiner) {
                myHolder.item_msg_content.setText(messageCenterBody.getFor_designer());
            } else {
                myHolder.item_msg_content.setText(messageCenterBody.getFor_consumer());
            }
        } else {
            myHolder.item_msg_content.setText(body);
        }


        return convertView;
    }

    class MyHolder {

        private TextView tv_msg_date;
        private TextView tv_msg_title;
        private TextView item_msg_content;

    }

}
