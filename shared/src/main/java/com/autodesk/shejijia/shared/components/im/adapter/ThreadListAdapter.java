package com.autodesk.shejijia.shared.components.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatCommandInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatMessage;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUser;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.common.uielements.CircleImageView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.Date;


public class ThreadListAdapter extends BaseAdapter
{
    public interface ThreadListAdapterInterface
    {
        int getThreadCount();

        MPChatThread getThreadObjectForIndex(int index);

        Boolean isUserConsumer();

        int getLoggedInUserId();
    }


    public ThreadListAdapter(Context context, ThreadListAdapterInterface threadListInterface, boolean isFileBased)
    {
        mContext = context;
        mThreadListInterface = threadListInterface;
        mIsFileBased = isFileBased;
    }


    public int getCount()
    {
        return mThreadListInterface.getThreadCount();
    }


    public MPChatThread getItem(int position)
    {
        return mThreadListInterface.getThreadObjectForIndex(position);
    }


    public long getItemId(int position)
    {
        return position;
    }


    public int getLayoutId()
    {
        if (mIsFileBased)
            return R.layout.view_thread_list_row_for_file;
        else
            return R.layout.view_thread_list_row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        ViewHolder holder = initHolder(convertView);
        loadData(holder, position);
        return convertView;
    }


    private boolean isUserConsumer()
    {
        return mThreadListInterface.isUserConsumer();
    }


    private ViewHolder initHolder(View container)
    {
        ViewHolder viewHolder = new ViewHolder();
        if (mIsFileBased)
            viewHolder.fileThumbnail = (ImageView) container.findViewById(R.id.head_ico);
        else
            viewHolder.userThumbnail = (CircleImageView) container.findViewById(R.id.head_ico);

        viewHolder.unreadMessageCount = (TextView) container.findViewById(R.id.tv_unread_message_count);
        viewHolder.name = (TextView) container.findViewById(R.id.tv_name);
        viewHolder.description = (TextView) container.findViewById(R.id.tv_content);
        viewHolder.time = (TextView) container.findViewById(R.id.tv_time);
        return viewHolder;
    }


    private void loadData(ViewHolder holder, int position)
    {
        MPChatThread thread = getItem(position);
        MPChatUser sender = MPChatUtility.getComplimentryUserFromThread(thread, "" + mThreadListInterface.getLoggedInUserId());
        if (thread.unread_message_count > 0)
        {
            (holder).unreadMessageCount.setText(String.valueOf(thread.unread_message_count));
            (holder).unreadMessageCount.setVisibility(View.VISIBLE);
        }
        else
        {
            (holder).unreadMessageCount.setVisibility(View.GONE);
        }

        (holder).name.setText(getDisplayName(thread));

        Date date =  DateUtil.acsDateToDate(thread.latest_message.sent_time);

        if (date != null)
            (holder).time.setText(DateUtil.formattedStringFromDateForChatList(mContext,date));
        else
            (holder).time.setText(DateUtil.getTimeMY(thread.latest_message.sent_time));

        MPChatMessage.eMPChatMessageType message_media_type = thread.latest_message.message_media_type;
        switch (message_media_type)
        {
            case eTEXT:
                (holder).description.setText(thread.latest_message.body);
                break;
            case eIMAGE:
                (holder).description.setText(R.string.picture_msg);
                break;
            case eAUDIO:
                (holder).description.setText(R.string.audio_msg);
                break;
            case eCOMMAND:
            {
                if (thread.latest_message.command.equalsIgnoreCase("command"))
                {

                    MPChatCommandInfo info = MPChatMessage.getCommandInfoFromMessage(thread.latest_message);
                    if (isUserConsumer())
                        (holder).description.setText(info.for_consumer);
                    else
                        (holder).description.setText(info.for_designer);
                }
                else if (thread.latest_message.command.equalsIgnoreCase("CHAT_ROLE_ADD"))
                {
                    (holder).description.setText(thread.latest_message.body);
                }
                else if (thread.latest_message.command.equalsIgnoreCase("CHAT_ROLE_REMOVE"))
                {
                    (holder).description.setText(thread.latest_message.body);
                }

            }
            break;
            default:
                break;
        }

        if (mIsFileBased)
        {
            String fileUrl = MPChatUtility.getFileUrlFromThread(thread) + "Medium.jpg";
            ImageUtils.loadImage((holder).fileThumbnail, fileUrl);
        }
        else
        {
            if (MPChatUtility.isAvatarImageIsDefaultForUser(sender.profile_image))
                (holder).userThumbnail.setImageResource(R.drawable.default_useravatar);
            else
                ImageUtils.loadUserAvatar((holder).userThumbnail, sender.profile_image);
        }
    }


    private String getDisplayName(MPChatThread thread)
    {
        MPChatUser sender = MPChatUtility.getComplimentryUserFromThread(thread, "" + mThreadListInterface.getLoggedInUserId());
        String userName = MPChatUtility.getUserDisplayNameFromUser(sender.name);
        String displayName = null;

        assert(userName != null && !userName.isEmpty());

        if (!isUserConsumer())
        {
            userName = trimAndAddEllipsis(userName, kMaxNameLength);

            String assetName = MPChatUtility.getAssetNameFromThread(thread);

            if (assetName != null && !assetName.isEmpty())
            {
                assetName = trimAndAddEllipsis(assetName, kMaxAssetNameLength);
                displayName = userName + "/" + assetName;
            }
            else
                displayName = userName;
        }
        else
            displayName = trimAndAddEllipsis(userName, kMaxTotalNameLength);

        return displayName;
    }


    private String trimAndAddEllipsis(String original, int maxCharacters)
    {
        if (original.length() > maxCharacters)
            return original.substring(0, maxCharacters) + "…";
        else
            return original;
    }

    private class ViewHolder
    {
        private ImageView fileThumbnail;
        private CircleImageView userThumbnail;
        private TextView unreadMessageCount;
        private TextView name;
        private TextView description;
        private TextView time;
    }

    private static final int kMaxNameLength = 8;
    private static final int kMaxAssetNameLength = 6;
    private static final int kMaxTotalNameLength = 12;

    private boolean mIsFileBased;
    private Context mContext;
    private ThreadListAdapterInterface mThreadListInterface;

}

