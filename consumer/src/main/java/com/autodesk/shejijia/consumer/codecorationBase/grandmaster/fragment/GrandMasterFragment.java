package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.fragment;


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
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
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
                            nicke_name = designer_list.get(currentItem - 1).getNick_name();
                            member_id = designer_list.get(currentItem - 1).getMember_id();
                            hs_uid = designer_list.get(currentItem - 1).getHs_uid();
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
                        upOrderDataForService(jsonObject);
                    }
                });
                orderDialog.show();

                break;
        }
    }

    private void initPageData(String masterInfo) {

        isLoginUserJust = isLoginUser();

        GrandMasterInfo grandMasterInfo = GsonUtil.jsonToBean(masterInfo, GrandMasterInfo.class);
        designer_list = grandMasterInfo.getDesigner_list();
        viewList = new ArrayList<View>();

        LayoutInflater lf = LayoutInflater.from(activity);
        View view1 = lf.inflate(R.layout.viewpager_item_grandmaster_first, null);
        viewList.add(view1);
        for (int i = 0; i < designer_list.size(); i++) {
            View view2 = lf.inflate(R.layout.viewpager_item_grandmaster_content, null);
            ImageView iv_grandmaster_pic = (ImageView) view2.findViewById(R.id.iv_grandmaster_pic);
            TextView tv_grandmaster_cn_name = (TextView) view2.findViewById(R.id.tv_grandmaster_cn_name);
            TextView tv_grandmaster_en_name = (TextView) view2.findViewById(R.id.tv_grandmaster_en_name);
            TextView tv_grandmaster_detail = (TextView) view2.findViewById(R.id.tv_grandmaster_detail);

            if (TextUtils.isEmpty(designer_list.get(i).getEnglish_name())) {
                tv_grandmaster_en_name.setText("后台无数据");
            } else {
                tv_grandmaster_en_name.setText(designer_list.get(i).getEnglish_name());
            }
            if (TextUtils.isEmpty(designer_list.get(i).getNick_name())) {
                tv_grandmaster_cn_name.setText("后台无数据");
            } else {
                tv_grandmaster_cn_name.setText(designer_list.get(i).getNick_name());
            }
            if (TextUtils.isEmpty(designer_list.get(i).getDesigner().getIntroduction())) {
                tv_grandmaster_detail.setText("后台无数据");
            } else {
                tv_grandmaster_detail.setText(designer_list.get(i).getDesigner().getIntroduction());
            }

            if (null != designer_list.get(i).getDesigner() && null != designer_list.get(i).getDesigner().getDesigner_profile_cover_app() && null != designer_list.get(i).getDesigner().getDesigner_profile_cover_app().getPublic_url()) {

                String img_url = designer_list.get(i).getDesigner().getDesigner_profile_cover_app().getPublic_url();
                ImageUtils.displayIconImage(img_url, iv_grandmaster_pic);
            }
            viewList.add(view2);
        }
        addImageViewtips();
        vp_grand_selection.setAdapter(pagerAdapter);
        vp_grand_selection.setOnPageChangeListener(this);

    }

    // 将圆点加入到ViewGroup中
    private void addImageViewtips() {
        tips = new ImageView[viewList.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
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

    protected PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ViewGroup view_parent = (ViewGroup) viewList.get(position).getParent();
            if (view_parent != null) {
                view_parent.removeView(viewList.get(position));
            }
            container.addView(viewList.get(position));
            if (position != 0) {//大师列表点击监听
                ImageView iv_grandmaster_pic = (ImageView) viewList.get(position).findViewById(R.id.iv_grandmaster_pic);
                iv_grandmaster_pic.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        Intent intent = new Intent(activity, GrandMasterDetailActivity.class);
                        intent.putExtra("hs_uid", designer_list.get(position - 1).getHs_uid());
                        startActivity(intent);
                    }
                });
            }
            return viewList.get(position);
        }

    };

    /**
     * 上传立即预约信息
     */
    public void upOrderDataForService(JSONObject jsonObject) {

        MPServerHttpManager.getInstance().upWorkRoomOrderData(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getActivity(), R.string.work_room_commit_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                Toast.makeText(getActivity(), R.string.work_room_commit_successful, Toast.LENGTH_SHORT).show();
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
        setImageBackground(position % viewList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private ViewPager vp_grand_selection;
    private ViewGroup ll_grand_selection;
    private ImageButton bt_grand_reservation;
    private boolean isLoginUserJust = false;
    private ImageView[] tips;
    private ArrayList<View> viewList;
    private List<MasterInfo> designer_list;
}
