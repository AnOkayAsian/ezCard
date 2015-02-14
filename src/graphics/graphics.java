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



public class graphics extends JFrame implements Runnable {
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 25;
    static final int WINDOW_WIDTH = 1000;
    static final int WINDOW_HEIGHT = 700;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    int scale;
    Image image;
    
    
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
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
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
//fill border
//        g.setColor(Color.white);
//        g.fillPolygon(x, y, 4);
// draw border
//        g.setColor(Color.red);
//        g.drawPolyline(x, y, 5);



        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

       

        gOld.drawImage(image, 0, 0, null);
    }
    public static void drawHouse (Graphics2D g, double xscale, double yscale, double rot, int x, int y)
    {
        g.translate(x,y);
        g.rotate(rot * Math.PI/180.0);
        g.scale(xscale,yscale);

        int xvals[]={0,10,10,-10,-10,0};
        int yvals[]={-15,-10,10,10,-10,-15};
        g.fillPolygon(xvals,yvals,xvals.length-1);

        g.scale(1.0/xscale,1.0/yscale);
        g.rotate(-rot*Math.PI/180.0);
        g.translate(-x,-y);
    }

     public static void drawSpade(Graphics g, int x, int y)
    {
        g.translate(x,y);
        int xvals[]={-30,-40,-40,-20,-20, 30, 20,-30};
        int yvals[]={ 20, 20, 40, 40, 30,-20,-30, 20};
        g.fillPolygon(xvals,yvals,xvals.length-1);
        g.setColor(Color.BLACK);
        g.drawPolygon(xvals,yvals,xvals.length-1);
        g.translate(-x,-y);
        g.setFont(new Font("Onyx",Font.PLAIN,30));
        g.drawString("Original",400,450);
    }
     public static void drawHead(Graphics g, int x, int y)
    {
        g.translate(x,y);
        int xvals[]={ 30, 40, 50, 50, 20, 10, 20,-30};
        int yvals[]={ -20,-10,-20,-50,-50,-40,-30,-20};
        g.fillPolygon(xvals,yvals,xvals.length-1);
        g.translate(-x,-y);
    }
    public static void drawSpadenew(Graphics2D g, double xscale, double yscale, double rot, int x, int y)
    {
        g.translate(x,y);
        g.rotate(rot * Math.PI/180.0);
        g.scale(xscale,yscale);

        int xvals[]={-30,-40,-40,-20,-20, 30, 20,-30};
        int yvals[]={ 20, 20, 40, 40, 30,-20,-30, 20};
        g.fillPolygon(xvals,yvals,xvals.length-1);
        g.setColor(Color.BLACK);
        g.drawPolygon(xvals,yvals,xvals.length-1);

        g.scale(1.0/xscale,1.0/yscale);
        g.rotate(-rot*Math.PI/180.0);
        g.translate(-x,-y);
        g.setFont(new Font("Onyx",Font.PLAIN,30));
        g.drawString("New",220,250);
    }
     public static void drawHeadnew(Graphics2D g, double xscale, double yscale, double rot, int x, int y)
    {
        g.translate(x,y);
        g.rotate(rot * Math.PI/180.0);
        g.scale(xscale,yscale);

        int xvals[]={ 30, 40, 50, 50, 20, 10, 20,-30};
        int yvals[]={ -20,-10,-20,-50,-50,-40,-30,-20};
        g.fillPolygon(xvals,yvals,xvals.length-1);

        g.scale(1.0/xscale,1.0/yscale);
        g.rotate(-rot*Math.PI/180.0);
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
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }

            reset();

scale++;
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
