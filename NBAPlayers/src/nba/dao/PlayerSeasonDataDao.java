package nba.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import nba.domin.PlayerSeasonData;
import nba.utils.DbcpUtils;

public class PlayerSeasonDataDao {

	
	QueryRunner queryRunner=new QueryRunner(DbcpUtils.getDataSource());
	
	public List<PlayerSeasonData> QuerySeasonDataByName(String playername) {
		String sql="select * from PlayerSeasonData where playername=?";
		List<PlayerSeasonData> list=null;
		try {
			
			 list=queryRunner.query(sql, new BeanListHandler<PlayerSeasonData>(PlayerSeasonData.class), playername);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
}
