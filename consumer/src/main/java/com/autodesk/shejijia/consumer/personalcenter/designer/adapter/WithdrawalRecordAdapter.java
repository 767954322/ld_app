package com.autodesk.shejijia.consumer.personalcenter.designer.adapter;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.WithdrawaRecoldBean;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.consumer.base.adapter.BaseAdapter;

import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-9-10
 * @file WithdrawalRecordAdapter.java  .
 * @brief 提现记录详情适配 .
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
        WithdrawaRecoldBean.TranslogListEntity translogListEntity = mDatas.get(position);
        double amount = translogListEntity.getAmount();

        switch (translogListEntity.getStatus()) {
            case -1:
                ((ViewHolder) holder).tv_withdrawal_recold_money.setText(amount + "元"); // 未处理
                ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.untreated));
                break;
            case 0:
                // DP-6086 fix 处理中 to DP-6200
                ((ViewHolder) holder).tv_withdrawal_recold_money.setText(amount + "元"); //　提现申请成功
                ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.in_the_processing));
                break;
            case 1:
                ((ViewHolder) holder).tv_withdrawal_recold_money.setText("-" + amount + "元"); //　提现成功
                ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.handle_successful));
                break;
            case 2:
                ((ViewHolder) holder).tv_withdrawal_recold_money.setText("+" + amount + "元"); //　提现失败
                ((ViewHolder) holder).tv_withdrawal_recold_state.setText(UIUtils.getString(R.string.handle_failure));
                break;
            default:
                break;
        }

        ((ViewHolder) holder).tv_item_lv_withdrawal_recold_bank.setText(translogListEntity.getBank_name());
        ((ViewHolder) holder).tv_withdrawal_recold_number.setText(translogListEntity.getTransLog_id() + "");


        String create_date = translogListEntity.getDate();
        String substring = create_date.substring(0, create_date.length() - 3);

        ((ViewHolder) holder).tv_withdrawal_recold_time.setText(substring);

        ((ViewHolder) holder).tv_withdrawal_recold_remark.setText("FSFSDFDSFDS");

        String remark = mDatas.get(position).getRemark();
        if (null != remark) {
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setMovementMethod(ScrollingMovementMethod.getInstance());
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setText(UIUtils.getString(R.string.remark) + remark);

        } else {
            ((ViewHolder) holder).tv_withdrawal_recold_remark.setVisibility(View.GONE);
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
