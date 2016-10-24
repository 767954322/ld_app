package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignFileBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPFileBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanListBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.gallerywidget.BasePagerAdapter;
import com.autodesk.shejijia.shared.components.common.uielements.gallerywidget.GalleryViewPager;
import com.autodesk.shejijia.shared.components.common.uielements.gallerywidget.UrlPagerAdapter;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file Wk3DPlanShowActivity.java  .
 * @brief 展示上传的单个交付的页面，含有分享功能等.
 */
public class Wk3DPlanShowActivity extends NavigationBarActivity implements BasePagerAdapter.OnItemClickListener {

    ArrayList<MPFileBean> mMPFileBeans = new ArrayList<>();
    ArrayList<MPDesignFileBean> mMPDesignFileBeans = new ArrayList<>();
    private GalleryViewPager mVpShowPager;
    List<String> mLinkList = new ArrayList<>();
    private UrlPagerAdapter mUrlPagerAdapter;
    private TextView tv_nav_left_textView;
    private Wk3DPlanListBean wk3DPlanListBean;
    private String url;
    private int mPosition;
    private boolean isLevel;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wk3dplan_show;
    }

    @Override
    protected void initView() {
        super.initView();
//        iv_image_show = (MPFileHotspotView) findViewById(iv_image_show);
        tv_nav_left_textView = (TextView) findViewById(R.id.nav_left_textView);
        mVpShowPager = (GalleryViewPager) findViewById(R.id.vp_show_pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        showState();
        setCurrentTitle(getTitleData());
        mUrlPagerAdapter = new UrlPagerAdapter(this, mLinkList);
        mVpShowPager.setAdapter(new UrlPagerAdapter(this, mLinkList));
        mVpShowPager.setCurrentItem(getCurrentPosition());

        mVpShowPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position + 1;
                String title = "";
                String positionAll = mMPDesignFileBeans == null ? "" : mMPDesignFileBeans.size() + "";
                String positionAll_1 = mMPFileBeans == null ? "" : mMPFileBeans.size() + "";

                boolean isPositionAll = StringUtils.isEmpty(positionAll);
                if (isPositionAll) {
                } else {
                    title = String.format("%s/%s", mPosition, positionAll);
                }
                setCurrentTitle(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        isLevel = bundleExtra.getBoolean(Constant.DeliveryShowBundleKey._LEVEL_TAG);
        String title = null;
        if (isLevel) {
            String string = bundleExtra.getString(Constant.DeliveryShowBundleKey._JAVA_BEAN);
            if (Constant.DeliveryShowBundleKey.DESIGN_DELIVERY_LEVEL_ZERO.equals(string)) {
                wk3DPlanListBean = (Wk3DPlanListBean) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN);
                url = wk3DPlanListBean.getLink();
                title = wk3DPlanListBean.getDesign_name();
                mLinkList.add(url);

            } else if (Constant.DeliveryShowBundleKey.DESIGN_DELIVERY_OTHERS.equals(string)) {
                mMPDesignFileBeans = (ArrayList<MPDesignFileBean>) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN);
                mPosition = (int) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._POSITION);

                for (MPDesignFileBean mpDesignFileBean : mMPDesignFileBeans) {
                    String link = mpDesignFileBean.getLink();
                    mLinkList.add(link);
                }

                if (null != mMPDesignFileBeans && mMPDesignFileBeans.size() > 0 && mPosition <= mMPDesignFileBeans.size()) {
                    title = String.format("%s/%s", mPosition + 1, mMPDesignFileBeans.size());
                }
            }
        } else {
            mMPFileBeans = (ArrayList<MPFileBean>) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN);
            mPosition = (int) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._POSITION);

            for (MPFileBean mpFileBean : mMPFileBeans) {
                String url = mpFileBean.getUrl();
                mLinkList.add(url);
            }

            if (null != mMPFileBeans && mMPFileBeans.size() > 0 && mPosition <= mMPFileBeans.size()) {
                title = String.format("%s/%s", mPosition + 1, mMPFileBeans.size());
            }
        }
        return title;
    }

    private void setCurrentTitle(String title) {
        setTitleForNavbar(title);
        tv_nav_left_textView.setText(getResources().getString(R.string.select_finish));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mUrlPagerAdapter.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(int currentPosition) {

    }

    public int getCurrentPosition() {
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra(Constant.DeliveryShowBundleKey._BUNDLE_INTENT);
        isLevel = bundleExtra.getBoolean(Constant.DeliveryShowBundleKey._LEVEL_TAG);
        int currentPosition = 0;
        if (isLevel) {
            String string = bundleExtra.getString(Constant.DeliveryShowBundleKey._JAVA_BEAN);
            if (Constant.DeliveryShowBundleKey.DESIGN_DELIVERY_OTHERS.equals(string)) {
                currentPosition = (int) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._POSITION);
            }
        } else {
            currentPosition = (int) bundleExtra.getSerializable(Constant.DeliveryShowBundleKey._POSITION);
        }
        return currentPosition;
    }
}
