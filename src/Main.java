
import java.io.*;

public class Main {
	
	public static void main(String[] args) {

		readInputTXT("C:/Users/s_fer/Desktop/TCC/input/param.txt");
		//readInputTXT(args[0]);
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
}