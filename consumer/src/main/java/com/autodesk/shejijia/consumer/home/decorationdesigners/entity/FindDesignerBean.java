package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-22 .
 * @file FindDesignerBean.java .
 * @brief 搜索，查找设计师实体类 .
 */
public class FindDesignerBean implements Serializable {

//    nick_name 昵称
//    style_names  风格
//    style　  风格对应的英文
//    start_experience 工作年限开始时间
//    end_experience   工作年限结束时间
//    design_price_code 设计师设计费区间code

    private String nick_name;
    private String style_names;
    private String start_experience;
    private String end_experience;
    private String design_price_code;
    private String style;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        if (TextUtils.isEmpty(style)) {
            this.style = "";
        } else {
            this.style = style;
        }
    }

    public void setNick_name(String nick_name) {
        if (!TextUtils.isEmpty(nick_name)) {
            try {
                this.nick_name = URLEncoder.encode(nick_name, Constant.NetBundleKey.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            this.nick_name = nick_name;
        }
    }

    public void setStyle_names(String style_names) {
        if (!TextUtils.isEmpty(style_names)) {
            try {
                this.style_names = URLEncoder.encode(style_names, Constant.NetBundleKey.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            this.style_names = "";
        }
    }

    public void setEnd_experience(String end_experience) {
        if (TextUtils.isEmpty(end_experience)) {
            this.end_experience = "";
        } else {
            this.end_experience = end_experience;
        }
    }

    public void setStart_experience(String start_experience) {
        if (TextUtils.isEmpty(start_experience)) {
            this.start_experience = "";
        } else {
            this.start_experience = start_experience;
        }
    }

    public void setDesign_price_code(String design_price_code) {
        if (TextUtils.isEmpty(design_price_code)) {
            this.design_price_code = "-1";
        } else {
            this.design_price_code = design_price_code;
        }
    }

    public String getStart_experience() {

        return start_experience;
    }

    public String getStyle_names() {
        return style_names;
    }

    public String getEnd_experience() {
        return end_experience;
    }

    public String getDesign_price_code() {
        return design_price_code;
    }

    public String getNick_name() {
        return nick_name;
    }

    @Override
    public String toString() {
        return "FindDesignerBean{" +
                "nick_name='" + nick_name + '\'' +
                ", style_names='" + style_names + '\'' +
                ", start_experience='" + start_experience + '\'' +
                ", end_experience='" + end_experience + '\'' +
                ", design_price_code='" + design_price_code + '\'' +
                '}';
    }
}
