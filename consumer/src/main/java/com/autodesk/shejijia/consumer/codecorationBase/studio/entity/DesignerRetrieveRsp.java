package com.autodesk.shejijia.consumer.codecorationBase.studio.entity;

import java.io.Serializable;
import java.util.List;


public class DesignerRetrieveRsp implements Serializable {


    /**
     * @Fields designer 设计师扩展信息
     */
    private Designer designer;

    /**
     * @Fields realName 设计师实名信息
     */
    private RealName real_name;

    /**
     * @Fields memberId 用户在Hitachi的标识
     */
    private String member_id;

    /**
     * @Fields hsUid 用户在homestyler的标识
     */
    private String hs_uid;

    /**
     * @Fields firstName 姓
     */
    private String first_name;

    /**
     * @Fields lastName 名
     */
    private String last_name;

    /**
     * @Fields email 用户邮箱
     */
    private String email;

    /**
     * @Fields avatar 用户的头像
     */
    private String avatar;

    /**
     * @Fields gender 用户的性别 0:保密 1:女： 2：男
     */
    private String gender;

    /**
     * @Fields homePhone 用户的住宅电话
     */
    private String home_phone;

    /**
     * @Fields zipCode 用户的邮编
     */
    private String zip_code;

    /**
     * @Fields hitachiAccount 会员账户名
     */
    private String hitachi_account;

    /**
     * @Fields userName 用户的名称
     */
    private String user_name;

    /**
     * @Fields birthday 用户的生日
     */
    private String birthday;

    /**
     * @Fields registerDate 用户的注册时间
     */
    private String register_date;

    /**
     * @Fields mobileNumber 用户的手机
     */
    private String mobile_number;

    /**
     * @Fields province 省(code)
     */
    private String province;

    /**
     * @Fields city 市(code)
     */
    private String city;

    /**
     * @Fields district 区(code)
     */
    private String district;

    /**
     * @Fields cityName 市(市名称)
     */
    private String city_name;

    /**
     * @Fields provinceName 省(省名称)
     */
    private String province_name;

    /**
     * @Fields districtName 区(区名称)
     */
    private String district_name;

    /**
     * @Fields address 详细地址
     */
    private String address;

    /**
     * @Fields isOrderSms 是否订阅短信服务:0 未订阅短信服务， 1是已订短信服务
     */
    private Integer is_order_sms;

    /**
     * @Fields nickName 昵称
     */
    private String nick_name;

    /**
     * @Fields isValidatedByMobile 是否通过手机校验:0 未通过手机验证，1是通过手机验证
     */
    private String is_validated_by_mobile;

    /**
     * @Fields isEmailBinding 是否绑定邮箱:0未绑定邮箱，1是绑定邮箱
     */
    private String is_email_binding;

    /**
     * @Fields hasSecreted 是否有密保信息:0 没有密保，1 有密保
     */
    private String has_secreted;


    /**
     * @Fields 是否关注了此设计师
     */
    private boolean is_following;

    /**
     * @Fields 设计师的关注数
     */
    private String follows;
    /**
     * @Fields 认证状态
     */
    private String audit_status;
    /**
     * @Fields 置顶
     */
    private String region;

    private String seq;
    private String name_en;
    private String occupation_en;
    private String summary;
    private String tag;
    private String tag_names;
    private String designer_profile_cover;
    private String designer_detail_cover;
    private List<MainDesigners> main_designer;
    private String signature;

    public void setMain_designer(List<MainDesigners> main_designer) {
        this.main_designer = main_designer;
    }

    public String getDesignerProfileCover() {
        return designer_profile_cover;
    }

    public void setDesignerProfileCover(String designerProfileCover) {
        this.designer_profile_cover = designerProfileCover;
    }

    public String getDesignerDetailCover() {
        return designer_detail_cover;
    }

    public void setDesignerDetailCover(String designerDetailCover) {
        this.designer_detail_cover = designerDetailCover;
    }


    public String getNameEn() {
        return name_en;
    }

    public void setNameEn(String nameEn) {
        this.name_en = nameEn;
    }

    public String getOccupationEN() {
        return occupation_en;
    }

    public void setOccupationEN(String occupationEN) {
        this.occupation_en = occupationEN;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    /**
     * 获取设计师扩展信息<br>
     *
     * @return TODO返回结果描述 @return Designer 返回值类型 @throws
     */
    public Designer getDesigner() {
        return designer;
    }

    /**
     * 设置一个信息的设计师扩展信息对象<br>
     *
     * @param designer TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    /**
     * 获取实名信息对象<br>
     *
     * @return TODO返回结果描述 @return RealName 返回值类型 @throws
     */
    public RealName getRealName() {
        return real_name;
    }

    /**
     * 设置一个新的实名对象<br>
     *
     * @param realName TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setRealName(RealName realName) {
        this.real_name = realName;
    }

    /**
     * 获取hitachi的用户id<br>
     *
     * @return TODO返回结果描述 @return Long 返回值类型 @throws
     */
    public String getMemberId() {
        return member_id;
    }

    /**
     * 设置一个新的hitachi的用户id<br>
     *
     * @param memberId TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setMemberId(String memberId) {
        this.member_id = memberId;
    }

    /**
     * 获取用户在homestyler的uid<br>
     *
     * @return TODO返回结果描述 @return String 返回值类型 @throws
     */
    public String getHsUid() {
        return hs_uid;
    }

    /**
     * 设置一个新的uid<br>
     *
     * @param hsUid TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setHsUid(String hsUid) {
        this.hs_uid = hsUid;
    }

    /**
     * 获取姓<br>
     *
     * @return TODO返回结果描述 @return String 返回值类型 @throws
     */
    public String getFirstName() {
        return first_name;
    }

    /**
     * 设置一个新的姓<br>
     *
     * @param firstName TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    /**
     * 获取名<br>
     *
     * @return TODO返回结果描述 @return String 返回值类型 @throws
     */
    public String getLastName() {
        return last_name;
    }

    /**
     * 设置一个新的名<br>
     *
     * @param lastName TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    /**
     * 获取email<br>
     *
     * @return TODO返回结果描述 @return String 返回值类型 @throws
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置一个新的email<br>
     *
     * @param email TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取头像<br>
     *
     * @return TODO返回结果描述 @return String 返回值类型 @throws
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置一个新的头像<br>
     *
     * @param avatar TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取性别<br>
     *
     * @return TODO返回结果描述 @return Integer 返回值类型 @throws
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置一个新的性别<br>
     *
     * @param gender TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取住宅电话<br>
     *
     * @return TODO返回结果描述 @return String 返回值类型 @throws
     */
    public String getHomePhone() {
        return home_phone;
    }

    /**
     * 设置一个新的住宅电话<br>
     *
     * @param homePhone TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setHomePhone(String homePhone) {
        this.home_phone = homePhone;
    }


    public RealName getReal_name() {
        return real_name;
    }

    public void setReal_name(RealName real_name) {
        this.real_name = real_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getHs_uid() {
        return hs_uid;
    }

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getHitachi_account() {
        return hitachi_account;
    }

    public void setHitachi_account(String hitachi_account) {
        this.hitachi_account = hitachi_account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
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

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIs_order_sms() {
        return is_order_sms;
    }

    public void setIs_order_sms(Integer is_order_sms) {
        this.is_order_sms = is_order_sms;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getIs_validated_by_mobile() {
        return is_validated_by_mobile;
    }

    public void setIs_validated_by_mobile(String is_validated_by_mobile) {
        this.is_validated_by_mobile = is_validated_by_mobile;
    }

    public String getIs_email_binding() {
        return is_email_binding;
    }

    public void setIs_email_binding(String is_email_binding) {
        this.is_email_binding = is_email_binding;
    }

    public String getHas_secreted() {
        return has_secreted;
    }

    public void setHas_secreted(String has_secreted) {
        this.has_secreted = has_secreted;
    }


    public Boolean getIs_following() {
        return is_following;
    }

    public void setIs_following(Boolean is_following) {
        this.is_following = is_following;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getOccupation_en() {
        return occupation_en;
    }

    public void setOccupation_en(String occupation_en) {
        this.occupation_en = occupation_en;
    }

    public String getTag_names() {
        return tag_names;
    }

    public void setTag_names(String tag_names) {
        this.tag_names = tag_names;
    }

    public String getDesigner_profile_cover() {
        return designer_profile_cover;
    }

    public void setDesigner_profile_cover(String designer_profile_cover) {
        this.designer_profile_cover = designer_profile_cover;
    }

    public String getDesigner_detail_cover() {
        return designer_detail_cover;
    }

    public void setDesigner_detail_cover(String designer_detail_cover) {
        this.designer_detail_cover = designer_detail_cover;
    }


    public String getFollowingCount() {
        return follows;
    }

    public void setFollowingCount(String follows) {
        this.follows = follows;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public DesignerRetrieveRsp() {
        super();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<MainDesigners> getMain_designer() {
        return main_designer;
    }

    @Override
    public String toString() {
        return "DesignerRetrieveRsp{" +
                "designer=" + designer +
                ", real_name=" + real_name +
                ", member_id='" + member_id + '\'' +
                ", hs_uid='" + hs_uid + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", home_phone='" + home_phone + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", hitachi_account='" + hitachi_account + '\'' +
                ", user_name='" + user_name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", register_date='" + register_date + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", city_name='" + city_name + '\'' +
                ", province_name='" + province_name + '\'' +
                ", district_name='" + district_name + '\'' +
                ", address='" + address + '\'' +
                ", is_order_sms=" + is_order_sms +
                ", nick_name='" + nick_name + '\'' +
                ", is_validated_by_mobile='" + is_validated_by_mobile + '\'' +
                ", is_email_binding='" + is_email_binding + '\'' +
                ", has_secreted='" + has_secreted + '\'' +
                ", is_following=" + is_following +
                ", follows='" + follows + '\'' +
                ", audit_status='" + audit_status + '\'' +
                ", region='" + region + '\'' +
                ", seq='" + seq + '\'' +
                ", name_en='" + name_en + '\'' +
                ", occupation_en='" + occupation_en + '\'' +
                ", summary='" + summary + '\'' +
                ", tag='" + tag + '\'' +
                ", tag_names='" + tag_names + '\'' +
                ", designer_profile_cover='" + designer_profile_cover + '\'' +
                ", designer_detail_cover='" + designer_detail_cover + '\'' +
                ", main_designer=" + main_designer +
                ", signature='" + signature + '\'' +
                '}';
    }
}