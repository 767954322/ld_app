package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import com.qy.appframe.model.IModel;

/**
 * @User: 蜡笔小新
 * @date: 16-10-25
 * @GitHub: https://github.com/meikoz
 */

public class RecommendDetailsEntity implements IModel {

    /**
     * province : 370000
     * city : 370300
     * district : 0
     * scfd : [{"brands":[{"malls":[{"mall_name":"????","mall_number":"DS2","$$hashKey":"03G"},{"mall_name":"???","mall_number":"DS25","$$hashKey":"03H"}],"brand_name":"????","code":"00001004","logo_url":"http://image1.juran.cn/img/543ba8ad498e905d7cfd2516.img","$$hashKey":"02J","remarks":"????","amountAndUnit":"10","dimension":"??","apartment":"3"},{"malls":[{"mall_name":"???","mall_number":"DS25","$$hashKey":"042"},{"mall_name":"???","mall_number":"DS74","$$hashKey":"043"},{"mall_name":"???","mall_number":"DS76","$$hashKey":"044"},{"mall_name":"????","mall_number":"DS1","$$hashKey":"045"}],"brand_name":"????","code":"00001010","logo_url":"http://image1.juran.cn/img/52552f6b498e46b0f16db74e.img","$$hashKey":"02P","remarks":"","amountAndUnit":"10","dimension":"??","apartment":"6"}],"category_3d_id":"1df3e3a8-15b0-4647-bd69-e64350cab281","category_3d_name":"??","sub_category_3d_id":"3c79b519-10c2-4f3c-9b78-7378c832316c","sub_category_3d_name":"???","source":"1","$$hashKey":"04T"},{"brands":[{"malls":[{"mall_name":"???","mall_number":"DS25","$$hashKey":"064"},{"mall_name":"????","mall_number":"DS1","$$hashKey":"065"}],"brand_name":"????","code":"00001006","logo_url":"http://image1.juran.cn/img/5268a3cf498e46b0f16f33ae.img","$$hashKey":"051","remarks":"","amountAndUnit":"10","dimension":"??","apartment":"6"},{"malls":[{"mall_name":"???","mall_number":"DS25","$$hashKey":"06I"},{"mall_name":"???","mall_number":"DS74","$$hashKey":"06J"},{"mall_name":"???","mall_number":"DS76","$$hashKey":"06K"},{"mall_name":"????","mall_number":"DS1","$$hashKey":"06L"}],"brand_name":"????","code":"00001010","logo_url":"http://image1.juran.cn/img/52552f6b498e46b0f16db74e.img","$$hashKey":"055","remarks":"","amountAndUnit":"10","dimension":"??","apartment":""}],"category_3d_id":"1df3e3a8-15b0-4647-bd69-e64350cab281","category_3d_name":"??","sub_category_3d_id":"ccb93c54-00cf-4db4-b858-f3aabe44f3e6","sub_category_3d_name":"?/?","source":"1","$$hashKey":"079"}]
     * scfc :
     * status : unsent
     * type : null
     * source : 0
     * main_project_id : 1622572
     * design_project_id : 0
     * consumer_id : 20739039
     * consumer_zid : 0
     * consumer_uid : 20739039
     * designer_id : 20730531
     * designer_uid : 7db9447f-a024-4689-867b-1909ee16c04d
     * consumer_name : zfm
     * consumer_mobile : 15210092994
     * province_name : ???
     * city_name : ???
     * district_name : ???
     * community_name : ??????
     * effective_time : 0
     * expiration_time : 0
     * modified_count : 2
     * community_address : ???10
     */

    private String province;
    private String city;
    private String district;
    private String scfd;
    private String scfc;
    private String status;
    private Object type;
    private String source;
    private int main_project_id;
    private int design_project_id;
    private int consumer_id;
    private int consumer_zid;
    private String consumer_uid;
    private String designer_id;
    private String designer_uid;
    private String consumer_name;
    private String consumer_mobile;
    private String province_name;
    private String city_name;
    private String district_name;
    private String community_name;
    private String project_code;
    private int effective_time;
    private int expiration_time;
    private int modified_count;
    private String community_address;
    private String date_submitted;

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getDate_submitted() {
        return date_submitted;
    }

    public void setDate_submitted(String date_submitted) {
        this.date_submitted = date_submitted;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getScfd() {
        return scfd;
    }

    public void setScfd(String scfd) {
        this.scfd = scfd;
    }

    public String getScfc() {
        return scfc;
    }

    public void setScfc(String scfc) {
        this.scfc = scfc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getMain_project_id() {
        return main_project_id;
    }

    public void setMain_project_id(int main_project_id) {
        this.main_project_id = main_project_id;
    }

    public int getDesign_project_id() {
        return design_project_id;
    }

    public void setDesign_project_id(int design_project_id) {
        this.design_project_id = design_project_id;
    }

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public int getConsumer_zid() {
        return consumer_zid;
    }

    public void setConsumer_zid(int consumer_zid) {
        this.consumer_zid = consumer_zid;
    }

    public String getConsumer_uid() {
        return consumer_uid;
    }

    public void setConsumer_uid(String consumer_uid) {
        this.consumer_uid = consumer_uid;
    }

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }

    public String getDesigner_uid() {
        return designer_uid;
    }

    public void setDesigner_uid(String designer_uid) {
        this.designer_uid = designer_uid;
    }

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public String getConsumer_mobile() {
        return consumer_mobile;
    }

    public void setConsumer_mobile(String consumer_mobile) {
        this.consumer_mobile = consumer_mobile;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public int getEffective_time() {
        return effective_time;
    }

    public void setEffective_time(int effective_time) {
        this.effective_time = effective_time;
    }

    public int getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(int expiration_time) {
        this.expiration_time = expiration_time;
    }

    public int getModified_count() {
        return modified_count;
    }

    public void setModified_count(int modified_count) {
        this.modified_count = modified_count;
    }

    public String getCommunity_address() {
        return community_address;
    }

    public void setCommunity_address(String community_address) {
        this.community_address = community_address;
    }
}
