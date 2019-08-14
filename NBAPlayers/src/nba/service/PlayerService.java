package nba.service;

import java.util.List;

import com.alibaba.fastjson.JSON;

import nba.dao.PlayerDao;
import nba.dao.PlayerSeasonDataDao;
import nba.domin.Player;
import nba.domin.PlayerSeasonData;

public class PlayerService {

	
	private PlayerDao dao=new PlayerDao();
	private PlayerSeasonDataDao dao2=new PlayerSeasonDataDao();
	
	public String queryPlayer(String team) {
		List<Player> list=dao.QueryByTeam(team);
		return JSON.toJSONString(list);
	}
	
	public String queryPlayerSeasonData(String playername) {
		List<PlayerSeasonData> list=dao2.QuerySeasonDataByName(playername);
		return JSON.toJSONString(list);
	}
}
