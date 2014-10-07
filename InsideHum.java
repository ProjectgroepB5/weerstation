import java.util.ArrayList; 
import java.io.*;
import java.net.*;
public class Humiditie 
{
    Weerstation weerstation; //maken een nieuw weerstation aan
    Measurement  meting; // pakken recente gegeven
    ArrayList<Measurement>laatste24uur;
    
   public Humiditie()
    {
     weerstation = new Weerstation();
     meting = weerstation.getMostRecentMeasurement(); 
    }
   
   public double InsideHum()
      {
       IO.init();
       meting.getInsideHum();
       return meting.getInsideHum();
       
      }
   
    public void  MaxHum()
      {    
        IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        
        short maximale = 0;
        for(int i = 0; i < laatste24uur.size(); i++)
        {
            if(laatste24uur.get(i).getRawInsideHum() > maximale)
            {
                maximale = laatste24uur.get(i).getRawInsideHum();
            }
        }
        System.out.println(maximale);
      }
   
   public void MinHum()
      {
       IO.init();
       laatste24uur = weerstation.getAllMeasurementsLast24h();
        
       short minimale = 100; // kan niet groter dan 100 worden
       for(int i = 0; i < laatste24uur.size(); i++)
       {
           if(laatste24uur.get(i).getRawInsideHum() < minimale)
           {
                minimale = laatste24uur.get(i).getRawInsideHum(); 
           }
       }
        System.out.println(minimale);
       }
   
   public void getAverage()
    {
         IO.init();
        laatste24uur = weerstation.getAllMeasurementsLast24h();
        int average = 0;
        for(int i=0; i < laatste24uur.size();i++)
        {
            average += laatste24uur.get(i).getRawInsideHum();
        }
        average = average / laatste24uur.size();
        System.out.println(average);
     }  
      public void updateRecent(Measurement measurement1)
    {
		this.laatsteMeting = measurement1;
		setInsidehum(laatsteMeting.getInsideHum());
	}
	public void update24Hour(ArrayList<Measurement> measurement2)
	{
		this.laatste24Uur = measurement2;
		calculateInsidehum();
	}
	
	public void display()
	{
		GUIboard.writeUpperDigits(getCurrentInsidehum());
		GUIboard.writeLeftDigits(getMaxInsidehum());
		GUIboard.writeRightDigits(getMinInsidehum());
	}
   }
    
    
