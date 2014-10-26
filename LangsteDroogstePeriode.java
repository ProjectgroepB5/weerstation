import java.util.ArrayList;

public class LangsteDroogstePeriode extends Grootheid{
    private ArrayList<Double> list;
    private int langstePeriode;
    
    public LangsteDroogstePeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        langstePeriode = 0;
        updatePeriod(measurement2);
    }

    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2); 
        langstePeriode = StatisticsCalculator.langsteDroogstePeriode(list);
    }
    
    public void display(String periode, boolean knop1, boolean knop2){
        GUIboard.writePageToMatrix("Langste Droge Periode", langstePeriode + "", periode, knop1, knop2);
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
