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


@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminTest
{
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
}
