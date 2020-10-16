package com.njwb.factory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.njwb.factory.annotation.FactoryAnnotation;


public class ObjectFactory {
	private static Logger log=Logger.getLogger(ObjectFactory.class);
	private static Properties properties; 
	//容器
	private static Map<String,Object> objectsMap=new HashMap<String, Object>();
	
	static{
		initProperties();
		try {
			initObjectFactory();
		} catch (Exception e) {
			log.error("对象工厂初始化失败！",e);
//			e.printStackTrace();
		}
	}
	
	
	
	private static void initProperties(){
		properties=new Properties();
		try {
			properties.load(ObjectFactory.class.getClassLoader().
					getResourceAsStream("object.properties"));
			
		} catch (IOException e) {
			log.error("properties解析失败！",e);
//			e.printStackTrace();
		}
		
	}
	
	
	private static void initObjectFactory() throws Exception{
		Set<Entry<Object,Object>> entrySet=properties.entrySet();
		Iterator<Entry<Object,Object>> iterator=entrySet.iterator();
		while(iterator.hasNext()){
			Entry<Object,Object> entry=iterator.next();
			String key=(String)entry.getKey();
			if(objectsMap.get(key)!=null){
				log.info("初始化实例失败,key重复,重复的key:"+key);
				continue;
			}
			String className=(String)entry.getValue();
			Object obj=initObject(className);
			objectsMap.put(key, obj);
			
		}
		
		
		
		
		
		
		
	}
	
	
//	private static Object initObject(String className){
//		Object obj=null;
//		try {
//			Class clz = Class.forName(className);
//			obj=clz.newInstance();
//			Field[] fields=clz.getDeclaredFields();
//			for(Field field:fields){
//				//跳过没有FactoryAnnotation注解的属性
//				if(field.isAnnotationPresent(FactoryAnnotation.class)==false){
//					continue;
//				}
//				FactoryAnnotation annotation=field.getAnnotation(FactoryAnnotation.class);
//				Method method=clz.getDeclaredMethod("set"
//						+field.getName().substring(0,1).toUpperCase()
//						+field.getName().substring(1),field.getType());
//				//拿注解里的value,即Map容器里的key
//				String value=annotation.value();
//				//先去容器里找value
//				Object propertiesObj=objectsMap.get(value);
//				//如果找不到,递归,直至获取
//				if(propertiesObj==null){
//					Object propertiesValue=properties.getProperty(value);
//					String proClassName=(String)propertiesValue;
//					propertiesObj=initObject(proClassName);
//				}
//				method.invoke(propertiesObj, fields.getClass());
//			}
//			
//			
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		
//		return obj;
//	}
	
	/**
	 * 初始化实例对象
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private static Object initObject(String className)
			throws Exception
			{
		Class clazz = Class.forName(className);
		Object obj = clazz.newInstance();

		// 对实例对象中的属性进行赋值
		// 1.拿到所有的属性列表
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//拿注解对象FactoryAnnotation
			if(!field.isAnnotationPresent(FactoryAnnotation.class))
			{
				continue;
			}
			FactoryAnnotation annotaion = field.getAnnotation(FactoryAnnotation.class);
			// 3.注解中的value值，是map容器中对象的key
			String daoKey = annotaion.value();
			
			//拿调用set方法需要传入的实参
			Object propertyObj = objectsMap.get(daoKey);
			
			if(propertyObj == null)
			{
				log.info("容器中还没有dao的对象，先实例化dao：" + daoKey);
				//容器中还没有dao对象
				//那么我先去properties中拿这个dao的信息，先实例化，丢到map里面
				String proClassName = properties.getProperty(daoKey);
				objectsMap.put(daoKey, Class.forName(proClassName).newInstance());
				//再从容器中拿实参对象
				propertyObj = objectsMap.get(daoKey);
			}
			
			//容器中已经有了dao对象
			//拿set方法对象
			// 方法名，方法参数列表（也就是属性类型）
			String methodName = "set"
				+ field.getName().substring(0, 1).toUpperCase()
				+ field.getName().substring(1);
			//第一种写法，方法的形参类型是属性类型
			Method method = clazz.getDeclaredMethod(methodName, field.getType());
			//第二种写法，方法形参类型（class）通过实参的对象去获取
			//propertyObj.getClass().getInterfaces()[0];
			
			//执行set方法赋值
			method.invoke(obj, propertyObj);
			
		}

		return obj;
	}
	
	
	
	/**
	 * 返回指定对象实例
	 * @param key 容器中的key，实际上是object.txt中的key
	 * @return
	 */
	public static Object getObject(String key)
	{
		Object obj = objectsMap.get(key);
		if(obj == null)
		{
			log.info("容器中没有这个key对应的实例对象，key:" + key);
		}
		return obj;
	}
	
	
	
	
}
