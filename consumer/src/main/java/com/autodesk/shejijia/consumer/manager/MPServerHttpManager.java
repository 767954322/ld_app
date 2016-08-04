package com.autodesk.shejijia.consumer.manager;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file MPServerHttpManager.java .
 * @brief 请求网络的管理类.
 */
public class MPServerHttpManager {

    private RequestQueue queue = AdskApplication.getInstance().queue;
    private static MPServerHttpManager mpServerHttpManager = new MPServerHttpManager();

    private static String xToken;
    private static String member_id;
    private static String memType;
    private static String acs_token;

    public MPServerHttpManager() {
//        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//        if (null != memberEntity) {
//            xToken = memberEntity.getHs_accesstoken();
//            member_id = memberEntity.getAcs_member_id();
//            memType = memberEntity.getMember_type();
//            acs_token = memberEntity.getAcs_token();
//        }
    }

    public static MPServerHttpManager getInstance() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            xToken = memberEntity.getHs_accesstoken();
            member_id = memberEntity.getAcs_member_id();
            memType = memberEntity.getMember_type();
            acs_token = memberEntity.getAcs_token();
        }
//        if (null == mpServerHttpManager) {
//            mpServerHttpManager = new MPServerHttpManager();
//        }
        return mpServerHttpManager;
    }

    /**
     * 我的家装订单
     *
     * @param offset
     * @param limit
     * @param callback
     */
    public void getMyDecorationData(final int offset, final int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_MY_DECORATION_LIST + member_id + "/needs?" +
                "media_type_id=" + 53 +
                "&offset=" + offset +
                "&sort_order=desc" +
                "&sort_by=date" +
                "&limit=" + limit +
                "&version=4.15";

        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 首页案例列表
     *
     * @param custom_string_style
     * @param custom_string_type
     * @param custom_string_keywords
     * @param custom_string_area
     * @param custom_string_bedroom
     * @param taxonomy_id
     * @param offset
     * @param limit
     * @param custom_string_restroom
     * @param custom_string_form
     * @param callback
     */
    public void getCaseListData(String custom_string_style, String custom_string_type,
                                String custom_string_keywords, String custom_string_area, String custom_string_bedroom, String taxonomy_id,
                                String custom_string_restroom, String custom_string_form,
                                final int offset, final int limit,
                                OkJsonRequest.OKResponseCallback callback) {

//        String url = UrlConstants.URL_GET_CASE_LIST_SEARCH +
//                "custom_string_style=" + custom_string_style +
//                "&custom_string_type=" + custom_string_type +
//                "&custom_string_keywords=" + custom_string_keywords +
//                "&sort_by=date" +
//                "&custom_string_area=" + custom_string_area +
//                "&custom_string_bedroom=" + custom_string_bedroom +
//                "&taxonomy_id=" + taxonomy_id +
//                "&offset=" + offset +
//                "&limit=" + limit +
//                "&custom_string_restroom=" + custom_string_restroom +
//                "&sort_order=desc" +
//                "&custom_string_form=" + custom_string_form;

        String url = "http://192.168.120.90:8080/design-app/v1/api/cases/search?" +
                "custom_string_style=&custom_string_type=" +
                "&custom_string_keywords=" +
                "&sort_by=date" +
                "&custom_string_area=" +
                "&custom_string_bedroom=" +
                "&taxonomy_id=01&offset=0" +
                "&limit=10&custom_string_restroom=&sort_order=desc&custom_string_form=";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取应标大厅应标信息
     *
     * @param offset
     * @param custom_string_area
     * @param custom_string_form
     * @param custom_string_type
     * @param custom_string_bedroom
     * @param limit
     * @param custom_string_style
     * @param asset_taxonomy
     * @param custom_string_restroom
     * @param callback
     */
    public void getShouldHallData(
            int offset,
            String custom_string_area,
            String custom_string_form,
            String custom_string_type,
            String custom_string_bedroom,
            int limit,
            String custom_string_style,
            String asset_taxonomy,
            String custom_string_restroom,
            OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.URL_GET_SHOULD_HALL_LIST + "?" +
                "offset=" + offset +
                "&custom_string_area=" + custom_string_area +
                "&custom_string_form=" + custom_string_form +
                "&custom_string_type=" + custom_string_type +
                "&custom_string_bedroom=" + custom_string_bedroom +
                "&sort_by=date" +
                "&limit=" + limit +
                "&custom_string_style=" + custom_string_style +
                "&asset_taxonomy=" + asset_taxonomy +
                "&sort_order=desc" +
                "&custom_string_restroom=" + custom_string_restroom;

        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }


    /**
     * 应标大厅详情页
     *
     * @param needs_id 　项目编号
     * @param callback 　回调
     */
    public void getBidHallDetailData(String needs_id,
                                     OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_BID_HALL_DETAIL + needs_id;
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 发送实名认证信息
     *
     * @param hs_uid
     * @param Callback
     */
    public void postRealNameData(JSONObject jsonObject, final String hs_uid,
                                 OkJsonRequest.OKResponseCallback Callback) {
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.POST, UrlConstants.URL_POST_REAL_NAME + member_id + "/realnames", jsonObject, Callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.HS_UID, hs_uid);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取实名认证状态
     *
     * @param member_id
     * @param hs_uid
     * @param Callback
     */
    public void getRealNameAuditStatus(String member_id, final String hs_uid,
                                       OkJsonRequest.OKResponseCallback Callback) {
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, UrlConstants.URL_POST_REAL_NAME + member_id + "/realnames", null, Callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
//                header.put(Constant.NetBundleKey.HS_UID, hs_uid);
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }


    /**
     * @param jsonObject
     * @param needs_id
     * @param designer_id
     * @param callback
     */
    public void sendBidDemand(JSONObject jsonObject,/* final String acsToken,*/ String needs_id, String designer_id,
                              OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_I_WANT_SHOULD_BID + needs_id +
                "/designers/" + designer_id;

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
//                header.put(Constant.NetBundleKey.MEMBER_TYPE, memType);
//                header.put(Constant.NetBundleKey.ACS_TOKEN, acsToken);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取设计师信息
     *
     * @param offset
     * @param limit
     * @param callback
     */
    public void getFindDesignerData(int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_FIND_DESIGNER +
                "?offset=" + offset +
                "&sort_order=desc" +
                "&sort_by=date" +
                "&limit=" + limit;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 全流程节点信息获取
     */

    public void getWkFlowStatePointInformation(OkJsonRequest.OKResponseCallback callback) {

        OkJsonRequest okJsonRequest = new OkJsonRequest(OkJsonRequest.Method.GET, UrlConstants.URL_WkFlowState_pointe_Information, null, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        queue.add(okJsonRequest);
    }

    /**
     * 设计师个人信息
     *
     * @param callback
     * @param designer_id
     * @param hs_uid
     */
    public void getDesignerInfoData(final String designer_id, final String hs_uid, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_DESIGNER_INFO + designer_id;

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.HS_UID, hs_uid);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取消费者\设计师个人基本信息
     *
     * @param callback
     * @param member_id
     */
    public void getConsumerInfoData(String member_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_CONSUMER_INFO + member_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 更新用户基础信息
     *
     * @param designer_id 用户id
     * @param jsonObject  修改的内容
     * @param callback    监听回调
     */
    public void putAmendDesignerInfoData(String designer_id, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_PUT_AMEND_DESIGNER_INFO + designer_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, url, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 修改设计师个人中心信息-量房费
     *
     * @param callback
     * @param designer_id
     * @param hs_uid
     * @param jsonObject
     * @brief Modify the project design .
     */
    public void putAmendDesignerCostData(String designer_id, final String hs_uid, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_PUT_AMEND_DESIGNER_COST + designer_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, url, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.HS_UID, hs_uid);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 修改消费者个人中心信息
     *
     * @param callback
     * @param member_id
     * @param jsonObject
     */
    public void putAmendConsumerInfoData(String member_id, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_PUT_AMEND_CONSUMER_INFO + member_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, url, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }


    /**
     * 设计师详情
     *
     * @param callback
     * @param designer_id
     * @param offset
     * @param limit
     */
    public void getSeekDesignerDetailData(String designer_id, int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_SEEK_DESIGNER_DETAIL + designer_id + "/cases?" +
                "offset=" + offset +
                "&sort_order=desc" +
                "&sort_by=date" +
                "&limit=" + limit;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 设计师详情-设计师信息
     */
    public void getSeekDesignerDetailHomeData(String designer_id, final String hsUid, OkJsonRequest.OKResponseCallback callback) {
        String mUrl = UrlConstants.URL_GET_SEEK_DESIGNER_DETAIL_HOME + designer_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, mUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.HS_UID, hsUid);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取案例库列表
     *
     * @param case_id
     * @param callback
     */
    public void getCaseListDetail(String case_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_CASE_DETAILS + case_id;
//        String url = "http://192.168.120.90:8080/design-app/v1/api/cases/" + case_id;
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 发送点赞请求
     *
     * @param assetId
     * @param callback
     */
    public void sendThumbUpRequest(String assetId, OkJsonRequest.OKResponseCallback callback) {
        String url = "http://192.168.120.90:8080/design-app/v1/api/designers/d2/cases/like/" + assetId;
//        String url = UrlConstants.URL_GET_CASE_DETAILS_LIKE + assetId;
        Log.d("yxw", url);
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.PUT, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_XTOKEN, xToken);
                Log.d("yxw", Constant.NetBundleKey.X_XTOKEN + "   " + xToken);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获得点赞状态
     *
     * @param assetId
     * @param callback
     */
    public void getThumbUpRequest(String assetId, OkJsonRequest.OKResponseCallback callback) {
        String url = "http://192.168.120.90:8080/design-app/v1/api/designers/d2/cases/like/" + assetId;
//        String url = UrlConstants.URL_GET_CASE_DETAILS_LIKE + assetId;
        Log.d("yxw", url);
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_XTOKEN, xToken);
                Log.d("yxw", Constant.NetBundleKey.X_XTOKEN + "   " + xToken);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 判断是否是实名认证
     *
     * @param hsUid
     * @param designer_id
     * @param callback
     */
    public void getRealName(final String hsUid, String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_IS_REALY_NAME + designer_id +
                "/home";
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.HS_UID, hsUid);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 个人中心 我要应标
     *
     * @param callback
     * @param memType
     * @param acsToken
     * @param offset
     * @param limit
     * @param designer_id
     */
    public void getMyBidData(final String memType, final String acsToken, int offset, int limit, String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_MY_BID + designer_id +
                "/bidders?" +
                "offset=" + offset +
                "&limit=" + limit +
                "&sort_by=date" +
                "&sort_order=desc";
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.ACS_TOKEN, acsToken);
                header.put(Constant.NetBundleKey.MEMBER_TYPE, memType);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 北舒家装订单
     *
     * @param callback
     * @param designer_id
     * @param offset
     * @param limit
     */
    public void getDesignerBeiShuOrder(String designer_id, int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_BEI_SHU_ORDER + designer_id +
                "/needs?" +
                "sort_order=desc" +
                "&limit=" + limit +
                "&offset=" + offset +
                "&media_type_id=53" +
                "&software=96" +
                "&asset_taxonomy=ezhome/beishu" +
                "&sort_by=date" +
                "&version=4.15";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 设计师普通订单
     *
     * @param callback
     * @param memType
     * @param designer_id
     * @param offset
     * @param limit
     */
    public void getDesignerOrder(final String memType, final String designer_id, final int offset, final int limit, final OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_ORDER + designer_id +
                "/orders?" +
                "offset=" + offset +
                "&sort_order=desc" +
                "&sort_by=date" +
                "&limit=" + limit;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.MEMBER_TYPE, memType);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 北舒套餐表单
     *
     * @param callback
     * @param member_id
     * @param jsonObject
     */
    public void sendBeiShuMealInfoData(String member_id, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_BEI_SHU_MEAL + member_id;
        KLog.d(TAG, url);

        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.POST, url, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 全流程订单详情
     *
     * @param callback
     * @param needs_id
     * @param member_id
     */
    public void getOrderDetailsInfoData(String needs_id, String member_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_ORDER_DETAILS + needs_id +
                "/designers/" + member_id;
        KLog.d(TAG, url);
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取云相册
     *
     * @param callback
     * @param X_Token
     * @param needs_id
     * @param member_id
     */
    public void getCloudFiles(
            final String X_Token, String needs_id, String member_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_ORDER_DETAILS + needs_id +
                "/designers/" + member_id
                + "/cloud_files";

        KLog.d(TAG, url);
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(X_Token));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 拒绝设计师量房
     */
    public void refuseDesignerMeasure(final String needs_id, String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.MAIN_DESIGN +
                "/needs/" + needs_id +
                "/designers/" + designer_id +
                "?bidding_status=03";

        KLog.d(TAG, url);
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.PUT, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 修改需求
     *
     * @param callback
     * @param amendJson
     */
    public void getModifyDesignerRequirement(String needs_id, final JSONObject amendJson, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_MODIFY_MEAL + needs_id;
        KLog.d(TAG, url);
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.PUT, url, amendJson, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 终止需求
     *
     * @param callback
     * @param needs_id
     * @param is_deleted
     */
    public void getStopDesignerRequirement(final String needs_id, final int is_deleted, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_MODIFY_MEAL + needs_id +
                "/cancel" +
                "?is_deleted=" + is_deleted;
        KLog.d(TAG, url);

        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.PUT, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 发布需求
     *
     * @param callback
     * @param jsonObject
     */
    public void sendDesignRequirements(JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.POST, UrlConstants.URL_SEND_DESIGN_REQUIREMENTS, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取合同编号
     *
     * @param callback
     */
    public void getContractNumber(OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, UrlConstants.URL_GET_CONTRACT_NUM, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取需求详情
     *
     * @param need_id
     * @param callback
     */
    public void getAmendDemand(String need_id, OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, UrlConstants.URL_POST_MODIFY_MEAL + need_id, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.MEMBER_TYPE, memType);
                header.put(Constant.NetBundleKey.ACS_TOKEN, acs_token);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 发送设计合同
     */
    public void sendEstablishContract(final String need_id, final String Member_Type, final String acsToken, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_SEND_ESTABLISH_CONTRACT + need_id;
        KLog.d(TAG, "url:" + url + "##Authorization:" + addX_Token(xToken) + "##Member-Type:" + Member_Type + "##ACS-Token:" + acsToken);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url, jsonObject, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.MEMBER_TYPE, Member_Type);
                header.put(Constant.NetBundleKey.ACS_TOKEN, acsToken);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 设计师同意量房
     *
     * @param callback
     * @param need_id
     */
    public void agreeMeasureHouse(String need_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_PUT_AGREE_MEASURE_HOUSE + need_id;
        JSONObject jsonObject = new JSONObject();
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, url, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 设计师拒绝量房
     *
     * @param callback
     * @param need_id
     */
    public void agreeRefusedHouse(String need_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_PUT_REFUSED_MEASURE_HOUSE + need_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 消费者同意应标
     *
     * @param callback
     */
    public void agreeResponseBid(JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, UrlConstants.URL_AGREE_RESPONSE_BID, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 消费者自选设计师量房
     *
     * @param callback
     */
    public void agreeOneselfResponseBid(JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_ONESELF_AGREE_RESPONSE_BID;
        KLog.d(TAG, "url" + url);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 支付宝接口
     *
     * @param order_no
     * @param order_line_no
     * @param callback
     */
    public void getAlipayDetailInfo(String order_no, String order_line_no, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_PAY +
                "parameters" +
                "?orderId=" + order_no +
                "&orderLineId=" + order_line_no +
                "&channel_type=mobile" +
                "&paymethod=1";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 判断是否是乐屋设计师
     *
     * @param designers
     * @param hs_uid
     * @param callback
     */
    public void ifIsLohoDesiner(String designers, final String hs_uid, OkJsonRequest.OKResponseCallback callback) {
        //http://alpha-api.gdfcx.net/member-app/v1/api/designers/20735915
        String url = UrlConstants.ALPHA_MP_MAIN + "/member-app/v1/api/designers/" + designers;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.HS_UID, hs_uid);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取与装修项目相关联的3D方案列表
     *
     * @param callback
     * @param needs_id
     */
    public void get3DPlanInfoData(String needs_id, String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_DELIVER +
                "references/" + needs_id +
                "?limit=10" +
                "&offset=0" +
                "&designer_id=" + designer_id;
        KLog.d(TAG, url);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                KLog.d(TAG, Constant.NetBundleKey.X_TOKEN + ":" + addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取3D方案的文件列表
     *
     * @param callback
     * @param asset_3d_id
     */
    public void get3DPlanList(final String needs_id, String asset_3d_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_DELIVER + asset_3d_id +
                "?limit=10" +
                "&offset=0" +
                "&needs_id=" + needs_id;
        KLog.d(TAG, url);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 提交交付物
     *
     * @param callback
     * @param needs_id
     * @param designer_id
     * @param file_ids
     * @param design_assets_id
     */
    public void postDelivery(final String needs_id, final String designer_id, String file_ids, String design_assets_id, String type, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_DELIVER + design_assets_id +
                "/references/" + needs_id +
                "?designer_id=" + designer_id +
                "&file_ids=" + file_ids + design_assets_id +
                "&type=" + type;

        KLog.d(TAG, url);
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.POST, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 设计师获取已经交付的文件
     *
     * @param callback
     * @param needs_id
     */
    public void getDeliveredFile(String needs_id, String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_DELIVER +
                "delivery/" + needs_id +
                "?designer_id=" + designer_id;

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }


    /**
     * @param callback
     */
    public void getLoginThreadId(String memberId, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.MAIN_DESIGN +
                "/message/" +
                "member/" + memberId;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 我的资产
     *
     * @param callback
     * @param designer_id
     */
    public void getMyPropertyData(String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_MY_PROPERTY + designer_id;
        KLog.d(TAG, "url:" + url + "\n" + Constant.NetBundleKey.X_TOKEN + ":" + addX_Token(xToken));
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("X-Token", xToken);
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取交易记录数据
     *
     * @param designer_id
     * @param offset
     * @param limit
     * @param callback
     */
    public void getTransactionRecordData(String designer_id, int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_TRANSACTION_RECORD + designer_id +
                "?limit=" + limit +
                "&offset=" + offset;

        KLog.d(TAG, "url=" + url + "\ndesigner_id=" + designer_id + "\n" + Constant.NetBundleKey.X_TOKEN + ":" + addX_Token(xToken));
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put("X-Token", xToken);
                return header;
            }
        };
        queue.add(okRequest);
    }

    public void getWithdrawalRecordData(String designer_id, int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_WITHDRAW_RECORD + designer_id +
                "?limit=" + limit +
                "&offset=" + offset;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put("X-Token", xToken);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取消息中心数据
     *
     * @param offset
     * @param limit
     */
    public void getMessageCenterMessages(int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_MESSAGE_CENTER + member_id + "/sysmessages?limit=" + limit + "&offset=" + offset;
        KLog.d("test", url);

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                KLog.d("test", addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取提现数据
     *
     * @param designer_id
     * @param jsonObject
     * @param callback
     */
    public void getWithDrawBalanceData(final long designer_id,
                                       JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, UrlConstants.URL_WITHDRAW_BALANCE + designer_id, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    public void sendUnBindBankCard(final long designer_id,
                                   JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, UrlConstants.URL_WITHDRAW_BALANCE + designer_id, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.X_XTOKEN, xToken);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 消费者交付物确认处理
     *
     * @param demands_id         　当前项目需求id
     * @param designer_id        　设计师id
     * @param okResponseCallback 回调接口
     */
    public void makeSureDelivery(String demands_id, String designer_id, OkJsonRequest.OKResponseCallback okResponseCallback) {
        String makeSureUrl = UrlConstants.MAIN_DESIGN +
                "/demands/" + demands_id +
                "/designers/" + designer_id +
                "/deliveries/options/confirm";
        KLog.d(TAG, makeSureUrl);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, makeSureUrl, null, okResponseCallback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 保存评价信息接口
     *  @param demands_id         项目编号
     * @param designer_id        设计师编号
     * @param jsonObject
     * @param okResponseCallback 回调接口
     */
    public void submitAppraisement(String demands_id, String designer_id, JSONObject jsonObject, OkJsonRequest.OKResponseCallback okResponseCallback) {
        String makeSureUrl = UrlConstants.MAIN_MEMBER +
                "/demands/" + demands_id +
                "/designers/" + designer_id +
                "/score";
        KLog.d(TAG, makeSureUrl);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, makeSureUrl, jsonObject, okResponseCallback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 交付物延期
     *
     * @param demands_id  需求id
     * @param designer_id 设计师id
     * @param callback    回调接口
     */
    public void getFlowUploadDeliveryDelay(String demands_id, String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_DELIVERY_DELAY + demands_id +
                "/designers/" + designer_id +
                "/deliveries/options/delay";
        KLog.d(TAG, url);

        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.PUT, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 交付物延期时间
     *
     * @param demands_id  需求id
     * @param designer_id 设计师id
     * @param callback    回调接口
     */
    public void getFlowUploadDeliveryDelayDate(String demands_id, String designer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_DELIVERY_DELAY_DATA + demands_id +
                "/designers/" + designer_id +
                "/deliveries/options/confirm/remaindays";
        KLog.d(TAG, url);

        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 关注
     *
     * @param member_id          登陆人编号
     * @param followed_member_id 被关注设计师编号
     * @param callback           回调接口
     */
    public void followingDesigner(String member_id, String followed_member_id, String followed_member_uid, OkJsonRequest.OKResponseCallback callback) {
        String attentionOrUnfollowDesignerUrl = UrlConstants.MAIN_MEMBER +
                "/members/" + member_id +
                "/follows/" + followed_member_id +
                "?followed_member_uid=" + followed_member_uid;

        KLog.d(TAG, attentionOrUnfollowDesignerUrl);

        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.POST, attentionOrUnfollowDesignerUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                KLog.d(TAG, Constant.NetBundleKey.X_TOKEN + ":" + addX_Token(xToken));
                return header;
            }
        };
        queue.add(okJsonRequest);
    }

    /**
     * 取消关注
     *
     * @param member_id          登陆人编号
     * @param followed_member_id 被关注设计师编号
     * @param callback           回调接口
     */
    public void unFollowedDesigner(String member_id, final String followed_member_id, final String followed_member_uid, OkJsonRequest.OKResponseCallback callback) {
        String attentionOrUnfollowDesignerUrl = UrlConstants.MAIN_MEMBER +
                "/members/" + member_id +
                "/follows/" + followed_member_id;
        KLog.d(TAG, attentionOrUnfollowDesignerUrl);

        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.PUT, attentionOrUnfollowDesignerUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.HS_UID, followed_member_uid);
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                KLog.d(TAG, Constant.NetBundleKey.X_TOKEN + ":" + addX_Token(xToken));
                return header;
            }
        };
        queue.add(okJsonRequest);
    }

    /**
     * 关注列表
     *
     * @param member_id 用户id
     * @param limit
     * @param offset
     * @param callback
     */
    public void attentionListData(String member_id, int limit, int offset, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_DELETE_ATTENTION + member_id + "/follows?" + "limit=" + limit + "&offset=" + offset;
        KLog.d(TAG, url);

        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return header;
            }
        };
        queue.add(okJsonRequest);

    }


    /**
     * 为X-Token 增加前缀
     *
     * @param xToken
     * @return
     */
    private String addX_Token(String xToken) {
        return Constant.NetBundleKey.X_TOKEN_PREFIX + xToken;
    }

    private String TAG = getClass().getSimpleName();

}
