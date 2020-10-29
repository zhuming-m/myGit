package nba.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import nba.domin.Player;
import nba.utils.DbcpUtils;

public class PlayerDao {

	QueryRunner queryRunner=new QueryRunner(DbcpUtils.getDataSource());
	
	
	public List<Player> QueryByTeam(String team) {
		String sql="select * from Player where team=?";
		List<Player> list=null;
		
		try {
			list=queryRunner.query(sql, new BeanListHandler<Player>(Player.class), team);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
