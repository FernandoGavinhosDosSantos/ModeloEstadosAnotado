import java.util.ArrayList;
import java.util.LinkedList;

public class ProcessInstance {
	
	public ProcessInstance(String id, String state, long timestamp){
		
		this.id = id;
		this.transitions = new LinkedList<String>();
		this.transitions.add(state);
		this.timestamps = new ArrayList<Long>();
		this.timestamps.add(timestamp);
	}
	
	public String id;
	public LinkedList<String> transitions;
	public ArrayList<Long> timestamps;
	
	public void addStep(String state, long timestamp){

		this.transitions.add(state);
		this.timestamps.add(timestamp);
	}
	
}
