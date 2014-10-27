 

    
import java.util.ArrayList;

public class GraadDagen extends Grootheid{
    private ArrayList<Double> list;
    private double graadDagen;
    
    //constructor
    public GraadDagen(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        graadDagen = 0;
        updatePeriod(measurement2);
    }

    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2); 
        graadDagen = StatisticsCalculator.graadDagen(list);
    }
    
    public void display(String periode, boolean knop1, boolean knop2, short batt){
        GUIboard.writePageToMatrix("Aantal Graaddagen", graadDagen + "", periode, knop1, knop2, batt);
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

