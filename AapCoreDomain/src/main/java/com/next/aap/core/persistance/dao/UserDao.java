package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.User;


public interface UserDao {

	/**
	 * Creates/updates a user in Database
	 * 
	 * @param user
	 * @return saved user
	 * @throws AppException
	 */
	public abstract User saveUser(User user);

	/**
	 * deletes a user in Database
	 * 
	 * @param user
	 * @return updated user
	 * @throws AppException
	 */
	public abstract void deleteUser(User user);

	/**
	 * return a User with given primary key/id
	 * 
	 * @param id
	 * @return User with PK as id(parameter)
	 * @throws AppException
	 */
	public abstract User getUserById(Long id);

	/**
	 * @return search result
	 * @throws AppException
	 */
	public abstract List<User> searchUsers();

	/**
	 * 
	 * @return list of all Users
	 */
	public abstract List<User> getAllUsers();

	/**
	 * Search user by Email
	 * @param userEmail
	 * @return user or null if not found
	 */
	public abstract User getUserByEmail(String userEmail);
	
	/**
	 * Search user by Membership Number
	 * @param userMembershipNumber
	 * @return user or null if not found
	 */
	public abstract User getUserByMembershipNumber(String membershipNumber);


	/**
	 * Search user by Passport Number
	 * @param userPassportNumber
	 * @return user or null if not found
	 */
	public abstract User getUserByPassportNumber(String passportNumber);


	List<User> searchUserOfAssemblyConstituency(String name,Long livingAcId,Long votingAcId);

}
