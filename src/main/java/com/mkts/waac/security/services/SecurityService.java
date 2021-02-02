package com.mkts.waac.security.services;


import com.mkts.waac.security.dto.SignedInAccountDto;
import com.mkts.waac.Dto.UserDto;

public interface SecurityService {

	boolean isUserSignedIn();

	UserDto registerNewUser(UserDto registration);

	String generateRandomPassword();

	String generateRandomPassword(int passwordLength);

	String encodePassword(String rawPassword);

	boolean verifyPassword(SignedInAccountDto signedInAccount, String passwordCandidate);
	
	SignedInAccountDto getSignedInAccount();
}
