 
import java.util.ArrayList;

public class DewPoint extends Grootheid
{
    public ArrayList<Double> list;
    
    //constructor
    public DewPoint(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getDewPoint());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        calculateMaxMin(list);
        setMedian(StatisticsCalculator.median(list));
    }
    
    public void display(){
        super.display();
        GUIboard.writePageToMatrix("Dauwpunt", "Mediaan: " + getMedian(), "");
    }
    
    public void displayGraph()
    {
        GUIboard.writeGraphToMatrix(list, getMin(), getMax());
    }
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add(ms.getDewPoint());
        }
    }
}
