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
public class BuitenTemperatuur
{
    private Measurement laatsteMeting;
    Measurement meting;
    ArrayList<Measurement> laatste24uur; //ArrayList om de Temperatuur in op te slaan
    double temp;
    double max;
    double min;
    double avg;
    
    public BuitenTemperatuur(Measurement measurement1, ArrayList<Measurement> measurement2)
    {
    	updateRecent(measurement1);
		update24Hour(measurement2);
    }
    public double buitenTemperatuur()
    {
        meting.getOutsideTemp();
        return meting.getOutsideTemp();
    }
    public double getMaximale()
    {
        short maximale = 0; // Maximale is iets boven de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideTemp() > maximale)
            {
                maximale = laatste24uur.get(i).getRawOutsideTemp();
            }
        }
        return(Calculator.temperatuur((maximale)));
    }
    public double getMinimale()
    {
        short minimale = 1000; // Minimale is iets onder de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawOutsideTemp() < minimale)
            {
                minimale = laatste24uur.get(i).getRawOutsideTemp(); // Defineert average
            }
        }
        
        return(Calculator.temperatuur((minimale))); // Geeft minimale
    }
    public double getAverage()
    {
        short average = 0; // Average is nieuw
        for(int i=0; i < laatste24uur.size();i++)
        {
            average += laatste24uur.get(i).getRawOutsideTemp(); // Defineert average
        }
        average /= laatste24uur.size(); // Berekening average
        return(Calculator.temperatuur((average))); // Geeft average terug
    }
    
    public void updateRecent(Measurement measurement1)
    {
        this.laatsteMeting = measurement1;
        temp = laatsteMeting.getOutsideTemp();
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
        GUIboard.writeUpperDigits(temp);
        GUIboard.writeLeftDigits(max);
        GUIboard.writeRightDigits(min);
    }
}
