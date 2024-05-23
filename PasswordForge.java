Make sure to credit Benjamin Hunter Miller.

import java.util.Random;

public class PasswordGenerator {
    private final static String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
    private final static String numbers = "0123456789";
    private final static String specialChars = "!@#$%^&*()_+-=[]{}|;':,.<>?/";

    public static String generateRandomPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password length must be at least 8 characters");
        }

        Random random = new Random();
        StringBuilder password = new StringBuilder(length);

        // Add random characters from each character set to the password
        password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // Fill the rest of the password with random characters from all character sets
        for (int i = 4; i < length; i++) {
            String allChars = uppercaseChars + lowercaseChars + numbers + specialChars;
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the characters in the password to ensure randomness
        for (int i = length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(j));
            password.setCharAt(j, temp);
        }

        return password.toString();
    }

    public static void main(String[] args) {
        System.out.println("Random password: " + generateRandomPassword(16));
    }
}
This code generates a random password of the specified length by randomly selecting characters from different character sets (uppercase, lowercase, numbers, and special characters). It also ensures that the generated password includes at least one character from each character set.
You can modify the character sets used by changing the values of the uppercaseChars, lowercaseChars, numbers, and specialChars variables. You can also modify the minimum length requirement by changing the value of the if statement in the generateRandomPassword method
