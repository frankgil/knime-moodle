package org.knime.moodle.internals.logs;

import java.util.HashMap;
import java.util.Map;

import org.knime.core.node.NodeLogger;

public enum LogParameters {
	
	USER("setUser"),
	TARGET_USER("setTargetUser"),
	MODULE("setModule"),
	COURSE("setCourse"),
	TARGET_COURSE("setTargetCourse"),
	SECTION("setSection"),
	ITEM("setItem"),
	SUBITEM("setSubItem"),
	EMPTY("setEmptyParameter"),
	GROUP("setGroup"),
	PARAM_NOT_AVAILABLE("Parameter not avaible");

	
	private String name;
	private static Map<String, LogParameters> map;

	private LogParameters(String name) {
		this.name = name;
	}

	static {
		map = new HashMap<>();
		for (LogParameters logParameters : LogParameters.values()) {
			map.put(logParameters.name, logParameters);
		}
	}
		
	
	/**
	 * Devuelve el método de LogParameter a partir del String, si no existe devuelve PARAM_NOT_AVAILABLE
	 * @param name string del método de log
	 * @return el método de LogParameter a partir del String.
	 */
	public static LogParameters get(String name) {
		return map.getOrDefault(name, LogParameters.PARAM_NOT_AVAILABLE);
	}
	
	/**
	 * Devuelve el nombre corto del parámetro
	 * @return el nombre corto del parámetro
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
}
