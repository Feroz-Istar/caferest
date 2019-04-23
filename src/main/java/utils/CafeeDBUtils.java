package utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class CafeeDBUtils {

		private static final Logger logger = LogManager.getLogger(CafeeDBUtils.class);
		static CafeeDBUtils db;
		static ComboPooledDataSource dataSource;

		private synchronized static ComboPooledDataSource getDataSource() throws PropertyVetoException {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/feroz");
			cpds.setUser("postgres");
			cpds.setPassword("root");
			cpds.setDriverClass("org.postgresql.Driver");
			// Optional Settings
			cpds.setInitialPoolSize(5);
			cpds.setMinPoolSize(5);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
			cpds.setMaxStatements(100);

			return cpds;
		}

		public static CafeeDBUtils getInstance() {
			try {
				if (db == null) {
					db = new CafeeDBUtils();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return db;
		}

		private CafeeDBUtils() throws PropertyVetoException {
			dataSource = getDataSource();
		}

		public int insertIntoDB(String sqlQuery) {
			logger.info(sqlQuery);
			int retrunIndex = 0;
			Connection connection = null;
			Statement pstmt = null;
			try {
				connection = dataSource.getConnection();
				pstmt = connection.createStatement();

				pstmt.executeUpdate(sqlQuery);
			} catch (SQLException se) {
				logger.error(sqlQuery);
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						connection.close();
				} catch (SQLException se) {
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			return retrunIndex;
		}

		public int insertIntoDBWithGeneratedKey(String sqlQuery) {
			logger.info(sqlQuery);
			int retrunIndex = 0;
			Connection connection = null;
			Statement pstmt = null;
			ResultSet resultSet = null;
			try {
				connection = dataSource.getConnection();
				pstmt = connection.createStatement();
				resultSet = pstmt.executeQuery(sqlQuery);
				resultSet.next();
				retrunIndex = resultSet.getInt(1);
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						connection.close();
				} catch (SQLException se) {
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			System.err.println(retrunIndex);
			return retrunIndex;
		}

		public ArrayList<HashMap<String, String>> executeQuery(StackTraceElement[] stackTraceElements, String sqlQuery) {
			long now = System.currentTimeMillis();
			ArrayList<HashMap<String, String>> table = new ArrayList<HashMap<String, String>>();
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet resultSet = null;
			logger.info("["+stackTraceElements[1].getClassName() + "." + stackTraceElements[1].getMethodName() + ":"
					+ stackTraceElements[1].getLineNumber() + "]--->" + sqlQuery);

			try {
				connection = dataSource.getConnection();
				pstmt = connection.prepareStatement(sqlQuery);
				resultSet = pstmt.executeQuery();
				ResultSetMetaData rsmd = resultSet.getMetaData();

				ArrayList<String> columnnames = new ArrayList<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {

					columnnames.add(rsmd.getColumnName(i));
				}

				while (resultSet.next()) {
					HashMap<String, String> row = new HashMap<String, String>();
					for (String columnName : columnnames) {
						String first = resultSet.getString(columnName);
						row.put(columnName, first);
					}
					table.add(row);
				}
			} catch (PSQLException psqe) {
				logger.error("Problem running this query ->" + sqlQuery);
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						connection.close();
				} catch (SQLException se) {
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if ((System.currentTimeMillis() - now) > 5000)
				logger.error("Exxceptionally long time (" + (System.currentTimeMillis() - now) + ") taken for query ->"
						+ sqlQuery);
			return table;
		}

	}