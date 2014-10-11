 
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Weerstation {
	WeerstationConnector weerstation1;
	Measurement meting1;
	ArrayList<Measurement> meting2;
	int currentScreen;
	boolean wait;
	
	public Weerstation(){
		weerstation1 = new WeerstationConnector();
		meting1 = weerstation1.getMostRecentMeasurement();
		meting2 = weerstation1.getAllMeasurementsLast24h();
		currentScreen = 0;
		wait = true;
		IO.init();
		Timer timer = new Timer();

		//All the different screen classes
	
		final List<Grootheid> lstScreens = new ArrayList<Grootheid>();
		lstScreens.add(new AvgWindspeed(meting1, meting2));
		lstScreens.add(new RainRate(meting1, meting2));
		lstScreens.add(new OutsideTemp(meting1, meting2));
		lstScreens.add(new InsideTemp(meting1, meting2));
		lstScreens.add(new OutsideHum(meting1, meting2));
		lstScreens.add(new InsideHum(meting1, meting2));
		lstScreens.add(new CloudHeight(meting1, meting2));
		lstScreens.add(new WindChill(meting1, meting2));
		lstScreens.add(new UVLevel(meting1, meting2));
		
		
		//Screen switcher
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	if(wait == false){
		        	currentScreen++;
	        	}
	        	if(currentScreen == lstScreens.size()){
	        		currentScreen = 0;
	        	}
	        	Grootheid obj = lstScreens.get(currentScreen);
	        	obj.display();   	
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
		    		}else if(IO.readShort(0x80) == 0){
		    			wait = false;
		    		}
	    		}else if(IO.readShort(0x100) == 0){
	    			wait = true;
	    		}	    		
	        }
	    }, 0, 100);
		
	}
}
