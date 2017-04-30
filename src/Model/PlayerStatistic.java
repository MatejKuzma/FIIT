// trieda na uchovanie dat pre štatistiku hraca
public class PlayerStatistic 
{
	private int id;
	private int score;
	private int playhours;
	
	PlayerStatistic(int id, int score, int playhours)
	{
		this.setId(id);
		this.setPlayhours(playhours);
		this.setScore(score);
	}
	
//___________________________________GETTERS&SETTERS______________________________________________
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getPlayhours() {
		return playhours;
	}
	public void setPlayhours(int playhours) {
		this.playhours = playhours;
	}
	
	
}
