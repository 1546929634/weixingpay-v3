package com.ws.controller;

import com.ws.service.PayService;
import com.ws.utils.CommonUtil;
import com.ws.utils.ConfigUtil;
import com.ws.utils.XMLUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.SortedMap;

/**
 * Created by wensl on 2016/11/23.
 * 接受微信回调
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {

    @Resource
    private PayService payService;

    @ResponseBody
    @RequestMapping(value = "/callBack")
    public String notify(HttpServletRequest request){
        String return_code = "FAIL";
        String return_msg = "参数错误";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            if(!"".equals(sb)){
                SortedMap<String,String> map = XMLUtil.doXMLParse(sb.toString());
                String signStr = CommonUtil.createSign(ConfigUtil.ENCODING, map);// 进行签名字符串构造
                if(map.get("sign").equals(signStr)){
                    String result = payService.toOrderState(map.get("out_trade_no"));
                    if(null != result && "OK".equals(result)){
                        return_code = "SUCCESS";
                        return_msg = "ok";
                    }
                }
            }
        } catch (Exception e) {
            return_msg = e.getMessage();
        } finally {
            return XMLUtil.setXML(return_code,return_msg);
        }
    }
}
