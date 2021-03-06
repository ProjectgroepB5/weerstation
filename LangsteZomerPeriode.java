 
import java.util.ArrayList;

public class LangsteZomerPeriode extends Grootheid{
    
    //constructor
    public LangsteZomerPeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
        setName("Zomerse periode");
        updatePeriod(measurement2);
    }

    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        int[] index = StatisticsCalculator.langsteZomersePeriode(list);
        if(index[0]==index[1] && index[0]==0)
        {
            setCustom("-");
        }
        else
        {
            setPeriod(Calculator.timeStampToPeriode( measurement2.get(index[0]).getDateStamp(), measurement2.get(index[1]).getDateStamp()));
        }
    }
    
    public void displayGraph(){}
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add((double)ms.getRawOutsideTemp());
        }
    }
}
