import java.util.ArrayList;

public class AvgWindspeed extends Grootheid{
    
    //constructor
    public AvgWindspeed(Measurement measurement1, ArrayList<Measurement> measurement2){
        updateRecent(measurement1);
        update24Hour(measurement2);
    }
    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getAvgWindSpeed());
    }
    
    public void update24Hour(ArrayList<Measurement> measurement2){
        
        ArrayList<Double> list = new ArrayList<Double>();
        
        for(Measurement ms : measurement2)
        {
            list.add(ms.getAvgWindSpeed());
        }
        
        calculateMaxMinAvg(list);
    }
    
    public void display(){
        super.display();
        GUIboard.writePageToMatrix("Windsnelheid in m/s", "Gemiddelde: " + getAvg(), "");
    }
    
}
