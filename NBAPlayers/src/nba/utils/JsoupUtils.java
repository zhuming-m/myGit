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

import nba.domin.Player;

public class JsoupUtils {
	
	//获取每个队每个球员信息并存入数据库
	@Test
	public  void getallpalayer() {
		
		QueryRunner queryRunner=new QueryRunner(DbcpUtils.getDataSource());
		
		JsoupUtils jsoupUtils=new JsoupUtils();
		//获取到每一个队的链接
		ArrayList<String> urls=jsoupUtils.getTeamUrl();
		for (String string : urls) {
			ArrayList<Player> player=new ArrayList<Player>();
			player=getplayer(string);
			//获取到每个队的每个球员
			for(Player p:player) {
				String sql="insert into Player(img,name,number,position,height,weight,birthday,contrast,team) "
						+ "values(?,?,?,?,?,?,?,?,?)";
			    Object[] params= {p.getImg(),p.getName(),p.getNumber(),p.getPosition(),p.getHeight()
					 ,p.getWeight(),p.getBirthday(),p.getContrast(),p.getTeam()};
			    try {
					queryRunner.update(sql, params);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//从某个队的url获取这个队的所有球员
	public static ArrayList<Player> getplayer(String url) {
		Document document = null;
		//获取队名
		String team=url.substring(29,url.length());
		ArrayList<Player> list=new ArrayList<Player>();
		try {
			document = Jsoup.connect(url).timeout(5000).get();
			
			Elements elements=document.select("table.players_table>tbody>tr:not(.title)");
			for (Element element : elements) {
				//获取球员图片地址
				Element tdmig=element.selectFirst("td.td_padding");
				String imglink=tdmig.select("a[href]>img").first().attr("src");
				Elements tdelments=element.select("td:not(.td_padding)");
				Player player=new Player();
				player.setImg(imglink);
				player.setTeam(team);
				for (int i=0;i<tdelments.size();i++) {
					if(i==0) {
						player.setName(tdelments.get(i).text());
					}else if(i==1) {
						player.setNumber(tdelments.get(i).text());
					}else if(i==2) {
						player.setPosition(tdelments.get(i).text());
					}else if(i==3) {
						player.setHeight(tdelments.get(i).text());
					}else if(i==4) {
						player.setWeight(tdelments.get(i).text());
					}else if(i==5) {
						player.setBirthday(tdelments.get(i).text());
					}else {
						player.setContrast(tdelments.get(i).text());
					}
				}
			  list.add(player);
			}
//			for (Player p : list) {
//				System.out.println(p);
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//获取所有球队的url
	public ArrayList<String>  getTeamUrl() {
		Document document1 = null;
		String url="https://nba.hupu.com/players/pacers";
		ArrayList<String> urls=new ArrayList<String>();
		try {
			document1 = Jsoup.connect(url).timeout(5000).get();
			
			Elements elements=document1.select("div.players_left:has(ul) a[href]");
			for (Element element : elements) {
				String href=element.attr("href");
				urls.add(href);
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urls;
	}
	
	
	
}
