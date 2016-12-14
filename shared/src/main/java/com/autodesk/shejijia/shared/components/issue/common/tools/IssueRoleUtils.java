package com.autodesk.shejijia.shared.components.issue.common.tools;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Menghao.Gu on 16-12-14.
 */
public class IssueRoleUtils {

    private static IssueRoleUtils issueRoleUtils = new IssueRoleUtils();

    public static IssueRoleUtils getInstance() {
        return issueRoleUtils;
    }

    /**
     * 从项目信息获取人员信息　返回Map(英文名称，用户信息)
     *
     * @param projectInfo
     * @return
     */
    public Map<String, Member> getMembersFromProject(ProjectInfo projectInfo) {
        HashMap<String, Member> mapMember = null;
        if (projectInfo != null && projectInfo.getMembers() != null && projectInfo.getMembers().size() > 0) {
            mapMember = new HashMap<>();
            ArrayList<Member> listMember = projectInfo.getMembers();
            for (int i = 0; i < listMember.size(); i++) {
                String englishRole = listMember.get(i).getRole();
                if (englishRole.equals("inspectorcompany")) {//监理公司
                    mapMember.put("inspectorcompany", listMember.get(i));
                } else if (englishRole.equals("member")) {//消费者
                    mapMember.put("member", listMember.get(i));
                } else if (englishRole.equals("designer")) {//设计师
                    mapMember.put("designer", listMember.get(i));
                } else if (englishRole.equals("foreman")) {//班长
                    mapMember.put("foreman", listMember.get(i));
                } else if (englishRole.equals("materialstaff")) {//材料员
                    mapMember.put("materialstaff", listMember.get(i));
                } else if (englishRole.equals("clientmanager")) {//客户经理
                    mapMember.put("clientmanager", listMember.get(i));
                }
            }
        }
        return mapMember;
    }

    /**
     * 通过项目信息获取除了监理公司和监理和消费者的跟进人数据
     *
     * @param projectInfo
     * @return
     */

    public ArrayList<Member> getFollowListByProject(ProjectInfo projectInfo) {

        ArrayList<Member> listFollowMember = null;
        if (projectInfo != null && projectInfo.getMembers() != null && projectInfo.getMembers().size() > 0) {
            listFollowMember = new ArrayList<>();
            ArrayList<Member> listMember = projectInfo.getMembers();
            for (int i = 0; i < listMember.size(); i++) {
                String englishRole = listMember.get(i).getRole();
                if (!englishRole.equals("inspectorcompany") && !englishRole.equals("inspector") && !englishRole.equals("member")) //筛选掉监理和监理公司
                    listFollowMember.add(listMember.get(i));
            }
        }

        return listFollowMember;
    }


    /**
     * 通过职位名称获取中文职位
     *
     * @param engRole
     * @return
     */
    public String getChiRoleByEngRole(String engRole) {
        String chinaRole;
        if (engRole.equals("inspectorcompany")) {//监理公司  .
            chinaRole = "监理公司";
        } else if (engRole.equals("inspector")) {//监理
            chinaRole = "监理";
        } else if (engRole.equals("member")) {//消费者  .
            chinaRole = "消费者";
        } else if (engRole.equals("designer")) {//设计师
            chinaRole = "设计师";
        } else if (engRole.equals("foreman")) {//班长
            chinaRole = "班长";
        } else if (engRole.equals("materialstaff")) {//材料员
            chinaRole = "材料员";
        } else if (engRole.equals("clientmanager")) {//客户经理
            chinaRole = "客户经理";
        } else {
            chinaRole = "";
        }
        return chinaRole;
    }

    /**
     * 通过问题类型获取中文职位
     *
     * @param issueType
     * @return
     */
    public String getChiRoleByIssueType(String issueType) {
        String chinaRole = "";
        if (issueType.equals("设计问题")) {
            chinaRole = "设计师";
        } else if (issueType.equals("巡查问题") || issueType.equals("后期安装")) {
            chinaRole = "班长";
        } else if (issueType.equals("材料问题")) {
            chinaRole = "材料员";
        } else if (issueType.equals("其他问题")) {
            chinaRole = "客户经理";
        }
        return chinaRole;
    }

    /**
     * 通过问题类型获取需要的跟进人员
     *
     * @param mapMember
     * @param issueType
     * @return
     */
    public Member getMemberByIssueType(Map<String, Member> mapMember, String issueType) {
        Member member = null;
        if (issueType.equals("设计问题")) {
            member = mapMember.get("designer");
        } else if (issueType.equals("巡查问题") || issueType.equals("后期安装")) {
            member = mapMember.get("foreman");
        } else if (issueType.equals("材料问题")) {
            member = mapMember.get("materialstaff");
        } else if (issueType.equals("其他问题")) {
            member = mapMember.get("clientmanager");
        }

        return member;
    }

}
