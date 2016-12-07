package com.autodesk.shejijia.shared.components.message.activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.message.adapter.ProjectMessageCenterAdapter;
import com.autodesk.shejijia.shared.components.message.datamodel.CallBackMessageCenterDataSource;
import com.autodesk.shejijia.shared.components.message.datamodel.MessageCenterDataSource;
import com.autodesk.shejijia.shared.components.message.datamodel.MessageCenterRemoteDataSource;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/5.
 */
public class ProjectMessageCenterActivity extends BaseActivity implements CallBackMessageCenterDataSource {

    private MessageCenterDataSource mMessageCenterDataSource;
    private List<MessageInfo.DataBean> mData;
    private String mProjectId;
    private RecyclerView mRvProjectMessagCenterView;
    private ProjectMessageCenterAdapter mProjectMessageCenterAdapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_message_center;
    }
    @Override
    protected void initView() {
        mRvProjectMessagCenterView = (RecyclerView)findViewById(R.id.rv_project_message_center_view);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
//        mProjectId = getIntent().getStringExtra("project_id");
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        mData = new ArrayList<>();
        String result = ss();
        MessageInfo messageInfo = GsonUtil.jsonToBean(result, MessageInfo.class);
        mData.addAll(messageInfo.getData());
        mMessageCenterDataSource = new MessageCenterRemoteDataSource(this);
        mProjectMessageCenterAdapter = new ProjectMessageCenterAdapter(mData,R.layout.item_messagecenter);
        mRvProjectMessagCenterView.setAdapter(mProjectMessageCenterAdapter);
        mProjectMessageCenterAdapter.notifyDataSetChanged();
        getListMessageCenterInfo();
    }
    private void getListMessageCenterInfo(){
        mMessageCenterDataSource.listMessageCenterInfo(getRequestBundle(),TAG);
    }
    private Bundle getRequestBundle(){
        Bundle requestParams = new Bundle();
        requestParams.putString("project_id","1641539");//"1642677"
        requestParams.putInt("offset",0);
        requestParams.putBoolean("unread",false);
        requestParams.putInt("limit",20);
        return requestParams;
    }
    @Override
    protected void initListener() {
        super.initListener();
    }
    @Override
    public void onErrorResponse(ResponseError responseError) {

    }
    @Override
    public void onResponse(JSONObject jsonObject) {
        String result = jsonObject.toString();
        MessageInfo messageInfo = GsonUtil.jsonToBean(result, MessageInfo.class);
        messageInfo.getData();
}
    private String ss(){
        return "{\n" +
                "  \"total\": 11,\n" +
                "  \"limit\": 20,\n" +
                "  \"offset\": 0,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *中期验收* 预约时间 为*2017年8月27日*。\",\n" +
                "        \"detail_items\": null\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": \"1641539-31170-659611\",\n" +
                "        \"event_category\": \"inspection\",\n" +
                "        \"event\": \"reserve\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": {\n" +
                "          \"title\": \"中期验收\",\n" +
                "          \"description\": \"中期验收\",\n" +
                "          \"task_name\": \"中期验收\",\n" +
                "          \"files\": null\n" +
                "        }\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 09:10:23\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *中期验收* 预约时间 为*2017年8月27日*。\",\n" +
                "        \"detail_items\": null\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": \"1641539-31170-659611\",\n" +
                "        \"event_category\": \"inspection\",\n" +
                "        \"event\": \"reserve\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": {\n" +
                "          \"title\": \"中期验收\",\n" +
                "          \"description\": \"中期验收\",\n" +
                "          \"task_name\": \"中期验收\",\n" +
                "          \"files\": null\n" +
                "        }\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 09:10:08\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *2*个 施工计划。\",\n" +
                "        \"detail_items\": [\n" +
                "          \"[计划]浴室柜/淋浴屏初次测量更新为 2017年8月27日 ~ 2017年8月31日\",\n" +
                "          \"[计划]浴室柜/淋浴屏复尺，吊顶测量更新为 2017年8月31日\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": null,\n" +
                "        \"event_category\": \"timeline\",\n" +
                "        \"event\": \"update\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": null\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 09:09:39\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *闭水试验* 预约时间 为*2017年7月11日*。\",\n" +
                "        \"detail_items\": null\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": \"1641539-31170-659607\",\n" +
                "        \"event_category\": \"inspection\",\n" +
                "        \"event\": \"reserve\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": {\n" +
                "          \"title\": \"闭水试验\",\n" +
                "          \"description\": \"闭水试验\",\n" +
                "          \"task_name\": \"闭水试验\",\n" +
                "          \"files\": null\n" +
                "        }\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 09:07:45\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *竣工验收* 预约时间 为*2017年8月31日*。\",\n" +
                "        \"detail_items\": null\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": \"1641539-31170-659630\",\n" +
                "        \"event_category\": \"inspection\",\n" +
                "        \"event\": \"reserve\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": {\n" +
                "          \"title\": \"竣工验收\",\n" +
                "          \"description\": \"竣工验收\",\n" +
                "          \"task_name\": \"竣工验收\",\n" +
                "          \"files\": null\n" +
                "        }\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 09:04:30\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *竣工验收* 预约时间 为*2017年8月23日*。\",\n" +
                "        \"detail_items\": null\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": \"1641539-31170-659630\",\n" +
                "        \"event_category\": \"inspection\",\n" +
                "        \"event\": \"reserve\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": {\n" +
                "          \"title\": \"竣工验收\",\n" +
                "          \"description\": \"竣工验收\",\n" +
                "          \"task_name\": \"竣工验收\",\n" +
                "          \"files\": null\n" +
                "        }\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 08:32:28\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *2*个 施工计划。\",\n" +
                "        \"detail_items\": [\n" +
                "          \"[计划]浴室柜/淋浴屏初次测量更新为 2017年8月9日 ~ 2017年12月13日\",\n" +
                "          \"[计划]浴室柜/淋浴屏复尺，吊顶测量更新为 2017年8月22日 ~ 2017年12月13日\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": null,\n" +
                "        \"event_category\": \"timeline\",\n" +
                "        \"event\": \"update\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": null\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 08:31:44\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *2*个 施工计划。\",\n" +
                "        \"detail_items\": [\n" +
                "          \"[计划]浴室柜/淋浴屏复尺，吊顶测量更新为 2017年8月22日 ~ 2017年8月31日\",\n" +
                "          \"[计划]竣工验收更新为 2017年12月13日\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": null,\n" +
                "        \"event_category\": \"timeline\",\n" +
                "        \"event\": \"update\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": null\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 08:28:32\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 更新了 *2*个 施工计划。\",\n" +
                "        \"detail_items\": [\n" +
                "          \"[计划]洁具、浴室柜、淋浴屏安装更新为 2017年8月23日 ~ 2017年8月31日\",\n" +
                "          \"[计划]竣工验收更新为 2017年8月31日\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": null,\n" +
                "        \"event_category\": \"timeline\",\n" +
                "        \"event\": \"update\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": null\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 08:26:31\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"*客户经理曹牧* 创建了 施工计划。\",\n" +
                "        \"detail_items\": null\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": null,\n" +
                "        \"event_category\": \"timeline\",\n" +
                "        \"event\": \"create\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": null\n" +
                "      },\n" +
                "      \"sender_role\": \"clientmanager\",\n" +
                "      \"sender_avatar\": \"http://image.juranzx.com.cn:8082/img/5836b25de4b07d88ca9095a5.img\",\n" +
                "      \"sent_time\": \"December 02, 2016 08:25:44\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"display_message\": {\n" +
                "        \"summary\": \"您的施工项目 *何12栋02号04室老房大项目* 将于 *2016年12月2日* 开工。\",\n" +
                "        \"detail_items\": null\n" +
                "      },\n" +
                "      \"custom_data\": {\n" +
                "        \"project_id\": \"1641539\",\n" +
                "        \"task_id\": null,\n" +
                "        \"event_category\": \"timeline\",\n" +
                "        \"event\": \"prepare\",\n" +
                "        \"in_consumer_feeds\": false,\n" +
                "        \"entity_id\": null,\n" +
                "        \"extend_data\": null\n" +
                "      },\n" +
                "      \"sender_role\": \"admin\",\n" +
                "      \"sender_avatar\": \"\",\n" +
                "      \"sent_time\": \"December 02, 2016 08:17:22\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
