  

public class Calculator {
    
    // Luchtdruk in hPa
    // Malek&Tom
    public static double luchtdruk(short mval)
    {
        double luchtdruk = (mval / 1000f) * 33.86389;
        return luchtdruk;
    }
    
    // Temperatuur in graden Celcius
    // Malek&Tom
    public static double temperatuur(short mval)
    {
        double temperatuur = (((double)mval / 10) -32) / 1.8;
        return temperatuur;
    }
    
    // Relatieve luchtvochtigheid in %
    // Malek&Tom
    public static double luchtVochtigheid(short mval)
    {
        double luchtvochtigheid = mval;
        return luchtvochtigheid;
    }
    
    // Windsnelheid in m/s
    // Janco&Tim
    public static double windSnelheid(short mval)
    {
        double windSpeed = mval * 0.44704;
        return windSpeed;
    }
    
    // Windrichting in noord, oost, zuid en west. 
    // Kenneth&Daniël
    public static String windRichting(short mval)
    {
        String direction = "Error";
        
        if(mval < 12)
        {
            direction = "N";
        }
        else if(mval < 34)
        {
            direction = "NNO";
        }
        else if(mval < 57)
        {
            direction = "NO";
        }
        else if(mval < 79)
        {
            direction = "ONO";
        }
        else if(mval < 102)
        {
            direction = "O";
        }
        else if(mval < 124)
        {
            direction = "OZO";
        }
        else if(mval < 147)
        {
            direction = "ZO";
        }
        else if(mval < 169)
        {
            direction = "ZZO";
        }
        else if(mval < 192)
        {
            direction = "Z";
        }
        else if(mval < 214)
        {
            direction = "ZZW";
        }
        else if(mval < 237)
        {
            direction = "ZW";
        }
        else if(mval < 259)
        {
            direction = "WZW";
        }
        else if(mval < 282)
        {
            direction = "W";
        }
        else if(mval < 304)
        {
            direction = "WNW";
        }
        else if(mval < 327)
        {
            direction = "NW";
        }
        else if(mval < 349)
        {
            direction = "NNW";
        }
        else if(mval < 360)
        {
            direction = "N";
        }
        
        return direction;
    }
    
    // Regenmeter in mm
    // Kenneth&Daniël
    public static double regenmeter(short mval)
    {
        double rainAmount = (double)mval*0.2;
        return rainAmount;
    }
    
    // uvIndex in index    
    // Kenneth&Daniël
    public static double uvIndex(short mval)
    {
        double index = (double) mval/10;
        return index;
    }
    
    // BatterySpanning in Volt
    // Janco&Tim
    public static double batterySpanning(short mval){
        double voltage = (((double)mval * 300)/512)/100;
        return voltage; 
    }
    
    // sunRise en Sunset in tijdformaat hh:mm
    // Janco&Tim
    public static String sunRise(short mval){
        return sun(mval);
    }
    public static String sunSet(short mval){
        return sun(mval);
    }
    private static String sun(short sunRaw){
        String tijd = "";
        for(int i = 0; i <= 3; i++){
            tijd = sunRaw % 10 + tijd;
            sunRaw /= 10;
            if(i == 1){             
                tijd = ":" + tijd;
            }
        }
        return tijd;
    }
    
    public static double solarRad(short mval){
        double zonSterkte = mval;
        return zonSterkte;
    }

    //windchill in graden Celcius
    //Janco en Keneth
    public static double windChill(short grdnFh, short mph)
    {
        double gradenFahrenheit = grdnFh;
        double mijlPerUur = mph;
        double windChill2 = (35.74 + (0.6215*gradenFahrenheit) - 35.75*Math.pow(mijlPerUur, 0.16) + 0.4275*gradenFahrenheit*Math.pow(mijlPerUur, 0.16));
        short windChill = (short) windChill2;
        return temperatuur(windChill);
    }
    
    //Heatindex in celcius
    //Tom met Malek
    public static double heatIndex(short RH, short T2)
    {        
            double T = T2/10;
            double HI = -42.379 + 2.04901523*T + 10.14333127*RH - .22475541*T*RH - .00683783*T*T - .05481717*RH*RH + .00122874*T*T*RH + .00085282*T*RH*RH - .00000199*T*T*RH*RH;
                
            if (RH < 13 && T < 112 && T > 80)
            {
                HI =- ((13-RH)/4)*Math.sqrt((17-Math.abs(T-95.))/17);
            }
            
            if (RH > 85 && T < 87 && T > 80)
            {
                HI =+ ((RH-85)/10) * ((87-T)/5);
            }
            
            if (T > 80)
            {
                HI = 0.5 * (T + 61.0 + ((T-68.0)*1.2) + (RH*0.094));
            }
            
            double heatindex = Calculator.temperatuur( (short) (HI*10) );   
            return heatindex;     
    }
    
    //Dauwpunt in Celcius
    //Daniel en Tim
    public static double dewPoint(double omgevingsTemp, short luchtVochtigheid)
    {
       double hm = luchtVochtigheid/100f;
       double dauwpunt = Math.pow( hm, (1/8f)) * (112 + 0.9*omgevingsTemp) + (0.1*omgevingsTemp) - 112;
       return dauwpunt;
    }
    
    public static double cloudHeight(double temp, short luchtVochtigheid ){
        
        double wolkhoogte = 125 * (temp-dewPoint(temp, luchtVochtigheid));
        return wolkhoogte; 
    }    

}
