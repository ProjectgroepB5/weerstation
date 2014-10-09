import java.util.ArrayList;

public class RainRate {
    
    //fields
    private Measurement laatsteMeting;
    private ArrayList<Measurement> laatste24Uur;
    private Calculator calculator;
    private double currentRainRate;
    private double maxRainRate;
    private double minRainRate;
    private double avgRainRate;
    
    //constructor
    public RainRate(Measurement measurement1, ArrayList<Measurement> measurement2){
        calculator = new Calculator();
        updateRecent(measurement1);
        update24Hour(measurement2);
        
    }
    
    //getters & setters
    public double getCurrentRainRate() {
        return currentRainRate;
    }

    public void setCurrentRainRate(double currentRainRate) {
        if(currentRainRate >= 0){
            this.currentRainRate = currentRainRate;
        }
    }

    public double getMaxRainRate() {
        return maxRainRate;
    }

    public void setMaxRainRate(double maxRainRate) {
        if(maxRainRate >= 0){
            this.maxRainRate = maxRainRate;
        }
    }

    public double getMinRainRate() {
        return minRainRate;
    }

    public void setMinRainRate(double minRainRate) {
        if(minRainRate >= 0){
            this.minRainRate = minRainRate;
        }
    }

    public double getAvgRainRate() {
        return avgRainRate;
    }

    public void setAvgRainRate(double avgRainRate) {
        this.avgRainRate = avgRainRate;
    }
    
    //Methods
    public void calculateMaxMinAvgRainRate(){
        int max = 0;
        int min = 0;
        float avg = 0;
        for(Measurement minut : laatste24Uur){
            if(minut.getRawRainRate() > max){
                max = minut.getRawRainRate();
            }
            if(minut.getRawRainRate() <  min){
                min = minut.getRawRainRate();
            }
            avg += minut.getRawRainRate();
        }
        avg /= laatste24Uur.size();
        
        setAvgRainRate(calculator.regenmeter((short)avg));
        setMaxRainRate(calculator.regenmeter((short)max));
        setMinRainRate(calculator.regenmeter((short)min));
    }
    
    public void updateRecent(Measurement measurement1){
        this.laatsteMeting = measurement1;
        setCurrentRainRate(laatsteMeting.getRainRate());
    }
    public void update24Hour(ArrayList<Measurement> measurement2){
        this.laatste24Uur = measurement2;
        calculateMaxMinAvgRainRate();
    }
    
    public void display(){
        GUIboard.writeUpperDigits(getCurrentRainRate());
        GUIboard.writeLeftDigits(getMaxRainRate());
        GUIboard.writeRightDigits(getMinRainRate());
        GUIboard.writePageToMatrix("Regenval in mm/h", "Gemiddelde: " + avgRainRate, "");
    }
    
}
