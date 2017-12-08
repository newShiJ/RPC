package com.learn.client.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.learn.api.core.domin.NetModel;


public class ProxyFactory {

	private static InvocationHandler handler = new InvocationHandler() {
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			RpcClient client = new RpcClient();
			NetModel model = new NetModel();
			
			Class<?>[] classes = proxy.getClass().getInterfaces();
			String className = classes[0].getName();
			
			String [] types = null; 
			if(args!=null) {
				types = new String [args.length];
				for (int i = 0; i < types.length; i++) {
					types[i] = args[i].getClass().getName();
				}
			}
			
			model.setArgs(args);
			model.setTypes(types);
			model.setType(className);
			model.setMethod(method.getName());
			
			Object invoke = client.invoke(model);
			return invoke;
		}
	};
	
	public static <T> T getInstance(Class<T> clazz) {
		ClassLoader classLoader = clazz.getClassLoader();  	
        Class<?>[] interfaces = new Class[] {clazz};  
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);  
	}
}
