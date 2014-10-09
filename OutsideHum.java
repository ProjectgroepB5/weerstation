package weerstation;
import java.net.*;
import java.util.ArrayList;
/**
 * Write a description of class OpdrachtDinges here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OutsideHum
{
    Measurement meting; //connectie maken met Measurement klasse
    ArrayList<Measurement> laatste24uur; //ArrayList om de luchtvochtigheid op te slaan
    double vochtigheid;
    private Measurement laatsteMeting;
    double max;
    double min;
    double avg;
    
    public OutsideHum(Measurement measurement1, ArrayList<Measurement> measurement2)
    {
    	updateRecent(measurement1);
		update24Hour(measurement2);
    }
    
    public double buitenLuchtvochtigheid()
    {
        meting.getOutsideHum(); //waardes van buitenluchtvochtigheid ophalen uit de meting klasse
        return meting.getOutsideHum();
    }
    
    public double berekenMaximale()
    {
        short maximale = 0;
        for(int i=0; i < laatste24uur.size();i++) //zolang i kleiner is dan de grootte van de ArrayList laatste24uur voert het de if-statement uit
        {
            if(laatste24uur.get(i).getRawOutsideHum() > maximale) //plek (i) in de ArrayList laatste24uur vraag je de waarde RawOutsideHum van op. Als die groter is dan huidige maximale, vervang je maximale door dat getal.
            {
                maximale = laatste24uur.get(i).getRawOutsideHum();
            }
        }
        return(maximale);
    }
    
    public double berekenMinimale()
    {
        short minimale = 100;
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideHum() < minimale)
            {
                minimale = laatste24uur.get(i).getRawOutsideHum();
            }
        }
        return(minimale);
    }
    
    public double berekenAverage()
    {
        short average = 0;
        for(int i=0; i < laatste24uur.size(); i ++)
        {
            average += laatste24uur.get(i).getRawOutsideHum();
        }
        average /= laatste24uur.size();
        return(average);
    }
    
    public void updateRecent(Measurement measurement1){
		this.laatsteMeting = measurement1;
		vochtigheid = laatsteMeting.getOutsideHum();
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
		GUIboard.writePageToMatrix("Luchtv. Buiten", "Gemiddelde: " + avg, "");
	}
}
