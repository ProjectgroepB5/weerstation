 

public class Voorspellingen extends Grootheid{
private double barometer;

    //constructor
    public Voorspellingen(Measurement measurement1){
    	setName("Weersvoorspelling:");
    	updateRecent(measurement1);
    }

    public void updateRecent(Measurement measurement1){
        barometer = measurement1.getBarometer();
        if (barometer >= 1033 && barometer <= 1084)
        {
            setCustom("Mooi weer");
            setCurrent(10);
        }
        else
        if (barometer >= 1030 && barometer <= 1033)
        {
        	setCustom("Mooi weer");
            setCurrent(10);
        }
        else
        if (barometer >= 1020 && barometer <= 1030)
        {
        	setCustom("Goed weer");
            setCurrent(20);
        }
        else
        if (barometer >= 1015 && barometer <= 1020)
        {
        	setCustom("Wisselvallig weer");
            setCurrent(30);
        }
        else
        if (barometer >= 1010 && barometer <= 1015)
        {
        	setCustom("Wisselvallig weer");
            setCurrent(40);
        }
        else
        if (barometer >= 1007 && barometer <= 1010)
        {
        	setCustom("Wisselvallig weer");
            setCurrent(50);
        }
        else
        if (barometer >= 1003 && barometer <= 1007)
        {
        	setCustom("Regen of wind");
            setCurrent(60);
        }
        else
        if (barometer >= 1000 && barometer <= 1003)
        {
        	setCustom("Regen of wind");
            setCurrent(70);
        }
        else
        if (barometer >= 990 && barometer <= 1000)
        {
        	setCustom("Regen of wind");
            setCurrent(80);
        }
        else
        if (barometer >= 980 && barometer <= 990)
        {
        	setCustom("Veel regen");
            setCurrent(90);
        }
        else
        if (barometer >= 970 && barometer <= 980)
        {
        	setCustom("Storm");
            setCurrent(90);
        }
        else
        if (barometer >= 870 && barometer <= 970)
        {
        	setCustom("Zware storm");
            setCurrent(90);
        }
    }
    
    
    public void displayGraph()
    {
    }
}
