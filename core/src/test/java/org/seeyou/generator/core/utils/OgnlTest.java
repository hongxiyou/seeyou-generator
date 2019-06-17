package org.seeyou.generator.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.snakeyaml.engine.v1.api.Load;
import org.snakeyaml.engine.v1.api.LoadSettings;
import org.snakeyaml.engine.v1.api.LoadSettingsBuilder;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class OgnlTest {

	@Test
	public void testOgnl() throws OgnlException {
		LoadSettings settings = new LoadSettingsBuilder().build();
		Load load = new Load(settings);
		Object obj = load
				.loadFromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("project.yml"));

		OgnlContext context = new OgnlContext(null, null, new DefaultMemberAccess(true));
		context.setRoot(obj);

		System.out.println(obj);

		Assertions.assertEquals("org.seeyou.demo", Ognl.getValue("parent.groupId", context, obj));
		Assertions.assertEquals("seeyou-generator-demo", Ognl.getValue("parent.artifactId", context, obj));
		Assertions.assertEquals("0.1.0-SNAPSHOT", Ognl.getValue("parent.version", context, obj));
		Assertions.assertEquals("SeeYou Generator Demo", Ognl.getValue("parent.name", context, obj));

	}

}
