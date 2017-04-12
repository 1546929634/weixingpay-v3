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
		String sign=CommonUtil.createSign(ConfigUtil.ENCODING,map);//进行签名字符串构造
		map.put("sign", sign); // 将得到的签名添加在预支付请求的参数组里面
		return map;
	}

	public SortedMap<String, String> createUnifiedMap(String body, String order_id, String sum_price) {
		// 预处理的map集合中key一定要是全部小写英文
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", ConfigUtil.APPID); // 公众账号ID
		parameters.put("mch_id", ConfigUtil.PARTNER_ID); // 商户号
		parameters.put("nonce_str", commonUtil.getRandomStr()); // 随机字符串
		parameters.put("body", body); // 商品描述
		parameters.put("out_trade_no", order_id);// 商户订单号
		parameters.put("total_fee", unitConverter(sum_price)); // 总金额
		parameters.put("spbill_create_ip", ConfigUtil.SPBILL_CREATE_IP); // 订单生成的机器IP,测试IP	//TODO
		parameters.put("notify_url", ConfigUtil.NOTIFY_URL); // 通知地址,测试地址
		parameters.put("trade_type", ConfigUtil.TRADE_TYPE); // 交易类型(JSAPI、NATIVE、APP)

		String signStr =CommonUtil.createSign(ConfigUtil.ENCODING, parameters);// 进行签名字符串构造
		parameters.put("sign", signStr); // 将得到的签名添加在预支付请求的参数组里面
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

	//修改数据库订单状态
	public String toOrderState(String orderId) throws Exception{
		if(null != orderId && !orderId.equals("")){
			Gson gson = new Gson();
			Map<String,String > map = new HashMap<String, String>();
			map.put("orderId",orderId);
			//此处可以加入一些随机数验证
			String result = commonUtil.toPostRequest(ConfigUtil.SUCCESS_URL,map);
			if(null == result) throw new CheckedException("状态设置失败");
			Map<String ,String > resultMap = gson.fromJson(result,Map.class);
			if (null != resultMap && resultMap.get("state").equals("success")){
				return "OK";
			} else{
				throw new CheckedException(resultMap.get("msg"));
			}
		}
		throw new CheckedException("订单错误");
	}
}
