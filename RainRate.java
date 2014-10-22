package weerstation1;
import java.util.ArrayList;

public class RainRate extends Grootheid{
	//constructor
    public RainRate(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Regenval in mm/h");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getRainRate());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        maxMin();
        avg();
    }
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add((double)ms.getRawRainRate());
        }
    }
    
    public double calculate(double value){
    	return Calculator.regenmeter((short)value);
    }
}
