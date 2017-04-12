package com.ws.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

public class BaseExceptionController {
	
	private Gson gosn;
	
	@ExceptionHandler
	@ResponseBody
    public Object handleException(Exception t) {
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("state", "error");
        data.put("msg", t.getMessage());
        data.put("excetion", t.getClass().getSimpleName());
        Gson gson = new Gson();
        return gson.toJson(data);
    }
	
	protected Object success(String msg){
		return success(msg,null);
	}
	
	protected Object success(String msg,Map<String, Object> data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		if(null != msg)
			map.put("msg", msg);
		if(null != data && data.size() > 0)
			map.putAll(data);
        Gson gson = new Gson();
        return gson.toJson(map);
	}	
	
	protected String toJson(Object obj){
		if(null == gosn)
			gosn = new Gson();
		return gosn.toJson(obj);
	}
}
