 

import java.math.BigDecimal;

public class Measurement
{

    private String stationId;
    private java.sql.Timestamp dateStamp;
    private short barometer;
    private short insideTemp;
    private short insideHum;
    private short outsideTemp;
    private short windSpeed;
    private short avgWindSpeed;
    private short windDir;
    private short outsideHum;
    private short rainRate;
    private short UVLevel;
    private short solarRad;
    private short xmitBatt;
    private short battLevel;
    private short sunrise;
    private short sunset;
    Calculator calc;
    
    public Measurement()
    {
        calc = new Calculator();
    }
    
    // stationId
    public void setStationId (String str) { this.stationId = str;};
    public String getStationId () { return stationId; };

    // dateStamp
    public void setDateStamp (java.sql.Timestamp ts) { this.dateStamp = ts;};
    public java.sql.Timestamp getDateStamp () { return dateStamp; };
    
    // barometer
    public void setRawBarometer (short val) { this.barometer = val;};
    public short getRawBarometer () { return barometer; };  
    public double getBarometer () { return round(calc.luchtdruk(barometer),0); };

    // insideTemp
    public void setRawInsideTemp (short val) { this.insideTemp = val;};
    public short getRawInsideTemp () { return insideTemp; };   
    public double getInsideTemp () { return round(calc.temperatuur(insideTemp),2); };

    // insideHum
    public void setRawInsideHum (short val) { this.insideHum = val;};
    public short getRawInsideHum () { return insideHum; }; 
    public double getInsideHum () { return round(calc.luchtVochtigheid(insideHum),0); };

    // outsideTemp
    public void setRawOutsideTemp (short val) { this.outsideTemp = val;};
    public short getRawOutsideTemp () { return outsideTemp; };
    public double getOutsideTemp () { return round(calc.temperatuur(outsideTemp),2); };

    // windSpeed
    public void setRawWindSpeed (short val) { this.windSpeed = val;};
    public short getRawWindSpeed () { return windSpeed; };
    public double getWindSpeed () { return round(calc.windSnelheid(windSpeed),2); };

    // avgWindSpeed
    public void setRawAvgWindSpeed (short val) { this.avgWindSpeed = val;};
    public short getRawAvgWindSpeed () { return avgWindSpeed; };
    public double getAvgWindSpeed () { return round(calc.windSnelheid(avgWindSpeed),2); };

    // windDir
    public void setRawWindDir (short val) { this.windDir = val;};
    public short getRawWindDir () { return windDir; };
    public String getWindDir () { return calc.windRichting(windDir); };

    // outsideHum
    public void setRawOutsideHum (short val) { this.outsideHum = val;};
    public short getRawOutsideHum () { return outsideHum; };
    public double getOutsideHum () { return round(calc.luchtVochtigheid(outsideHum),0); };

    // rainRate
    public void setRawRainRate (short val) { this.rainRate = val;};
    public short getRawRainRate () { return rainRate; };
    public double getRainRate () { return calc.regenmeter(rainRate); };

    // UVLevel
    public void setRawUVLevel (short val) { this.UVLevel = val;};
    public short getRawUVLevel () { return UVLevel; };
    public double getUVLevel () { return Math.ceil(calc.uvIndex(UVLevel)); };

    // solarRad
    public void setRawSolarRad (short val) { this.solarRad = val;};
    public short getRawSolarRad () { return solarRad; };
    

    // xmitBatt
    public void setRawXmitBatt (short val) { this.xmitBatt = val;};
    public short getRawXmitBatt () { return xmitBatt; };

    // battLevel
    public void setRawBattLevel (short val) { this.battLevel = val;};
    public short getRawBattLevel () { return battLevel; };
    public double getBattLevel () { return round(calc.batterySpanning(battLevel),2); };

    // sunrise
    public void setRawSunrise (short val) { this.sunrise = val;};
    public short getRawSunrise () { return sunrise; };
    public String getSunrise () { return calc.sunRise(sunrise); };

    // sunset
    public void setRawSunset (short val) { this.sunset = val;};
    public short getRawSunset () { return sunset; };
    public String getSunset () { return calc.sunSet(sunset); };
    
    // windChill
    public double getWindChill () { return round(calc.windChill(outsideTemp, windSpeed),2); };
     
    // heatIndex
    public double getHeatIndex () { return round(calc.heatIndex(outsideHum, outsideTemp),0); };
     
    // dewPoint
    public double getDewPoint () { return round(calc.dewPoint( getOutsideTemp() , outsideHum),2); };
    
    //wolkHoogte
    public double getCloudHeight () { return round(calc.cloudHeight(outsideTemp, outsideHum),1); };

    public String toString()
    {
        String s = "stationId = " + stationId
            + ", dateStamp = " + dateStamp
            + ", barometer = " + barometer
            + ", insideTemp = " + insideTemp
            + ", insideHum = " + insideHum
            + ", outsideTemp = " + outsideTemp
            + ", windSpeed = " + windSpeed
            + ", avgWindSpeed = " + avgWindSpeed
            + ", windDir = " + windDir
            + ", outsideHum = " + outsideHum
            + ", rainRate = " + rainRate
            + ", UVLevel = " + UVLevel
            + ", solarRad = " + solarRad
            + ", xmitBatt = " + xmitBatt
            + ", battLevel = " + battLevel
            + ", sunrise = " + sunrise
            + ", sunset = " + sunset;
        return s; 
    } 
    
    //afronder
 public double round(double unrounded, int precision)
 {
     BigDecimal bd = new BigDecimal(unrounded);
     BigDecimal rounded = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
     return rounded.doubleValue();
 }  
    
}