 
import java.util.ArrayList;

public class LangsteRegenPeriode extends Grootheid{
    private ArrayList<Double> list;
    private Periode regenPeriode;
    
    //constructor
    public LangsteRegenPeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        regenPeriode = new Periode("Regen");
        update24Hour(measurement2);
    }

    public void update24Hour(ArrayList<Measurement> measurement2){
        createList(measurement2);
        int[] index = StatisticsCalculator.langsteRegenPeriode(list);
        regenPeriode = Calculator.timeStampToPeriode( measurement2.get(index[0]).getDateStamp(), measurement2.get(index[1]).getDateStamp());
    }
    
    public void display(String periode, boolean knop1, boolean knop2, double batt)
    {
        GUIboard.writePageToMatrix("Langste Regen Periode", regenPeriode.toString(), periode, knop1, knop2, batt);
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
            list.add(ms.getRainRate());
        }
    }
}
