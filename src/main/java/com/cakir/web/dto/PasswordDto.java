package com.cakir.web.dto;

import com.cakir.validation.FieldMatch;
import com.cakir.validation.PasswordMatches;
import com.cakir.validation.ValidPassword;

@FieldMatch(first = "newPassword", second = "confirmPassword", message = "label.error.emailconfirm")
//@PasswordMatches
public class PasswordDto {

	 	private String oldPassword;

	    @ValidPassword
	    private String newPassword;
	    
	    private String confirmPassword;

	    public String getOldPassword() {
	        return oldPassword;
	    }

	    public void setOldPassword(String oldPassword) {
	        this.oldPassword = oldPassword;
	    }

	    public String getNewPassword() {
	        return newPassword;
	    }

	    public void setNewPassword(String newPassword) {
	        this.newPassword = newPassword;
	    }

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
	    
	    
}
