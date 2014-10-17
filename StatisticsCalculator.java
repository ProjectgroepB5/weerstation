import java.util.ArrayList;  

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
