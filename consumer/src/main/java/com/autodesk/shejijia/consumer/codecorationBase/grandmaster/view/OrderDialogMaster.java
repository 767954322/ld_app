package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;

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
    private int header_drawble;
    private CommitListenser commitListenser;


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
        close = (ImageView) view.findViewById(R.id.work_room_dialog_close);
        dialogCancle();
        setCancelable(false);

    }

    //弹框消失
    public void dialogCancle() {

        close.setOnClickListener(this);

        commit.setOnClickListener(this);

    }

    public void setListenser(CommitListenser commitListenser) {

        this.commitListenser = commitListenser;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.commit_information:

                String nameText = name.getText().toString();
                String phoneNumberText = phoneNumber.getText().toString();
                if (!nameText.equals("") && !phoneNumberText.equals("")) {

                    if (commitListenser != null) {

                        commitListenser.commitListener(nameText, phoneNumberText);
                    }
                    dismiss();

                } else {

                    Toast.makeText(context, "信息不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.work_room_dialog_close:

                dismiss();
                break;
        }

    }


}
