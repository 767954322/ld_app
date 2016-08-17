package com.autodesk.shejijia.consumer.personalcenter.designer.adapter;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.adapter.BaseAdapter;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.WithdrawaRecoldBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file WithdrawalRecordAdapter.java  .
 * @brief .
 */
public class WithdrawalRecordAdapter extends BaseAdapter<WithdrawaRecoldBean.TranslogListEntity> {

    public WithdrawalRecordAdapter(Context context, List<WithdrawaRecoldBean.TranslogListEntity> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lv_withdrawal_recold;
    }

    @Override
    public Holder initHolder(View container) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_item_lv_withdrawal_recold_bank = (TextView) container.findViewById(R.id.tv_item_lv_withdrawal_recold_bank);
        viewHolder.tv_withdrawal_recold_state = (TextView) container.findViewById(R.id.tv_withdrawal_recold_state);
        viewHolder.tv_withdrawal_recold_money = (TextView) container.findViewById(R.id.tv_withdrawal_recold_money);
        viewHolder.tv_withdrawal_recold_number = (TextView) container.findViewById(R.id.tv_withdrawal_recold_number);
        viewHolder.tv_withdrawal_recold_time = (TextView) container.findViewById(R.id.tv_withdrawal_recold_time);
        viewHolder.tv_withdrawal_recold_remark = (TextView) container.findViewById(R.id.tv_withdrawal_recold_remark);

        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {
        ((ViewHolder) holder).tv_item_lv_withdrawal_recold_bank.setText(mDatas.get(position).getBank_name());
        ((ViewHolder) holder).tv_withdrawal_recold_money.setText("¥" + mDatas.get(position).getAmount());
        ((ViewHolder) holder).tv_withdrawal_recold_number.setText(mDatas.get(position).getTransLog_id() + "");
        ((ViewHolder) holder).tv_withdrawal_recold_time.setText(mDatas.get(position).getDate());

        ((ViewHolder) holder).tv_withdrawal_recold_remark.setText("FSFSDFDSFDS");

        String remark = mDatas.get(position).getRemark();
        if (null != remark) {
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setMovementMethod(ScrollingMovementMethod.getInstance());
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setText(UIUtils.getString(R.string.remark) + remark);

        } else {
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setVisibility(View.GONE);
        }
        int status = mDatas.get(position).getStatus();
        if (status == 0) {
            ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.in_the_processing));
        } else if (status == 1) {
            ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.handle_successful));
        } else if (status == 2) {
            ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.handle_failure));
        } else if (status == -1) {
            ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.untreated));
        }
    }

    public class ViewHolder extends BaseAdapter.Holder {
        TextView tv_withdrawal_recold_state;
        TextView tv_withdrawal_recold_money;
        TextView tv_withdrawal_recold_number;
        TextView tv_withdrawal_recold_time;
        TextView tv_item_lv_withdrawal_recold_bank;
        TextView tv_withdrawal_recold_remark;
    }
}
