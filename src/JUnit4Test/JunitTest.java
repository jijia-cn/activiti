package JUnit4Test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class JunitTest {

	
	@Test
	public void testAssert()
	{
//		Assert()
		String test = "cc";
		System.out.println((test instanceof String)+" "+(test instanceof Object));
	}
}
