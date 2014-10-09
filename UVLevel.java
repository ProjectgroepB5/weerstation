import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 * Write a description of class OpdrachtDinges here.
 * 
 * @author (Tim van Lieshout) 
 * @version (a version number or a date)
 */
public class UVLevel
{
    Weerstation weerstation;
    Measurement meting;
    ArrayList<Measurement> laatste24uur;
    private Measurement laatsteMeting;
    double max;
    double min;
    double avg;
    double level;
    
    public UVLevel(Measurement measurement1, ArrayList<Measurement> measurement2)
    {
        updateRecent(measurement1);
        update24Hour(measurement2);
    }
    
    public double buitenLuchtvochtigheid()
    {
        meting.getUVLevel();
        return meting.getUVLevel();
    }
    
    public double berekenMaximale()
    {
        double maximale = 0;
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawUVLevel() > maximale) 
            {
                maximale = laatste24uur.get(i).getUVLevel();
            }
        }
        return(maximale);
    }
    
    public double berekenMinimale()
    {
        double minimale = 100;
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideHum() < minimale)
            {
                minimale = laatste24uur.get(i).getUVLevel();
            }
        }
        return(minimale);
    }
    
    public double berekenAverage()
    {
        int average = 0;
        for(int i=0; i < laatste24uur.size(); i ++)
        {
            average += laatste24uur.get(i).getUVLevel();
        }
        average /= laatste24uur.size();
        return(average);
    }
    
    public void updateRecent(Measurement measurement1){
		this.laatsteMeting = measurement1;
		level = laatsteMeting.getOutsideHum();
	}
	
	public void update24Hour(ArrayList<Measurement> measurement2){
		this.laatste24uur = measurement2;
		min = berekenMinimale();
		max = berekenMaximale();
		avg = berekenAverage();
	}
	
	public void display(){
		GUIboard.writeUpperDigits(level);
		GUIboard.writeLeftDigits(max);
		GUIboard.writeRightDigits(min);
		GUIboard.writePageToMatrix("UVLevel Buiten", "Gemiddelde: " + avg, "");
	}
	
	
}
