package com.rong.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.rong.model.AccessToken;
import com.rong.model.Button;
import com.rong.model.ClickButton;
import com.rong.model.Menu;
import com.rong.model.ViewButton;

public class WeChatUtils {
	private static final String APPID = "wxe846dd9c2d13e64e";
	private static final String APPSECRET = "8c9524d242f8569c329fa8696b37a1bd";
	private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 通过get方法获取数据
	 * 
	 * @return
	 */
	public static JSONObject doGetData(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				jsonObject = JSONObject.parseObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * post方法获取数据
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static JSONObject doPostData(String url,String data){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(data,"UTF-8"));
		JSONObject jsonObject = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				String result = EntityUtils.toString(httpEntity);
				jsonObject = JSONObject.parseObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 获取AccessToken
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetData(url);
		if (jsonObject != null) {
			accessToken.setAccess_token(jsonObject.getString("access_token"));
			accessToken.setExpires_in(jsonObject.getIntValue("expires_in"));
		}
		return accessToken;
	}

	/**
	 * 生成菜单
	 * 
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		ClickButton btn11 = new ClickButton();
		btn11.setName("主菜单");
		btn11.setType("click");
		btn11.setKey("11");

		ViewButton btn21 = new ViewButton();
		btn21.setName("百度一下");
		btn21.setType("view");
		btn21.setUrl("https://www.baidu.com");

		ClickButton btn31 = new ClickButton();
		btn31.setName("扫一扫");
		btn31.setType("scancode_waitmsg");
		btn31.setKey("31");

		ClickButton btn32 = new ClickButton();
		btn32.setName("打开地图");
		btn32.setType("location_select");
		btn32.setKey("32");

		Button button = new Button();
		button.setName("工具类");
		button.setType("click");

		button.setSub_button(new Button[] { btn31, btn32 });
		menu.setButton(new Button[] { btn11, btn21, button });
		return menu;
	}

	/**
	 * 创建菜单
	 * @param accessToken
	 * @param menu
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static int createMenu(String accessToken, String menu){
		int errcode=-1;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = doPostData(url,menu);
		if (jsonObject != null) {
			errcode=jsonObject.getInteger("errcode");
		}
		return errcode;
	}
}
