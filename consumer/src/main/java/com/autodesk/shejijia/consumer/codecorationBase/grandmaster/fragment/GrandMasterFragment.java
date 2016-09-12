package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.fragment;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.activity.GrandMasterDetailActivity;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.GrandMasterInfo;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.MasterInfo;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.view.OrderDialogMaster;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 大师
 */
public class GrandMasterFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_grand_master;
    }

    @Override
    protected void initView() {

        vp_grand_selection = (ViewPager) rootView.findViewById(R.id.vp_grand_selection);
        bt_grand_reservation = (ImageButton) rootView.findViewById(R.id.bt_grand_reservation);
        ll_grand_selection = (ViewGroup) rootView.findViewById(R.id.ll_grand_selection);

    }

    @Override
    protected void initListener() {
        bt_grand_reservation.setOnClickListener(this);
    }

    @Override
    protected void initData() {


        MPServerHttpManager.getInstance().getGrandMasterInfo(0, 10, "61", new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                ApiStatusUtil.getInstance().apiStatuError(volleyError, activity);
                initPageData("error");
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                String masterInfo = GsonUtil.jsonToString(jsonObject);

                initPageData(masterInfo);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_grand_reservation://大师列表预约监听

                OrderDialogMaster orderDialog = new OrderDialogMaster(getActivity(), R.style.add_dialog, R.drawable.tital_yuyue);
                orderDialog.setListenser(new OrderDialogMaster.CommitListenser() {
                    @Override
                    public void commitListener(String name, String phoneNumber) {

                        JSONObject jsonObject = new JSONObject();

                        int currentItem = vp_grand_selection.getCurrentItem();
                        String nicke_name = "", member_id = "", hs_uid = "", login_member_id = "", login_hs_uid = "";
                        if (currentItem != 0) {
                            nicke_name = mMasterInfoList.get(currentItem - 1).getNick_name();
                            member_id = mMasterInfoList.get(currentItem - 1).getMember_id();
                            hs_uid = mMasterInfoList.get(currentItem - 1).getHs_uid();
                        }
                        if (isLoginUserJust) {
                            login_member_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
                            login_hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
                        } else {
                            login_member_id = "";
                            login_hs_uid = "";
                        }

                        try {
                            jsonObject.put("consumer_name", name);//姓名
                            jsonObject.put("consumer_mobile", phoneNumber);//电话
                            jsonObject.put("type", 1);//大师类型
                            jsonObject.put("customer_id", login_member_id);//消费者ID
                            jsonObject.put("consumer_uid", login_hs_uid);
                            jsonObject.put("name", nicke_name);
                            jsonObject.put("member_id", member_id);
                            jsonObject.put("hs_uid", hs_uid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CustomProgress.show(activity, "提交中...", false, null);
                        upOrderDataForService(jsonObject);
                    }
                });
                orderDialog.show();

                break;
        }
    }

    private void initPageData(String masterInfo) {
        isLoginUserJust = isLoginUser();
        if (!"error".equals(masterInfo)) {
            GrandMasterInfo grandMasterInfo = GsonUtil.jsonToBean(masterInfo, GrandMasterInfo.class);
            mMasterInfoList = grandMasterInfo.getDesigner_list();
        }
        mPagerAdapter = new MastersPagerAdapter(getActivity(), mMasterInfoList);
        vp_grand_selection.setAdapter(mPagerAdapter);
        vp_grand_selection.setOnPageChangeListener(this);

        addImageViewtips();
    }

    // 将圆点加入到ViewGroup中
    private void addImageViewtips() {
        tips = new ImageView[mPagerAdapter.getCount()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(activity);
            LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LayoutParams.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(LayoutParams);
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            ll_grand_selection.addView(imageView);
        }
    }

    //设置pager页联动圆点样式
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }

    }

    private static class MastersPagerAdapter extends PagerAdapter {
        private Activity mContext;
        private List<MasterInfo> mMasterInfoList;

        public MastersPagerAdapter(Activity context, List<MasterInfo> masterDesigners) {
            mMasterInfoList = masterDesigners;
            mContext = context;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return mMasterInfoList.size() + 1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view;
            LayoutInflater layoutInflater =LayoutInflater.from(mContext);
            switch(position) {
                case 0:
                    view = layoutInflater.inflate(R.layout.viewpager_item_grandmaster_first, null);
                    ImageView iv_grandmaster_pic_first = (ImageView) view.findViewById(R.id.iv_grandmaster_pic_first);
                    ImageUtils.displayIconImage("drawable://" + R.drawable.shouye, iv_grandmaster_pic_first);
                    break;
                default:
                    view = layoutInflater.inflate(R.layout.viewpager_item_grandmaster_content, null);
                    ImageView iv_grandmaster_pic = (ImageView) view.findViewById(R.id.iv_grandmaster_pic);
                    final MasterInfo masterInfo = mMasterInfoList.get(position - 1);
                    if (null != masterInfo.getDesigner() && null != masterInfo.getDesigner().getDesigner_profile_cover_app()
                            && null != masterInfo.getDesigner().getDesigner_profile_cover_app().getPublic_url()) {
                        String img_url = masterInfo.getDesigner().getDesigner_profile_cover_app().getPublic_url();
                        ImageUtils.displayIconImage(img_url, iv_grandmaster_pic);
                    }
                    //大师列表点击监听
                    iv_grandmaster_pic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, GrandMasterDetailActivity.class);
                            intent.putExtra("hs_uid", masterInfo.getHs_uid());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
            }

            container.addView(view);
            return view;
        }
    };

    /**
     * 上传立即预约信息
     */
    public void upOrderDataForService(JSONObject jsonObject) {

        MPServerHttpManager.getInstance().upWorkRoomOrderData(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, activity);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                ToastUtil.showCustomToast(activity, UIUtils.getString(R.string.work_room_commit_successful));
            }
        });

    }

    //判断该用户是否登陆了
    public boolean isLoginUser() {

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

        if (memberEntity == null) {

            return false;//未登录
        } else {

            return true;//已登录
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % mPagerAdapter.getCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private ViewPager vp_grand_selection;
    private ViewGroup ll_grand_selection;
    private ImageButton bt_grand_reservation;
    private boolean isLoginUserJust = false;
    private ImageView[] tips;
    private List<MasterInfo> mMasterInfoList = new ArrayList<>();
    private MastersPagerAdapter mPagerAdapter;
}
