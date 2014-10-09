package weerstation;
import java.io.*;
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
    private Measurement laatsteMeting;
    Weerstation weerstation;
    Measurement meting;
    ArrayList<Measurement> dag; 
    double hum;
    double max;
    double min;
    double avg;
    
    public InsideHum(Measurement measurement1, ArrayList<Measurement> laatste24uur )
    {
        dag = laatste24uur;
        meting = measurement1; 
    }
    public double InsideHum()
    {
        meting.getInsideHum();
        return meting.getInsideHum();
    }
    public double getMaximale()
    {
        short maximale = 0; // Maximale is iets boven de 100
        for(int i=0; i < dag.size();i++)
        {
            if(dag.get(i).getRawInsideHum() > maximale)
            {
                maximale = dag.get(i).getRawInsideHum();
            }
        }
        return(maximale);
    }
    public double getMinimale()
    {
        short minimale = 100; // Minimale is iets onder de 100
        for(int i=0; i < dag.size();i++)
        {
            if(dag.get(i).getRawInsideHum() < minimale)
            {
                minimale = dag.get(i).getRawInsideHum(); 
            }
        }
        return(minimale); // Geeft minimale
    }
    public double getAverage()
    {
        int average = 0; // Average is nieuw
        for(int i=0; i < dag.size();i++)
        {
            average += dag.get(i).getRawInsideHum(); 
        }
        average /= dag.size(); // Berekening average
        return(average); // Geeft average terug
    }
    
    public void updateRecent(Measurement measurement1)
    {
        this.laatsteMeting = measurement1;
        hum = laatsteMeting.getInsideHum();
    }
    public void update24Hour(ArrayList<Measurement> measurement2)
    {
        this.dag = measurement2;
        min = getMinimale();
        max = getMaximale();
        avg = getAverage();
    }
    public void display()
    {
        GUIboard.writeUpperDigits(hum);
        GUIboard.writeLeftDigits(max);
        GUIboard.writeRightDigits(min);
		GUIboard.writePageToMatrix("Luchtv. Binnen", "Gemiddelde: " + avg, "");
    }
}

    
