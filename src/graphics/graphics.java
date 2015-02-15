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


public class graphics extends JFrame implements Runnable {
    static final int XBORDER = 0;
    static final int YBORDER = 0;
    static final int YTITLE = 0;
    static final int WINDOW_WIDTH = 800;
    static final int WINDOW_HEIGHT = 600;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    int scale;
    Image image;
        Image backgroundIMG;
        Image cardCreateIMG;

        boolean menu;
        boolean cardCreate;
        
        boolean sideA=true;
        //boolean sideB;
    
    String s;
    
    String c;
    //card card;
    StringBuilder str;
    
    card card1;
    

    
    boolean maxCharA=false;
    boolean maxCharB=false;
   //card cardDeck = card.addCard("", "");
    
             
     Graphics2D g;

    public static void main(String[] args) {
        graphics frame = new graphics();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public graphics() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {

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
                
             if (e.getKeyCode()  >= e.VK_A && e.getKeyCode()  <= e.VK_Z) 
             {
              if(sideA && !maxCharA)  
                card1.setSideA(Character.toString(e.getKeyChar()));
              
              if(!sideA && !maxCharB)
                card1.setSideB(Character.toString(e.getKeyChar()));
             }    
            if (e.VK_ENTER == e.getKeyCode() && sideA==false) 
                sideA=true;                
            else if (e.VK_ENTER == e.getKeyCode() && sideA==true)           
                sideA=false;
            
            if(card1.getSideA()!=null && card1.getSideA().length() == 10)
                maxCharA=true;
            
            if(card1.getSideB()!=null && card1.getSideB().length() == 10 )
                maxCharB=true;
                
            System.out.println(sideA);
                //sideB=true;
            
//                if (e.VK_UP == e.getKeyCode()) {
//                } else if (e.VK_DOWN == e.getKeyCode()) {
//                } else if (e.VK_LEFT == e.getKeyCode()) {
//                } else if (e.VK_RIGHT == e.getKeyCode()) {
//                }
                
//                if (e.getKeyCode()  >= e.VK_A && e.getKeyCode()  <= e.VK_Z) 
//                    {  
//                       
//                       
//                        
//                    }
//                 else if(e.VK_SHIFT == e.getKeyCode()) {
//                        shift = true;
//                    }
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
                       g.setFont(new Font("Roboto",Font.BOLD,100));
                       g.drawString("ezCard", getWidth2()/5, getHeight2()/3);
                       g.setFont(new Font("Roboto",Font.ITALIC,50));
                       g.drawString("start", getWidth2()/5, getHeight2()/2);
                       g.drawString("settings", getWidth2()/5, getHeight2()/2 + 100);
                   }
                   if(cardCreate)
                   {
                       
                       if(sideA)                         
                           g.fillRect(30, 230, 350, 270);
                       else 
                           g.fillRect(425, 230, 350, 270);

                       g.setColor(Color.black);
                       g.setFont(new Font("Roboto",Font.PLAIN,50));      
                       g.drawImage(cardCreateIMG, getX(0), getY(0),getWidth2(),getHeight2(), this);
                       if(card1.getSideA() != null)                                                                      
                           g.drawString("" + card1.getSideA(),52, 6*getHeight2()/10 );
                       
                       if(card1.getSideB()!=null)
                           g.drawString("" + card1.getSideB(),450, 6*getHeight2()/10 );
                       
                       
                           
                   }
                    
                       
                       

        gOld.drawImage(image, 0, 0, null);
    }
  
public void drawCannonBall(Graphics2D g,double xscale,double yscale,
double rot,int x,int y)
{
   g.translate(x,y);
   g.rotate(rot  * Math.PI/180.0);
   g.scale( xscale , yscale );
   g.fillOval(-5,-5, 10, 10);

   g.scale( 1.0/xscale,1.0/yscale );
   g.rotate(-rot  * Math.PI/180.0);
   g.translate(-x,-y);
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
    scale=0;
    cardCreate=true; 
    menu=false;

    card1 = new card();

    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            backgroundIMG = Toolkit.getDefaultToolkit().getImage("./background.PNG");
            cardCreateIMG = Toolkit.getDefaultToolkit().getImage("./cardCreate.PNG");
            
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
}
