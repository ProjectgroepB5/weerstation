import java.util.ArrayList;
import java.net.*;
/**
 * Write a description of class CloudHeight here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CloudHeight
{
   Measurement meting;
   ArrayList<Measurement>laatste24uur;
   double cloud;
   private Measurement laatsteMeting;
   double maxHeight;
   double minHeight;
   double avgHeight;
   
   public CloudHeight(Measurement measurement1, ArrayList<Measurement>measurement2)
   {
       updateRecent(measurement1);
       update24Hour(measurement2);
   }
   
   public double Height()
   {
       meting.getCloudHeight();
       return meting.getCloudHeight();
   }
   
   public double maximaleHeight()
   {
       double max = 0; 
       for(int i = 0; i < laatste24uur.size(); i++)
       {
           if(laatste24uur.get(i).getCloudHeight() > max)
           {
               max = laatste24uur.get(i).getCloudHeight();
           }
       }
       return(max);
   }
   
   public double minimaleHeight()
   {
       double min = 100000;
       
       for(int i = 0; i < laatste24uur.size(); i++)
       {
           if(laatste24uur.get(i).getCloudHeight() < min)
           {
               min = laatste24uur.get(i).getCloudHeight();               
           }
       }
       System.out.println(min);
       return(min);
   }
   
   public double AverageHeight()
   {
       float average =0;
       for(int i = 0; i < laatste24uur.size(); i++)
       {
           average += laatste24uur.get(i).getCloudHeight();
       }
       average /=laatste24uur.size();
       return(average);
   }
   
   public void updateRecent(Measurement measurement1){
		this.laatsteMeting = measurement1;
		cloud = laatsteMeting.getCloudHeight();
	}
	
	public void update24Hour(ArrayList<Measurement> measurement2){
		this.laatste24uur = measurement2;
		minHeight = minimaleHeight();
		maxHeight = maximaleHeight();
		avgHeight = AverageHeight();
	}
	
	public void display(){
		GUIboard.writeUpperDigits(cloud);
		GUIboard.writePageToMatrix("Wolkhoogte", "Gemiddelde: " + avgHeight,"");
		//GUIboard.writePageToMatrix( "min","min: " + minHeight, "");
		//GUIboard.writePageToMatrix( "max","max: " + maxHeight, "");
		System.out.println(maxHeight);
		       System.out.println(minHeight);
	}
}

