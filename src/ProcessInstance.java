import java.util.ArrayList;

public class ProcessInstance {
	
	public ProcessInstance(String id, String state, long timestamp){
		
		this.id = id;
		this.transitions = state;
		this.timestamps = new ArrayList<Long>();
		this.timestamps.add(timestamp);
	}
	
	public String id;
	public String transitions;
	public ArrayList<Long> timestamps;
	
	public void addStep(String state, long timestamp){

		if (this.transitions.isEmpty()) this.transitions = state;
		else this.transitions += " " + state;
		this.timestamps.add(timestamp);
	}
	
}
