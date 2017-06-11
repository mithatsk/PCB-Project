/**
 * Created by aidgha14 on 2017-05-02.
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
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
import java.io.Serializable;

public class connectionNode implements Node, Serializable{

    /**
     Construct a circle node with a given size and color.
     @param aColor the fill color
     */
    public connectionNode(Color aColor)
    {
        size = DEFAULT_SIZE/3;
        x = 0;
        y = 0;
        color = aColor;
        nsize = size;
    }
    public connectionNode(Color aColor,double x ,double y)
    {
        size = DEFAULT_SIZE/3;
        this.x=x;
        this.y=y;
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

    Ellipse2D circle = new Ellipse2D.Double(x,y,size,size);
    Color oldColor = g2.getColor();
    g2.setColor(color);
    g2.fill(circle);
    g2.setColor(oldColor);
    g2.draw(circle);

    }

    public void rotationDraw(Graphics2D g2){}

    public connectionNode getCn1(){
        return cn1;
    }
    public connectionNode getCn2(){
        return cn1;
    }
    public void translate(double dx, double dy)
    {
        x += dx;
        y += dy;
    }

    public boolean contains(Point2D p)
    {
        Ellipse2D circle = new Ellipse2D.Double(
                x, y, nsize, nsize);
        return circle.contains(p);
    }

    public Rectangle2D getBounds()
    {
        return new Rectangle2D.Double(
                x, y, size, size);
    }

    public Point2D getConnectionPoint(Point2D other)
    {
        double centerX = x + size / 2;
        double centerY = y + size / 2;
        double dx = other.getX() - centerX;
        double dy = other.getY() - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance == 0) return other;
        else return new Point2D.Double(
                centerX + dx * (size / 2) / distance,
                centerY + dy * (size / 2) / distance);
    }

    public int getCount(){
        return 0;
    }
    public void  incCounter(int count){}
    public int typeComp(){

        return 0;
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
    public void setDefaultSize(int s){
    	this.nsize = (double)s;
    }
    private double nsize;
    private int labelid;
    private double x;
    private double y;
    private double size;
    private connectionNode cn1;
    private Color color;
    private static final int DEFAULT_SIZE = 20;
}





