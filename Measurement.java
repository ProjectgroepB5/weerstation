package weerstation;

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
    
    public Measurement()
    {
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
    public double getBarometer () { return round(Calculator.luchtdruk(barometer),0); };

    // insideTemp
    public void setRawInsideTemp (short val) { this.insideTemp = val;};
    public short getRawInsideTemp () { return insideTemp; };   
    public double getInsideTemp () { return round(Calculator.temperatuur(insideTemp),2); };

    // insideHum
    public void setRawInsideHum (short val) { this.insideHum = val;};
    public short getRawInsideHum () { return insideHum; }; 
    public double getInsideHum () { return round(Calculator.luchtVochtigheid(insideHum),0); };

    // outsideTemp
    public void setRawOutsideTemp (short val) { this.outsideTemp = val;};
    public short getRawOutsideTemp () { return outsideTemp; };
    public double getOutsideTemp () { return round(Calculator.temperatuur(outsideTemp),2); };

    // windSpeed
    public void setRawWindSpeed (short val) { this.windSpeed = val;};
    public short getRawWindSpeed () { return windSpeed; };
    public double getWindSpeed () { return round(Calculator.windSnelheid(windSpeed),2); };

    // avgWindSpeed
    public void setRawAvgWindSpeed (short val) { this.avgWindSpeed = val;};
    public short getRawAvgWindSpeed () { return avgWindSpeed; };
    public double getAvgWindSpeed () { return round(Calculator.windSnelheid(avgWindSpeed),2); };

    // windDir
    public void setRawWindDir (short val) { this.windDir = val;};
    public short getRawWindDir () { return windDir; };
    public String getWindDir () { return Calculator.windRichting(windDir); };

    // outsideHum
    public void setRawOutsideHum (short val) { this.outsideHum = val;};
    public short getRawOutsideHum () { return outsideHum; };
    public double getOutsideHum () { return round(Calculator.luchtVochtigheid(outsideHum),0); };

    // rainRate
    public void setRawRainRate (short val) { this.rainRate = val;};
    public short getRawRainRate () { return rainRate; };
    public double getRainRate () { return Calculator.regenmeter(rainRate); };

    // UVLevel
    public void setRawUVLevel (short val) { this.UVLevel = val;};
    public short getRawUVLevel () { return UVLevel; };
    public double getUVLevel () { return Math.ceil(Calculator.uvIndex(UVLevel)); };

    // solarRad
    public void setRawSolarRad (short val) { this.solarRad = val;};
    public short getRawSolarRad () { return solarRad; };
    

    // xmitBatt
    public void setRawXmitBatt (short val) { this.xmitBatt = val;};
    public short getRawXmitBatt () { return xmitBatt; };

    // battLevel
    public void setRawBattLevel (short val) { this.battLevel = val;};
    public short getRawBattLevel () { return battLevel; };
    public double getBattLevel () { return round(Calculator.batterySpanning(battLevel),2); };

    // sunrise
    public void setRawSunrise (short val) { this.sunrise = val;};
    public short getRawSunrise () { return sunrise; };
    public String getSunrise () { return Calculator.sunRise(sunrise); };

    // sunset
    public void setRawSunset (short val) { this.sunset = val;};
    public short getRawSunset () { return sunset; };
    public String getSunset () { return Calculator.sunSet(sunset); };
    
    // windChill
    public double getWindChill () { return round(Calculator.windChill(outsideTemp, windSpeed),2); };
     
    // heatIndex
    public double getHeatIndex () { return round(Calculator.heatIndex(outsideHum, outsideTemp),0); };
     
    // dewPoint
    public double getDewPoint () { return round(Calculator.dewPoint( getOutsideTemp() , outsideHum),2); };
    
    //wolkHoogte
    public double getCloudHeight () { return round(Calculator.cloudHeight(outsideTemp, outsideHum),1); };

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