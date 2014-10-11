import java.util.ArrayList;

public class Grootheid
{
    // instance variables - replace the example below with your own
    public double avg;
    public double max;
    public double min;
    public double current;

    //constructor
    public Grootheid(){
        avg = 0;
        max = 0;
        min = 0;
        current = 0;
    }
    
    //getters & setters
    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max= max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
    
    //Methods
    public void calculateMaxMinAvg(ArrayList<Double> laatste24uur){
        double tempMax = 0;
        double tempAvg = 0;
        double tempMin = laatste24uur.get(0);
        
        for(double ms : laatste24uur){
            if(ms > tempMax){
                tempMax = ms;
            }
            if(ms <  tempMin){
                tempMin = ms;
            }
            tempAvg += ms;
        }
        tempAvg /= laatste24uur.size();
        
        setMax(tempMax);
        setMin(tempMin);
        setAvg(tempAvg);
    }

    public void updateRecent(Measurement measurement1){

    }
    public void update24Hour(ArrayList<Measurement> measurement2){
        
    }
    
    public void display(){
        GUIboard.writeUpperDigits(getCurrent());
        GUIboard.writeLeftDigits(getMax());
        GUIboard.writeRightDigits(getMin());
    }
    
    public void displayGraph()
    {
    }
}
