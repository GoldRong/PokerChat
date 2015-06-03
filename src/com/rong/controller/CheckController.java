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
	 * ��֤
	 * @param req
	 * @param res
	 * @return
	 */
    @RequestMapping(method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8") 
	@ResponseBody
	public String checkToken(HttpServletRequest req,HttpServletResponse res) {
		// ΢�ż���ǩ��
		String signature = req.getParameter("signature");
		// ʱ��¾
		String timestamp = req.getParameter("timestamp");
		// �����
		String nonce = req.getParameter("nonce");
		// ����ַ���
		String echostr = req.getParameter("echostr");

		// ͨ������ signature ���������У�飬��У��ɹ���ԭ������ echostr����ʾ����ɹ����������ʧ��
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			System.out.println("��֤ͨ��");
			return echostr;
		}
		System.out.println("��֤ͨ����");
		return null;
	}
    /**
     * ������Ϣ
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
    			textMessage.setContent("�����͵���Ϣ�ǣ�"+content);
    			String info=MessageUtils.textMessage2Xml(textMessage);
    			return info;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
}
