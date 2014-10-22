package weerstation1;
import java.util.ArrayList;

public class MaximaleRegenPeriode extends Grootheid{
    
    //constructor
    public MaximaleRegenPeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Totale regenval");
        updatePeriod(measurement2);
    }

    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        setCustom(StatisticsCalculator.maximaleRegenPeriode(list) + " ");
    }
    

    public void displayGraph()
    {
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
}
