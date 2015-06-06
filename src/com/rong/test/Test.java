package com.rong.test;

import com.alibaba.fastjson.JSONObject;
import com.rong.model.AccessToken;
import com.rong.utils.WeChatUtils;


public class Test {

	public static void main(String[] args) {
		AccessToken accessToken=WeChatUtils.getAccessToken();
		String menu=JSONObject.toJSONString(WeChatUtils.initMenu());
		int result=WeChatUtils.createMenu(accessToken.getAccess_token(), menu);
		if(result==0){
			System.out.println("菜单创建成功！");
		}else{
			System.out.println("菜单创建失败！result="+result);
		}
	}

}
