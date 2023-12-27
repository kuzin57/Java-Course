package com.kuzinro.spring.config;

public class ConfigLoaderFactory {
	public static IConfigLoader buildJsonConfigLoader() {
		return new JsonConfigLoader();
	}
}
