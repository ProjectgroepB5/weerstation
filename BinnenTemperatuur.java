 
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class BinnenTemperatuur
{
    private Measurement laatsteMeting;
    Calculator calculator = new Calculator();
    Measurement meting;
    ArrayList<Measurement> laatste24uur; //ArrayList om de Temperatuur in op te slaan
    double temp;
    double max;
    double min;
    double avg;
    
    public BinnenTemperatuur(Measurement measurement1, ArrayList<Measurement> measurement2)
    {
    	updateRecent(measurement1);
		update24Hour(measurement2);
    }
    public double binnenTemperatuur()
    {
        meting.getInsideTemp();
        return meting.getInsideTemp();
    }
    public double getMaximale()
    {
        short maximale = 0; // Maximale is iets boven de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawInsideTemp() > maximale)
            {
                maximale = laatste24uur.get(i).getRawInsideTemp();
            }
        }
        return(calculator.temperatuur((maximale)));
    }
	public double getMinimale()
    {
        short minimale = 1000; // Minimale is iets onder de 100
        for(int i=0; i < laatste24uur.size();i++)
        {
            if(laatste24uur.get(i).getRawInsideTemp() < minimale)
            {
                minimale = laatste24uur.get(i).getRawInsideTemp(); // Defineert average
            }
        }
        
        return(calculator.temperatuur((minimale))); // Geeft minimale
    }
    public double getAverage()
    {
        short average = 0; // Average is nieuw
        for(int i=0; i < laatste24uur.size();i++)
        {
            average += laatste24uur.get(i).getRawInsideTemp(); // Defineert average
        }
        average /= laatste24uur.size(); // Berekening average
        return(calculator.temperatuur((average))); // Geeft average terug
    }
    
    public void updateRecent(Measurement measurement1)
    {
        this.laatsteMeting = measurement1;
        temp = laatsteMeting.getInsideTemp();
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
