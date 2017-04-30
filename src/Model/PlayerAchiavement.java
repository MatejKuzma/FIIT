// trieda na drzanie dat ohladom achiavementov hraca
public class PlayerAchiavement 
{
	private int id;
	private String achiavement;
	private String earnTime;
	
	PlayerAchiavement(int id, String achiav, String earnTime)
	{
		this.setId(id);
		this.setAchiavement(achiav);
		this.setEarnTime(earnTime);
	}
	
//_____________________________________GETTERS&SETTERS_______________________________________
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAchiavement() {
		return achiavement;
	}
	public void setAchiavement(String achiavement) {
		this.achiavement = achiavement;
	}
	public String getEarnTime() {
		return earnTime;
	}
	public void setEarnTime(String earnTime) {
		this.earnTime = earnTime;
	}
	
	
}
