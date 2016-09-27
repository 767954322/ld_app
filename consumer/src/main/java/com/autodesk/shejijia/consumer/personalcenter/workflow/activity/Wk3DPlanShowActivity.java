package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignFileBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPFileBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanListBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;


/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file Wk3DPlanShowActivity.java  .
 * @brief 展示上传的单个交付的页面，含有分享功能等.
 */
public class Wk3DPlanShowActivity extends NavigationBarActivity {
    ArrayList<MPFileBean> mMPFileBeens;
    ArrayList<MPDesignFileBean> mMPDesignFileBeens;
    private ViewPager mVpShowPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wk3dplan_show;
    }

    @Override
    protected void initView() {
        super.initView();
//        iv_image_show = (MPFileHotspotView) findViewById(iv_image_show);
        tv_nav_left_textView = (TextView) findViewById(R.id.nav_left_textView);
        mVpShowPager = (ViewPager) findViewById(R.id.vp_show_pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        showState();
        initView(getTitleData());


    }

//    public static void actionStart(Context context, MPDesignFileBean mpDesignFileBean, MPFileBean deliveryFilesEntity, String javaBean, boolean b) {
//        Intent intent = new Intent(context, Wk3DPlanShowActivity.class);
//        Bundle bundle = new Bundle();
//
//        bundle.putSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN, mpDesignFileBean);
//        bundle.putString(Constant.DeliveryShowBundleKey._JAVA_BEAN, Constant.DeliveryShowBundleKey.DESIGN_DELIVERY_OTHERS);
//        bundle.putBoolean(Constant.DeliveryShowBundleKey._LEVEL_TAG, true);
//
//        intent.putExtra(Constant.DeliveryShowBundleKey._BUNDLE_INTENT, bundle);
//        context.startActivity(intent);
//    }

    /**
     * 标题栏状态
     */
    private void showState() {
        setVisibilityForNavButton(ButtonType.LEFT, false);
        setVisibilityForNavButton(ButtonType.RIGHT, true);
        setImageForNavButton(ButtonType.RIGHT, R.drawable.ic_menu_share);
        tv_nav_left_textView.setVisibility(View.VISIBLE);
    }

    /**
     * 获取标题
     *
     * @return
     */
    private String getTitleData() {
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra(Constant.DeliveryShowBundleKey._BUNDLE_INTENT);
        boolean aBoolean = bundleExtra.getBoolean(Constant.DeliveryShowBundleKey._LEVEL_TAG);
        String title = null;
        if (aBoolean) {
            String string = bundleExtra.getString(Constant.DeliveryShowBundleKey._JAVA_BEAN);
            if (Constant.DeliveryShowBundleKey.DESIGN_DELIVERY_LEVEL_ZERO.equals(string)) {
                wk3DPlanListBean = (Wk3DPlanListBean) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN);
                url = wk3DPlanListBean.getLink();
                title = wk3DPlanListBean.getDesign_name();

            } else if (Constant.DeliveryShowBundleKey.DESIGN_DELIVERY_OTHERS.equals(string)) {
                mMPDesignFileBeens = (ArrayList<MPDesignFileBean>) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN);
                int position = (int) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._POSITION);
                if (null != mMPFileBeens && mMPFileBeens.size() > 0 && position <= mMPFileBeens.size()) {
                    MPDesignFileBean mpFileBean = mMPDesignFileBeens.get(position);
                    url = mpFileBean.getLink();
                    title = mpFileBean.getName();
                }
            }
        } else {
            mMPFileBeens = (ArrayList<MPFileBean>) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN);
            int position = (int) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._POSITION);
            if (null != mMPFileBeens && mMPFileBeens.size() > 0 && position <= mMPFileBeens.size()) {
                MPFileBean mpFileBean = mMPFileBeens.get(position);
                url = mpFileBean.getUrl();
                title = mpFileBean.getFiled_name();
            }
        }

        mVpShowPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }

        });
        String str = url.substring(url.lastIndexOf('.') + 1);
//        if (Constant.DocumentTypeKey.TYPE_PNG.equals(str) || Constant.DocumentTypeKey.TYPE_JPG.equals(str)) {
//            iv_image_show.setVisibility(View.VISIBLE);
//
//            ImageUtils.displayIconImage(url, iv_image_show);
//            iv_image_show.setEnableTouch(true);
//        } else {
//            setReflectIcon(iv_image_show, str);
//        }
        return title;
    }

    /**
     * 初始化控件
     *
     * @param title
     */

    private void initView(String title) {
        setTitleForNavbar(title);
        tv_nav_left_textView.setText(getResources().getString(R.string.select_finish));
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, UIUtils.getString(R.string.share) + ":" + url);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, UIUtils.getString(R.string.share)));
    }


    private void setReflectIcon(ImageView imageView, String str) {
        if (Constant.DocumentTypeKey.TYPE_DOCX.equals(str) || Constant.DocumentTypeKey.TYPE_DOC.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_world));
        } else if (Constant.DocumentTypeKey.TYPE_XLS.equals(str) || Constant.DocumentTypeKey.TYPE_XLSX.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_excel));
        } else if (Constant.DocumentTypeKey.TYPE_PDF.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_pdf));
        } else {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.common_case_icon));
        }
    }

    private TextView tv_nav_left_textView;
//    private MPFileHotspotView iv_image_show;

//    private MPFileBean deliveryFilesEntity;

    private Wk3DPlanListBean wk3DPlanListBean;
//    private MPDesignFileBean designFileEntity;

    private String url;
}
