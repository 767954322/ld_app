package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * <p>Description:全流程中设计合同实体类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: http://www.leediancn.com</p>
 *
 * @author he.liu .
 * @date 2016-07-17.
 */
public class MPDesignContractBean implements Serializable {

    /**
     * contract_charge : 0.05
     * contract_create_date : 1468661943298
     * contract_data : {@jr@mobile@jr@:@jr@13111111111@jr@,@jr@addrDe@jr@:@jr@quantum@jr@,@jr@render_map@jr@:@jr@1@jr@,@jr@addr@jr@:@jr@北京北京市东城区@jr@,@jr@zip@jr@:@jr@344001@jr@,@jr@email@jr@:@jr@ihg@gg.com@jr@,@jr@design_sketch@jr@:@jr@2@jr@,@jr@design_sketch_plus@jr@:@jr@1@jr@,@jr@name@jr@:@jr@wi @jr@}
     * contract_first_charge : .04
     * contract_no : LWEC-16071605
     * contract_status : 0
     * contract_template_url : www.baidu.com
     * contract_type : 0
     * contract_update_date : 1468661943298
     */

    public String contract_charge;
    public String contract_create_date;
    public String contract_data;
    public String contract_first_charge;
    public String contract_no;
    public int contract_status;
    public String contract_template_url;
    public int contract_type;
    public String contract_update_date;

    public String getContract_charge() {
        return contract_charge;
    }

    public void setContract_charge(String contract_charge) {
        this.contract_charge = contract_charge;
    }

    public String getContract_create_date() {
        return contract_create_date;
    }

    public void setContract_create_date(String contract_create_date) {
        this.contract_create_date = contract_create_date;
    }

    public String getContract_data() {
        return contract_data;
    }

    public void setContract_data(String contract_data) {
        this.contract_data = contract_data;
    }

    public String getContract_first_charge() {
        return contract_first_charge;
    }

    public void setContract_first_charge(String contract_first_charge) {
        this.contract_first_charge = contract_first_charge;
    }

    public String getContract_no() {
        return contract_no;
    }

    public void setContract_no(String contract_no) {
        this.contract_no = contract_no;
    }

    public int getContract_status() {
        return contract_status;
    }

    public void setContract_status(int contract_status) {
        this.contract_status = contract_status;
    }

    public String getContract_template_url() {
        return contract_template_url;
    }

    public void setContract_template_url(String contract_template_url) {
        this.contract_template_url = contract_template_url;
    }

    public int getContract_type() {
        return contract_type;
    }

    public void setContract_type(int contract_type) {
        this.contract_type = contract_type;
    }

    public String getContract_update_date() {
        return contract_update_date;
    }

    public void setContract_update_date(String contract_update_date) {
        this.contract_update_date = contract_update_date;
    }
}
