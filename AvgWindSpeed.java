package weerstation1;
import java.util.ArrayList;

public class AvgWindSpeed extends Grootheid{
	//constructor
    public AvgWindSpeed(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Gem. Windsnelheid");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getAvgWindSpeed());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        maxMin();
        afwijking();
    }
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add((double)ms.getRawAvgWindSpeed());
        }
    }
    
    public double calculate(double value){
    	return Calculator.windSnelheid((short)value);
    }
}
