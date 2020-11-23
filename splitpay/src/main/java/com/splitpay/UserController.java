package com.splitpay;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class UserController {

	//users
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HttpSession httpSession;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean validate() {
		return (httpSession.getAttribute("userId") == null) ? false : true;
	}
	
	//@CrossOrigin
	@PostMapping("/signup")
	public User signUp(@RequestBody User user) {
		try {
			user = userRepository.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		if (user.getUserId() != 0) {
			return user;
		}
		return null;
	}
	//@CrossOrigin
	@PostMapping("/signin")
	public User signIn(@RequestBody User user) {
		User dbUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

		if (dbUser != null && dbUser.getUsername().equals(user.getUsername())
				&& dbUser.getPassword().equals(user.getPassword())) 
		{
			httpSession.setAttribute("userId", dbUser.getUserId());
			return dbUser;
		}
		return null;
	}	
	
}