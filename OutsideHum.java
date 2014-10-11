import java.util.ArrayList;

public class OutsideHum extends Grootheid{
    
    //constructor
    public OutsideHum(Measurement measurement1, ArrayList<Measurement> measurement2){
        updateRecent(measurement1);
        update24Hour(measurement2);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getOutsideHum());
    }
    public void update24Hour(ArrayList<Measurement> measurement2){
        
        ArrayList<Double> list = new ArrayList<Double>();
        
        for(Measurement ms : measurement2)
        {
            list.add(ms.getOutsideHum());
        }
        
        calculateMaxMinAvg(list);
    }
    
    public void display(){
        super.display();
        GUIboard.writePageToMatrix("Luchtv. Buiten", "Gemiddelde: " + getAvg(), "");
    }
    
}
