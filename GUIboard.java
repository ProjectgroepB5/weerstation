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
}
