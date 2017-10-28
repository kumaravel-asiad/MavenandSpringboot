/**
 * 
 */
/**
 * @author kumaraavelr
 *
 */
package com.nxtlvl.springboot.service;


import java.util.List;

import com.nxtlvl.springboot.model.User;

public interface UserService {
	
	User findById(String id);
	
	User findByName(String name);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(String id);

	List<User> findAllUsers();
	
	void deleteAllUsers();
	
	boolean isUserExist(User user);

	boolean isUserEmailExist(User user);
	
	boolean validateDate(User user);
	
}