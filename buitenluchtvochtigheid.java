import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 * Write a description of class OpdrachtDinges here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OpdrachtBuitenLuchtvochtigheid
{
    Weerstation weerstation;
    Measurement meting;
    ArrayList<Measurement> laatste24uur; //ArrayList om de luchtvochtigheid op te slaan
    
    public OpdrachtBuitenLuchtvochtigheid()
    {
            weerstation = new Weerstation();  //maakt een nieuw weerstation aan
            meting = weerstation.getMostRecentMeasurement(); //pakken recenste gegevens MOET VERWIJDERD WORDEN
    }
    
    public double buitenLuchtvochtigheid()
    {
        IO.init();
        meting.getOutsideHum();
        return meting.getOutsideHum();
    }
    
    public void getMaximale()
    {
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        
        short maximale = 0;
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideHum() > maximale)
            {
                maximale = laatste24uur.get(i).getRawOutsideHum();
            }
        }
        System.out.println(maximale);
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
