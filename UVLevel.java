 
import java.util.ArrayList;

public class UVLevel extends Grootheid{
    public ArrayList<Double> list;
    
    //constructor
    public UVLevel(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getUVLevel());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        calculateMaxMin(list);
        setAvg(StatisticsCalculator.avg(list));
    }
    
    public void display(){
        super.display();
        GUIboard.writePageToMatrix("UV Level", "Gemiddelde: " + getAvg(), "");
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
            list.add(ms.getUVLevel());
        }
    }
}
