package weerstation1;
 
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Connect met de database
 * 
 * @author Diederich Kroeske
 * @version 2
 */
public class WeerstationConnector
{

    private Connection myConn = null;
    
    
    
    
    public WeerstationConnector()
    {
        this("84.24.41.72","5329","aws_data","aws","aws");
    }

    //Start connection with Databse
    public WeerstationConnector(String host, String port, String dbName, String userName, String password)
    {
        try
        {
            String url = "jdbc:mysql://" + host + ":" + port + "/"+ dbName + "?user="
            + userName
            + "&password="
            + password;
            Class.forName("com.mysql.jdbc.Driver").newInstance ();
            myConn = DriverManager.getConnection(url);
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex.getMessage());
        }
    }
    
    
    
    //Get most recent value of all columns
    public Measurement getMostRecentMeasurement()
    {

        Measurement m = new Measurement();

        try
        {
            // query:
            Statement s = myConn.createStatement();
            s.executeQuery("SELECT stationId, timestamp, " +
                    "barometer, " +
                    "insideTemp, " +
                    "insideHum, " +
                    "outsideTemp, " +
                    "windSpeed, " +
                    "avgWindSpeed, " +
                    "windDir, " +
                    "outsideHum, " +
                    "rainRate, " +
                    "UVLevel, " +
                    "solarRad, " +
                    "xmitBatt, " +
                    "battLevel, " +
                    "sunrise, " +
                    "sunset " +
                    "FROM measurement order by measurementId desc limit 1");

            ResultSet rs = s.getResultSet();
            int count = 0;
            while( rs.next() )
            {
                m.setStationId( rs.getString("stationId") );
                m.setDateStamp( rs.getTimestamp(2));
                m.setRawBarometer( Short.valueOf(rs.getString("barometer")) );
                m.setRawInsideTemp( Short.valueOf(rs.getString("insideTemp")) );
                m.setRawInsideHum( Short.valueOf(rs.getString("insideHum")) );
                m.setRawOutsideTemp( Short.valueOf(rs.getString("outsideTemp")) );
                m.setRawWindSpeed( Short.valueOf(rs.getString("windSpeed")) );
                m.setRawAvgWindSpeed( Short.valueOf(rs.getString("avgWindSpeed")) );
                m.setRawWindDir( Short.valueOf(rs.getString("windDir")) );
                m.setRawOutsideHum( Short.valueOf(rs.getString("outsideHum")) );
                m.setRawRainRate( Short.valueOf(rs.getString("rainRate")) );
                m.setRawUVLevel( Short.valueOf(rs.getString("UVLevel")) );
                m.setRawSolarRad( Short.valueOf(rs.getString("solarRad")) );
                m.setRawXmitBatt( Short.valueOf(rs.getString("xmitBatt")) );
                m.setRawBattLevel( Short.valueOf(rs.getString("battLevel")) );
                m.setRawSunrise( Short.valueOf(rs.getString("sunrise")) );
                m.setRawSunset( Short.valueOf(rs.getString("sunset")) );

                count++;
            }
            rs.close();
            s.close();
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
                System.out.println("getMeasurement: " + ex.getMessage());
        }

        return m;
    }

    
    
    //Get specified values of all columns
    public ArrayList<Measurement> getAllMeasurementsBetween(GregorianCalendar d1, GregorianCalendar d2)
    {

        String sd1 = d1.get(Calendar.YEAR) + "-" + (d1.get(Calendar.MONTH)+1) + "-" + d1.get(Calendar.DATE) + " 0:0:0";
        String sd2 = d2.get(Calendar.YEAR) + "-" + (d2.get(Calendar.MONTH)+1) + "-" + d2.get(Calendar.DATE) + " 23:59:59";

        ArrayList<Measurement> mArr = new ArrayList<Measurement>();

        try
        {
            // query:
            Statement s = myConn.createStatement();
            s.executeQuery("SELECT stationId, timestamp, " +
                    "barometer, " +
                    "insideTemp, " +
                    "insideHum, " +
                    "outsideTemp, " +
                    "windSpeed, " +
                    "avgWindSpeed, " +
                    "windDir, " +
                    "outsideHum, " +
                    "rainRate, " +
                    "UVLevel, " +
                    "solarRad, " +
                    "xmitBatt, " +
                    "battLevel, " +
                    "sunrise, " +
                    "sunset " +
                    "FROM measurement where timestamp between " +
                    "'" + sd1 + "' and '" + sd2 + "'");

            ResultSet rs = s.getResultSet();
            int count = 0;
            
            short prevBaro = 0;
            short prevInsideTemp = 0;
            short prevInsideHum = 0;
            short prevOutsideTemp = 0;
            short prevOutsideHum = 0;
            short prevWindDir = 0;
            short prevWindSpeed = 0;
            short prevAvgWindSpeed = 0;
            short prevRainRate = 0;
            short prevUVLevel = 0;
            short prevSolarRad = 0;
            short prevSunrise = 0;
            short prevSunset = 0;
            
            while( rs.next() )
            {
                Measurement m = new Measurement();

                m.setStationId( rs.getString("stationId") );
                m.setDateStamp( rs.getTimestamp(2));
                m.setRawXmitBatt( Short.valueOf(rs.getString("xmitBatt")) );
                m.setRawBattLevel( Short.valueOf(rs.getString("battLevel")) );
                
                //Check barometer
                if(Short.valueOf(rs.getString("barometer"))==0)
                {
                    m.setRawBarometer( prevBaro );
                }
                else
                {
                    m.setRawBarometer( Short.valueOf(rs.getString("barometer")) );
                }
                prevBaro = m.getRawBarometer();
                
                //Check insideTemp
                if(Short.valueOf(rs.getString("insideTemp"))==Short.MAX_VALUE)
                {
                    m.setRawInsideTemp( prevInsideTemp );
                }
                else
                {
                    m.setRawInsideTemp( Short.valueOf(rs.getString("insideTemp")) );
                }
                prevInsideTemp = m.getRawInsideTemp();
                
                //Check insideHum
                if(Short.valueOf(rs.getString("insideHum"))==255)
                {
                    m.setRawInsideHum( prevInsideHum );
                }
                else
                {
                    m.setRawInsideHum( Short.valueOf(rs.getString("insideHum")) );
                }
                prevInsideHum = m.getRawInsideHum();
                
                //Check outsideTemp
                if(Short.valueOf(rs.getString("outsideTemp"))==Short.MAX_VALUE)
                {
                    m.setRawOutsideTemp( prevOutsideTemp );
                }
                else
                {
                    m.setRawOutsideTemp( Short.valueOf(rs.getString("outsideTemp")) );
                }
                prevOutsideTemp = m.getRawOutsideTemp();
                
                //Check outsideHum
                if(Short.valueOf(rs.getString("outsideHum"))==255)
                {
                    m.setRawOutsideHum( prevOutsideHum );
                }
                else
                {
                    m.setRawOutsideHum( Short.valueOf(rs.getString("outsideHum")) );
                }
                prevOutsideHum = m.getRawOutsideHum();
                
                //Check windDir
                if(Short.valueOf(rs.getString("windDir"))==Short.MAX_VALUE)
                {
                    m.setRawWindDir( prevWindDir );
                }
                else
                {
                    m.setRawWindDir( Short.valueOf(rs.getString("windDir")) );
                }
                prevWindDir = m.getRawWindDir();
                
                //Check windSpeed
                if(Short.valueOf(rs.getString("windSpeed"))==255)
                {
                    m.setRawWindSpeed( prevWindSpeed );
                }
                else
                {
                    m.setRawWindSpeed( Short.valueOf(rs.getString("windSpeed")) );
                }
                prevWindSpeed = m.getRawWindSpeed();
                
                //Check AvgWindSpeed
                if(Short.valueOf(rs.getString("avgWindSpeed"))==255)
                {
                    m.setRawAvgWindSpeed( prevAvgWindSpeed );
                }
                else
                {
                    m.setRawAvgWindSpeed( Short.valueOf(rs.getString("avgWindSpeed")) );
                }
                prevAvgWindSpeed = m.getRawAvgWindSpeed();
                
                //Check RainRate
                if(Short.valueOf(rs.getString("rainRate"))==Short.MAX_VALUE)
                {
                    m.setRawRainRate( prevRainRate );
                }
                else
                {
                    m.setRawRainRate( Short.valueOf(rs.getString("rainRate")) );
                }
                prevRainRate = m.getRawRainRate();
                
                //Check UVLevel
                if(Short.valueOf(rs.getString("UVLevel"))==255)
                {
                    m.setRawUVLevel( prevUVLevel );
                }
                else
                {
                    m.setRawUVLevel( Short.valueOf(rs.getString("UVLevel")) );
                }
                prevUVLevel = m.getRawUVLevel();
                
                //Check solarRad
                if(Short.valueOf(rs.getString("solarRad"))==Short.MAX_VALUE)
                {
                    m.setRawSolarRad( prevSolarRad );
                }
                else
                {
                    m.setRawSolarRad( Short.valueOf(rs.getString("solarRad")) );
                }
                prevSolarRad = m.getRawSolarRad();
                
                //Check sunrise
                if(Short.valueOf(rs.getString("sunrise"))==Short.MAX_VALUE)
                {
                    m.setRawSunrise( prevSunrise );
                }
                else
                {
                    m.setRawSunrise( Short.valueOf(rs.getString("sunrise")) );
                }
                prevSunrise = m.getRawSunrise();
                
                //Check sunset
                if(Short.valueOf(rs.getString("sunset"))==Short.MAX_VALUE)
                {
                    m.setRawSunset( prevSunset );
                }
                else
                {
                    m.setRawSunset( Short.valueOf(rs.getString("sunset")) );
                }
                prevSunset = m.getRawSunset();

                mArr.add(m);

                count++;
            }
            rs.close();
            s.close();
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
                System.out.println("getMeasurement: " + ex.getMessage());
        }

        return mArr;
    }

    
    
    
    
    
    
    
    
    
    
    
    //Get most recent values of one column
    public Timestamp getMostRecentTimeStamp()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getDateStamp();

    }

    public short getMostRecentBarometer()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawBarometer();

    }

    public short getMostRecentInsideTemp()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawInsideTemp();

    }

    public short getMostRecentInsideHum()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawInsideHum();

    }

    public short getMostRecentOutsideTemp()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawOutsideTemp();

    }

    public short getMostRecentWindSpeed()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawWindSpeed();

    }
    
    public short getMostRecentAvgWindSpeed()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawAvgWindSpeed();

    }

    public short getMostRecentWindDir()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawWindDir();

    }

    public short getMostRecentOutsideHum()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawOutsideHum();

    }

    public short getMostRecentRainRate()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawRainRate();

    }

    public short getMostRecentUVLevel()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawUVLevel();

    }

    public short getMostRecentSolarRadiation()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawSolarRad();

    }

    public short getMostRecentXmitBatt()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawXmitBatt();

    }

    public short getMostRecentBattLevel()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawBattLevel();

    }

    public short getMostRecentSunrise()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawSunrise();

    }

    public short getMostRecentSunset()
    {
        Measurement m = getMostRecentMeasurement();
        return m.getRawSunset();

    }

    //Get all values of one column
    public short[] getAllOutsideTemp()
    {
        ArrayList<Measurement> mArr = getAllMeasurements();
        short[] values = new short[mArr.size()];
        int count = 0;
        for(Measurement m: mArr )
        {
            values[count++] = m.getRawOutsideTemp();
        }
        return values;
    }

    public short[] getAllBarometer()
    {
        ArrayList<Measurement> mArr = getAllMeasurements();
        short[] values = new short[mArr.size()];
        int count = 0;
        for(Measurement m: mArr )
        {
            values[count++] = m.getRawBarometer();
        }
        return values;
    }
    
    //Get all values of all columns
    public ArrayList<Measurement> getAllMeasurements()
    {

        ArrayList<Measurement> mArr = new ArrayList<Measurement>();

        try
        {
            // query:
            Statement s = myConn.createStatement();
            s.executeQuery("SELECT stationId, timestamp, " +
                    "barometer, " +
                    "insideTemp, " +
                    "insideHum, " +
                    "outsideTemp, " +
                    "windSpeed, " +
                    "avgWindSpeed, " +
                    "windDir, " +
                    "outsideHum, " +
                    "rainRate, " +
                    "UVLevel, " +
                    "solarRad, " +
                    "xmitBatt, " +
                    "battLevel, " +
                    "sunrise, " +
                    "sunset " +
                    "FROM measurement");

            ResultSet rs = s.getResultSet();
            int count = 0;
            while( rs.next() )
            {
                Measurement m = new Measurement();

                m.setStationId( rs.getString("stationId") );
                m.setDateStamp( rs.getTimestamp(2));
                m.setRawBarometer( Short.valueOf(rs.getString("barometer")) );
                m.setRawInsideTemp( Short.valueOf(rs.getString("insideTemp")) );
                m.setRawInsideHum( Short.valueOf(rs.getString("insideHum")) );
                m.setRawOutsideTemp( Short.valueOf(rs.getString("outsideTemp")) );
                m.setRawWindSpeed( Short.valueOf(rs.getString("windSpeed")) );
                m.setRawAvgWindSpeed( Short.valueOf(rs.getString("avgWindSpeed")) );
                m.setRawWindDir( Short.valueOf(rs.getString("windDir")) );
                m.setRawOutsideHum( Short.valueOf(rs.getString("outsideHum")) );
                m.setRawRainRate( Short.valueOf(rs.getString("rainRate")) );
                m.setRawUVLevel( Short.valueOf(rs.getString("UVLevel")) );
                m.setRawSolarRad( Short.valueOf(rs.getString("solarRad")) );
                m.setRawXmitBatt( Short.valueOf(rs.getString("xmitBatt")) );
                m.setRawBattLevel( Short.valueOf(rs.getString("battLevel")) );
                m.setRawSunrise( Short.valueOf(rs.getString("sunrise")) );
                m.setRawSunset( Short.valueOf(rs.getString("sunset")) );

                mArr.add(m);

                count++;
            }
            rs.close();
            s.close();
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
                System.out.println("getMeasurement: " + ex.getMessage());
        }

        return mArr;
    }
    
    //Get last 86400 values of all columns
    public ArrayList<Measurement> getAllMeasurementsLast24h()
    {
        return getAllMeasurementsLastHours(24);
    }

    //Get specified values of all columns
    public ArrayList<Measurement> getAllMeasurementsLastHours(int hour)
    {

        ArrayList<Measurement> mArr = new ArrayList<Measurement>();

        try
        {
            // query:
            Statement s = myConn.createStatement();
            s.executeQuery("SELECT stationId, timestamp, " +
                    "barometer, " +
                    "insideTemp, " +
                    "insideHum, " +
                    "outsideTemp, " +
                    "windSpeed, " +
                    "avgWindSpeed, " +
                    "windDir, " +
                    "outsideHum, " +
                    "rainRate, " +
                    "UVLevel, " +
                    "solarRad, " +
                    "xmitBatt, " +
                    "battLevel, " +
                    "sunrise, " +
                    "sunset " +
                    "FROM measurement where timestamp between NOW() - INTERVAL " +
                    hour + " HOUR and NOW()");


            ResultSet rs = s.getResultSet();
            int count = 0;
            while( rs.next() )
            {
                Measurement m = new Measurement();

                m.setStationId( rs.getString("stationId") );
                m.setDateStamp( rs.getTimestamp(2));
                m.setRawBarometer( Short.valueOf(rs.getString("barometer")) );
                m.setRawInsideTemp( Short.valueOf(rs.getString("insideTemp")) );
                m.setRawInsideHum( Short.valueOf(rs.getString("insideHum")) );
                m.setRawOutsideTemp( Short.valueOf(rs.getString("outsideTemp")) );
                m.setRawWindSpeed( Short.valueOf(rs.getString("windSpeed")) );
                m.setRawAvgWindSpeed( Short.valueOf(rs.getString("avgWindSpeed")) );
                m.setRawWindDir( Short.valueOf(rs.getString("windDir")) );
                m.setRawOutsideHum( Short.valueOf(rs.getString("outsideHum")) );
                m.setRawRainRate( Short.valueOf(rs.getString("rainRate")) );
                m.setRawUVLevel( Short.valueOf(rs.getString("UVLevel")) );
                m.setRawSolarRad( Short.valueOf(rs.getString("solarRad")) );
                m.setRawXmitBatt( Short.valueOf(rs.getString("xmitBatt")) );
                m.setRawBattLevel( Short.valueOf(rs.getString("battLevel")) );
                m.setRawSunrise( Short.valueOf(rs.getString("sunrise")) );
                m.setRawSunset( Short.valueOf(rs.getString("sunset")) );

                mArr.add(m);

                count++;
            }
            rs.close();
            s.close();
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
                System.out.println("getMeasurement: " + ex.getMessage());
        }

        return mArr;
    }

    //Exectute a custom query
    private ResultSet executeCustomQuery(String query)
    {
        ResultSet returnSet = null;
        
        try{
            Statement s = myConn.createStatement();
            s.executeQuery(query);
            ResultSet rs = s.getResultSet();
            
            returnSet = rs;
            
            rs.close();
            s.close();
        }
        catch( SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
           System.out.println("getMeasurement: " + ex.getMessage());
        }
        
        return returnSet;
    }
    
    //Close connection with Database
    protected void finalize() throws Throwable
    {
        // Close database connection
        if( myConn != null )
        {
            try
            {
                myConn.close();
                System.out.println("Database connection terminated");
            }
            catch( Exception e ) {}
        }

        super.finalize();
    }

}