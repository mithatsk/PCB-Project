import java.awt.*;

/**
 A simple graph with round nodes and straight edges.
 */
public class SimpleGraph extends Graph
{
   public Node[] getNodePrototypes()
   {
      Node[] nodeTypes =
              {
                      new resistorNode(Color.BLACK), new voltageSupplyNode(Color.BLACK), new capacitorNode(Color.BLACK), new inductorNode(Color.BLACK)

              };
      return nodeTypes;
   }

   public Edge[] getEdgePrototypes()
   {
      Edge[] edgeTypes =
              {
                      new LineEdge()
              };
      return edgeTypes;
   }
}





