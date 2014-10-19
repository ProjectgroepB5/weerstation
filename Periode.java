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
        beginPeriode = new GregorianCalendar(jaar, maand, dag);
        eindePeriode = new GregorianCalendar(eindjaar, eindmaand, einddag);
        
        /*
        if(eindjaar > jaar && eindmaand > maand && einddag > dag)
        {
            beginPeriode.set(eindjaar, eindmaand, einddag);
            eindePeriode.set(jaar, maand, dag);
        }
        else
        {
            beginPeriode.set(jaar, maand, dag);
            eindePeriode.set(eindjaar, eindmaand, einddag);
        }
        */
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
        Calendar cal = getBeginPeriode().getInstance();
        Calendar cal2 = getEindePeriode().getInstance();
        String returnString = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
        returnString += " | ";
        returnString += cal2.get(Calendar.YEAR) + "-" + (cal2.get(Calendar.MONTH)+1) + "-" + cal2.get(Calendar.DATE);
        return returnString;
    }
}











