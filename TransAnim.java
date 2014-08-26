import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class TransAnim extends JApplet {
    public static void main(String args[]) {
        JFrame f = new JFrame("TransAnim");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new TransAnim();
        f.getContentPane().add(applet, BorderLayout.CENTER);
        applet.init();
        f.setSize(new Dimension(500,500));
        f.setVisible(true);
    }

    public void init() {
        getContentPane().setLayout(new BorderLayout());
        TransAnimPanel animator = new TransAnimPanel();
        animator.setSize(500, 500);
        getContentPane().add(animator);
        animator.setVisible(true);
        animator.startAnimation();
    }
}

class TransAnimPanel extends JPanel implements Runnable {
    Thread animatorThread;
    int delay;

    AffineTransform raytrans,suntrans,earthtrans,moontrans,rot;
    Shape earth,sun,moon,sunray;
    double theta=0.0;
    double x=250,y=250;

    public TransAnimPanel() {
	raytrans = new AffineTransform();
        suntrans=new AffineTransform();
        earthtrans=new AffineTransform();
        moontrans=new AffineTransform();
        rot=new AffineTransform();
	//myshape = new Arc2D.Float(-10,-10,20,20,45,270, Arc2D.PIE);// Its an Arc2D so you can see it spinning!
        sun=new Ellipse2D.Double(-20,-20,40,40);
        earth=new Ellipse2D.Double(-10,-15,20,30);
        moon=new Ellipse2D.Double(-5,-7.5,10,15);

        int [] xPoints={-6,0,6};
        int [] yPoints={4,-8,4};
        sunray=new Polygon(xPoints,yPoints,3);


        delay = 100;
    }

    //Draw the current frame of animation.
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2 = (Graphics2D)g;
      theta +=0.1;

      suntrans.setToTranslation(x,y);
      earthtrans.setTransform(suntrans);

      suntrans.rotate(-theta); //rotate anti-clockwise
      

      Shape fillsun=suntrans.createTransformedShape(sun);//operations for the sun
      g2.setColor(Color.yellow);
      g2.fill(fillsun);
      g2.setColor(Color.black);
      g2.draw(suntrans.createTransformedShape(sun));

      //REPRESENTING THE RAYS OF THE SUN
      g2.setColor(Color.yellow);//Color for sunrays

      raytrans.setTransform(suntrans);//ray1 drawing
      raytrans.translate(25,25);
      Shape fillray=raytrans.createTransformedShape(sunray);
      g2.fill(fillray);
      g2.draw(raytrans.createTransformedShape(sunray));

      raytrans.translate(-50, -50);
      Shape fillray1=raytrans.createTransformedShape(sunray);
      g2.fill(fillray1);
      g2.draw(raytrans.createTransformedShape(sunray));

      raytrans.translate(50,0);
      Shape fillray2=raytrans.createTransformedShape(sunray);
      g2.fill(fillray2);
      g2.draw(raytrans.createTransformedShape(sunray));

      raytrans.translate(-50,50);
      Shape fillray3=raytrans.createTransformedShape(sunray);
      g2.fill(fillray3);
      g2.draw(raytrans.createTransformedShape(sunray));

      //TRANSFORMATION FOR THE EARTH

      earthtrans.rotate(-theta);
      earthtrans.translate(100,0);
      moontrans.setTransform(earthtrans);
      
      earthtrans.setTransform(earthtrans);
      earthtrans.rotate(theta);
      earthtrans.rotate(theta);

      Shape fillearth=earthtrans.createTransformedShape(earth);
      g2.setColor(Color.blue);
      g2.fill(fillearth);
      g2.draw(earthtrans.createTransformedShape(earth));

      //TRANSFORMATION FOR THE MOON
      
      moontrans.rotate(theta*5);
      moontrans.translate(50,0);
      moontrans.rotate(-theta*100);
      
      Shape fillmoon=moontrans.createTransformedShape(moon);
      g2.setColor(Color.gray);
      g2.fill(fillmoon);
      g2.draw(moontrans.createTransformedShape(moon));
    }


    public void startAnimation() {
      //Start the animating thread.
      if (animatorThread == null) {
	animatorThread = new Thread(this);
      }
      animatorThread.start();
    }

    public void stopAnimation() {
      //Stop the animating thread.
      animatorThread = null;
    }

    public void run() {
        //Just to be nice, lower this thread's priority
        //so it can't interfere with other processing going on.
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        //Remember the starting time.
        long startTime = System.currentTimeMillis();

        //Remember which thread we are.
        Thread currentThread = Thread.currentThread();

        //This is the animation loop.
        while (currentThread == animatorThread) {
            //Advance the animation frame.
            //Display it.
            repaint();

            //Delay depending on how far we are behind.
            try {
                startTime += delay;
                Thread.sleep(Math.max(0,
                             startTime-System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}