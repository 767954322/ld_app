package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBody;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;

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
    private List<MessageCenter.Message> mCasesEntities;


    public MessageCenterAdapter(Context mContext, List<MessageCenter.Message> mCasesEntities, Boolean ifIsDesiner) {
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
            myHolder.item_msg_content = (TextView) convertView.findViewById(R.id.item_msg_content);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        String body = mCasesEntities.get(position).getBody();
        MessageCenterBody messageCenterBody = GsonUtil.jsonToBean(body.replaceAll("&quot;", "\""), MessageCenterBody.class);

        Log.d("test",messageCenterBody.toString());
        if (ifIsDesiner) {
            myHolder.item_msg_content.setText(messageCenterBody.getFor_designer());
        } else {
            myHolder.item_msg_content.setText(messageCenterBody.getFor_consumer());
        }


        return convertView;
    }

    class MyHolder {

        private TextView item_msg_content;

    }

}
