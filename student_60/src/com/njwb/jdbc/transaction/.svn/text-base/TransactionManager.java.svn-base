package com.njwb.jdbc.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.njwb.jdbc.JdbcUtil;
/**
 * 事务封装对象
 * @author longhuan
 * @创建日期 2020-9-15
 */
public class TransactionManager {

	private Logger log = Logger.getLogger(TransactionManager.class);

	/**
	 * 开启事务
	 * @throws Exception
	 */
	public void begin() throws SQLException {
		//获取连接
		Connection conn = JdbcUtil.getConn();
		//开启事务，将自动提交设置为false
		conn.setAutoCommit(false);
	}

	/**
	 * 提交事务
	 * @throws Exception
	 */
	public void commit() throws SQLException{
		Connection conn = JdbcUtil.getConn();
		try {
			//手动提交事务
			conn.commit();
		} catch (SQLException e) {
			log.info("提交异常：",e);
			throw e;
			
		}finally
		{
			//关闭连接
			JdbcUtil.close();
		}
	}

	/**
	 * 回滚事务
	 * @throws Exception
	 */
	public void rollback() throws SQLException{
		Connection conn = JdbcUtil.getConn();
		try {
			//事务回滚
			conn.rollback();
		} catch (SQLException e) {
			log.info("提交异常：",e);
			throw e;
		}finally
		{
			//关闭连接
			JdbcUtil.close();
		}
	}

}
