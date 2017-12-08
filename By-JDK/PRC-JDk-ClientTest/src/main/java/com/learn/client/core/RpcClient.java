package com.learn.client.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

import com.learn.api.core.domin.NetModel;
import com.learn.api.core.utils.SerializationUtil;



/**
 * scoket 通信客户端
 * @author Ming
 *
 */
public class RpcClient {
	private static Properties conf = new Properties();
	
	private static int port = 0;
	
	private static String ip = null;
	
	static {
		try {
			conf.load(RpcClient.class.getResourceAsStream("/config.properties"));
			ip = conf.getProperty("ip");
			port = Integer.parseInt(conf.getProperty("port"));
		} catch (Exception e) {
			System.out.println("配置文件读取失败");
			e.printStackTrace();
		}
	}
	
	public Object invoke (NetModel model) {
		Object value = null; 
		try {
			Socket socket = new Socket(ip, port);
			OutputStream out = socket.getOutputStream();
			byte[] bytes = SerializationUtil.objToNetBytes(model);
			out.write(bytes);
			InputStream in = socket.getInputStream();
			byte [] legnthBytes = new byte[4];
			in.read(legnthBytes);
			int legnth = SerializationUtil.bytesToInt(legnthBytes, 0);
			byte [] objBytes =  new byte [legnth];
			in.read(objBytes);
			value = SerializationUtil.byetsToObject(objBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
		
	}
	
}
