 


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
	
	public Weerstation(){
		weerstation1 = new WeerstationConnector();
		meting1 = weerstation1.getMostRecentMeasurement();
		meting2 = weerstation1.getAllMeasurementsLast24h();
		currentScreen = -1;
		
		IO.init();
		Timer timer = new Timer();

		//All the different screen classes
	
		final List<Object> lstScreens = new ArrayList<Object>();
		lstScreens.add(new AvgWindspeed(meting1, meting2));
		lstScreens.add(new RainRate(meting1, meting2));
		lstScreens.add(new BinnenTemperatuur(meting1, meting2));
		lstScreens.add(new BuitenTemperatuur(meting1, meting2));
		lstScreens.add(new InsideHum(meting1, meting2));
		lstScreens.add(new OutsideHum(meting1, meting2));
		
		
		//Screen switcher
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	currentScreen++;
	        	if(currentScreen == lstScreens.size()){
	        		currentScreen = 0;
	        	}
	        	Object obj = lstScreens.get(currentScreen);
	        	try {
					obj.getClass().getMethod("display").invoke(obj);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}        	
	        }
	    }, 0, 5*1000);
		
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
