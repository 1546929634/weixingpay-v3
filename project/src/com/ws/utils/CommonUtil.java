package com.ws.utils;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import java.util.*;

/**
 * 通用工具类
 * @author wsl
 * @date 2014-11-21下午9:10:30
 */
public class CommonUtil {
	public static CloseableHttpClient httpclient;

	static{
		httpclient = HttpClients.createDefault();
	}

	/**
	 * 生成时间戳
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * @author wsl
	 * @date 2014-12-5下午2:29:34
	 * @Description：sign签名
	 * @param characterEncoding 编码格式
	 * @param parameters 请求参数
	 * @return
	 */
	public static String createSign(String characterEncoding,SortedMap<String,String> parameters){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + ConfigUtil.API_KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	/**
	 * getRandomStr:随机生成16位字符串. <br/>
	 *
	 * @author liangke
	 * @return
	 * @since JDK 1.6
	 */
	public String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public String toPostRequest(String url,String xmlParam){
		HttpPost httpPost = HttpClientConnectionManager.getPostMethod(url);
		try {
			httpPost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toPostRequest(String url,Map<String,String> paramMap){
		HttpPost httpPost = new HttpPost(url);
		try {
			//拼接参数
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			Iterator iterator = paramMap.entrySet().iterator();
			while (iterator.hasNext()){
				Map.Entry<String,String> entity = (Map.Entry)iterator.next();
				String key = entity.getKey();
				String val = entity.getValue();
				nvps.add(new BasicNameValuePair(key, val));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpclient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public void toGetRequest(String url){
//		HttpGet httpGet = new HttpGet(url);
//		//建立的http连接，仍旧被response1保持着，允许我们从网络socket中获取返回的数据
//		//为了释放资源，我们必须手动消耗掉response1或者取消连接（使用CloseableHttpResponse类的close方法）
//		//http://blog.csdn.net/leeo1010/article/details/41801509
//		try {
//			CloseableHttpResponse response1 = httpclient.execute(httpGet);
//			System.out.println(response1.getStatusLine());
//			HttpEntity entity1 = response1.getEntity();
//			// do something useful with the response body
//			// and ensure it is fully consumed
//			EntityUtils.consume(entity1);
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//	}
}