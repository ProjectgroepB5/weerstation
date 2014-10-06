package weerstation;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
	static Weerstation weerstation1;
	static Measurement meting1;
	static ArrayList<Measurement> meting2;
	
	public static void main(String[] args){
		weerstation1 = new Weerstation();
		meting1 = weerstation1.getMostRecentMeasurement();
		meting2 = weerstation1.getAllMeasurementsLast24h();
		IO.init();
		Timer timer = new Timer();

		//All the different screen classes
	
		final List<Object> lstScreens = new ArrayList<Object>();
		lstScreens.add(new AvgWindspeed(meting1, meting2, 1));
		
		//Screen switcher
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {

	        }
	    }, 0, 15*1000);
		
		//Update recent measurement every 60 seconds
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	
	    		meting1 = weerstation1.getMostRecentMeasurement();
	    		for(Object obj : lstScreens){
	    			try {
						obj.getClass().getMethod("updateRecent", Measurement.class).invoke(obj, meting1 );
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
	    		}
	        }
	    }, 60*1000, 60*1000);
		
		
		//Update 24hours every 60 seconds
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	meting2 = weerstation1.getAllMeasurementsLast24h();
	    		for(Object obj : lstScreens){
	    			try {
						obj.getClass().getMethod("update24Hour", ArrayList.class).invoke(obj, meting2 );
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
	    		}
	        }
	    }, 10*60*1000, 10*60*1000);
		
		
		//Button checker
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	    		
	        }
	    }, 0, 60*1000);
		
	}
}
