package com.rong.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rong.model.TextMessage;
import com.rong.utils.MessageUtils;
import com.rong.utils.SignUtil;

@Controller
@RequestMapping(value = "checkToken")
public class CheckController {
	/**
	 * 验证
	 * @param req
	 * @param res
	 * @return
	 */
    @RequestMapping(method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8") 
	@ResponseBody
	public String checkToken(HttpServletRequest req,HttpServletResponse res) {
		// 微信加密签名
		String signature = req.getParameter("signature");
		// 时间戮
		String timestamp = req.getParameter("timestamp");
		// 随机数
		String nonce = req.getParameter("nonce");
		// 随机字符串
		String echostr = req.getParameter("echostr");

		// 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			System.out.println("验证通过");
			return echostr;
		}
		System.out.println("验证通不过");
		return null;
	}
    /**
     * 处理消息
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8") 
	@ResponseBody
	public String handleMessage(HttpServletRequest req,HttpServletResponse res) {
    	try{
    		Map<String,String> map=MessageUtils.xml2Map(req.getInputStream());
    		String fromUserName=map.get("FromUserName");
    		String toUserName=map.get("ToUserName");
    		String msgType=map.get("MsgType");
    		String content=map.get("Content");
    		if(msgType.equals("text")){
    			TextMessage textMessage=new TextMessage();
    			textMessage.setFromUserName(toUserName);
    			textMessage.setToUserName(fromUserName);
    			textMessage.setMsgType("text");
    			textMessage.setCreateTime(new Date().getTime());
    			textMessage.setContent("您发送的消息是："+content);
    			String info=MessageUtils.textMessage2Xml(textMessage);
    			return info;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
}
