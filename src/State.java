
import java.util.ArrayList;

public class State {

	//constructor
	public State(String name){
		this.name = name;
		this.transitions = new ArrayList<State>();
	}

	//attributes
	private String name;
	private ArrayList<State> transitions;

	//getters'n setters
	public String getName() {
		return name;
	}
	public ArrayList<State> getTransitions() {
		return transitions;
	}

	//methods
	/**
	 * @param target the name of the node we want to know if this node transitions to
	 * @return the node itself if there is a transition between this node and the one received in param 'target' or NULL if this transition doesn't exist
	 */
	public State TransitionsTo(String target, int representation){
		switch (representation){
		case 1:
			return SetTransitionCheck(target);
		case 2:
			return SequenceTransitionCheck(target);
		default:
			return null;
		}
	}

	public State SetTransitionCheck(String target){

		for (State s : this.transitions){
			if(s.getName().equals(target)) return s;
		}

		return null;
	}

	public State SequenceTransitionCheck(String target){

		for (State s : this.transitions){
			if(s.getName().equals(target)) return s;
		}

		return null;
	}

	/**
	 * @param target the node we want to add a transition to
	 */
	public void AddTransition(State target){

		this.transitions.add(target);
	}
}
