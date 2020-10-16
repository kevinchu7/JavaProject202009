package com.njwb.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class JdbcUtil {
	private static Logger log = Logger.getLogger(JdbcUtil.class);
	private static String className, url, user, password;
	
	//本地线程使用的局部变量
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	

	static {
		try {
			// 加载properties,初始化连接参数
			Properties properties = new Properties();
			properties.load(JdbcUtil.class.getClassLoader().getResourceAsStream("datasource.properties"));
			className = properties.getProperty("driverClassName");
			url = properties.getProperty("url");
			user = properties.getProperty("username");
			password = properties.getProperty("password");
			// 加载驱动
			Class.forName(className);
		} catch (IOException e) {
			log.error("数据库连接参数初始化失败！", e);
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log.error("数据库驱动加载失败！", e);
			// e.printStackTrace();
		}

	}

	/**
	 * 建立连接
	 * 
	 * @return 连接对象conn
	 */
	public static Connection getConn() {
		Connection conn = threadLocal.get();
		if(conn==null){
			try {
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				log.error("数据库连接失败！", e);
//				e.printStackTrace();
			}
			threadLocal.set(conn);
		}
		return conn;
	}

	/**
	 * 关闭conn连接
	 * @param conn
	 */
	public static void close()
	{
		Connection conn = threadLocal.get();
		if (conn != null) {
			try {
				//从本地线程中移除
				threadLocal.remove();
				conn.close();
			} catch (SQLException e) {
				log.error("conn连接关闭失败！",e);
//				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 关闭资源（res为null)
	 * @param pdst
	 * @param conn
	 */
	public static void close(PreparedStatement pdst){
		close(null, pdst);
	}
	
	
	/**
	 * 关闭资源
	 * @param rs
	 * @param pdst
	 * @param conn
	 */
	public static void close(ResultSet rs, PreparedStatement pdst) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pdst != null) {
				pdst.close();
			}
		} catch (SQLException e) {
			log.error("资源关闭失败！",e);
//			e.printStackTrace();
		}
	}
}
