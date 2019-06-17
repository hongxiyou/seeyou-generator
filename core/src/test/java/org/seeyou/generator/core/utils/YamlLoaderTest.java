package org.seeyou.generator.core.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class YamlLoaderTest {

	@Test
	public void testLoadProject() {
		Map<String, Object> options = YamlLoader
				.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("project.yml"));

		Assertions.assertEquals("org.seeyou.demo", options.get("parent.groupId"));
		Assertions.assertEquals("seeyou-generator-demo", options.get("parent.artifactId"));
		Assertions.assertEquals("0.1.0-SNAPSHOT", options.get("parent.version"));
		Assertions.assertEquals("SeeYou Generator Demo", options.get("parent.name"));

		@SuppressWarnings("unchecked")
		List<String> modules = (List<String>) options.get("parent.modules");
		Assertions.assertLinesMatch(Arrays.asList("server", "client", "frontend"), modules);

		Assertions.assertEquals("seeyou-generator-demo-server", options.get("server.artifactId"));
		Assertions.assertEquals("SeeYou Generator Demo -- Server", options.get("server.name"));

		Assertions.assertEquals("seeyou-generator-demo-client", options.get("client.artifactId"));
		Assertions.assertEquals("SeeYou Generator Demo -- Client", options.get("client.name"));

		Assertions.assertEquals("seeyou-generator-demo-frontend", options.get("frontend.artifactId"));
		Assertions.assertEquals("SeeYou Generator Demo -- Frontend", options.get("frontend.name"));
	}

}
