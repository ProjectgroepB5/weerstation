import java.util.ArrayList;

public class RainRate extends Grootheid{
    
    //constructor
    public RainRate(Measurement measurement1, ArrayList<Measurement> measurement2){
        updateRecent(measurement1);
        update24Hour(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getRainRate());
    }
    public void update24Hour(ArrayList<Measurement> measurement2){
        
        ArrayList<Double> list = new ArrayList<Double>();
        
        for(Measurement ms : measurement2)
        {
            list.add(ms.getRainRate());
        }
        
        calculateMaxMinAvg(list);
    }
    
    public void display(){
        super.display();
        GUIboard.writePageToMatrix("Regenval in mm/h", "Gemiddelde: " + getAvg(), "");
    }
    
}
