package weerstation;

import java.util.ArrayList;

public class WindChill {
	
	//fields
	private double currentWindChill;
	private double maxWindChill;
	private double minWindChill;
	private double avgWindChill;
	
	//constructor
	public WindChill(Measurement measurement1, ArrayList<Measurement> measurement2){
		updateRecent(measurement1);
		update24Hour(measurement2);
	}
	
	//getters & setters
	public double getCurrentWindChill() {
		return currentWindChill;
	}

	public void setCurrentWindChill(double currentWindChill) {
		this.currentWindChill = currentWindChill;
	}

	public double getMaxWindChill() {
		return maxWindChill;
	}

	public void setMaxWindChill(double maxWindChill) {
		this.maxWindChill = maxWindChill;
	}

	public double getMinWindChill() {
		return minWindChill;
	}

	public void setMinWindChill(double minWindChill) {
		this.minWindChill = minWindChill;
	}

	public double getAvgWindChill() {
		return avgWindChill;
	}

	public void setAvgWindChill(double avgWindChill) {
		this.avgWindChill = Math.round(avgWindChill*100)/100;
	}
	
	//Methods
	private void calculateMaxMinAvgWindChill(ArrayList<Measurement> laatste24uur){
		double max = 0;
		double min = 1000;
		float avg = 0;
		for(Measurement minut :laatste24uur){
			if(minut.getWindChill() > max){
				max = minut.getWindChill();
			}
			if(minut.getWindChill() <  min){
				min = minut.getWindChill();
			}
			avg += minut.getWindChill();
		}
		avg /= laatste24uur.size();
		
		setAvgWindChill(avg);
		setMaxWindChill(max);
		setMinWindChill(min);
	}

	
	public void updateRecent(Measurement measurement1){
		setCurrentWindChill(measurement1.getWindChill());
	}
	public void update24Hour(ArrayList<Measurement> measurement2){
		calculateMaxMinAvgWindChill(measurement2);
	}
	
	public void display(){
		GUIboard.writeUpperDigits(getCurrentWindChill());
		GUIboard.writeLeftDigits(getMaxWindChill());
		GUIboard.writeRightDigits(getMinWindChill());
		GUIboard.writePageToMatrix("Gevoelstemp in C", "Gemiddelde: " + avgWindChill, "");
	}
	
}
