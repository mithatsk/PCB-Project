import javax.swing.*;

/**
   A program for editing UML diagrams.
*/
public class SimpleGraphEditor
{
   public static void main(String[] args)
   {
      JFrame frame = new GraphFrame(new SimpleGraph());
      frame.setDefaultCloseOperation(2);
      frame.setVisible(true);
   }
}

