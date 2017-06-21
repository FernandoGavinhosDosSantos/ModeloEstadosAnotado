import java.util.ArrayList;
import java.util.Arrays;

public class NewStateMachine {

	private final int SET_REPRESENTATION = 1;
	private final int SEQUENCE_REPRESENTATION = 2;
	private final int MULTISET_REPRESENTATION = 3;
	private final String[] STATE_ID= {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private int freeStateId = 0;

	public NewStateMachine() {

		this.startingPoints = new ArrayList<NewState>();
		this.stateList = new ArrayList<NewState>();
		this.instanceList = new ArrayList<ProcessInstance>();
	}

	private ArrayList<NewState> startingPoints;
	public ArrayList<NewState> stateList;
	public ArrayList<ProcessInstance> instanceList;

	private NewState FindStateByStatus(String status){

		for (NewState s : stateList){
			if (s.status.equals(status)) return s;
		}

		return null;
	}

	private NewState FindStateByName(String name){

		for (NewState s : stateList){
			if (s.name.equals(name)) return s;
		}

		return null;
	}

	public ProcessInstance FindInstance(String id){

		for (ProcessInstance pi : instanceList){

			if(pi.id.equals(id)){
				return pi;
			}
		}

		return null;
	}

	public NewState AddState(String name, String status){

		NewState st = new NewState(name, status);
		this.stateList.add(st);

		return st;
	}

	public void AddState(String status, boolean isStartingPoint){

		NewState st = FindStateByStatus(status);

		if(st == null){
			st = new NewState(STATE_ID[freeStateId], status);
			freeStateId++;
			this.stateList.add(st);
		}

		if (isStartingPoint){
			this.startingPoints.add(st);
			st.isStartPoint = true;
		}
	}

	public void AddInstance(String id, String state, long timestamp){

		ProcessInstance pi = FindInstance(id);

		if(pi != null)
			pi.addStep(FindStateByStatus(state).name, timestamp);
		else{
			NewState teste = FindStateByStatus(state);
			this.instanceList.add(new ProcessInstance(id, teste.name, timestamp));
		}
	}

	public void BuildStateMachine(String path, ArrayList<Long> timestampList, int representation, int horizon){

		String[] nodes = path.split(" ");
		NewState currentNode = FindStateByName(nodes[0]);

		for (int i = 1; i < nodes.length; i++){

			long delay = (timestampList.get(i) - timestampList.get(i - 1));

			nodes[i] = nodes[i - 1] + nodes[i];
			int index = (horizon > 0 ? nodes[i].length() - horizon : 0);
			String nextNode = nodes[i].substring((index > 0 ? index : 0));

			if(representation == SET_REPRESENTATION){
				String[] aNextNode = nextNode.split("");
				Arrays.sort(aNextNode);

				nextNode = aNextNode[0];
				for (int j = 1; j < aNextNode.length; j++){
					if (!aNextNode[j].equals(aNextNode[j - 1])) nextNode += aNextNode[j];
				}
			}

			AnnotatedTransition target = currentNode.TransitionsTo(nextNode);
			if(target == null){
				NewState newTarget = FindStateByName(nextNode);
				if (newTarget == null) newTarget = AddState(nextNode, currentNode.status + "_" + FindStateByName(nextNode.substring(nextNode.length() - 1)).status);
				target = currentNode.AddTransition(newTarget, delay);
			}
			else target.addOccurrence(delay);

			currentNode = target.state;
		}
	}

	public void PrintStateCollection(int representation, int horizon){

		String h = (horizon == 0 ? "infinite" : horizon + ""); 

		switch (representation){
		case SET_REPRESENTATION:
			System.out.println("[SET REPRESENTATION] - HORIZON: " + h);
			break;
		case SEQUENCE_REPRESENTATION:
			System.out.println("[SEQUENCE REPRESENTATION] - HORIZON: " + h);
			break;
		case MULTISET_REPRESENTATION:
			System.out.println("[MULTISET REPRESENTATION] - HORIZON: " + h);
			break;
		}

		for (NewState s : this.stateList){
			if (s.name.length() == 1) System.out.println("Estado '" + s.name + "' (" + s.status + ") ");
		}
	}

	public void PrintStatemachine(){

		String result = "";

		for (NewState s : this.startingPoints){
			result = PrintTransitions(s, result);
		}

		System.out.println(result);
	}

	private String PrintTransitions(NewState node, String result){

		if(!result.contains("*" + node.name + "->")){

			for (AnnotatedTransition s : node.transitions){
				result += "*" + node.name + "->" + s.name + " (avg delay: " + s.AvgDelay() + "h) \n";
				result = PrintTransitions(s.state, result);
			}
		}
		return result;
	}
}
