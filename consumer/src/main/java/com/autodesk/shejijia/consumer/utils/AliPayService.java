package com.autodesk.shejijia.consumer.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.autodesk.shejijia.shared.components.common.utility.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author yonghanliu .
 * @version v1.0 .
 * @date 2016-6-13 .
 * @file AliPayService.java .
 * @brief 支付宝支付执行的操作 .
 */
public class AliPayService {

    AliPayActionStatus AliCallBack;

    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMbDgiRrWNBmgIe+IIEKV5UoyCDRjIO6UZulU5l6Tc9PClbw/wBtVNWdtdKcUNF1psAeFBmKMuSsy/5eYnPrEDQfKRHC31o+NAYSj3U7Lwjmx/0UL+nq56620YdI+RLGpbr4Z3jPN/10Ox7qKa1Zju1hmD40Pzak795yg4Sn9KXvAgMBAAECgYAUMuIS2WXZJ9F/+e5LrsVfvxk3XJQu/sw0SQTJ4AfwPVQLqcoAPRwV6UUE9iWwY4hilavtPIIXgwfn/ad7qDOpKnPhhDXFF/koR8q1n6wOzycLgWBLchwKxc+twipXcJ0C5Cg5ZVaRtiZSSJIdpm48WeAXWj/GESeK9cKqM36MAQJBAOgRaDBqSIU25dcYJYVFgVOyoZOFjA/n2umIrGsceuvyzKy6B1DieIl1gqnSkQLZ8r2Q3T0tOhH0yjv0RSr9moECQQDbQt5OXy+jORSCsWA5dfcTjOAtB/iX1CCbhyRKQRYgVQGfqTt3Q0JQZuGjCOsd0ovvCyzoCTXjfGGnqO0lmqhvAkA86ozR4KRGq6Frc8AtmpAXG1XEdpLMfgz9rk2sFB7EHsjRMkfHWJtRYdI5p7c+610Hm6Ynb97FZd9MG5OodEeBAkAwLUErdz7AXopjLRY3ifQAF7QqMNYuhi2j/s26gxKZiBQTQNwQGHc5s2FgsVT3+ItGuu3jDiMJGQtcC4IQASuBAkEAz6mhDzSq4M1eB9Dggh2iPQusXM2f4UBph0MjxQzuoVPZ0L7KHyOlUQ1ipDBQsV2W5DVGeSDijzfFa9gEgdtUDA==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    String strPayInfo = "";

    public AliPayService(String Seller, String Partner, String ProductName, String amount, String notifyUrl, String Description, String tradeNO) {

        String OrderInfo = getOrderInfo(Seller, Partner, ProductName, amount, notifyUrl, Description, tradeNO);

        String sign = sign(OrderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        strPayInfo = OrderInfo + "&sign=\"" + sign + "\"&" + getSignType();
    }
    //AliCallBack

    public void SetCallBack(AliPayActionStatus callBack) {
        AliCallBack = callBack;
    }

    public interface AliPayActionStatus {
        void onOK();

        void onFail();
    }

    public void DoPayment(final Activity CurActivity) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(CurActivity);
                // 调用支付接口，获取支付结果
                String result = payTask.pay(strPayInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (AliCallBack != null)
                            AliCallBack.onOK();
                        //Toast.makeText(CurActivity, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            // Toast.makeText(FlowMeasureCostActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            if (AliCallBack != null)
                                AliCallBack.onFail();
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            // Toast.makeText(FlowMeasureCostActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String Seller, String Partner, String ProductName, String amount, String notifyUrl, String Description, String tradeNO) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Partner + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Seller + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + tradeNO + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + ProductName + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + Description + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + amount + "\"";

        // 服务器异步通知页面路径
        // notifyURL
        orderInfo += "&notify_url=" + "\"" + notifyUrl + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
