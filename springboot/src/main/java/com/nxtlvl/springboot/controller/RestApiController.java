/**
 * @author kumaraavelr
 *
 */
package com.nxtlvl.springboot.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.nxtlvl.springboot.model.User;
import com.nxtlvl.springboot.service.UserService;
import com.nxtlvl.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single User------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") String id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user .equals(null)) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);
	  
		String sDate1=user.getBirthDate();  
	    Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(sDate1);
		} catch (ParseException e) {
			logger.error("Date format error. Please provide the valid date format like dd-MMM-yyyy", sDate1);
			return new ResponseEntity(new CustomErrorType("Unable to create the record. Date format error. Please provide the valid date format like dd-MMM-yyyy"
					+ "Given date " + sDate1 + " Not valid."),HttpStatus.NOT_ACCEPTABLE);
		}  
	    System.out.println(sDate1+"\t"+date1); 
	    if (userService.isUserEmailExist(user)) {
			logger.error("Unable to create. A User with email {} already exist", user.getEmail());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with email " + 
			user.getEmail() + " already exist."),HttpStatus.CONFLICT);
		}else if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getfName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			user.getfName() + " already exist."),HttpStatus.CONFLICT);
		}
		
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User ------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);

		User currentUser = userService.findById(id);

		if (currentUser.equals(null)) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		} 

		currentUser.setfName(user.getfName());
		currentUser.setlName(user.getlName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPinCode(user.getPinCode());
		currentUser.setBirthDate(user.getBirthDate());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
		logger.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}