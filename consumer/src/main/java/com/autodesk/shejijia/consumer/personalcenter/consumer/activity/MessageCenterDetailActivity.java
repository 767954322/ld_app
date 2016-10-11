package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBean;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

/**
 * Created by allengu on 16-10-11.
 */
public class MessageCenterDetailActivity extends NavigationBarActivity {


    private MessageCenterBean.MessagesBean messagesBean;
    private ImageButton nav_left_imageButton;
    private TextView nav_title_textView;
    private TextView tv_msg_date;
    private String show_Body;
    private TextView tv_msg_content;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_messagecenter_detail;
    }

    @Override
    protected void initView() {

        nav_left_imageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        nav_title_textView = (TextView) findViewById(R.id.nav_title_textView);
        tv_msg_date = (TextView) findViewById(R.id.tv_msg_date);
        tv_msg_content = (TextView) findViewById(R.id.tv_msg_content);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        messagesBean = (MessageCenterBean.MessagesBean) getIntent().getSerializableExtra("messagesBean");
        show_Body = getIntent().getStringExtra("show_Body");
        String title = messagesBean.getTitle();
        String sent_time = messagesBean.getSent_time();

        String timeMY = "";
        if (StringUtils.isNumeric(sent_time)) {
            Long aLong = Long.valueOf(sent_time);
            timeMY = DateUtil.showDate(aLong);
        }

        nav_title_textView.setText(title);
        tv_msg_date.setText(timeMY);
        tv_msg_content.setText(show_Body);

    }

}
