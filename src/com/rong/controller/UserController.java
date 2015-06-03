package com.rong.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rong.action.impl.UserAction;
import com.rong.model.User;

@Controller
@RequestMapping(value="user")
public class UserController{
	public UserAction userAction;
	
	public UserAction getUserAction() {
		return userAction;
	}

	@Resource
	public void setUserAction(UserAction userAction) {
		this.userAction = userAction;
	}

	@RequestMapping(value="addUser",params="json")
	@ResponseBody
	public String addUser(HttpServletRequest req){
		String json=req.getParameter("json");
		User user=JSONObject.parseObject(json, User.class);
		userAction.add(user);
		return user.getUname()+"=="+user.getUpass();
	}

	@RequestMapping(value="deleteUser")
	@ResponseBody
	public String deleteUser(HttpServletRequest req){
		String json=req.getParameter("json");
		int id=JSONObject.parseObject(json).getInteger("uid");
		userAction.delete(id);
		return ""+id;
	}

	@RequestMapping(value="updateUser")
	@ResponseBody
	public String updateUser(HttpServletRequest req){
		String json=req.getParameter("json");
		User user=JSONObject.parseObject(json, User.class);
		userAction.update(user);
		return user.getUid()+"=="+user.getUname()+"=="+user.getUpass();
	}

	@RequestMapping(value="queryUser")
	@ResponseBody
	public String queryUser(HttpServletRequest req){
		String json=req.getParameter("json");
		int id=JSONObject.parseObject(json).getInteger("uid");
		return ""+JSONArray.toJSONString(userAction.query(id));
	}
}
