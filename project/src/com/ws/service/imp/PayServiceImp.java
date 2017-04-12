package com.ws.service.imp;

import com.google.gson.Gson;
import com.ws.service.PayService;
import com.ws.utils.CheckedException;
import com.ws.utils.CommonUtil;
import com.ws.utils.ConfigUtil;
import com.ws.utils.XMLUtil;
import org.jdom2.Element;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PayServiceImp implements PayService {

	private CommonUtil commonUtil = new CommonUtil();

	public SortedMap<String, String> createPayMap(String prepayid){
		String timestamp = commonUtil.getTimeStamp();
		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("appid", ConfigUtil.APPID);
		map.put("partnerid", ConfigUtil.PARTNER_ID);
		map.put("prepayid", prepayid);
		map.put("package", "Sign=WXPay");
		map.put("noncestr", commonUtil.getRandomStr());
		map.put("timestamp", timestamp);
		String sign=CommonUtil.createSign(ConfigUtil.ENCODING,map);//����ǩ���ַ�������
		map.put("sign", sign); // ���õ���ǩ�������Ԥ֧������Ĳ���������
		return map;
	}

	public SortedMap<String, String> createUnifiedMap(String body, String order_id, String sum_price) {
		// Ԥ�����map������keyһ��Ҫ��ȫ��СдӢ��
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", ConfigUtil.APPID); // �����˺�ID
		parameters.put("mch_id", ConfigUtil.PARTNER_ID); // �̻���
		parameters.put("nonce_str", commonUtil.getRandomStr()); // ����ַ���
		parameters.put("body", body); // ��Ʒ����
		parameters.put("out_trade_no", order_id);// �̻�������
		parameters.put("total_fee", unitConverter(sum_price)); // �ܽ��
		parameters.put("spbill_create_ip", ConfigUtil.SPBILL_CREATE_IP); // �������ɵĻ���IP,����IP	//TODO
		parameters.put("notify_url", ConfigUtil.NOTIFY_URL); // ֪ͨ��ַ,���Ե�ַ
		parameters.put("trade_type", ConfigUtil.TRADE_TYPE); // ��������(JSAPI��NATIVE��APP)

		String signStr =CommonUtil.createSign(ConfigUtil.ENCODING, parameters);// ����ǩ���ַ�������
		parameters.put("sign", signStr); // ���õ���ǩ�������Ԥ֧������Ĳ���������
		return parameters;
	}

	private String unitConverter(String price){
		if(null != price && price.indexOf(".") > -1){
			Double d_price = Double.parseDouble(price);
			return String.valueOf(d_price * 100);
		}
		return price;
	}

	public String  getPrepayId(String xmlParam){
		String prepay_id = "";
		String result = commonUtil.toPostRequest(ConfigUtil.UNIFIED_ORDER_URL,xmlParam);
		if(null == result) return prepay_id;
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			if(result.indexOf("FAIL")==-1) {
				Map<String, String> map = XMLUtil.doXMLParse(result);
				if (null != map && null != map.get("return_code") && "SUCCESS".equalsIgnoreCase(map.get("return_code"))) {
					if (null != map.get("result_code") && "SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
						prepay_id = map.get("prepay_id").toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}

	//�޸����ݿⶩ��״̬
	public String toOrderState(String orderId) throws Exception{
		if(null != orderId && !orderId.equals("")){
			Gson gson = new Gson();
			Map<String,String > map = new HashMap<String, String>();
			map.put("orderId",orderId);
			//�˴����Լ���һЩ�������֤
			String result = commonUtil.toPostRequest(ConfigUtil.SUCCESS_URL,map);
			if(null == result) throw new CheckedException("״̬����ʧ��");
			Map<String ,String > resultMap = gson.fromJson(result,Map.class);
			if (null != resultMap && resultMap.get("state").equals("success")){
				return "OK";
			} else{
				throw new CheckedException(resultMap.get("msg"));
			}
		}
		throw new CheckedException("��������");
	}
}
