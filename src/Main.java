
public class Main {

	public static void main(String[] args) {
		
		int horizon = 0;
		
		StateMachine fsm = new StateMachine();
		int rep = 2;

		fsm.BuildStateMachine("A B C", rep, horizon);
		fsm.BuildStateMachine("A B B C", rep, horizon);
		fsm.BuildStateMachine("B B C", rep, horizon);
		fsm.BuildStateMachine("B A", rep, horizon);
		
		fsm.PrintStatemachine();
		
		fsm = new StateMachine();
		rep = 1;

		fsm.BuildStateMachine("A B D E", rep, horizon);
		fsm.BuildStateMachine("A D A B E F", rep, horizon);
		
		fsm.PrintStatemachine();
	}
}