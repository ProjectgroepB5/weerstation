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
    ArrayList<Measurement> laatste24uur; //ArrayList om de Temperatuur in op te slaan
    double Hum;
    double max;
    double min;
    double avg;
    
    public InsideHum()
    {
        weerstation = new Weerstation(); //maakt een nieuw weerstation aan
        meting = weerstation.getMostRecentMeasurement(); //pakken recenste gegevens MOET VERWIJDERD WORDEN
    }
    public double InsideHum()
    {
        meting.getInsideHum();
        return meting.getInsideHum();
    }
    public double getMaximale()
    {
        short maximale = 0; // Maximale is iets boven de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawInsideHum() > maximale)
            {
                maximale = laatste24uur.get(i).getRawInsideHum();
            }
        }
        return(maximale);
    }
    public double getMinimale()
    {
        short minimale = 100; // Minimale is iets onder de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawInsideHum() < minimale)
            {
                minimale = laatste24uur.get(i).getRawInsideHum(); // Defineert average
            }
        }
        return(minimale); // Geeft minimale
    }
    public double getAverage()
    {
        int average = 0; // Average is nieuw
        for(int i=0; i < laatste24uur.size();i++)
        {
            average += laatste24uur.get(i).getRawInsideHum(); // Defineert average
        }
        average /= laatste24uur.size(); // Berekening average
        return(average); // Geeft average terug
    }
    
    public void updateRecent(Measurement measurement1)
    {
        this.laatsteMeting = measurement1;
        temp = laatsteMeting.getInsideHum();
    }
    public void update24Hour(ArrayList<Measurement> measurement2)
    {
        this.laatste24uur = measurement2;
        min = getMinimale();
        max = getMaximale();
        avg = getAverage();
    }
    public void display()
    {
        GUIboard.writeUpperDigits(Hum);
        GUIboard.writeLeftDigits(max);
        GUIboard.writeRightDigits(min);
    }
}

    
