package com.autodesk.shejijia.shared.components.issue.common.tools;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
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
                if (englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTORCOMPANY)) {//监理公司
                    mapMember.put(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTORCOMPANY, listMember.get(i));
                } else if (englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTOR)) {//监理
                    mapMember.put(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTOR, listMember.get(i));
                } else if (englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MEMBER)) {//消费者
                    mapMember.put(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MEMBER, listMember.get(i));
                } else if (englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_DESIGNER)) {//设计师
                    mapMember.put(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_DESIGNER, listMember.get(i));
                } else if (englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_FOREMAN)) {//班长
                    mapMember.put(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_FOREMAN, listMember.get(i));
                } else if (englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MATERIALSTAFF)) {//材料员
                    mapMember.put(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MATERIALSTAFF, listMember.get(i));
                } else if (englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_CLIENTMANAGER)) {//客户经理
                    mapMember.put(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_CLIENTMANAGER, listMember.get(i));
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
                if (!englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTORCOMPANY)
                        && !englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTOR)
                        && !englishRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MEMBER)) //筛选掉监理和监理公司和消费者
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
        if (engRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTORCOMPANY)) {//监理公司  .
            chinaRole = UIUtils.getString(R.string.issuerole_china_inspectorcompany);
        } else if (engRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_INSPECTOR)) {//监理
            chinaRole = UIUtils.getString(R.string.issuerole_china_inspector);
        } else if (engRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MEMBER)) {//消费者  .
            chinaRole = UIUtils.getString(R.string.issuerole_china_member);
        } else if (engRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_DESIGNER)) {//设计师
            chinaRole = UIUtils.getString(R.string.issuerole_china_designer);
        } else if (engRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_FOREMAN)) {//班长
            chinaRole = UIUtils.getString(R.string.issuerole_china_foreman);
        } else if (engRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MATERIALSTAFF)) {//材料员
            chinaRole = UIUtils.getString(R.string.issuerole_china_materialstaff);
        } else if (engRole.equals(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_CLIENTMANAGER)) {//客户经理
            chinaRole = UIUtils.getString(R.string.issuerole_china_clientmanager);
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
        if (issueType.equals(UIUtils.getString(R.string.issuetype_china_sheji))) {
            chinaRole = UIUtils.getString(R.string.issuerole_china_designer);
        } else if (issueType.equals(UIUtils.getString(R.string.issuetype_china_xuncha)) || issueType.equals(UIUtils.getString(R.string.issuetype_china_houqi))) {
            chinaRole = UIUtils.getString(R.string.issuerole_china_foreman);
        } else if (issueType.equals(UIUtils.getString(R.string.issuetype_china_cailiao))) {
            chinaRole = UIUtils.getString(R.string.issuerole_china_materialstaff);
        } else if (issueType.equals(UIUtils.getString(R.string.issuetype_china_qita))) {
            chinaRole = UIUtils.getString(R.string.issuerole_china_clientmanager);
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
        if (issueType.equals(UIUtils.getString(R.string.issuetype_china_sheji))) {
            member = mapMember.get(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_DESIGNER);
        } else if (issueType.equals(UIUtils.getString(R.string.issuetype_china_xuncha)) || issueType.equals(UIUtils.getString(R.string.issuetype_china_houqi))) {
            member = mapMember.get(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_FOREMAN);
        } else if (issueType.equals(UIUtils.getString(R.string.issuetype_china_cailiao))) {
            member = mapMember.get(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_MATERIALSTAFF);
        } else if (issueType.equals(UIUtils.getString(R.string.issuetype_china_qita))) {
            member = mapMember.get(ConstructionConstants.IssueTracking.ISSUE_ROLE_ENGLISH_CLIENTMANAGER);
        }

        return member;
    }

}
