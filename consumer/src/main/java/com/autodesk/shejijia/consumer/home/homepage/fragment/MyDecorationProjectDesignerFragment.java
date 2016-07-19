package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * Created by yaoxuehua on 16-7-13.
 */
public class MyDecorationProjectDesignerFragment extends BaseFragment{
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_decoration_project_designer;
    }

    @Override
    protected void initView() {

//        WindowManager wm = (WindowManager) getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        LinearLayout rl_contain = (LinearLayout) rootView.findViewById(R.id.rl_contain);
//
//        ChooseView chooseView = new ChooseView(getActivity(),width,60);
//
//        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//        View view = layoutInflater.inflate(R.layout.choose_view,null);
//        TextView textView = new TextView(getActivity());
//        textView.setText("1324654654");
//        rl_contain.addView(textView);
//        //rl_contain.addView(view);
//        rl_contain.addView(chooseView);

    }

    @Override
    protected void initData() {

    }
}
