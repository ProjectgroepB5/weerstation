package weerstation1;
import java.util.ArrayList;

public class WindChill extends Grootheid{
	//constructor
    public WindChill(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("WindChill");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getWindChill());
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
            list.add((double)ms.getWindChill());
        }
    }
}
