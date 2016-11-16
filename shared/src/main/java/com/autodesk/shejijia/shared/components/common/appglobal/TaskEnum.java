package com.autodesk.shejijia.shared.components.common.appglobal;

/**
 * Created by wenhulin on 11/16/16.
 */

public class TaskEnum {

    public enum TemplateId {
        KAI_GONG_JIAO_DI("kai_gong_jiao_di"),    //开工交底
        YINBIGONGCHENG_YANSHOU("yinbigongcheng_yanshou"), //隐蔽工程验收
        BI_SHUI_SHI_YAN("bi_shui_shi_yan"),    //闭水实验
        ZHONGQI_YANSHOU("zhongqi_yanshou"),     //中期验收
        JICHU_WANGONG_YANSHOU("jichu_wangong_yanshou"), //基础完工验收
        JUNGONG_YANSHOU("jungong_yanshou"),  //竣工验收

        OTHERS("others");  //其他

        private String value;
        TemplateId(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }

        public static TemplateId getEnum(String value) {
            for (TemplateId templateId: values()) {
                if (value.equalsIgnoreCase(templateId.getValue())) {
                    return templateId;
                }
            }

            return OTHERS;
        }
    }

    public enum Category {
        TIME_LINE("timeline"),    //开工交底
        INSPECTOR_INSPECTION("inspectorInspection"), //监理验收
        CLIENT_MANAGER_INSPECTION("clientmanagerInspection"),    //客户经理验收
        CONSTRUCTION("construction"),     //中期验收
        MATERIAL_MEASURING("materialMeasuring"), //主材测量
        MATERIAL_INSTALLATION("materialInstallation"),  //主材安装
        OTHERS("others");  //其他

        private String value;
        Category(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }

        public static Category getEnum(String value) {
            for (Category category: values()) {
                if (value.equalsIgnoreCase(category.getValue())) {
                    return category;
                }
            }

            return OTHERS;
        }
    }
}
