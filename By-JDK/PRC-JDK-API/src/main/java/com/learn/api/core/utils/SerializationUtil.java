package com.learn.api.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 网络通信序列化工具
 * @author Ming
 *
 */
public class SerializationUtil {
	/**
	 * 对象序列化成数组
	 * @param object
	 * @return
	 */
	public static byte[] objectToBytes(Object object){  
	    ByteArrayOutputStream output = new ByteArrayOutputStream();  
	    ObjectOutputStream objectOut;  
	    try {  
	        objectOut = new ObjectOutputStream(output);  
	        objectOut.writeObject(object);  
	        objectOut.close();  
	        output.close();  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return output.toByteArray();  
	}
	/**
	 * 数组反序列化成对象
	 * @param bytes
	 * @return
	 */
	public static Object byetsToObject(byte[] bytes){  
	    ByteArrayInputStream input = new ByteArrayInputStream(bytes);  
	    ObjectInputStream objectIn;  
	    Object object = null;  
	    try {  
	        objectIn = new ObjectInputStream(input);      
	        object = objectIn.readObject();  
	        objectIn.close();  
	        input.close();  
	           
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }catch (ClassNotFoundException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  	      
	    return object;  
	}
	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。  
	 * @param value
	 * @return
	 */
	public static byte[] intToBytes(int value)   {   
	    byte[] byte_src = new byte[4];  
	    byte_src[3] = (byte) ((value & 0xFF000000)>>24);  
	    byte_src[2] = (byte) ((value & 0x00FF0000)>>16);  
	    byte_src[1] = (byte) ((value & 0x0000FF00)>>8);    
	    byte_src[0] = (byte) ((value & 0x000000FF));          
	    return byte_src;  
	} 
	
	 /**  
	    * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。 
	    *   
	    * @param ary  
	    *            byte数组  
	    * @param offset  
	    *            从数组的第offset位开始  
	    * @return int数值  
	    */    
	public static int bytesToInt(byte[] ary, int offset) {  
	    int value;    
	    value = (int) ((ary[offset]&0xFF)   
	            | ((ary[offset+1]<<8) & 0xFF00)  
	            | ((ary[offset+2]<<16)& 0xFF0000)   
	            | ((ary[offset+3]<<24) & 0xFF000000));  
	    return value;  
	}  
	
	/**
	 * 将对象直接序列化成为RPC网络通信中使用的二进制数组
	 * @param obj
	 * @return
	 */
	public static byte[] objToNetBytes(Object obj) {
		byte[] objBytes = objectToBytes(obj);
		int length = objBytes.length;
		byte[] lengthBytes = intToBytes(length);
		byte[] RPCBytes = new byte[length+4];
		System.arraycopy(lengthBytes, 0, RPCBytes, 0, 4);
		System.arraycopy(objBytes, 0, RPCBytes, 4, length);
		return RPCBytes;	
	}
	
	/**
	 * 将RPC网络通信中的二进制数组序列化成为使用对象
	 * @param bytes
	 * @return
	 */
	public static Object netBytesToObj(byte [] RPCbytes) {
		int length = bytesToInt(RPCbytes, 0);
		byte [] objBytes = new byte[length];
		System.arraycopy(RPCbytes, 4, objBytes, 0, length);
		Object object = byetsToObject(objBytes);
		return object;		
	}
}
