package xyp.tool.velocity;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.junit.Assert;
import org.junit.Test;

public class VelocityTest {

	@Test
	public void test1() throws Exception {
		
		VelocityEngineWrapper vew = VelocityEngineWrapper
				.newInstance(VelocityTest.class.getClassLoader()
						.getResourceAsStream("velocity.sql.properties"));
		
	
		StringResourceRepository repo = StringResourceLoader.getRepository("foo");
		System.out.println(repo);
		repo.putStringResource("abc", "hello abc");

		Map<String, Object> abc = new HashMap<>();
		Map<String, Integer> abc2 = new HashMap<>();
		String result = vew.mergeTemplate("abc", abc);
		System.out.println(result);
		result = vew.mergeTemplate("abc", abc2);
		System.out.println(result);
		
		Assert.assertEquals("hello abc", result);

	}
}
