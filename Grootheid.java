package weerstation1;
 
import java.util.ArrayList;

/**
 * Dient als Parent voor alle grootheden en zorgt voor een constante interface
 * 
 * @author Kenneth van Ewijk
 * @author Janco Kock
 */
public class Grootheid
{
    // instance variables - replace the example below with your own
    private double statics;
    private double max;
    private double min;
    private double current;
    private Periode period;
    ArrayList<Double> list = new ArrayList<Double>();
    
    //boolean to check if the variables are set
    private boolean max_min_check = false;
    private boolean current_check = false;
    
    private String statics_name = "";
    private String name = "";
    private String custom = "";
    
    //constructor
    public Grootheid(){
    }
    
    //getters & setters
    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
    	current_check = true;
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

	public double getStatics() {
		return statics;
	}

	public void setStatics(double statics) {
		this.statics = Math.round(statics * 100.0) / 100.0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Periode getPeriod() {
		return period;
	}

	public void setPeriod(Periode period) {
		this.period = period;
	}

	public void setStatics_name(String statics_name) {
		this.statics_name = statics_name;
	}
	
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	//Methods
    public void maxMin(){
    	max_min_check = true;
    	setMax(calculate(StatisticsCalculator.max(list)));
    	setMin(calculate(StatisticsCalculator.min(list)));
    }
    public void median(){
    	setStatics_name("Mediaan: ");
    	setStatics(calculate(StatisticsCalculator.median(list)));
    }
    public void modus(){
    	setStatics_name("Modus: ");
    	setStatics(calculate(StatisticsCalculator.modus(list)));
    }
    public void avg(){
    	setStatics_name("Gemiddelde: ");
    	setStatics(calculate(StatisticsCalculator.avg(list)));
    }
    public void afwijking(){
    	setStatics_name("Afwijking: ");
    	setStatics(calculate(StatisticsCalculator.afwijking(list)));
    }


	public void updateRecent(Measurement measurement1){
    	
    }
    
    public void updatePeriod(ArrayList<Measurement> measurement2){
        
    }
    
    public void display(String periodname){
    	GUIboard.clearLeft();
    	GUIboard.clearRight();
    	GUIboard.clearTop();
    	GUIboard.clearBottom();
    	
    	if(current_check){
        	GUIboard.writeUpperDigits(getCurrent());
    	}
    	if(max_min_check){
    		GUIboard.writeLeftDigits(getMax());
        	GUIboard.writeRightDigits(getMin());
    	}
    	if(!(statics_name == "")){

    		GUIboard.writePageToMatrix(getName(), statics_name + getStatics(), periodname);
    	}else if(!(custom == "")){

    		GUIboard.writePageToMatrix(getName(), custom, periodname);
    	}
    	if(!(period == null)){
    		GUIboard.writePageToMatrix(getName(), period.toString(), periodname);   		
    	}
    }
    
    public void displayGraph()
    {
        GUIboard.writeGraphToMatrix(list, getMin(), getMax());
    }
    
    public double calculate(double value){
    	return value;
    }
}
