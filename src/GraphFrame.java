import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 This frame shows the toolbar and the graph.
 */
public class GraphFrame extends JFrame
{
    /**
     Constructs a graph frame that displays a given graph.
     @param graph the graph to display
     */
    public GraphFrame(final Graph graph)
    {
        this.setMinimumSize(new Dimension(1280, 960));
        this.setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.graph = graph;
    
        constructFrameComponents();
        // set up menus

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                panel.newSelected();
            }
        });
        fileMenu.add(newItem);



        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                openFile();
            }
        });
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new
                                           ActionListener()
                                           {
                                               public void actionPerformed(ActionEvent event)
                                               {
                                                   saveFile();
                                               }
                                           });
        fileMenu.add(saveItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new
                                           ActionListener()
                                           {
                                               public void actionPerformed(ActionEvent event)
                                               {
                                                   System.exit(0);
                                               }
                                           });
        fileMenu.add(exitItem);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new
                                             ActionListener()
                                             {
                                                 public void actionPerformed(ActionEvent event)
                                                 {
                                                     panel.removeSelected();
                                                 }
                                             });

        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(new
                                          ActionListener()
                                          {
                                              public void actionPerformed(ActionEvent event)
                                              {
                                                  panel.cutSelected();
                                              }
                                          });

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(new
                                           ActionListener()
                                           {
                                               public void actionPerformed(ActionEvent event)
                                               {
                                                   panel.copySelected();
                                               }
                                           });

        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(new
                                            ActionListener()
                                            {
                                                public void actionPerformed(ActionEvent event)
                                                {
                                                    panel.pasteClipboard();
                                                }
                                            });

        JMenuItem deleteAllItem = new JMenuItem("Delete All");
        deleteAllItem.addActionListener(new
                                                ActionListener()
                                                {
                                                    public void actionPerformed(ActionEvent event)
                                                    {
                                                        panel.removeAll();
                                                    }
                                                });

        JMenu editMenu = new JMenu("Edit");

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(deleteItem);
        editMenu.add(deleteAllItem);

        menuBar.add(editMenu);
    }

    public class myNodes implements Serializable{

        public Node nodes;
        public double xCord;
        public double yCord;
        public boolean available;
        public myNodes(Node n, double x , double y,boolean b){

            nodes = n;
            xCord = x;
            yCord = y;
            available = b;
            
        }
      public double getX(){
    	  return xCord;
      }
      public double getY(){
    	  return yCord;
      }
      public boolean isAvailable(){
    	  return available;
      }
      public void setAvailable(boolean b){
    	  available = b;
      }
    }

    /**
     Constructs the tool bar and graph panel.
     */
    private void constructFrameComponents() {
        toolBar = new ToolBar(graph);
        panel = new GraphPanel(toolBar, graph);

        scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(new TitledBorder("Playground"));
        scrollPane.setBounds(210, 21, 1000, 660);

        //Creates grid with nodes
     for (double x = 0; x < 1000; x = x + 70) {
            for (double y = 0; y < 660; y = y + 60) {
                connectionNode cn = new connectionNode(Color.GRAY);
               // cn.setDefaultSize(0);
                graph.add(cn, new Point2D.Double(x, y));
                nodeGridList.add(new myNodes(cn,x,y,true));
            }
        }

        toolBar.setBorder(new TitledBorder("ToolBar"));
        toolBar.setBounds(20, 20, 185, 300);

        bottomRightPanel = new JPanel();
        bottomRightPanel.setBorder(new TitledBorder("Price-log"));
        bottomRightPanel.setLayout(null);
        bottomRightPanel.setBounds(720, 690, 490, 190);

        bottomToolBar = new JPanel();
        bottomToolBar.setBorder(new TitledBorder("Check the cost"));
        bottomToolBar.setLayout(null);
        bottomToolBar.setBounds(20, 320, 185, 520);


       


        outPut = new JTextArea();
        outPut.setBounds(10, 20, 470, 120);

        outPut2 = new JTextArea();
        outPut2.setBounds(10, 20, 470, 120);

        JButton clearButton = new JButton("Clear Price-Log");
        clearButton.setBounds(5, 145, 480, 40);
        bottomRightPanel.add(clearButton);

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outPut2.setText(null);
            }
        });
        
        ImageIcon imageIcon = new ImageIcon("src/shoppingchart.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        JLabel calculateLabel = new JLabel(imageIcon);
        JLabel calculateLabelText = new JLabel("Calculate the price:");
        calculateLabelText.setBounds(20, 40, 150, 20);
        calculateLabel.setBounds(50, 70, 50, 50);

        bottomToolBar.add(calculateLabel);
        bottomToolBar.add(calculateLabelText);
        calculateLabel.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

            	calculatePrice();
            	
            }
        });

   


        bottomPanel = new JPanel();
        bottomPanel.setBorder(new TitledBorder("Output-log"));
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(210, 690, 490, 150);

        bottomPanel.add(outPut);
        bottomRightPanel.add(outPut2);

        this.add(scrollPane);
        this.add(toolBar);
        this.add(bottomToolBar);
        this.add(bottomPanel);
        this.add(bottomRightPanel);
    }

    public void calculatePrice(){
    	
    	int resistorPrice =  (GraphPanel.resistorId-GraphPanel.resistorDeletedId.size() + GraphPanel.plusRes)*resPrice;
    	int inductorPrice =  (GraphPanel.inductorId-GraphPanel.inductorDeletedId.size() + GraphPanel.plusInd)*indPrice;
    	int voltagePrice =  (GraphPanel.voltageId-GraphPanel.voltageDeletedId.size() + GraphPanel.plusVol)*volPrice;
    	int capacitorPrice =  (GraphPanel.capacitorId-GraphPanel.capacitorDeletedId.size() + GraphPanel.plusCap)*capPrice;
    	int totalprice = resistorPrice + inductorPrice + voltagePrice + capacitorPrice;
    	outPut2.setText("Price for resistors is:" + resistorPrice);
    	outPut2.append("\nPrice for inductors is:" + inductorPrice);
    	outPut2.append("\nPrice for voltages is:" + voltagePrice);
    	outPut2.append("\nPrice for capacitors is:" + capacitorPrice);
    	outPut2.append("\nTotal cost :" + totalprice);
     }

    public static void displayOutPutLogg(String s){
        outPut.setText(null);
        displayText = s;
        outPut.append(displayText + " \n");
    }

    public static void clearErrorLogg(boolean state){

        outPut.setText(null);
    }

   
    /**
     Asks the user to open a graph file.
     */
    private void openFile()
    {
        // let user select file

        JFileChooser fileChooser = new JFileChooser();
        int r = fileChooser.showOpenDialog(this);
        if (r == JFileChooser.APPROVE_OPTION)
        {
            // open the file that the user selected
            try
            {
                File file = fileChooser.getSelectedFile();
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(file));
                graph = (Graph) in.readObject();
                in.close();
                this.remove(scrollPane);
                this.remove(toolBar);
                this.remove(bottomToolBar);
                this.remove(bottomRightPanel);
                this.remove(bottomPanel);
                constructFrameComponents();
                validate();
                repaint();
            }
            catch (IOException exception)
            {
                JOptionPane.showMessageDialog(null,
                        exception);
            }
            catch (ClassNotFoundException exception)
            {
                JOptionPane.showMessageDialog(null,
                        exception);
            }
        }
    }

    /**
     Saves the current graph in a file.
     */
    private void saveFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this)
                == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                File file = fileChooser.getSelectedFile();
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(file));
                out.writeObject(graph);
                out.close();
            }
            catch (IOException exception)
            {
                JOptionPane.showMessageDialog(null,
                        exception);
            }
        }
    }

    private Graph graph;
    private GraphPanel panel;
    public JScrollPane scrollPane;
    private ToolBar toolBar;
    private static JTextArea outPut;
    public static JTextArea outPut2;
    private JPanel bottomPanel;
    private JPanel bottomToolBar;
    private JPanel bottomRightPanel;
    //private String sum;
    private static String displayText;
    //prices
    private int resPrice = 5;
    private int volPrice = 15;
    private int indPrice = 15;
    private int capPrice = 20;
    //prices end
    public static ArrayList<myNodes> nodeGridList  = new ArrayList<myNodes>();

    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 400;
}
