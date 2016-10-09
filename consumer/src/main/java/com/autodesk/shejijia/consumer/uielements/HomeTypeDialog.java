package com.autodesk.shejijia.consumer.uielements;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.wheel.OnWheelChangedListener;
import com.autodesk.shejijia.shared.components.common.tools.wheel.WheelView;
import com.autodesk.shejijia.shared.components.common.tools.wheel.adapters.HomeWheelAdapter;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 户型选择
 */
public class HomeTypeDialog extends DialogFragment implements
        OnWheelChangedListener,
        View.OnClickListener {

    private static final String HOUSE_PATH = "house.json";
    private static final String JSON_ROOT_NAME = "housetypelist";
    private static final int ITEM_NUM = 7;    // 设置可见条目数量
    private static final int TEXT_SIZE = 7;//选择器的字体大小

    /**
     * 所有室
     */
    private String[] mRoomDatas;
    /**
     * key - 室 value - 卫s
     */
    private Map<String, String[]> mLivingRoomDatasMap = new HashMap<String, String[]>();

    /**
     * key - 卫 values - 厅s
     */
    private Map<String, String[]> mToiletDatasMap = new HashMap<String, String[]>();
    /**
     * 当前室的名称
     */
    private String mCurrentRoomName = "";
    /**
     * 当前卫的名称
     */
    private String mCurrentLivingRoomName = "";
    /**
     * 当前厅的名称
     */
    private String mCurrentToiletName = "";

    private WheelView mWlRoom;
    private WheelView mWlLivingRoom;
    private WheelView mWlToilet;
    private TextView mTvSure;//确定按钮
    private TextView mTvCancel;//取消按钮

    private static JSONObject mJsonObj;

    //回调方法
    private OnAddressCListener onAddressCListener;

    public interface OnAddressCListener {
        void onClick(String roomName, String livingRoom, String toilet);
    }

    public void setOnAddressCListener(OnAddressCListener onAddressCListener) {
        this.onAddressCListener = onAddressCListener;
    }

    public static HomeTypeDialog getInstance(Activity activity) {
        HomeTypeDialog dialog = new HomeTypeDialog();
        dialog.setCancelable(false);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_dialog_hometype, null);

        initView(view);
        initJsonData(getActivity());

        initDatas();
        setClickListener();
        addChangeListener();
        setVisibleItems(ITEM_NUM);

        HomeWheelAdapter mHomeWheelAdapter = new HomeWheelAdapter<>(getActivity(), mRoomDatas);
        mHomeWheelAdapter.setTextSize(UIUtils.dip2px(getActivity(), TEXT_SIZE));//设置字体大小
        mHomeWheelAdapter.setTextColor(UIUtils.getColor(R.color.black));
        mWlRoom.setViewAdapter(mHomeWheelAdapter);

        updateLivingRoom();

        return view;
    }

    /// 设置dialog的样式，位于屏幕最下边并宽度与屏幕对齐 .
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.ShareDialog);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        return dialog;
    }

    private void initView(View view) {
        mTvSure = (TextView) view.findViewById(R.id.btn_select_sure);
        mTvCancel = (TextView) view.findViewById(R.id.btn_select_cancel);
        mWlRoom = (WheelView) view.findViewById(R.id.wl_room);
        mWlLivingRoom = (WheelView) view.findViewById(R.id.wl_living_room);
        mWlToilet = (WheelView) view.findViewById(R.id.wl_toilet);
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray(JSON_ROOT_NAME);
            mRoomDatas = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个室的json对象
                String province = jsonP.getString("p");// 室名字

                mRoomDatas[i] = province;

                JSONArray jsonCs = null;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1) {
                    continue;
                }
                String[] mLivingRoomDatas = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonLivingRoom = jsonCs.getJSONObject(j);
                    String livingRoom = jsonLivingRoom.getString("n");// 卫名字
                    mLivingRoomDatas[j] = livingRoom;
                    JSONArray jsonToilet = null;
                    try {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonToilet = jsonLivingRoom.getJSONArray("a");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] toilets = new String[jsonToilet.length()];// 当前卫的所有区
                    for (int k = 0; k < jsonToilet.length(); k++) {
                        String area = jsonToilet.getJSONObject(k).getString("s");// 厅的名称
                        toilets[k] = area;
                    }
                    mToiletDatasMap.put(livingRoom, toilets);
                }

                mLivingRoomDatasMap.put(province, mLivingRoomDatas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mWlRoom) {
            updateLivingRoom();
        } else if (wheel == mWlLivingRoom) {
            updateToilet();
        } else if (wheel == mWlToilet) {
            mCurrentToiletName = mToiletDatasMap.get(mCurrentLivingRoomName)[newValue];
        }
    }

    @Override
    public void onClick(View v) {

        if (v == mTvSure) {
            if (onAddressCListener != null) {
                onAddressCListener.onClick(mCurrentRoomName, mCurrentLivingRoomName, mCurrentToiletName);
            }
        }
        if (v == mTvCancel) {
            dismiss();
        }
    }

    private static void initJsonData(Activity activity) {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = activity.getAssets().open(HOUSE_PATH);
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateLivingRoom() {
        int pCurrent = mWlRoom.getCurrentItem();
        mCurrentRoomName = mRoomDatas[pCurrent];
        String[] livingRooms = mLivingRoomDatasMap.get(mCurrentRoomName);
        if (livingRooms == null) {
            livingRooms = new String[]{""};
        }
        HomeWheelAdapter<String> livingRoomAdapter = new HomeWheelAdapter<>(getActivity(), livingRooms);
        livingRoomAdapter.setTextSize(UIUtils.dip2px(getActivity(), TEXT_SIZE));//设置字体大小
        livingRoomAdapter.setTextColor(UIUtils.getColor(R.color.black));
        mWlLivingRoom.setViewAdapter(livingRoomAdapter);
        mWlLivingRoom.setCurrentItem(0);
        updateToilet();
    }

    private void updateToilet() {
        int pCurrent = mWlLivingRoom.getCurrentItem();
        mCurrentLivingRoomName = mLivingRoomDatasMap.get(mCurrentRoomName)[pCurrent];
        String[] toilets = mToiletDatasMap.get(mCurrentLivingRoomName);

        if (toilets == null) {
            toilets = new String[]{""};
        }

        int currentItem = mWlToilet.getCurrentItem();
        mCurrentToiletName = mToiletDatasMap.get(mCurrentLivingRoomName)[currentItem];

        HomeWheelAdapter<String> toiletAdapter = new HomeWheelAdapter<>(getActivity(), toilets);
        toiletAdapter.setTextSize(UIUtils.dip2px(getActivity(), TEXT_SIZE));//设置字体大小
        toiletAdapter.setTextColor(UIUtils.getColor(R.color.black));
        mWlToilet.setViewAdapter(toiletAdapter);
        mWlToilet.setCurrentItem(0);

    }

    private void setClickListener() {
        mTvSure.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    private void setVisibleItems(int i) {
        // 设置可见条目数量
        mWlRoom.setVisibleItems(i);
        mWlLivingRoom.setVisibleItems(i);
        mWlToilet.setVisibleItems(i);
    }

    private void addChangeListener() {
        // 添加change事件
        mWlRoom.addChangingListener(this);
        // 添加change事件
        mWlLivingRoom.addChangingListener(this);
        // 添加change事件
        mWlToilet.addChangingListener(this);

    }
}