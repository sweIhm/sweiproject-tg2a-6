package base.admin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class GenerateHash {

	public static String getHash(String toEncode) {
		
		byte[] hash = new byte[toEncode.length()];
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(toEncode.getBytes(StandardCharsets.UTF_8));
		} 
		catch (NoSuchAlgorithmException e) {
			e.getStackTrace();
			return null;
		}
		return Arrays.toString(hash);
	}
}
