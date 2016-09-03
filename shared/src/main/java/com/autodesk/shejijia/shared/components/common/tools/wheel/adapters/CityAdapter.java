package com.autodesk.shejijia.shared.components.common.tools.wheel.adapters;

import android.content.Context;

import com.autodesk.shejijia.shared.components.common.tools.wheel.model.CityModel;

import java.util.List;


/**
 * Created by liuhe on 16/1/7.
 */
public class CityAdapter extends AbstractWheelTextAdapter {
    public List<CityModel> mList;
    private Context mContext;

    public CityAdapter(Context context, List<CityModel> list) {
        super(context);
        mList = list;
        mContext = context;
    }

    @Override
    protected CharSequence getItemText(int index) {
        CityModel cityModel = mList.get(index);
        return cityModel.NAME;
    }

    @Override
    public int getItemsCount() {
        return mList.size();
    }
}
