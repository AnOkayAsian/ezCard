/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphics;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;



public class graphics extends JFrame implements Runnable {
    static final int XBORDER = 0;
    static final int YBORDER = 0;
    static final int YTITLE = 0;
    static final int WINDOW_WIDTH = 800;
    static final int WINDOW_HEIGHT = 600;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    
    Image image;
    Image backgroundIMG;
    Image cardCreateIMG;
    Image playIMG;

    //various screens
    boolean menu;
    boolean cardCreate;
    boolean play;

    
    //SIDE A is very important. This changes what side will show up first
    boolean sideA=true;

   
    int maxCards=15;
    card cardDeck[] = new card[maxCards];


    boolean commitDef=false;

    boolean maxCharA=false;
    boolean maxCharB=false;

    //changes based on actions
    int currentCard;


    int numCorrect=0;
    int numIncorrect=0;  
    int numTimesRepeated=0;

    Graphics2D g;

    public static void main(String[] args) {
        graphics frame = new graphics();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public graphics() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {           
                if (e.BUTTON1 == e.getButton()) {
                    
                    int xpos = e.getX();
                    int ypos = e.getY();
                    if(menu)
                    {
                        if(xpos>72 && xpos<730 && ypos<420 && ypos>330)
                        {
                                cardCreate=true;
                                menu=false;
                        }
                    } 
                    if(play)
                    {
                        //if the card was flipped and one of the buttons was pressed
                        if(!sideA)
                        {
                            if( xpos>25 && xpos<145
                             && ypos>343 &&ypos<470)
                            {
                                cardDeck[currentCard].setCorr(true);
                                numCorrect++;
                                sideA=true;
                                currentCard++;
                                //flipCard=true;

                            }
                            else if(xpos>650 && xpos<776 
                            && ypos>343 &&ypos<470)
                            {
                                cardDeck[currentCard].setCorr(false);
                                numIncorrect++;
                                sideA=true;
                                currentCard++;

                                //flipCard=true;


                            }
                        }
                    }
                }
                if (e.BUTTON3 == e.getButton()) {

                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {

        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                
                
                if(cardCreate)
                {                                
                    if(sideA && !maxCharA)  
                        cardDeck[currentCard].setSideA(Character.toString(e.getKeyChar()));

                    else if(!sideA && !maxCharB)
                        cardDeck[currentCard].setSideB(Character.toString(e.getKeyChar()));                          
                    //////////CHANGING SIDES/////////////////
                    if (e.VK_ENTER == e.getKeyCode() && sideA==false) 
                        sideA=true;                
                    else if (e.VK_ENTER == e.getKeyCode() && sideA==true)           
                        sideA=false;

                    ///////////CHECK IF MAX CHARACTER///////////////
                    if(cardDeck[currentCard].getSideA()!=null && cardDeck[currentCard].getSideA().length() == 10)
                        maxCharA=true;

                    if(cardDeck[currentCard].getSideB()!=null && cardDeck[currentCard].getSideB().length() == 10 )
                        maxCharB=true; 
                    
                    ///////////COMMIT KEY//////////
                    if (e.VK_ALT == e.getKeyCode())
                    {
                        play=true;
                        cardCreate=false;
                        
                        
                        //changes the max amount of cards to the amount of cards written
                        maxCards=currentCard;
                        //
                        currentCard=0;
                        sideA=true;
                    }
                    //////////////////////////////////
                    if (e.VK_RIGHT == e.getKeyCode())
                    {
                        currentCard++;
                        sideA=true;
                        maxCharA=false;
                    }
                    else if (e.VK_LEFT == e.getKeyCode() && currentCard !=0)
                    {
                        currentCard--;
                        sideA=true;
                        maxCharB=false;
                        
                    }
                    
                }
                
                if(play)
                {
                   
                    //keybinding for flipping card
                  if((e.VK_SPACE == e.getKeyCode() || e.VK_ENTER == e.getKeyCode()) && sideA)                    
                      sideA=false;
                  else if((e.VK_SPACE == e.getKeyCode() || e.VK_ENTER == e.getKeyCode()) && !sideA)   
                      sideA=true;
                  
                  
                }
           
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
                    g.drawImage(backgroundIMG, getX(0), getY(0),getWidth2(),getHeight2(), this);

                   g.setColor(Color.BLACK);
                   
                                              //g.drawString("" + c,200, 200);     

                   if(menu)
                   {
                       g.drawImage(backgroundIMG, getX(0), getY(0),getWidth2(),getHeight2(), this);
                   }
/////////////////////////////////////                   
                   else if(cardCreate)
                   {
                                              
                       g.setColor(Color.black);
                       g.setFont(new Font("Roboto",Font.PLAIN,40));      
                       g.drawImage(cardCreateIMG, getX(0), getY(0),getWidth2(),getHeight2(), this);
                       
                       if(sideA)                         
                           g.drawRect(45, 245, 317, 240);
                       else 
                           g.drawRect(440, 245, 317, 240);
                        
                       if(cardDeck[currentCard].getSideA() != null)                                                                      
                           g.drawString("" + cardDeck[currentCard].getSideA(),52, 6*getHeight2()/10 );
                       
                       if(cardDeck[currentCard].getSideB()!=null)
                           g.drawString("" + cardDeck[currentCard].getSideB(),450, 6*getHeight2()/10 );
                                                       
                       g.drawString(""+currentCard, 100, 100);

                   }
/////////////////////////////////////                   
                   else if(play)
                   {
                    g.drawImage(playIMG, getX(0), getY(0),getWidth2(),getHeight2(), this);
                    
                    g.setFont(new Font("Roboto",Font.PLAIN,30));    
                    g.drawString(""+numIncorrect ,307, 135 );
                    g.drawString(""+numCorrect ,307, 190 );  
                    
                    g.drawString(""+numTimesRepeated,721,155);

                    
                    if(sideA)
                        g.drawString("side A",331, 250 );
                    else if(!sideA) 
                        g.drawString("side B",331, 250 );

                    
                    
                    g.setFont(new Font("Roboto",Font.PLAIN,50));   
                    //Checks to see if card is correct. If is incorrect ->Display; else->Next card
                    if(cardDeck[currentCard].getCorr() ==false)
                    {
                        if(sideA)                        
                            g.drawString("" + cardDeck[currentCard].getSideA(),196, 400 );                        
                        else if (!sideA)
                            g.drawString("" + cardDeck[currentCard].getSideB(),196, 400 );
                    }
                    else 
                        currentCard++;
//                    
//                                        for (int zi =0;zi<maxCards;zi++)
//                                        {
//                                            if(cardDeck[currentCard].getSideA() == null || cardDeck[currentCard].getSideB() == null && cardDeck[zi].getCorr()==false )
//                                            {
//                                                currentCard=0;
//                                            }
//                                        }
//                                        
                   
                   
//                    for(boolean b : cardDeck[zi].getCorr()) 
                                        
//                                            if(!b) 
//                                                return false;
                                        if(cardDeck[currentCard].getSideA() == null && cardDeck[currentCard].getSideA() == null)
                                        {
                                                for(int zi=0;zi<currentCard;zi++)  
                                                {
                                                    if(Arrays.asList(cardDeck[zi].getCorr()).contains(false))
                                                    {
                                                        
                                                        currentCard=0;
                                                        numIncorrect=500;
                                                        numTimesRepeated++;
                                                    }
                                                    else if (Arrays.asList(cardDeck[zi].getCorr()).contains(true))
                                                        g.drawString("ALL TERMS COMPLETE",20, 300 );
                                                }

                                        }

                   }
                    

                       

        gOld.drawImage(image, 0, 0, null);
    }

    
////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.01;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset()
    {
    cardCreate=false; 
    menu=true;
    play=false;
    currentCard = 0;

    for(int zi=0;zi<maxCards;zi++)    
    {
        cardDeck[zi] = new card();
    }

    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            backgroundIMG = Toolkit.getDefaultToolkit().getImage("./title.PNG");
            cardCreateIMG = Toolkit.getDefaultToolkit().getImage("./cardCreate.PNG");
            playIMG = Toolkit.getDefaultToolkit().getImage("./study.PNG");
            reset();

        }

             
    }
////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE);
    }

    public int getWidth2() {
        return (xsize - getX(0) - XBORDER);
    }

    public int getHeight2() {
        return (ysize - getY(0) - YBORDER);
    }
////////////////////////////////////////////////////////////////////////////
//    public void saveCurrentState()
//    {
//        try {
//            byte[] data;
//            File file = new File("info.txt");
//            FileOutputStream fileStream = new FileOutputStream(file);
//
////            String str = "numscore " + score + "\n";
////            data = str.getBytes();
////            fileStream.write(data,0,data.length);
//            fileStream.close();
//        }
//        catch (IOException ioe) {
//        }
//
//    }
//        public void readFile() {
//        try {
//            String inputfile = "info.txt";
//            BufferedReader in = new BufferedReader(new FileReader(inputfile));
//            String line = in.readLine();
//            int loop=0;
//            int endA=0;
//            String tempo;
//            while (line != null) {
//             
//                String newLine = line.toLowerCase();
//                card temp = new card(newLine);
//                cardDeck.add(temp);
//                loop++;
//                line = in.readLine();
//            }
//            in.close();
//        } catch (IOException ioe) {
//        }
//    }
}
