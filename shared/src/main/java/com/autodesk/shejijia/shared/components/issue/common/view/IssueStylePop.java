package com.autodesk.shejijia.shared.components.issue.common.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.issue.contract.PopItemClickContract;

import java.util.ArrayList;
import java.util.List;

import static com.autodesk.shejijia.shared.R.array.add_issue_type_list;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */
public class IssueStylePop extends PopupWindow {

    private Context mContext;
    private List<String> strList;

    private View view;
    private ListView mIssueStyleList;
    private View.OnClickListener itemsOnClick;
    private PopItemClickContract onItemClickListener;


    public IssueStylePop(Context mContext, View.OnClickListener itemsOnClick, PopItemClickContract onItemClickListener) {
        this.mContext = mContext;
        this.itemsOnClick = itemsOnClick;
        this.onItemClickListener = onItemClickListener;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_issuestyle, null);
        initView();
        initPop();
        initList();
    }


    private void initView() {
        mIssueStyleList = (ListView) this.view.findViewById(R.id.lv_pop_issuestytle);
    }

    private void initPop() {
        // 设置外部可点击
        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void initList() {
        if (strList == null) {
            strList = new ArrayList<>();
        }
        strList.clear();
        String[] strArray = mContext.getResources().getStringArray(add_issue_type_list);
        for (int i = 0; i < strArray.length; i++) {
            strList.add(strArray[i]);
        }
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, R.layout.item_pop_issuestyle, strList);
        mIssueStyleList.setAdapter(itemAdapter);
        mIssueStyleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClickListener.onPopItemClickListener(view, position);
            }
        });
    }

}
