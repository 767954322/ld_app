package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.RollFragAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationListEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.fragment.DecorationBeiShuFragment;
import com.autodesk.shejijia.consumer.personalcenter.consumer.fragment.DecorationFragment;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.ZoomOutPageTransformer;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author he.liu
 * @version v1.0 .
 * @date 2016-6-6 .
 * @file DecorationActivity.java .
 * @brief 消费者, 我的家装订单 .
 */
public class DecorationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_decoration);
        initView();
        initExtraBundle();
        initListener();
        initData();
    }

    private void initView() {
        mIbnAdd = (ImageButton) findViewById(R.id.nav_right_imageButton);
        mIbnBack = (ImageButton) findViewById(R.id.nav_left_imageButton);
        mLlPagers = (LinearLayout) findViewById(R.id.dots_ll);
        mRlEmpty = (RelativeLayout) findViewById(R.id.rl_empty);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTitle = (TextView) findViewById(R.id.nav_title_textView);
        mTvLoadingMore = (TextView) findViewById(R.id.tv_loading_more);
        mTvEmptyShow = (TextView) findViewById(R.id.tv_empty_message);
        mIvEmptyShow = (ImageView) findViewById(R.id.iv_default_empty);

        mIbnAdd.setVisibility(View.VISIBLE);
        mTvLoadingMore.setVisibility(View.GONE);
    }

    private void initExtraBundle() {
        mIntent = getIntent();
        mNickNameConsumer = mIntent.getStringExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME);
    }

    private void initData() {
        mContext = this;
        mTitle.setText(UIUtils.getString(R.string.consumer_decoration));
        mTvLoadingMore.setText(UIUtils.getString(R.string.loding_more));
        mTvEmptyShow.setText(UIUtils.getString(R.string.empty_order_fitment));
        mIbnAdd.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_title_add));
        mIvEmptyShow.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_order_empty));
        initPagerNum();

        CustomProgress.show(DecorationActivity.this, "", false, null);
        getMyDecorationData(0, limit);
    }

    private void initListener() {
        mIbnAdd.setOnClickListener(this);
        mIbnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_imageButton:
                finish();
                break;
            case R.id.nav_right_imageButton:
                mIntent = new Intent(this, IssueDemandActivity.class);
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
            setOnPageChangeListener();
            startRoll();
        }
    };

    /**
     * 获取消费者家装订单
     */
    public void getMyDecorationData(final int offset, final int limit) {
        MPServerHttpManager.getInstance().getMyDecorationData(offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                DecorationListEntity decorationListEntity = GsonUtil.jsonToBean(userInfo, DecorationListEntity.class);
                KLog.json(TAG, userInfo);

                updateViewFromDecorationData(decorationListEntity, offset);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DecorationActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        });
    }

    /**
     * 获取网络数据填充布局
     *
     * @param decorationListEntity 　数据实体类
     * @param offset               　显示的页数
     */
    private void updateViewFromDecorationData(DecorationListEntity decorationListEntity, int offset) {
        mTvLoadingMore.setText(UIUtils.getString(R.string.load_succeed));
        mCount = decorationListEntity.getCount();
        if (mCount == 0) {
            mRlEmpty.setVisibility(View.VISIBLE);
            return;
        }
        mNeedsListEntityArrayList = (ArrayList<DecorationListEntity.NeedsListEntity>) decorationListEntity.getNeeds_list();
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

    /**
     * 底部页码展示
     */
    private void initPagerNum() {
        mLlPagers.removeAllViews();
        mTvPager = new TextView(mContext);
        mLlPagers.addView(mTvPager);
    }

    /**
     * 设置viewpager适配器及动画效果
     */
    public void startRoll() {
        if (mRollFragAdapter == null) {
            mRollFragAdapter = new RollFragAdapter(getSupportFragmentManager(), mFragmentArrayList);
            mViewPager.setAdapter(mRollFragAdapter);
        } else {
            if (isRefush) {

                mRollFragAdapter = new RollFragAdapter(getSupportFragmentManager(), mFragmentArrayList);
                mViewPager.setAdapter(mRollFragAdapter);
            }
            mRollFragAdapter.notifyDataSetChanged();
        }
        /// viewpager animation.
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        if (mMCurrentPosition == 0) {
            mTvPager.setText(1 + "/" + mCount);
        }
        mViewPager.setCurrentItem(mMCurrentPosition, false); /// 默认设置的是第0个开始的 .
        mRollFragAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(0);
    }

    /**
     * 设置滑动ViewPager的监听
     */
    private void setOnPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (mNeedsListEntities == null || mNeedsListEntities.size() < 1) {
                    return;
                }
                mTvPager.setText((position + 1) + "/" + mCount);

                if (position != mNeedsListEntities.size() - 1) {
                    mTvLoadingMore.setVisibility(View.GONE);
                } else {
                    if (position + 1 == mCount) {
                        mTvLoadingMore.setVisibility(View.GONE);
                    } else {
                        mTvLoadingMore.setVisibility(View.VISIBLE);
                    }
                    mTvLoadingMore.setText(UIUtils.getString(R.string.loding_more));
                    mTvLoadingMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomProgress.show(DecorationActivity.this, "", false, null);
                            mMCurrentPosition = position;
                            mTvLoadingMore.setText(UIUtils.getString(R.string.loding));
                            getMyDecorationData(mOffset, limit);
                        }
                    });
                }
            }
        });
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

    private ImageButton mIbnAdd;
    private ImageButton mIbnBack;
    private RelativeLayout mRlEmpty;
    private ImageView mIvEmptyShow;
    private TextView mTvEmptyShow;
    private TextView mTitle;
    private TextView mTvPager;
    private LinearLayout mLlPagers;
    private TextView mTvLoadingMore;
    private ViewPager mViewPager;

    private RollFragAdapter mRollFragAdapter;
    private Intent mIntent;
    private String mNickNameConsumer;
    private Context mContext;
    private int limit = 10;
    private int mOffset = 0;
    private int mCount;
    private int mMCurrentPosition;
    private boolean isRefush = false;
    final int RESULT_CODE = 101;

    private ArrayList<DecorationListEntity.NeedsListEntity> mNeedsListEntityArrayList;
    private ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();
    private ArrayList<DecorationListEntity.NeedsListEntity> mNeedsListEntities = new ArrayList<>();
}