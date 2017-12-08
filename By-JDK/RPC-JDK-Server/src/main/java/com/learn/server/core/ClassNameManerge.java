package com.learn.server.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ClassNameManerge {
	private static Map<String, Object> instances = new HashMap<String, Object>();
	
	private static Properties classNames = new Properties();
	
	static {
		try {
			classNames.load(ClassNameManerge.class.getResourceAsStream("/className.properties"));
		} catch (Exception e) {
			System.out.println("配置文件读取异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据配置文件中的配置接口实现类获取对应的接口实习 （使用了一个不怎么严谨的单例模式）
	 * @param className 接口的类名称
	 * @return 接口的一个实现
	 */
	public static Object getInstance(String className) {
		String type = classNames.getProperty(className);
		Object instance = instances.get(type);
		if(instance!=null) {
			return instance;
		}
		try {
			Class clazz = Class.forName(type);
			instance = clazz.newInstance();
			instances.put(className, instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
}
