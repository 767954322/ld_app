package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * @author Malidong .
 * @version v1.0 .
 * @date 2016-6-23 .
 * @file MPContractNoBean.java .
 * @brief 合同编号实体类 .
 */
public class MPContractNoBean implements Serializable {

    private String contractNO;

    public void setContractNO(String contractNO) {
        this.contractNO = contractNO;
    }

    public String getContractNO() {
        return contractNO;
    }
}
