import java.util.GregorianCalendar;

public class Periode
{
    public GregorianCalendar beginPeriode;
    public GregorianCalendar eindePeriode;
    
    public Periode()
    {
    }
    
    public Periode(int jaar, int maand, int dag, int eindjaar, int eindmaand, int einddag)
    {
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        beginPeriode.set(jaar, maand, dag,0,0,0);
        eindePeriode.set(eindjaar, eindmaand, einddag,23,59,59);
    }
    
    public GregorianCalendar getBeginPeriode()
    {
        return beginPeriode;
    }
    
    public GregorianCalendar getEindePeriode()
    {
        return eindePeriode;
    }
}