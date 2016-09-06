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
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.packages.activity.PackageDetailActivity;
import com.autodesk.shejijia.consumer.codecorationBase.packages.activity.ReservationFormActivity;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.ImageUrlUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 套餐
 */
public class PackagesFragment extends BaseFragment implements View.OnClickListener {


    private ImageView iv_packages_one;
    private ImageView iv_packages_two;
    private ImageView iv_packages_three;
    private ImageView iv_packages_four;
    private ImageView iv_packages_five;
    private ImageView iv_packages_six;
    private ImageView iv_packages_seven;
    private ImageView iv_packages_eight;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_packages;
    }

    @Override
    protected void initView() {

//        gv_packages = (GridView) rootView.findViewById(R.id.gv_packages);
        bt_packages_yuyue = (ImageButton) rootView.findViewById(R.id.bt_packages_yuyue);
        iv_packages_tital = (ImageView) rootView.findViewById(R.id.iv_packages_tital);
        iv_packages_one = (ImageView) rootView.findViewById(R.id.iv_packages_one);
        iv_packages_two = (ImageView) rootView.findViewById(R.id.iv_packages_two);
        iv_packages_three = (ImageView) rootView.findViewById(R.id.iv_packages_three);
        iv_packages_four = (ImageView) rootView.findViewById(R.id.iv_packages_four);
        iv_packages_five = (ImageView) rootView.findViewById(R.id.iv_packages_five);
        iv_packages_six = (ImageView) rootView.findViewById(R.id.iv_packages_six);
        iv_packages_seven = (ImageView) rootView.findViewById(R.id.iv_packages_seven);
        iv_packages_eight = (ImageView) rootView.findViewById(R.id.iv_packages_eight);

    }

    @Override
    protected void initData() {

        packages = ImageUrlUtils.getPackagesListImage();
        banner_image = ImageUrlUtils.getPackagesListBanner();

        isLoginUserJust = isLoginUser();
        ImageLoader.getInstance().displayImage(banner_image, iv_packages_tital);
        ImageLoader.getInstance().displayImage(packages[0], iv_packages_one);
        ImageLoader.getInstance().displayImage(packages[1], iv_packages_two);
        ImageLoader.getInstance().displayImage(packages[2], iv_packages_three);
        ImageLoader.getInstance().displayImage(packages[3], iv_packages_four);
        ImageLoader.getInstance().displayImage(packages[4], iv_packages_five);
        ImageLoader.getInstance().displayImage(packages[5], iv_packages_six);
        ImageLoader.getInstance().displayImage(packages[6], iv_packages_seven);
        ImageLoader.getInstance().displayImage(packages[7], iv_packages_eight);

    }


    @Override
    protected void initListener() {

        bt_packages_yuyue.setOnClickListener(this);
        iv_packages_one.setOnClickListener(this);
        iv_packages_two.setOnClickListener(this);
        iv_packages_three.setOnClickListener(this);
        iv_packages_four.setOnClickListener(this);
        iv_packages_five.setOnClickListener(this);
        iv_packages_six.setOnClickListener(this);
        iv_packages_seven.setOnClickListener(this);
        iv_packages_eight.setOnClickListener(this);

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
                    Toast.makeText(activity, "先登录", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.iv_packages_one:
                Intent intent1 = new Intent(activity, PackageDetailActivity.class);
                intent1.putExtra("item_num", 0);
                startActivity(intent1);
                break;
            case R.id.iv_packages_two:
                Intent intent2 = new Intent(activity, PackageDetailActivity.class);
                intent2.putExtra("item_num", 1);
                startActivity(intent2);
                break;
            case R.id.iv_packages_three:
                Intent intent3 = new Intent(activity, PackageDetailActivity.class);
                intent3.putExtra("item_num", 2);
                startActivity(intent3);
                break;
            case R.id.iv_packages_four:
                Intent intent4 = new Intent(activity, PackageDetailActivity.class);
                intent4.putExtra("item_num", 3);
                startActivity(intent4);
                break;
            case R.id.iv_packages_five:
                Intent intent5 = new Intent(activity, PackageDetailActivity.class);
                intent5.putExtra("item_num", 4);
                startActivity(intent5);
                break;
            case R.id.iv_packages_six:
                Intent intent6 = new Intent(activity, PackageDetailActivity.class);
                intent6.putExtra("item_num", 5);
                startActivity(intent6);
                break;
            case R.id.iv_packages_seven:
                Intent intent7 = new Intent(activity, PackageDetailActivity.class);
                intent7.putExtra("item_num", 6);
                startActivity(intent7);
                break;
            case R.id.iv_packages_eight:
                Intent intent8 = new Intent(activity, PackageDetailActivity.class);
                intent8.putExtra("item_num", 7);
                startActivity(intent8);
                break;
            default:
                break;
        }

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

    private String[] list_Image;
    private String banner_image;


    private String[] packages;
    private ImageButton bt_packages_yuyue;
    private boolean isLoginUserJust;
    private ImageView iv_packages_tital;

}
