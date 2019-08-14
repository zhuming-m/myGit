package nba.domin;

public class Player {

	private int id;
	private String img;
	private String name;
	private String number;
	private String position;
	private String height;
	private String weight;
	private String birthday;
	private String contrast;
	private String team;
	public Player(String img,String name, String number, String position, String height, String weight, String birthday,
			String contrast,String team) {
		super();
		this.img=img;
		this.name = name;
		this.number = number;
		this.position = position;
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.contrast = contrast;
		this.team=team;
	}
	@Override
	public String toString() {
		return "Player [id=" + id + ", img=" + img + ", name=" + name + ", number=" + number + ", position=" + position
				+ ", height=" + height + ", weight=" + weight + ", birthday=" + birthday + ", contrast=" + contrast
				+ ", team=" + team + "]";
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Player() {
		super();
	}
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getContrast() {
		return contrast;
	}
	public void setContrast(String contrast) {
		this.contrast = contrast;
	}
	
	
	
}
