package base.admin;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Admin
{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(unique=true)
    private String name;
    
    private byte[] salt;
    
    private byte[] passHash;
    
    private byte[] generatePassword( final char[] pass )
    {
    	try {
			SecretKeyFactory s = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
			PBEKeySpec spec = new PBEKeySpec( pass, salt, 100, 256 );
			SecretKey key = s.generateSecret( spec );
			return key.getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

			System.out.println(e);
			return new byte[0];
		}
    }
    
    public Admin() {}
    
    public Admin( String name, String pass )
    {
    	this.name = name;
    	
    	salt = new byte[32];
    	SecureRandom random = new SecureRandom();
    	random.nextBytes( salt );
    	
    	passHash = generatePassword( pass.toCharArray() );

    }


	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public byte[] getSalt() {
		return salt;
	}

	public byte[] getPassHash() {
		return passHash;
	}
	
	public boolean checkPassword( String password ) {
		
		return Arrays.equals(generatePassword( password.toCharArray() ), passHash);
		
	}
	
	public void prepareForREST()
	{
		salt = null;
		passHash = null;
	}

}
