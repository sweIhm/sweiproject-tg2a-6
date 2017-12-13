package base.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;

import static org.junit.Assert.*;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminTest
{

	static final String ADMIN_NAME_1 = "admin1";
	static final String ADMIN_NAME_2 = "admin2";
	static final String ADMIN_NAME_3 = "admin3";
	
	static final String ADMIN_PASS_1 = "admin1";
	static final String ADMIN_PASS_2 = "admin2";
	
	
	static final String WRONG_ADMIN_NAME = "wrongadmin";
	static final String WRONG_ADMIN_PASS = "wrongadmin";
	
	@Test
	public void validateSettersAndGetters()
	{
		PojoClass adminPojo = PojoClassFactory.getPojoClass(Admin.class);
		Validator validator = ValidatorBuilder.create()
				// Lets make sure that we have a getter and a setter for every
				// field defined.
				.with(new GetterMustExistRule())
				// Lets also validate that they are behaving as expected
				.with(new GetterTester()).build();
		// Start the Test
		validator.validate(adminPojo);
	}
	
	@Test
	public void ensureCorrectPasswordIsAccepted()
	{
		Admin admin = new Admin( ADMIN_NAME_1, ADMIN_PASS_1 );
		assertTrue( admin.checkPassword( ADMIN_PASS_1 ) );
	}
	
	@Test
	public void ensureWrongPasswordIsRejected()
	{
		Admin admin = new Admin( ADMIN_NAME_1, ADMIN_PASS_1 );
		assertFalse( admin.checkPassword( WRONG_ADMIN_PASS ) );
	}
	
	@Test
	public void ensureNoPassHashIsStoredTwice()
	{
		Admin admin1 = new Admin( ADMIN_NAME_1, ADMIN_PASS_1 );
		Admin admin2 = new Admin( ADMIN_NAME_2, ADMIN_PASS_1 );
		Admin admin3 = new Admin( ADMIN_NAME_3, ADMIN_PASS_2 );

		assertFalse(admin1.getPassHash().equals(admin2.getPassHash()));
		assertFalse(admin2.getPassHash().equals(admin3.getPassHash()));
		assertFalse(admin1.getPassHash().equals(admin3.getPassHash()));
		
	}
}
