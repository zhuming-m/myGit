package nba.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbutils.QueryRunner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import nba.domin.PlayerSeasonData;

public class JsoupUtils2 {

	
	//插入每个球员的赛季数据到数据库
	public static void main(String[] args) {
		QueryRunner  queryRunner=new QueryRunner(DbcpUtils.getDataSource());
		JsoupUtils2 jsoupUtils=new JsoupUtils2();
		ArrayList<PlayerSeasonData> datas=jsoupUtils.getseasoninfo();
		for(PlayerSeasonData pdata:datas) {
		String sql="insert into PlayerSeasonData(season,team,games,gamestart,time,shot,hitrate,threeball,"
				+ "threehitrate,penaltyshot,penaltyhitrate,rebound,assist,steal,block,fault,foul,score,playername) values(?,?"
				+ ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params= {pdata.getSeason(),pdata.getTeam(),pdata.getGames(),pdata.getGamestart(),pdata.getTime()
				,pdata.getShot(),pdata.getHitrate(),pdata.getThreeball(),pdata.getThreehitrate(),pdata.getPenaltyshot()
				,pdata.getPenaltyhitrate(),pdata.getRebound(),pdata.getAssist(),pdata.getSteal(),pdata.getBlock(),pdata.getFault()
				,pdata.getFoul(),pdata.getScore(),pdata.getPlayername()};
		try {
			queryRunner.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}
	
//	@Test
//	public void test() {
//		JsoupUtils2 jsoupUtils=new JsoupUtils2();
//		ArrayList<String> list=jsoupUtils.getSeasonData();
//		for(String s:list) {
//			System.out.println(s);
//		}
//	}
	//获取所有球员的若干个赛季数据
	public ArrayList<PlayerSeasonData> getseasoninfo() {
		
		JsoupUtils2 jsoupUtils=new JsoupUtils2();
		ArrayList<PlayerSeasonData> datalist =new ArrayList<PlayerSeasonData>();
		ArrayList<String> list=jsoupUtils.getSeasonData();
		//每个球员的赛季数据地址
		for (String str : list) {
			Document document=null;
			try {
				
				document = Jsoup.connect(str).timeout(5000).get();
				//有的球员没有赛季数据,则跳过
				Elements tables=document.getElementsByClass("bgs_table");
				if(tables.isEmpty()) {
					continue;
				}
				//球员名字
				int end=str.indexOf('-');
				String playername=str.substring(29, end);
				Element firsttable=document.select("table.bgs_table").first();
				Element tbody=firsttable.select("tbody").first();
				Elements elements=tbody.select("tr");
				//每个赛季的数据
				for (Element tr : elements) {
					PlayerSeasonData data=new PlayerSeasonData();
					if(!tr.equals(elements.get(0))) {
						Elements tds=tr.select("td");
							for (int i=0;i<tds.size();i++) {
								if(i==0) {
									data.setSeason(tds.get(i).text());
								}else if(i==1) {
									data.setTeam(tds.get(i).text());
								}else if(i==2) {
									data.setGames(tds.get(i).text());
								}else if(i==3) {
									data.setGamestart(tds.get(i).text());
								}else if(i==4) {
									data.setTime(tds.get(i).text());
								}else if(i==5) {
									data.setShot(tds.get(i).text());
								}else if(i==6){
									data.setHitrate(tds.get(i).text());
								}else if(i==7) {
									data.setThreeball(tds.get(i).text());
								}else if(i==8) {
									data.setThreehitrate(tds.get(i).text());
								}else if(i==9) {
									data.setPenaltyshot(tds.get(i).text());
								}else if(i==10) {
									data.setPenaltyhitrate(tds.get(i).text());
								}else if(i==11) {
									data.setRebound(tds.get(i).text());
								}else if(i==12) {
									data.setAssist(tds.get(i).text());
								}else if(i==13) {
									data.setSteal(tds.get(i).text());
								}else if(i==14) {
									data.setBlock(tds.get(i).text());
								}else if(i==15) {
									data.setFault(tds.get(i).text());
								}else if(i==16) {
									data.setFoul(tds.get(i).text());
								}else {
									data.setScore(tds.get(i).text());
								}
							}
							data.setPlayername(playername);
							datalist.add(data);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datalist;
//		System.out.println(datalist.size());
//		for(PlayerSeasonData da:datalist) {
//			System.out.println(da);
//		}
		
	}
	
	//爬取球员赛季数据地址用ArrayList存放
	public ArrayList<String> getSeasonData() {
			
			JsoupUtils jsoupUtils=new JsoupUtils();
			 //获取到每一个队的链接
			ArrayList<String> urls=jsoupUtils.getTeamUrl();
			ArrayList<String> playersinfo=new ArrayList<String>();
			for (String string : urls) {
			  Document document = null;
			  try {
				document = Jsoup.connect(string).timeout(5000).get();
				Elements elements=document.select("table.players_table>tbody>tr:not(.title)");
				for (Element element : elements) {
					Element tdmig=element.selectFirst("td.td_padding");
					//球员赛季信息地址
					String playerlink=tdmig.select("a").first().attr("href");
					playersinfo.add(playerlink);
				     }
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			        }  
	          }
			return playersinfo;
	    }
}
