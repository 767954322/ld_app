package com.autodesk.shejijia.shared.components.common.uielements;

import android.app.Dialog;
import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.tools.wheel.CityDataHelper;
import com.autodesk.shejijia.shared.components.common.tools.wheel.OnWheelChangedListener;
import com.autodesk.shejijia.shared.components.common.tools.wheel.WheelView;
import com.autodesk.shejijia.shared.components.common.tools.wheel.adapters.AreaAdapter;
import com.autodesk.shejijia.shared.components.common.tools.wheel.adapters.CityAdapter;
import com.autodesk.shejijia.shared.components.common.tools.wheel.adapters.ProvinceAdapter;
import com.autodesk.shejijia.shared.components.common.tools.wheel.model.CityModel;
import com.autodesk.shejijia.shared.components.common.tools.wheel.model.DistrictModel;
import com.autodesk.shejijia.shared.components.common.tools.wheel.model.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-13 .
 * @file AddressDialog.java .
 * @brief 省市区弹出的dialog .
 */
public class AddressDialog extends DialogFragment implements OnWheelChangedListener, View.OnClickListener {

    public interface OnAddressCListener {
        void onClick(String province_name, String province, String city_name, String city, String district_name, String district);
    }

    public AddressDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_dialog_myinfo_changeaddress, null);
        mTvSure = (TextView) view.findViewById(R.id.btn_select_sure);
        mTvCancel = (TextView) view.findViewById(R.id.btn_select_cancel);

        mTvSure.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);

        mProvinceWheelView = (WheelView) view.findViewById(R.id.provinceView);
        mCityWheelView = (WheelView) view.findViewById(R.id.cityView);
        mDistrictWheelView = (WheelView) view.findViewById(R.id.districtView);

        // 设置可见条目数量
        mProvinceWheelView.setVisibleItems(7);
        mCityWheelView.setVisibleItems(7);
        mDistrictWheelView.setVisibleItems(7);

        // 添加change事件
        mProvinceWheelView.addChangingListener(this);
        // 添加change事件
        mCityWheelView.addChangingListener(this);
        // 添加change事件
        mDistrictWheelView.addChangingListener(this);
        initData();
        return view;
    }

    /// 设置dialog的样式，位于屏幕最下边并宽度与屏幕对齐 .
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.ShareDialogV2);
//        Dialog dialog = new Dialog(getActivity(), R.style.ShareDialog);
//        Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.mystyle);  //添加动画
//        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.x = 0;
//        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
//        // 以下这两句是为了保证按钮可以水平满屏
//        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        return dialog;
    }

    //初始化数据
    private void initData() {
        mCityDataHelper = CityDataHelper.getInstance(getActivity());
        mDb = mCityDataHelper.openDataBase();
        mProvinceModelArrayList = mCityDataHelper.getProvince(mDb);
        if (mProvinceModelArrayList.size() > 0) {
            province_name = mProvinceModelArrayList.get(0).NAME;
            province = mProvinceModelArrayList.get(0).CODE;
            mCityModelArrayList = mCityDataHelper.getCityByParentId(mDb, mProvinceModelArrayList.get(0).CODE);
        }
        if (mCityModelArrayList.size() > 0) {
            mDistrictModelArrayList = mCityDataHelper.getDistrictById(mDb, mCityModelArrayList.get(0).CODE);
        }
        mProvinceAdapter = new ProvinceAdapter(getActivity(), mProvinceModelArrayList);
        mProvinceAdapter.setTextSize(UIUtils.dip2px(getActivity(), TEXT_SIZE));//设置字体大小
        mProvinceAdapter.setTextColor(UIUtils.getColor(R.color.black));

        mProvinceWheelView.setViewAdapter(mProvinceAdapter);
        province_name = mProvinceModelArrayList.get(mProvinceWheelView.getCurrentItem()).NAME;
        province = mProvinceModelArrayList.get(mProvinceWheelView.getCurrentItem()).CODE;

        city_name = mCityModelArrayList.get(mCityWheelView.getCurrentItem()).NAME;
        city = mCityModelArrayList.get(mCityWheelView.getCurrentItem()).CODE;

        district_name = mDistrictModelArrayList.get(mDistrictWheelView.getCurrentItem()).NAME;
        district = mDistrictModelArrayList.get(mDistrictWheelView.getCurrentItem()).CODE;
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mProvinceWheelView) {
            province_name = mProvinceModelArrayList.get(newValue).NAME;
            province = mProvinceModelArrayList.get(newValue).CODE;
            updateCities();
        }
        if (wheel == mCityWheelView) {
            city_name = mCityModelArrayList.get(newValue).NAME;
            city = mCityModelArrayList.get(newValue).CODE;
            updateAreas();
        }
        if (wheel == mDistrictWheelView) {
            district_name = mDistrictModelArrayList.get(newValue).NAME;
            district = mDistrictModelArrayList.get(newValue).CODE;
        }
    }

    private void updateAreas() {
        int cCurrent = mCityWheelView.getCurrentItem();
        if (mCityModelArrayList.size() > 0) {
            mDistrictModelArrayList = mCityDataHelper.getDistrictById(mDb, mCityModelArrayList.get(cCurrent).CODE);
        } else {
            mDistrictModelArrayList.clear();
        }
        mAreaAdapter = new AreaAdapter(getActivity(), mDistrictModelArrayList);
        mAreaAdapter.setTextSize(UIUtils.dip2px(getActivity(), TEXT_SIZE));
        mDistrictWheelView.setViewAdapter(mAreaAdapter);
        if (mDistrictModelArrayList.size() > 0) {
            district_name = mDistrictModelArrayList.get(0).NAME;
            district = mDistrictModelArrayList.get(0).CODE;
            mDistrictWheelView.setCurrentItem(0);
        } else {
            district_name = "";
        }
    }

    private void updateCities() {
        int pCurrent = mProvinceWheelView.getCurrentItem();
        if (mProvinceModelArrayList.size() > 0) {
            mCityModelArrayList = mCityDataHelper.getCityByParentId(mDb, mProvinceModelArrayList.get(pCurrent).CODE);
        } else {
            mCityModelArrayList.clear();
        }
        mCityAdapter = new CityAdapter(getActivity(), mCityModelArrayList);
        mCityAdapter.setTextSize(UIUtils.dip2px(getActivity(), TEXT_SIZE));
        mCityWheelView.setViewAdapter(mCityAdapter);
        if (mCityModelArrayList.size() > 0) {
            mCityWheelView.setCurrentItem(0);
            city_name = mCityModelArrayList.get(0).NAME;
            city = mCityModelArrayList.get(0).CODE;
        } else {
            city_name = "";
        }
        updateAreas();
    }

    public void setAddressListener(OnAddressCListener onAddressCListener) {
        this.onAddressCListener = onAddressCListener;
    }

    @Override
    public void onClick(View v) {
        if (v == mTvSure) {
            if (onAddressCListener != null) {
                onAddressCListener.onClick(province_name, province, city_name, city, district_name, district);
            }
        }
        if (v == mTvCancel) {
            dismiss();
        }
    }

    private static final int TEXT_SIZE = 7;//选择器的字体大小
    private WheelView mProvinceWheelView;
    private WheelView mCityWheelView;
    private WheelView mDistrictWheelView;
    private TextView mTvSure;//确定按钮
    private TextView mTvCancel;//取消按钮

    private String province, province_name;
    private String city, city_name;
    private String district, district_name;

    private List<ProvinceModel> mProvinceModelArrayList = new ArrayList<>();
    private List<CityModel> mCityModelArrayList = new ArrayList<>();
    private List<DistrictModel> mDistrictModelArrayList = new ArrayList<>();

    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private AreaAdapter mAreaAdapter;
    private SQLiteDatabase mDb;
    private CityDataHelper mCityDataHelper;
    //回调方法
    private OnAddressCListener onAddressCListener;
}