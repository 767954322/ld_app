package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBody;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBodyNew;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
    private List<MessageCenterBean.MessagesBean> listMessagesBeans;
    private List<Integer> listposition;
    private final Set<Integer> mCheckeds;


    public MessageCenterAdapter(Context mContext, List<MessageCenterBean.MessagesBean> mCasesEntities, Boolean ifIsDesiner, int right_wid) {
        mCheckeds = new HashSet<>();
        this.mContext = mContext;
        this.mCasesEntities = mCasesEntities;
        this.ifIsDesiner = ifIsDesiner;
        this.right_wid = right_wid;
        listMessagesBeans = new ArrayList<>();
        listposition = new ArrayList<>();
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
        String sent_time = messagesBean.getSent_time();
        String timeMY = !TextUtils.isEmpty(sent_time) ? DateUtil.showDate(new Long(sent_time)) : "";
        String title = "";
        if (null != messagesBean.getCommand() && messagesBean.getCommand().equals("COMMAND_CONSTRUCTION_MESSAGE")) {

            if (!TextUtils.isEmpty(body)) {
                MessageCenterBodyNew messageCenterBodyNew = GsonUtil.jsonToBean(body, MessageCenterBodyNew.class);
                title = messageCenterBodyNew.getDisplay_message().getSummary();
                StringBuffer sb_body = new StringBuffer();
                sb_body.append("<body>");
                if (null != messageCenterBodyNew.getDisplay_message() && null != messageCenterBodyNew.getDisplay_message().getDetail_items()) {
                    int number = messageCenterBodyNew.getDisplay_message().getDetail_items().size();
                    for (int i = 0; i < number; i++) {
                        sb_body.append(messageCenterBodyNew.getDisplay_message().getDetail_items().get(i));
                        if (i != number - 1)
                            sb_body.append("<br>");
                    }
                }
                sb_body.append("</body>");
                myHolder.item_msg_content.setText(Html.fromHtml(sb_body.toString()));
            }

        } else {

            title = TextUtils.isEmpty(messagesBean.getTitle()) ? "消息中心" : messagesBean.getTitle();
            if (title.length() > 12)
                title = title.substring(0, 12) + "...";

            String show_Body = "";
            if (!TextUtils.isEmpty(body) && body.contains("&quot;")) {
                MessageCenterBody messageCenterBody = GsonUtil.jsonToBean(body.replaceAll("&quot;", "\""), MessageCenterBody.class);
                show_Body = ifIsDesiner ? messageCenterBody.getFor_designer().replace("&gt;", ">") : messageCenterBody.getFor_consumer().replace("&gt;", ">");
            } else {
                show_Body = body.replace("&gt;", ">");
            }
            myHolder.item_msg_content.setText(show_Body);
        }

        myHolder.tv_msg_title.setText(title);
        myHolder.tv_msg_date.setText(timeMY);
        // 设置添加消息左滑删除功能
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(right_wid, LinearLayout.LayoutParams.MATCH_PARENT);
        myHolder.item_right.setLayoutParams(lp2);

        final View finalConvertView = convertView;
        myHolder.item_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMessagesBeans.clear();
                listMessagesBeans.add(messagesBean);
                listposition.add(position);
                deleteMessage.deleteMsgs(listposition, listMessagesBeans, finalConvertView);

            }
        });
        myHolder.checkBox.setChecked(mCheckeds.contains(position));
        Log.d("tezst", position + ":" + mCheckeds.size() + "");
        //checkBox设置
        if (!isClickEditor) {
            myHolder.checkBox.setVisibility(View.GONE);
        } else {
            myHolder.checkBox.setVisibility(View.VISIBLE);
        }

        myHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckeds.contains(position)) {
                    mCheckeds.remove(position);
                } else {
                    mCheckeds.add(position);
                }
            }
        });

        //checkBox监听
//        myHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    mCheckeds.add(position);
//                } else {
//                    mCheckeds.remove(position);
//                    Log.d("tezst",position+"被从Set集合清楚了删除了");
//                }
//
//            }
//        });

//        final String finalShow_Body = show_Body;
//        myHolder.item_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MessageCenterDetailActivity.class);
//                intent.putExtra("messagesBean", messagesBean);
//                intent.putExtra("show_Body", finalShow_Body);
//                mContext.startActivity(intent);
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

    public void setDeleteMessageListener(DeleteMessage deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public interface DeleteMessage {
        void deleteMsgs(List<Integer> listpositions, List<MessageCenterBean.MessagesBean> listMessagesBeans, View convertView);
    }

    public void changeVisibility(boolean ifNeedVisibility, View view) {

        if (ifNeedVisibility)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);

    }

    class MyHolder {

        private TextView item_msg_content;
        private TextView tv_msg_title;
        private TextView tv_msg_date;
        private CheckBox checkBox;
        private View item_right, item_left;

    }

}
