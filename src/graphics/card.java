package graphics;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

public class card {
    private String sideA;
    private String sideB;
    private boolean corr;
    //private String sideAB;
    private boolean active=false;
//    private static ArrayList<card> cardDeck = new ArrayList<card>();

    
    
//    public static card addCards(String _sideA,String _sideB)
//    {
//        card temp = new card(_sideA,_sideB);
//        cardDeck.add(temp);
//        return(temp);
//    }
    //default constructors
    card()
    {
        sideA = null;
        sideB = null;
               // corr=null;

        //sideAB = null;
    }
//    card(String _sideAB)
//    {
//        sideAB = _sideAB;
//        sideA = null;
//        sideB = null;
//    }
    card(String _sideA,String _sideB)
    {
        sideA = _sideA;
        sideB = _sideB;
        //corr=false;
        //sideAB = null;
    }
    //contructors
    
    void createSides(String _sideAB)
    {
        String A;
        String B;
        int endA=(_sideAB.indexOf("~"));
        A=_sideAB.substring(0,endA);
        B=_sideAB.substring(endA+1,_sideAB.length());
        setSideA(A);
        setSideB(B);
    }
    //accessors
    String getSideA()
    {
        return sideA;
    }
    String getSideB()
    {
        return sideB;
    }
    boolean getCorr()
    {
        return corr;
    }
    boolean getActive()
    {
    
        return active;
    }
    void setCorr(Boolean _corr)
    {
        corr=_corr;
    }
    void setSideA(String _sideA)
    {
        if(sideA==null)
            sideA=_sideA;
        else
        sideA += _sideA;
    }    
    void setSideB(String _sideB)
    {
        if(sideB==null)
            sideB=_sideB;
        else
        sideB += _sideB;
    }
    void setSideA1(String _sideA)
    {
       sideA=_sideA;
    }

    
    void setSideB1(String _sideB)
    {
        sideB=_sideB;
    }
 
    void setActive(boolean _active)
    {
        active=_active;
    }
}
