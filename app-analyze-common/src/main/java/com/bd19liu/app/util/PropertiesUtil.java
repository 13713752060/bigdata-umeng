package com.bd19liu.app.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 属性复制
 */
public class PropertiesUtil {
	/**
	 * 复制属性
	 */
	public static void copyProperties(Object src, Object dest) {
		try {
			//源对象的BI
			BeanInfo bisrc = Introspector.getBeanInfo(src.getClass());

			//属性描述符
			PropertyDescriptor[] parr = bisrc.getPropertyDescriptors();
			for(PropertyDescriptor pd : parr){
				//getXxx方法
				Method getter = pd.getReadMethod();
				//setter方法
				Method setter = pd.getWriteMethod();
				
				try{
					//获得set方法名称
					String methodName = setter.getName();
					//获得setter参数类型
					Class[] paramTypes = setter.getParameterTypes();
					//获取src对象的get方法的返回值
					Object value = getter.invoke(src);
					//获得dest对象对应的方法
					Method destSetter= dest.getClass().getMethod(methodName, paramTypes);
					destSetter.invoke(dest,value) ;
				} catch(Exception e){
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  @Description: 属性拷贝
	 *  @parameter:
	 *  @Author liudugang
	 *  @Date 2019-01-07 10:50
	 *  @return 
	 */
	public static void copyProperties(Object src, Object[] arr){
		for (Object dest : arr) {
			copyProperties(src, dest);
		}
	}
}
