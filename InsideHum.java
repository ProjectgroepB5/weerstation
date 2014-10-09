import java.net.*;
import java.util.ArrayList;
/**
 * Write a description of class OpdrachtDinges here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InsideHum
{
    Measurement meting; 
    ArrayList<Measurement> laatste24uur; 
    double vochtigheid;
    private Measurement laatsteMeting;
    double max;
    double min;
    double avg;
    
    public InsideHum(Measurement measurement1, ArrayList<Measurement> measurement2)
    {
    	updateRecent(measurement1);
		update24Hour(measurement2);
    }
    
    public double binnenLuchtvochtigheid()
    {
        meting.getInsideHum(); 
        return meting.getInsideHum();
    }
    
    public double berekenMaximale()
    {
        short maximale = 0;
        for(int i=0; i < laatste24uur.size();i++) 
        {
            if(laatste24uur.get(i).getRawInsideHum() > maximale) 
            {
                maximale = laatste24uur.get(i).getRawInsideHum();
            }
        }
        return(maximale);
    }
    
    public double berekenMinimale()
    {
        short minimale = 100;
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawInsideHum() < minimale)
            {
                minimale = laatste24uur.get(i).getRawInsideHum();
            }
        }
        return(minimale);
    }
    
    public double berekenAverage()
    {
        int average = 0;
        for(int i=0; i < laatste24uur.size(); i ++)
        {
            average += laatste24uur.get(i).getRawInsideHum();
        }
        average /= laatste24uur.size();
        return(average);
    }
    
    public void updateRecent(Measurement measurement1){
		this.laatsteMeting = measurement1;
		vochtigheid = laatsteMeting.getInsideHum();
	}
	
	public void update24Hour(ArrayList<Measurement> measurement2){
		this.laatste24uur = measurement2;
		min = berekenMinimale();
		max = berekenMaximale();
		avg = berekenAverage();
	}
	
	public void display(){
		GUIboard.writeUpperDigits(vochtigheid);
		GUIboard.writeLeftDigits(max);
		GUIboard.writeRightDigits(min);
		GUIboard.writePageToMatrix("Luchtv. Binnen", "Gemiddelde: " + avg, "");
	}
}
