package com.cakir.web.dto;

import javax.validation.constraints.NotEmpty;
import com.cakir.model.Ort;
import com.cakir.validation.FieldMatch;
import com.cakir.validation.ValidEmail;
import com.cakir.validation.ValidPassword;



@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "label.error.emailconfirm"),
    @FieldMatch(first = "email", second = "confirmEmail", message = "label.error.emailconfirm")
})
public class UserRegistrationDto {

	@NotEmpty(message = "label.error.notempty")
    private String firstName;

    @NotEmpty(message = "label.error.notempty")
    private String lastName;

    
    
    @NotEmpty(message = "label.error.notempty")
    @ValidPassword
    private String password;

    
    @NotEmpty(message = "label.error.notempty")
    @ValidPassword
    private String confirmPassword;

    @ValidEmail
    @NotEmpty(message = "label.error.notempty")
    private String email;

    @ValidEmail
    @NotEmpty(message = "label.error.notempty")
    private String confirmEmail;
    
    
    private Long ort;

    
    private Integer role;
    
    private boolean isUsing2FA;
    
    public Long getOrt() {
		return ort;
	}

	public void setOrt(Long ort) {
		this.ort = ort;
	}

	

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public boolean isUsing2FA() {
		return isUsing2FA;
	}

	public void setUsing2FA(boolean isUsing2FA) {
		this.isUsing2FA = isUsing2FA;
	}

	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRegistrationDto [firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", confirmPassword=");
		builder.append(confirmPassword);
		builder.append(", email=");
		builder.append(email);
		builder.append(", confirmEmail=");
		builder.append(confirmEmail);
		builder.append(", ort=");
		builder.append(ort);
		builder.append(", role=");
		builder.append(role);
		builder.append(", isUsing2FA=");
		builder.append(isUsing2FA);
		builder.append("]");
		return builder.toString();
	}

   

	
    
    
}
