package com.rong.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.rong.action.IUserAction;
import com.rong.model.User;
import com.rong.service.IUserService;

@Component
public class UserAction implements IUserAction {
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void add(User user) {
		userService.add(user);
	}

	@Override
	public void delete(int id) {
		userService.delete(id);
	}

	@Override
	public void update(User user) {
		userService.update(user);
	}

	@Override
	public List<User> query(int id) {
		return userService.query(id);
	}

}
