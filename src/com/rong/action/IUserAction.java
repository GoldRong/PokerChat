package com.rong.action;

import java.util.List;

import com.rong.model.User;

public interface IUserAction {
	public void add(User user);
	public void delete(int id);
	public void update(User user);
	public List<User> query(int id);
}
