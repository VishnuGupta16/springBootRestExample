package org.vg.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.vg.entity.User;
import org.vg.exception.runtime.ResourceNotFoundException;

@Service
public class UserDAO {
	
	private static Map<Integer, User> users = null;
	private static int counter = 0;
	
	static {
		users = new HashMap<Integer,User>();
		users.put(++counter,new User(counter, "Vishnu Gupta", LocalDate.of(1990, 8, 16), "guptavishnunitsk@gmail.com"));
		users.put(++counter,new User(counter, "Born Bad", LocalDate.of(1990, 8, 16), "devil@gmail.com"));
	}
	
	public Collection<User> getAllUser(){
		return new ArrayList<User>(users.values());
	}
	
	public User getUserById(int id) {
		if(!users.containsKey(id))
			throw new ResourceNotFoundException(String.format("user by given id- %d not found", id));
		return new User(users.get(id).getUserId(), users.get(id).getName(), users.get(id).getDateOfBirth(), users.get(id).getEmailId());
	}
	
	public  List<User> getUserByEmail(String emailId) {
		
		List<User> userWithGivenEmailId = null;
		
		for(User user: getAllUser()) {
			if(user.getEmailId().toLowerCase().compareTo(emailId.toLowerCase()) == 0) {
				if(userWithGivenEmailId == null)
					userWithGivenEmailId = new ArrayList<User>();
				userWithGivenEmailId.add(new User(user.getUserId(), user.getName(), user.getDateOfBirth(), user.getEmailId()));
			}	
		}
		return userWithGivenEmailId;
	}
	
	public User addUser(User user) {
		users.put(++counter,new User(counter, user.getName(), user.getDateOfBirth(), user.getEmailId()));
		return user;
	}
	
	public List<User> deleteByEmail(String email) {
		Collection<User> users = getAllUser();
		List<User> deletedUser = new ArrayList<User>();
		if(users != null) {
			Iterator<User> iterator = users.iterator();
			while(iterator.hasNext()) {
				if(iterator.next().getEmailId().equalsIgnoreCase(email)) {
					deletedUser.add(iterator.next());
					iterator.remove();
				}
			}
		}
		return deletedUser;
	}
	
}
