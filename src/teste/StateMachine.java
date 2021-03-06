package teste;

import java.util.ArrayList;
import java.util.Arrays;

public class StateMachine {

	private final int SET_REPRESENTATION = 1;
	private final int SEQUENCE_REPRESENTATION = 2;
	private final int MULTISET_REPRESENTATION = 3;
	
	private ArrayList<State> startingPoints = new ArrayList<State>();
	private ArrayList<State> stateList = new ArrayList<State>();
	
	private State FindState(String target){
		
		for (State s : stateList){
			if (s.getName().equals(target)) return s;
		}
		
		State newState = new State(target);
		this.stateList.add(newState);
		return newState;
	}
	
	public String StateName(String path, int horizon){
		
		if (horizon == 0) return path;
		
		int cont = 0;
		for (int i = path.length() - 1; i > 0; i--){
			
			if (path.charAt(i) == '_') cont++;
			if (cont == horizon) return path.substring(i + 1, path.length() - 1);
		}
		
		return "";
	}

	/**
	 * this method builds the state machine that represents the process
	 * 
	 * @param path sequence of states that the process instance have passed trough separated by spaces
	 * @param representation 1=set representation 2=sequence representation
	 * @param horizon 0=infinite
	 */
	public void BuildStateMachine(String path, int representation, int horizon){
		
		String[] nodes = path.split(" ");
		boolean newPoint = true;
		State startingPoint = null;

		for (State s : this.startingPoints){
			if (nodes[0].equals(s.getName())){
				newPoint = false;
				startingPoint = s;
				break;
			}
		}

		if (newPoint){
			startingPoint = FindState(nodes[0]);
			this.startingPoints.add(startingPoint);
		}

		State currentNode = startingPoint;

		for (int i = 1; i < nodes.length; i++){

			nodes[i] = nodes[i - 1] + "_" + nodes[i];
			String nextNode = StateName(nodes[i], horizon);
			
			if(representation == SET_REPRESENTATION){
				String[] aNextNode = nextNode.split("_");
				Arrays.sort(aNextNode);
				
				nextNode = aNextNode[0];
				for (int j = 1; j < aNextNode.length; j++){
					if (!aNextNode[j].equals(aNextNode[j - 1])) nextNode += "_" + aNextNode[j];
				}
			}

			State target = currentNode.TransitionsTo(nextNode, representation);
			if(target == null){
				target = FindState(nextNode);
				currentNode.AddTransition(target);
			}

			currentNode = target;
		}
	}

	public void PrintStatemachine(){

		String result = "";

		for (State s : this.startingPoints){
			result = PrintTransitions(s, result);
		}

		System.out.println(result);
	}

	private String PrintTransitions(State node, String result){

		if(!result.contains("*" + node.getName() + "->")){

			for (State s : node.getTransitions()){
				result += "*" + node.getName() + "->" + s.getName() + " ";
				result = PrintTransitions(s, result);
			}
		}
		return result;
	}
}
