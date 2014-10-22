 
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Periode
{
    private GregorianCalendar beginPeriode;
    private GregorianCalendar eindePeriode;
    private String name;
    
    public Periode(String name)
    {
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        this.name = name;
    }
    
    public Periode(int jaar, int maand, int dag, int eindjaar, int eindmaand, int einddag, String name)
    {
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        setBeginPeriode(jaar, maand, dag);
        setEindePeriode(eindjaar, eindmaand, einddag);
        this.name = name;
    }
    
    public Periode(Calendar cal1, Calendar cal2, String name){
    	beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        setBeginPeriode(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE));
        setEindePeriode(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));
        this.name = name;
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
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String toString()
    {
    	String returnString = beginPeriode.get(Calendar.DATE) + "-" + (beginPeriode.get(Calendar.MONTH)+1) + "-" + beginPeriode.get(Calendar.YEAR);
        returnString += "|";
        returnString += eindePeriode.get(Calendar.DATE) + "-" + (eindePeriode.get(Calendar.MONTH)+1) + "-" + eindePeriode.get(Calendar.YEAR);
        return returnString;
    }
}











