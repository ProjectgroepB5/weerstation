 
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

public class Weerstation {
    WeerstationConnector weerstation1;
    Calendar now;
    Measurement meting1;
    Periode periodeDag;
    ArrayList<Measurement> meting2;
    Timer starter;
    int currentScreen;
    boolean wait;
    boolean graph;
    boolean startup;
    
    public Weerstation(){
        weerstation1 = new WeerstationConnector();
        now = Calendar.getInstance();
        
        periodeDag = new Periode(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);
        
        GUIboard.init();
        starter = new Timer();
        startAnimatie();
        
        meting1 = weerstation1.getMostRecentMeasurement();
        meting2 = weerstation1.getAllMeasurementsBetween(periodeDag.getBeginPeriode(), periodeDag.getEindePeriode());
        
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
        
        
        currentScreen = 0;
        wait = true;
        graph = false;
        Timer timer = new Timer();

        //All the different screen classes
    
        final List<Grootheid> lstScreens = new ArrayList<Grootheid>();
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
                
                for(int i=1; i<128;i+=2)
                {
                    for(int n=0; n<32;n++)
                    {
                        IO.writeShort(0x42, 1 << 12 | i-1 << 5 | n);
                        IO.writeShort(0x42, 1 << 12 | i << 5 | n);
                        IO.delay(1);
                    }
                }
                
                startup = false;
            }
        }, 0, 128*32);
    }
    
    public void stopAnimatie()
    {
        starter.cancel();
    }
}
