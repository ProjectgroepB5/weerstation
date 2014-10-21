import java.util.ArrayList;

public class LangsteRegenPeriode extends Grootheid{
    private ArrayList<Double> list;
    private Periode regenPeriode;
    
    //constructor
    public LangsteRegenPeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        regenPeriode = new Periode();
        updateRecent(measurement1);
        update24Hour(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getOutsideTemp());
    }
    public void update24Hour(ArrayList<Measurement> measurement2){
        createList(measurement2);
        
        int[] index = StatisticsCalculator.langsteRegenPeriode(list);
        regenPeriode = Calculator.timeStampToPeriode( measurement2.get(index[0]).getDateStamp(), measurement2.get(index[1]).getDateStamp());
    }
    
    public void display()
    {
        GUIboard.writePageToMatrix("Langste Regen Periode", regenPeriode.toString(), "");
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
            list.add(ms.getRainRate());
        }
    }
}
