package com.autodesk.shejijia.consumer.codecorationBase.packages.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.packages.activity.PackageDetailActivity;
import com.autodesk.shejijia.consumer.codecorationBase.packages.activity.ReservationFormActivity;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.ImageUrlUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 套餐
 */
public class PackagesFragment extends BaseFragment implements View.OnClickListener, GridView.OnItemClickListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_packages;
    }

    @Override
    protected void initView() {

        gv_packages = (GridView) rootView.findViewById(R.id.gv_packages);
        bt_packages_yuyue = (ImageButton) rootView.findViewById(R.id.bt_packages_yuyue);
        iv_packages_tital = (ImageView) rootView.findViewById(R.id.iv_packages_tital);

    }

    @Override
    protected void initData() {
        isLoginUserJust = isLoginUser();
        packages = ImageUrlUtils.getPackagesListImage();
        banner_image = ImageUrlUtils.getPackagesListBanner();

        iv_packages_tital.setFocusable(true);
        iv_packages_tital.setFocusableInTouchMode(true);
        iv_packages_tital.requestFocus();

        ImageUtils.loadImageIcon(iv_packages_tital,banner_image);
        PacksAdapter pagerAdapter = new PacksAdapter();
        gv_packages.setAdapter(pagerAdapter);

    }


    @Override
    protected void initListener() {

        bt_packages_yuyue.setOnClickListener(this);
        gv_packages.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_packages_yuyue:
                if (isLoginUserJust) {
                    Intent intent_yuyue = new Intent(activity, ReservationFormActivity.class);
                    intent_yuyue.putExtra("item_num", 0);
                    intent_yuyue.putExtra("item_name", "");
                    activity.startActivity(intent_yuyue);
                } else {
                    AdskApplication.getInstance().doLogin(activity);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        isLoginUserJust = isLoginUser();

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(activity, PackageDetailActivity.class);
        intent.putExtra("item_num", position);
        startActivity(intent);

    }


    class PacksAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return packages.length;
        }

        @Override
        public Object getItem(int position) {
            return packages[position];
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
                convertView = LayoutInflater.from(activity).inflate(R.layout.fragment_packages_item, null);
                myHolder.iv_packets = (ImageView) convertView.findViewById(R.id.iv_packages_item);
                convertView.setTag(myHolder);
            }
            myHolder = (MyHolder) convertView.getTag();
            ImageUtils.loadImageIcon(myHolder.iv_packets,packages[position]);
            return convertView;
        }

        class MyHolder {
            private ImageView iv_packets;
        }
    }

    private String[] packages;
    private String banner_image;
    private boolean isLoginUserJust;

    private GridView gv_packages;
    private ImageView iv_packages_tital;
    private ImageButton bt_packages_yuyue;

}
