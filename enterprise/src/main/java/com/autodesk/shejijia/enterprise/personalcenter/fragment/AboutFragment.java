package com.autodesk.shejijia.enterprise.personalcenter.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.BuildConfig;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.personalcenter.activity.ConstructionIntroductionActivity;
import com.autodesk.shejijia.enterprise.personalcenter.activity.VersionDescriptionActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

/**
 * Created by t_xuz on 9/2/16.
 * 我页--关于app页面
 */
public class AboutFragment extends BaseConstructionFragment implements View.OnClickListener{
    private RelativeLayout mRlDesignIntroduction;
    private RelativeLayout mRlVersionDescription;
    private TextView mTvVersion;
    private TextView mTvGitCommitId;
    public static AboutFragment newInstance(){
        return new AboutFragment();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_center_about;
    }

    @Override
    protected void initView() {
        mRlDesignIntroduction = (RelativeLayout)rootView.findViewById(R.id.rl_design_introduction);
        mRlVersionDescription = (RelativeLayout)rootView.findViewById(R.id.rl_version_description);
        mTvVersion = (TextView)rootView.findViewById(R.id.tv_version);
        mTvGitCommitId = (TextView)rootView.findViewById(R.id.tv_git_commit_id);

    }

    @Override
    protected void initData() {
        mTvVersion.setText(Constant.VERSION_NUMBER);
        mTvGitCommitId.setText(BuildConfig.GITLASTCOMMITID);
    }

    @Override
    protected void initListener() {
        mRlDesignIntroduction.setOnClickListener(this);
        mRlVersionDescription.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_design_introduction:
                startActivity(new Intent(mContext,ConstructionIntroductionActivity.class));
                break;
            case R.id.rl_version_description:
                startActivity(new Intent(mContext,VersionDescriptionActivity.class));
                break;
            default:
                break;
        }
    }
}
