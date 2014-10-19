package weerstation1;
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
    
    public double getMode() {
		return mode;
	}

	public void setMode(double mode) {
		this.mode = mode;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public double getDeviation() {
		return deviation;
	}

	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}

	//Methods
    public void calculateMaxMinAvg(ArrayList<Double> array){
        setMax( StatisticsCalculator.max(array) );
        setMin( StatisticsCalculator.min(array) );
        setAvg( StatisticsCalculator.avg(array) );
        setMedian( StatisticsCalculator.median(array) );
        setMode( StatisticsCalculator.modus(array) );
        setDeviation( StatisticsCalculator.afwijking(array) );
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
