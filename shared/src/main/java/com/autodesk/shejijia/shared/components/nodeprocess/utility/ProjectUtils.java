package com.autodesk.shejijia.shared.components.nodeprocess.utility;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;

import java.util.List;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/12/7
 */

public class ProjectUtils {
    public static Member getMemberByRole(ProjectInfo projectInfo, @NonNull String role) {
        if (role.equalsIgnoreCase(ConstructionConstants.MemberType.INSPECTOR)) {
            role = ConstructionConstants.MemberType.INSPECTOR_COMPANY;
        }

        List<Member> members = projectInfo.getMembers();
        for (Member member: members) {
            if (role.equalsIgnoreCase(member.getRole())) {
                return member;
            }
        }

        return null;
    }
}
