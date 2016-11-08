package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Building;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Profile;
import com.autodesk.shejijia.shared.components.form.contract.ProjectInfoContract;

import java.util.Iterator;
import java.util.List;

/**
 * Created by t_aij on 16/10/21.
 */

public class ProjectInfoPresenter implements ProjectInfoContract.Presenter {
    private ProjectInfoContract.View mView;

    public ProjectInfoPresenter(ProjectInfoContract.View view) {
        mView = view;
        mView.setToolbar();
    }

    @Override
    public void setCustomer(Project projectBean) {
        List<Member> members = projectBean.getMembers();
        Iterator<Member> iterator = members.iterator();
        while(iterator.hasNext()) {
            Member membersBean = iterator.next();
            if("member".equals(membersBean.getRole())) {
                Profile profile = membersBean.getProfile();
                mView.setUsername(profile.getName());
                mView.setTelephone(profile.getMobile());
                break;
            }
        }
        Building building = projectBean.getBuilding();
        String provinceName = building.getProvinceName();
        String cityName = building.getCityName();
        if(cityName.contains(provinceName)) {
            mView.setAddress(cityName + building.getDistrictName() + building.getDistrict() + "号");
        } else {
            mView.setAddress(provinceName + cityName + building.getDistrictName());
        }
        mView.setCommunity(building.getCommunityName());
    }

    @Override
    public void confirm() {
        // TODO: 16/10/28 根据项目信息的状态,选择进入不同的表格
        mView.selectConfirm();
    }

    @Override
    public void cancel() {
        mView.selectCancel();
    }

}
