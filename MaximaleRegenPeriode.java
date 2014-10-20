import java.util.ArrayList;

public class MaximaleRegenPeriode extends Grootheid{
    private ArrayList<Double> list;
    
    //constructor
    public MaximaleRegenPeriode(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        updateRecent(measurement1);
        update24Hour(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getRainRate());
    }
    public void update24Hour(ArrayList<Measurement> measurement2){
        createList(measurement2);
        calculateMaxMinAvg(list);
    }
    
    public void display(){
        GUIboard.writePageToMatrix("Totale regenval", StatisticsCalculator.maximaleRegenPeriode(list) + "", "");
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
