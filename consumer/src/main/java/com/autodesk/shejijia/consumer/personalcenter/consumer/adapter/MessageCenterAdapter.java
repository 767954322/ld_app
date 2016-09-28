package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBody;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gumenghao .
 * @version 1.0 .
 * @date 16-7-13
 * @file MessageCenterAdapter.java  .
 * @brief 消息中心 .
 */
public class MessageCenterAdapter extends BaseAdapter {
    private DeleteMessage deleteMessage;
    private int right_wid;
    private Context mContext;
    private Boolean ifIsDesiner;
    private boolean isClickEditor = false;
    private boolean isClickedAll = false;


    private List<MessageCenterBean.MessagesBean> mCasesEntities;
    private final Set<Integer> mCheckeds;


    public MessageCenterAdapter(Context mContext, List<MessageCenterBean.MessagesBean> mCasesEntities, Boolean ifIsDesiner, int right_wid) {
        mCheckeds = new HashSet<>();
        this.mContext = mContext;
        this.mCasesEntities = mCasesEntities;
        this.ifIsDesiner = ifIsDesiner;
        this.right_wid = right_wid;
    }

    public Set<Integer> getmCheckeds() {
        return mCheckeds;
    }

    public void setEditor(boolean status) {
        isClickEditor = status;
    }

    public boolean getEditor() {
        return isClickEditor;
    }

    public boolean getClickedAll() {
        return isClickedAll;
    }

    public void setClickedAll(boolean clickedAll) {
        isClickedAll = clickedAll;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyHolder myHolder;
        if (convertView == null) {
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_messagecenter_msg, null);
            myHolder.item_msg_content = (TextView) convertView.findViewById(R.id.tv_msg_content);
            myHolder.tv_msg_title = (TextView) convertView.findViewById(R.id.tv_msg_title);
            myHolder.tv_msg_date = (TextView) convertView.findViewById(R.id.tv_msg_date);
            myHolder.checkBox = (CheckBox) convertView.findViewById(R.id.item_msg_checkout);
            myHolder.item_left = (View) convertView.findViewById(R.id.item_relayout_left);
            myHolder.item_right = (View) convertView.findViewById(R.id.item_relayout_right);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        final MessageCenterBean.MessagesBean messagesBean = mCasesEntities.get(position);

        String body = messagesBean.getBody();
        String title = messagesBean.getTitle();
        String sent_time = messagesBean.getSent_time();

        String timeMY = "";
        if (StringUtils.isNumeric(sent_time)) {
            Long aLong = Long.valueOf(sent_time);
            timeMY = DateUtil.showDate(aLong);
        }
        title = TextUtils.isEmpty(title) ? "消息中心" : title;

        if (title.length() > 12) {
            title = title.substring(0, 12) + "...";
        }
        myHolder.tv_msg_title.setText(title);
        myHolder.tv_msg_date.setText(timeMY);

        if (!TextUtils.isEmpty(body) && body.contains("&quot;")) {
            MessageCenterBody messageCenterBody = GsonUtil.jsonToBean(body.replaceAll("&quot;", "\""), MessageCenterBody.class);

            Log.d("test", messageCenterBody.toString());
            if (ifIsDesiner) {
                myHolder.item_msg_content.setText(messageCenterBody.getFor_designer().replace("&gt;", ">"));
            } else {
                myHolder.item_msg_content.setText(messageCenterBody.getFor_consumer().replace("&gt;", ">"));
            }
        } else {
            myHolder.item_msg_content.setText(body.replace("&gt;", ">"));
        }
        // 设置添加消息左滑删除功能
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        myHolder.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(right_wid, LinearLayout.LayoutParams.MATCH_PARENT);
        myHolder.item_right.setLayoutParams(lp2);

        myHolder.item_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "左侧", Toast.LENGTH_SHORT).show();
            }
        });
        final View finalConvertView = convertView;
        myHolder.item_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteMessage.deleteOneMsg(position, messagesBean, finalConvertView);

            }
        });
//        myHolder.checkBox.setChecked(mCheckeds.contains(position));
//        //checkBox设置
//        if (!isClickEditor) {
//            myHolder.checkBox.setVisibility(View.GONE);
//        } else {
//            myHolder.checkBox.setVisibility(View.VISIBLE);
//        }
//        //checkBox监听
//        myHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    mCheckeds.add(position);
//                } else {
//                    mCheckeds.remove(position);
//                }
//
//            }
//        });

        return convertView;
    }

    public void addAll() {
        for (int i = 0; i < mCasesEntities.size(); i++) {
            mCheckeds.add(i);
        }
        this.notifyDataSetChanged();
    }

    public void clearChose() {
        mCheckeds.clear();
        this.notifyDataSetChanged();
    }

    class MyHolder {

        private TextView tv_msg_date;
        private TextView tv_msg_title;
        private TextView item_msg_content;
        private View item_left;

        private CheckBox checkBox;
        private View item_right;

    }

    public void setDeleteMessageListener(DeleteMessage deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public interface DeleteMessage {
        public void deleteOneMsg(int position, MessageCenterBean.MessagesBean messagesBean,View convertView);
    }

}
