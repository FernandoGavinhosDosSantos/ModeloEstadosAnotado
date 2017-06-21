
import java.io.*;

public class Main {
	
	public static void main(String[] args) {

		//readInput("C:/Users/s_fer/Desktop/TCC/input/input1.txt");
		//readInputTXT("C:/Users/s_fer/Desktop/TCC/input/input2.txt");
		readInputTXT(args[0]);
	}

	public static void readInputTXT(String InputFile){

		File file = new File(InputFile);
		BufferedReader reader = null;
		CSVReader csvReader = new CSVReader();

		try {

			reader = new BufferedReader(new FileReader(file));
			String line;
			
			String csvPath = reader.readLine();
			String[] colsIdTs = reader.readLine().split(" ");
			String[] colStatusTemp = reader.readLine().split(" ");
			int[] colStatus = new int[colStatusTemp.length];
			
			for (int i = 0; i < colStatus.length; i++)
				colStatus[i] = Integer.parseInt(colStatusTemp[i]);
			
			
			while ((line = reader.readLine()) != null) {
				
				NewStateMachine ats = csvReader.read(csvPath, Integer.parseInt(colsIdTs[0]), colStatus, Integer.parseInt(colsIdTs[1]));

				String[] param = line.split(" ");
				int horizon = Integer.parseInt(param[0]);
				int representation = Integer.parseInt(param[1]);
				
				//percorre estados construindo transições
				for (ProcessInstance pi: ats.instanceList){
					ats.BuildStateMachine(pi.transitions, pi.timestamps, representation, horizon);
				}

				ats.PrintStateCollection(representation, horizon);
				ats.PrintStatemachine();
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