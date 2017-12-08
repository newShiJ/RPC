package com.learn.server.core;

import java.lang.reflect.Method;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.learn.api.core.domin.NetModel;
import com.learn.api.core.utils.SerializationUtil;

public class RpcInvokeHandler extends IoHandlerAdapter{
	/**
	 * 提供了一个在Server端执行方法的过程
	 * 这里偷懒使用的是MINA框架
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		try {
			IoBuffer bf = (IoBuffer) message;
			byte[] netBytes = new byte[bf.limit()];
			bf.get(netBytes);
			Object obj = SerializationUtil.netBytesToObj(netBytes);
			NetModel model = (NetModel) obj;
			
			Object[] args = model.getArgs();
			String methodName = model.getMethod();
			String type = model.getType();
			String[] types = model.getTypes();
			
			Class [] classes = null;
			if(types!=null) {
				classes = new Class[types.length];
				for (int i = 0; i < classes.length; i++) {
					classes[i] = Class.forName(types[i]);
				}
			}
			
			Object service = ClassNameManerge.getInstance(type);
			Class<? extends Object> clazz = service.getClass();
			Method method = clazz.getMethod(methodName, classes);
			Object invoke = method.invoke(service, args);
			byte[] bytes = SerializationUtil.objToNetBytes(invoke);
			
			System.out.println("RPC >>>");
			System.out.println("\t inerface:"+type);
			System.out.println("\t method:"+methodName);
			String ss ="";
			for (String b : types) {
				ss += (b+"--");
			}
			System.out.println("\t argsType:"+ss);
			String tt ="";
			for (Object b : args) {
				tt += (b+"--");
			}
			System.out.println("\t args:"+tt);
			
			
			
			IoBuffer buffer = IoBuffer.allocate(bytes.length);  
			buffer.put(bytes, 0, bytes.length);    
			buffer.flip();    
			session.write(buffer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
