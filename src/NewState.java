import java.util.ArrayList;

public class NewState {
	
	//constructor
	public NewState(String name, String status){
		this.name = name;
		this.status = status;
		this.isStartPoint = false;
		this.transitions = new ArrayList<AnnotatedTransition>();
	}
	
	//attributes
	public String name;
	public String status;
	public boolean isStartPoint;
	public ArrayList<AnnotatedTransition> transitions;
	
	public AnnotatedTransition TransitionsTo(String target){

		for (AnnotatedTransition s : this.transitions){
			if(s.name.equals(target)) return s;
		}

		return null;
	}
	
	public AnnotatedTransition AddTransition(NewState target, long timestamp){
		
		AnnotatedTransition newTr = new AnnotatedTransition(target, timestamp);
		this.transitions.add(newTr);
		
		return newTr;
	}
}
