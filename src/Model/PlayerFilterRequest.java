// trieda na drzanie dat ohladom filtrovacieho requestu pre player
public class PlayerFilterRequest 
{
	private int sexRequest;
	private String name;
	private int logicRequest;
	private float sumValue;
	
//____________________________GETTERS&&SETTERS_______________________________________________
	public int getSexRequest() {
		return sexRequest;
	}
	public void setSexRequest(int sexRequest) {
		this.sexRequest = sexRequest;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLogicRequest() {
		return logicRequest;
	}
	public void setLogicRequest(int logicRequest) {
		this.logicRequest = logicRequest;
	}
	public float getSumValue() {
		return sumValue;
	}
	public void setSumValue(float sumValue) {
		this.sumValue = sumValue;
	}

}
