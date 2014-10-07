import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
* Write a description of class OpdrachtDinges here.
*
* @author (your name)
* @version (a version number or a date)
*/
public class BuitenTemperatuur
{
    Weerstation weerstation;
    Measurement meting;
    ArrayList<Measurement> laatste24uur; //ArrayList om de Temperatuur in op te slaan
    public BuitenTemperatuur()
    {
        weerstation = new Weerstation(); //maakt een nieuw weerstation aan
        meting = weerstation.getMostRecentMeasurement(); //pakken recenste gegevens MOET VERWIJDERD WORDEN
    }
    public double buitenTemperatuur()
    {
        IO.init();
        meting.getOutsideTemp();
        return meting.getOutsideTemp();
    }
    public void getMaximale()
    {
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        short maximale = 0; // Maximale is iets boven de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideTemp() > maximale)
            {
                maximale = laatste24uur.get(i).getRawOutsideTemp();
            }
        }
        System.out.println(maximale);
    }
    public void getMinimale()
    {
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        short minimale = 100; // Minimale is iets onder de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideTemp() < minimale)
            {
                minimale = laatste24uur.get(i).getRawOutsideTemp(); // Defineert average
            }
        }
        System.out.println(minimale); // Geeft minimale
    }
    public void getAverage()
    {
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        int average = 0; // Average is nieuw
        for(int i=0; i < laatste24uur.size();i++)
        {
            average += laatste24uur.get(i).getRawOutsideTemp(); // Defineert average
        }
        average /= laatste24uur.size(); // Berekening average
        System.out.println(average); // Geeft average terug
    }
    
    public void updateRecent(Measurement measurement1)
    {
        this.laatsteMeting = measurement1;
        setCurrentWindSpeed(laatsteMeting.getAvgWindSpeed());
    }
    public void update24Hour(ArrayList<Measurement> measurement2)
    {
        this.laatste24Uur = measurement2;
        calculateMaxMinAvgWindSpeed();
    }
    public void display()
    {
        GUIboard.writeUpperDigits(getCurrentWindSpeed());
        GUIboard.writeLeftDigits(getMaxWindSpeed());
        GUIboard.writeRightDigits(getMinWindSpeed());
    }
    
    
}
