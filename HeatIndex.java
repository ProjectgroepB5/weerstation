package weerstation1;
import java.util.ArrayList;

public class HeatIndex extends Grootheid{
	//constructor
    public HeatIndex(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("HeatIndex");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getHeatIndex());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        maxMin();
        median();
    }
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add((double)ms.getHeatIndex());
        }
    }
}
