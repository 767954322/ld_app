package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.RollFragAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationListBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.fragment.DecorationBeiShuFragment;
import com.autodesk.shejijia.consumer.personalcenter.consumer.fragment.DecorationFragment;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.ZoomOutPageTransformer;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-12
 * @file DesignerOrderBeiShuActivity.java  .
 * @brief 我的装修项目--消费者 .
 */
public class DecorationConsumerFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_consumer_decoration_new;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        mContext = getActivity();

        getMyDecorationData(0, limit);

    }


    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nav_right_imageButton:
                mIntent = new Intent(getActivity(), IssueDemandActivity.class);
                mNickNameConsumer = TextUtils.isEmpty(mNickNameConsumer) ? UIUtils.getString(R.string.anonymity) : mNickNameConsumer;
                mIntent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, mNickNameConsumer);
                startActivityForResult(mIntent, RESULT_CODE);
                break;
        }

    }

    /**
     * 获取数据后执行的操作
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mNeedsListEntities == null && mNeedsListEntities.size() < 1) {
                return;
            }

            mFragmentArrayList.clear();
            String is_beishu;
            for (int i = 0; i < mNeedsListEntities.size(); i++) {
                is_beishu = mNeedsListEntities.get(i).getIs_beishu();
                if (IS_NOT_BEI_SHU.equals(is_beishu)) {
                    /**
                     * 数据添加到普通家装订单
                     */
                    mFragmentArrayList.add(DecorationFragment.getInstance(mNeedsListEntities.get(i)));
                } else {
                    /**
                     * 数据添加到北舒家装订单
                     */
                    mFragmentArrayList.add(DecorationBeiShuFragment.getInstance(mNeedsListEntities.get(i)));
                }
            }
        }
    };

    /**
     * 获取消费者家装订单
     */
    public void getMyDecorationData(final int offset, final int limit) {
        CustomProgress.show(getActivity(), "", false, null);

        MPServerHttpManager.getInstance().getMyDecorationData(offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                DecorationListBean decorationListBean = GsonUtil.jsonToBean(userInfo, DecorationListBean.class);
                KLog.json(TAG, userInfo);

                updateViewFromDecorationData(decorationListBean, offset);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
                        AlertView.Style.Alert, null).show();
            }
        });
    }

    /**
     * 获取网络数据填充布局
     *
     * @param decorationListBean 　数据实体类
     * @param offset             　显示的页数
     */
    private void updateViewFromDecorationData(DecorationListBean decorationListBean, int offset) {

        mNeedsListEntityArrayList = decorationListBean.getNeeds_list();
        if (mNeedsListEntityArrayList == null || mNeedsListEntityArrayList.size() < 1) {
            return;
        }
        if (offset == 0) {
            mNeedsListEntities.clear();
        }
        mOffset = offset + 10;
        mNeedsListEntities.addAll(mNeedsListEntityArrayList);
        Message msg = Message.obtain();
        msg.obj = offset;
        handler.sendMessage(msg);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CODE) {

            String success = data.getStringExtra("SUCCESS");
            if (success.equals("SUCCESS")) {

                isRefush = true;
                getMyDecorationData(0, limit);
            }
        }
    }


    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String IS_NOT_BEI_SHU = "1";
    private String TAG = getClass().getSimpleName();


    private Intent mIntent;
    private String mNickNameConsumer;
    private Context mContext;
    private int limit = 10;
    private int mOffset = 0;
    private int mCount;
    private int mMCurrentPosition;
    private boolean isRefush = false;
    final int RESULT_CODE = 101;


    private List<DecorationNeedsListBean> mNeedsListEntityArrayList;
    private ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();
    private ArrayList<DecorationNeedsListBean> mNeedsListEntities = new ArrayList<>();

}
