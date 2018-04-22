package org.vg.entity;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



/**
 * User entity has property user id which is unique and auto generated.
 * Using java validation API for validation
 * Extends ResourceSupport class for supporting hateoas
 * This class is immutable
 *
 * @author Vishnu Gupta
 *
 */
@ApiModel("User Entity is Immutable")
public final class User extends ResourceSupport{

	@ApiModelProperty("User Id")
	private final int userId;
	
	@ApiModelProperty("Minimum 2 character needed")
	@Size(min = 2, message="Name size should have atleast 2 character")
	@NotEmpty(message= "Name cannot be empty")
	private final String name;
	
	@ApiModelProperty("Date of Birth should be in past")
	@Past(message="Date of Birth should be past date")
	private final LocalDate dateOfBirth;
	
	@ApiModelProperty(notes="Email cannot be empty")
	@Email
	@NotEmpty(message= "Email cannot be empty")
	private final String emailId;

	@JsonCreator
	public User(@JsonProperty("userId") final int userId, @JsonProperty("name") final String name,
			@JsonProperty("dateOfBirth") final LocalDate dateOfBirth, @JsonProperty("emailId") final String emailId) {
		super();
		this.userId = userId;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.emailId = emailId;
	}

	public int getUserId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getEmailId() {
		return emailId;
	}

}
