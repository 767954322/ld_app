package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.os.Bundle;
import android.widget.RatingBar;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-1 .
 * @file AppraiseDesignerActivity.java .
 * @brief 设计师评价页面 .
 */
public class AppraiseDesignerActivity extends NavigationBarActivity implements RatingBar.OnRatingBarChangeListener {
    private RatingBar mRatingBarStar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_appraise;
    }

    @Override
    protected void initView() {
        super.initView();
        mRatingBarStar = (RatingBar) findViewById(R.id.rating_star);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRatingBarStar.setOnRatingBarChangeListener(this);
        mRatingBarStar.setRating(2);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        KLog.d("rating=" + rating);
        ratingBar.setRating(rating);
    }
}
