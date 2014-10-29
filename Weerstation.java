import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
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
    
    boolean console;
    
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
        
        console = true;

 
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
        console();
        
        //Screen switcher
        timer.scheduleAtFixedRate(new TimerTask() 
        {
            public void run() 
            {
                if(button2)
                {
                    if(!graphIsDisplayed)
                    {
                        setScreen(currentScreen + 1);
                        if(currentScreen == 0 && button1)
                        {
                            setPeriod(periodeNr + 1);
                        }
                    }
                }
                else if(button1)
                {
                    if(!graphIsDisplayed)
                    {
                        setPeriod(periodeNr + 1);
                    }
                }

                if(button3)
                {
                    displayGraph();
                }
                else
                {
                    displayMain();
                }
            }
        }, 0, 5*1000);
        
        //Update recent measurement every 60 seconds
        timer.scheduleAtFixedRate( new TimerTask() 
        {
            public void run() 
            {
                meting1 = weerstation1.getMostRecentMeasurement();
                for (ArrayList<Grootheid> l: periodsScreens) 
                {
                    for (Grootheid obj : l) 
                    {
                        obj.updateRecent(meting1);
                    }
                }
            }
        }, 120*1000, 60*1000);
        
        //Button checker
        timer.scheduleAtFixedRate( new TimerTask() 
        {
            boolean oldButton1, oldButton2;
            public void run() 
            {
                if(IO.readShort(0x0090) == 1)
                {  //Blauw 1 aan
                    button1 = true;
                }
                else
                {
                    button1 = false;
                }
                if(IO.readShort(0x00100) == 1)
                {  //Blauw 2 aan
                    button2 = true;
                }
                else
                {
                    button2 = false;
                }
                if(IO.readShort(0x0080) == 1)
                {  //Rood aan
                    button3 = true;
                    displayGraph();
                }
                else
                {
                    button3 = false;    
                    if(graphIsDisplayed)
                    {
                        displayMain();
                    }
                }
                if(button2 == !oldButton2)
                { // If the button is changed, the play/pauze icon wil be updated
                    displayMain();
                    oldButton2 = button2;
                }
                if(button1 == !oldButton1)
                { // If the button is changed, the play/pauze icon wil be updated
                    displayMain();
                    oldButton1 = button1;
                }
            }
        }, 0, 200);
    }
    
    public void setScreen(int screen)
    {   //Set the next screen
        if (screen >= periodsScreens.get(periodeNr).size() || screen < 0)
        {
            currentScreen = 0;
        }else{
            currentScreen = screen;
        }
    }
    
    public void setPeriod(int period)
    { //Set the period
        if(period >= periodsScreens.size() || period < 0)
        {
            periodeNr = 0;
        }else{
            periodeNr = period;
        }
        setScreen(currentScreen);
    }

    public void displayGraph()
    {
        if(!graphIsDisplayed)
        {
            Grootheid obj = periodsScreens.get(periodeNr).get(currentScreen);
            obj.displayGraph();
            graphIsDisplayed = true;
        }
    }
    
    public void displayMain()
    {
        Grootheid obj = periodsScreens.get(periodeNr).get(currentScreen);
        obj.display(periods.get(periodeNr).getName(), button1, button2, meting1.getBattLevel());
        graphIsDisplayed = false;
    }
    
    public void console(){
        
      new Thread("Console")
        {
           public void run()
           {
                Scanner scanner = new Scanner(System.in);
                String voorvoegsel = "weerstation ~ $ ";
                String input;
                System.out.println("Applicatie geladen. Console is klaar voor gebruik.");
                
                while(console)
                {                    
                    System.out.print(voorvoegsel);
                    input = scanner.nextLine();
                    System.out.println(parseCommand(input));
                }
                
           }
           public String parseCommand(String command){               
                if(command.startsWith("/")){
                    command = command.substring(1);
                    String[] parts = command.split(" ");
                    int length = parts.length;
                    
                    if(length==0)
                    {
                        
                        return help();
                        
                    }
                    else if(length==1)
                    {
                        
                        switch (parts[0]){    //A command without parameters
                            case "help":
                            case "?":
                                return help();
                            case "exit":
                            case "close":
                                return exit();
                        }
                        
                    }
                    if(length==2){      //A command with 1 parameter
                      
                        switch(parts[0]){
                            case "periode":
                            case "p":
                                return periode(parts[1]);
                            case "scherm":
                            case "s":
                                return scherm(parts[1]);
                        }
                        
                    }
                }
                return notFound();
           }
           
           //Commands
           public String notFound()
           {
               return notFound(false);
           }
           public String notFound(boolean param)
           {
               if(param)
               {
                   return "Dit is geen geldige paramater. Type /help voor alle commando's";
               }
               else
               {
                   return "Dit is geen geldig commando. Type /help voor alle commando's"; 
               }
           }
           
           public String help()
           {
               String tab = "\n    ";
               String comS = "\n"; String comE = ":" + tab;
               String desc = tab + "> ";
               
               String help = "";
               
               //start
               help += "HELP\n------------";
               //help
               help += comS + "Help" + comE 
                            + "/help" + tab + "/?"
                    +  desc + "Laat dit scherm zien";
               //exit
               help += comS + "Exit" + comE 
                            + "/exit" + tab + "/close"
                    + desc + "Sluit de console permanent af"; 
               //periode
               help += comS + "Periode" + comE 
                            + "/periode <num|name>" + tab + "/p <num|name>"
                    + desc + "Zet de huidige periode op getal <num>" + desc + "Zet de huidige periode op naam <name>";
               //scherm
               help += comS + "Scherm" + comE
                            + "/scherm <num|name>" + tab + "/s <num|name>"
                    + desc + "Zet het huidige scherm op getal <num>" + desc + "Zet het huidige scherm op naam <name>";
               
               return help; 
           }
           
           public String exit()
           {
               console = false;
               return "Console is afgesloten.\nDe console kan alleen herstart worden door de applicatie opnieuw te starten.";
           }
           
           public String periode(String param)
           {
               int param1 = -1;
               try
               {
                   param1 = Integer.valueOf(param) - 1;
               }
               catch(NumberFormatException e)
               { 
                   param = param.toLowerCase();
                   if(param.contains("dag") || param.contains("day"))
                   {
                       param1 = 0;
                   }
                   else if(param.contains("week"))
                   {
                       param1 = 1;
                   }
                   else if(param.contains("month") || param.contains("maand"))
                   {
                       if(param.contains("6"))
                       {
                           param1 = 4;
                       }
                       if(param.contains("3"))
                       {
                           param1 = 3;  
                       }
                       else
                       {
                           param1 = 2;
                       }
                   }
                   else if(param.contains("year") || param.contains("jaar"))
                   {
                       if(param.contains("2"))
                       {
                          param1 = 6; 
                       }
                       else
                       {
                           param1 = 5;
                       }
                   }
                   else if(param.contains("next") || param.contains("volgend"))
                   {
                       param1 = periodeNr+1;
                   }
                   else if(param.contains("prev") || param.contains("vorig"))
                   {
                       param1 = periodeNr-1 < 0 ? periodsScreens.size()-1 : periodeNr - 1;
                   }
                   
                   if(param1==-1)
                   {
                       return notFound(true);
                   }
               }
                    
               setPeriod(param1);
               displayMain();
               return "Periode gezet";
           }
           
           public String scherm(String param)
           {
               int param1 = 0;
               try
               {
                   param1 = Integer.valueOf(param) - 1;
               }
               catch(NumberFormatException e)
               { 
                   param = param.toLowerCase();
                   if(param.contains("period"))
                   {
                       if(periodeNr < 4)
                       {
                           setPeriod(4);
                       }
                       if(param.contains("regen") || param.contains("rain"))
                       {
                           if(param.contains("max"))
                           {
                               param1=15;
                           }
                           else if(param.contains("lang") || param.contains("long"))
                           {
                               param1=19;
                           }
                       }
                       else if(param.contains("hitte") || param.contains("golf") || param.contains("heat") || param.contains("wave"))
                       {
                           param1=17;
                       }
                       else if(param.contains("zomer") || param.contains("summer"))
                       {
                           param1=18;
                       }
                       else if(param.contains("droog") || param.contains("droge") || param.contains("dry"))
                       {
                           param1=20;
                       }
                       else
                       {
                           param1=15;
                       }
                   }
                   else if(param.contains("graad"))
                   {
                       if(periodeNr < 4)
                       {
                           setPeriod(4);
                       }
                       param1=16;
                   }
                   else if(param.contains("temp"))
                   {
                       if(param.contains("binnen") || param.contains("inside"))
                       {
                           param1=7;
                       }
                       else if(param.contains("gevoel"))
                       {
                           param1=4;
                       }
                       else
                       {
                           param1=0;
                       }
                   }
                   else if(param.contains("hum") || param.contains("vocht"))
                   {
                       if(param.contains("binnen") || param.contains("inside"))
                       {
                           param1=8;
                       }
                       else
                       {
                           param1=1;
                       }
                   }
                   else if(param.contains("wind"))
                   {
                       if(param.contains("direction") || param.contains("richting"))
                       {
                           param1=3;
                       }
                       else if(param.contains("chill"))
                       {
                           param1=4;
                       }
                       else
                       {
                           param1=2;
                       }
                   }
                   else if(param.contains("rain") || param.contains("regen"))
                   {
                       param1=2;
                   }
                   else if(param.contains("druk") || param.contains("baro"))
                   {
                       param1=6;
                   }
                   else if(param.contains("next") || param.contains("volgend"))
                   {
                       param1 = currentScreen+1;
                   }
                   else if(param.contains("prev") || param.contains("vorig"))
                   {
                       param1 = currentScreen-1 < 0 ? periodsScreens.get(periodeNr).size()-1 : currentScreen - 1;
                   }
                   
                   if(param1==-1)
                   {
                       return notFound(true);
                   }
               }
               setScreen(param1);
               displayMain();
               return "Scherm gezet";
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
        starter.scheduleAtFixedRate(new TimerTask() 
        {
            public void run() 
            {
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
        animator.scheduleAtFixedRate(new TimerTask() 
        {
            public void run() 
            {
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
    
    public void createPeriods()
    {
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
        periods.add(new Periode(now, calPeriod, "3 Maanden"));
        calPeriod = Calendar.getInstance(); 
        
        calPeriod.add(Calendar.MONTH, -6);
        periods.add(new Periode(now, calPeriod, "6 Maanden"));
        calPeriod = Calendar.getInstance(); 
        
        calPeriod.add(Calendar.YEAR, -1);
        periods.add(new Periode(now, calPeriod, "Jaar"));
        calPeriod = Calendar.getInstance(); 
        
        calPeriod.add(Calendar.YEAR, -2);
        periods.add(new Periode(now, calPeriod, "2 Jaar"));
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
    
    public void backgroundLoader()
    {
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
                
                day.add(new Voorspellingen(meting1));       //Voorspellingen
                
                periodsScreens.add(day);
                    
                
                animator = new Timer();
                startLoadAnimatie();
                
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
                month6.add(new LangsteDroogstePeriode(meting1, meting2));           //Langste Droogste Periode
                //month6.add(new LangsteTempStijgingPeriode(meting1, meting2));     //Langste temperatuurstijging
                
                periodsScreens.add(month6);
                
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
                year.add(new LangsteDroogstePeriode(meting1, meting2));           //Langste Droogste Periode
                //year.add(new LangsteTempStijgingPeriode(meting1, meting2));     //Langste temperatuurstijging
                
                periodsScreens.add(year);
                    
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
                year2.add(new LangsteRegenPeriode(meting1, meting2)); 
                year2.add(new LangsteDroogstePeriode(meting1, meting2));           //Langste Droogste Periode
                //Langste Regen Periode
                //year2.add(new LangsteTempStijgingPeriode(meting1, meting2));     //Langste temperatuurstijging
                
                periodsScreens.add(year2);
                
                stopLoadAnimatie();
            }
        }.start(); 
    }
}


