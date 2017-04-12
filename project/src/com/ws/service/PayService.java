package com.ws.service;

import java.util.Map;
import java.util.SortedMap;

public interface PayService {

	public Map<String,String> createPayMap(String prepayid);

	public SortedMap<String,String> createUnifiedMap(String body, String order_id, String sum_price);

	public String  getPrepayId(String xmlParam);

	public String toOrderState(String orderId) throws Exception;
	
}
