package com.autodesk.shejijia.shared.components.form.common.entity.categoryForm;

import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/11/14.
 */

public class SHFormCategory {
    private String name;
    private List<CheckItem> formItems;
    public SHFormCategory(String name){
        this.name = name;
        formItems = new ArrayList<>();
    }

    public int getCheckedItemNum(){
        int count = 0;
        for(CheckItem item : formItems){
            if(item.isChecked()){
                count ++;
            }
        }
        return count;
    }

    public int getTotalItemNum(){
        return formItems.size();
    }
}
