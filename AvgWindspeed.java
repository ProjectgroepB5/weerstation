 

import java.util.ArrayList;

public class AvgWindspeed {
	
	//fields
	private Measurement laatsteMeting;
	private ArrayList<Measurement> laatste24Uur;
	private Calculator calculator;
	private double currentWindSpeed;
	private double maxWindSpeed;
	private double minWindSpeed;
	private double avgWindSpeed;
	
	//constructor
	public AvgWindspeed(Measurement measurement1, ArrayList<Measurement> measurement2){
		calculator = new Calculator();
		updateRecent(measurement1);
		update24Hour(measurement2);
		
	}
	
	//getters & setters
	public double getCurrentWindSpeed() {
		return currentWindSpeed;
	}

	public void setCurrentWindSpeed(double currentWindSpeed) {
		if(currentWindSpeed >= 0){
			this.currentWindSpeed = currentWindSpeed;
		}
	}

	public double getMaxWindSpeed() {
		return maxWindSpeed;
	}

	public void setMaxWindSpeed(double maxWindSpeed) {
		if(maxWindSpeed >= 0){
			this.maxWindSpeed = maxWindSpeed;
		}
	}

	public double getMinWindSpeed() {
		return minWindSpeed;
	}

	public void setMinWindSpeed(double minWindSpeed) {
		if(minWindSpeed >= 0){
			this.minWindSpeed = minWindSpeed;
		}
	}

	public double getAvgWindSpeed() {
		return avgWindSpeed;
	}

	public void setAvgWindSpeed(double avgWindSpeed) {
		this.avgWindSpeed = avgWindSpeed;
	}
	
	//Methods
	private void calculateMaxMinAvgWindSpeed(){
		int max = 0;
		int min = 1000;
		float avg = 0;
		for(Measurement minut :laatste24Uur){
			if(minut.getRawAvgWindSpeed() > max){
				max = minut.getRawAvgWindSpeed();
			}
			if(minut.getRawAvgWindSpeed() <  min){
				min = minut.getRawAvgWindSpeed();
			}
			avg += minut.getRawWindSpeed();
		}
		avg /= laatste24Uur.size();
		
		setAvgWindSpeed(calculator.windSnelheid((short)avg));
		setMaxWindSpeed(calculator.windSnelheid((short)max));
		setMinWindSpeed(calculator.windSnelheid((short)min));
	}
	
	public void updateRecent(Measurement measurement1){
		this.laatsteMeting = measurement1;
		setCurrentWindSpeed(laatsteMeting.getAvgWindSpeed());
	}
	public void update24Hour(ArrayList<Measurement> measurement2){
		this.laatste24Uur = measurement2;
		calculateMaxMinAvgWindSpeed();
	}
	
	public void display(){
		GUIboard.writeUpperDigits(getCurrentWindSpeed());
		GUIboard.writeLeftDigits(getMaxWindSpeed());
		GUIboard.writeRightDigits(getMinWindSpeed());
	}
	
}
