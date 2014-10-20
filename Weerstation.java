
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

public class Weerstation {
    WeerstationConnector weerstation1;
    
    Calendar now, calPeriod;

    Measurement meting1;
    ArrayList<Measurement> meting2;
    Timer starter;
    int currentScreen;
    boolean wait;
    boolean graph;
    boolean startup;
    boolean startupState;
    
    public Weerstation(){
    	now = Calendar.getInstance();
    	calPeriod = Calendar.getInstance();
    	ArrayList<Periode> periods = new ArrayList<Periode>();
    	
    	GUIboard.init();
    	startupState = true;
        starter = new Timer();
        startAnimatie();
    	
		calPeriod.add(Calendar.DATE, -1);
		periods.add(new Periode(now, calPeriod));
		
		calPeriod.add(Calendar.DATE, -6);
		periods.add(new Periode(now, calPeriod));
		
		calPeriod.add(Calendar.MONTH, -1);
		periods.add(new Periode(now, calPeriod));
		
		calPeriod.add(Calendar.MONTH, -2);
		periods.add(new Periode(now, calPeriod));
		
		calPeriod.add(Calendar.MONTH, -3);
		periods.add(new Periode(now, calPeriod));
		
		calPeriod.add(Calendar.MONTH, -6);
		periods.add(new Periode(now, calPeriod));
		
		calPeriod.add(Calendar.YEAR, -1);
		periods.add(new Periode(now, calPeriod));
        
		System.out.println(periods.get(0));
	    
        weerstation1 = new WeerstationConnector();
        meting1 = weerstation1.getMostRecentMeasurement();
        meting2 = weerstation1.getAllMeasurementsBetween(periods.get(0).getBeginPeriode(), periods.get(0).getEindePeriode());

        startupState = false;
        
        currentScreen = 0;
        wait = true;
        graph = false;
        Timer timer = new Timer();

        //All the different screen classes
    
        final List<Grootheid> lstScreens = new ArrayList<Grootheid>();
        //lstScreens.add(new LangsteZomerPeriode(meting1, meting2));
        lstScreens.add(new MaximaleRegenPeriode(meting1, meting2));
        lstScreens.add(new WindDirection(meting1, meting2));
        lstScreens.add(new OutsideTemp(meting1, meting2));
        lstScreens.add(new WindChill(meting1, meting2));
        lstScreens.add(new OutsideHum(meting1, meting2));
        lstScreens.add(new Barometer(meting1, meting2));
        lstScreens.add(new AvgWindSpeed(meting1, meting2));
        lstScreens.add(new RainRate(meting1, meting2));
        lstScreens.add(new InsideTemp(meting1, meting2));
        lstScreens.add(new InsideHum(meting1, meting2));
        lstScreens.add(new CloudHeight(meting1, meting2));
        lstScreens.add(new UVLevel(meting1, meting2));
        lstScreens.add(new Zonsterkte(meting1, meting2));
        lstScreens.add(new DewPoint(meting1, meting2));
        lstScreens.add(new Sun(meting1));
        
        stopAnimatie();
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
        
        
        
        //Screen switcher
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(!wait){
                    currentScreen++;
                }
                if(currentScreen == lstScreens.size()){
                    currentScreen = 0;
                }
                
                Grootheid obj = lstScreens.get(currentScreen);
                if(graph)
                {
                    obj.displayGraph();    
                }
                else
                {
                    obj.display();      
                }
            }
        }, 0, 5*1000);
        
        //Update recent measurement every 60 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                meting1 = weerstation1.getMostRecentMeasurement();
                for(Grootheid obj : lstScreens){
                    obj.updateRecent(meting1);
                }
            }
        }, 60*1000, 60*1000);
        
        
        //Update 24hours every 60 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                meting2 = weerstation1.getAllMeasurementsLast24h();
                for(Grootheid obj : lstScreens){
                    obj.update24Hour(meting2);
                }
            }
        }, 10*60*1000, 10*60*1000);
        
        
        //Button checker
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(IO.readShort(0x100) == 1){
                    if(IO.readShort(0x80) == 1){
                        wait = true;
                        if(!graph)
                        {
                            Grootheid obj = lstScreens.get(currentScreen);
                            obj.displayGraph();
                        }
                        graph = true;
                    }else if(IO.readShort(0x80) == 0){
                        wait = false;
                        if(graph)
                        {
                            Grootheid obj = lstScreens.get(currentScreen);
                            obj.display();
                        }
                        graph = false;
                    }
                }else if(IO.readShort(0x100) == 0){
                    if(IO.readShort(0x80) == 1){
                        if(!graph)
                        {
                            Grootheid obj = lstScreens.get(currentScreen);
                            obj.displayGraph();
                        }
                        graph = true;
                    }else if(IO.readShort(0x80) == 0){
                        if(graph)
                        {
                            Grootheid obj = lstScreens.get(currentScreen);
                            obj.display();
                        }
                        graph = false;
                    }
                    wait = true;
                }               
            }
        }, 0, 100);
        
    }
    
    public void startAnimatie()
    {
        starter.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                startup = true;
                
                GUIboard.clearBottom();
                char[] charray;
                
                if(startupState)
                {
                    charray = new char[] {' ', ' ', ' ', ' ', ' ', 'C', 'o', 'n', 'n', 'e', 'c', 't', 'i', 'n', 'g'};
                }
                else
                {
                    charray = new char[]{' ', ' ', ' ', ' ', ' ', 'C', 'a', 'l', 'c', 'u', 'l', 'a', 't', 'i', 'n', 'g'};
                }
                
                for(char ch : charray)
                {
                    IO.writeShort(0x40, ch);
                }
                
                for(int i=1; i<128;i++)
                {
                    //for(int n=0; n<32;n++)
                    //{
                        //IO.writeShort(0x42, 1 << 12 | i-1 << 5 | n);
                        IO.writeShort(0x42, 1 << 12 | i << 5 | 20);
                        IO.delay(8);
                    //}
                }
                
                startup = false;
            }
        }, 0, 128*8 + 2);
    }
    
    public void stopAnimatie()
    {
        starter.cancel();
    }
}
