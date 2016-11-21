package com.autodesk.shejijia.shared.components.form.common.entity.categoryForm;

import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.CategoryFormPresenter;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;

import java.util.HashMap;

/**
 * Created by t_panya on 16/11/13.
 */

public class SHPatrolForm extends SHForm implements CategoryFormPresenter {
    public SHPatrolForm(HashMap map) {
        super(map);
    }

    @Override
    public String formType() {
        if("patrol_project_completion".equals(formTemplateId)){
            return SHFormConstant.SHFormType.TYPE_COMPLETION;
        }
        if("recording_water_proofing_work".equals(formTemplateId)){
            return SHFormConstant.SHFormType.TYPE_WATERPROOF;
        }
        if("patrol_water_proofing_work".equals(formTemplateId)){
            return SHFormConstant.SHFormType.TYPE_RECORD_WATERPROOF;
        }
        if("patrol_concealed_work".equals(formTemplateId)){

        }
        if("patrol_base_completion".equals(formTemplateId)){

        }
        return null;
    }

    @Override
    public String formCategory() {
        return SHFormConstant.SHFormCategory.PATROL;
    }
}
