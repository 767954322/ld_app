package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.fragment.BidHallFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.ConsumerPersonalCenterFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.DesignerPersonalCenterFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.UserHomeFragment;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseHomeActivity;

public class MPConsumerHomeActivity extends BaseHomeActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_main;
    }

    @Override
    protected void initView() {
        super.initView();

        //hide navigation left button
        setVisibilityForNavButton(ButtonType.LEFT, false);

        mDesignerMainRadioBtn = (RadioButton) findViewById(getDesignerMainRadioBtnId());
        designer_main_radio_group = (RadioGroup) findViewById(R.id.designer_main_radio_group);
        designer_main_radio_btn = (RadioButton) findViewById(R.id.designer_main_radio_btn);
        mDesignerIndentListBtn = (RadioButton) findViewById(R.id.designer_indent_list_btn);
        mDesigner_session_radio_btn = (RadioButton) findViewById(R.id.designer_session_radio_btn);
        mDesignerPersonCenterRadioBtn = (RadioButton) findViewById(R.id.designer_person_center_radio_btn);

        addRadioButtons(mDesignerMainRadioBtn);
        addRadioButtons(mDesignerIndentListBtn);
        addRadioButtons(mDesignerPersonCenterRadioBtn);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        showDesignerOrConsumerRadioGroup();
        super.initData(savedInstanceState);
        showFragment(getDesignerMainRadioBtnId());
    }


    @Override
    protected void onResume() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(Constant.DesignerBeiShuMeal.SKIP_DESIGNER_PERSONAL_CENTER, -1);
        if (id > 0) {
            switch (id) {
                case 1:
                    /// 回到个人中心 .
                    mDesignerPersonCenterRadioBtn.performClick();
                    break;
                default:
                    break;
            }
        }

//        else {
//            mDesignerMainRadioBtn.performClick();
//            showDesignerOrConsumerRadioGroup();
//        }
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showDesignerOrConsumerRadioGroup();
        MemberEntity  mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        //登陆设计师时，会进入；
        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            designer_main_radio_group.check(index);

        }
        //登陆消费者时，会进入
        if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())){
            designer_main_radio_group.check(index);
        }

        //未登录状态，会自动进入案例fragment
        if (mMemberEntity == null){

            designer_main_radio_btn.setChecked(true);

        }
    }

    @Override
    protected RadioButton getRadioButtonById(int id) {
        RadioButton button = super.getRadioButtonById(id);
        switch (id) {
            case R.id.designer_main_radio_btn:
                button = mDesignerMainRadioBtn;
                break;
            case R.id.designer_indent_list_btn:
                button = mDesignerIndentListBtn;
                break;
            case R.id.designer_person_center_radio_btn:
                button = mDesignerPersonCenterRadioBtn;
                break;
        }

        return button;
    }

    @Override
    protected boolean needLoginOnRadiobuttonTap(int id) {
        if ((super.needLoginOnRadiobuttonTap(id)) ||
                (id == R.id.designer_indent_list_btn) ||
                (id == R.id.designer_person_center_radio_btn))
            return true;
        else
            return false;
    }


    @Override
    protected void initAndAddFragments(int index) {
        super.initAndAddFragments(index);


        this.index = index;

        if (mUserHomeFragment == null && index == getDesignerMainRadioBtnId()) {
            mUserHomeFragment = new UserHomeFragment();
            loadMainFragment(mUserHomeFragment);
        }

        if (mBidHallFragment == null && index == R.id.designer_indent_list_btn) {
            mBidHallFragment = new BidHallFragment();
            loadMainFragment(mBidHallFragment);
        }

        if (index == R.id.designer_person_center_radio_btn) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

            if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
                if (mDesignerPersonalCenterFragment == null) {
                    mDesignerPersonalCenterFragment = new DesignerPersonalCenterFragment();
                    loadMainFragment(mDesignerPersonalCenterFragment);
                }
            } else {
                if (mConsumerPersonalCenterFragment == null) {
                    mConsumerPersonalCenterFragment = new ConsumerPersonalCenterFragment();
                    loadMainFragment(mConsumerPersonalCenterFragment);
                }
            }
        }
    }

    @Override
    protected Fragment getFragmentByButtonId(int id) {
        Fragment f = super.getFragmentByButtonId(id);
        if (id == R.id.designer_indent_list_btn)
            f = mBidHallFragment;
        else if (id == R.id.designer_person_center_radio_btn) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type()))
                f = mDesignerPersonalCenterFragment;
            else
                f = mConsumerPersonalCenterFragment;
        } else if (id == getDesignerMainRadioBtnId()) {
            f = mUserHomeFragment;
        }
        return f;
    }


    @Override
    protected void leftNavButtonClicked(View view) {
        if (isActiveFragment(BidHallFragment.class))
            mBidHallFragment.handleFilterOption();
    }

    protected void configureNavigationBar(int index) {

        super.configureNavigationBar(index);

        switch (index) {
            case R.id.designer_main_radio_btn:
                setTitleForNavbar(UIUtils.getString(R.string.app_name));
                break;
            case R.id.designer_indent_list_btn:    /// 应标大厅按钮.
                TextView textView = (TextView) findViewById(R.id.nav_left_textView);
                textView.setVisibility(View.VISIBLE);
                Drawable drawable = UIUtils.getDrawable(R.drawable.shanjiao_ico);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, drawable, null);
                textView.setText(UIUtils.getString(R.string.bid_filter));
                setTitleForNavbar(UIUtils.getString(R.string.tab_hall));
                break;

            case R.id.designer_person_center_radio_btn:  /// 个人中心按钮.
                setTitleForNavbar(UIUtils.getString(R.string.designer_personal));
                break;

            default:
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == getDesignerMainRadioBtnId())
            showFragment(getDesignerMainRadioBtnId());

        super.onCheckedChanged(group, checkedId);
    }

    protected int getDesignerMainRadioBtnId() {
        return R.id.designer_main_radio_btn;
    }

    protected int getDesignerSessionRadioBtnId() {
        return R.id.designer_session_radio_btn;
    }

    protected int getRadioGroupId() {
        return R.id.designer_main_radio_group;
    }


    protected int getMainContentId() {
        return R.id.main_content;
    }


    private void showDesignerOrConsumerRadioGroup() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
            mDesignerIndentListBtn.setVisibility(View.VISIBLE);
        } else {
            TextView textView = (TextView) findViewById(R.id.tv_gron_msg_number);
            textView.setVisibility(View.VISIBLE);
            mDesignerIndentListBtn.setVisibility(View.GONE);
        }
    }

    private RadioButton mDesignerMainRadioBtn;
    private RadioButton mDesignerPersonCenterRadioBtn;
    private RadioButton mDesignerIndentListBtn;
    private RadioButton designer_main_radio_btn;
    private RadioButton mDesigner_session_radio_btn;
    private RadioGroup designer_main_radio_group;
    private int index;//判断所在fragment
    private int lastIndex;

    private UserHomeFragment mUserHomeFragment;

    private BidHallFragment mBidHallFragment;
    private DesignerPersonalCenterFragment mDesignerPersonalCenterFragment;
    private ConsumerPersonalCenterFragment mConsumerPersonalCenterFragment;
}
