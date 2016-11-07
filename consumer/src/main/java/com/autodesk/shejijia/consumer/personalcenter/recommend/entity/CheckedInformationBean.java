package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-11-3 .
 * @file CheckedInformationBean.java .
 * @brief 添加主材页面与清单页面交互BEAN.
 */

public class CheckedInformationBean implements Serializable{

    private List<RecommendBrandsBean> checkedBrandsInformationBean; // 选中的品牌信息Bean
    private MaterialCategoryBean.Categories3dBean categories3dBean;//一级品类信息Bean
    private MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean;//二级品类信息bean
    private List<BtnStatusBean> list;//选中的标记集合；
    private RecommendBrandsBean recommendBrandsBean;
    private List<RecommendBrandsBean> havedBrandsInformationBean;//已经有的展示的品牌集合


    public List<RecommendBrandsBean> getCheckedBrandsInformationBean() {
        return checkedBrandsInformationBean;
    }

    public void setCheckedBrandsInformationBean(List<RecommendBrandsBean> checkedBrandsInformationBean) {
        this.checkedBrandsInformationBean = checkedBrandsInformationBean;
    }

    public MaterialCategoryBean.Categories3dBean getCategories3dBean() {
        return categories3dBean;
    }

    public void setCategories3dBean(MaterialCategoryBean.Categories3dBean categories3dBean) {
        this.categories3dBean = categories3dBean;
    }

    public MaterialCategoryBean.Categories3dBean.SubCategoryBean getSubCategoryBean() {
        return subCategoryBean;
    }

    public void setSubCategoryBean(MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean) {
        this.subCategoryBean = subCategoryBean;
    }

    public List<BtnStatusBean> getList() {
        return list;
    }

    public void setList(List<BtnStatusBean> list) {
        this.list = list;
    }

    public RecommendBrandsBean getRecommendBrandsBean() {
        return recommendBrandsBean;
    }

    public void setRecommendBrandsBean(RecommendBrandsBean recommendBrandsBean) {
        this.recommendBrandsBean = recommendBrandsBean;
    }

    public List<RecommendBrandsBean> getHavedBrandsInformationBean() {
        return havedBrandsInformationBean;
    }

    public void setHavedBrandsInformationBean(List<RecommendBrandsBean> havedBrandsInformationBean) {
        this.havedBrandsInformationBean = havedBrandsInformationBean;
    }
}
