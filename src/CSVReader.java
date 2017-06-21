
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVReader {

	public NewStateMachine read(String path, int idCol, int[] statusCol, int timestampCol) {

		NewStateMachine ats = new NewStateMachine();

		String line = "";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			String header = br.readLine();
			
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] eventLog = line.split(",");
				
				//add state
				String status = eventLog[statusCol[0]];
				for (int i = 1; i < statusCol.length; i++) status += "_" + eventLog[statusCol[i]];
				ats.AddState(status, ats.FindInstance(eventLog[idCol]) == null);
				
				//add process instance
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
				    Date ts = f.parse(eventLog[timestampCol]);
				    long hours = ts.getTime() / 3600000;
				    ats.AddInstance(eventLog[idCol], status, hours);
				} catch (ParseException e) {
				    e.printStackTrace();
				}
			}

			return ats;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}