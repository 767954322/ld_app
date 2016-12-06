package com.autodesk.shejijia.shared.components.issue.common.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.issue.common.entity.IssueFllowBean;
import com.autodesk.shejijia.shared.components.issue.contract.PopItemClickContract;

import java.util.ArrayList;
import java.util.List;

import static com.autodesk.shejijia.shared.R.array.add_issue_type_list;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */
public class IssueFlowPop extends PopupWindow {

    private List<IssueFllowBean> list;
    private Context mContext;
    private View view;
    private ImageView mClosePopImg;
    private RecyclerView mIssueStyleList;
    private View.OnClickListener itemsOnClick;
    private PopItemClickContract onItemClickListener;
    private GalleryAdapter mListAdapter;


    public IssueFlowPop(List<IssueFllowBean> list, Context mContext, View.OnClickListener itemsOnClick, PopItemClickContract onItemClickListener) {
        this.list = list;
        this.mContext = mContext;
        this.itemsOnClick = itemsOnClick;
        this.onItemClickListener = onItemClickListener;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_issuefllow, null);

        initView();
        initListener();
        initPop();
        initListView();
    }

    private void initView() {
        mClosePopImg = (ImageView) this.view.findViewById(R.id.iv_close_style_pop);
        mIssueStyleList = (RecyclerView) this.view.findViewById(R.id.id_recyclerview_horizontal);
    }

    private void initListener() {

        mClosePopImg.setOnClickListener(itemsOnClick);
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

    private void initListView() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mIssueStyleList.setLayoutManager(linearLayoutManager);
        //设置适配器
        mListAdapter = new GalleryAdapter(LayoutInflater.from(mContext));
        mIssueStyleList.setAdapter(mListAdapter);
    }

    class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private LayoutInflater mInflater;

        public GalleryAdapter(LayoutInflater mInflater) {
            this.mInflater = mInflater;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_pop_issuefllow,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ViewHolder viewHolder = new ViewHolder(view);
            //TODO 此处有BUG，明天调试
//            viewHolder.mIssueFllowRole.setText(list.get(position).getmIssueFllowRole());
//            viewHolder.mIssueFllowName.setText(list.get(position).getmIssueFllowName());
//            ImageUtils.loadImageRound(viewHolder.mImage, list.get(position).getmImagePath());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View view) {
                super(view);
                mImage = (ImageView) view.findViewById(R.id.iv_issue_fllow_pic);
                mIssueFllowRole = (TextView) view.findViewById(R.id.tv_issue_fllow_person_role);
                mIssueFllowName = (TextView) view.findViewById(R.id.tv_issue_fllow_person_name);
            }

            ImageView mImage;
            TextView mIssueFllowRole;
            TextView mIssueFllowName;
        }
    }

}