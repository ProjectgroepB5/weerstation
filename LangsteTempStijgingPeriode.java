 
import java.util.ArrayList;

public class LangsteTempStijgingPeriode extends Grootheid
{
    private ArrayList<Double> list;
    private Periode StijgingPeriode;
    
    //constructor
    public LangsteTempStijgingPeriode(Measurement measurement1, ArrayList<Measurement> measurement2)
    {
        list = new ArrayList<Double>();
        StijgingPeriode = new Periode("TempStijgingsperiode");
        updatePeriod(measurement2);
    }
    
    public void updatePeriod(ArrayList<Measurement> measurement2)
    {
        createList(measurement2);
        
        int[] index = StatisticsCalculator.langsteTempStijgingPeriode(list);
        StijgingPeriode= Calculator.timeStampToPeriode( measurement2.get(index[0]).getDateStamp(), measurement2.get(index[1]).getDateStamp());
    }
    
    public void display()
    {
        GUIboard.writePageToMatrix("Langste temp.stijging", StijgingPeriode.toString(), "", true, true);
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
            list.add(ms.getOutsideTemp());
        }
    }
}
