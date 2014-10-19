package weerstation1;
import java.util.ArrayList;
import java.util.Collections;

public class StatisticsCalculator {
    
    public static double max(ArrayList<Double> array)
    {
        double max = 0;
        
        for(double waarde : array){
            if(waarde > max){
                max = waarde;
            }
        }
        
        return max;
    }
    
    public static double min(ArrayList<Double> array)
    {
        double min = array.get(0);
        
        for(double waarde : array){
            if(waarde < min){
                min = waarde;
            }
        }
        
        return min;
    }
    
    public static double avg(ArrayList<Double> array)
    {
        double avg = 0;
        
        for(double waarde : array){
            avg += waarde;
        }
        avg /= array.size();
        return avg;
    }
    
    public static double median(ArrayList<Double> array){
    	Collections.sort(array);							//sort the array
    	
    	double median = 0;
    	int middle = array.size()/2; 						//calculate the middle of the array

    	if (array.size()%2 == 1) { 							//check if the array is even or uneven 
    		median = array.get(middle);			
    	} else { 
    		median = (array.get(middle-1) + array.get(middle+1)) / 2; 
    	}
    	return median;
    }
    
    public static double modus(ArrayList<Double> array){
	    double maxValue = 0;
	    int maxCount = 0;
	
	    for (int i = 0; i < array.size(); ++i){ 			//cycle through every number in the array
	    	int count = 0;
		    for (int j = 0; j < array.size(); ++j) { 		
		    	if (array.get(j) == array.get(i)){
		    		++count; 
		    	}
		    } 
		    if (count > maxCount) { 						//if the count is bigger then the max count, it will be the temporary modus
		    	maxCount = count; 
		    	maxValue = array.get(i); 
		    }
	    }
	    return maxValue;
    }	
	    
    public static double afwijking(ArrayList<Double> array){
	    double mediaan = StatisticsCalculator.median(array);
	    double afwijking = 0; 
	    for(double m :array){
		   	afwijking += (mediaan-m)*(mediaan-m); 
	    }
		afwijking /= array.size();
		afwijking = Math.sqrt(afwijking);
	    return afwijking;
    }   

    
    public static Periode langsteDroogstePeriode(ArrayList<Double> array)
    {
        Periode periode = new Periode();
        
        
        return periode;
    }
    
    public static Periode langsteDroogstePeriodeMetMax(ArrayList<Double> array, int maxNeerslag)
    {
        Periode periode = new Periode();
        
        
        return periode;
    }
    
    public static Periode langsteRegenPeriode(ArrayList<Double> array)
    {
        Periode periode = new Periode();
        
        
        return periode;
    }
    
    public static double meesteRegenAchterElkaar(ArrayList<Double> array)
    {
        double regenHoeveelheid = 0;
        
        
        return regenHoeveelheid;
    }
    
}
