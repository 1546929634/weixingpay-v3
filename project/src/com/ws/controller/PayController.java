package com.ws.controller;

import com.ws.service.PayService;
import com.ws.utils.ConfigUtil;
import com.ws.utils.XMLUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.SortedMap;

/**
 * Created by xiao4 on 2016/11/19.
 */
@Controller
@RequestMapping(value = "/pay")
public class PayController extends BaseExceptionController {

    @Resource
    private PayService payService;

    @ResponseBody
    @RequestMapping(value = "uniformOrder",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Object uniformOrder(String orderId,String description,String sunPrice){

        String body = ConfigUtil.APPNAME+"-"+description;

        return createOrder(body, orderId, sunPrice);
    }

    private String createOrder(String body, String order_id, String sum_price) {

        SortedMap<String, String> parameters = payService.createUnifiedMap(body, order_id, sum_price);

        String requestXML = XMLUtil.getRequestXml(parameters);//生成预处理请求xml

        String prepay_id = payService.getPrepayId(requestXML);//获取预支付订单id

        if(!"".equals(prepay_id)){
             return toJson(payService.createPayMap(prepay_id));//组装支付订单所需信息,返回给app 使app调起微信支付
        }
        throw new com.ws.utils.CheckedException("订单提交失败!");
    }

}
