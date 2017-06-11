import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 A rectangular node that is filled with a color.
 */
public class voltageSupplyNode implements Node
{
   /**
    Construct a rectangle node with a given color and the default width
    and height.
    @param aColor the fill color
    */
   public voltageSupplyNode(Color aColor)
   {
      height = DEFAULT_SIZE;
      width = DEFAULT_SIZE;
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

   public void draw(Graphics2D g2)
   {

      try {
         image = ImageIO.read(new File("src/swagLord.png"));
      } catch (IOException ex) {
         System.out.println("Cant load image");
      }

      /*AffineTransform at = new AffineTransform();
      at.translate(x,y);
      at.rotate(Math.PI/2);
      at.scale(0.0750, 0.0750);
      at.translate(-image.getWidth()/15, -image.getHeight()/1);
      g2.drawImage(image, at, null);*/

      g2.drawImage(image, x, y,70,70,null);
   //   String volLabel = "V"+this.getCount();
      g2.drawString("V"+this.labelid, (int)x - 13, (int)y + 40);
    //  GraphFrame.addVolLabels(volLabel, this.getCount());

   }

   public void rotationDraw(Graphics2D g2){

      try {
         image = ImageIO.read(new File("src/swagLord.png"));

      } catch (IOException ex) {
         System.out.println("Cant load image");
      }

      AffineTransform at = new AffineTransform();

      at.translate(x,y);
      at.rotate(Math.PI/2);
      at.scale(0.0750, 0.0750);
      at.translate(-image.getWidth()/15, -image.getHeight()/1);

      g2.drawImage(image, at, null);

   //   String volLabel = "V"+this.getCount();
      g2.drawString("V"+this.getCount(), (int)x - 13, (int)y + 40);
     // GraphFrame.addVolLabels(volLabel, this.getCount());

   }

   public void translate(double dx, double dy)
   {
      x += dx;
      y += dy;
   }

   public boolean contains(Point2D p)
   {
      Rectangle2D rectangle = new Rectangle2D.Double(
              x, y+15, 50, 40);
      return rectangle.contains(p);
   }

   public Rectangle2D getBounds()
   {
      return new Rectangle2D.Double(
              x, y, width, height);
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


      if (distance == 0)
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

   public int typeComp(){

      return 4;
   }


   public int getCount(){
      return volLabelCounter;
   }
   public void  incCounter(int count){
      volLabelCounter = count;
   }
   public connectionNode getCn1(){
      return cn1;
   }
   public connectionNode getCn2(){
      return cn1;
   }
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
   private connectionNode cn1;
   private int x;
   private int y;
   private int width;
   private int height;
   private Color color;
   private transient BufferedImage image;
   public int volLabelCounter = 0;
   private static final int DEFAULT_SIZE = 20;
}
