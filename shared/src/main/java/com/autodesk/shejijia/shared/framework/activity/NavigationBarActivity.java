package com.autodesk.shejijia.shared.framework.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.matertab.MaterialTabs;

public class NavigationBarActivity extends BaseActivity {

    protected enum ButtonType {
        LEFT,
        LEFTCIRCLE,
        SECONDARY,
        BADGE,
        RIGHT,
        middle,
        middlecontain,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
        setupNavigationBar();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

   public ImageView getUserAvatar(){
        return (ImageView) findViewById(R.id.user_avatar);
    }

    @Override
    protected void initListener() {
        ImageButton leftImageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        ImageButton secondaryImageButton = (ImageButton) findViewById(R.id.nav_secondary_imageButton);
        ImageButton rightImageButton = (ImageButton) findViewById(R.id.nav_right_imageButton);
        TextView rightTextView = (TextView) findViewById(R.id.nav_right_textView);
        TextView leftTextView = (TextView) findViewById(R.id.nav_left_textView);
        userAvatar = (ImageView) findViewById(R.id.user_avatar);

        //2. hook up events

        if (userAvatar != null) {

            userAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftCircleUserAvarClicked(v);
                }
            });
        }

        if (leftImageButton != null)
            leftImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftNavButtonClicked(v);
                }
            });

        if (rightImageButton != null) {
            rightImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightNavButtonClicked(v);
                }
            });
        }

        if (secondaryImageButton != null) {
            secondaryImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondaryNavButtonClicked(v);
                }
            });
        }

        if (rightTextView != null) {
            rightTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightNavButtonClicked(v);
                }
            });
        }

        if (leftTextView != null) {
            leftTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftNavButtonClicked(v);
                }
            });
        }
    }

    protected void setVisibilityForNavButton(ButtonType type, boolean bShow) {
        int visibilityFlag = (bShow ? View.VISIBLE : View.GONE);

        View view = getNavigationButton(type);

        if (view != null)
            view.setVisibility(visibilityFlag);

        view = getNavigationTextView(type);

        if (view != null)
            view.setVisibility(visibilityFlag);

        ViewGroup viewGroup = getNavitionViewGroup(type);

        if (viewGroup != null)
            viewGroup.setVisibility(visibilityFlag);

    }

    protected void hideAllNavButtons() {
        setVisibilityForNavButton(ButtonType.LEFT, false);
        setVisibilityForNavButton(ButtonType.RIGHT, false);
        setVisibilityForNavButton(ButtonType.BADGE, false);
        setVisibilityForNavButton(ButtonType.SECONDARY, false);
    }

    protected void showBadgeOnNavBar(String value) {
        TextView badgeTextView = (TextView) findViewById(R.id.nav_badge_textView);

        if (badgeTextView != null) {
            badgeTextView.setText(value);
            badgeTextView.setVisibility(View.VISIBLE);
        }
    }

    protected void setTitleForNavbar(String value) {

        TextView titleTextView = (TextView) findViewById(R.id.nav_title_textView);
        if (titleTextView != null) {
            titleTextView.setText(value);
            MaterialTabs materialTabs = (MaterialTabs) findViewById(R.id.case_library_head);
            titleTextView.setVisibility(View.VISIBLE);
            materialTabs.setVisibility(View.GONE);
        }
    }

    protected void hideCaseLIbraryTitle() {
        TextView titleTextView = (TextView) findViewById(R.id.nav_title_textView);
        MaterialTabs materialTabs = (MaterialTabs) findViewById(R.id.case_library_head);
        titleTextView.setVisibility(View.VISIBLE);
        materialTabs.setVisibility(View.GONE);
        setVisibilityForNavButton(ButtonType.RIGHT, false);

    }

    protected void setCaseLIbraryTitle() {
        TextView titleTextView = (TextView) findViewById(R.id.nav_title_textView);
        MaterialTabs materialTabs = (MaterialTabs) findViewById(R.id.case_library_head);
        titleTextView.setVisibility(View.GONE);
        materialTabs.setVisibility(View.VISIBLE);
        setImageForNavButton(ButtonType.RIGHT,R.drawable.icon_search);
        setImageForNavButton(ButtonType.SECONDARY,R.drawable.icon_filtrate_normal);
        setVisibilityForNavButton(ButtonType.RIGHT, true);
        setVisibilityForNavButton(ButtonType.SECONDARY, true);

    }

    protected void setNavButtonEnabled(ButtonType type, boolean bEnabled) {
        View view = getNavigationButton(type);

        if (view != null)
            view.setEnabled(bEnabled);
    }

    protected void setTextColorForRightNavButton(int color) {
        TextView rightTextView = (TextView) findViewById(R.id.nav_right_textView);

        if (rightTextView != null) {
            rightTextView.setTextColor(color);
        }
    }

    protected void setImageForNavButton(ButtonType type, int resId) {
        View view = getNavigationButton(type);

        if (view != null && view instanceof ImageButton) {
            ((ImageButton) view).setImageResource(resId);
            view.setVisibility(View.VISIBLE);

            TextView textView = getNavigationTextView(type);

            if (textView != null)
                textView.setVisibility(View.GONE);
        }
    }

    protected void setTitleForNavButton(ButtonType type, String title) {
        TextView textView = getNavigationTextView(type);

        if (textView != null) {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);

            View view = getNavigationButton(type);

            if (view != null)
                view.setVisibility(View.GONE);

        }
    }

    //currently support is for either title or either image for navigation buttons
    // overiding title method for navigation buttons will ignore button image
    // we will support both with public interfaces afterwards
    protected void leftNavButtonClicked(View view) {
        finish();
    }

    protected void leftCircleUserAvarClicked(View view) {


    }


    protected void rightNavButtonClicked(View view) {
    }

    protected void secondaryNavButtonClicked(View view) {

    }

    //can be abstract method also
    protected String getActivityTitle() {
        return null;
    }

    protected String getLeftNavButtonTitle() {
        return null;
    }

    protected String getRightNavButtonTitle() {
        return null;
    }

    protected int getLeftButtonImageResourceId() {
        //default set in XML
        return 0;
    }

    protected int getRightButtonImageResourceId() {
        //default set in XML
        return 0;
    }

    protected int getSecondaryButtonImageResourceId() {
        //default set in XML
        return 0;
    }

    protected void setImageForNavCircleView(ButtonType type, int resId) {
        View view = getNavigationButton(type);

        if (view != null && view instanceof ImageView) {
            ((ImageView) view).setImageResource(resId);
            view.setVisibility(View.VISIBLE);

            TextView textView = getNavigationTextView(type);

            if (textView != null)
                textView.setVisibility(View.GONE);
        }
    }

    private void setupNavigationBar() {
        View navBar = findViewById(R.id.common_navbar);

        if (navBar != null) {

            View parentView = (View) navBar.getParent();

            if (parentView != null && parentView instanceof RelativeLayout) {
                navBar.bringToFront();
            }
        }

        // 1. setup navigation buttons

        // first setup left button
        ImageButton leftImageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);

        userAvatar = (ImageView) findViewById(R.id.user_avatar);

        if (leftImageButton != null) {

            int resId = getLeftButtonImageResourceId();

            if (resId > 0) {
                leftImageButton.setImageResource(resId);
                leftImageButton.setVisibility(View.VISIBLE);
            } else {
                String buttonTitle = getLeftNavButtonTitle();

                TextView leftTextView = (TextView) findViewById(R.id.nav_left_textView);

                if (buttonTitle != null && leftTextView != null) {
                    leftTextView.setText(buttonTitle);
                    leftTextView.setVisibility(View.VISIBLE);
                    leftImageButton.setVisibility(View.GONE);
                }
            }
        }

        // title view
        TextView titleView = (TextView) findViewById(R.id.nav_title_textView);

        if (titleView != null) {
            titleView.setText(getActivityTitle());
        }

        //secondary view
        ImageButton secondaryImageButton = (ImageButton) findViewById(R.id.nav_secondary_imageButton);

        if (secondaryImageButton != null) {
            secondaryImageButton.setVisibility(View.GONE);
            int resId = getSecondaryButtonImageResourceId();

            if (resId > 0) {
                secondaryImageButton.setVisibility(View.VISIBLE);
                secondaryImageButton.setImageResource(resId);
            }
        }

        //right button

        ImageButton rightImageButton = (ImageButton) findViewById(R.id.nav_right_imageButton);
        TextView rightTextView = (TextView) findViewById(R.id.nav_right_textView);

        if (rightImageButton != null && rightTextView != null) {

            rightImageButton.setVisibility(View.GONE);
            rightTextView.setVisibility(View.GONE);

            int resId = getRightButtonImageResourceId();

            if (resId > 0) {
                rightImageButton.setImageResource(resId);
                rightImageButton.setVisibility(View.VISIBLE);
                rightTextView.setVisibility(View.GONE);
            } else {
                String buttonTitle = getRightNavButtonTitle();

                if (buttonTitle != null) {
                    rightTextView.setText(buttonTitle);
                    rightTextView.setVisibility(View.VISIBLE);
                    rightImageButton.setVisibility(View.GONE);
                }
            }
        }

        //set badge value
        TextView badgeTextView = (TextView) findViewById(R.id.nav_badge_textView);

        if (badgeTextView != null) {
            badgeTextView.setVisibility(View.GONE);
        }
    }

    private View getNavigationButton(ButtonType type) {

        View view = null;

        if (type == ButtonType.LEFT)
            view = findViewById(R.id.nav_left_imageButton);
        else if (type == ButtonType.RIGHT)
            view = findViewById(R.id.nav_right_imageButton);
        else if (type == ButtonType.SECONDARY)
            view = findViewById(R.id.nav_secondary_imageButton);
        else if (type == ButtonType.BADGE)
            view = findViewById(R.id.nav_badge_textView);
        else if (type == ButtonType.LEFTCIRCLE)
            view = findViewById(R.id.user_avatar);

        return view;
    }

    private TextView getNavigationTextView(ButtonType type) {

        TextView textView = null;

        if (type == ButtonType.LEFT)
            textView = (TextView) findViewById(R.id.nav_left_textView);
        else if (type == ButtonType.RIGHT)
            textView = (TextView) findViewById(R.id.nav_right_textView);
        else if (type == ButtonType.middle)
            textView = (TextView) findViewById(R.id.nav_title_textView);


        return textView;
    }

    private ViewGroup getNavitionViewGroup(ButtonType type) {

        ViewGroup viewGroup = null;
        if (type == ButtonType.middlecontain)
            viewGroup = (ViewGroup) findViewById(R.id.ll_contain);

        return viewGroup;
    }
    private  ImageView userAvatar;


    public MaterialTabs getMaterialTabs() {

        return (MaterialTabs) findViewById(R.id.case_library_head);

    }
}