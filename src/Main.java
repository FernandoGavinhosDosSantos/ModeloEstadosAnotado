import java.io.*;

public class Main {

	public static void main(String[] args) {

		//readInput("C:\\Users\\s_fer\\Desktop\\TCC\\input.txt");
		
		/*
		 */
		int horizon = 0;

		StateMachine fsm = new StateMachine();
		int rep = 2;

		fsm.BuildStateMachine("A B C", rep, horizon);
		fsm.BuildStateMachine("A B B C", rep, horizon);
		fsm.BuildStateMachine("B B C", rep, horizon);
		fsm.BuildStateMachine("B A", rep, horizon);
		fsm.BuildStateMachine("E F", rep, horizon);

		fsm.PrintStatemachine();

		fsm = new StateMachine();
		rep = 1;

		fsm.BuildStateMachine("A B D", rep, horizon);
		fsm.BuildStateMachine("A D B D", rep, horizon);
		fsm.BuildStateMachine("E F", rep, horizon);

		fsm.PrintStatemachine();
	}

	public static void readInput(String InputFile){

		File file = new File(InputFile);
		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(file));
			String[] param;
			String text;
			int n;

			while (true) {

				int horizon = 0, representation = 0;
				text = reader.readLine();
				n = Integer.parseInt(text);
				if (n == 0) break;

				param = reader.readLine().split(" ");
				horizon = Integer.parseInt(param[0]);
				representation = Integer.parseInt(param[1]);

				StateMachine fsm = new StateMachine();

				for (int i = 0; i < n; i++){

					text = reader.readLine();
					fsm.BuildStateMachine(text, representation, horizon);
				}

				fsm.PrintStatemachine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}
}