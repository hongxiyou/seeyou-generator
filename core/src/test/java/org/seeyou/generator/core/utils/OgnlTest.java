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

		Assertions.assertEquals("org.seeyou.demo", Ognl.getValue("groupId", context, obj));
		Assertions.assertEquals("seeyou-demo", Ognl.getValue("artifactId", context, obj));
		Assertions.assertEquals("0.1.0-SNAPSHOT", Ognl.getValue("version", context, obj));
		Assertions.assertEquals("SeeYou Demo", Ognl.getValue("name", context, obj));

		Assertions.assertEquals("${artifactId}-server", Ognl.getValue("modules.server.artifactId", context, obj));
		Assertions.assertEquals("${name} -- Server", Ognl.getValue("modules.server.name", context, obj));

		Assertions.assertEquals("${artifactId}-client", Ognl.getValue("modules.client.artifactId", context, obj));
		Assertions.assertEquals("${name} -- Client", Ognl.getValue("modules.client.name", context, obj));

		Assertions.assertEquals("${artifactId}-frontend", Ognl.getValue("modules.frontend.artifactId", context, obj));
		Assertions.assertEquals("${name} -- Frontend", Ognl.getValue("modules.frontend.name", context, obj));

	}

}
