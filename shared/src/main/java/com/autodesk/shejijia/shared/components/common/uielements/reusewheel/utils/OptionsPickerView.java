package com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils;

/**
 * Created by yaoxuehua on 16-6-17.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.timepicker.BasePickerView;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.timepicker.WheelOptions;

import java.util.ArrayList;
import java.util.Timer;


public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {
    WheelOptions wheelOptions;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private OnOptionsSelectListener optionsSelectListener;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";


    private Handler handler;//用于监听数据变化；
    private Timer timer;//时间线程，用于监听数据变化

    private ArrayList<String> roomsList = new ArrayList<>();//监听变化的数据
    private ArrayList<ArrayList<ArrayList<String>>> toiletsList = new ArrayList<ArrayList<ArrayList<String>>>();
    private ArrayList<ArrayList<String>> hallsList = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> hallsListReplace = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> toiletsListReplace = new ArrayList<ArrayList<ArrayList<String>>>();

    public OptionsPickerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_pickerview_options, contentContainer);

        btnSubmit = findViewById(R.id.btnSubmit);// -----确定和取消按钮
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----转轮
        final View optionspicker = findViewById(R.id.optionspicker);
        wheelOptions = new WheelOptions(optionspicker);

    }

    public void setPicker(ArrayList<T> optionsItems) {
        wheelOptions.setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<ArrayList<T>> options2Items, boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<ArrayList<T>> options2Items,
                          ArrayList<ArrayList<ArrayList<T>>> options3Items,
                          boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, options3Items,
                linkage);
    }

    /**
     * 获取存在的数据，通过该数据获取相应数据
     * */
    public void setList(ArrayList<String> roomList,ArrayList<ArrayList<String>> hallList,ArrayList<ArrayList<ArrayList<String>>> toiletList ){


        this.roomsList = roomList;
        this.toiletsList = toiletList;
        this.hallsList = hallList;
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<>();
        ArrayList<String> options3Items_01_01 = new ArrayList<>();
        //第三列空数据
        for (int i = 0; i < toiletsList.size(); i++) {
            options3Items_01_01.add(" ");
        }
        for (int i = 0; i < hallsList.size(); i++) {
            options3Items_01.add(options3Items_01_01);
        }
        for (int i = 0; i < roomsList.size(); i++) {
            toiletsListReplace.add(options3Items_01);
        }
//        wheelOptions.setPicker(roomsList,hallsList,toiletsListReplace,true);
        //第二列空数据空
        ArrayList<String> options2Items_01 = new ArrayList<>();
        for (int i =0; i < hallsList.size();i++) {
            options2Items_01.add(" ");
        }
        for (int i = 0; i < roomsList.size(); i++) {
            hallsListReplace.add(options2Items_01);
        }

    }


    /**
     * 设置选中的item位置
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        wheelOptions.setCurrentItems(option1, 0, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     */
    public void setSelectOptions(int option1, int option2) {
        wheelOptions.setCurrentItems(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     * @param option3
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        wheelOptions.setCurrentItems(option1, option2, option3);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     */
    public void setLabels(String label1) {
        wheelOptions.setLabels(label1, null, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     */
    public void setLabels(String label1, String label2) {
        wheelOptions.setLabels(label1, label2, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        wheelOptions.setLabels(label1, label2, label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelOptions.setCyclic(cyclic);
    }

    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (optionsSelectListener != null) {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
            }
            dismiss();
            return;
        }
    }

    public interface OnOptionsSelectListener {
        public void onOptionsSelect(int options1, int option2, int options3);
    }

    public void setOnoptionsSelectListener(
            OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
