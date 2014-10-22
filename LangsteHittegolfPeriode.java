package weerstation1;
import java.util.ArrayList;

public class LangsteHittegolfPeriode extends Grootheid{
    
    //constructor
    public LangsteHittegolfPeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Langste Hittegolf ");
    	updatePeriod(measurement2);
    }

    public void updatePeriod(ArrayList<Measurement> list){
        setPeriod(StatisticsCalculator.langsteHittegolfPeriode(list));
    }
    

}
