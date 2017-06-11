
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 A panel to draw a graph
 */

public class GraphPanel extends JComponent
{

   public class Cordinates{

      public Point2D mp;
      public int id;

      public Cordinates(Point2D mousePointer, int idForComp){

         mp = mousePointer;
         id = idForComp;
      }
   }

   public class myNodes{

      public Node nodes;


      public myNodes(Node n){
         nodes = n;
      }
   }
   /**
    Constructs a graph.
    @param aToolBar the tool bar with the node and edge tools
    @param aGraph the graph to be displayed and edited
    */


   public GraphPanel(ToolBar aToolBar, Graph aGraph)
   {
      toolBar = aToolBar;
      graph = aGraph;
      setBackground(Color.WHITE);

      ListforResistorNodes.add(null);
      ListforCapacitorNodes.add(null);
      ListforVoltageNodes.add(null);
      ListforInductorNodes.add(null);

      addMouseListener(new MouseAdapter()
      {
         public void mousePressed(MouseEvent event)
         {
            System.out.println("mousePressed");

            mousePoint = event.getPoint();

            Node n = graph.findNode(mousePoint);
            Edge e = graph.findEdge(mousePoint);

            Object tool = toolBar.getSelectedTool();
            selectedTools = toolBar.getSelectedTool().getClass().getName();

            System.out.println("SelectedTools: " + selectedTools);
            System.out.println("tool: " + tool);

            if(tool == "Selected Comp"){

               System.out.println("DEBUGG 0");
               stateNode = graph.findNode(mousePoint);

               int idForComp = stateNode.typeComp();

               System.out.println("Print ID: " + idForComp);

               if(stateNode != null){
                  //res
                  if(idForComp == 1){
                     System.out.println("DEBUGG 1");

                     GraphFrame.clearErrorLogg(true);

                     selectState = false;
                     resState = true;
                     indState = false;
                     condState = false;
                     volState = false;

                     selected = stateNode;

                     dragStartPoint = mousePoint;
                     dragStartBounds = stateNode.getBounds();
                  }

                  //cond
                  if(idForComp == 2){

                     System.out.println("DEBUGG 2");

                     GraphFrame.clearErrorLogg(true);

                     selectState = false;
                     resState = false;
                     indState = false;
                     condState = true;
                     volState = false;

                     selected = stateNode;

                     dragStartPoint = mousePoint;
                     dragStartBounds = stateNode.getBounds();

                  }
                  //ind
                  if(idForComp == 3){

                     System.out.println("DEBUGG 2");

                     GraphFrame.clearErrorLogg(true);

                     selectState = false;
                     resState = false;
                     indState = true;
                     condState = false;
                     volState = false;

                     selected = stateNode;

                     dragStartPoint = mousePoint;
                     dragStartBounds = stateNode.getBounds();

                  }
                  //vol
                  if(idForComp == 4){

                     System.out.println("DEBUGG 4");

                     GraphFrame.clearErrorLogg(true);

                     selectState = false;
                     resState = false;
                     indState = false;
                     condState = false;
                     volState = true;

                     selected = stateNode;
                     dragStartPoint = mousePoint;
                     dragStartBounds = stateNode.getBounds();

                     //Progress is needed here for rotational stuff
                     Graph.rotationState(true,(Node)selected, mousePoint);

                  }

               }else{

                  attention = "Try to select the component again please!";
                  GraphFrame.displayOutPutLogg(attention);
                  selected = null;
               }
            }

            if (tool instanceof Node)
            {

               String res = "resistorNode";
               String ind = "inductorNode";
               String cap = "capacitorNode";
               String vol = "voltageSupplyNode";

               if(selectedTools == res){
            	  
                  resCounter++;
                  resState = true;
                  condState = false;
                  indState = false;
                  volState = false;
                  selectState = false;
                  
                  Node prototype = (Node) tool;

                  newNode = (Node) prototype.clone();
                  connectionNode cn = new connectionNode(Color.BLACK);
                  connectionNode cn2 = new connectionNode(Color.BLACK);
                  double dx, dy;
                  dx=event.getX()+70;
                  dy=event.getY();
                  if(resistorDeletedId.size()>0){
                	  newNode.setLabelId(resistorDeletedId.get(resistorDeletedId.size()-1));
            		  resistorDeletedId.remove(resistorDeletedId.size()-1);            		  
            	  }else{    
            	  resistorId ++;
            	  	newNode.setLabelId(resistorId);
            	  }
                  
                  newNode.incCounter(resCounter);
                  
                  ListforResistorNodes.add(cn);
                  ListforResistorNodes.add(cn2);

                  graph.add(cn,new Point2D.Double(dx -1 , dy + 2.7));
                  graph.add(cn2,new Point2D.Double(dx - 70,dy + 2.7));

                  added = graph.add(newNode, mousePoint);

                  //This is for development purposes
                  nodeArrayList.add(new myNodes(newNode));
                  cordinateArrayList.add(new Cordinates(mousePoint,1));

               }

               if(selectedTools == cap){

                  capCounter++;
                  condState=true;
                  volState = false;
                  resState = false;
                  indState = false;
                  selectState = false;

                  System.out.println(volState);
                  Node prototype = (Node) tool;
                  newNode = (Node) prototype.clone();
                  connectionNode cn = new connectionNode(Color.BLACK);
                  connectionNode cn2 = new connectionNode(Color.BLACK);
                  double dx, dy;
                  dx=event.getX()+70;
                  dy=event.getY();
                  
                  if(capacitorDeletedId.size()>0){
                	  newNode.setLabelId(capacitorDeletedId.get(capacitorDeletedId.size()-1));
                	  capacitorDeletedId.remove(capacitorDeletedId.size()-1);            		  
            	  }else{    
            	  capacitorId ++;
            	  	newNode.setLabelId(capacitorId);
            	  }
                  
                  newNode.incCounter(capCounter);

                  ListforCapacitorNodes.add(cn);
                  ListforCapacitorNodes.add(cn2);

                  graph.add(cn,new Point2D.Double(dx-1, dy+31));
                  graph.add(cn2,new Point2D.Double(dx-72,dy+31));
                  graph.add(newNode, new Point2D.Double(10, 10));
                  added = graph.add(newNode, mousePoint);

                  //This is for development purposes
                  nodeArrayList.add(new myNodes(newNode));
                  cordinateArrayList.add(new Cordinates(mousePoint,2));
               }

               if(selectedTools == ind){

                  indCounter++;
                  indState = true;
                  volState = false;
                  resState = false;
                  condState = false;
                  selectState = false;
                  System.out.println(volState);
                  Node prototype = (Node) tool;
                  newNode = (Node) prototype.clone();
                  connectionNode cn = new connectionNode(Color.BLACK);
                  connectionNode cn2 = new connectionNode(Color.BLACK);
                  int dx, dy;
                  dx=event.getX()+70;
                  dy=event.getY();
                  
                  if(inductorDeletedId.size()>0){
                	  newNode.setLabelId(inductorDeletedId.get(inductorDeletedId.size()-1));
                	  inductorDeletedId.remove(inductorDeletedId.size()-1);            		  
            	  }else{    
            		inductorId ++;
            	  	newNode.setLabelId(inductorId);
            	  }
                  
                  newNode.incCounter(indCounter);
                  ListforInductorNodes.add(cn);
                  ListforInductorNodes.add(cn2);

                  graph.add(cn,new Point2D.Double(dx-3, dy+35));
                  graph.add(cn2,new Point2D.Double(dx-73,dy+35));
                  graph.add(newNode, new Point2D.Double(10, 10));
                  added = graph.add(newNode, mousePoint);

                  //This is for development purposes
                  nodeArrayList.add(new myNodes(newNode));
                  cordinateArrayList.add(new Cordinates(mousePoint,3));

               }

               if(selectedTools == vol){

                  volCounter++;
                  resState = false;
                  condState = false;
                  indState = false;
                  volState=true;
                  selectState = false;
                  System.out.println(volState);
                  Node prototype = (Node) tool;
                  newNode = (Node) prototype.clone();
                  connectionNode cn = new connectionNode(Color.BLACK);
                  connectionNode cn2 = new connectionNode(Color.BLACK);
                  int dx, dy;
                  dx=event.getX()+32;
                  dy=event.getY();
                  
                  if(voltageDeletedId.size()>0){
                	  newNode.setLabelId(voltageDeletedId.get(voltageDeletedId.size()-1));
                	  voltageDeletedId.remove(voltageDeletedId.size()-1);            		  
            	  }else{    
            		voltageId ++;
            	  	newNode.setLabelId(voltageId);
            	  }
                  
                  newNode.incCounter(volCounter);
                  ListforVoltageNodes.add(cn);
                  ListforVoltageNodes.add(cn2);
                  graph.add(cn,new Point2D.Double(dx, dy));
                  graph.add(cn2,new Point2D.Double(dx,dy+61));
                  graph.add(newNode, new Point2D.Double(10, 10));
                  added=graph.add(newNode, mousePoint);

                  //This is for development purposes
                  nodeArrayList.add(new myNodes(newNode));
                  Graph.rotationState(true,newNode,mousePoint);
                  cordinateArrayList.add(new Cordinates(mousePoint,4));
               }

               if (added)
               {
                  selected = newNode;
                  dragStartPoint = mousePoint;
                  dragStartBounds = newNode.getBounds();
               }
               else if (n != null)
               {
                  selected = n;
                  dragStartPoint = mousePoint;
                  dragStartBounds = n.getBounds();
               }
            }
            else if (tool instanceof Edge)
            {
               volState = false;
               resState = false;
               indState = false;
               condState = false;
               selectState = false;

               if (n != null) rubberBandStart = mousePoint;
            }
            lastMousePoint = mousePoint;
            repaint();
         }
         
   
         public void mouseReleased(MouseEvent event)
         { 
        	 Object tool = toolBar.getSelectedTool();
        	 System.out.println("bu:"+tool.getClass().getName().toString());
        	 String toolname = tool.getClass().getName().toString();
        	
            double currentX = event.getX();
            double currentY	= event.getY();
            if (selected instanceof Node){
            	 Node n = (Node) selected;
            	 
            	 //Check if the pin has a component close to it if it has 2 components we will set the pin notAvailable .
            	 //We do this by checking the distance between the pins and all the component's left node
         
            	 for(int i=0;i<GraphFrame.nodeGridList.size();i++){
            	   	 int full = 0;
            		 for(int ii=1;ii<ListforResistorNodes.size();ii=ii+2){
            			currentX = ListforResistorNodes.get(ii).getX();	
                 		currentY = ListforResistorNodes.get(ii).getY();
                 		double distance = distance(GraphFrame.nodeGridList.get(i).getX(),currentX,GraphFrame.nodeGridList.get(i).getY(),currentY);
                 		if(distance<15){
                 			full++;
                 		}
            		 }
            		 for(int ii=1;ii<ListforInductorNodes.size();ii=ii+2){
        				currentX = ListforInductorNodes.get(ii).getX();	
                 		currentY = ListforInductorNodes.get(ii).getY();	 
                 		double distance = distance(GraphFrame.nodeGridList.get(i).getX(),currentX,GraphFrame.nodeGridList.get(i).getY(),currentY);
                 		if(distance<15){
                 			full++;
                 		}
                 		
            		 }
            		 for(int ii=1;ii<ListforCapacitorNodes.size();ii=ii+2){
            			currentX = ListforCapacitorNodes.get(ii).getX();	
                  		currentY = ListforCapacitorNodes.get(ii).getY();	
                  		double distance = distance(GraphFrame.nodeGridList.get(i).getX(),currentX,GraphFrame.nodeGridList.get(i).getY(),currentY);                 		
                  		if(distance<15){
                 			full++;
                 		}
            		 }
            		 for(int ii=1;ii<ListforVoltageNodes.size();ii=ii+2){
            			currentX = ListforVoltageNodes.get(ii).getX();	
                  		currentY = ListforVoltageNodes.get(ii).getY();	 
                  		double distance = distance(GraphFrame.nodeGridList.get(i).getX(),currentX,GraphFrame.nodeGridList.get(i).getY(),currentY);
                  		if(distance<15){
                 			full++;
                 			 System.out.println("x: "+GraphFrame.nodeGridList.get(i).xCord + ","+ "y: " + GraphFrame.nodeGridList.get(i).getY()+"doldu");
                 			 System.out.println("x: "+currentX + ","+ "y: " + currentY+"doldu");
                  		}
            		 }
            		 if(full>1){
            			// System.out.println("x: "+GraphFrame.nodeGridList.get(i).xCord + ","+ "y: " + GraphFrame.nodeGridList.get(i).getY()+"doldu");
            			 GraphFrame.nodeGridList.get(i).setAvailable(false);
            		 }else{
            			 GraphFrame.nodeGridList.get(i).setAvailable(true);
            		 }
            	
            	 }
            	 
          	 
            	 
            	 //get X and Y cordinates of the node on the left side of the component
            	for(int i=0;i<GraphFrame.nodeGridList.size();i++){
            		if(n.getClass().getName().toString() == "resistorNode"){
            			currentX = ListforResistorNodes.get(n.getCount()*2-1).getX();
            			currentY = ListforResistorNodes.get(n.getCount()*2-1).getY();
                	}
                	if(n.getClass().getName().toString() == "inductorNode"){
                		currentX = ListforInductorNodes.get(n.getCount()*2-1).getX();	
                		currentY = ListforInductorNodes.get(n.getCount()*2-1).getY();
                    }
                	if(n.getClass().getName().toString() == "capacitorNode"){
                		currentX = ListforCapacitorNodes.get(n.getCount()*2-1).getX();
                		currentY = ListforCapacitorNodes.get(n.getCount()*2-1).getY();
                    }
                	if(n.getClass().getName().toString() == "voltageSupplyNode"){
                		currentX = ListforVoltageNodes.get(n.getCount()*2-1).getX();
                		currentY = ListforVoltageNodes.get(n.getCount()*2-1).getY();
                    }
            //calculate the distance between selected component's left node and closest pin
            //if its not close to the pin then delete it and add it's id to deletedId list
            double distance = distance(GraphFrame.nodeGridList.get(i).getX(),currentX,GraphFrame.nodeGridList.get(i).getY(),currentY);
     	 
            		  
            	if(distance > 15 && distance <45){
            		int compId = n.getCount()*2 - 1;
            		graph.removeNode(n);
            		if(n.getClass().getName().toString() == "resistorNode"){
            			graph.removeNode(ListforResistorNodes.get(compId));
            			graph.removeNode(ListforResistorNodes.get(compId+1));
            			if(resistorDeletedId.size()>0 && resistorDeletedId.get(resistorDeletedId.size()-1) == n.getLabelId()){
            				//do nothing
            			}else
            			{
            				resistorDeletedId.add(n.getLabelId());
            			}

            		}
            		if(n.getClass().getName().toString() == "inductorNode"){
                		graph.removeNode(ListforInductorNodes.get(compId));
                		graph.removeNode(ListforInductorNodes.get(compId+1));
                		graph.removeNode(n);
                		if(inductorDeletedId.size()>0 && inductorDeletedId.get(inductorDeletedId.size()-1) == n.getLabelId()){
            				//do nothing
            			}else
            			{
            				inductorDeletedId.add(n.getLabelId());
            			}
                	}
            		if(n.getClass().getName().toString() == "capacitorNode"){
                		graph.removeNode(ListforCapacitorNodes.get(compId));
                		graph.removeNode(ListforCapacitorNodes.get(compId+1));
                		graph.removeNode(n);
                		if(capacitorDeletedId.size()>0 && capacitorDeletedId.get(capacitorDeletedId.size()-1) == n.getLabelId()){
            				//do nothing
            			}else
            			{
            				capacitorDeletedId.add(n.getLabelId());
            			}
                	}
            		if(n.getClass().getName().toString() == "voltageSupplyNode"){
                		graph.removeNode(ListforVoltageNodes.get(compId));
                		graph.removeNode(ListforVoltageNodes.get(compId+1));
                		graph.removeNode(n);
                		if(voltageDeletedId.size()>0 && voltageDeletedId.get(voltageDeletedId.size()-1) == n.getLabelId()){
            				//do nothing
            			}else
            			{
            				voltageDeletedId.add(n.getLabelId());
            			}
                	}
            		
            		GraphFrame.displayOutPutLogg("You can only place components over the pins");
            		System.out.println("You cant put here");
            	}
            	
            	//if component's left node is close to the pin and pin is not available then remove it and move away the nodes to make the node available 
            	 if (distance <= 15 && !GraphFrame.nodeGridList.get(i).isAvailable()){
            		int compId = n.getCount()*2 - 1;
            		graph.removeNode(n);
            		if(n.getClass().getName().toString() == "resistorNode"){
            		graph.removeNode(ListforResistorNodes.get(compId));
            		graph.removeNode(ListforResistorNodes.get(compId+1));
            		ListforResistorNodes.get(compId).translate(3000, 3000);
            		ListforResistorNodes.get(compId+1).translate(3000, 3000);
            		resistorDeletedId.add(((Node) selected).getLabelId());
            		}
            		if(n.getClass().getName().toString() == "inductorNode"){
                		graph.removeNode(ListforInductorNodes.get(compId));
                		graph.removeNode(ListforInductorNodes.get(compId+1));
                		graph.removeNode(n);
                		ListforInductorNodes.get(compId).translate(3000, 3000);
                		ListforInductorNodes.get(compId+1).translate(3000, 3000);
                		inductorDeletedId.add(((Node) selected).getLabelId());
                	}
            		if(n.getClass().getName().toString() == "capacitorNode"){
                		graph.removeNode(ListforCapacitorNodes.get(compId));
                		graph.removeNode(ListforCapacitorNodes.get(compId+1));
                		graph.removeNode(n);
                		ListforCapacitorNodes.get(compId).translate(3000, 3000);
                		ListforCapacitorNodes.get(compId+1).translate(3000, 3000);
                		capacitorDeletedId.add(((Node) selected).getLabelId());
                	}
            		if(n.getClass().getName().toString() == "voltageSupplyNode"){
                		graph.removeNode(ListforVoltageNodes.get(compId));
                		graph.removeNode(ListforVoltageNodes.get(compId+1));
                		graph.removeNode(n);
                		ListforVoltageNodes.get(compId).translate(3000, 3000);
                		ListforVoltageNodes.get(compId+1).translate(3000, 3000);
                		voltageDeletedId.add(((Node) selected).getLabelId());
                	}
            		GraphFrame.displayOutPutLogg("You can't put components on each other");
            		
            	//if pin is available and distance is less than 15 then place the component on the pins by auto locating it
            	}else if(distance <= 15 && GraphFrame.nodeGridList.get(i).isAvailable())
            	{
            		if(n.getClass().getName().toString() == "resistorNode"){
            			int compId = n.getCount()*2 - 1;
            			ListforResistorNodes.get(compId).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			ListforResistorNodes.get(compId+1).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			//move the node to the pin by taking the difference between cordinates and adding the starting point of the component and it's nodes.
            			n.translate(GraphFrame.nodeGridList.get(i).getX()-currentX,GraphFrame.nodeGridList.get(i).getY()-currentY);
            		}           		
            	
            		if(n.getClass().getName().toString() == "inductorNode"){
            			int compId = n.getCount()*2 - 1;
            			ListforInductorNodes.get(compId).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			ListforInductorNodes.get(compId+1).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			n.translate(GraphFrame.nodeGridList.get(i).getX()-currentX,GraphFrame.nodeGridList.get(i).getY()-currentY);
            			//GraphFrame.nodeGridList.get(i).setAvailable(false);
            		}  
            		if(n.getClass().getName().toString() == "capacitorNode"){
            			int compId = n.getCount()*2 - 1;
            			ListforCapacitorNodes.get(compId).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			ListforCapacitorNodes.get(compId+1).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			n.translate(GraphFrame.nodeGridList.get(i).getX()-currentX,GraphFrame.nodeGridList.get(i).getY()-currentY);
            		//	GraphFrame.nodeGridList.get(i).setAvailable(false);
            		}  
            		if(n.getClass().getName().toString() == "voltageSupplyNode"){
            			int compId = n.getCount()*2 - 1;
            			ListforVoltageNodes.get(compId).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			ListforVoltageNodes.get(compId+1).translate(GraphFrame.nodeGridList.get(i).getX()-currentX, GraphFrame.nodeGridList.get(i).getY()-currentY);
            			n.translate(GraphFrame.nodeGridList.get(i).getX()-currentX,GraphFrame.nodeGridList.get(i).getY()-currentY);
            		//	GraphFrame.nodeGridList.get(i).setAvailable(false);
            		}  
            			
            	
            	}
            
        
            	}
            	//System.out.println("released a component :" + n.getClass().getName().toString() );
            	
            }
            
            if (rubberBandStart != null)
            {
               Point2D mousePoint = event.getPoint();
               Edge prototype = (Edge) tool;
               Edge newEdge = (Edge) prototype.clone();
               if (graph.connect(newEdge, rubberBandStart, mousePoint))
                  selected = newEdge;
            }

            revalidate();
            repaint();

            lastMousePoint = null;
            dragStartBounds = null;
            rubberBandStart = null;
         }
      });

      
      //edited
      addMouseMotionListener(new MouseMotionAdapter() {

         public void mouseMoved(MouseEvent event){

            Point2D pointz = event.getPoint();
            int mx = event.getX();
            int my = event.getY();

            Node hoverNode = graph.findNode(new Point(mx,my));

            if(hoverNode != null) {


               if(hoverNode.typeComp() == 4) {

                  GraphFrame.displayOutPutLogg("This is Voltage supply " + "V" + hoverNode.getLabelId());

               }else if(hoverNode.typeComp() == 1){

                  GraphFrame.displayOutPutLogg("This is Resistor " + "R" + hoverNode.getLabelId());

               }else if(hoverNode.typeComp() == 2){

                  GraphFrame.displayOutPutLogg("This is Capacitor " + "C" + hoverNode.getLabelId());

               }else if(hoverNode.typeComp() == 3){

                  GraphFrame.displayOutPutLogg("This is Inductor " + "L" + hoverNode.getLabelId());
               }
            }else{

               //GraphFrame.displayOutPutLogg(null);

            }

         }
         public void mouseDragged(MouseEvent event)
         {

            System.out.println("addMouseMotionListener");

            Point2D mousePoint = event.getPoint();
            if (dragStartBounds != null)
            {
               if (selected instanceof Node)
               {
                  Node n = (Node) selected;
                  Rectangle2D bounds = n.getBounds();

                  volState = false;
                  resState = false;
                  indState = false;
                  condState = false;
                  selectState = false;

                  if(n.getClass().getName()=="resistorNode"){
                     resistorNodeCounter = n.getCount()*2 - 1; //move the nodes that are attached to the component while moving the component thus nodes will follow the component
                     ListforResistorNodes.get(resistorNodeCounter).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                     ListforResistorNodes.get(resistorNodeCounter+1).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                  }
                  if(n.getClass().getName()=="capacitorNode"){
                     capacitorNodeCounter = n.getCount()*2 - 1;
                     ListforCapacitorNodes.get(capacitorNodeCounter).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                     ListforCapacitorNodes.get(capacitorNodeCounter+1).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                  }
                  if(n.getClass().getName()=="voltageSupplyNode"){
                     voltageNodeCounter = n.getCount()*2 - 1;
                     ListforVoltageNodes.get(voltageNodeCounter).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                     ListforVoltageNodes.get(voltageNodeCounter+1).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                  }
                  if(n.getClass().getName()=="inductorNode"){
                     inductorNodeCounter = n.getCount()*2 - 1;
                     ListforInductorNodes.get(inductorNodeCounter).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                     ListforInductorNodes.get(inductorNodeCounter+1).translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
                     System.out.println(inductorNodeCounter);
                  }

                  n.translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(), dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
               }
            }
            lastMousePoint = mousePoint;
            repaint();
         }
      });
   }


   public static void drawGrabber2(Graphics2D g2, double x, double y)
   {
      final int SIZE = 5;
      Color oldColor = g2.getColor();
      g2.setColor(PURPLE);
      g2.fill(new Rectangle2D.Double(x - SIZE / 2,
         y - SIZE / 2, SIZE, SIZE));      
      g2.setColor(oldColor);
   }
   
   /**
    Draws a single "grabber", a filled square
    @param g2 the graphics context
    @param x the x coordinate of the center of the grabber
    @param y the y coordinate of the center of the grabber
    */
   
   public static void drawGrabber(Graphics2D g2, double x, double y)
   {
      newY = y;
      newX = x;

      final int SIZE = 5;

      if(selectState){

         g2.setColor(Color.RED);
         g2.drawString("Selected", (int)xCord+15, (int)yCord-14);
         g2.draw(new Rectangle2D.Double(xCord,yCord,75,20));
         rekt = new Rectangle2D.Double(newX,newY,75,20);
     

      }

      if(resState) {

         g2.setColor(Color.RED);
         g2.drawString("Selected", (int)newX+15, (int)newY-14);
         g2.draw(new Rectangle2D.Double(newX,newY,75,20));
         rekt = new Rectangle2D.Double(newX,newY,75,20);
       

      }

      if(volState){
         g2.setColor(Color.RED);
         g2.drawString("Selected", (int)newX+15, (int)newY-5);
         g2.draw(new Rectangle2D.Double(newX,newY,70,75));
         rekt = new Rectangle2D.Double(newX,newY,75,20);
       

      }

      if(condState) {

         g2.setColor(Color.RED);
         g2.drawString("Selected", (int)x+15, (int)y-5);
         g2.draw(new Rectangle2D.Double(x,y,70,60));
         rekt = new Rectangle2D.Double(newX,newY,75,20);
    

      }

      if(indState){
         g2.setColor(Color.RED);
         g2.drawString("Selected", (int)x+15, (int)y-5);
         g2.draw(new Rectangle2D.Double(x,y,70,75));
         rekt = new Rectangle2D.Double(newX,newY,75,20);
     

      }
   }


   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      Rectangle2D bounds = getBounds();
      Rectangle2D graphBounds = graph.getBounds(g2);
      graph.draw(g2);

      if (selected instanceof Node)
      {
         Rectangle2D grabberBounds = ((Node) selected).getBounds();
         drawGrabber(g2, grabberBounds.getMinX(), grabberBounds.getMinY());
        /* drawGrabber(g2, grabberBounds.getMinX(), grabberBounds.getMaxY());
         drawGrabber(g2, grabberBounds.getMaxX(), grabberBounds.getMinY());
         drawGrabber(g2, grabberBounds.getMaxX(), grabberBounds.getMaxY()); */
      }

      if (selected instanceof Edge)
      {
         Line2D line = ((Edge) selected).getConnectionPoints();
         drawGrabber(g2, line.getX1(), line.getY1());
         drawGrabber(g2, line.getX2(), line.getY2());
      }

      if (rubberBandStart != null)
      {
         Color oldColor = g2.getColor();
         g2.setColor(PURPLE);
         g2.draw(new Line2D.Double(rubberBandStart, lastMousePoint));
         g2.setColor(oldColor);
      }
   }

   public void newSelected()
   {
      JFrame frame = new GraphFrame(new SimpleGraph());
      frame.setDefaultCloseOperation(1);
      frame.setVisible(true);
   }


   /**
    Puts a copy of the selected node or edge into the clipboard.
    */
   public void copySelected()
   {
      clipboard.clear();

      if (selected instanceof Node)
         clipboard.add(((Node) selected).clone());

      else if (selected instanceof Edge)
      {
         Edge edge = (Edge) selected;

         // Paste assumes the edge was added to the clipboard before the nodes
         clipboard.add(edge.clone());

         Node start = edge.getStart();
         clipboard.add(start.clone());

         // If start and end are the same, only put one of them in the clip
         if (start != edge.getEnd())
            clipboard.add(edge.getEnd().clone());
      }
   }

   /**
    Puts the selected node or edge into the clipboard and removes it from
    the graph.
    */
   public void cutSelected()
   {
      clipboard.clear();
      if (selected instanceof Node)
         clipboard.add(selected);

      else if (selected instanceof Edge)
      {
         Edge edge = (Edge) selected;

         // Paste assumes the edge was added to the clipboard before the nodes
         clipboard.add(edge);

         Node start = edge.getStart();
         // Since the node is still in the graph, we clone it
         clipboard.add((Node)start.clone());

         // If start and end are the same, only put one of them in the clip
         if (start != edge.getEnd())
            clipboard.add((Node)edge.getEnd().clone());
      }
      removeSelected();
   }

   /**
    Pastes whatever is in the clipboard, if anything, into the graph.
    */
   public void pasteClipboard()
   {
      if (clipboard.isEmpty())
         return;

      Object o = clipboard.get(0);
      if (o instanceof Node)
      {
         Node node = (Node) o;
         graph.add((Node)node.clone(), new Point2D.Double(0, 0));
         if(node.getClass().getName().toString() == "resistorNode"){
        	 graph.add(ListforResistorNodes.get(node.getCount()*2 - 1), new Point2D.Double(0 -1 , 0 + 2.7));
        	 graph.add(ListforResistorNodes.get(node.getCount()*2), new Point2D.Double(70,0 + 2.7));
        	 plusRes++;
        	 
         }
         if(node.getClass().getName().toString() == "voltageSupplyNode"){
        	 graph.add(ListforVoltageNodes.get(node.getCount()*2 - 1), new Point2D.Double(0 , 0));
        	 graph.add(ListforVoltageNodes.get(node.getCount()*2), new Point2D.Double(0 , 61));
        	 plusVol++;
         }
         if(node.getClass().getName().toString() == "inductorNode"){
        	 graph.add(ListforInductorNodes.get(node.getCount()*2 - 1), new Point2D.Double(0 , 35));
        	 graph.add(ListforInductorNodes.get(node.getCount()*2), new Point2D.Double(69, + 35));
        	 plusInd++;
        	 
         }
         if(node.getClass().getName().toString() == "capacitorNode"){
        	 graph.add(ListforCapacitorNodes.get(node.getCount()*2 - 1), new Point2D.Double(0 -1 , 32));
        	 graph.add(ListforCapacitorNodes.get(node.getCount()*2), new Point2D.Double(70,32));
        	 plusCap++;
         }
      }
      else // (o instanceof Edge)
      {
         Edge edge = (Edge)o;

         Node node1 = (Node)clipboard.get(1);
         // If the start and end nodes of the edge were the same, it was
         // only added once, so we must handle that special case.
         if (clipboard.size() == 2)
         {
            Rectangle2D bounds1 = node1.getBounds();
            Node clone1 = (Node) node1.clone();
            graph.add((Node)clone1, new Point2D.Double(0, 0));

            // We may have moved clone1 so we need new bounds
            bounds1 = clone1.getBounds();
            Point2D center =
                    new Point2D.Double(bounds1.getCenterX(), bounds1.getCenterY());
            graph.connect((Edge)edge.clone(), center, center);
         }
         else
         {
            Node node2 = (Node) clipboard.get(2);

            Rectangle2D bounds1 = node1.getBounds();
            Rectangle2D bounds2 = node2.getBounds();
            Rectangle2D bounds3 = new Rectangle2D.Double();
            Rectangle2D.union(bounds1, bounds2, bounds3);

            Node clone1 = (Node) node1.clone();
            Node clone2 = (Node) node2.clone();

            clone1.translate(-bounds3.getX(), -bounds3.getY());
            clone2.translate(-bounds3.getX(), -bounds3.getY());

            bounds1 = clone1.getBounds();
            bounds2 = clone2.getBounds();

            graph.add(clone1,
                    new Point2D.Double(bounds1.getX(), bounds1.getY()));
            graph.add(clone2,
                    new Point2D.Double(bounds2.getX(), bounds2.getY()));

            graph.connect((Edge)edge.clone(),
                    new Point2D.Double(bounds1.getCenterX(), bounds1.getCenterY()),
                    new Point2D.Double(bounds2.getCenterX(), bounds2.getCenterY()));
         }
      }
      repaint();
   }

   /**
    Removes the selected node or edge.
    */
   public void removeSelected()
   {
      if (selected instanceof Node) {



         if(selected.getClass().getName().toString() == "resistorNode")
         {
            int index = ((Node) selected).getCount()*2 - 1;
            graph.removeNode(ListforResistorNodes.get(index));
            graph.removeNode(ListforResistorNodes.get(index+1));
            ListforResistorNodes.get(index).translate(3000, 3000);//moved far away to take it out of gridpanel's ranged to avoid node is being full eventough component is deleted.
            ListforResistorNodes.get(index+1).translate(3000, 3000);
            resistorDeletedId.add(((Node) selected).getLabelId());
         }
         if(selected.getClass().getName().toString() == "capacitorNode")
         {
            int index = ((Node) selected).getCount()*2 - 1;
            graph.removeNode(ListforCapacitorNodes.get(index));
            graph.removeNode(ListforCapacitorNodes.get(index+1));
            graph.removeNode((Node) selected);
            ListforCapacitorNodes.get(index).translate(3000, 3000);
            ListforCapacitorNodes.get(index+1).translate(3000, 3000);
            capacitorDeletedId.add(((Node) selected).getLabelId());
         }
         if(selected.getClass().getName().toString() == "voltageSupplyNode")
         {
            int index = ((Node) selected).getCount()*2 - 1;
            graph.removeNode(ListforVoltageNodes.get(index));
            graph.removeNode(ListforVoltageNodes.get(index+1));
            graph.removeNode((Node) selected);
            ListforVoltageNodes.get(index).translate(3000, 3000);
            ListforVoltageNodes.get(index+1).translate(3000, 3000);
            voltageDeletedId.add(((Node) selected).getLabelId());
         }
         if(selected.getClass().getName().toString() == "inductorNode")
         {
            int index = ((Node) selected).getCount()*2 - 1;
            graph.removeNode(ListforInductorNodes.get(index));
            graph.removeNode(ListforInductorNodes.get(index+1));
            graph.removeNode((Node) selected);
            ListforInductorNodes.get(index).translate(3000, 3000);
            ListforInductorNodes.get(index+1).translate(3000, 3000);
            inductorDeletedId.add(((Node) selected).getLabelId());
         }
         graph.removeNode((Node) selected);

      }
      else if (selected instanceof Edge)
      {
         graph.removeEdge((Edge) selected);
      }
      selected = null;
      repaint();

   }

   //remove all components and store the deleted components id in the reletedId list thus the next
   //placed component will have the deleted component's id
   public void removeAll(){

      for(int i = 0; i < nodeArrayList.size(); i++){

         Node tempNode = nodeArrayList.get(i).nodes;

         if(tempNode.getClass().getName().toString() == "resistorNode")
         {
            int index = ((Node) tempNode).getCount()*2 - 1;
            graph.removeNode(ListforResistorNodes.get(index));
            graph.removeNode(ListforResistorNodes.get(index+1));
            ListforResistorNodes.get(index).translate(3000, 3000);//moved far away to take it out of gridpanel's ranged to avoid node is being full eventough component is deleted.
            ListforResistorNodes.get(index+1).translate(3000, 3000);
            boolean alreadyhave = false;
            for(int ii=0;ii<resistorDeletedId.size();ii++){
            	if(tempNode.getLabelId() == resistorDeletedId.get(ii)){
            		alreadyhave = true;
            	}
            }
            if(!alreadyhave) //checks if the id is already in deletedid list then dont add it.
            resistorDeletedId.add(tempNode.getLabelId());
         }
         if(tempNode.getClass().getName().toString() == "capacitorNode")
         {
            int index = ((Node) tempNode).getCount()*2 - 1;
            graph.removeNode(ListforCapacitorNodes.get(index));
            graph.removeNode(ListforCapacitorNodes.get(index+1));
            graph.removeNode(tempNode);
            ListforCapacitorNodes.get(index).translate(3000, 3000);
            ListforCapacitorNodes.get(index+1).translate(3000, 3000);
            boolean alreadyhave = false;
            for(int ii=0;ii<capacitorDeletedId.size();ii++){
            	if(tempNode.getLabelId() == capacitorDeletedId.get(ii)){
            		alreadyhave = true;
            	}
            }
            if(!alreadyhave)
            	capacitorDeletedId.add(tempNode.getLabelId());
         }
         if(tempNode.getClass().getName().toString() == "voltageSupplyNode")
         {
            int index = ((Node) tempNode).getCount()*2 - 1;
            graph.removeNode(ListforVoltageNodes.get(index));
            graph.removeNode(ListforVoltageNodes.get(index+1));
            graph.removeNode(tempNode);
            ListforVoltageNodes.get(index).translate(3000, 3000);
            ListforVoltageNodes.get(index+1).translate(3000, 3000);
            boolean alreadyhave = false;
            for(int ii=0;ii<voltageDeletedId.size();ii++){
            	if(tempNode.getLabelId() == voltageDeletedId.get(ii)){
            		alreadyhave = true;
            	}
            }
            if(!alreadyhave)
            	voltageDeletedId.add(tempNode.getLabelId());
         }
         if(tempNode.getClass().getName().toString() == "inductorNode")
         {
            int index = ((Node) tempNode).getCount()*2 - 1;
            graph.removeNode(ListforInductorNodes.get(index));
            graph.removeNode(ListforInductorNodes.get(index+1));
            graph.removeNode(tempNode);
            ListforInductorNodes.get(index).translate(3000, 3000);
            ListforInductorNodes.get(index+1).translate(3000, 3000);
            boolean alreadyhave = false;
            for(int ii=0;ii<inductorDeletedId.size();ii++){
            	if(tempNode.getLabelId() == inductorDeletedId.get(ii)){
            		alreadyhave = true;
            	}
            }
            if(!alreadyhave)
            inductorDeletedId.add(tempNode.getLabelId());
         }
         //graph.removeNode(n);
         graph.removeNode(tempNode);
         //graph.removeEdge(e);

         // System.out.println(cordinateArrayList.get(i).mp);
      }
      repaint();

   }



   public Dimension getPreferredSize()
   {
      Rectangle2D bounds = graph.getBounds((Graphics2D) getGraphics());
      return new Dimension((int) bounds.getMaxX(), (int) bounds.getMaxY());
   }

   private static Graph graph;
   private ToolBar toolBar;
   private Point2D lastMousePoint;
   private Point2D rubberBandStart;
   private Point2D dragStartPoint;
   private Rectangle2D dragStartBounds;
   private static Object selected;
   private Node newNode;
   private boolean added;
   private static boolean resState = false;
   private static boolean volState= false;
   private static boolean condState= false;
   private static boolean indState= false;
   private static boolean selectState = false;

   private String attention = "";

   private static double newX;
   private static double newY;

   private static double xCord;
   private static double yCord;

   private Object selectedTools;

   private Node stateNode;
   private static Rectangle2D rekt;
   private Point2D mousePoint;

   private ArrayList<Cordinates> cordinateArrayList = new ArrayList<Cordinates>();
   private static ArrayList<myNodes> nodeArrayList  = new ArrayList<myNodes>();

   private ArrayList<Object> clipboard = new ArrayList<Object>();
   private static final Color PURPLE = new Color(0.7f, 0.4f, 0.7f);

   private ArrayList<connectionNode> ListforResistorNodes = new ArrayList<connectionNode>();
   private ArrayList<connectionNode> ListforCapacitorNodes = new ArrayList<connectionNode>();
   private ArrayList<connectionNode> ListforVoltageNodes = new ArrayList<connectionNode>();
   private ArrayList<connectionNode> ListforInductorNodes = new ArrayList<connectionNode>();
   //put all components in one array and loop trough all the components and check if the nearest
   //node is close to a component if thats true than the node is full so we cant place a component there.
   //tracked and compared the component's node instead of component itself
   private int indCounter = 0;
   private int resCounter = 0;
   private int capCounter = 0;
   private int volCounter = 0;
  
   private int resistorNodeCounter;
   private int capacitorNodeCounter;
   private int voltageNodeCounter;
   private int inductorNodeCounter;
   //keep the deleted id for resistor 
   public static ArrayList<Integer> resistorDeletedId = new ArrayList<Integer>();
   public static int resistorId = 0 ;
   public static ArrayList<Integer> inductorDeletedId = new ArrayList<Integer>();
   public static int inductorId = 0 ;
   public static ArrayList<Integer> capacitorDeletedId = new ArrayList<Integer>();
   public static int capacitorId = 0 ;
   public static ArrayList<Integer> voltageDeletedId = new ArrayList<Integer>();
   public static int voltageId = 0 ;
   public static int plusRes = 0;
   public static int plusVol = 0;
   public static int plusCap = 0;
   public static int plusInd = 0;
   public double distance(double x1,double x2,double y1,double y2){
  	 
  	 return Math.sqrt( Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
   }
}                               
