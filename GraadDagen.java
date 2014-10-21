
    
import java.util.ArrayList;

public class GraadDagen extends Grootheid{
    private ArrayList<Double> list;
    private double graadDagen;
    
    //constructor
    public GraadDagen(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        graadDagen = 0;
        updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getOutsideTemp());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
                
        graadDagen = StatisticsCalculator.graadDagen(list);
    }
    
    public void display(){
        GUIboard.writePageToMatrix("Aantal Graaddagen", graadDagen + "", "");
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

