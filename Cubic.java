import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.Applet;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Cubic extends JApplet{

    static protected JLabel label;
    CubicPanel cubicPanel;

    public void init(){
	//Initialize the layout.
        getContentPane().setLayout(new BorderLayout());
        cubicPanel = new CubicPanel();
        cubicPanel.setBackground(Color.white);
	getContentPane().add(cubicPanel);
	label = new JLabel("Drag the points to adjust the curve.");
	getContentPane().add("South", label);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("Cubic");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new Cubic();
        f.getContentPane().add(applet, BorderLayout.CENTER);
        applet.init();
        f.setSize(new Dimension(500,500));
        f.setVisible(true);
    }
}

class CubicPanel extends JPanel implements MouseListener, MouseMotionListener{

	BufferedImage bi;
	Graphics2D big;
	int x, y;
        double x2Original,y2Original,xMidOriginal,yMidOriginal,x3Original,y3Original;
	Rectangle area, startpt, endpt, onept, twopt, rect;
        Rectangle endpt1,threept,fourpt;//*************************************************
	CubicCurve2D.Double cubic = new CubicCurve2D.Double();

        CubicCurve2D.Double cubic1=new CubicCurve2D.Double();

	Point2D.Double start, end, one, two, point;
        Point2D.Double three,four,end1;
	boolean firstTime = true;
	boolean pressOut = false;

	public CubicPanel(){

		setBackground(Color.white);
                addMouseMotionListener(this);
                addMouseListener(this);

		start = new Point2D.Double();
		one = new Point2D.Double();
		two = new Point2D.Double();
		end = new Point2D.Double();

                cubic.setCurve(start, one, two, end);

                three= new Point2D.Double();
                four= new Point2D.Double();
                end1= new Point2D.Double();
                cubic1.setCurve(end,three,four,end1);

		startpt = new Rectangle(0, 0, 8, 8);
		endpt = new Rectangle(0, 0, 8, 8);
		onept = new Rectangle(0, 0, 8, 8);
		twopt = new Rectangle(0, 0, 8, 8);

                endpt1=new Rectangle(0,0,8,8);
                threept=new Rectangle(0,0,8,8);
                fourpt=new Rectangle(0, 0, 8, 8);
	}

	public void mousePressed(MouseEvent e){

		x = e.getX();
		y = e.getY();

		if(startpt.contains(x, y)){
			rect = startpt;
			point = start;
                        x = startpt.x - e.getX();
                        y = startpt.y - e.getY();
                        updateLocation(e);
		}
 		else if(endpt.contains(x, y)){
			rect = endpt;
			point = end;
                        x2Original=(int)two.x;
                        y2Original=(int)two.y;
                        x3Original=(int)three.x;
                        y3Original=(int)three.y;
                        xMidOriginal=(int) end.x;
                        yMidOriginal=(int) end.y;
                        x = endpt.x - e.getX();
                        y = endpt.y - e.getY();
                        updateLocation(e);
                  
		}
		else if(onept.contains(x, y)){
			rect = onept;
			point = one;
                        x = onept.x - e.getX();
                        y = onept.y - e.getY();
                        updateLocation(e);
		}
		else if(twopt.contains(x, y)){
			rect = twopt;
			point = two;
                        x2Original=(int)two.x;
                        y2Original=(int)two.y;
                        x3Original=(int)three.x;
                        y3Original=(int)three.y;
                        x = twopt.x - e.getX();
                        y = twopt.y - e.getY();
                        updateLocation(e);
		}
                else if(threept.contains(x,y)){
                    rect=threept;
                    point=three;
                    x2Original=(int)two.x;
                    y2Original=(int)two.y;
                    x3Original=(int)three.x;
                    y3Original=(int)three.y;
                    x=threept.x-e.getX();
                    y=threept.y-e.getY();
                    updateLocation(e);
                }
                else if(fourpt.contains(x,y)){
                    rect=fourpt;
                    point=four;
                    x=fourpt.x-e.getX();
                    y=fourpt.y-e.getY();
                    updateLocation(e);
                }
                else if(endpt1.contains(x,y)){
                    rect=endpt1;
                    point=end1;
                    x=endpt1.x-e.getX();
                    y=endpt1.y-e.getY();
                    updateLocation(e);
                }


                else {
			pressOut = true;
                }
	}

	public void mouseDragged(MouseEvent e){
                if(!pressOut) {
                        updateLocation(e);
		}

	}

	public void mouseReleased(MouseEvent e){
                if(startpt.contains(e.getX(), e.getY())){
                        rect = startpt;
                        point = start;
                        updateLocation(e);
                }
                else if(endpt.contains(e.getX(), e.getY())){
                        rect = endpt;
                        point = end;
                        updateLocation(e);

                }
                else if(onept.contains(e.getX(), e.getY())){
                        rect = onept;
                        point = one;
                        updateLocation(e);
                }
                else if(twopt.contains(e.getX(), e.getY())){
                        rect = twopt;
                        point = two;
                        updateLocation(e);
                }
                else if(threept.contains(e.getX(),e.getY())){
                    rect=threept;
                    point=three;
                    updateLocation(e);
                }
                else if(fourpt.contains(e.getX(),e.getY())){
                    rect=fourpt;
                    point=four;
                    updateLocation(e);
                }
                else if(endpt1.contains(e.getX(),e.getY())){
                    rect=endpt1;
                    point=end1;
                    updateLocation(e);
                }
                else {
                        pressOut = false;
                }
	}

	public void mouseMoved(MouseEvent e){}

	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}

	public void updateLocation(MouseEvent e){

		rect.setLocation((x + e.getX())-4, (y + e.getY())-4);
		point.setLocation(x + e.getX(), y + e.getY());

                if(point==two){
                double distanceOnX=point.x-x2Original;
                double distanceOnY=point.y-y2Original;
                
                three.setLocation(x3Original-distanceOnX , y3Original-distanceOnY);
                threept.setLocation((int)(x3Original-distanceOnX), (int)(y3Original-distanceOnY));

                }

                if(point==three){
                double distanceOnX=point.x-x3Original;
                double distanceOnY=point.y-y3Original;

                two.setLocation(x2Original-distanceOnX , y2Original-distanceOnY);
                twopt.setLocation((int)(x2Original-distanceOnX) ,(int) (y2Original-distanceOnY));
                }

                if(point==end){
                double distanceOnX=point.x-xMidOriginal;
                double distanceOnY=point.y-yMidOriginal;

                two.setLocation(x2Original+distanceOnX , y2Original+distanceOnY);
                twopt.setLocation((int)(x2Original+distanceOnX) ,(int) (y2Original+distanceOnY));

                three.setLocation(x3Original+distanceOnX , y3Original+distanceOnY);
                threept.setLocation((int)(x3Original+distanceOnX) ,(int) (y3Original+distanceOnY));
                }

                checkPoint();

		cubic.setCurve(start, one, two, end);

                cubic.setCurve(end,three,four,end1);

		repaint();
	}

	public void paintComponent(Graphics g){
                super.paintComponent(g);
		update(g);
	}

	public void update(Graphics g){

		Graphics2D g2 = (Graphics2D)g;
		Dimension dim = getSize();
		int w = dim.width;
                int h = dim.height;

          	if(firstTime){

		  // Create the offsecren graphics  to render to
		  bi = (BufferedImage)createImage(w, h);
		  big = bi.createGraphics();

		  // Get some initial positions for the control points

		  start.setLocation(w/3+25, h/2);
		  end.setLocation(2*w/3, h/2);
		  one.setLocation((int)(end.x+start.x)/2+25, (int)(end.y+start.y)/2+25);
		  two.setLocation((int)(end.x+start.x)/2+25, (int)(end.y+start.y)/2-25);

                  end1.setLocation(w-25,h/2);
                  three.setLocation((int)(end1.x+end.x)/2+25,(int)(end1.y+end.y)/2+25);
                  four.setLocation((int)(end1.x+end.x)/2+25,(int)(end1.y+end.y)/2-25);

                  // Set the initial positions of the squares that are
		  // drawn at the control points

                  startpt.setLocation((int)((start.x)-4), (int)((start.y)-4));
                  endpt.setLocation((int)((end.x)-4), (int)((end.y)-4));
                  onept.setLocation((int)((one.x)-4), (int)((one.y)-4));
                  twopt.setLocation((int)((two.x)-4), (int)((two.y)-4));

                  threept.setLocation((int)((three.x)-4),(int)((three.y)-4));
                  fourpt.setLocation((int)((four.x)-4),(int)((four.y)-4));
                  endpt1.setLocation((int)((end1.x)-4),(int)((end1.y)-4));

		  // Initialise the CubicCurve2D
		  cubic.setCurve(start, one, two, end);
                  cubic1.setCurve(end,three,four,end1);

		  // Set some defaults for Java2D
        	  big.setColor(Color.black);
		  big.setStroke(new BasicStroke(5.0f));
		  big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				       RenderingHints.VALUE_ANTIALIAS_ON);
		  area = new Rectangle(dim);
		  firstTime = false;
		}

		// Clears the rectangle that was previously drawn.

		big.setColor(Color.white);
		big.clearRect(0, 0, area.width, area.height);

		// Set the colour for the bezier
		big.setPaint(Color.black);

		// Replace the following line by your own function to
		// draw the bezier specified by start, one, two, end
                //big.draw(cubic);
                draw(big,start,one,two,end);
                draw(big,end,three,four,end1);

          
		// Draw the control points

		big.setPaint(Color.red);
		big.fill(startpt);
		big.setPaint(Color.magenta);
		big.fill(endpt);
		big.setPaint(Color.blue);
		big.fill(onept);
		big.setPaint(new Color(0, 200, 0));
		big.fill(twopt);

                big.setPaint(Color.black);
                big.fill(threept);
                big.fill(fourpt);
                big.fill(endpt1);

		// Draws the buffered image to the screen.
		g2.drawImage(bi, 0, 0, this);

	}

        public void draw(Graphics2D big,Point2D.Double start,Point2D.Double one,Point2D.Double two,Point2D.Double end){


            if(Math.sqrt((end.x-start.x)*(end.x-start.x)+(end.y-start.y)*(end.y-start.y))<2)
            {
                big.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
                
            }
            else {
            Point2D.Double bis1,bis2,bis3,bis4,bis5,bis6;
            bis1=new Point2D.Double();
            bis2=new Point2D.Double();
            bis3=new Point2D.Double();
            bis4=new Point2D.Double();
            bis5=new Point2D.Double();
            bis6=new Point2D.Double();

            bis1.setLocation((start.x+one.x)/2,(start.y+one.y)/2 );
            bis3.setLocation((one.x+two.x)/2,(one.y+two.y)/2 );
            bis5.setLocation((two.x+end.x)/2,(two.y+end.y)/2 );
            bis2.setLocation((bis1.x+bis3.x)/2,(bis1.y+bis3.y)/2 );
            bis4.setLocation((bis3.x+bis5.x)/2,(bis3.y+bis5.y)/2 );
            bis6.setLocation((bis2.x+bis4.x)/2,(bis2.y+bis4.y)/2 );

            draw(big,start,bis1,bis2,bis6);
            draw(big,bis6,bis4,bis5,end);

            }
        }


	/* Checks if the rectangle is contained within the applet
         * window.  If the rectangle is not contained withing the
         * applet window, it is redrawn so that it is adjacent to the
         * edge of the window and just inside the window.  */

	void checkPoint(){

		if (area == null) {
			return;
		}

		if((area.contains(rect)) && (area.contains(point))){
			return;
		}
		int new_x = rect.x;
		int new_y = rect.y;

		double new_px = point.x;
		double new_py = point.y;

		if((rect.x+rect.width)>area.getWidth()){
			new_x = (int)area.getWidth()-(rect.width-1);
		}
		if(point.x > area.getWidth()){
			new_px = (int)area.getWidth()-1;
		}
		if(rect.x < 0){
			new_x = -1;
		}
		if(point.x < 0){
			new_px = -1;
		}
		if((rect.y+rect.width)>area.getHeight()){
			new_y = (int)area.getHeight()-(rect.height-1);
		}
		if(point.y > area.getHeight()){
			new_py = (int)area.getHeight()-1;
		}
		if(rect.y < 0){
			new_y = -1;
		}
		if(point.y < 0){
                        new_py = -1;
                }
		rect.setLocation(new_x, new_y);
		point.setLocation(new_px, new_py);

	}
}