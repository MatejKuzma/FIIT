// trieda na drzanie dat ohladom parametrov na filtrovanie hraca
public class LevelFilterRequest 
{
	private String dlcChoice;
	private String levelName;
	private int logicPlayedHoursChoice;
	private float playedHoursValue;
	private int averageScoreChoice;
	private float averageScoreValue;
	
//____________________________________________GETTERS&&SETTERS_______________________________
	public String getDlcChoice() {
		return dlcChoice;
	}
	public void setDlcChoice(String dlcChoice) {
		this.dlcChoice = dlcChoice;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public int getLogicPlayedHoursChoice() {
		return logicPlayedHoursChoice;
	}
	public void setLogicPlayedHoursChoice(int logicPlayedHoursChoice) {
		this.logicPlayedHoursChoice = logicPlayedHoursChoice;
	}
	public float getPlayedHoursValue() {
		return playedHoursValue;
	}
	public void setPlayedHoursValue(float playedHoursValue) {
		this.playedHoursValue = playedHoursValue;
	}
	public int getAverageScoreChoice() {
		return averageScoreChoice;
	}
	public void setAverageScoreChoice(int averageScoreChoice) {
		this.averageScoreChoice = averageScoreChoice;
	}
	public float getAverageScoreValue() {
		return averageScoreValue;
	}
	public void setAverageScoreValue(float averageScoreValue) {
		this.averageScoreValue = averageScoreValue;
	}

}
