package com.ws.utils;

public class ConfigUtil {

    public final static String APPNAME = "";
	public final static String APPID = "";//����ŵ�Ӧ�ú�
	public final static String NOTIFY_URL =	"";		//֪ͨ��ַ
	public final static String ENCODING = "UTF-8";
	public final static String API_KEY = "";//API��Կ
	public final static String PARTNER_ID = "";	//΢��֧��������̻���
	public final static String SIGN_TYPE = "MD5";//ǩ�����ܷ�ʽ
	public final static String TRADE_TYPE = "APP";	//��������

	public final static String SPBILL_CREATE_IP = "";//spbill_create_ip �û���ʵ��ip  ��Ҫͨ�������ȡ

    public final static String SUCCESS_URL = "";//�����޸Ķ���״̬�ĵ�ַ

	/**
	 * ΢��֧���ӿڵ�ַ
	 */
	//΢��֧��ͳһ�ӿ�(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//΢���˿�ӿ�(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//������ѯ�ӿ�(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//�رն����ӿ�(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	//�˿��ѯ�ӿ�(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//���˵��ӿ�(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	//������ת���ӿ�(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	//�ӿڵ����ϱ��ӿ�(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";

}
