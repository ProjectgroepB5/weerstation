package weerstation1;
import java.util.ArrayList;

public class WindDirection extends Grootheid{
   
	public WindDirection(Measurement measurement1, ArrayList<Measurement> measurement2){
    	setName("Windrichting");
    	updateRecent(measurement1);
        updatePeriod(measurement2);
    }

    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getRawWindDir());
    }
    public void updatePeriod(ArrayList<Measurement> measurement2){
        createList(measurement2);
        maxMin();
        modus();
    }
    
    public void displayGraph()
    {
        GUIboard.clearBottom();
        char[] charray = "   West       East".toCharArray();
        
        IO.writeShort(0x40, '\n'); 
        for(char ch : charray)
        {
            IO.writeShort(0x40, ch);
        }
        
        int x,y;
        int radius = 15;
        double degree= getCurrent();
        int x2 = 0;
        int y2 = 0;
        
        degree -= 90;
        
        if( degree < 0){
            degree += 360;
        }
        
        for(double i = 0; i< 360; i+= 1)
        {
            x = (int)(64 + (radius*Math.cos(i * Math.PI / 180)));
            y = (int)(16 + radius*Math.sin(i * Math.PI / 180));
            if(i == degree){
                x2 = x;
                y2 = y;
            }
            IO.writeShort(0x42, 1 << 12 | x << 5 | y);
        }     
        line(64, 16, x2, y2);    
    }
 
    public static void line(int x1,int y1,int x2, int y2) {
        
        if(x1 > x2)
        {
            int x3 = x1;
            x1 = x2;
            x2 = x3;
            int y3 = y1;
            y1 = y2;
            y2 = y3;
        }
        
        
        int x,y;
        int deltax = x2 - x1 ;
        int deltay = y2 - y1 ;
        for (x=x1; x<=x2; x++) {
            y = y1 + deltay * (x - x1) / deltax;
            IO.writeShort(0x42, 1 << 12 | x << 5 | y);
        }
    }
    
    private void createList(ArrayList<Measurement> measurement2)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        
        for(Measurement ms : measurement2)
        {
            list.add( (double)ms.getRawWindDir());
        }
    }
}
