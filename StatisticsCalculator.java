 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

/**
 * Berekent allerlei statistische waarden voor de gegeven data
 * 
 * @author Projectgroep B5
 */
public class StatisticsCalculator {
    
    /**
     * Berekent de maximale waarde
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De gevonden maximale waarde
     */
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
    
    /**
     * Berekent de minimale waarde
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De gevonden minimale waarde
     */
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
    
    /**
     * Berekent de gemiddelde waarde
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De gevonden gemiddelde waarde
     */
    public static double avg(ArrayList<Double> array)
    {
        double avg = 0;
        
        for(double waarde : array){
            avg += waarde;
        }
        avg /= array.size();
        return avg;
    }
    
    /**
     * Berekent de mediaan
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De gevonden mediaan
     */
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
    
    /**
     * Berekent de modus
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De gevonden modus
     */
    public static double modus(ArrayList<Double> array){
        Map<Double,Integer> map = new HashMap<Double,Integer>();
        for(double number : array)
        {
            if(map.containsKey(number)){
                map.put(number,(int)map.get(number)+1);
            }else{
                map.put(number, 1);
            }
        }
        
        double maxVal = 0;
        double maxKey = 0;
        
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxVal) {
                maxVal = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        
        return maxKey;
    }   
        
    /**
     * Berekent de afwijking
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De gevonden afwijking
     */
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
    
    public static int langsteDroogstePeriode(ArrayList<Double> array)
    {
        return langsteDroogstePeriodeMetMax(array, 0);
    }
    
    public static int langsteDroogstePeriodeMetMax(ArrayList<Double> array, int maxNeerslag)
    {    
        int i = 0;
        double maximaleRegenval = 0;
        ArrayList<Double> maxRegenval = new ArrayList<Double>();
        for(double rainAmount : array)
        {
            i++;
            if(i%1440==0)
            {
                maxRegenval.add(maximaleRegenval);
                maximaleRegenval = 0;
            }
            if(rainAmount > maximaleRegenval)
            {
                maximaleRegenval = rainAmount;
            }
        }
        int periode = 0;
        int langstePeriode = 0;
        for(int r=0; r<maxRegenval.size(); r++)
        {
            if(maxRegenval.get(r) > maxNeerslag)
            {
                periode = 0;
            }
            else
            {
                periode ++;
                if(periode > langstePeriode)
                {
                    langstePeriode = periode;
                }
            }
        }
        return langstePeriode;
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
    
    /**
     * Berekent de totale regenval
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De gevonden totale regenval
     */
    public static short maximaleRegenPeriode(ArrayList<Double> array)
    {
        short regen = 0;
        ArrayList<Short> regenPerUur = new ArrayList<Short>();
        
        for(int i=0; i<array.size();i++)
        {
            if(i%60==0)
            {
                regen /= 60;
                regenPerUur.add(regen);
                regen = 0;
            }
            
            regen += array.get(i);
        }
        
        
        short totaleRegen = 0;
        
        for(short sh : regenPerUur)
        {
            totaleRegen += sh;
        }
        
        return totaleRegen;
    }
    
    /**
     * Berekent de langste periode waarin het boven 25 graden celcius is geweest
     * 
     * @param array Een ArrayList met alle waarden
     * 
     * @return De posities van de begin en eindtijd in de array van de periode
     */
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
               maxTempDag.add( (double) maxTemp);
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
            if(maxTempDag.get(t) > 770) {
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
    

    
    public static Periode langsteHittegolfPeriode(ArrayList<Measurement> array){
		double maxDay = 0;
		ArrayList<Double> maxTempPerDay = new ArrayList<Double>();
			
		//calculate the max for every day
		for(int i = 0; i < array.size(); i++){
			if((i % 1440) == 0){
				maxTempPerDay.add(maxDay);
				maxDay = 0;
			}
			if(array.get(i).getRawOutsideTemp() > maxDay){
				maxDay = array.get(i).getRawOutsideTemp();
			}
		}
		
		int periodLength = 0;
		int maxPeriodLength = 5;
		int numberOfTropicalDays = 0;
	
		Calendar begin = Calendar.getInstance();
		Calendar eind = Calendar.getInstance();
		Periode periode = new Periode(begin, eind, "Geen hittegolfperiode gevonden");
		
		for(int i = 0; i < maxTempPerDay.size(); i++){	
			if( maxTempPerDay.get(i) >= 770){		// If the temperature is bigger then 25 degree Celcius (770 degree Fahrenheit) the summer period will start				
				periodLength++;
				if (maxTempPerDay.get(i) >= 860){	// If the temperature is bigger then 30 degree Celcius (860 degree Fahrenheit) it will add a tropical day					
					numberOfTropicalDays++;
				}
			}else if(periodLength > maxPeriodLength && numberOfTropicalDays >= 3){					//period has ended. If it is bigger then 5 days AND there are 3 or more tropical days, the period is a heat wave	
				maxPeriodLength = periodLength;
				periodLength = 0;
				numberOfTropicalDays = 0;
				eind.setTimeInMillis(array.get((i-1)*1440).getDateStamp().getTime());
				periode = new Periode(eind, begin, "Langste hittegolf periode");
			}else{
				numberOfTropicalDays = 0;
				periodLength = 0;
			}
			if(periodLength == 5){
				begin.setTimeInMillis(array.get((i-5)*1440).getDateStamp().getTime());
			}
		}
		return periode;
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
    


    

