 
import java.util.ArrayList;

public class Barometer extends Grootheid{
    public ArrayList<Double> list;
    
    //constructor
    public Barometer(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        updateRecent(measurement1);
        updatePeriod(measurement2);
    }
    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getBarometer());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        calculateMaxMin(list);
        setMode(StatisticsCalculator.modus(list));
    }
    
    public void display(){
        super.display();
		GUIboard.clearLeft();
		GUIboard.clearRight();
        GUIboard.writePageToMatrix("Luchtdruk", "Modus: " + getMode(), "");
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
            list.add(ms.getBarometer());
        }
    }
}
