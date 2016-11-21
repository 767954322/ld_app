package com.autodesk.shejijia.shared.components.form.common.entity.categoryForm;

import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.CategoryFormPresenter;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;

import java.util.HashMap;

/**
 * Created by t_panya on 16/11/13.
 */

public class SHMaterialForm extends SHForm implements CategoryFormPresenter{
    public SHMaterialForm(HashMap map) {
        super(map);
    }

    @Override
    public String formType() {
        return SHFormConstant.SHFormType.TYPE_MATERIAL;
    }

    @Override
    public String formCategory() {
        return SHFormConstant.SHFormCategory.MATERIAL;
    }
}
