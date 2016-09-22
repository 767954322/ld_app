package com.autodesk.shejijia.shared.components.common.tools.wheel.adapters;

import android.content.Context;

import com.autodesk.shejijia.shared.components.common.tools.wheel.model.DistrictModel;

import java.util.List;

public class AreaAdapter extends AbstractWheelTextAdapter {
    private List<DistrictModel> mList;
    private Context mContext;

    public AreaAdapter(Context context, List<DistrictModel> list) {
        super(context);
        mList = list;
        mContext = context;
    }

    @Override
    protected CharSequence getItemText(int index) {
        DistrictModel districtModel = mList.get(index);
        return districtModel.NAME;
    }

    @Override
    public int getItemsCount() {
        return mList.size();
    }
}
