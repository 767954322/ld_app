package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.widget.SwipePullListView;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.MessageCenterAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenterBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author gumenghao .
 * @version 1.0 .
 * @date 16-6-12
 * @file MessageCenterActivity.java  .
 * @brief 消息中心 .
 */
public class MessageCenterActivity extends NavigationBarActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, CheckBox.OnCheckedChangeListener, MessageCenterAdapter.DeleteMessage {

    private MemberEntity mMemberEntity;
    private TextView nav_right_textView;
    private TextView tv_msg_delete_all;
    private CheckBox cb_msg_delete_all;
    private RelativeLayout rl_msg_delete_msgs;
    private List<MessageCenterBean.MessagesBean> listMessagesBeans;
    private List<Integer> listpositions;
    private String needs_id;
    private String designer_id;
    private String message_type;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_messagecenter;
    }

    @Override
    protected void initView() {
        rl_msg_delete_msgs = (RelativeLayout) findViewById(R.id.rl_msg_delete_msgs);
        mIbnBack = (ImageButton) findViewById(R.id.nav_left_imageButton);
        nav_right_textView = (TextView) findViewById(R.id.nav_right_textView);
        tv_msg_delete_all = (TextView) findViewById(R.id.tv_msg_delete_all);
        cb_msg_delete_all = (CheckBox) findViewById(R.id.cb_msg_delete_all);
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        mListView = (SwipePullListView) findViewById(R.id.message_center_list_view);
        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);

        mIvEmpty = ((ImageView) mFooterView.findViewById(R.id.iv_default_empty));

        mListView.addFooterView(mFooterView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mContext = this;
        setTitleForNavbar(UIUtils.getString(R.string.title_messagecenter));
        listMessagesBeans = new ArrayList<>();
        listpositions = new ArrayList<>();
        nav_right_textView.setVisibility(View.VISIBLE);
        nav_right_textView.setTextColor(Color.BLUE);
        nav_right_textView.setText("编辑");
        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            ifIsDesiner = true;
        } else {
            ifIsDesiner = false;
        }

        if (messageCenterAdapter == null) {

            messageCenterAdapter = new MessageCenterAdapter(mContext, mCasesEntities, ifIsDesiner, mListView.getRightViewWidth());
        }

        mListView.setAdapter(messageCenterAdapter);
        mPullToRefreshLayout.autoRefresh();
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        needs_id = getIntent().getStringExtra(Constant.MessageCenterActivityKey.NEEDS_ID);
        designer_id = getIntent().getStringExtra(Constant.MessageCenterActivityKey.DESINER_ID);
        message_type = getIntent().getStringExtra(Constant.MessageCenterActivityKey.MESSAGE_TYPE);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIbnBack.setOnClickListener(this);
        tv_msg_delete_all.setOnClickListener(this);
        cb_msg_delete_all.setOnCheckedChangeListener(this);
        nav_right_textView.setOnClickListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
        messageCenterAdapter.setDeleteMessageListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_imageButton:
                finish();
                break;
            case R.id.nav_right_textView://编辑

                cb_msg_delete_all.setChecked(false);
                boolean changeStatus_editor = messageCenterAdapter.getEditor() ? false : true;
                if (changeStatus_editor) {
                    nav_right_textView.setText("取消");
                    rl_msg_delete_msgs.setVisibility(View.VISIBLE);
                } else {
                    nav_right_textView.setText("编辑");
                    rl_msg_delete_msgs.setVisibility(View.GONE);
                }
                messageCenterAdapter.setEditor(changeStatus_editor);
                messageCenterAdapter.clearChose();
                break;
            case R.id.tv_msg_delete_all:

                //删除
                Set<Integer> integers = messageCenterAdapter.getmCheckeds();
                listMessagesBeans.clear();
                if (integers.size() > 0) {
                    Iterator<Integer> it = integers.iterator();
                    while (it.hasNext()) {
                        int id = it.next();
                        listMessagesBeans.add(mCasesEntities.get(id));
                        listpositions.add(id);
                    }
                    deleteMsgs(listpositions, listMessagesBeans, null);
                } else {
                    Toast.makeText(MessageCenterActivity.this, "请选择删除条目", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {

            messageCenterAdapter.addAll();

        } else {

            messageCenterAdapter.clearChose();

        }

    }

    //删除单条消息
    @Override
    public void deleteMsgs(final List<Integer> msgListpositions, final List<MessageCenterBean.MessagesBean> listMessagesBeans, final View convertView) {

        StringBuffer sb_msg_ids = new StringBuffer();
        for (int i = 0; i < listMessagesBeans.size(); i++) {
            sb_msg_ids.append(listMessagesBeans.get(i).getMessage_id() + ",");
        }
        String msg_ids = sb_msg_ids.toString().substring(0, sb_msg_ids.length() - 1);

        MPServerHttpManager.getInstance().deleteMessage(mMemberEntity.getAcs_member_id(), msg_ids, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "删除失败,请重新删除", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                //请求成功后删除本地数据
                for (int i = 0; i < listMessagesBeans.size(); i++) {
                    --offset;
                    mCasesEntities.remove(listMessagesBeans.get(i));
                }
                if (null != convertView) {
                    mListView.hiddenRight(convertView);
                } else {
                    //请求成功后删除本地数据
                    messageCenterAdapter.setEditor(false);
                    cb_msg_delete_all.setChecked(false);
                    rl_msg_delete_msgs.setVisibility(View.GONE);
                    nav_right_textView.setText("编辑");
                }
                messageCenterAdapter.clearChose();
            }
        });

    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        offset = 0;
        showListView();
        getMessageData(REFRESH_STATUS);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        getMessageData(LOADMORE_STATUS);

    }

    //是否显示ListView.
    private void showListView() {
        mPullToRefreshLayout.setVisibility(View.VISIBLE);
        mRlEmpty.setVisibility(View.GONE);
    }

    //获取消息数据
    public void getMessageData(final String state) {

        MPServerHttpManager.getInstance().getNewsMessageCenterMessages(designer_id, needs_id, message_type, offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (state.equals(REFRESH_STATUS)) {
                    mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                } else {
                    mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
                ApiStatusUtil.getInstance().apiStatuError(volleyError, MessageCenterActivity.this);
                hideFooterView(mCasesEntities);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                MessageCenterBean messageCenter = GsonUtil.jsonToBean(jsonString, MessageCenterBean.class);
                updateViewFromData(messageCenter.getMessages(), state);
            }
        });

    }

    private void updateViewFromData(List<MessageCenterBean.MessagesBean> messages, String state) {
        switch (state) {
            case REFRESH_STATUS:
                offset = 10;
                mCasesEntities.clear();
                break;
            case LOADMORE_STATUS:
                offset += 10;
                break;
        }

        mCasesEntities.addAll(messages);
        hideFooterView(mCasesEntities);
        messageCenterAdapter.notifyDataSetChanged();
        if (state.equals(REFRESH_STATUS)) {
            mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        } else {
            mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    /**
     * 是否隐藏底部布局
     *
     * @param list 数据集合
     */
    private void hideFooterView(List<MessageCenterBean.MessagesBean> list) {
        if (list != null && list.size() > 0) {
            mRlEmpty.setVisibility(View.GONE);
        } else {
            mRlEmpty.setVisibility(View.VISIBLE);
        }
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.photopicker_thumbnail_placeholder);
        mIvEmpty.setImageBitmap(bmp);
        mTvEmptyMessage.setText(R.string.no_designer_case);
        WindowManager wm = (WindowManager) MessageCenterActivity.this.getSystemService(MessageCenterActivity.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams layoutParams = mRlEmpty.getLayoutParams();
        mRlEmpty.getLayoutParams();
        layoutParams.height = height - 10;
        mRlEmpty.setLayoutParams(layoutParams);
        mTvEmptyMessage.setText(UIUtils.getString(R.string.no_designer_case));
        mIvEmpty.setBackground(null);
    }

    private Context mContext;
    private View mFooterView;
    private RelativeLayout mRlEmpty;
    private boolean ifIsDesiner = true;

    private final String REFRESH_STATUS = "refresh";
    private final String LOADMORE_STATUS = "loadmore";
    private int offset = 0;
    private int limit = 10;
    private TextView mTvEmptyMessage;
    private ImageView mIvEmpty;
    private ImageButton mIbnBack;

    private SwipePullListView mListView;
    private MessageCenterAdapter messageCenterAdapter;
    private PullToRefreshLayout mPullToRefreshLayout;
    private List<MessageCenterBean.MessagesBean> mCasesEntities = new ArrayList<MessageCenterBean.MessagesBean>();

}
