package com.autodesk.shejijia.shared.components.form.common.uitity;

import com.autodesk.shejijia.shared.components.form.common.constant.FormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.SHMaterialForm;
import com.autodesk.shejijia.shared.components.form.common.entity.SHPatrolForm;
import com.autodesk.shejijia.shared.components.form.common.entity.SHPrecheckForm;

import java.util.HashMap;

/**
 * Created by t_panya on 16/11/15.
 */

public class FormFactory {

    public static <T extends SHForm> T createCategoryForm(HashMap formMap){
        String cateGory = (String) formMap.get("category");
        if(FormConstant.SHFormCategory.INSPECTION.equals(cateGory)){
            return  (T)new SHInspectionForm(formMap);
        }else if (FormConstant.SHFormCategory.MATERIAL.equals(cateGory)){
            return (T)new SHMaterialForm(formMap);
        }else if(FormConstant.SHFormCategory.PATROL.equals(cateGory)){
            return (T)new SHPatrolForm(formMap);
        }else if(FormConstant.SHFormCategory.PRECHECK.equals(cateGory)){
            return (T)new SHPrecheckForm(formMap);
        }else{
            return (T)new SHForm(formMap);
        }
    }
}
