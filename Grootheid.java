 
import java.util.ArrayList;

public class Grootheid
{
    // instance variables - replace the example below with your own
    private double avg;
    private double max;
    private double min;
    private double mode;
    private double median;
    private double deviation;
    private double current;

    //constructor
    public Grootheid(){
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
        this.max= Math.round(max * 100.0) / 100.0;;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = Math.round(min * 100.0) / 100.0;;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = Math.round(avg * 100.0) / 100.0;
    }
    
    public double getMode() {
		return mode;
	}

	public void setMode(double mode) {
		this.mode = Math.round(mode * 100.0) / 100.0;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = Math.round(median * 100.0) / 100.0;
	}

	public double getDeviation() {
		return deviation;
	}

	public void setDeviation(double deviation) {
		this.deviation = Math.round(deviation * 100.0) / 100.0;
	}

	//Methods
    public void calculateMaxMin(ArrayList<Double> array){
    	setMax(StatisticsCalculator.max(array));
    	setMin(StatisticsCalculator.min(array));
    }

    public void updateRecent(Measurement measurement1){

    }
    
    public void updatePeriod(ArrayList<Measurement> measurement2){
        
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
