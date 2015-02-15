package graphics;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

public class card {
    private String sideA;
    private String sideB;
    
    private static ArrayList<card> cardDeck = new ArrayList<card>();

    
    //default constructors
    card()
    {
//        sideA = null;
//        sideB = null;
    }
    card(String _sideA,String _sideB)
    {
        sideA = _sideA;
        sideB = _sideB;
    }
    //contructors
    public static card addCard(String _sideA,String _sideB)
    {
        card temp = new card(_sideA,_sideB);
        cardDeck.add(temp);
        return(temp);

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
 
}
