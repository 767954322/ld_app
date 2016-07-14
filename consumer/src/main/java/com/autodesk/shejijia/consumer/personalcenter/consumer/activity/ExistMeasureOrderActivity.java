package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.PinnedHeaderExpandableAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationListEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.PinnedHeaderExpandableListView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnDismissListener;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerView;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7 上午10:29
 * @file ExistMeasureOrderActivity.java  .
 * @brief 附用历史量房记录 .
 */
public class ExistMeasureOrderActivity extends NavigationBarActivity implements View.OnClickListener, OnDismissListener, OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_exist_measure_order;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_measure_time = (TextView) findViewById(R.id.tv_exsit_measure_detail_time);
        rl_measure_time = (RelativeLayout) findViewById(R.id.rl_exsit_measure_time);
        tv_measure_fee = (TextView) findViewById(R.id.tv_exsit_measure_measurement_fee);
        explistview = (PinnedHeaderExpandableListView) findViewById(R.id.explistview);
        btn_send = (Button) findViewById(R.id.btn_exist_measure_order_send);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        fee = (String) getIntent().getExtras().get(Constant.ConsumerMeasureFormKey.MEASURE); // 量房费
        designer_id = (String) getIntent().getExtras().get(Constant.ConsumerMeasureFormKey.DESIGNER_ID);
        hs_uid = (String) getIntent().getExtras().get(Constant.ConsumerMeasureFormKey.HS_UID);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setTitleForNavbar(UIUtils.getString(R.string.exist_measure_order_information));
        initAlertView();
        showState();
        setMeasureTime();
        CustomProgress.show(ExistMeasureOrderActivity.this, UIUtils.getString(R.string.getting_data), false, null);

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            user_id = memberEntity.getAcs_member_id();
        }
        getMyDecorationData(0, 100);
    }

    private void showState() {
        if (!fee.isEmpty()&& !fee.equals("0")) {
            double dFee = Double.valueOf(fee);
            DecimalFormat df1 = new DecimalFormat("#####.00");
            tv_measure_fee.setText(df1.format(dFee) + "元");
        } else {
            tv_measure_fee.setText("0.00");
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_send.setOnClickListener(this);
        rl_measure_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exist_measure_order_send:
                /**
                 * 获取系统当前时间
                 */
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());

                JSONObject jsonObject = new JSONObject();
                try {
                    if (expandFlag != -1) {
                        if (currentTime == null) {
                            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.amount_of_time_is_empty), null, new String[]{UIUtils.getString(R.string.sure)}, null, ExistMeasureOrderActivity.this,
                                    AlertView.Style.Alert, null).show();
                        } else {
                            if (formatDate(date, currentTime)) {
                                jsonObject.put("service_date", currentTime);
                                jsonObject.put("user_id", user_id);
                                jsonObject.put("hs_uid", hs_uid);
                                jsonObject.put("needs_id", dList.get(expandFlag).getNeeds_id());
                                jsonObject.put("designer_id", designer_id);
                                jsonObject.put("user_name", dList.get(expandFlag).getContacts_name());
                                jsonObject.put("mobile_number", dList.get(expandFlag).getContacts_mobile());
                                jsonObject.put("order_type", 0);
                                jsonObject.put("thread_id", "");
                                if (fee == null) {
                                    jsonObject.put("amount", 0.01);
                                } else {
                                    jsonObject.put("amount", fee);
                                }
                                jsonObject.put("adjustment", 600);
                                jsonObject.put("channel_type", "IOS");
                                CustomProgress.show(ExistMeasureOrderActivity.this, UIUtils.getString(R.string.sending_request), false, null);
                                agreeOneselfResponseBid(jsonObject);
                            } else {
                                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.amount_of_time_than_current_time_one_hour), null, new String[]{UIUtils.getString(R.string.sure)}, null, ExistMeasureOrderActivity.this,
                                        AlertView.Style.Alert, null).show();
                            }
                        }
                    } else {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_select_a_project_first), null, new String[]{UIUtils.getString(R.string.sure)}, null, ExistMeasureOrderActivity.this,
                                AlertView.Style.Alert, null).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    class GroupClickListener implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            if (expandFlag == -1) {
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                // explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                explistview.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                explistview.collapseGroup(expandFlag);
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                // explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }

    }

    /**
     * @param beforeDate 转化之前的时间
     * @param afterDate  转化后的时间
     * @return 是否可以解析
     * @throws ParseException
     */
    public static boolean formatDate(String beforeDate, String afterDate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = sf.parse(beforeDate);
        Date d2 = sf.parse(afterDate);
        long stamp = d2.getTime() - d1.getTime();
        return stamp >= (3600 * 1000);
    }

    /**
     * 消费者自选设计师量房
     *
     * @param jsonObject
     */
    public void agreeOneselfResponseBid(JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mAgreeResponseBidSuccessAlertView.show();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                KLog.d(TAG, userInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.dialog.cancel();
                mAgreeResponseBidFailAlertView.show();
            }
        };
        MPServerHttpManager.getInstance().agreeOneselfResponseBid(jsonObject, okResponseCallback);
    }

    /**
     * 获取遍历数据组成新的数据用来加载
     *
     * @param offset
     * @param limit
     */
    public void getMyDecorationData(int offset, final int limit) {
        MPServerHttpManager.getInstance().getMyDecorationData(offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.dialog.cancel();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                mDecorationListEntity = GsonUtil.jsonToBean(userInfo, DecorationListEntity.class);
                updateViewFromData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    /**
     * 更新视图
     */
    private void updateViewFromData() {

        ArrayList<DecorationListEntity.NeedsListEntity> mList = new ArrayList<>();
        mList.addAll(mDecorationListEntity.getNeeds_list());
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            String wk_template_id = mList.get(i).getWk_template_id();
            if (wk_template_id != null && Constant.NumKey.ONE.equals(wk_template_id)) {
                if (mList.get(i).getContract() == null) {
                    dList.add(mList.get(i));
                } else {
                    mList.get(i).setContract("null");
                    dList.add(mList.get(i));
                }
            }
        }

        // 设置悬浮头部VIEW
        // explistview.setHeaderView(getLayoutInflater().inflate(R.layout.group_head, explistview, false));
        if (dList != null) {
            adapter = new PinnedHeaderExpandableAdapter(dList, ExistMeasureOrderActivity.this, explistview);
            explistview.setAdapter(adapter);
        }
        // 设置单个分组展开
        explistview.setOnGroupClickListener(new GroupClickListener());
    }

    /**
     * 设置量房时间
     */
    private void setMeasureTime() {
        ///  设置时间的样式  .
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        /// 设置量房时间范围 .
        pvTime.setRange(2016, 2018);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        ///  The callback after the time to choose  .
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                currentTime = getTime(date);
                tv_measure_time.setText(currentTime);
            }
        });
    }

    private void initAlertView() {
        mAgreeResponseBidFailAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.choose_ta_room_fail), null, null, new String[]{UIUtils.getString(R.string.sure)}, ExistMeasureOrderActivity.this, AlertView.Style.Alert, ExistMeasureOrderActivity.this).setOnDismissListener(this);
        mAgreeResponseBidSuccessAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.choose_ta_room_success), null, null, new String[]{UIUtils.getString(R.string.sure)}, ExistMeasureOrderActivity.this, AlertView.Style.Alert, ExistMeasureOrderActivity.this).setOnDismissListener(this);
    }

    /**
     * 获取yyyy-MM-dd HH:mm:ss格式的时间
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    public void onDismiss(Object o) {
        finish();
    }

    @Override
    public void onItemClick(Object o, int position) {
    }

    /// 控件.
    private RelativeLayout rl_measure_time;
    private TextView tv_measure_time;
    private TextView tv_measure_fee;
    private Button btn_send;
    private TimePickerView pvTime;
    private AlertView mAgreeResponseBidSuccessAlertView;
    private AlertView mAgreeResponseBidFailAlertView;
    private PinnedHeaderExpandableListView explistview;
    private PinnedHeaderExpandableAdapter adapter;

    /// 变量　.
    private String currentTime;
    private String fee;
    private String user_id;
    private String designer_id;
    private String hs_uid;
    private int expandFlag = -1;// 控制列表的展开

    /// 集合，类.
    private DecorationListEntity mDecorationListEntity;
    private ArrayList<DecorationListEntity.NeedsListEntity> dList = new ArrayList<>();
}
