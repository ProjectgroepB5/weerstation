 

import java.util.ArrayList;

public class GUIboard {
    
    public static void init()
    {
        IO.init();
    }

    public static void writeUpperDigits(double number){
        clearTop();
        
        int checkNumber = String.valueOf(Math.round(number)).length();
        
        if(checkNumber <= 5){
            checkNumber = 5 - checkNumber > 0 ? 5 - checkNumber : 0;
            number = number * Math.pow(10, checkNumber);
            number = Math.round(number);
            number = number / Math.pow(10, checkNumber);
        }
        else
        {
            number = 0;
        }
        
        writeDigits(number, 0x10, 0x18);
    }
    
    public static void writeLeftDigits(double number){
        clearLeft();
        
        int checkNumber = String.valueOf(Math.round(number)).length();
        
        if(checkNumber <= 3){
            checkNumber = 3 - checkNumber > 0 ? 3 - checkNumber : 0;
            number = number * Math.pow(10, checkNumber);
            number = Math.round(number);
            number = number / Math.pow(10, checkNumber);
        }
        else
        {
            number = 0;
        }
        
        writeDigits(number, 0x20, 0x24);
    }
    
    public static void writeRightDigits(double number){
        clearRight();
        
        int checkNumber = String.valueOf(Math.round(number)).length();
        
        if(checkNumber <= 3){
            checkNumber = 3 - checkNumber > 0 ? 3 - checkNumber : 0;
            number = number * Math.pow(10, checkNumber);
            number = Math.round(number);
            number = number / Math.pow(10, checkNumber);
        }
        else
        {
            number = 0;
        }
        
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
        segA | segB | segC | segD | segE | segF,        //0
        segB | segC,                                    //1
        segA | segB | segG | segE | segD,               //2
        segA | segB | segC | segG | segD,               //3
        segB | segC | segF | segG | segC,               //4
        segA | segF | segG | segC | segD,               //5
        segA | segF | segG | segC | segD | segE,        //6
        segA | segB | segC,                             //7
        segA | segB | segC | segD | segE | segF | segG, //8
        segA | segF | segG | segC | segD | segB,        //9
        };
        
        int digit = firstSegment;
        String numberString = String.valueOf(number);; 
        char numberSplit[] = numberString.toCharArray();
        
        for(int i = numberSplit.length-1; i >= 0; i--){ 
            if(numberSplit[i] == '.'){
                IO.writeShort(digit, digits[(Character.getNumericValue(numberSplit[i - 1]))]|0x180);    //display a . with the next number
                i--;
            }else if(numberSplit[i] == '-'){
                IO.writeShort(digit, 0x140);            //will display a -
            }else{
                IO.writeShort(digit, Character.getNumericValue(numberSplit[i]));
            }
            digit += 2;                                 //Next digits screen
            if(digit > lastSegment){                    //If there are more then max digits needed, it will stop.
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
        
        String nav = "<   >  S";                            //creates the navigation and will center it out to the right
        
        for(int i=0; i < (12-regel3.length()); i++){
            nav = " " + nav;
        }
        char[] regel1CharArray = regel1.toCharArray();
        char[] regel2CharArray = regel2.toCharArray();
        char[] regel3CharArray  = (regel3 + nav).toCharArray();
        
        double centerMargins = 20-regel1CharArray.length;
        int centerLeft = (int) Math.floor(centerMargins/2.0);
        
        double centerMargins2 = 20-regel2CharArray.length;
        int centerLeft2 = (int) Math.floor(centerMargins2/2.0);
        
        
        for(int i=0; i<centerLeft; i++)
        {
            IO.writeShort(0x40, ' ');
        }
        for(char ch : regel1CharArray)
        {
            IO.writeShort(0x40, ch);
        }
        
        IO.writeShort(0x40, '\n');
        
        for(int i=0; i<centerLeft2; i++)
        {
            IO.writeShort(0x40, ' ');
        }
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
    
    public static void writeGraphToMatrix(ArrayList<Double> msList, double min, double max)
    {
        clearBottom();
        
        msList = normalizeData(msList, (max-min)/32);
        max = StatisticsCalculator.max(msList)+1;
        min = StatisticsCalculator.min(msList)-1 > 0 ? StatisticsCalculator.min(msList)-1 : StatisticsCalculator.min(msList);
        
        createAxis(min,max);
        
        int x,y;
        double getal;
        for(double i=0;i<msList.size();i++)
        {
            getal = msList.get((int)i);
            x = (int)(i/(msList.size()-1)*127.0);
            double temp = ((getal - min)/(max-min));
            y = (int)(temp*31.0);
            y = 31 - y;
            IO.writeShort(0x42, 1 << 12 | x << 5 | y );
        }
    }
    
    //Private functions
    public static void createAxis(double min, double max)
    {        
        int x,y;
        double diff = max-min;
        double valpix = diff/32;
            
        if(min<=0){
            if(min==0)
            {
                y = 31;
            }
            else
            {
                y = (int) ( (diff-max) / valpix);
                y = 31 - y;
            }
            for(int x2 = 0; x2 < 128; x2++)
            {
                IO.writeShort(0x42, 1 << 12 | x2 << 5 | y );
            }
        }
    }
    
    private static ArrayList<Double> normalizeData(ArrayList<Double> data2 , double margin){
        
        ArrayList<Double> data = new ArrayList<Double>();
        
        int deler = data2.size() / 256; //Delen door het aantal punten dat je over wil houden.
        double avg=0;
        
        for(int q=0;q<data2.size(); q++)
        {
            if(q%deler==0)
            {
                avg /= deler;
                data.add(avg);
                avg=0;
            }
            avg += data2.get(q);
        }
        
        
        double prevPrevVal = 0.0, prevVal = 0.0, nextVal = 0.0, nextNextVal = 0.0, avrVal = 0.0, setVal = 0.0;
        
        for(int i = 0; i < data.size(); i ++){
            if(i + 1 < data.size()){
                nextVal = data.get(i+1);
            }
            if(i + 2 < data.size()){
                nextNextVal = data.get(i+2);
            }
            if(prevPrevVal != 0 && prevVal != 0 && nextVal != 0 && nextNextVal != 0){
                avrVal = (prevPrevVal + prevVal + nextVal + nextNextVal) / 4;
                if((data.get(i) - avrVal) >= margin){
                    setVal = avrVal;
                }else{
                    setVal = data.get(i);
                }
            }else{
                setVal = data.get(i);
            }
            
            data.set(i, setVal);
            prevVal = data.get(i);
            
            if(i - 1 >= 0){
                prevPrevVal = data.get(i-1);
            }
        }
        
        return data;
    }
    
    
    public  static void clearTop()
    {
         IO.writeShort(0x10, 0x100 | 0x0);
         IO.writeShort(0x12, 0x100 | 0x0);
         IO.writeShort(0x14, 0x100 | 0x0);
         IO.writeShort(0x16, 0x100 | 0x0);
         IO.writeShort(0x18, 0x100 | 0x0);
    }
    
    public  static void clearLeft()
    {
        IO.writeShort(0x24, 0x100 | 0x0);
        IO.writeShort(0x22, 0x100 | 0x0);
        IO.writeShort(0x20, 0x100 | 0x0);
    }
    
    public  static void clearRight()
    {
        IO.writeShort(0x34, 0x100 | 0x0);
        IO.writeShort(0x32, 0x100 | 0x0);
        IO.writeShort(0x30, 0x100 | 0x0);
    }
    
    public static void clearBottom()
    {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
        IO.writeShort(0x42, 3 << 12);
    }
}
