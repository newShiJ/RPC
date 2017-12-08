package com.learn.api.core.domin;

import java.io.Serializable;

/**
 * 使用jdk方式序列化 json序列化时 反序列化有时候比麻烦 
 * @author Ming
 *
 */
public class NetModel implements Serializable{
	//类名称
	private String type;
	//方法名称
	private String method;
	//参数
	private Object[] args;
	//参数的类型
	private String[] types;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	
	
	
}
