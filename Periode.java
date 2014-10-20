package weerstation;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Periode
{
    private GregorianCalendar beginPeriode;
    private GregorianCalendar eindePeriode;
    
    public Periode()
    {
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
    }
    
    public Periode(int jaar, int maand, int dag, int eindjaar, int eindmaand, int einddag)
    {
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        setBeginPeriode(jaar, maand, dag);
        setEindePeriode(eindjaar, eindmaand, einddag);
    }
    
    public Periode(Calendar cal1, Calendar cal2){
    	beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        setBeginPeriode(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
        setEindePeriode(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
    }
    
    public GregorianCalendar getBeginPeriode()
    {
        return beginPeriode;
    }
    
    public GregorianCalendar getEindePeriode()
    {
        return eindePeriode;
    }
    
    public void setBeginPeriode(int jaar, int maand, int dag)
    {
        beginPeriode.set(jaar, maand, dag);
    }
    
    public void setEindePeriode(int jaar, int maand, int dag)
    {
        eindePeriode.set(jaar, maand, dag);
    }
    
    public String toString()
    {
    	String returnString = beginPeriode.get(Calendar.YEAR) + "-" + (beginPeriode.get(Calendar.MONTH)+1) + "-" + beginPeriode.get(Calendar.DATE);
        returnString += " | ";
        returnString += eindePeriode.get(Calendar.YEAR) + "-" + (eindePeriode.get(Calendar.MONTH)+1) + "-" + eindePeriode.get(Calendar.DATE);
        return returnString;
    }
}











