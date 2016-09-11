package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private RelativeLayout close;
    private CommitListenser commitListenser;
    private TextView write_name;
    private TextView write_phone;
    private TextView line_name;
    private TextView line_phone;
    private int header_drawble;
    private boolean phoneRight;
    private boolean nameSure = false;


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

    public OrderDialogMaster(Context context, int themeResId,int header_drawble) {
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
        close = (RelativeLayout) view.findViewById(R.id.work_room_dialog_close);
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

    //判断是不是手机号
    public static boolean checkPhoneNumber(String phone) {
        String str = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(phone);
        boolean isMatches = m.matches();
        return isMatches;
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
                phoneRight = checkPhoneNumber(mobile);
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
