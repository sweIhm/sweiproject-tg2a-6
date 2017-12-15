package base.admin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class GenerateHash {
	
	private String toEncode;
	
	public GenerateHash(String toEncode) {
		this.toEncode = toEncode;
	};

	public String getHash() {
		
		byte[] hash = new byte[toEncode.length()];
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(toEncode.getBytes(StandardCharsets.UTF_8));
			return Arrays.toString(hash);
		} 
		catch (NoSuchAlgorithmException e) {
			e.getStackTrace();
			return null;
		}
		catch (NullPointerException e) {
			e.getStackTrace();
			return null;
		}
	}
}
