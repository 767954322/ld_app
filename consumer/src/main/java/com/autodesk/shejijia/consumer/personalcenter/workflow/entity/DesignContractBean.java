package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file DesignContractBean.java  .
 * @brief .
 */
public class DesignContractBean implements Serializable {

    private String name;
    private String mobile;
    private String zip;
    private String email;
    private String addr;
    private String addrDe;
    private String design_sketch;
    private String render_map;
    private String design_sketch_plus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDe() {
        return addrDe;
    }

    public void setAddrDe(String addrDe) {
        this.addrDe = addrDe;
    }

    public String getDesign_sketch() {
        return design_sketch;
    }

    public void setDesign_sketch(String design_sketch) {
        this.design_sketch = design_sketch;
    }

    public String getRender_map() {
        return render_map;
    }

    public void setRender_map(String render_map) {
        this.render_map = render_map;
    }

    public String getDesign_sketch_plus() {
        return design_sketch_plus;
    }

    public void setDesign_sketch_plus(String design_sketch_plus) {
        this.design_sketch_plus = design_sketch_plus;
    }
}
