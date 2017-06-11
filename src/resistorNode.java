import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

/**
 A circular node that is filled with a color.
 */
public class resistorNode implements Node  {

   /**
    Construct a circle node with a given size and color.
    @param aColor the fill color
    */
   public resistorNode(Color aColor)
   {
      size = DEFAULT_SIZE;
   /*   width = 60;
      hight = 40; */
      x = 0;
      y = 0;
      color = aColor;


   }

   public void setColor(Color aColor)
   {
      color = aColor;
   }

   public Color getColor()
   {
      return color;
   }

   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException exception)
      {
         return null;
      }
   }

   public void draw(Graphics2D g2){

      try {
         image = ImageIO.read(new File("src/resistor.png"));
         Rectangle r1 = new Rectangle();
         r1.setSize(80,20);
         r1.setLocation((int)x,(int)y);

      } catch (IOException ex) {
         System.out.println("Cant load image");
      }

      //String resLabel = "R"+this.getCount();
      g2.drawImage(image, (int)x,(int)y,70,15,null);
      g2.drawString("R"+this.labelid, (int)x+30, (int)y-3);
    //  GraphFrame.addResLabels(resLabel, this.getCount());

   }

   public void rotationDraw(Graphics2D g2){



   }

   public connectionNode getCn1(){
      return cn1;
   }
   public connectionNode getCn2(){
      return cn2;
   }

   public int getCount(){
      return resistorLabelCounter;
   }

   public void  incCounter(int count){
      resistorLabelCounter = count;
      System.out.println(resistorLabelCounter + " walla res counterix");
   }


   public void translate(double dx, double dy)
   {
      x += dx;
      y += dy;
   }

   public boolean contains(Point2D p)
   {
	   Rectangle2D circle = new Rectangle2D.Double(
              x+10, y-25, 40, 50);
      return circle.contains(p);
   }

   public Rectangle2D getBounds()
   {
      return new Rectangle2D.Double(
              x, y, size, size);
   }

   public Point2D getConnectionPoint(Point2D other)
   {
	   double halfHeight = height / 2;
	      double halfWidth = width / 2;

	      double centerX = x + halfWidth;
	      double centerY = y + halfHeight;

	      double dx = other.getX() - centerX;
	      double dy = other.getY() - centerY;
	      double distance = Math.sqrt(dx * dx + dy * dy);


	      if (true)
	    	  return new Point2D.Double(0,0);

	      double xOffset;
	      double yOffset;
	      if (dx == 0)
	      {
	         xOffset = 0;
	         yOffset = halfHeight * sign(dy);
	      }
	      else if (dy == 0)
	      {
	         xOffset = halfWidth * sign(dx);
	         yOffset = 0;
	      }
	      else
	      {
	         xOffset =
	                 Math.min(halfWidth, Math.abs(halfHeight * dx / dy)) * sign(dx);

	         yOffset =
	                 Math.min(halfHeight, Math.abs(halfWidth * dy / dx)) * sign(dy);
	      }

	      return new Point2D.Double(0,0);
   }
   
   private final double sign(double d)
   {
      if (d >= 0)
         return 1.0;
      else
         return -1.0;

   }

 
   
   public int typeComp(){return 1; }
   public double getX(){
   	return x;
   }
   public double getY(){
   	return y;
   }
   public void setLabelId(int id){
   	labelid = id;
   }
   public int getLabelId(){
	   return this.labelid;
   }
   private int labelid;
   private transient BufferedImage image;
   private double x;
   private double y;
   private double size ;
   private double width;
   private int height;
   private connectionNode cn1;
   private connectionNode cn2;
   private Color color;
   public int resistorLabelCounter = 0;
   private static final int DEFAULT_SIZE = 20;
}
