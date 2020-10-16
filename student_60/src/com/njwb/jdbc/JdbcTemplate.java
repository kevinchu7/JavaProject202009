package com.njwb.jdbc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * JdbcUtil
 * 
 * @author 朱凯 No.60 Date 2020-9-12
 */
public class JdbcTemplate {
	private static Logger log = Logger.getLogger(JdbcTemplate.class);
	private static Properties ORMapping = null;

	static {
		// 加载关系映射
		ORMapping = new Properties();
		try {
			ORMapping.load(JdbcUtil.class.getClassLoader()
					.getResourceAsStream("ORMapping.properties"));
		} catch (IOException e) {
			log.error("数据库关系映射properties加载失败！", e);
			// e.printStackTrace();
		}

	}

	/**
	 * insert
	 * 
	 * @param sql
	 * @param args
	 * @return 影响行数
	 * @throws SQLException
	 */
	public static int insert(String sql, Object... args) throws SQLException{
		return executeUpdate(sql, args);
	
	}
	
	/**
	 * delete
	 * 
	 * @param sql
	 * @param args
	 * @return 影响行数
	 * @throws SQLException
	 */
	public static int delete(String sql, Object... args) throws SQLException{
		return executeUpdate(sql, args);
	}
	
	
	/**
	 * update
	 * 
	 * @param sql
	 * @param args
	 * @return 影响行数
	 * @throws SQLException
	 */
	public static int update(String sql, Object... args) throws SQLException{
		return executeUpdate(sql, args);
	}
	
	/**
	 * select 
	 * 
	 * @param <T>
	 * @param sql
	 * @param clz
	 * @param args
	 * @return List<T>
	 * @throws SQLException
	 * @throws Exception
	 * @throws NoSuchMethodException
	 */
	public static <T> List<T> select(String sql, 
			Class<T> clz, Object... args) throws SQLException{
		return executeQueryObjectList(sql, clz, args);
	}
	
	
	
	/**
	 * 查询单行单字段的值
	 * @param <T>
	 * @param sql
	 * @param colName
	 * @param clz
	 * @return
	 */
	public static <T> T select(String sql, String colName, Class clz,
			Object... args) throws SQLException {
		List<Map<String, Object>> list=executeQuery(sql, args);
		if(list==null||list.size()==0) {
			return null;
		}else if(list.size()>1){
			log.info("查询出错,符合条件的行不唯一！");
			throw new SQLException();
		}
		Map<String, Object> map=list.get(0);
		T res=(T) map.get(ORMapping.getProperty(colName));
		return res;
	}
	
	
	/**
	 * 查询单行数据
	 * @param <T>
	 * @param sql
	 * @param clz
	 * @param args
	 * @return T
	 * @throws SQLException
	 * @throws Exception
	 */
	public static <T> T selectOne(String sql, 
	Class<T> clz, Object... args) throws SQLException{
		List<T> list=executeQueryObjectList(sql, clz, args);
		if(list==null||list.size()==0){
			return null;
		}else if(list.size()>1){
			log.info("查询出错,符合条件的行不唯一！");
			throw new SQLException();
		}
		return list.get(0);
		
	}
	
	
	/**
	 * 查询单列数据
	 * @param <T>
	 * @param sql
	 * @param colName
	 * @param colClz
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> selectOneCol(String sql, String colName,
			Class<T> colClz,Object...args) throws SQLException {
		List<T> colList=new ArrayList<T>();
		List<Map<String, Object>> list=executeQuery(sql, args);
		if(list==null||list.size()==0) {
			return null;
		}
		for(Map<String, Object> map:list) {
			T colValue=(T) map.get(ORMapping.getProperty(colName));
			colList.add(colValue);
		}
		return colList;
		
	}
		
	
	/**
	 * 增、删、改
	 * 
	 * @param sql
	 * @param args
	 * @return 受影响行数rows
	 * @throws SQLException
	 */
	private static int executeUpdate(String sql, 
			Object... args) throws SQLException {
		Connection conn = JdbcUtil.getConn();
		PreparedStatement pdst = null;
		int rows = 0;

		// 建立sql语句对象
		pdst = conn.prepareStatement(sql);

		// 如果有参数，即sql语句含有占位符，逐个解释占位符 （数组索引从0开始，占位符从1开始。。。）
		setPdstObjects(pdst, args);

		// excuteUpedate可执行增、删、改语句
		rows = pdst.executeUpdate();

		// 关闭资源
		JdbcUtil.close(pdst);

		if (rows > 0) {
			log.info("数据更新成功,受影响行数：" + rows);
		} else {
			log.info("数据更新失败！");
			throw new SQLException("数据更新失败！");
		}
		return rows;

	}


	/**
	 * ObjectList查询
	 * @param <T>
	 * @param sql
	 * @param clz
	 * @param args
	 * @return List<T>
	 * @throws SQLException
	 * @throws Exception
	 * @throws NoSuchMethodException
	 */
	private static <T> List<T> executeQueryObjectList(String sql, 
			Class<T> clz, Object... args) throws SQLException{
		List<T> objList = new ArrayList<T>();
		List<Map<String, Object>> resList = executeQuery(sql, args);
		Field[] fields = clz.getDeclaredFields();
		for (Map<String, Object> map : resList) {
			// 拿对象类的属性
			Class[] fieldClasses = new Class[fields.length];
			Object[] fieldValues = new Object[fields.length];

			for (int j = 0; j < fields.length; j++) {
				// 获取属性类型,存入属性类型数组
				fieldClasses[j] = fields[j].getType();
				// 获取属性值，存入属性值数组
				fieldValues[j] = fieldValueFormat(fieldClasses[j],
						map.get(ORMapping.getProperty(fields[j].getName())));

			}
			try {
				// 拿构造器 
				Constructor<T> constructor = clz.getConstructor(fieldClasses);
				// 创建对象
				T obj = constructor.newInstance(fieldValues);
				
				objList.add(obj);
			} catch (IllegalArgumentException e) {
				log.error("查询操作失败！",e);
//				e.printStackTrace();
			} catch (InstantiationException e) {
				log.error("查询操作失败！",e);
//				e.printStackTrace();
			} catch (SecurityException e) {
				log.error("查询操作失败！",e);
//				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				log.error("查询操作失败！",e);
//				e.printStackTrace();
			} catch (IllegalAccessException e) {
				log.error("查询操作失败！",e);
//				e.printStackTrace();
			} catch (InvocationTargetException e) {
				log.error("查询操作失败！",e);
//				e.printStackTrace();
			}
		}
		return objList;
	}

	
	
	/**
	 * 查询
	 * 
	 * @param sql
	 * @param args
	 * @return 行数据List<Map<String(字段名),Object(字段值)>>
	 * @throws SQLException
	 */
	private static List<Map<String, Object>> executeQuery(String sql, 
			Object... args) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = JdbcUtil.getConn();
		// 建立sql语句对象
		PreparedStatement pdst = conn.prepareStatement(sql);

		// 如果有参数，说明sql语句中有占位符，逐个解释占位符、
		setPdstObjects(pdst, args);
		// 执行查询语句，获取结果集
		ResultSet rs = pdst.executeQuery();
		// 获取结果集中的数据表(ResultSetMetaData)
		ResultSetMetaData rsmd = rs.getMetaData();
		// 获取结果集数据表的列数
		int count = rsmd.getColumnCount();
		// 循环结果集的每一行
		while (rs.next()) {
			// 创建map存储行数据
			Map<String, Object> rowData = new HashMap<String, Object>();
			// 遍历结果集表的每一列（即该行的每一字段）
			for (int j = 1; j <= count; j++) {
				// 获取列名（字段名）
				String cname = rsmd.getColumnName(j);
				// 获取该位置（该行该列）的值（以Object的形式）
				Object cvalue = rs.getObject(cname);
				// 将该行的 列名（字段名），值（字段值） 放入 行数据map
				rowData.put(cname, cvalue);
			}
			// 将该行数据的map<String 字段名，Object 字段值>添加到查询结果list中
			list.add(rowData);
		}
		JdbcUtil.close(rs, pdst);
		return list;

	}

	/**
	 * 规范属性值的类型
	 * 
	 * @param fieldClass
	 * @param fieldValue
	 * @return Object
	 */
	private static Object fieldValueFormat(Class fieldClass, Object fieldValue) {
		// null值处理
		if (fieldValue == null) {
			if (fieldClass == byte.class || fieldClass == short.class || fieldClass == int.class || fieldClass == long.class || fieldClass == double.class || fieldClass == float.class) {
				return 0;
			} else if (fieldClass == boolean.class) {
				return false;
			} else if (fieldClass == char.class) {
				return '\0';
			} else if (fieldClass == String.class) {
				return null;
			}

		}

		// BigDecimal处理
		if (fieldValue.getClass() == BigDecimal.class) {
			BigDecimal bigValue = (BigDecimal) fieldValue;
			if (fieldClass == int.class || fieldClass == Integer.class) {
				return bigValue.intValue();
			} else if (fieldClass == float.class || fieldClass == Float.class) {
				return bigValue.floatValue();
			} else if (fieldClass == double.class || fieldClass == Double.class) {
				return bigValue.doubleValue();
			}

			// sql.Date处理
		} else if (fieldValue == java.sql.Date.class) {
			java.sql.Date date = (java.sql.Date) fieldValue;
			return new Date(date.getTime());

			// 时间戳处理
		} else if (fieldValue.getClass() == java.sql.Timestamp.class) {
			java.sql.Timestamp ts = (java.sql.Timestamp) fieldValue;
			return new Date(ts.getTime());
		} 

		return fieldValue;

	}

	
	
	
	/**
	 * 解释sql语句中的占位符
	 * 
	 * @param pdst
	 * @param args
	 */
	private static void setPdstObjects(PreparedStatement pdst, Object[] args) {
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				try {
					pdst.setObject(i + 1, args[i]);
				} catch (SQLException e) {
					log.error("sql语句占位符解释失败！", e);
					// e.printStackTrace();
				}
			}
		}
	}

	


}
