/**
 * @author kumaraavelr
 *
 */
package com.nxtlvl.springboot.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.nxtlvl.springboot.model.User;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<User> users;
	
	static{
		users= populateDummyUsers();
	}

	public List<User> findAllUsers() {
		return users;
	}
	
	public User findById(String id) {
		for(User user : users){
			if(user.getId().equals(id)){
				return user;
			}
		}
		return null;
	}
	
	public User findByName(String fName) {
		for(User user : users){
			if(user.getfName().equalsIgnoreCase(fName)){
				return user;
			}
		}
		return null;
	}
	
	public User findByEmail(String email) {
		for(User user : users){
			if(user.getEmail().equalsIgnoreCase(email)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet()+"");
		users.add(user);
	}

	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(String id) {
		
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User user = iterator.next();
		    if (user.getId().equals(id)) {
		    	user.setActive(false);
		    }
		}
	}

	public boolean isUserExist(User user) {
		return findByName(user.getfName())!=null;
	}
	
	public boolean isUserEmailExist(User user) {
		return findByEmail(user.getEmail())!=null;
	}
	
	public boolean validateDate(User user) {
		return findByEmail(user.getEmail())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	private static List<User> populateDummyUsers(){
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet()+"","Johns","Smith","John.Smith@gmail.com",600045,"02-MAR-1980"));
		users.add(new User(counter.incrementAndGet()+"","Tom","Anderson","Tom.Anderson@gmail.com",600045, "05-MAR-1982"));
		users.add(new User(counter.incrementAndGet()+"","Jerome","Chris","Jerome@gmail.com",600045, "01-JUNE-1990"));
		users.add(new User(counter.incrementAndGet()+"","Silvia","Holly","Silvia.Holly@gmail.com",600045,"02-JULY-1980"));
		return users;
	}

}

