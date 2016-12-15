package com.autodesk.shejijia.enterprise.personalcenter.fragment;

import android.view.View;
import android.widget.TextView;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

/**
 * Created by t_xuz on 9/2/16.
 * 我页--关于app页面
 */
@SuppressWarnings("ALL")
public class AboutFragment extends BaseConstructionFragment implements View.OnClickListener{
    private TextView mTvDesignIntroduction;
    private TextView mTvVersionDescription;

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_center_about;
    }

    @Override
    protected void initView() {
        mTvDesignIntroduction = (TextView)rootView.findViewById(R.id.tv_design_introduction);
        mTvVersionDescription = (TextView)rootView.findViewById(R.id.tv_version_description);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        mTvDesignIntroduction.setOnClickListener(this);
        mTvVersionDescription.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_design_introduction:
                ToastUtils.showLong(getActivity(),"dasdas");
                break;
            case R.id.tv_version_description:
                ToastUtils.showLong(getActivity(),"123");
                break;
            default:
                break;
        }
    }
}
