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
        ArrayList<Double> rainday = new ArrayList<Double>();
         
        int i = 0;
        double longRainday = 0;
        
        // Bereken de Rainrate per dag 
         for(double rain : array)
         {
             i++;
             if (i% 1440 ==0)
             {
                 rainday.add(longRainday);
                 longRainday = 0;
             }
             
             if (rain > longRainday)
             {
                longRainday = rain;
             }
        }
        int[] index = new int[2];
        int index1 = 0;
        int index2 = 0;
        
        int index1_1 = 0;
        
        boolean regen = false;
        
        int p = 0;
        int maxDays = 0;
        
        for(int t = 0; t < rainday.size(); t++)
        {
            if (rainday.get(t) > 0 )
            {
                p++;
                
                if(!regen)
                {
                    regen = true;
                    index1_1 = t; 
                }
                else
                {
                    if(p > maxDays)
                    {
                        maxDays = p;
                        index1 = index1_1;
                        index2 = t-1;
                        regen=false;
                    }
                    p = 0;
                }
            }
        }
        
        index[0] = index1*1440;
        if(index2<0)
        {
            index2 =0;
        }
        index[1] = index2*1440;
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
        ArrayList<Double> maxTempDag = new ArrayList<Double>();
        ArrayList<Integer> periodeLengteList = new ArrayList<Integer>();
        ArrayList<Integer> periodeStartList = new ArrayList<Integer>();
        
        int[] index = new int[2];
        int index1 = 0;
        int index2 = 0;
        int indexStart = 0;
        int indexEind = 1;
        int periodeLengte = 0;
        int i = 0;
        double maxTemp = 0; 
        
        //berekent maximale temp. per dag
        for(double db : array)
        {
           i ++;
           if(i % 1440 == 0)
           {
               maxTempDag.add(maxTemp);
               maxTemp = 0;
           }
           
           if(db > maxTemp) 
           {
               maxTemp = db;
           }    
        }        
        
        for(int t = 1; t < maxTempDag.size(); t ++)  //zoekt naar periodes van temp.stijgingen
        { 
            if (maxTempDag.get(t) > maxTempDag.get(t - 1))
            {                
                indexEind ++;
            }
            
            if (maxTempDag.get(t) <= maxTempDag.get(t - 1))
            {
               periodeLengte = indexEind - indexStart;
               periodeLengteList.add(periodeLengte);
               periodeStartList.add(indexStart);
               indexStart = indexEind;
            }
        } 
        
        Integer tempMax = Collections.max(periodeLengteList); //onthoudt de grootste periode van een temp.stijging
        
        for (int u = 0; u < periodeLengteList.size(); u ++) //pakt de index van de grootste periode
        {
            if (tempMax == periodeLengteList.get(u))
            {
                index1 = u;
                index2 = tempMax - u;
            }
        }
        
        index[0] = index1 * 1440;
        index[1] = index2 * 1440;
        return index; 
    }       
}
