package org.vg.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.vg.dao.UserDAO;
import org.vg.entity.User;
import org.vg.exception.runtime.ResourceNotFoundException;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MessageSource messageSource; 
	
	@GetMapping("")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users = new ArrayList<User>(userDAO.getAllUser());
		List<Link> link = null;
		for(User user: users) {
			link = new ArrayList<Link>();
			link.add(linkTo(methodOn(this.getClass()).getUserById(user.getUserId())).withSelfRel());
			link.add(linkTo(methodOn(this.getClass()).getUserByEmail(user.getEmailId())).withSelfRel().withTitle("Search by email"));
			link.add(linkTo(methodOn(this.getClass()).deleteByEmail(user.getEmailId())).withSelfRel().withTitle("Delete Url"));
			user.add(link);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id){
		User user = userDAO.getUserById(id);
		List<Link> link = new ArrayList<Link>();
		link.add(linkTo(methodOn(this.getClass()).getAllUser()).withRel("all-users").withTitle("Fetch Users"));
		link.add(linkTo(methodOn(this.getClass()).getUserByEmail(user.getEmailId())).withSelfRel().withTitle("Search by email"));
		link.add(linkTo(methodOn(this.getClass()).deleteByEmail(user.getEmailId())).withSelfRel().withTitle("Delete User"));
		user.add(link);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<List<User>> getUserByEmail(@PathVariable String email){
		List<User> users = userDAO.getUserByEmail(email);
		if(users == null)
			throw new ResourceNotFoundException("No user by given email-"+email);
		List<Link> link = null;
		
		for(User user: users) {
			link = new ArrayList<Link>();
			link.add(linkTo(methodOn(this.getClass()).getUserById(user.getUserId())).withSelfRel());
			link.add(linkTo(methodOn(this.getClass()).deleteByEmail(user.getEmailId())).withSelfRel().withTitle("Delete Url"));
			user.add(link);
		}
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user){
		userDAO.addUser(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<List<User>> deleteByEmail(@PathVariable String email){
		if(userDAO.getUserByEmail(email) == null)
			throw new ResourceNotFoundException("No user by given email-"+email);
		return new ResponseEntity<List<User>>(userDAO.deleteByEmail(email), HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>(messageSource.getMessage("test", null, "Not Working", LocaleContextHolder.getLocale()), HttpStatus.OK);
	}
}
