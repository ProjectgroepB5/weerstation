package weerstation1;
import java.util.ArrayList;

public class InsideHum extends Grootheid{
    
    //constructor
    public InsideHum(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Luchtv. Binnen");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getInsideHum());
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
            list.add((double)ms.getRawInsideHum());
        }
    }
    
    public double calculate(double value){
    	return Calculator.luchtVochtigheid((short)value);
    }
}
