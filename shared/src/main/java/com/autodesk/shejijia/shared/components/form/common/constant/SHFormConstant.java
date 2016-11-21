package com.autodesk.shejijia.shared.components.form.common.constant;

/**
 * Created by t_panya on 16/11/15.
 */

public class SHFormConstant {

    public static class SHFormCategory{
        public static final String INSPECTION = "inspection";
        public static final String MATERIAL = "material";
        public static final String PRECHECK = "precheck";
        public static final String PATROL = "patrol";
    }

    public static class SHFormType{
        public static final String TYPE_PRECHECK = "type_precheck";         //预检表
        public static final String TYPE_INSPECTION = "type_inspection";     //监理验收
        public static final String TYPE_PATROL = "type_patrol";             //经理巡检
        public static final String TYPE_COMPLETION = "type_completion";     //经理巡检竣工表
        public static final String TYPE_MATERIAL = "type_material";         //班长主材表
        public static final String TYPE_HOLLOWNESS = "type_hollowness";     //经理空鼓记录表
        public static final String TYPE_REINSPECT = "type_reinspect";       //监理整改表
        public static final String TYPE_REVIEW = "type_review";             //经理监督整改表
        public static final String TYPE_WATERPROOF = "type_waterproof";
        public static final String TYPE_RECORD_WATERPROOF = "type_record_waterproof";     //经理防水记录表
    }
}
