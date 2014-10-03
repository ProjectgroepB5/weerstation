package weerstation;

public class GUIboard {
	

	public static void writeUpperDigits(double number){
		writeDigits(number, 0x10, 0x18);
	}
	
	public static void writeLeftDigits(double number){
		writeDigits(number, 0x20, 0x24);
	}
	
	public static void writeRightDigits(double number){
		writeDigits(number, 0x30, 0x34);
	}
	
	private static void writeDigits(double number, int firstSegment, int lastSegment){
		
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
	    segA | segB | segC | segF | segD,           //3
	    segB | segC | segF | segG | segC,           //4
	    segA | segF | segG | segC | segD,           //5
	    segA | segF | segG | segC | segD | segE,    //6
	    segA | segB | segC,                         //7
	    segDP - 1,                                  //8
	    segA | segF | segG | segC | segD | segB,    //9
	    };
	    
		int digit = firstSegment;
		IO.writeShort(0x10, 0); 						//default value
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

public void writeLeftDigits(double value)
    {
        clearLeft();
    }
    
    private void writeRightDigits(int number){
      int digit = 0x30;
      IO.writeShort(0x30, 0);       //default value
      
      while(number > 0){ 
           IO.writeShort(digit, (number%10));
           number /= 10;
           digit += 2;          //Next digits screen
           if(digit > 0x30){        //If there are more then 5 digits needed, it will stop.
                break;
           }
      }
    }
    
    public boolean writePageToMatrix(String name, double median, int page)
    {
        clearBottom();
        
        IO.init();
        char[] charArray = name.toCharArray();
        String num = "Avg: " + median;
        char[] numArray  = num.toCharArray();
        String nav = page + "/" + pages + "  <  >  S";
        char[] navArray  = nav.toCharArray();
        
        if(charArray.length > 20)
        {
            return false;
        }

        for(char ch : charArray)
        {
            IO.writeShort(0x40, ch);
        }
        
        IO.writeShort(0x40, '\n');
        
        for(char ch : numArray)
        {
            IO.writeShort(0x40, ch);
        }
        
        IO.writeShort(0x40, '\n');
        
        for(char ch : navArray)
        {
            IO.writeShort(0x40, ch);
        }
        
        return true;
    }
    
    public void writeGraphToMatrix(ArrayList<Measurement> msList, int axisx, int axisy)
    {
        clearBottom();
        
        createAxis(axisx,axisy);
        
        IO.init();
        
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
    private void createAxis(int x, int y)
    {
        IO.init();
        
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
    
    private void clearTop()
    {
        IO.init();
        
         IO.writeShort(0x10, 0x100 | 0x0);
         IO.writeShort(0x12, 0x100 | 0x0);
         IO.writeShort(0x14, 0x100 | 0x0);
         IO.writeShort(0x16, 0x100 | 0x0);
         IO.writeShort(0x18, 0x100 | 0x0);
    }
    
    private void clearLeft()
    {
        IO.init();
        
        IO.writeShort(0x24, 0x100 | 0x0);
        IO.writeShort(0x22, 0x100 | 0x0);
        IO.writeShort(0x20, 0x100 | 0x0);
    }
    
    private void clearRight()
    {
        IO.init();
        
        IO.writeShort(0x34, 0x100 | 0x0);
        IO.writeShort(0x32, 0x100 | 0x0);
        IO.writeShort(0x30, 0x100 | 0x0);
    }
    
    private void clearBottom()
    {
        IO.init();
        
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
        IO.writeShort(0x42, 3 << 12);
    }
}


}
