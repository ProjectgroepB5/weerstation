 
 
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * Klasse die een makkelijke manier biedt om twee calender datums op te slaan die samen een periode vormen.
 * 
 * @author Daniël Compagner
 * @author Tim van Lieshout
 * @author Malek Sediqi
 * 
 * @version 2.0
 */
public class Periode
{
    private GregorianCalendar beginPeriode;
    private GregorianCalendar eindePeriode;
    private String name;
    
    
    /**
     * Lege constructor. Alleen een naam voor de periode wordt gegeven.
     * 
     * @param name Naam van de periode
     */
    public Periode(String name)
    {
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        this.name = name;
    }
    
    /**
     * Volledige constructor. Naast de naam wordt ook al de begin en einddatum van de periode gegeven.
     * De datums worden meegegeven door los het jaar, de maand en de datum mee te geven.
     * 
     * @param jaar Jaar van begindatum
     * @param maand Maand van begindatum
     * @param dag Dag van begindatum
     * @param eindjaar Jaar van einddatum
     * @param eindmaand Maand van einddatum
     * @param  einddag Dag van einddatum
     * @param name Naam van de periode
     */
    public Periode(int jaar, int maand, int dag, int eindjaar, int eindmaand, int einddag, String name)
    {
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        setBeginPeriode(jaar, maand-1, dag);
        setEindePeriode(eindjaar, eindmaand-1, einddag);
        this.name = name;
    }
    
    /**
     * Volledige constructor. Naast de naam wordt ook al de begin en einddatum van de periode gegeven.
     * De datums worden meegegeven door een calender element mee te geven.
     * 
     * @param cal1 Calender met begindatum
     * @param cal2 Calender met einddatum
     * @param name Naam van de periode
     */
    public Periode(Calendar cal1, Calendar cal2, String name){
        beginPeriode = new GregorianCalendar();
        eindePeriode = new GregorianCalendar();
        setBeginPeriode(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE));
        setEindePeriode(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));
        this.name = name;
    }
    
    /**
     * Geeft een calender terug die de datum van het begin van de periode bevat
     * 
     * @return Calender met datum beginperiode
     */
    public GregorianCalendar getBeginPeriode()
    {
        return beginPeriode;
    }
    
    /**
     * Geeft een calender terug die de datum van het einde van de periode bevat
     * 
     * @return Calender met datum eindeperiode
     */
    public GregorianCalendar getEindePeriode()
    {
        return eindePeriode;
    }
    
    /**
     * Stel los de begindatum in
     * 
     * @param jaar Jaar van datum
     * @param maand Maand van datum
     * @param dag Dag van datum
     */
    public void setBeginPeriode(int jaar, int maand, int dag)
    {
        beginPeriode.set(jaar, maand, dag);
    }
    
    /**
     * Stel los de einddatum in
     * 
     * @param jaar Jaar van datum
     * @param maand Maand van datum
     * @param dag Dag van datum
     */
    public void setEindePeriode(int jaar, int maand, int dag)
    {
        eindePeriode.set(jaar, maand, dag);
    }
    
    /**
     * Stel los de naam in
     * 
     * @param name Naam van de periode
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Geeft de naam van de periode terug
     * 
     * @return String met naam van de periode
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Zet de data die in de periode is opgeslagen om naar een string.
     * 
     * @return String met de datums die in de periode zijn opgeslagen
     */
    public String toString()
    {
        String returnString = beginPeriode.get(Calendar.DATE) + "-" + (beginPeriode.get(Calendar.MONTH)+1) + "-" + beginPeriode.get(Calendar.YEAR);
        returnString += "|";
        returnString += eindePeriode.get(Calendar.DATE) + "-" + (eindePeriode.get(Calendar.MONTH)+1) + "-" + eindePeriode.get(Calendar.YEAR);
        return returnString;
    }
}











