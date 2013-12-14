package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.UserDao;

@Repository
public class UserDaoHibernateSpringImpl extends BaseDaoHibernateSpring<User> implements UserDao{


	@Override
	public User saveUser(User user) {
		getCurrentSession().save(user);
		return user;
	}

	@Override
	public void deleteUser(User user) {
		getCurrentSession().delete(user);
	}

	@Override
	public User getUserById(Long id) {
		return (User)getObjectById(User.class, id);
	}

	@Override
	public List<User> searchUsers() {
		return getAll(User.class);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String userEmail) {
		if(userEmail == null){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("emailUp", userEmail.toUpperCase());
		String query = "from User where emailUp=:emailUp";
		return executeQueryGetObject(query, params);
	}

	@Override
	public User getUserByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}
}
