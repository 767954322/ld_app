package com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.uielements.matertab.Utils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 2016/8/22 0025 14:46 .
 * @file WorkRoomDetailActivity  .
 * @brief 查看工作室详情页面 .
 */
public class OrderDialog extends Dialog implements View.OnClickListener {

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


    public interface CommitListenser {

        public void commitListener(String name, String phoneNumber);
    }

    public OrderDialog(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OrderDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    public void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_work_room_order, null);
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

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus == true) {

                } else {

                    if (name.getText().toString().equals("") ||
                            (name.getText().toString().length() < 2 && name.getText().toString().length() > 0) ||
                            (name.getText().toString().length() > 10)) {

                        line_name.setBackgroundColor(Color.parseColor("#ff0000"));
                        write_name.setVisibility(View.VISIBLE);
                        nameSure = false;
                    } else {

                        line_name.setBackgroundColor(Color.BLACK);
                        write_name.setVisibility(View.GONE);
                        nameSure = true;
                    }
                }
            }
        });

        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus == true) {

                } else {

                    String mobile = phoneNumber.getText().toString();
                    phoneRight = mobile.matches(RegexUtil.PHONE_REGEX);
                    if (phoneRight) {

                        line_phone.setBackgroundColor(Color.BLACK);
                        write_phone.setVisibility(View.GONE);

                    } else {
                        if (mobile.equals("") || (mobile.length() > 0 && mobile.length() <= 11)) {

                            line_phone.setBackgroundColor(Color.parseColor("#ff0000"));
                            write_phone.setVisibility(View.VISIBLE);
                        } else {

                            line_phone.setBackgroundColor(Color.BLACK);
                            write_phone.setVisibility(View.GONE);
                        }
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
                if (name.getText().toString().equals("") ||
                        (name.getText().toString().length() < 2 && name.getText().toString().length() > 0) ||
                        (name.getText().toString().length() > 10)) {

                    line_name.setBackgroundColor(Color.parseColor("#ff0000"));
                    write_name.setVisibility(View.VISIBLE);
                    nameSure = false;
                } else {

                    line_name.setBackgroundColor(Color.BLACK);
                    write_name.setVisibility(View.GONE);
                    nameSure = true;
                }
                //phone
                String mobile = phoneNumber.getText().toString();
                phoneRight = mobile.matches(RegexUtil.PHONE_REGEX);
                if (phoneRight) {

                    line_phone.setBackgroundColor(Color.BLACK);
                    write_phone.setVisibility(View.GONE);

                } else {
                    if (mobile.equals("") || (mobile.length() > 0 && mobile.length() <= 11)) {

                        line_phone.setBackgroundColor(Color.parseColor("#ff0000"));
                        write_phone.setVisibility(View.VISIBLE);
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
