package base.admin;

public class GenerateHash {

	public static String getHash(String toEncode) {

		int length = toEncode.length();
		char[] encoded = new char[length];
		
		String salt = System.getenv("SHA_SALT");
		salt = salt.substring(0, length);
		
		char[] charSalt = salt.toCharArray();
		char[] encoding = toEncode.toCharArray();
		
		for(int i = 0; i < encoding.length; i++) {
			encoded[i] = (char) (charSalt[i] ^ encoding[i]);
		}
		
		return String.valueOf(encoded);
	}
}
