package com.autodesk.shejijia.shared.components.issue.common.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Profile;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.issue.common.tools.IssueRoleUtils;
import com.autodesk.shejijia.shared.components.issue.contract.PopItemClickContract;

import java.util.ArrayList;

import static com.autodesk.shejijia.shared.R.array.add_issue_fllow;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */
public class IssueFlowPop extends PopupWindow {

    private PopItemClickContract mOnItemClickListener;
    private ArrayList<Member> mListMember;
    private RecyclerView mIssueStyleList;
    private GalleryAdapter mListAdapter;
    private Context mContext;
    private View mView;

    public IssueFlowPop(ArrayList<Member> listMember, Context mContext, PopItemClickContract onItemClickListener) {
        this.mListMember = listMember;
        this.mContext = mContext;
        this.mOnItemClickListener = onItemClickListener;
        this.mView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_issuefllow, null);
        initView();
        initPop();
        initListView();
    }

    private void initView() {
        mIssueStyleList = (RecyclerView) this.mView.findViewById(R.id.id_recyclerview_horizontal);
    }

    private void initPop() {
        // 设置外部可点击
        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.mView);
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
        this.mView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mView.findViewById(R.id.pop_layout).getTop();

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
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mIssueStyleList.setLayoutManager(linearLayoutManager);
        //设置适配器
        mListAdapter = new GalleryAdapter(LayoutInflater.from(mContext), mListMember);
        mIssueStyleList.setAdapter(mListAdapter);
    }

    class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private LayoutInflater mInflater;
        private ArrayList<Member> listMember;

        public GalleryAdapter(LayoutInflater mInflater, ArrayList<Member> listMember) {
            this.mInflater = mInflater;
            this.listMember = listMember;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_pop_issuefllow,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            String chinaRole = IssueRoleUtils.getInstance().getChiRoleByEngRole(listMember.get(position).getRole());
            holder.mIssueFllowRole.setText(chinaRole);
            Profile profile = listMember.get(position).getProfile();
            if (profile != null) {
                holder.mIssueFllowName.setText(listMember.get(position).getProfile().getName());
                ImageUtils.displayRoundImage(listMember.get(position).getProfile().getAvatar(), holder.mImage);
            }
            holder.mIssueFllowAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onPopItemClickListener(v, position);
                }
            });
        }

        @Override
        public int getItemCount() {

            return mListMember.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View view) {
                super(view);
                mImage = (ImageView) view.findViewById(R.id.iv_issue_fllow_pic);
                mIssueFllowRole = (TextView) view.findViewById(R.id.tv_issue_fllow_person_role);
                mIssueFllowName = (TextView) view.findViewById(R.id.tv_issue_fllow_person_name);
                mIssueFllowAll = (RelativeLayout) view.findViewById(R.id.rl_issue_fllow_person);
            }

            ImageView mImage;
            TextView mIssueFllowRole;
            TextView mIssueFllowName;
            RelativeLayout mIssueFllowAll;
        }
    }

}