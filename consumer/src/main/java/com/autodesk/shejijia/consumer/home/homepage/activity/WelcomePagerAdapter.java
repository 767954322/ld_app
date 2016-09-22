package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by zjl on 16-9-12.
 */
public class WelcomePagerAdapter extends PagerAdapter {
    public List<Integer> comm_data_ls;
    private final Activity context;
    private View itemView;
    public static final String ISFIRST = "isfirst";

    public WelcomePagerAdapter(Activity context, List<Integer> comm_data_ls) {
        this.context = context;
        this.comm_data_ls = comm_data_ls;
    }

    @Override
    public int getCount() {
        return comm_data_ls.size();
    }

    public Object instantiateItem(ViewGroup container, final int position) {
        itemView = View.inflate(context, R.layout.item_viewpage_content, null);
        ImageView imageView = ((ImageView) itemView.findViewById(R.id.image));
        Button start = (Button) itemView.findViewById(R.id.start);
        imageView.setBackgroundResource(comm_data_ls.get(position));
        //开启
        start.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.writeBoolean(ISFIRST, true);
                MPConsumerHomeActivity.start(context);
            }
        });
        container.addView(itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
//        container.removeView(itemView);
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }
}
