package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.PhoneNumberUtils;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 2016/8/22 0025 14:46 .
 * @file WorkRoomDetailActivity  .
 * @brief 查看工作室详情页面 .
 */
public class OrderDialogMaster extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText phoneNumber;
    private EditText name;
    private TextView commit;
    private ImageView close;
    private CommitListenser commitListenser;
    private TextView write_name;
    private TextView write_phone;
    private TextView line_name;
    private TextView line_phone;
    private boolean phoneRight;
    private boolean nameSure = false;
    private int header_drawble;
    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700
     **/
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
     **/
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
     * ,187,188,147,178,1705
     **/
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";


    public interface CommitListenser {

        public void commitListener(String name, String phoneNumber);
    }

    public OrderDialogMaster(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OrderDialogMaster(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    public OrderDialogMaster(Context context, int themeResId, int header_drawble) {
        super(context, themeResId);
        this.context = context;
        this.header_drawble = header_drawble;
        init();
    }

    public void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_grand_master_order, null);
        ImageView header_pic = (ImageView) view.findViewById(R.id.dialog_title_picture);
        header_pic.setImageResource(header_drawble);
        setContentView(view);

        phoneNumber = (EditText) view.findViewById(R.id.work_room_phoneNumber);
        name = (EditText) view.findViewById(R.id.work_room_name);
        commit = (TextView) view.findViewById(R.id.commit_information);
        close = (ImageView) view.findViewById(R.id.work_room_dialog_close);
        write_name = (TextView) view.findViewById(R.id.write_name);
        line_name = (TextView) view.findViewById(R.id.line_name);
        write_phone = (TextView) view.findViewById(R.id.write_phone);
        line_phone = (TextView) view.findViewById(R.id.line_phone);
        dialogCancle();
        setCancelable(false);
        setEdiTextListener();

    }

    //弹框消失
    public void dialogCancle() {

        close.setOnClickListener(this);

        commit.setOnClickListener(this);

    }

    public void setListenser(CommitListenser commitListenser) {

        this.commitListenser = commitListenser;
    }


    public void setEdiTextListener() {
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name.getText().toString().equals("")) {

                    line_name.setBackgroundColor(Color.parseColor("#fe6e6e"));
                    write_name.setVisibility(View.VISIBLE);
                    nameSure = false;
                } else if (name.getText().toString().length() < 2 || name.getText().toString().length() > 20) {
                    line_name.setBackgroundColor(Color.parseColor("#fe6e6e"));
                    write_name.setVisibility(View.VISIBLE);
                    write_name.setText(R.string.number_dashi);
                    nameSure = false;
                } else {

                    line_name.setBackgroundColor(Color.parseColor("#d7d7d7"));
                    write_name.setVisibility(View.GONE);
                    nameSure = true;
                }

            }
        });
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String mobile = phoneNumber.getText().toString();
                phoneRight = PhoneNumberUtils.justPhoneNumber(mobile);
                if (phoneRight) {

                    line_phone.setBackgroundColor(Color.BLACK);
                    write_phone.setVisibility(View.GONE);

                } else {
                    if (mobile.equals("")) {

                        line_phone.setBackgroundColor(Color.parseColor("#fe6e6e"));
                        write_phone.setVisibility(View.VISIBLE);
                    } else if ((mobile.length() > 0 && mobile.length() <= 11)) {
                        line_phone.setBackgroundColor(Color.parseColor("#fe6e6e"));
                        write_phone.setVisibility(View.VISIBLE);
                        write_phone.setText(R.string.mobile_phone);

                    } else {

                        line_phone.setBackgroundColor(Color.parseColor("#d7d7d7"));
                        write_phone.setVisibility(View.GONE);
                    }
                }

            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.commit_information:

                String nameText = name.getText().toString();
                String phoneNumberText = phoneNumber.getText().toString();
                //name
                if (TextUtils.isEmpty(nameText)) {

                    line_name.setBackgroundColor(Color.parseColor("#fe6e6e"));
                    write_name.setVisibility(View.VISIBLE);
                    nameSure = false;
                } else if (nameText.length() < 2 || nameText.length() > 20) {
                    line_name.setBackgroundColor(Color.parseColor("#fe6e6e"));
                    write_name.setVisibility(View.VISIBLE);
                    write_name.setText(R.string.number_dashi);
                    nameSure = false;
                } else {

                    line_name.setBackgroundColor(Color.parseColor("#d7d7d7"));
                    write_name.setVisibility(View.GONE);
                    nameSure = true;
                }
                //phone

                String mobile = phoneNumber.getText().toString();
                phoneRight = PhoneNumberUtils.justPhoneNumber(mobile);
                if (phoneRight) {

                    line_phone.setBackgroundColor(Color.parseColor("#d7d7d7"));
                    write_phone.setVisibility(View.GONE);

                } else {
                    if (mobile.equals("")) {

                        line_phone.setBackgroundColor(Color.parseColor("#fe6e6e"));
                        write_phone.setVisibility(View.VISIBLE);
                    } else if ((mobile.length() > 0 && mobile.length() <= 11)) {
                        line_phone.setBackgroundColor(Color.parseColor("#fe6e6e"));
                        write_phone.setVisibility(View.VISIBLE);
                        write_phone.setText(R.string.mobile_phone);

                    } else {

                        line_phone.setBackgroundColor(Color.BLACK);
                        write_phone.setVisibility(View.GONE);
                    }
                }


                if (commitListenser != null) {

                    if (nameSure && phoneRight) {

                        commitListenser.commitListener(nameText, phoneNumberText);
                        dismiss();
                    } else {


                    }
                }

                break;
            case R.id.work_room_dialog_close:

                dismiss();
                break;
        }

    }

}
