// trieda na drzanie dat ohladom hracovych dátach
public class PlayerData 
{
	private int    id;
	private String nickname;
	private char   sex;
	private String country;
	private int countryInt;
	
//_____________________________________GETTERS&SETTERS_______________________________________
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getCountryInt() {
		return countryInt;
	}
	public void setCountryInt(int country) {
		this.countryInt = country;
	}
}
