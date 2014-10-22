package weerstation1;
import java.util.ArrayList;

public class CloudHeight extends Grootheid{
	//constructor
    public CloudHeight(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Wolkhoogte");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getCloudHeight());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        modus();
    }
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add((double)ms.getCloudHeight());
        }
    }
}
