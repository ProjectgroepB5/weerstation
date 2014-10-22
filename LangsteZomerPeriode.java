 
import java.util.ArrayList;

public class LangsteZomerPeriode extends Grootheid{
    private ArrayList<Double> list;
    private Periode zomerPeriode;
    
    //constructor
    public LangsteZomerPeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        zomerPeriode = new Periode("Zomer");
        updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getOutsideTemp());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        calculateMaxMin(list);
        
        int[] index = StatisticsCalculator.langsteZomersePeriode(list);
        zomerPeriode = Calculator.timeStampToPeriode( measurement2.get(index[0]).getDateStamp(), measurement2.get(index[1]).getDateStamp());
    }
    
    public void display(){
        GUIboard.writePageToMatrix("Langste zomerse periode", zomerPeriode.toString(), "");
    }
    
    public void displayGraph()
    {
        display();
    }
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add(ms.getOutsideTemp());
        }
    }
}
