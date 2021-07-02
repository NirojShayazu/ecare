package com.example.demo.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {
	private final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
	private final String DIGIT = "0123456789";
	private final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
	private final String OTHER_SYMBOL = "~$^+=<>";
	private final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
	private final int PASSWORD_LENGTH = 7;

	private final String PASSWORD_ALLOW = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

	private SecureRandom random = new SecureRandom();

	public String generatePassword() {
		StringBuilder result = new StringBuilder(PASSWORD_LENGTH);
		String strLowerCase = generateRandomString(CHAR_LOWERCASE, 2);
		result.append(strLowerCase);

		// at least 1 chars (uppercase)
		String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 1);
		result.append(strUppercaseCase);

		// at least 2 digits
		String strDigit = generateRandomString(DIGIT, 2);
		result.append(strDigit);

		// at least 2 special characters (punctuation + symbols)
		String strSpecialChar = generateRandomString(OTHER_SPECIAL, 2);
		result.append(strSpecialChar);

		String password = result.toString();
		// combine all
		System.out.format("%-20s: %s%n", "Password", password);
		// shuffle again
		System.out.format("%-20s: %s%n", "Final Password", shuffleString(password));
		System.out.format("%-20s: %s%n%n", "Password Length", password.length());

		return password;
	}
	
	private String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0)
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1) throw new IllegalArgumentException("Invalid size.");

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

    // for final password, make it more random
    public String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        // java 8
        return result.stream().collect(Collectors.joining());
    }

}
