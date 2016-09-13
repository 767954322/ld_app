package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by he.liu  on 16-9-3.
 * <p/>
 * v2接口的设计师家订单的实体类
 */
public class OrderBeiShuBean implements Serializable {


    /**
     * count : 99
     * date : 1473762774000
     * limit : 10
     * offset : 0
     * _link : null
     * beishu_needs_order_list : [{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TW1ASQFISX0XF9N","join_time":"2016-09-10 18:03:22","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-10 18:03:22","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"20812","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57d0c4dbe4b07d88ca908fe8.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"cfbe6162-dd82-42fd-97b1-448b22ede038","beishu_thread_id":"TW1ASQFISX0XF9N","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"Qqqqqq","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"18500373374","contacts_name":"æ¶\u0088è´¹å\u0093¼å\u0093\u0088","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"27","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1608878,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-09-10 18:03:21","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TTL683JISX0O2Q8","join_time":"2016-09-10 17:56:06","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-10 17:56:06","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"20808","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57d0c4dbe4b07d88ca908fe8.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"cfbe6162-dd82-42fd-97b1-448b22ede038","beishu_thread_id":"TTL683JISX0O2Q8","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"qqq","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"18500373374","contacts_name":"æ¶\u0088è´¹å\u0093¼å\u0093\u0088","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"27","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1608868,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-09-10 17:56:04","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TQZ7L4WISWRO49N","join_time":"2016-09-10 13:44:12","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-10 13:44:12","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"20698","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"420100","district":"420102","province":"420000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57cfb61ee4b07d88ca908fc6.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"a11aa399-5811-42c2-8613-cc006fdde65c","beishu_thread_id":"TQZ7L4WISWRO49N","bidder_count":1,"bidding_status":true,"city_name":"æ­¦æ±\u0089å¸\u0082","click_number":0,"community_name":"é\u0098\u009cå\u008d\u0097é\u0087\u008c","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"13651225333","contacts_name":"å\u008f¤æ\u009c¨","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"æ±\u009få²¸å\u008cº","end_day":"27","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1608603,"province_name":"æ¹\u0096å\u008c\u0097ç\u009c\u0081","publish_time":"2016-09-10 13:44:10","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TUM26X6ISVLYLCF","join_time":"2016-09-09 18:16:36","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-09 18:16:36","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"20577","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57d7a49ce4b07d88ca909162.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"685e39d1-120a-4ce0-9b77-a8437f1c0816","beishu_thread_id":"TUM26X6ISVLYLCF","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"å\u0095¦å\u0095¦","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"15246466466","contacts_name":"ffhjjj","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"26","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1608336,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-09-09 18:16:34","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"T4GOYBTISSWP2FZ","join_time":"2016-09-07 20:53:49","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-07 20:53:49","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"19887","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110113","province":"110000","room":null,"toilet":" ","avatar":null,"type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"261824a9-488b-493b-af57-165050b3eba1","beishu_thread_id":"T4GOYBTISSWP2FZ","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"zhao0907008","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"18699990000","contacts_name":"XFZzdong","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"é¡ºä¹\u0089å\u008cº","end_day":"24","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1606351,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-09-07 20:53:47","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TJN3ME2ISJYVFR8","join_time":"2016-09-01 14:44:50","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-01 14:44:50","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"18560","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":null,"type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"261824a9-488b-493b-af57-165050b3eba1","beishu_thread_id":"TJN3ME2ISJYVFR8","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"test0003","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"18512234566","contacts_name":"XFZzdong","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"18","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1601087,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-09-01 14:44:48","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TW0C05SISJY1VH5","join_time":"2016-09-01 14:21:51","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-01 14:21:51","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"18547","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57d4bce0e4b07d88ca9090d2.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"c70df072-4c8d-4f38-9712-b447b4858198","beishu_thread_id":"TW0C05SISJY1VH5","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"é£\u008eè\u008d·æ\u009b²è\u008b\u00912","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"13666666666","contacts_name":"UATæ¶\u0088è´¹è\u0080\u0085","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"18","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1601049,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-09-01 14:21:49","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TFGBZL7ISIKLKW2","join_time":"2016-08-31 15:17:29","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-08-31 15:17:29","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"18139","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57d25968e4b07d88ca90904c.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"ce28d1bd-4ce6-40b1-ad05-727af8fe59a0","beishu_thread_id":"TFGBZL7ISIKLKW2","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"å¥\u0097é¤\u0090å\u008b¿å\u008a¨","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"13520180501","contacts_name":"é«\u0098ä¸­æ\u0097¶ä»£","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"17","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1600216,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-08-31 15:17:27","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TCAAB78ISIK1JFB","join_time":"2016-08-31 15:01:54","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-08-31 15:01:54","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"18128","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57bfa1c6e4b07d88ca908c6c.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"3e4de022-19b9-4f1a-9e34-13698976447e","beishu_thread_id":"TCAAB78ISIK1JFB","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"æµ\u008bè¯\u0095è\u0081\u008aå¤©","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"13412457853","contacts_name":"2007072","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"17","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1600197,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-08-31 15:01:52","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null},{"auditer":null,"bidders":[{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TWIAUCRISIJWPNE","join_time":"2016-08-31 14:58:09","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-08-31 14:58:09","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"18122","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}],"city":"110100","district":"110101","province":"110000","room":null,"toilet":" ","avatar":"http://image.juranzx.com.cn:8082/img/57cfb61ee4b07d88ca908fc6.img","type":null,"address":null,"credentials":null,"elite":null,"pkg":null,"after_bidding_status":"1","audit_desc":null,"consumer_uid":"a11aa399-5811-42c2-8613-cc006fdde65c","beishu_thread_id":"TWIAUCRISIJWPNE","bidder_count":1,"bidding_status":true,"city_name":"å\u008c\u0097äº¬å¸\u0082","click_number":0,"community_name":"å¥½ç\u009a\u0084å¾\u0088","consumer_mobile":null,"consumer_name":null,"contacts_mobile":"18888888888","contacts_name":"æ \u0091è\u008e\u0093","custom_string_status":1,"decoration_budget":null,"decoration_style":null,"design_budget":null,"detail_desc":null,"district_name":"ä¸\u009cå\u009f\u008eå\u008cº","end_day":"17","house_area":null,"house_type":null,"is_beishu":"0","is_public":"0","living_room":" ","needs_id":1600186,"province_name":"å\u008c\u0097äº¬","publish_time":"2016-08-31 14:58:06","wk_template_id":"3","project_status":null,"design_style":null,"main_demand":null,"project_type":null,"project_start_time":null,"customer_id":null,"hs_uid":null,"member_id":null,"designer_name":null,"pkg_name":null,"custom_string_pkg_type":null}]
     */

    private int count;
    private long date;
    private int limit;
    private int offset;
    private Object _link;
    /**
     * auditer : null
     * bidders : [{"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg","declaration":"å\u008c\u0097è\u0088\u0092è®¾è®¡å¸\u0088","delivery":null,"orders":null,"payment":null,"status":"1","uid":"b282fdc6-d544-4db2-876e-9424aeec210a","experience":null,"design_contract":[],"designer_id":20730531,"design_thread_id":"TW1ASQFISX0XF9N","join_time":"2016-09-10 18:03:22","measurement_fee":"0","measure_time":null,"refused_time":null,"selected_time":"2016-09-10 18:03:22","user_name":"å\u0088\u0098æ\u0096° å\u0095\u008a","wk_cur_node_id":"1","wk_current_step_id":null,"wk_cur_sub_node_id":"14","wk_id":"20812","wk_next_possible_sub_node_ids":[],"wk_steps":[],"style_names":null,"design_price_max":null,"design_price_min":null,"measurement_status":null,"mobile_number":null}]
     * city : 110100
     * district : 110101
     * province : 110000
     * room : null
     * toilet :
     * avatar : http://image.juranzx.com.cn:8082/img/57d0c4dbe4b07d88ca908fe8.img
     * type : null
     * address : null
     * credentials : null
     * elite : null
     * pkg : null
     * after_bidding_status : 1
     * audit_desc : null
     * consumer_uid : cfbe6162-dd82-42fd-97b1-448b22ede038
     * beishu_thread_id : TW1ASQFISX0XF9N
     * bidder_count : 1
     * bidding_status : true
     * city_name : åäº¬å¸
     * click_number : 0
     * community_name : Qqqqqq
     * consumer_mobile : null
     * consumer_name : null
     * contacts_mobile : 18500373374
     * contacts_name : æ¶è´¹å¼å
     * custom_string_status : 1
     * decoration_budget : null
     * decoration_style : null
     * design_budget : null
     * detail_desc : null
     * district_name : ä¸ååº
     * end_day : 27
     * house_area : null
     * house_type : null
     * is_beishu : 0
     * is_public : 0
     * living_room :
     * needs_id : 1608878
     * province_name : åäº¬
     * publish_time : 2016-09-10 18:03:21
     * wk_template_id : 3
     * project_status : null
     * design_style : null
     * main_demand : null
     * project_type : null
     * project_start_time : null
     * customer_id : null
     * hs_uid : null
     * member_id : null
     * designer_name : null
     * pkg_name : null
     * custom_string_pkg_type : null
     */

    private List<BeishuNeedsOrderListBean> beishu_needs_order_list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Object get_link() {
        return _link;
    }

    public void set_link(Object _link) {
        this._link = _link;
    }

    public List<BeishuNeedsOrderListBean> getBeishu_needs_order_list() {
        return beishu_needs_order_list;
    }

    public void setBeishu_needs_order_list(List<BeishuNeedsOrderListBean> beishu_needs_order_list) {
        this.beishu_needs_order_list = beishu_needs_order_list;
    }

    public static class BeishuNeedsOrderListBean {
        private Object auditer;
        private String city;
        private String district;
        private String province;
        private Object room;
        private String toilet;
        private String avatar;
        private Object type;
        private Object address;
        private Object credentials;
        private Object elite;
        private Object pkg;
        private String after_bidding_status;
        private Object audit_desc;
        private String consumer_uid;
        private String beishu_thread_id;
        private int bidder_count;
        private boolean bidding_status;
        private String city_name;
        private int click_number;
        private String community_name;
        private Object consumer_mobile;
        private Object consumer_name;
        private String contacts_mobile;
        private String contacts_name;
        private int custom_string_status;
        private Object decoration_budget;
        private Object decoration_style;
        private Object design_budget;
        private Object detail_desc;
        private String district_name;
        private String end_day;
        private Object house_area;
        private Object house_type;
        private String is_beishu;
        private String is_public;
        private String living_room;
        private int needs_id;
        private String province_name;
        private String publish_time;
        private String wk_template_id;
        private Object project_status;
        private Object design_style;
        private Object main_demand;
        private Object project_type;
        private Object project_start_time;
        private String customer_id;
        private Object hs_uid;
        private Object member_id;
        private Object designer_name;
        private Object pkg_name;
        private Object custom_string_pkg_type;
        /**
         * avatar : https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/09/13__03_59_51/57d7a42ce4b07d88ca909152.img1bdb8237-6f51-4753-b466-0338788a7c07.jpg
         * declaration : åèè®¾è®¡å¸
         * delivery : null
         * orders : null
         * payment : null
         * status : 1
         * uid : b282fdc6-d544-4db2-876e-9424aeec210a
         * experience : null
         * design_contract : []
         * designer_id : 20730531
         * design_thread_id : TW1ASQFISX0XF9N
         * join_time : 2016-09-10 18:03:22
         * measurement_fee : 0
         * measure_time : null
         * refused_time : null
         * selected_time : 2016-09-10 18:03:22
         * user_name : åæ° å
         * wk_cur_node_id : 1
         * wk_current_step_id : null
         * wk_cur_sub_node_id : 14
         * wk_id : 20812
         * wk_next_possible_sub_node_ids : []
         * wk_steps : []
         * style_names : null
         * design_price_max : null
         * design_price_min : null
         * measurement_status : null
         * mobile_number : null
         */

        private List<BiddersBean> bidders;

        public Object getAuditer() {
            return auditer;
        }

        public void setAuditer(Object auditer) {
            this.auditer = auditer;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public Object getRoom() {
            return room;
        }

        public void setRoom(Object room) {
            this.room = room;
        }

        public String getToilet() {
            return toilet;
        }

        public void setToilet(String toilet) {
            this.toilet = toilet;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getCredentials() {
            return credentials;
        }

        public void setCredentials(Object credentials) {
            this.credentials = credentials;
        }

        public Object getElite() {
            return elite;
        }

        public void setElite(Object elite) {
            this.elite = elite;
        }

        public Object getPkg() {
            return pkg;
        }

        public void setPkg(Object pkg) {
            this.pkg = pkg;
        }

        public String getAfter_bidding_status() {
            return after_bidding_status;
        }

        public void setAfter_bidding_status(String after_bidding_status) {
            this.after_bidding_status = after_bidding_status;
        }

        public Object getAudit_desc() {
            return audit_desc;
        }

        public void setAudit_desc(Object audit_desc) {
            this.audit_desc = audit_desc;
        }

        public String getConsumer_uid() {
            return consumer_uid;
        }

        public void setConsumer_uid(String consumer_uid) {
            this.consumer_uid = consumer_uid;
        }

        public String getBeishu_thread_id() {
            return beishu_thread_id;
        }

        public void setBeishu_thread_id(String beishu_thread_id) {
            this.beishu_thread_id = beishu_thread_id;
        }

        public int getBidder_count() {
            return bidder_count;
        }

        public void setBidder_count(int bidder_count) {
            this.bidder_count = bidder_count;
        }

        public boolean isBidding_status() {
            return bidding_status;
        }

        public void setBidding_status(boolean bidding_status) {
            this.bidding_status = bidding_status;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getClick_number() {
            return click_number;
        }

        public void setClick_number(int click_number) {
            this.click_number = click_number;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public Object getConsumer_mobile() {
            return consumer_mobile;
        }

        public void setConsumer_mobile(Object consumer_mobile) {
            this.consumer_mobile = consumer_mobile;
        }

        public Object getConsumer_name() {
            return consumer_name;
        }

        public void setConsumer_name(Object consumer_name) {
            this.consumer_name = consumer_name;
        }

        public String getContacts_mobile() {
            return contacts_mobile;
        }

        public void setContacts_mobile(String contacts_mobile) {
            this.contacts_mobile = contacts_mobile;
        }

        public String getContacts_name() {
            return contacts_name;
        }

        public void setContacts_name(String contacts_name) {
            this.contacts_name = contacts_name;
        }

        public int getCustom_string_status() {
            return custom_string_status;
        }

        public void setCustom_string_status(int custom_string_status) {
            this.custom_string_status = custom_string_status;
        }

        public Object getDecoration_budget() {
            return decoration_budget;
        }

        public void setDecoration_budget(Object decoration_budget) {
            this.decoration_budget = decoration_budget;
        }

        public Object getDecoration_style() {
            return decoration_style;
        }

        public void setDecoration_style(Object decoration_style) {
            this.decoration_style = decoration_style;
        }

        public Object getDesign_budget() {
            return design_budget;
        }

        public void setDesign_budget(Object design_budget) {
            this.design_budget = design_budget;
        }

        public Object getDetail_desc() {
            return detail_desc;
        }

        public void setDetail_desc(Object detail_desc) {
            this.detail_desc = detail_desc;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getEnd_day() {
            return end_day;
        }

        public void setEnd_day(String end_day) {
            this.end_day = end_day;
        }

        public Object getHouse_area() {
            return house_area;
        }

        public void setHouse_area(Object house_area) {
            this.house_area = house_area;
        }

        public Object getHouse_type() {
            return house_type;
        }

        public void setHouse_type(Object house_type) {
            this.house_type = house_type;
        }

        public String getIs_beishu() {
            return is_beishu;
        }

        public void setIs_beishu(String is_beishu) {
            this.is_beishu = is_beishu;
        }

        public String getIs_public() {
            return is_public;
        }

        public void setIs_public(String is_public) {
            this.is_public = is_public;
        }

        public String getLiving_room() {
            return living_room;
        }

        public void setLiving_room(String living_room) {
            this.living_room = living_room;
        }

        public int getNeeds_id() {
            return needs_id;
        }

        public void setNeeds_id(int needs_id) {
            this.needs_id = needs_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getWk_template_id() {
            return wk_template_id;
        }

        public void setWk_template_id(String wk_template_id) {
            this.wk_template_id = wk_template_id;
        }

        public Object getProject_status() {
            return project_status;
        }

        public void setProject_status(Object project_status) {
            this.project_status = project_status;
        }

        public Object getDesign_style() {
            return design_style;
        }

        public void setDesign_style(Object design_style) {
            this.design_style = design_style;
        }

        public Object getMain_demand() {
            return main_demand;
        }

        public void setMain_demand(Object main_demand) {
            this.main_demand = main_demand;
        }

        public Object getProject_type() {
            return project_type;
        }

        public void setProject_type(Object project_type) {
            this.project_type = project_type;
        }

        public Object getProject_start_time() {
            return project_start_time;
        }

        public void setProject_start_time(Object project_start_time) {
            this.project_start_time = project_start_time;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public Object getHs_uid() {
            return hs_uid;
        }

        public void setHs_uid(Object hs_uid) {
            this.hs_uid = hs_uid;
        }

        public Object getMember_id() {
            return member_id;
        }

        public void setMember_id(Object member_id) {
            this.member_id = member_id;
        }

        public Object getDesigner_name() {
            return designer_name;
        }

        public void setDesigner_name(Object designer_name) {
            this.designer_name = designer_name;
        }

        public Object getPkg_name() {
            return pkg_name;
        }

        public void setPkg_name(Object pkg_name) {
            this.pkg_name = pkg_name;
        }

        public Object getCustom_string_pkg_type() {
            return custom_string_pkg_type;
        }

        public void setCustom_string_pkg_type(Object custom_string_pkg_type) {
            this.custom_string_pkg_type = custom_string_pkg_type;
        }

        public List<BiddersBean> getBidders() {
            return bidders;
        }

        public void setBidders(List<BiddersBean> bidders) {
            this.bidders = bidders;
        }

        public static class BiddersBean {
            private String avatar;
            private String declaration;
            private Object delivery;
            private Object orders;
            private Object payment;
            private String status;
            private String uid;
            private Object experience;
            private int designer_id;
            private String design_thread_id;
            private String join_time;
            private String measurement_fee;
            private Object measure_time;
            private Object refused_time;
            private String selected_time;
            private String user_name;
            private String wk_cur_node_id;
            private Object wk_current_step_id;
            private String wk_cur_sub_node_id;
            private String wk_id;
            private Object style_names;
            private Object design_price_max;
            private Object design_price_min;
            private Object measurement_status;
            private Object mobile_number;
            private List<?> design_contract;
            private List<?> wk_next_possible_sub_node_ids;
            private List<?> wk_steps;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDeclaration() {
                return declaration;
            }

            public void setDeclaration(String declaration) {
                this.declaration = declaration;
            }

            public Object getDelivery() {
                return delivery;
            }

            public void setDelivery(Object delivery) {
                this.delivery = delivery;
            }

            public Object getOrders() {
                return orders;
            }

            public void setOrders(Object orders) {
                this.orders = orders;
            }

            public Object getPayment() {
                return payment;
            }

            public void setPayment(Object payment) {
                this.payment = payment;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public Object getExperience() {
                return experience;
            }

            public void setExperience(Object experience) {
                this.experience = experience;
            }

            public int getDesigner_id() {
                return designer_id;
            }

            public void setDesigner_id(int designer_id) {
                this.designer_id = designer_id;
            }

            public String getDesign_thread_id() {
                return design_thread_id;
            }

            public void setDesign_thread_id(String design_thread_id) {
                this.design_thread_id = design_thread_id;
            }

            public String getJoin_time() {
                return join_time;
            }

            public void setJoin_time(String join_time) {
                this.join_time = join_time;
            }

            public String getMeasurement_fee() {
                return measurement_fee;
            }

            public void setMeasurement_fee(String measurement_fee) {
                this.measurement_fee = measurement_fee;
            }

            public Object getMeasure_time() {
                return measure_time;
            }

            public void setMeasure_time(Object measure_time) {
                this.measure_time = measure_time;
            }

            public Object getRefused_time() {
                return refused_time;
            }

            public void setRefused_time(Object refused_time) {
                this.refused_time = refused_time;
            }

            public String getSelected_time() {
                return selected_time;
            }

            public void setSelected_time(String selected_time) {
                this.selected_time = selected_time;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getWk_cur_node_id() {
                return wk_cur_node_id;
            }

            public void setWk_cur_node_id(String wk_cur_node_id) {
                this.wk_cur_node_id = wk_cur_node_id;
            }

            public Object getWk_current_step_id() {
                return wk_current_step_id;
            }

            public void setWk_current_step_id(Object wk_current_step_id) {
                this.wk_current_step_id = wk_current_step_id;
            }

            public String getWk_cur_sub_node_id() {
                return wk_cur_sub_node_id;
            }

            public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
                this.wk_cur_sub_node_id = wk_cur_sub_node_id;
            }

            public String getWk_id() {
                return wk_id;
            }

            public void setWk_id(String wk_id) {
                this.wk_id = wk_id;
            }

            public Object getStyle_names() {
                return style_names;
            }

            public void setStyle_names(Object style_names) {
                this.style_names = style_names;
            }

            public Object getDesign_price_max() {
                return design_price_max;
            }

            public void setDesign_price_max(Object design_price_max) {
                this.design_price_max = design_price_max;
            }

            public Object getDesign_price_min() {
                return design_price_min;
            }

            public void setDesign_price_min(Object design_price_min) {
                this.design_price_min = design_price_min;
            }

            public Object getMeasurement_status() {
                return measurement_status;
            }

            public void setMeasurement_status(Object measurement_status) {
                this.measurement_status = measurement_status;
            }

            public Object getMobile_number() {
                return mobile_number;
            }

            public void setMobile_number(Object mobile_number) {
                this.mobile_number = mobile_number;
            }

            public List<?> getDesign_contract() {
                return design_contract;
            }

            public void setDesign_contract(List<?> design_contract) {
                this.design_contract = design_contract;
            }

            public List<?> getWk_next_possible_sub_node_ids() {
                return wk_next_possible_sub_node_ids;
            }

            public void setWk_next_possible_sub_node_ids(List<?> wk_next_possible_sub_node_ids) {
                this.wk_next_possible_sub_node_ids = wk_next_possible_sub_node_ids;
            }

            public List<?> getWk_steps() {
                return wk_steps;
            }

            public void setWk_steps(List<?> wk_steps) {
                this.wk_steps = wk_steps;
            }
        }
    }
}
