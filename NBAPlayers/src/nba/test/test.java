package nba.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nba.dao.PlayerDao;
import nba.dao.PlayerSeasonDataDao;
import nba.domin.Player;
import nba.domin.PlayerSeasonData;

public class test {

	@Test
	public void test() {
		String s="https://nba.hupu.com/players/aaaa-";
		int end=s.indexOf('-');
		String s1=s.substring(29, end);
		System.out.println(s1);
	}
	
	@Test
	public void test2() {
		PlayerDao dao=new PlayerDao();
		List<Player> list=dao.QueryByTeam("rocket");
		System.out.println(list.size());
		for(Player p:list) {
			System.out.println(p);
		}
	}
	
	@Test
	public void test03() {
		PlayerSeasonDataDao dao=new PlayerSeasonDataDao();
	   List<PlayerSeasonData> list=dao.QuerySeasonDataByName("JamesHarden");
		for(PlayerSeasonData pd:list) {
			System.out.println(pd);
		}
		
	}
	
	@Test
	public void test04() {
		String url="https://nba.hupu.com/players/pacers";
		String s=url.substring(29, url.length());
		System.out.println(s);
	}
}
