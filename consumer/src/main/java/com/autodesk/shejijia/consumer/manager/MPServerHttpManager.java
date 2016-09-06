package com.autodesk.shejijia.consumer.manager;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FindDesignerBean;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
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
    }

    public static MPServerHttpManager getInstance() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            xToken = memberEntity.getHs_accesstoken();
            member_id = memberEntity.getAcs_member_id();
            memType = memberEntity.getMember_type();
            acs_token = memberEntity.getAcs_token();
        }
        return mpServerHttpManager;
    }

    /**
     * 我的家装订单
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
     * 获取精选设计师作品图片
     *
     * @param callback
     */
    public void getDesignWorks(OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_PUT_SELECTION_DESIGNER_PICTURES;//"http://192.168.120.163:8080/design-app/v1/api/selection/pictures";

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
     */
    public void getCaseListData(String custom_string_style,
                                String custom_string_type,
                                String custom_string_keywords,
                                String custom_string_area,
                                String custom_string_bedroom,
                                String taxonomy_id,
                                String custom_string_restroom,
                                String custom_string_form,
                                final int offset, final int limit,
                                OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.URL_GET_CASE_LIST_SEARCH +
                "custom_string_style=" + custom_string_style +
                "&custom_string_type=" + custom_string_type +
                "&custom_string_keywords=" + custom_string_keywords +
                "&sort_by=date" +
                "&custom_string_area=" + custom_string_area +
                "&custom_string_bedroom=" + custom_string_bedroom +
                "&taxonomy_id=" + taxonomy_id +
                "&offset=" + offset +
                "&limit=" + limit +
                "&custom_string_restroom=" + custom_string_restroom +
                "&sort_order=desc" +
                "&custom_string_form=" + custom_string_form;

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //HashMap<String, String> header = new HashMap<>();
                // header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    public void get3DCaseListData(String custom_string_style,
                                  String custom_string_type,
                                  String custom_string_keywords,
                                  String custom_string_area,
                                  String custom_string_bedroom,
                                  String taxonomy_id,
                                  String custom_string_restroom,
                                  String custom_string_form,
                                  final int offset, final int limit,
                                  OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.URL_GET_CASE_LIST_D3 +
                "custom_string_style=" + custom_string_style +
                "&custom_string_type=" + custom_string_type +
                "&custom_string_keywords=" + custom_string_keywords +
                "&sort_by=date" +
                "&custom_string_area=" + custom_string_area +
                "&custom_string_bedroom=" + custom_string_bedroom +
                "&taxonomy_id=" + taxonomy_id +
                "&offset=" + offset +
                "&limit=" + limit +
                "&custom_string_restroom=" + custom_string_restroom +
                "&sort_order=desc" +
                "&custom_string_form=" + custom_string_form;

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (xToken == null) {
                    return super.getHeaders();
                } else {
                    HashMap<String, String> header = new HashMap<>();
                    header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                    return header;
                }
            }
        };
        queue.add(okRequest);
    }


    /**
     * 获取应标大厅应标信息
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
                header.put(Constant.NetBundleKey.HS_UID, hs_uid);
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
    public void sendBidDemand(JSONObject jsonObject, String needs_id, String designer_id,
                              OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_I_WANT_SHOULD_BID + needs_id +
                "/designers/" + designer_id;

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url, jsonObject, callback) {
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
     * 搜索，查找设计师
     */
    public void findDesignerList(FindDesignerBean findDesignerBean,
                                 int offset,
                                 int limit, OkJsonRequest.OKResponseCallback callback) {
        String filterURl = UrlConstants.MAIN_MEMBER + "/designers/search?" +
                "nick_name=" + findDesignerBean.getNick_name() +
                "&style_names=" + findDesignerBean.getStyle_names() +
                "&start_experience=" + findDesignerBean.getStart_experience() +
                "&end_experience=" + findDesignerBean.getEnd_experience() +
                "&design_price_code=" + findDesignerBean.getDesign_price_code() +
                "&styles=" + findDesignerBean.getNick_name() +
                "&offset=" + offset +
                "&limit=" + limit;

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, filterURl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }


    /**
     * 获取设计师从业年限列表
     *
     * @param callback
     */
    public void getDesignerExperiences(OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_FIND_DESIGNER +
                "/experiences";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取设计师设计费区间
     *
     * @param callback
     */
    public void getDesignerCost(OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_FIND_DESIGNER +
                "/costs";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取设计师风格列表
     *
     * @param callback
     */
    public void getDesignerStyles(OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_FIND_DESIGNER +
                "/styles ";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

    /**
     * 终止合作
     *
     * @param callback
     */
    public void sendStopFlow(JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_STOP_COLLABORATION;

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
     * 全流程节点信息获取(带精选)
     */
    public void getAll_WkFlowStatePointInformation(OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_ALL_WKFLOWSTATE_POINTE_INFORMATION;

        OkJsonRequest okJsonRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {

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
     * 设计师3D详情
     *
     * @param callback
     * @param designer_id
     * @param offset
     * @param limit
     */
    public void getSeekDesigner3DDetailData(String designer_id, int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_SEEK_DESIGNER_ANONYMITY + designer_id + "/d3/d3dimensionals?" +
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
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.HS_UID, hsUid);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取案例库详情列表
     *
     * @param case_id
     * @param callback
     */
    public void getCaseListDetail(String case_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_CASE_DETAILS + case_id;
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (xToken != null) {
                    HashMap<String, String> header = new HashMap<>();
                    header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                    return header;
                } else {
                    return super.getHeaders();
                }
            }
        };
        queue.add(okRequest);
    }

    /**
     * 获取3D案例库详情列表
     */
    public void getCaseList3DDetail(String case_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_GET_CASE_LIST_D3_DETAIL + case_id;
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (xToken!=null){
                    HashMap<String, String> header = new HashMap<>();
                    header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                    return header;
                }else {
                    return  super.getHeaders();
                }

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
        String url = UrlConstants.URL_GET_CASE_DETAILS_LIKE + assetId;
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.PUT, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_XTOKEN, xToken);
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
        String url = UrlConstants.URL_GET_CASE_DETAILS_LIKE + assetId;
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
//        String url = UrlConstants.URL_GET_BEI_SHU_ORDER + designer_id +
//                "/needs?" +
//                "sort_order=desc" +
//                "&limit=" + limit +
//                "&offset=" + offset +
//                "&media_type_id=53" +
//                "&software=96" +
//                "&asset_taxonomy=ezhome/beishu" +
//                "&sort_by=date" +
//                "&version=4.15";

        String urlV2 = UrlConstants.URL_GET_ORDER + designer_id + "/orders" +
                "?offset=" + offset +
                "&limit=" + limit + "" +
                "&sort_order=desc" +
                "&sort_by=date" +
                "&version=2" +
                "&service_modlue=4" +
                "&node_ids=" +
                "&sub_node_ids=" +
                "&commend=";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, urlV2, null, callback) {
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
     */
    public void getDesignerOrder(final String memType, final String designer_id, final int offset, final int limit, final OkJsonRequest.OKResponseCallback callback) {
//        String url = UrlConstants.URL_GET_ORDER + designer_id +
//                "/orders?" +
//                "offset=" + offset +
//                "&sort_order=desc" +
//                "&sort_by=date" +
//                "&limit=" + limit;

        String urlV2 = UrlConstants.URL_GET_ORDER + designer_id + "/orders" +
                "?offset=" + offset +
                "&limit=" + limit + "" +
                "&sort_order=desc" +
                "&sort_by=date" +
                "&version=2" +
                "&service_modlue=2" +
                "&node_ids=" +
                "&sub_node_ids=" +
                "&commend=";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, urlV2, null, callback) {
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
     * 精选订单
     */
    public void getSliteOder(HashMap<String, Object> map, final OkJsonRequest.OKResponseCallback callback) {
//        {@"offset"   :offset,
//            @"limit"    :limit,
//            @"sort_by"  :@"date",
//            @"sort_order":@"desc",
//            @"version":version,
//            @"service_modlue":servicemodlue,
//            @"node_ids":@"",
//            @"sub_node_ids":@"",
//            @"commend":@""};




        String url = UrlConstants.URL_GET_ORDER + map.get(JsonConstants.JSON_MEASURE_FORM_DESIGNER_ID) + "/orders?" +
                "offset=" + map.get(JsonConstants.JSON_DEMAND_LIST_OFFSET) +
                "&limit=" + map.get(JsonConstants.JSON_DEMAND_LIST_LIMIT) +
                "&sort_by=desc" +
                "&sort_order=date" +
                "&version=2" +
                "&service_modlue=5" +
                "&node_ids=" +""+
                "&sub_node_ids=" +""+
                "&commend="+"";

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
     */
    public void getModifyDesignerRequirement(String needs_id, String wk_template_id, final JSONObject amendJson, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_MODIFY_MEAL + needs_id;
        if (wk_template_id.equals("4")) {
            url = UrlConstants.URL_POST_ELITE_MODIFY_MEAL + needs_id;
        }
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
     */
    public void sendDesignRequirements(JSONObject jsonObject, boolean isSelection, OkJsonRequest.OKResponseCallback callback) {
        String url = isSelection ? UrlConstants.URL_SEND_DESIGN_SELECTION_REQUIREMENTS : UrlConstants.URL_SEND_DESIGN_REQUIREMENTS;

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
     * 获取合同编号
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
     */
    public void getAmendDemand(String need_id, OkJsonRequest.OKResponseCallback callback) {
        String amendUrl = UrlConstants.MAIN_DESIGN + "/demands/" + need_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, amendUrl, null, callback) {
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
    public void sendEstablishContract(final String need_id, final String Member_Type, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_POST_SEND_ESTABLISH_CONTRACT + need_id;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url, jsonObject, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.MEMBER_TYPE, Member_Type);
                header.put(Constant.NetBundleKey.ACS_TOKEN, acs_token);
                return header;
            }
        };
        queue.add(okRequest);
    }

    /**
     * 设计师同意量房
     */
    public void agreeMeasureHouse(String need_id, String designer_id, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String agreeMeasureUrl = UrlConstants.MAIN_DESIGN +
                "/demands/" + need_id +
                "/designers/" + designer_id +
                "/measurement/options/agreement";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, agreeMeasureUrl, jsonObject, callback) {
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
     */
    public void agreeOneselfResponseBid(JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_ONESELF_AGREE_RESPONSE_BID;
//        String url ="192.168.120.113:8080/design-app/v1/api/orders?is_need=false"; // 要和明旭联调得接口
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
     * 消费者发送量房邀约
     *
     * @param callback
     */
    public void SendMeasureForm(String needs_id, String acs_designer_id, JSONObject jsonObject, OkJsonRequest.OKResponseCallback callback) {
        String url1 = UrlConstants.URL_SEND_DESIGN_SELECTION_REQUIREMENTS + "/" + needs_id + "" +
                "/designers/" + acs_designer_id + "/measurement/options/invitation";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url1, jsonObject, callback) {
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
     */
    public void getAlipayDetailInfo(String order_no, String order_line_no, OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.URL_PAY +
                "parameters" +
                "?orderId=" + order_no +
                "&orderLineId=" + order_line_no +    //

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
     */
    public void ifIsLohoDesiner(String designers, final String hs_uid, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.MAIN_MEMBER + "/designers/" + designers;
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
     */
    public void getMessageCenterMessages(int offset, int limit, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.URL_MESSAGE_CENTER + member_id + "/sysmessages?limit=" + limit + "&offset=" + offset;

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
        String url = UrlConstants.URL_WITHDRAW_MEMBERS + designer_id + "/balances/delete";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.PUT, url, jsonObject, callback) {
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
     *
     * @param demands_id         项目编号
     * @param designer_id        设计师编号
     * @param jsonObject
     * @param okResponseCallback 回调接口
     */
    public void submitAppraisement(String demands_id, String designer_id, JSONObject jsonObject, OkJsonRequest.OKResponseCallback okResponseCallback) {
        String makeSureUrl = UrlConstants.MAIN_DESIGN +
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
     * 获取设计师首页评价列表信息
     */
    public void getEstimateList(String designer_id, int limit, int offset, OkJsonRequest.OKResponseCallback callback) {
        String estimateUrl = UrlConstants.MAIN_MEMBER + "/designers/" + designer_id + "/score?limit=" + limit + "&offset=" + offset;
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, estimateUrl, null, callback) {
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
     * 关注或者取消关注设计师
     *
     * @param member_id          登陆人编号
     * @param followed_member_id 被关注设计师编号
     * @param callback           回调接口
     */
    public void followingOrUnFollowedDesigner(String member_id,
                                              final String followed_member_id,
                                              final String followed_member_uid,
                                              boolean followsType,
                                              OkJsonRequest.OKResponseCallback callback) {

        String attentionOrUnFollowDesignerUrl = UrlConstants.MAIN_MEMBER +
                "/members/" + member_id +
                "/follows/" + followed_member_id +
                "?followed_member_uid=" + followed_member_uid +
                "&follows_type=" + followsType;
        KLog.d(TAG, attentionOrUnFollowDesignerUrl);

        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.POST, attentionOrUnFollowDesignerUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                return header;
            }
        };
        queue.add(okJsonRequest);
    }

    /**
     * 关注列表
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
     * 获取设计师设计费用列表
     */
    public void getDesignerDesignCost(OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.URL_DESIGNER_DESIGN_COST_RANGE;

        OkJsonRequest okJsonRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        queue.add(okJsonRequest);

    }

    /**
     * 获得工作室列表
     */
    public void getWorkRoomList(OkJsonRequest.OKResponseCallback callback, String type, int offset, int limit) {

        String url = UrlConstants.MAIN_MEMBER + "/designers/search/studio?limit=" + limit + "&offset=" + offset + "&type_code=" + type;
        OkJsonRequest okJsonRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        queue.add(okJsonRequest);

    }


    /**
     * 套餐发布需求
     */
    public void sendPackagesForm(JSONObject jsonObject, String customer_id, OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.MAIN_DESIGN + "/appointMeal/" + customer_id;
//        "http://192.168.88.175:8080/design-app/v1/api/appointMeal/"
//        String url = UrlConstants.SEND_PACKAGES_FORM + customer_id;

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
     * 大师接口
     */
    public void getGrandMasterInfo(int offset, int limit, String type, OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.MAIN_MEMBER + "/designers/search/studio?limit=" + limit + "&offset=" + offset + "&type_code=" + type;//61
//        String url = "http://192.168.120.217:8083/member-app/v1/api/designers/search/studio?limit=20&offset=0&type_code=61";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback);
        queue.add(okRequest);
    }

    /**
     * 大师详情接口
     */
    public void getGrandMasterDetailInfo(int offset, int limit, final String hs_uid, OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.MAIN_MEMBER + "/designers/studio?limit=" + limit + "&offset=" + offset;
//        String url = "http://192.168.120.217:8083/member-app/v1/api/designers/studio?limit=10&offset=0";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("hs_uid", hs_uid);
                return header;
            }
        };
        queue.add(okRequest);
    }


    /**
     * 上传工作室立即预约信息
     */

    public void upWorkRoomOrderData(JSONObject jsonObject,
                                    OkJsonRequest.OKResponseCallback callback) {
        String url = UrlConstants.MAIN_DESIGN + "/sixmodules/demands";

        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, url, jsonObject, callback) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> header = new HashMap<>();
//                header.put(Constant.NetBundleKey.X_TOKEN, addX_Token(xToken));
//                return header;
//            }
        };
        queue.add(okRequest);

    }

    /**
     * 获取工作室详情的信息
     */

    public void getWorkRoomOrderData(int designer_id, int offset, int limit, final String hs_uid, OkJsonRequest.OKResponseCallback callback) {

        String url = UrlConstants.MAIN_MEMBER + "/designers/studio?limit=" + limit + "&offset=" + offset;

        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("hs_uid", hs_uid);
                return header;
            }
        };
        queue.add(okRequest);

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
