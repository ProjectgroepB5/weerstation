import java.util.ArrayList;

public class Voorspellingen extends Grootheid{
    public ArrayList<Double> list;
    //constructor
    public Voorspellingen(Measurement measurement1){
        updateRecent(measurement1);
    }

    
    public void updateRecent(Measurement measurement1){
        setCurrent(measurement1.getBarometer());
    }
    
    public void display()
    {          
        if (getCurrent() >= 1033 && getCurrent() <= 1084)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "mooi weer", "");
            GUIboard.writeUpperDigits(10);
        }
        else
        if (getCurrent() >= 1030 && getCurrent() <= 1033)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "mooi weer", "");
            GUIboard.writeUpperDigits(10);
        }
        else
        if (getCurrent() >= 1020 && getCurrent() <= 1030)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "goed weer", "");
            GUIboard.writeUpperDigits(20);
        }
        else
        if (getCurrent() >= 1015 && getCurrent() <= 1020)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "wisselvallig weer", "");
            GUIboard.writeUpperDigits(30);
        }
        else
        if (getCurrent() >= 1010 && getCurrent() <= 1015)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "wisselvallig weer", "");
            GUIboard.writeUpperDigits(40);
        }
        else
        if (getCurrent() >= 1007 && getCurrent() <= 1010)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "wisselvallig weer", "");
            GUIboard.writeUpperDigits(50);
        }
        else
        if (getCurrent() >= 1003 && getCurrent() <= 1007)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "regen of wind", "");
            GUIboard.writeUpperDigits(60);
        }
        else
        if (getCurrent() >= 1000 && getCurrent() <= 1003)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "regen of wind", "");
            GUIboard.writeUpperDigits(70);
        }
        else
        if (getCurrent() >= 990 && getCurrent() <= 1000)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "regen of wind", "");
            GUIboard.writeUpperDigits(80);
        }
        else
        if (getCurrent() >= 980 && getCurrent() <= 990)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "veel regen", "");
            GUIboard.writeUpperDigits(90);
        }
        else
        if (getCurrent() >= 970 && getCurrent() <= 980)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "storm", "");
            GUIboard.writeUpperDigits(90);
        }
        else
        if (getCurrent() >= 870 && getCurrent() <= 970)
        {
            GUIboard.writePageToMatrix("Weersvoorspelling:", "zware storm", "");
            GUIboard.writeUpperDigits(90);
        }
    }
}
