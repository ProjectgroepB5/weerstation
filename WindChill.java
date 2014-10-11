import java.util.ArrayList;

public class WindChill extends Grootheid{
    public ArrayList<Double> list;
    
    //constructor
    public WindChill(Measurement measurement1, ArrayList<Measurement> measurement2){
        list = new ArrayList<Double>();
        updateRecent(measurement1);
        update24Hour(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getWindChill());
    }
    public void update24Hour(ArrayList<Measurement> measurement2){
        createList(measurement2);
        calculateMaxMinAvg(list);
    }
    
    public void display(){
        super.display();
        GUIboard.writePageToMatrix("Gevoelstemp", "Gemiddelde: " + getAvg(), "");
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
            list.add(ms.getWindChill());
        }
    }
}
