package com.rong.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.rong.dao.IUserDao;
import com.rong.model.User;
@Component
public class UserDao implements IUserDao{
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate=new JdbcTemplate(dataSource);
	}

	@Override
	public void add(User user) {
		System.out.println(user.toString());
		int i=jdbcTemplate.update("insert into user(uid,uname,upass) values(?,?,?)",user.getUid(),user.getUname(),user.getUpass());
		if(i==1){
			System.out.println("��ӳɹ�");
		}else{
			System.out.println("���ʧ��");
		}
	}

	@Override
	public void delete(int id) {
		System.out.println(id);
		int i=jdbcTemplate.update("delete from user where uid=?",id);
		if(i==1){
			System.out.println("ɾ���ɹ�");
		}else{
			System.out.println("ɾ��ʧ��");
		}
	}

	@Override
	public void update(User user) {
		System.out.println(user);
		int i=jdbcTemplate.update("update user set uname=?,upass=? where uid=?",user.getUname(),user.getUpass(),user.getUid());
		if(i==1){
			System.out.println("���³ɹ�");
		}else{
			System.out.println("����ʧ��");
		}
	}

	@Override
	public List<User> query(int id) {
		System.out.println(id);
		List<User> list=jdbcTemplate.query("select * from user where uid="+id, new User());
		return list;
	}
}
