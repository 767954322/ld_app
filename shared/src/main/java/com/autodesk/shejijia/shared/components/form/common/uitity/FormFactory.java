package com.autodesk.shejijia.shared.components.form.common.uitity;

import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHMaterialForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPatrolForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;

import java.util.HashMap;

/**
 * Created by t_panya on 16/11/15.
 */
@SuppressWarnings("unchecked")
public class FormFactory {

    public static <T extends SHForm> T createCategoryForm(HashMap formMap){
        String category = (String) formMap.get("category");
        if(SHFormConstant.SHFormCategory.INSPECTION.equals(category)){
            return  (T)new SHInspectionForm(formMap);
        }else if (SHFormConstant.SHFormCategory.MATERIAL.equals(category)){
            return (T)new SHMaterialForm(formMap);
        }else if(SHFormConstant.SHFormCategory.PATROL.equals(category)){
            return (T)new SHPatrolForm(formMap);
        }else if(SHFormConstant.SHFormCategory.PRECHECK.equals(category)){
            return (T)new SHPrecheckForm(formMap);
        }else{
            return (T)new SHForm(formMap);
        }
    }
}
