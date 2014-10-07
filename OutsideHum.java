import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 * Write a description of class OpdrachtDinges here.
 * 
 * @author (Tim van Lieshout) 
 * @version (a version number or a date)
 */
public class OutsideHum
{
    Weerstation weerstation; //connectie maken met Weerstation klasse
    Measurement meting; //connectie maken met Measurement klasse
    ArrayList<Measurement> laatste24uur; //ArrayList om de luchtvochtigheid op te slaan
    
    public OutsideHum()
    {
            weerstation = new Weerstation();  //maakt een nieuw weerstation aan
            meting = weerstation.getMostRecentMeasurement(); //pakken recenste gegevens MOET VERWIJDERD WORDEN
    }
    
    public double buitenLuchtvochtigheid()
    {
        IO.init();
        meting.getOutsideHum(); //waardes van buitenluchtvochtigheid ophalen uit de meting klasse
        return meting.getOutsideHum();
    }
    
    public void getMaximale()
    {
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h(); //geef inhoud aan de (nu nog lege) ArrayList laatste24uur
        
        short maximale = 0;
        for(int i=0; i < laatste24uur.size();i++) //zolang i kleiner is dan de grootte van de ArrayList laatste24uur voert het de if-statement uit
        {
            if(laatste24uur.get(i).getRawOutsideHum() > maximale) //plek (i) in de ArrayList laatste24uur vraag je de waarde RawOutsideHum van op. Als die groter is dan huidige maximale, vervang je maximale door dat getal.
            {
                maximale = laatste24uur.get(i).getRawOutsideHum();
            }
        }
        System.out.println(maximale); //laat de waarde van maximale zien.
    }
    
    public void getMinimale()
    {
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        
        short minimale = 100;
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideHum() < minimale)
            {
                minimale = laatste24uur.get(i).getRawOutsideHum();
            }
        }
        System.out.println(minimale);
    }
    
    public void getAverage()
    {
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        
        int average = 0;
        for(int i=0; i < laatste24uur.size(); i ++)
        {
            average += laatste24uur.get(i).getRawOutsideHum();
        }
        average /= laatste24uur.size();
        System.out.println(average);
    }
    
    public void matrixScherm()
    {
        IO.init();
        if( IO.readShort(0x80) != 0 )
        {
            IO.writeShort(0x40,'q'); //tussen de '' moet de tekst komen die op het scherm komt
        }
        else {
            //hier code voor grafiek te laten tekenen
        }
    }
}
