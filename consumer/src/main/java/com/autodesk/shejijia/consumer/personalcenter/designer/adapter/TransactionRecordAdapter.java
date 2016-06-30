package com.autodesk.shejijia.consumer.personalcenter.designer.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.adapter.BaseAdapter;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.TransactionRecordBean;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file TransactionRecordAdapter.java  .
 * @brief 交易记录.
 */
public class TransactionRecordAdapter extends BaseAdapter<TransactionRecordBean.DesignerTransListEntity> {

    public TransactionRecordAdapter(Context context, List<TransactionRecordBean.DesignerTransListEntity> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lv_transation_record;
    }

    @Override
    public Holder initHolder(View container) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_project_name = (TextView) container.findViewById(R.id.tv_my_property_project_name);
        viewHolder.tv_project_time = (TextView) container.findViewById(R.id.tv_my_property_project_time);
        viewHolder.tv_order_reference = (TextView) container.findViewById(R.id.tv_my_property_order_reference);
        viewHolder.tv_transaction_name = (TextView) container.findViewById(R.id.tv_my_property_transaction_name);
        viewHolder.tv_transaction_type = (TextView) container.findViewById(R.id.tv_my_property_transaction_type);
        viewHolder.tv_transaction_amount = (TextView) container.findViewById(R.id.tv_my_property_transaction_amount);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {
        ((ViewHolder) holder).tv_project_name.setText(mDatas.get(position).getName());

        String data = DateUtil.showDate(mDatas.get(position).getCreate_date());

        ((ViewHolder) holder).tv_project_time.setText(data);

        ((ViewHolder) holder).tv_order_reference.setText(mDatas.get(position).getOrder_line_id() + "");
        ((ViewHolder) holder).tv_transaction_name.setText(mDatas.get(position).getTitle());
        ((ViewHolder) holder).tv_transaction_amount.setText("¥" + mDatas.get(position).getAdjustment());
        if (mDatas.get(position).getType().equals("0")) {
            ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.no_payment));
        } else if (mDatas.get(position).getType().equals("1")) {
            ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.paid));
        } else if (mDatas.get(position).getType().equals("2")) {
            ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.transfer_room_design));
        } else if (mDatas.get(position).getType().equals("3")) {
            ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.has_been_booked));
        }

    }

    public class ViewHolder extends BaseAdapter.Holder {
        TextView tv_project_name;
        TextView tv_project_time;
        TextView tv_transaction_name;
        TextView tv_transaction_amount;
        TextView tv_order_reference;
        TextView tv_transaction_type;
    }
}
