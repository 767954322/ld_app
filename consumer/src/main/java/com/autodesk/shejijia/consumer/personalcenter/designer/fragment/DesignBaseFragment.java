package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.widget.SegmentTabLayout;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjl on 16-9-6.
 */
public class DesignBaseFragment extends BaseFragment {

    private List<String> mTabNames = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private SegmentTabLayout tab_design_layout;
    private RelativeLayout rlt_tab_view;
    private int status;
    private int loho;

    public static DesignBaseFragment newInstance(int status, int loho) {
        DesignBaseFragment fragment = new DesignBaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        bundle.putInt("loho", loho);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_design_base;
    }

    @Override
    protected void initView() {
        tab_design_layout = (SegmentTabLayout) rootView.findViewById(R.id.tab_design_layout);
        rlt_tab_view = (RelativeLayout) rootView.findViewById(R.id.rlt_tab_view);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        status = arguments.getInt("status");
        loho = arguments.getInt("loho");
        updateFragmentContent(status, loho);
    }

    private void updateFragmentContent(int status, int loho) {
        mTabNames.clear();
        fragments.clear();
        if (status == 2 && loho == 1) {
            this.addDesignGood2Content();
            this.addDesignCompetition2Content();
            this.addDesignCombo2Content();
        }

        if (status == 2 && loho != 1) {
            this.addDesignGood2Content();
            this.addDesignCompetition2Content();
        }

        if (status != 2 && loho == 1) {
            this.addDesignCompetition2Content();
            this.addDesignCombo2Content();
        }

        if (status != 2 && loho != 1) {
            this.addDesignCompetition2Content();
        }

        String[] mTitles = mTabNames.toArray(new String[mTabNames.size()]);
        if (mTitles.length == 1) {
            rlt_tab_view.setVisibility(View.GONE);
        }
        if (mTitles != null && mTitles.length > 0)
            tab_design_layout.setTabData(mTitles, this.getActivity(), R.id.design_container, fragments);
    }

    public void addDesignGood2Content() {
        mTabNames.add("精选项目");
        fragments.add(new DesignGoodFragment());
    }


    public void addDesignCompetition2Content() {
        mTabNames.add("竞优项目");
        fragments.add(new OrderCommonFragment());
    }

    public void addDesignCombo2Content() {
        mTabNames.add("套餐项目");
        fragments.add(new OrderBeiShuFragment());
    }


    @Override
    public void onFragmentShown() {
        LogUtils.i(TAG, "onFragmentShown: onFragmentShown");
        for (int i = 0; i < fragments.size(); ++i) {
            BaseFragment f1 = (BaseFragment) fragments.get(i);
            f1.onFragmentShown();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabNames.clear();
        fragments.clear();
    }
}
