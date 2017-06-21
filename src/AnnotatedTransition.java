
import java.util.ArrayList;

public class AnnotatedTransition {

	public AnnotatedTransition(NewState state, long timestamp) {

		this.name = state.name;
		this.state = state;
		this.timestamps = new ArrayList<Long>();
		this.timestamps.add(timestamp);
	}

	public String name;
	public NewState state;
	public ArrayList<Long> timestamps;
	
	private double average = -1;

	public void addOccurrence(long timestamp){

		this.timestamps.add(timestamp);
	}
	
	public double AvgDelay(){
		
		if (this.average == -1){
			int cont = 0;
			this.average = 0;

			for (long ts : timestamps){
				this.average += (double) ts;
				cont++;
			}

			this.average = this.average / cont;
		}
		
		return this.average;
	}
}
