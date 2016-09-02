package com.autodesk.shejijia.consumer.personalcenter.consumer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author he.liu  .
 * @version 1.0 .
 * @date 16-6-7 上午11:22
 * @file DecorationBeiShuFragment.java  .
 * @brief 消费者：我的装修项目中的北舒套餐展示.
 */
public class DecorationBeiShuFragment extends Fragment {

    public DecorationBeiShuFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResId(), container, false);
        initView();
        initData();
        return rootView;
    }

    private int getLayoutResId() {
        return R.layout.pager_consumer_decoration_beishu;
    }

    private void initView() {
        mBeiShuTag = (TextView) rootView.findViewById(R.id.tv_consumer_decoration_beishu_tag);
        mProjectId = (TextView) rootView.findViewById(R.id.tv_consumer_decoration_beishu_projectid);
        mName = (TextView) rootView.findViewById(R.id.tv_consumer_decoration_beishu_name);
        mPhone = (TextView) rootView.findViewById(R.id.tv_consumer_decoration_beishu_phone);
        mProjectAddress = (TextView) rootView.findViewById(R.id.tv_consumer_decoration_beishu_address);
        mHomeAddress = (TextView) rootView.findViewById(R.id.tv_consumer_decoration_beishu_homeaddress);
        mListView = (ListView) rootView.findViewById(R.id.lv_decoration_bid_beishu);
    }

    private void initData() {
        String contacts_name = need.getContacts_name();
        String contacts_mobile = need.getContacts_mobile();
        String community_name = need.getCommunity_name();
        String needs_id = need.getNeeds_id();

        province_name = need.getProvince_name();
        city_name = need.getCity_name();
        district_name = TextUtils.isEmpty(need.getDistrict_name()) ? "" : need.getDistrict_name();

        String address = UIUtils.getNoDataIfEmpty(province_name) + " " + UIUtils.getNoDataIfEmpty(city_name) + " " + UIUtils.getNoDataIfEmpty(district_name);
        mProjectAddress.setText(address);

        mBeiShuTag.setText(community_name);
        mProjectId.setText(needs_id);           /// 项目编号 .
        mName.setText(contacts_name);      /// 姓名 .
        mPhone.setText(contacts_mobile);    /// 联系电话 .
        mHomeAddress.setText(community_name);/// 小区名称 .

        mBeiShuAdapter = new BeiShuAdapter(getActivity(), bidders, R.layout.item_lv_decoration_beishu);
        mListView.setAdapter(mBeiShuAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;

        need = (DecorationNeedsListBean) getArguments().getSerializable(Constant.DecorationBundleKey.DECORATION_BEISHU_NEEDS_KEY);
        if (need == null) {
            return;
        }
        beishu_thread_id = need.getBeishu_thread_id();
        bidders = need.getBidders();
    }

    /**
     * 得到当前北舒套餐，方便DecorationActivity调用
     *
     * @param need
     * @return
     */

    public static final Fragment getInstance(DecorationNeedsListBean need) {
        Fragment fragment = new DecorationBeiShuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DecorationBundleKey.DECORATION_BEISHU_NEEDS_KEY, need);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 创建北舒套餐后，设计师的适配器
     */

    private class BeiShuAdapter extends CommonAdapter<DecorationBiddersBean> {
        private String user_name;
        private String avatarUrl;

        public BeiShuAdapter(Context context, List<DecorationBiddersBean> biddersEntities, int layoutId) {
            super(context, biddersEntities, layoutId);
        }


        public void convert(final CommonViewHolder holder, final DecorationBiddersBean biddersEntity) {
            avatarUrl = biddersEntity.getAvatar();
            user_name = biddersEntity.getUser_name();
            PolygonImageView piv_photo = holder.getView(R.id.ib_personal_b_photo_beishu);

            ImageUtils.displayAvatarImage(avatarUrl, piv_photo);

            piv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SeekDesignerDetailActivity.class);
                    intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, biddersEntity.getDesigner_id());
                    intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, biddersEntity.getUid());
                    startActivity(intent);
                }
            });

            user_name = TextUtils.isEmpty(user_name) ? "" : user_name;
            holder.setText(R.id.tv_designer_name_beishu, user_name);
            holder.getView(R.id.img_decoration_beishu_chat).setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                    if (memberEntity != null) {
                        member_id = memberEntity.getAcs_member_id();
                        final String designer_id = biddersEntity.getDesigner_id();
                        final String hs_uid = biddersEntity.getUid();
                        final String mMemberType = memberEntity.getMember_type();
                        final String receiver_name = biddersEntity.getUser_name();
                        final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();
                        final String beishu_thread_id = need.getBeishu_thread_id();


                        MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                            }

                            @Override
                            public void onResponse(String s) {
                                MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                                final Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                                intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                                intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                                intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                                intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);

                                if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {

                                    MPChatThread mpChatThread = mpChatThreads.threads.get(0);
                                    int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                                    intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                                    intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
                                    intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                    getActivity().startActivity(intent);

                                } else {
                                    MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(member_id, designer_id, new OkStringRequest.OKResponseCallback() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                        }

                                        @Override
                                        public void onResponse(String s) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                String thread_id = jsonObject.getString("thread_id");
                                                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                                intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                                intent.putExtra(ChatRoomActivity.THREAD_ID, thread_id);
                                                getActivity().startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    } else {
                        AdskApplication.getInstance().doLogin(getActivity());
                    }

                }
            });
        }
    }

    /**
     * this context activity
     */
    protected Activity activity;
    /**
     * root view
     */
    protected View rootView;

    private TextView mBeiShuTag, mProjectId, mName, mPhone, mProjectAddress, mHomeAddress;
    private ListView mListView;
    private BeiShuAdapter mBeiShuAdapter;

    private String province_name, city_name, district_name;
    private String beishu_thread_id;
    public String member_id;

    private List<DecorationBiddersBean> bidders;
    private DecorationNeedsListBean need;
}

