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
    
      public static double median(ArrayList<Double> array2){
        ArrayList<Double> array = new ArrayList<Double>();
        
        for(double db : array2)
        {
            array.add(db);
        }
          
        Collections.sort(array);                            //sort the array
        
        double median = 0;
        int middle = array.size()/2;                        //calculate the middle of the array

        if (array.size()%2 == 1) {                          //check if the array is even or uneven 
            median = array.get(middle);         
        } else { 
            median = (array.get(middle-1) + array.get(middle+1)) / 2; 
        }
        return median;
    }
    
    public static double modus(ArrayList<Double> array){
        double maxValue = 0;
        int maxCount = 0;
    
        for (int i = 0; i < array.size(); ++i){             //cycle through every number in the array
            int count = 0;
            for (int j = 0; j < array.size(); ++j) {        
                if (array.get(j) == array.get(i)){
                    ++count; 
                }
            } 
            if (count > maxCount) {                         //if the count is bigger then the max count, it will be the temporary modus
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

    
    public static double graadDagen(ArrayList<Double> array)
    {
        ArrayList<Double> avgTempDag = new ArrayList<Double>();
        int i = 0;
        double avgTemp = 0;
        
        //Breken de gemiddelde temperatuur per dag.
        for(double db : array)
        {
            i++;
            if(i%1440==0)
            {
                avgTempDag.add(avgTemp);
                avgTemp = 0;
            }
            avgTemp += db;         
        }
        avgTemp /= array.size();
        
        
        int graaddagen = 0;
        int periodeBegin = 0;
        int periodeEind = 1439;
        
        for(int j = 0; j < avgTempDag.size(); j++)
        {
            if(avgTemp < 18)
            {
                graaddagen += 18 - avgTemp;
            }
        }
        
        periodeBegin += 1400;
        periodeEind += 1400;
        
        //graaddagen afronden
        
        return graaddagen;
    }
    
    public static int[] langsteDroogstePeriode(ArrayList<Double> array)
    {
        return langsteDroogstePeriodeMetMax(array, 0);
    }
    
    public static int[] langsteDroogstePeriodeMetMax(ArrayList<Double> array, int maxNeerslag)
    {
        int[] index = new int[2];
        int index1 = 0;
        int index2 = 0;
        
        //Code
        
        index[0] = index1;
        index[1] = index2;
        return index;
    }
    
    public static int[] langsteRegenPeriode(ArrayList<Double> array)
    {
        int[] index = new int[2];
        int index1 = 0;
        int index2 = 0;
        
        //Code
        
        index[0] = index1;
        index[1] = index2;
        return index;
    }
    
    public static double maximaleRegenPeriode(ArrayList<Double> array)
    {
        double totaleRegen = 0;
        
        for(double db : array)
        {
            totaleRegen += db;
        }
        
        return totaleRegen;
    }
    
    public static int[] langsteZomersePeriode(ArrayList<Double> array)
    {
      
        ArrayList<Double> maxTempDag = new ArrayList<Double>();
        int i = 0;
        double maxTemp = 0;
        
        //Bereken de maximale temperatuur per dag.
        for(double db : array)
        {
           i++;
           if(i%1440==0)
           {
               maxTempDag.add(maxTemp);
               maxTemp = 0;
           }
           
           if(db > maxTemp)
           {
               maxTemp = db;
           }  
        }

        //Creer een array met twee indexen
        int[] index = new int[2];
        int index1 = 0;
        int index2 = 0;
        
        //Een tijdelijke index om bij te houden wat de index was in het geval dat de vorige groter was.
        int index1_1 = 0;
        
        //Een boolean 
        boolean zomers = false;
        
        int p = 0;
        int maxDays = 0;
        
        //Doorloop je maximale temperaturen en zoek de langste periode 
        for(int t=0; t<maxTempDag.size(); t++) {
            if(maxTempDag.get(t) > 25) {
                p++;
                if(!zomers)
                {
                    zomers = true;
                    index1_1 = t;
                }
            }
            else {
                if(p > maxDays) {
                    maxDays = p;
                    index1 = index1_1;
                    index2 = t-1;
                    zomers=false;
                }
                p=0;
            }
        }

        //Het terugsturen van de gevonden indexen. Je doet ze keer 1440 omdat je bij het berekenen van 
        //de temperatuur per dag je het in stukken van 1440 samenvoegde.
        index[0] = index1*1440;
        if(index2<0){index2=0;}
        index[1] = index2*1440;
        
        return index;
    }
    
    public static int[] langsteHitteGolfPeriode(ArrayList<Double> array)
    {
        int[] index = new int[2];
        int index1 = 0;
        int index2 = 0;
        
        //Code
        
        index[0] = index1;
        index[1] = index2;
        return index;
    }
    
    public static int[] langsteTempStijgingPeriode(ArrayList<Double> array)
    {
        int[] index = new int[2];
        int index1 = 0;
        int index2 = 0;
        
        //Code
        
        index[0] = index1;
        index[1] = index2;
        return index;
    }
    
}
