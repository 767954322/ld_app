package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;
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
import com.autodesk.shejijia.consumer.personalcenter.recommend.widget.ExpandListTextWatcher;
import com.autodesk.shejijia.consumer.personalcenter.recommend.widget.TextWatcherCallback;

import java.util.List;

import static com.autodesk.shejijia.consumer.personalcenter.recommend.widget.ExpandViewUtils.canVerticalScroll;

public class RecommendExpandableAdapter extends BaseExpandableListAdapter implements ExpandListHeaderInterface, TextWatcherCallback {

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
    public View getChildView(int groupPosition, final int childPosition,
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
//            mViewHolder.mEditText.setOnTouchListener(new OnTouchListener() {
//
//                @Override
//                public boolean onTouch(View view1, MotionEvent event) {
//                    //注意，此处必须使用getTag的方式，不能将position定义为final，写成mTouchItemPosition = position
//                    mTouchItemPosition = (Integer) view1.getTag();
//                    //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
//                    if ((view1.getId() == R.id.et_brand_num && canVerticalScroll((EditText) view1))) {
//                        view1.getParent().requestDisallowInterceptTouchEvent(true);
//                        if (event.getAction() == MotionEvent.ACTION_UP) {
//                            view1.getParent().requestDisallowInterceptTouchEvent(false);
//                        }
//                    }
//                    return false;
//                }
//            });

            // 让ViewHolder持有一个TextWathcer，动态更新position来防治数据错乱；不能将position定义成final直接使用，必须动态更新
            mViewHolder.mTextWatcher = new ExpandListTextWatcher();
            mViewHolder.mTextWatcher.addListener(this);

            mViewHolder.mEditText.addTextChangedListener(mViewHolder.mTextWatcher);
//            mViewHolder.mEditText.addTextChangedListener(mViewHolder.mTextWatcher);

            mViewHolder.updatePosition(groupPosition, childPosition);
            view.setTag(mViewHolder);
        }
        String brand_name = mRecommendSCFDList.get(groupPosition).getBrands().get(childPosition).getBrand_name();
        mViewHolder.mEditText.setText(brand_name);
        mViewHolder.mEditText.setTag(groupPosition * 10 + childPosition);

        if ((mTouchItemPosition / 10 == groupPosition) && (mTouchItemPosition % 10 == childPosition)) {
            mViewHolder.mEditText.requestFocus();
            mViewHolder.mEditText.setSelection(mViewHolder.mEditText.getText().length());
        } else {
            mViewHolder.mEditText.clearFocus();
        }

        return view;
    }


    static final class ViewHolder {
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

    @Override
    public void onCallBackData(int parentPosition, int ChildPosition, String data) {
        mRecommendSCFDList.get(parentPosition).getBrands().get(ChildPosition).setBrand_name(data);
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

}
