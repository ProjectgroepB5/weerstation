package weerstation1;
 
public class Sun extends Grootheid{
	
	//fields
	private String sunSet;
	private String sunRise;
	
	//constructor
	public Sun(Measurement measurement1){
		updateRecent(measurement1);
		setName("Zons opkomst: " + sunRise);
		setCustom("Zons ondergang: " + sunSet);		
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
	
    public void displayGraph()
    {
    }
	
	public void updateRecent(Measurement measurement1){
		setSunSet(measurement1.getSunset());
		setSunRise(measurement1.getSunrise());
	}
}
