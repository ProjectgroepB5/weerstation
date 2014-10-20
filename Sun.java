package weerstation;
 

import java.util.ArrayList;

public class Sun extends Grootheid{
	
	//fields
	private String sunSet;
	private String sunRise;
	
	//constructor
	public Sun(Measurement measurement1){
		updateRecent(measurement1);
	}
	
	//Getters and setters
	
	public String getSunSet() {
		return sunSet;
	}

	public void setSunSet(String sunSet) {
		this.sunSet = sunSet;
	}

	public String getSunRise() {
		return sunRise;
	}

	public void setSunRise(String sunRise) {
		this.sunRise = sunRise;
	}
	
	public void updateRecent(Measurement measurement1){
		setSunSet(measurement1.getSunset());
		setSunRise(measurement1.getSunrise());
	}
	
	public void updatePeriod(ArrayList<Measurement> measurement2){
	}
	
	public void display(){		
		GUIboard.clearLeft();
		GUIboard.clearRight();
		GUIboard.clearTop();
		GUIboard.writePageToMatrix("Sunset: " + sunSet, "Sunrise: " + sunRise, "");
	}
	
}
