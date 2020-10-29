package nba.domin;

public class PlayerSeasonData {

	private int id;
	private String season;
	private String team;
	private String games;
	private String gamestart;
	private String time;
	private String shot;
	private String hitrate;
	private String threeball;
	private String threehitrate;
	private String penaltyshot;
	private String penaltyhitrate;
	private String rebound;
	private String assist;
	private String steal;
	private String block;
	private String fault;
	private String foul;
	private String score;
	private String playername;
	
	public PlayerSeasonData() {
		super();
	}

	public PlayerSeasonData(String season, String team, String games, String gamestart, String time, String shot,
			String hitrate, String threeball, String threehitrate, String penaltyshot, String penaltyhitrate,
			String rebound, String assist, String steal, String block, String fault, String foul, String score,String playername) {
		super();
		this.season = season;
		this.team = team;
		this.games = games;
		this.gamestart = gamestart;
		this.time = time;
		this.shot = shot;
		this.hitrate = hitrate;
		this.threeball = threeball;
		this.threehitrate = threehitrate;
		this.penaltyshot = penaltyshot;
		this.penaltyhitrate = penaltyhitrate;
		this.rebound = rebound;
		this.assist = assist;
		this.steal = steal;
		this.block = block;
		this.fault = fault;
		this.foul = foul;
		this.score = score;
		this.playername=playername;
	}

	@Override
	public String toString() {
		return "PlayerSeasonData [id=" + id + ", season=" + season + ", team=" + team + ", games=" + games
				+ ", gamestart=" + gamestart + ", time=" + time + ", shot=" + shot + ", hitrate=" + hitrate
				+ ", threeball=" + threeball + ", threehitrate=" + threehitrate + ", penaltyshot=" + penaltyshot
				+ ", penaltyhitrate=" + penaltyhitrate + ", rebound=" + rebound + ", assist=" + assist + ", steal="
				+ steal + ", block=" + block + ", fault=" + fault + ", foul=" + foul + ", score=" + score
				+ ", playername=" + playername + "]";
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getGames() {
		return games;
	}

	public void setGames(String games) {
		this.games = games;
	}

	public String getGamestart() {
		return gamestart;
	}

	public void setGamestart(String gamestart) {
		this.gamestart = gamestart;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getShot() {
		return shot;
	}

	public void setShot(String shot) {
		this.shot = shot;
	}

	public String getHitrate() {
		return hitrate;
	}

	public void setHitrate(String hitrate) {
		this.hitrate = hitrate;
	}

	public String getThreeball() {
		return threeball;
	}

	public void setThreeball(String threeball) {
		this.threeball = threeball;
	}

	public String getThreehitrate() {
		return threehitrate;
	}

	public void setThreehitrate(String threehitrate) {
		this.threehitrate = threehitrate;
	}

	public String getPenaltyshot() {
		return penaltyshot;
	}

	public void setPenaltyshot(String penaltyshot) {
		this.penaltyshot = penaltyshot;
	}

	public String getPenaltyhitrate() {
		return penaltyhitrate;
	}

	public void setPenaltyhitrate(String penaltyhitrate) {
		this.penaltyhitrate = penaltyhitrate;
	}

	public String getRebound() {
		return rebound;
	}

	public void setRebound(String rebound) {
		this.rebound = rebound;
	}

	public String getAssist() {
		return assist;
	}

	public void setAssist(String assist) {
		this.assist = assist;
	}

	public String getSteal() {
		return steal;
	}

	public void setSteal(String steal) {
		this.steal = steal;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

	public String getFoul() {
		return foul;
	}

	public void setFoul(String foul) {
		this.foul = foul;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}
