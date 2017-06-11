import java.awt.*;
import java.awt.geom.*;

/**
   An inivisible node that is used in the toolbar to draw an
   edge.
*/
public class PointNode implements Node
{
   /**
      Constructs a point node with coordinates (0, 0)
   */
   public PointNode()
   {
      point = new Point2D.Double();
      x = point.getX();
      y = point.getY();
   }

   public void draw(Graphics2D g2)
   {
   }

   public void rotationDraw(Graphics2D g2){
   }

   public void translate(double dx, double dy)
   {
      point.setLocation(point.getX() + dx, point.getY() + dy);
   }

   public boolean contains(Point2D p)
   {
      return false;
   }

   public Rectangle2D getBounds()
   {
      return new Rectangle2D.Double(point.getX(), 
         point.getY(), 0, 0);
   }

   public Point2D getConnectionPoint(Point2D other)
   {
      return point;
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

   public int getCount(){
      return 0;
   }
   public void  incCounter(int count){

   }

   public int typeComp(){

      return 5;
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
   private double x;
   private double y;
   private Point2D point;
   private connectionNode cn1;
}
