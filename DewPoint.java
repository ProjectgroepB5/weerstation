package weerstation1;
import java.util.ArrayList;

public class DewPoint extends Grootheid
{
	//constructor
    public DewPoint(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Dauwpunt");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getDewPoint());
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
            list.add((double)ms.getDewPoint());
        }
    }
}
