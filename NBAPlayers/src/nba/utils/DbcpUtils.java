package nba.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DbcpUtils {

	private static DataSource dataSource = null;
	static{
		InputStream is = DbcpUtils.class.getClassLoader()
				.getResourceAsStream("dbcp-config.properties");
	
		Properties prop = new Properties();
		try {
			prop.load(is);
			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	public Connection getConnl() throws SQLException{
		return dataSource.getConnection();
	}
}
