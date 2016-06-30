package com.autodesk.shejijia.shared.components.im.datamodel;

import java.util.Arrays;
import java.util.List;


public class MPChatUtility
{
    public static final int MAX_DISPLAY_UNREAD_COUNT = 99;

    public static String getFormattedBadgeString(int count)
    {
        if (count > MAX_DISPLAY_UNREAD_COUNT)
            return MAX_DISPLAY_UNREAD_COUNT + "+";
        else
            return String.valueOf(count);
    }

    public static MPChatUser getComplimentryUserFromThread(MPChatThread thread, String loggedInUserId)
    {
        MPChatUser sysUser = isThereSystemThreadName(thread);

        if (!thread.sender.user_id.equalsIgnoreCase(loggedInUserId) && !thread.sender.user_id.equalsIgnoreCase(ADMIN_USER_ID))
        {

            return thread.sender;
        }

        for (MPChatUser user : thread.recipients.users)
        {
            if (!user.user_id.equalsIgnoreCase(loggedInUserId) && !user.user_id.equalsIgnoreCase(ADMIN_USER_ID))
                return user;
        }

        if (sysUser != null) return sysUser;

        return thread.sender;
    }

    public static MPChatUser isThereSystemThreadName(MPChatThread thread)
    {
        if (thread.sender.user_id.equalsIgnoreCase(ADMIN_USER_ID))
            return thread.sender;

        for (MPChatUser user : thread.recipients.users)
        {
            if (user.user_id.equalsIgnoreCase(ADMIN_USER_ID))
                return user;
        }

        return null;
    }


    public static String getFileUrlFromThread(MPChatThread thread)
    {
        String imageURL = null;
        if (thread.entity.entityInfos.size() > 0)
        {
            MPChatEntityInfo info = thread.entity.entityInfos.get(0);
            if (info.entity_data.public_file_url != null)
                imageURL = info.entity_data.public_file_url;
        }

        return imageURL;
    }


    public static String getAssetNameFromThread(MPChatThread thread)
    {
        if (thread.entity.entityInfos.size() > 0)
        {
            MPChatEntityInfo info = thread.entity.entityInfos.get(0);
            if (info.entity_data.asset_name != null)
                return info.entity_data.asset_name;
        }

        return null;
    }


    public static String getWorkflowStepNameFromThread(MPChatThread thread)
    {
        if (thread.entity.entityInfos.size() > 0)
        {
            MPChatEntityInfo info = thread.entity.entityInfos.get(0);
            if (info.entity_data.workflow_step_name != null)
                return info.entity_data.workflow_step_name;
        }

        return null;
    }


    public static int getAssetIdFromThread(MPChatThread thread)
    {
        if (thread.entity.entityInfos.size() > 0)
        {
            MPChatEntityInfo info = thread.entity.entityInfos.get(0);
            if (info.entity_data.asset_id > 0)
                return info.entity_data.asset_id;
        }

        return 0;
    }


    public static String getFileEntityIdForThread(MPChatThread thread)
    {
        if (thread.entity.entityInfos.size() > 0)
        {
            for (MPChatEntityInfo info : thread.entity.entityInfos)
            {
                if (info.entity_type != null && info.entity_type.equalsIgnoreCase("FILE"))
                    return info.entity_id;
            }
        }

        return null;
    }


    public static String getWorkflowIdForThread(MPChatThread thread)
    {
        if (thread.entity.entityInfos.size() > 0)
        {
            for (MPChatEntityInfo info : thread.entity.entityInfos)
            {
                if (info.entity_type != null && info.entity_type.equalsIgnoreCase("WORKFLOW_STEP"))
                    return info.entity_id;
            }
        }

        return null;
    }


    public static String getUserDisplayNameFromUser(String userName)
    {
        String name = null;
        List<String> items = Arrays.asList(userName.split("\\s*_\\s*"));
        if (items.size() > 1)
        {
            String nameId = items.get(items.size() - 1);
            name = userName.replace("_" + nameId, "");
        }
        else if (items.size() > 0)
            name = items.get(0);

        return name;
    }


    public static boolean isSystemThread(String thread_id)
    {
//        MPChatUser *user = [AppController AppGlobal_GetMemberInfoObj];
        return false;
    }


    public static boolean isAvatarImageIsDefaultForUser(String userProfileImage)
    {
        String fileName = userProfileImage.substring(userProfileImage.lastIndexOf("/") + 1);/// new File(userProfileImage).getName();
        return (fileName.equalsIgnoreCase(Default_UserImage));
    }


    public static String getSystemThreadUserName(String thread_id, String defaultName)
    {
        return defaultName;
    }

    public static String getUserHs_Uid(String name)
    {
        List<String> items = Arrays.asList(name.split("\\s*_\\s*"));

        if (items.size() > 1)
            return items.get(items.size() - 1);

        return "";
    }

    ///this is production Admin User Id.
    //////private static final String ADMIN_USER_ID = "20742718";
    private static final String ADMIN_USER_ID = "20730165";
    private static final String Default_UserImage = "default_avatar.png";
}
