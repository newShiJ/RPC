package com.learn.server.core;

import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;



public class MinaStart {
	private static IoAcceptor accept = new NioSocketAcceptor();
	public static Properties className = new Properties();
	
	public static void start(int port,String ip,IoHandlerAdapter adapter ) throws Exception{
		accept.getSessionConfig().setReadBufferSize(2048 * 10);
		accept.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,60*5);

		LoggingFilter lf = new LoggingFilter();
		lf.setSessionCreatedLogLevel(LogLevel.INFO);
		lf.setSessionOpenedLogLevel(LogLevel.INFO);
		lf.setMessageReceivedLogLevel(LogLevel.INFO);
		lf.setMessageSentLogLevel(LogLevel.INFO);
		
		accept.getFilterChain().addLast("logger", lf);
		accept.getFilterChain().addLast("exceutor",
				new ExecutorFilter(Executors.newCachedThreadPool()));
		accept.setHandler(adapter);
		accept.bind(new InetSocketAddress(ip, port));
		System.out.println("启动MINA服务 监听端口:"+port);
	}
	
	public static void main(String[] args) throws Exception{
		Properties conf = new Properties();	
		conf.load(MinaStart.class.getResourceAsStream("/conf.properties"));
		String ip = conf.getProperty("ip");
		int port = Integer.parseInt(conf.getProperty("port"));
		start(port, ip, new RpcInvokeHandler());
	}
}
