package weerstation1;
import java.util.ArrayList;

public class OutsideHum extends Grootheid{
	//constructor
    public OutsideHum(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Luchtv. Buiten");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getOutsideHum());
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
            list.add((double)ms.getRawOutsideHum());
        }
    }
    
    public double calculate(double value){
    	return Calculator.luchtVochtigheid((short)value);
    }
}
