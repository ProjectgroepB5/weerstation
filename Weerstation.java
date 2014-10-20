 

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
    boolean currentGraph;
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
		calPeriod = Calendar.getInstance(); 
		
		calPeriod.add(Calendar.DATE, -7);
		periods.add(new Periode(now, calPeriod));
		calPeriod = Calendar.getInstance(); 
				
		calPeriod.add(Calendar.MONTH, -1);
		periods.add(new Periode(now, calPeriod));
		calPeriod = Calendar.getInstance(); 
				
		calPeriod.add(Calendar.MONTH, -3);
		periods.add(new Periode(now, calPeriod));
		calPeriod = Calendar.getInstance(); 
		
		calPeriod.add(Calendar.MONTH, -6);
		periods.add(new Periode(now, calPeriod));
		calPeriod = Calendar.getInstance(); 
		
		calPeriod.add(Calendar.YEAR, -1);
		periods.add(new Periode(now, calPeriod));
		calPeriod = Calendar.getInstance(); 
		
		calPeriod.add(Calendar.YEAR, -2);
		periods.add(new Periode(now, calPeriod));
		calPeriod = Calendar.getInstance(); 
		
		System.out.println("Day: " + periods.get(0));
		System.out.println("Week: " + periods.get(1));
		System.out.println("Month: " + periods.get(2));
		System.out.println("2 Months: " + periods.get(3));
		System.out.println("3 Months: " + periods.get(4));
		System.out.println("6 Months: " + periods.get(5));
		System.out.println("Year: " + periods.get(6));
	    
        weerstation1 = new WeerstationConnector();
        meting1 = weerstation1.getMostRecentMeasurement();
        meting2 = weerstation1.getAllMeasurementsBetween(periods.get(3).getBeginPeriode(), periods.get(3).getEindePeriode());
        currentGraph = false;
        startupState = false;
        currentScreen = 0;
        wait = true;
        graph = false;
        Timer timer = new Timer();

        //All the different screen classes
    
        final List<Grootheid> lstScreens = new ArrayList<Grootheid>();
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
        lstScreens.add(new LangsteZomerPeriode(meting1, meting2));
        
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
        GUIboard.clearTop();
        GUIboard.clearLeft();
        GUIboard.clearRight();
        
        
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
                if(graph && (graph != currentGraph))
                {
                	obj.displayGraph(); 
                	currentGraph = true;
                }
                else if(!graph)
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
                for(int i=0; i<128;i++)
                {
                        IO.writeShort(0x42, 1 << 12 | i << 5 | 26);
                        
                        if(i%21==0)
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
                        
                        IO.delay(6);
                }
                
                startup = false;
            }
        }, 0, 128*6 + 2);
    }
    
    public void stopAnimatie()
    {
        starter.cancel();
    }
}
