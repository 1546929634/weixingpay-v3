package com.ws.utils;

public class ConfigUtil {

    public final static String APPNAME = "";
	public final static String APPID = "";//服务号的应用号
	public final static String NOTIFY_URL =	"";		//通知地址
	public final static String ENCODING = "UTF-8";
	public final static String API_KEY = "";//API密钥
	public final static String PARTNER_ID = "";	//微信支付分配的商户号
	public final static String SIGN_TYPE = "MD5";//签名加密方式
	public final static String TRADE_TYPE = "APP";	//交易类型

	public final static String SPBILL_CREATE_IP = "";//spbill_create_ip 用户端实际ip  需要通过程序获取

    public final static String SUCCESS_URL = "";//用于修改订单状态的地址

	/**
	 * 微信支付接口地址
	 */
	//微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	//退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	//短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	//接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";

}
