package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.CustomHeaderExpandableListView;
import com.autodesk.shejijia.consumer.personalcenter.recommend.widget.ExpandListHeaderInterface;

import java.util.List;

//import com.autodesk.shejijia.consumer.personalcenter.recommend.widget.ExpandListHeaderInterface;

public class RecommendExpandableAdapter extends BaseExpandableListAdapter implements ExpandListHeaderInterface {

    private ViewHolder mViewHolder;
    private LayoutInflater inflater;
    private List<RecommendSCFDBean> mRecommendSCFDList;
    private CustomHeaderExpandableListView listView;
    private Activity mActivity;

    private int mTouchItemPosition = -1;
    private SparseIntArray groupStatusMap = new SparseIntArray();

    public RecommendExpandableAdapter(Activity activity, List<RecommendSCFDBean> recommendSCFDList, CustomHeaderExpandableListView recyclerViewList) {
        this.mActivity = activity;
        this.mRecommendSCFDList = recommendSCFDList;
        this.listView = recyclerViewList;
        inflater = LayoutInflater.from(this.mActivity);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mRecommendSCFDList.get(groupPosition).getBrands().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition,  int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
            mViewHolder = (ViewHolder) convertView.getTag();
            //动态更新TextWathcer的position
            mViewHolder.updatePosition(groupPosition, childPosition);
        } else {
            view = createChildrenView();
            mViewHolder = new ViewHolder();
            mViewHolder.mEditText = (EditText) view.findViewById(R.id.et_brand_num);

            setOnTouchListenerForEditText(mViewHolder.mEditText);
            // 让ViewHolder持有一个TextWathcer，动态更新position来防治数据错乱；不能将position定义成final直接使用，必须动态更新
            mViewHolder.mTextWatcher = new ExpandListTextWatcher();
            mViewHolder.mEditText.addTextChangedListener(mViewHolder.mTextWatcher);
            mViewHolder.updatePosition(groupPosition, childPosition);
            // 让ViewHolder持有一个TextWathcer，动态更新position来防治数据错乱；不能将position定义成final直接使用，必须动态更新

            view.setTag(mViewHolder);
        }
        String amountAndUnit = mRecommendSCFDList.get(groupPosition).getBrands().get(childPosition).getAmountAndUnit();
        mViewHolder.mEditText.setText(amountAndUnit);
        mViewHolder.mEditText.setTag(groupPosition * 10 + childPosition);

        if ((mTouchItemPosition / 10 == groupPosition) && (mTouchItemPosition % 10 == childPosition)) {
            mViewHolder.mEditText.requestFocus();
            mViewHolder.mEditText.setSelection(mViewHolder.mEditText.getText().length());
        } else {
            mViewHolder.mEditText.clearFocus();
        }

        return view;
    }
    private   boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }


    static  class ViewHolder {
        EditText mEditText;
        ExpandListTextWatcher mTextWatcher;

        //动态更新TextWathcer的position
        public void updatePosition(int groupPosition, int position) {
            mTextWatcher.updatePosition(groupPosition, position);
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mRecommendSCFDList.get(groupPosition).getBrands().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mRecommendSCFDList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return null == mRecommendSCFDList ? 0 : (mRecommendSCFDList.size());
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }
        // isExpanded 判断是否展开．
//        if (isExpanded) {
//            iv.setImageResource(R.drawable.btn_browser2);
//        } else {
//            iv.setImageResource(R.drawable.btn_browser);
//        }

        TextView text = (TextView) view.findViewById(R.id.tv_category_name);
        text.setText(mRecommendSCFDList.get(groupPosition).getSub_category_3d_name());
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createChildrenView() {
        return inflater.inflate(R.layout.item_brand_edit_view, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.item_group_indicator, null);
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        // TODO 设置显示悬浮重叠效果．
//        if (childPosition == childCount - 1) {
//            return PINNED_HEADER_PUSHED_UP;
//        } else if (childPosition == -1
//                && !listView.isGroupExpanded(groupPosition)) {
        return PINNED_HEADER_GONE;
//        } else {
//            return PINNED_HEADER_VISIBLE;
//        }
    }

    @Override
    public void configureHeader(View header, int groupPosition,
                                int childPosition, int alpha) {
        String groupData = this.mRecommendSCFDList.get(groupPosition).getSub_category_3d_name();
        ((TextView) header.findViewById(R.id.tv_category_name)).setText(groupData);
    }

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }


    public  void  setOnTouchListenerForEditText(EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //注意，此处必须使用getTag的方式，不能将position定义为final，写成mTouchItemPosition = position
                mTouchItemPosition = (Integer) view.getTag();
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((view.getId() == R.id.et_brand_num && canVerticalScroll((EditText) view))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }


      class ExpandListTextWatcher implements TextWatcher {

        //由于TextWatcher的afterTextChanged中拿不到对应的position值，所以自己创建一个子类
        private int ChildPosition;
        private int parentPosition;

        public void updatePosition(int groupPosition, int position) {
            this.ChildPosition = position;
            this.parentPosition = groupPosition;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mRecommendSCFDList.get(parentPosition).getBrands().get(ChildPosition).setAmountAndUnit(s.toString());
        }
    }
}
