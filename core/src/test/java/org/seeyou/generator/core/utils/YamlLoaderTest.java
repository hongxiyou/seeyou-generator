package org.seeyou.generator.core.utils;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class YamlLoaderTest {

	@Test
	public void testLoadProject() {
		Map<String, Object> options = YamlLoader
				.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("project.yml"));

		Assertions.assertEquals("org.seeyou.demo", options.get("groupId"));
		Assertions.assertEquals("seeyou-demo", options.get("artifactId"));
		Assertions.assertEquals("0.1.0-SNAPSHOT", options.get("version"));
		Assertions.assertEquals("SeeYou Demo", options.get("name"));

		Assertions.assertEquals("seeyou-demo-server", options.get("modules.server.artifactId"));
		Assertions.assertEquals("SeeYou Demo -- Server", options.get("modules.server.name"));

		Assertions.assertEquals("seeyou-demo-client", options.get("modules.client.artifactId"));
		Assertions.assertEquals("SeeYou Demo -- Client", options.get("modules.client.name"));

		Assertions.assertEquals("seeyou-demo-frontend", options.get("modules.frontend.artifactId"));
		Assertions.assertEquals("SeeYou Demo -- Frontend", options.get("modules.frontend.name"));
	}

}
