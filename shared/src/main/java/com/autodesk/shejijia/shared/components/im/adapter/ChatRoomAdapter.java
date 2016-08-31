package com.autodesk.shejijia.shared.components.im.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.SingleClickUtils;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatCommandInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatMessage;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ChatRoomAdapter extends BaseAdapter {

    public enum eDownloadState {
        eInProgress,
        eFailure,
        eSuccess,
        eNotStarted
    }

    public interface MessagesListInterface {
        int getMessageCount();

        MPChatMessage getMessageForIndex(int index);

        Boolean ifLoggedInUserIsConsumer();

        Boolean ifMessageSenderIsLoggedInUser(int index);

        String getLoggedinMemberId();

        void onMessageCellClicked(int index);

        int getAudioMessageDuration(int index);

        boolean isAudioMessagePlayingForIndex(int index);

        eDownloadState isAudioFileDownloading(int index);

        boolean isDateChangeForIndex(int index);

        View getMessageCellOfListView(int index);
    }


    public ChatRoomAdapter(Context context, MessagesListInterface listInterface) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListInterface = listInterface;
    }


    public void updateMessageCell(int index) {
        View v = mListInterface.getMessageCellOfListView(index);

        if (v == null)
            return;

        MPChatMessage msg = getItem(index);

        switch (msg.message_media_type) {
            case eTEXT:
                setupTextMessage(msg, v);
                break;
            case eIMAGE:
                setupImageMessage(msg, index, v);
                break;
            case eAUDIO:
                setupAudioMessage(msg, index, v);
                break;
            case eCOMMAND:
                break;
            case eNONE:
                break;
        }
    }


    public int getCount() {
        return mListInterface.getMessageCount();
    }


    public MPChatMessage getItem(int position) {
        return mListInterface.getMessageForIndex(position);
    }


    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MPChatMessage message = getItem(position);
        convertView = createView(message.message_media_type, position);

        initializeHolderForView(convertView, message.message_media_type);
        ViewHolder holder = (ViewHolder) convertView.getTag();

        loadAvatarImage(holder, position);

        switch (message.message_media_type) {
            case eIMAGE:
                setupImageMessage(message, position, convertView);
                break;
            case eTEXT:
                setupTextMessage(message, convertView);
                break;
            case eAUDIO:
                setupAudioMessage(message, position, convertView);
                break;
            case eCOMMAND:
                setupCommandMessage(message, position, convertView);
                break;
            default:
        }

        setDateAndTime(message, holder, position);

        return convertView;
    }


    private void initializeHolderForView(View convertView, MPChatMessage.eMPChatMessageType msgType) {
        ViewHolder holder = new ViewHolder();
        convertView.setTag(holder);
        switch (msgType) {
            case eIMAGE: {
                try {
                    holder.unreadMessageCount = (TextView) convertView.findViewById(R.id.unread_file_count);
                    holder.messageImageView = ((ImageView) convertView.findViewById(R.id.message_image_view));
                    holder.userImageView = (ImageView) convertView.findViewById(R.id.user_avatar);
                    holder.timeTextView = (TextView) convertView.findViewById(R.id.send_time);
                    holder.dateTextView = (TextView) convertView.findViewById(R.id.date_text);
                    holder.progressBar = (ProgressBar) convertView.findViewById(R.id.imagecell_progress_bar);
                } catch (Exception e) {
                }
            }
            break;
            case eTEXT: {
                try {
                    holder.userImageView = (ImageView) convertView.findViewById(R.id.user_avatar);
                    holder.timeTextView = (TextView) convertView.findViewById(R.id.send_time);
                    holder.textView = (TextView) convertView.findViewById(R.id.chat_message);
                    holder.dateTextView = (TextView) convertView.findViewById(R.id.date_text);
                } catch (Exception e) {
                }
            }
            break;
            case eAUDIO: {
                try {
                    holder.relativeLayout = ((RelativeLayout) convertView.findViewById(R.id.audio_cell_parent_layout));
                    holder.userImageView = (ImageView) convertView.findViewById(R.id.user_avatar);
                    holder.timeTextView = (TextView) convertView.findViewById(R.id.send_time);
                    holder.voiceImage = (ImageView) convertView.findViewById(R.id.audio);
                    holder.voiceDurationTextView = (TextView) convertView.findViewById(R.id.voice_durationtext);
                    holder.dateTextView = (TextView) convertView.findViewById(R.id.date_text);
                    holder.progressBar = (ProgressBar) convertView.findViewById(R.id.audio_cell_progressbar);
                } catch (Exception e) {
                }
            }
            break;
            case eCOMMAND: {
                try {
                    holder.userImageView = (ImageView) convertView.findViewById(R.id.user_avatar);
                    holder.commandTextView = (TextView) convertView.findViewById(R.id.command_text);
                    holder.timeTextView = (TextView) convertView.findViewById(R.id.send_time);
                    holder.commandButton = (Button) convertView.findViewById(R.id.command_button);
                    holder.dateTextView = (TextView) convertView.findViewById(R.id.date_text);
                } catch (Exception e) {
                }
            }
            break;
            default:
                break;
        }

    }


    private View createView(MPChatMessage.eMPChatMessageType mediaType, int position) {
        View view = null;
        switch (mediaType) {
            case eIMAGE:
                view = mListInterface.ifMessageSenderIsLoggedInUser(position) ? mInflater.inflate(R.layout.chat_image_right_row, null) : mInflater.inflate(R.layout.chat_image_left_row, null);
                break;
            case eTEXT:
                view = mListInterface.ifMessageSenderIsLoggedInUser(position) ? mInflater.inflate(R.layout.chat_text_right_row, null) : mInflater.inflate(R.layout.chat_text_left_row, null);
                break;
            case eAUDIO:
                view = mListInterface.ifMessageSenderIsLoggedInUser(position) ? mInflater.inflate(R.layout.chat_voice_right_row, null) : mInflater.inflate(R.layout.chat_voice_left_row, null);
                break;
            case eCOMMAND:
                view = mInflater.inflate(R.layout.chat_command_row, null);
                break;
        }
        return view;
    }


    private void startAudioAnimation(final View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.audio);
        imageView.setImageDrawable(null);
        ((AnimationDrawable) imageView.getBackground()).start();
    }


    private void stopAudioAnimation(final View view, final int position) {
        ImageView imageView = (ImageView) view.findViewById(R.id.audio);
        ((AnimationDrawable) imageView.getBackground()).stop();
        imageView.setImageResource(R.drawable.audio);

        if (mListInterface.ifMessageSenderIsLoggedInUser(position))
            imageView.setImageResource(R.drawable.audio);
        else
            imageView.setImageResource(R.drawable.audior);
    }


    private void showDownloadProgress(final View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (holder != null)
            holder.progressBar.setVisibility(View.VISIBLE);
    }


    private void hideDownloadProgress(final View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (holder != null)
            holder.progressBar.setVisibility(View.GONE);
    }


    private void showAudioDuration(final View view, int duration) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (holder != null) {
            holder.voiceDurationTextView.setText(String.valueOf(duration) + "\"");
            holder.voiceDurationTextView.setVisibility(View.VISIBLE);
        }
    }

    private void hideAudioDuration(final View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (holder != null)
            holder.voiceDurationTextView.setVisibility(View.GONE);
    }


    private void setupCommandMessage(MPChatMessage message, final int position, View v) {
        final ViewHolder holder = (ViewHolder) v.getTag();
        try {
            MPChatCommandInfo info = MPChatMessage.getCommandInfoFromMessage(message);

            if (mListInterface.ifLoggedInUserIsConsumer())
                (holder).commandTextView.setText(info.for_consumer);
            else
                (holder).commandTextView.setText(info.for_designer);

            int subNodeId = Integer.parseInt(info.sub_node_id);
            switch (subNodeId) {
                case 13:
                    (holder).commandButton.setText(mContext.getString(R.string.Pay));
                    break;

                case 11:
                case 12:
                case 14:
                    (holder).commandButton.setText(mContext.getString(R.string.Measure));
                    break;

                case 21:
                case 22:
                case 31:
                    (holder).commandButton.setText(mContext.getString(R.string.Contract));
                    break;

                case 33:
                    (holder).commandButton.setText(mContext.getString(R.string.Deliver));
                    break;

                case 41:
                case 42:
                    (holder).commandButton.setText(mContext.getString(R.string.FinalPay));
                    break;

                case 51:
                case 52: {
                    if (mListInterface.ifLoggedInUserIsConsumer())
                        (holder).commandButton.setText(mContext.getString(R.string.DownloadDeliver));
                    else
                        (holder).commandButton.setText(mContext.getString(R.string.UploadDeliver));

                    break;
                }
                case 61:
                case 62:
                case 64:
                    if (mListInterface.ifLoggedInUserIsConsumer())
                        (holder).commandButton.setText(mContext.getString(R.string.DownloadDeliver));
                    else
                        (holder).commandButton.setText(mContext.getString(R.string.UploadDeliver));

                    break;

                case 63:
                case 71:
                case 72: {
                        (holder).commandButton.setText(mContext.getString(R.string.lookDeliver));

                    break;
                }

                default:
                    break;
            }


            (holder).commandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SingleClickUtils.isFastDoubleClick()) {
                        Toast.makeText(mContext, R.string.no_repeat_click, Toast.LENGTH_SHORT).show();
                    } else {
                        ChatRoomAdapter.this.mListInterface.onMessageCellClicked(position);
                    }
                }
            });
        } catch (Exception e) {
        }
    }


    private void loadAvatarImage(ViewHolder holder, int position) {
        MPChatMessage message = getItem(position);
        if (message.message_media_type == MPChatMessage.eMPChatMessageType.eCOMMAND)
            holder.userImageView.setImageResource(R.drawable.icon_admin);
        else {
            if (MPChatUtility.isAvatarImageIsDefaultForUser(message.sender_profile_image))
                (holder).userImageView.setImageResource(R.drawable.default_useravatar);
            else
                ImageUtils.loadUserAvatar((holder).userImageView, message.sender_profile_image);
        }
    }


    private void setupTextMessage(MPChatMessage message, View v) {
        ViewHolder holder = (ViewHolder) v.getTag();
        holder.textView.setText(message.body);
    }


    private void setupAudioMessage(final MPChatMessage message, final int position, View convertView) {
        if (mListInterface.isAudioMessagePlayingForIndex(position))
            startAudioAnimation(convertView);
        else
            stopAudioAnimation(convertView, position);

        if (mListInterface.isAudioFileDownloading(position) == eDownloadState.eInProgress)
            showDownloadProgress(convertView);
        else
            hideDownloadProgress(convertView);

        int audioDuration = mListInterface.getAudioMessageDuration(position);

        if (audioDuration < 0)
            hideAudioDuration(convertView);
        else
            showAudioDuration(convertView, audioDuration);

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SingleClickUtils.isFastDoubleClick()) {
                    Toast.makeText(mContext, R.string.no_repeat_click, Toast.LENGTH_SHORT).show();
                } else {
                    ChatRoomAdapter.this.mListInterface.onMessageCellClicked(position);
                }

            }
        });
    }


    private void getUnreadFileCount(final ViewHolder holder, final int position) {
        MPChatMessage message = null;
        if (null != mListInterface) {
            message = mListInterface.getMessageForIndex(position);
        }

        if (message == null)
            return;

        if (null != message && null != mListInterface) {
            MPChatHttpManager.getInstance().retrieveFileUnreadMessageCount(mListInterface.getLoggedinMemberId(), String.valueOf(message.media_file_id), new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    MPNetworkUtils.logError("ChatRoomAdapter", volleyError);
                }

                @Override
                public void onResponse(String s) {
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(s);
                        int count = obj.optInt("unread_message_count");
                        if (count > 0) {
                            holder.unreadMessageCount.setText(String.valueOf(count));
                            holder.unreadMessageCount.setVisibility(View.VISIBLE);
                        } else
                            holder.unreadMessageCount.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private void setupImageMessage(final MPChatMessage message, final int position, View v) {
        final ViewHolder holder = (ViewHolder) v.getTag();
        try {
            String imageUrl;
            imageUrl = message.body + "Medium.jpg";

            ImageUtils.loadFileImageListenr(imageUrl, holder.messageImageView, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.timeTextView.setVisibility(View.VISIBLE);
                    holder.messageImageView.setImageBitmap(new ImageProcessingUtil().createFramedPhoto(mContext, loadedImage, !(mListInterface.ifMessageSenderIsLoggedInUser(position))));

                    getUnreadFileCount(holder, position);
                }
            }, null);

            holder.messageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SingleClickUtils.isFastDoubleClick()) {
                        Toast.makeText(mContext, R.string.no_repeat_click, Toast.LENGTH_SHORT).show();
                    } else {
                        ChatRoomAdapter.this.mListInterface.onMessageCellClicked(position);
                    }

                }
            });
        } catch (Exception e) {
        }
    }


    private void setDateAndTime(final MPChatMessage message, final ViewHolder holder, final int position) {
        Date sendDate = DateUtil.acsDateToDate(message.sent_time);
        String timeStr = message.sent_time + "";

        if (sendDate != null)
            timeStr = DateUtil.formattedTimeFromDateForMessageCell(sendDate);

        holder.timeTextView.setText(timeStr);

        String dateStr = DateUtil.formattedStringFromDateForChatRoom(mContext, sendDate);
        holder.dateTextView.setText(dateStr);
        holder.dateTextView.setVisibility(View.GONE);

        if (mListInterface.isDateChangeForIndex(position))
            holder.dateTextView.setVisibility(View.VISIBLE);
    }


    private class ViewHolder {
        ImageView messageImageView;
        TextView textView;
        ProgressBar progressBar;
        TextView unreadMessageCount;
        ImageView userImageView;

        RelativeLayout relativeLayout;
        ImageView voiceImage;
        TextView timeTextView;
        TextView dateTextView;
        TextView voiceDurationTextView;
        TextView commandTextView;
        Button commandButton;
    }

    private Context mContext;
    private LayoutInflater mInflater;
    private MessagesListInterface mListInterface;
}