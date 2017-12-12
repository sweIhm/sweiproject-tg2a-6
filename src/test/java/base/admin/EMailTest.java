package base.admin;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.Validator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class EMailTest {

	@Test
	public void validateSettersAndGetters() {
		PojoClass EMailPojo = PojoClassFactory.getPojoClass(EMail.class);
		Validator validator = ValidatorBuilder.create()
				// Lets make sure that we have a getter for every
				// field defined.
				.with(new GetterMustExistRule()).with(new SetterMustExistRule())
				// Lets also validate that they are behaving as expected
				.with(new GetterTester()).with(new SetterTester()).build();
		// Start the Test
		validator.validate(EMailPojo);
	}
}