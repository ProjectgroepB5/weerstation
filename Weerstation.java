 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  De main klasse die het hele programma aanstuurt
 *  
 *  @author Projectgroep B5
 */
public class Weerstation{
    WeerstationConnector weerstation1;
    
    Calendar now, calPeriod;

    Measurement meting1;
    ArrayList<Measurement> meting2;
    ArrayList<Periode> periods;
    ArrayList<Grootheid> day, week, month, month3, month6, year, year2;
    ArrayList<ArrayList<Grootheid>> periodsScreens;
    
    Timer starter;
    Timer animator;
    
    int currentScreen;
    int periodeNr;

    boolean startup, startupState, load;
    
    boolean button1, button2, button3, graphIsDisplayed;
    
    /**
     * Constructor voor weerstation
     */
    public Weerstation(){
        GUIboard.init();
        startupState = true;
        starter = new Timer();
        startStartupAnimatie();
        Timer timer = new Timer();
        
        createPeriods();
        backgroundLoader();
        
        currentScreen = 0;
        periodeNr = 0;
        graphIsDisplayed = false;

 
        //De thread die op de achtergrond alles laad
      
      
        while(periodsScreens.size()<=0)
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
            
        stopStartupAnimatie();
        while(startup)
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        GUIboard.clearTop();
        GUIboard.clearLeft();
        GUIboard.clearRight();
        
        
        //Screen switcher
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(button2){
                    currentScreen++;
                    if (currentScreen+1 > periodsScreens.get(periodeNr).size()){
                        currentScreen = 0;
                        if(button1){
                            periodeNr++;
                        }
                    }
                }else if(button1){
                    periodeNr++;
                }
                if(periodeNr+1 > periodsScreens.size()){
                    periodeNr = 0;
                }
                Grootheid obj = periodsScreens.get(periodeNr).get(currentScreen);
                
                if(button3){
                    if(!graphIsDisplayed){
                        obj.displayGraph();
                        graphIsDisplayed = true;
                    }
                }else{
                    obj.display(periods.get(periodeNr).getName());
                    graphIsDisplayed = false;
                }
                

            }
        }, 0, 5*1000);
        
        //Update recent measurement every 60 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                meting1 = weerstation1.getMostRecentMeasurement();
                for (ArrayList<Grootheid> l: periodsScreens) {
                    for (Grootheid obj : l) {
                        obj.updateRecent(meting1);
                    }
                }
            }
        }, 60*1000, 60*1000);
        
        //Button checker
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(IO.readShort(0x0090) == 1){  //Blauw 1 aan
                    button1 = true;
                }else{
                    button1 = false;
                }
                if(IO.readShort(0x00100) == 1){  //Blauw 2 aan
                    button2 = true;
                }else{
                    button2 = false;
                }
               
                Grootheid obj = periodsScreens.get(periodeNr).get(currentScreen);
               
                if(IO.readShort(0x0080) == 1){  //Rood aan
                    button3 = true;
                    if(!graphIsDisplayed){
                        obj.displayGraph();
                        graphIsDisplayed = true;
                    }
                }else{
                    if(graphIsDisplayed){
                        obj.display(periods.get(periodeNr).getName());
                        graphIsDisplayed = false;
                    }
                    button3 = false;    
                }
            }
        }, 0, 100);
    }
    
    public void createPeriods(){
        now = Calendar.getInstance();
        calPeriod = Calendar.getInstance();
        periods = new ArrayList<Periode>();
        
        calPeriod.add(Calendar.DATE, -1);
        periods.add(new Periode(now, calPeriod, "Dag"));
        calPeriod = Calendar.getInstance(); 
        
        calPeriod.add(Calendar.DATE, -7);
        periods.add(new Periode(now, calPeriod, "Week"));
        calPeriod = Calendar.getInstance(); 
                
        calPeriod.add(Calendar.MONTH, -1);
        periods.add(new Periode(now, calPeriod, "Maand"));
        calPeriod = Calendar.getInstance(); 
                
        calPeriod.add(Calendar.MONTH, -3);
        periods.add(new Periode(now, calPeriod, "Maand 3"));
        calPeriod = Calendar.getInstance(); 
        
        calPeriod.add(Calendar.MONTH, -6);
        periods.add(new Periode(now, calPeriod, "Maand 6"));
        calPeriod = Calendar.getInstance(); 
        
        calPeriod.add(Calendar.YEAR, -1);
        periods.add(new Periode(now, calPeriod, "Jaar"));
        calPeriod = Calendar.getInstance(); 
        
        calPeriod.add(Calendar.YEAR, -2);
        periods.add(new Periode(now, calPeriod, "Jaar 2"));
        calPeriod = Calendar.getInstance(); 
        
        weerstation1 = new WeerstationConnector();
        day = new ArrayList<Grootheid>();
        week = new ArrayList<Grootheid>();
        month = new ArrayList<Grootheid>();
        month3 = new ArrayList<Grootheid>();
        month6 = new ArrayList<Grootheid>();
        year = new ArrayList<Grootheid>();
        year2 = new ArrayList<Grootheid>();
        periodsScreens = new ArrayList<ArrayList<Grootheid>>();
    }
    
    public void backgroundLoader(){
          new Thread("Backgroundloader")
          {
             public void run()
                {
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    meting1 = weerstation1.getMostRecentMeasurement();
                    meting2 = weerstation1.getAllMeasurementsBetween(periods.get(0).getBeginPeriode(), periods.get(0).getEindePeriode());
                    startupState = false;
                    
                    //Day
                    day.add(new OutsideTemp(meting1, meting2));          //Buitentemperatuur
                    day.add(new OutsideHum(meting1, meting2));           //Luchtv. Buiten
                    
                    day.add(new AvgWindSpeed(meting1, meting2));         //Gem. Windsnelheid
                    day.add(new WindDirection(meting1, meting2));        //Windrichting
                    day.add(new WindChill(meting1, meting2));            //Gevoelstemperatuur
                    day.add(new RainRate(meting1, meting2));             //Regenval
                    
                    day.add(new Barometer(meting1, meting2));            //Luchtdruk
                    day.add(new InsideTemp(meting1, meting2));           //Binnentemperatuur
                    day.add(new InsideHum(meting1, meting2));            //Luchtv. Binnen
                    day.add(new Zonsterkte(meting1, meting2));           //Zonkracht
                    day.add(new Sun(meting1));                           //Sunrise en Sunset
                    
                    day.add(new UVLevel(meting1, meting2));              //UV Level
                    day.add(new HeatIndex(meting1, meting2));            //Dauwpunt
                    day.add(new DewPoint(meting1, meting2));             //Dauwpunt
                    day.add(new CloudHeight(meting1, meting2));          //Wolkhoogte
                    
                    periodsScreens.add(day);
                        
                    
                    animator = new Timer();
                    startLoadAnimatie();
                    
                    meting1 = weerstation1.getMostRecentMeasurement();
                    meting2 = weerstation1.getAllMeasurementsBetween(periods.get(1).getBeginPeriode(), periods.get(1).getEindePeriode());
                    
                    //Week
                    week.add(new OutsideTemp(meting1, meting2));          //Buitentemperatuur
                    week.add(new OutsideHum(meting1, meting2));           //Luchtv. Buiten
                    
                    week.add(new AvgWindSpeed(meting1, meting2));         //Gem. Windsnelheid
                    week.add(new WindDirection(meting1, meting2));        //Windrichting
                    week.add(new WindChill(meting1, meting2));            //Gevoelstemperatuur
                    week.add(new RainRate(meting1, meting2));             //Regenval
                    
                    week.add(new Barometer(meting1, meting2));            //Luchtdruk
                    week.add(new InsideTemp(meting1, meting2));           //Binnentemperatuur
                    week.add(new InsideHum(meting1, meting2));            //Luchtv. Binnen
                    week.add(new Zonsterkte(meting1, meting2));           //Zonkracht
                    week.add(new Sun(meting1));                           //Sunrise en Sunset
                    
                    week.add(new UVLevel(meting1, meting2));              //UV Level
                    week.add(new HeatIndex(meting1, meting2));            //Dauwpunt
                    week.add(new DewPoint(meting1, meting2));             //Dauwpunt
                    week.add(new CloudHeight(meting1, meting2));          //Wolkhoogte
                        
                    periodsScreens.add(week);
                    
                    meting1 = weerstation1.getMostRecentMeasurement();
                    meting2 = weerstation1.getAllMeasurementsBetween(periods.get(2).getBeginPeriode(), periods.get(2).getEindePeriode());
                        
                    //Month
                    month.add(new OutsideTemp(meting1, meting2));          //Buitentemperatuur
                    month.add(new OutsideHum(meting1, meting2));           //Luchtv. Buiten
                    
                    month.add(new AvgWindSpeed(meting1, meting2));         //Gem. Windsnelheid
                    month.add(new WindDirection(meting1, meting2));        //Windrichting
                    month.add(new WindChill(meting1, meting2));            //Gevoelstemperatuur
                    month.add(new RainRate(meting1, meting2));             //Regenval
                    
                    month.add(new Barometer(meting1, meting2));            //Luchtdruk
                    month.add(new InsideTemp(meting1, meting2));           //Binnentemperatuur
                    month.add(new InsideHum(meting1, meting2));            //Luchtv. Binnen
                    month.add(new Zonsterkte(meting1, meting2));           //Zonkracht
                    month.add(new Sun(meting1));                           //Sunrise en Sunset
                    
                    month.add(new UVLevel(meting1, meting2));              //UV Level
                    month.add(new HeatIndex(meting1, meting2));            //Dauwpunt
                    month.add(new DewPoint(meting1, meting2));             //Dauwpunt
                    month.add(new CloudHeight(meting1, meting2));          //Wolkhoogte
                    
                    month.add(new MaximaleRegenPeriode(meting1, meting2));             //Totale regenval in een periode
                    month.add(new GraadDagen(meting1, meting2));                       //Aantal graaddagen in een periode
                        
                    periodsScreens.add(month);
                    
                    meting1 = weerstation1.getMostRecentMeasurement();
                    meting2 = weerstation1.getAllMeasurementsBetween(periods.get(3).getBeginPeriode(), periods.get(3).getEindePeriode());
                        
                    //3 Months
                    month3.add(new OutsideTemp(meting1, meting2));          //Buitentemperatuur
                    month3.add(new OutsideHum(meting1, meting2));           //Luchtv. Buiten
                    
                    month3.add(new AvgWindSpeed(meting1, meting2));         //Gem. Windsnelheid
                    month3.add(new WindDirection(meting1, meting2));        //Windrichting
                    month3.add(new WindChill(meting1, meting2));            //Gevoelstemperatuur
                    month3.add(new RainRate(meting1, meting2));             //Regenval
                    
                    month3.add(new Barometer(meting1, meting2));            //Luchtdruk
                    month3.add(new InsideTemp(meting1, meting2));           //Binnentemperatuur
                    month3.add(new InsideHum(meting1, meting2));            //Luchtv. Binnen
                    month3.add(new Zonsterkte(meting1, meting2));           //Zonkracht
                    month3.add(new Sun(meting1));                           //Sunrise en Sunset
                    
                    month3.add(new UVLevel(meting1, meting2));              //UV Level
                    month3.add(new HeatIndex(meting1, meting2));            //Dauwpunt
                    month3.add(new DewPoint(meting1, meting2));             //Dauwpunt
                    month3.add(new CloudHeight(meting1, meting2));          //Wolkhoogte
                    
                    month3.add(new MaximaleRegenPeriode(meting1, meting2));             //Totale regenval in een periode
                    month3.add(new GraadDagen(meting1, meting2));                       //Aantal graaddagen in een periode
                        
                    periodsScreens.add(month3);
                    
                    meting1 = weerstation1.getMostRecentMeasurement();
                    meting2 = weerstation1.getAllMeasurementsBetween(periods.get(4).getBeginPeriode(), periods.get(4).getEindePeriode());
                        
                    //6 Months
                    month6.add(new OutsideTemp(meting1, meting2));          //Buitentemperatuur
                    month6.add(new OutsideHum(meting1, meting2));           //Luchtv. Buiten
                    
                    month6.add(new AvgWindSpeed(meting1, meting2));         //Gem. Windsnelheid
                    month6.add(new WindDirection(meting1, meting2));        //Windrichting
                    month6.add(new WindChill(meting1, meting2));            //Gevoelstemperatuur
                    month6.add(new RainRate(meting1, meting2));             //Regenval
                    
                    month6.add(new Barometer(meting1, meting2));            //Luchtdruk
                    month6.add(new InsideTemp(meting1, meting2));           //Binnentemperatuur
                    month6.add(new InsideHum(meting1, meting2));            //Luchtv. Binnen
                    month6.add(new Zonsterkte(meting1, meting2));           //Zonkracht
                    month6.add(new Sun(meting1));                           //Sunrise en Sunset
                    
                    month6.add(new UVLevel(meting1, meting2));              //UV Level
                    month6.add(new HeatIndex(meting1, meting2));            //Dauwpunt
                    month6.add(new DewPoint(meting1, meting2));             //Dauwpunt
                    month6.add(new CloudHeight(meting1, meting2));          //Wolkhoogte
                    
                    month6.add(new MaximaleRegenPeriode(meting1, meting2));             //Totale regenval in een periode
                    month6.add(new GraadDagen(meting1, meting2));                       //Aantal graaddagen in een periode
                    month6.add(new LangsteHittegolfPeriode(meting1, meting2));          //Langste Hittegolf Periode
                    month6.add(new LangsteZomerPeriode(meting1, meting2));              //Langste Zomerse Periode
                    month6.add(new LangsteRegenPeriode(meting1, meting2));              //Langste Regen Periode
                    //month6.add(new LangsteTempStijgingPeriode(meting1, meting2));     //Langste temperatuurstijging
                    
                    periodsScreens.add(month6);
                    
                    meting1 = weerstation1.getMostRecentMeasurement();
                    meting2 = weerstation1.getAllMeasurementsBetween(periods.get(5).getBeginPeriode(), periods.get(5).getEindePeriode());
                    
                    //Year
                    
                    year.add(new OutsideTemp(meting1, meting2));          //Buitentemperatuur
                    year.add(new OutsideHum(meting1, meting2));           //Luchtv. Buiten
                    
                    year.add(new AvgWindSpeed(meting1, meting2));         //Gem. Windsnelheid
                    year.add(new WindDirection(meting1, meting2));        //Windrichting
                    year.add(new WindChill(meting1, meting2));            //Gevoelstemperatuur
                    year.add(new RainRate(meting1, meting2));             //Regenval
                    
                    year.add(new Barometer(meting1, meting2));            //Luchtdruk
                    year.add(new InsideTemp(meting1, meting2));           //Binnentemperatuur
                    year.add(new InsideHum(meting1, meting2));            //Luchtv. Binnen
                    year.add(new Zonsterkte(meting1, meting2));           //Zonkracht
                    year.add(new Sun(meting1));                           //Sunrise en Sunset
                    
                    year.add(new UVLevel(meting1, meting2));              //UV Level
                    year.add(new HeatIndex(meting1, meting2));            //Dauwpunt
                    year.add(new DewPoint(meting1, meting2));             //Dauwpunt
                    year.add(new CloudHeight(meting1, meting2));          //Wolkhoogte
                    
                    year.add(new MaximaleRegenPeriode(meting1, meting2));             //Totale regenval in een periode
                    year.add(new GraadDagen(meting1, meting2));                       //Aantal graaddagen in een periode
                    year.add(new LangsteHittegolfPeriode(meting1, meting2));          //Langste Hittegolf Periode
                    year.add(new LangsteZomerPeriode(meting1, meting2));              //Langste Zomerse Periode
                    year.add(new LangsteRegenPeriode(meting1, meting2));              //Langste Regen Periode
                    //year.add(new LangsteTempStijgingPeriode(meting1, meting2));     //Langste temperatuurstijging
                    
                    periodsScreens.add(year);
                        
                    meting1 = weerstation1.getMostRecentMeasurement();
                    meting2 = weerstation1.getAllMeasurementsBetween(periods.get(6).getBeginPeriode(), periods.get(6).getEindePeriode());
                    
                    //2 Years
                
                    year2.add(new OutsideTemp(meting1, meting2));          //Buitentemperatuur
                    year2.add(new OutsideHum(meting1, meting2));           //Luchtv. Buiten
                    
                    year2.add(new AvgWindSpeed(meting1, meting2));         //Gem. Windsnelheid
                    year2.add(new WindDirection(meting1, meting2));        //Windrichting
                    year2.add(new WindChill(meting1, meting2));            //Gevoelstemperatuur
                    year2.add(new RainRate(meting1, meting2));             //Regenval
                    
                    year2.add(new Barometer(meting1, meting2));            //Luchtdruk
                    year2.add(new InsideTemp(meting1, meting2));           //Binnentemperatuur
                    year2.add(new InsideHum(meting1, meting2));            //Luchtv. Binnen
                    year2.add(new Zonsterkte(meting1, meting2));           //Zonkracht
                    year2.add(new Sun(meting1));                           //Sunrise en Sunset
                    
                    year2.add(new UVLevel(meting1, meting2));              //UV Level
                    year2.add(new HeatIndex(meting1, meting2));            //Dauwpunt
                    year2.add(new DewPoint(meting1, meting2));             //Dauwpunt
                    year2.add(new CloudHeight(meting1, meting2));          //Wolkhoogte

                    year2.add(new MaximaleRegenPeriode(meting1, meting2));             //Totale regenval in een periode
                    year2.add(new GraadDagen(meting1, meting2));                       //Aantal graaddagen in een periode
                    year2.add(new LangsteHittegolfPeriode(meting1, meting2));          //Langste Hittegolf Periode
                    year2.add(new LangsteZomerPeriode(meting1, meting2));              //Langste Zomerse Periode
                    year2.add(new LangsteRegenPeriode(meting1, meting2));              //Langste Regen Periode
                    //year2.add(new LangsteTempStijgingPeriode(meting1, meting2));     //Langste temperatuurstijging
                    
                    periodsScreens.add(year2);
                    
                    stopLoadAnimatie();
                }
          }.start(); 
    }
    /**
     * Start de animatie die speelt tijdens het opstarten en laden van het weerstation
     * 
     * @param starter De timer die de animatie laat lopen
     */
    public void startStartupAnimatie()
    {
        starter.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                startup = true;
                
                GUIboard.clearBottom();
                GUIboard.clearTop();
                GUIboard.clearLeft();
                GUIboard.clearRight();
                
                char[] charray;
                
                if(startupState)
                {
                    charray = " Weerstation Breda\n     Connecting".toCharArray();
                }
                else
                {
                    charray = " Weerstation Breda\n     Calculating".toCharArray();
                }
                
                for(char ch : charray)
                {
                    IO.writeShort(0x40, ch);
                }
                
                int p = 1;
                for(int i=0; i<256;i++)
                {
                        if(i<128)
                        {
                            IO.writeShort(0x42, 1 << 12 | i << 5 | 26);
                        }
                        else
                        {
                            IO.writeShort(0x42, 0 << 12 | i-128 << 5 | 26);
                        }
                        
                        if(i%42==0)
                        {
                            p = p << 1;
                            if(p>32)
                            {
                                p=1;
                            }
                            for(int q=0x10; q<0x40; q+=0x02)
                            {
                                IO.writeShort(q, 0x100 | p);
                            }
                        }
                        
                        IO.delay(4);
                }
                
                startup = false;
            }
        }, 0, (128 + 1)*8);
    }
    
    /**
     * Stopt de animatie die speelt tijdens het opstarten en laden van het weerstation
     */
    public void stopStartupAnimatie()
    {
        starter.cancel();
    }
    
    /**
     * Start de animatie die speelt tijdens het laden op de achtergrond van het weerstation
     */
    public void startLoadAnimatie()
    {
        animator.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                load = true;
                for(int i=0; i<128;i++)
                {
                        if(!graphIsDisplayed)
                        {
                            IO.writeShort(0x42, 1 << 12 | i << 5 | 31);
                        }
                        IO.delay(4);
                }
                
                for(int i=0; i<128;i++)
                {
                        if(!graphIsDisplayed)
                        {
                            IO.writeShort(0x42, 0 << 12 | i << 5 | 31);
                        }
                        IO.delay(4);
                }
                load = false;
            }
        }, 0, (128 + 1)*8);
    }
    
    /**
     * Stopt de animatie die speelt tijdens het laden op de achtergrond van het weerstation
     */
    public void stopLoadAnimatie()
    {
        animator.cancel();
    }
}


