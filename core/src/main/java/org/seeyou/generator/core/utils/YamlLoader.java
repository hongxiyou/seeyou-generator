package org.seeyou.generator.core.utils;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.snakeyaml.engine.v1.api.Load;
import org.snakeyaml.engine.v1.api.LoadSettings;
import org.snakeyaml.engine.v1.api.LoadSettingsBuilder;

/**
 * Yaml 配置文件加载器，支持 ${} 变量引用
 */
public class YamlLoader {

	public static Map<String, Object> load(InputStream input) {
		LoadSettings settings = new LoadSettingsBuilder().build();
		Load load = new Load(settings);
		Object obj = load.loadFromInputStream(input);

		Map<String, Object> result = new LinkedHashMap<>();
		flatten("", obj, result);

		Map<String, Object> result2 = new LinkedHashMap<>();
		fillParamsValue(result, result2);
		return result2;
	}

	/**
	 * 参数扁平化
	 * 
	 * @param prefix
	 * @param in
	 * @param out
	 */
	private static void flatten(final String prefix, Object in, Map<String, Object> out) {
		if (in instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) in;
			map.forEach((k, v) -> {
				String key = prefix.equals("") ? k : prefix + "." + k;
				flatten(key, v, out);
			});
		} else {
			out.put(prefix, in);
		}
	}

	/**
	 * 变量替换
	 * 
	 * @param in
	 * @param out
	 */
	private static void fillParamsValue(Map<String, Object> in, Map<String, Object> out) {
		final Pattern p = Pattern.compile("(\\$\\{\\s*)([\\w\\.]+)(\\s*\\})");
		in.forEach((k, v) -> {
			if (v instanceof String) {
				Matcher m = p.matcher(v.toString());
				StringBuffer sb = new StringBuffer();
				while (m.find()) {
					String group = m.group(2);
					Object value = out.get(group);
					if (value != null) {
						m.appendReplacement(sb, value.toString());
					}
				}
				m.appendTail(sb);
				out.put(k, sb.toString());
			} else {
				out.put(k, v);
			}
		});
	}

}
