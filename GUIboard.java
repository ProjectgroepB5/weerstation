 

import java.util.ArrayList;

public class GUIboard {
	

	public static void writeUpperDigits(double number){
		clearTop();
		writeDigits(number, 0x10, 0x18);
	}
	
	public static void writeLeftDigits(double number){
		clearLeft();
		writeDigits(number, 0x20, 0x24);
	}
	
	public static void writeRightDigits(double number){
		clearRight();
		writeDigits(number, 0x30, 0x34);
	}
	
	private static void writeDigits(double number, int firstSegment, int lastSegment){
		number = Math.round(number * 100.0) / 100.0;
		//Segments
	     int segA = 0x01;
	     int segB = 0x02;
	     int segC = 0x04;
	     int segD = 0x08;
	     int segE = 0x10;
	     int segF = 0x20;
	     int segG = 0x40;
	     int segDP = 0x80;
	    
	    //Digits
	    int[] digits = {
	    segA | segB | segC | segD | segE | segF,    //0
	    segB | segC,                                //1
	    segA | segB | segG | segE | segD,           //2
	    segA | segB | segC | segG | segD,           //3
	    segB | segC | segF | segG | segC,           //4
	    segA | segF | segG | segC | segD,           //5
	    segA | segF | segG | segC | segD | segE,    //6
	    segA | segB | segC,                         //7
	    segDP - 1,                                  //8
	    segA | segF | segG | segC | segD | segB,    //9
	    };
	    
		int digit = firstSegment;
		String numberString = String.valueOf(number);; 
		char numberSplit[] = numberString.toCharArray();
		
		for(int i = numberSplit.length-1; i >= 0; i--){	
			if(numberSplit[i] == '.'){
				IO.writeShort(digit, digits[(Character.getNumericValue(numberSplit[i - 1]))]|0x180);	//display a . with the next number
				i--;
			}else if(numberSplit[i] == '-'){
				IO.writeShort(digit, 0x140);			//will display a -
			}else{
				IO.writeShort(digit, Character.getNumericValue(numberSplit[i]));
			}
			digit += 2;						 			//Next digits screen
			if(digit > lastSegment){ 					//If there are more then max digits needed, it will stop.
				break;
			}
		}
	}

    
    public static boolean writePageToMatrix(String regel1, String regel2, String regel3)
    {
        clearBottom();
        if(regel1.length() > 20 && regel2.length() > 20 && regel3.length() > 11) //check if the length is not to long
        {
            return false;
        }
        
        String nav = "<   >  S";							//creates the navigation and will center it out to the right
        
        for(int i=0; i < (12-regel3.length()); i++){
        	nav = " " + nav;
        }
        char[] regel1CharArray = regel1.toCharArray();
        char[] regel2CharArray = regel2.toCharArray();
        char[] regel3CharArray  = (regel3 + nav).toCharArray();

        for(char ch : regel1CharArray)
        {
            IO.writeShort(0x40, ch);
        }
        
        IO.writeShort(0x40, '\n');
        
        for(char ch : regel2CharArray)
        {
            IO.writeShort(0x40, ch);
        }
        
        IO.writeShort(0x40, '\n');
        
        for(char ch : regel3CharArray)
        {
            IO.writeShort(0x40, ch);
        }
        
        return true;
    }
    
    public static void writeGraphToMatrix(ArrayList<Measurement> msList, int axisx, int axisy)
    {
        clearBottom();
        createAxis(axisx,axisy);
   
        int x,y; 
        for(x = 0; x < 128; x++ ) 
        { 
            y = (int) (Math.sin(x)*2f) + 10;
            y = 31-y;
            IO.writeShort(0x42, 1 << 12 | x << 5 | y); 
            IO.delay(10); 
        } 
    }
    
    //Private functions
    private static void createAxis(int x, int y)
    {
        y = 31-y;
        for(int x2 = 0; x2 < 128; x2++)
        {
            IO.writeShort(0x42, 1 << 12 | x2 << 5 | y );
        }
        
        for(int y2 = 0; y2 < 32; y2++)
        {
            IO.writeShort(0x42, 1 << 12 | x << 5 | y2 );
        }
    }
    
    private static void clearTop()
    {
         IO.writeShort(0x10, 0x100 | 0x0);
         IO.writeShort(0x12, 0x100 | 0x0);
         IO.writeShort(0x14, 0x100 | 0x0);
         IO.writeShort(0x16, 0x100 | 0x0);
         IO.writeShort(0x18, 0x100 | 0x0);
    }
    
    private static void clearLeft()
    {
        IO.writeShort(0x24, 0x100 | 0x0);
        IO.writeShort(0x22, 0x100 | 0x0);
        IO.writeShort(0x20, 0x100 | 0x0);
    }
    
    private static void clearRight()
    {
        IO.writeShort(0x34, 0x100 | 0x0);
        IO.writeShort(0x32, 0x100 | 0x0);
        IO.writeShort(0x30, 0x100 | 0x0);
    }
    
    private static void clearBottom()
    {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
        IO.writeShort(0x42, 3 << 12);
    }
}
